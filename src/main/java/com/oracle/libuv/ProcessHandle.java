/*
 * Copyright (c) 2013, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */
package com.oracle.libuv;

import static java.lang.System.arraycopy;
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class ProcessHandle extends Handle {

    public enum ProcessFlags {
        NONE(0),
        /**
         * sets the child's execution user ID
         */
        SETUID(1 << 0),
        /**
         * sets the child's execution group ID
         */
        SETGID(1 << 1),
        /**
         * No quoting or escaping of args is done on Windows. Ignored on Unix.
         */
        WINDOWS_VERBATIM_ARGUMENTS(1 << 2),
        /**
         * Spawn the child process in a detached state.
         * <p>
         * This will make it a process group leader, and will effectively enable the
         * child to keep running after the parent exits. Note that the child process
         * will still keep the parent's event loop alive unless the parent process calls
         * uv_unref() on the child's process handle.
         */
        DETACHED(1 << 3),
        /**
         * Hide the subprocess window that would normally be created.
         * <p>
         * This option is only meaningful on Windows systems. On Unix it is silently
         * ignored.
         */
        WINDOWS_HIDE(1 << 4),
        /**
         * Hide the subprocess console window that would normally be created.
         * <p>
         * This option is only meaningful on Windows systems. On Unix it is silently
         * ignored.
         */
        WINDOWS_HIDE_CONSOLE(1 << 5),
        /**
         * Hide the subprocess GUI window that would normally be created.
         * <p>
         * This option is only meaningful on Windows systems. On Unix it is silently
         * ignored.
         */
        WINDOWS_HIDE_GUI(1 << 6);

        final int value;

        ProcessFlags(final int value) {
            this.value = value;
        }
    }

    private boolean closed;

    static {
        _static_initialize();
    }

    private ProcessCloseCallback onClose;

    private ProcessExitCallback onExit;

    ProcessHandle(final LoopHandle loop) {
        super(_new(loop.pointer()), loop);
        this.closed = false;
        _initialize(pointer);
    }

    public void setCloseCallback(final ProcessCloseCallback callback) {
        onClose = callback;
    }

    public void setExitCallback(final ProcessExitCallback callback) {
        onExit = callback;
    }

    /**
     * Initializes the process handle and starts the process.
     * <p>
     * If the process is successfully spawned, this function will return 0.
     * Otherwise, the negative error code corresponding to the
     * reason it couldn't spawn is returned.
     * <p>
     * Possible reasons for failing to spawn would include (but not be limited to) the file
     * to execute not existing, not having permissions to use the setuid or setgid specified,
     * or not having enough memory to allocate for the new process.
     */
    public int spawn(final String                program,
                     final String[]              args,
                     final String[]              env,
                     final String                dir,
                     final EnumSet<ProcessFlags> flags,
                     final StdioOptions[]        stdio,
                     final int                   uid,
                     final int                   gid) {
        requireNonNull(program);
        requireNonNull(args);
        assert args.length > 0;

        char[] cmdChars = args[0].toCharArray();
        boolean inQuote = false;
        List<String> javaArgs = new ArrayList<String>();
        int start = 0;
        int end = 0;

        for (int i = 0; i < cmdChars.length; i++) {
            switch (cmdChars[i]) {
            case '\"': {
                if (inQuote) {
                    // Closing quote
                    inQuote = false;
                } else {
                    // Opening quote
                    inQuote = true;
                }
                break;
            }
            case ' ': {
                if (!inQuote) {
                    javaArgs.add(args[0].substring(start, end));
                    start = ++end;
                    continue;
                }
                break;
            }
            }
            end++;
        }
        javaArgs.add(args[0].substring(start, end));

        final String[] arguments = new String[args.length + javaArgs.size() - 1];
        arraycopy(javaArgs.toArray(), 0, arguments, 0, javaArgs.size());
        arraycopy(args, 1, arguments, javaArgs.size(), args.length - 1);

        int[] stdioFlags = null;
        long[] streamPointers = null;
        int[] fds = null;
        if (stdio != null && stdio.length > 0) {
            stdioFlags = new int[stdio.length];
            streamPointers = new long[stdio.length];
            fds = new int[stdio.length];
            for (int i = 0; i < stdio.length; i++) {
                stdioFlags[i] = stdio[i].type();
                streamPointers[i] = stdio[i].stream();
                fds[i] = stdio[i].fd();
            }
        } else {
            throw new IllegalArgumentException("StdioOptions cannot be null or empty");
        }

        int processFlags = ProcessFlags.NONE.value;
        if (flags.contains(ProcessFlags.WINDOWS_VERBATIM_ARGUMENTS)) {
            processFlags |= ProcessFlags.WINDOWS_VERBATIM_ARGUMENTS.value;
        }
        if (flags.contains(ProcessFlags.DETACHED)) {
            processFlags |= ProcessFlags.DETACHED.value;
        }

        return _spawn(pointer, arguments[0],
                      arguments, env,
                      dir, processFlags,
                      stdioFlags, streamPointers,
                      fds, uid,
                      gid);
    }

    public void close() {
        if (!closed) {
            _close(pointer);
        }
        closed = true;
    }

    /**
     * Sends the specified signal to the given process handle.
     */
    public int kill(final int signal) {
        return _kill(pointer, signal);
    }

    // ------------------------------------------------------------------------
    // ~ Private
    // ------------------------------------------------------------------------

    private void callClose() {
        if (onClose != null) {
            loop.getCallbackHandler().handleProcessCloseCallback(onClose);
        }
    }

    private void callExit(final int status, final int signal, final Exception error) {
        if (onExit != null) {
            loop.getCallbackHandler().handleProcessExitCallback(onExit, status, signal, error);
        }
    }

    // ------------------------------------------------------------------------
    // ~ Native
    // ------------------------------------------------------------------------

    private static native long _new(final long loop);

    private static native void _static_initialize();

    private native void _initialize(final long ptr);

    private native int _spawn(final long ptr, final String program, final String[] args, final String[] env,
            final String dir, final int flags, final int[] stdioFlags, final long[] streams, final int[] fds,
            final int uid, final int gid);

    private native void _close(final long ptr);

    private native int _kill(final long ptr, final int signal);
}
