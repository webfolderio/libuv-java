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

/**
 * Idle handles will run the given callback once <strong>per loop
 * iteration</strong>, right before the {@link PrepareCallback} handles.
 * <p>
 * See <a href="http://docs.libuv.org/en/v1.x/idle.html">Idle handle</a>
 */
public class IdleHandle extends Handle {

    private boolean closed;

    private IdleCallback onIdle;

    private CloseCallback onClose;

    static {
        _static_initialize();
    }

    /**
     * Attach a {@link IdleCallback}.
     * 
     * @param callback A Callback, which will be invoked once <strong>per loop
     *                 iteration</strong>, right before the {@link PrepareCallback}
     *                 handles.
     */
    public void setIdleCallback(final IdleCallback callback) {
        if (onIdle != null) {
            throw new IllegalStateException();
        }
        onIdle = callback;
    }

    /**
     * Attach a {@link CloseCallback}.
     * 
     * @param callback A Callback, which will be invoked when check handle is
     *                 closed.
     *
     * @throws IllegalStateException if this method called more than once.
     */
    public void setCloseCallback(final CloseCallback callback) {
        if (onClose != null) {
            throw new IllegalStateException();
        }
        onClose = callback;
    }

    IdleHandle(final LoopHandle loop) {
        super(_new(loop.pointer()), loop);
        _initialize(pointer);
    }

    /**
     * Starts the idle handle with the callback specified
     * {@link #setIdleCallback(IdleCallback)}.
     * 
     * @return {@code 0} on success, or an error {@code code < 0} on failure.
     */
    public int start() {
        return _start(pointer);
    }

    /**
     * Stop the handle, the callback will no longer be called.
     * 
     * @return {@code 0} on success, or an error {@code code < 0} on failure.
     */
    public int stop() {
        return _stop(pointer);
    }

    /**
     * Close the handle and free up any resources that may be held by it.
     * 
     * @return {@code 0} on success, or an error {@code code < 0} on failure.
     */
    public void close() {
        if (!closed) {
            _close(pointer);
        }
        closed = true;
    }

    // ------------------------------------------------------------------------
    // ~ Private
    // ------------------------------------------------------------------------

    private void callback(final int type,
                          final int status) {
        switch (type) {
        case 1:
            if (onIdle != null) {
                loop.getCallbackHandler().handleIdleCallback(onIdle, status);
            }
            break;
        case 2:
            if (onClose != null) {
                loop.getCallbackHandler().handleIdleCallback(onClose, status);
            }
            break;
        default:
            assert false : "unsupported callback type " + type;
        }
    }

    // ------------------------------------------------------------------------
    // ~ Native
    // ------------------------------------------------------------------------

    private static native long _new(final long loop);

    private static native void _static_initialize();

    private native void _initialize(final long ptr);

    private native int _start(final long ptr);

    private native int _stop(final long ptr);

    private native void _close(final long ptr);
}
