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

package com.oracle.libuv.handles;

import java.nio.ByteBuffer;

import com.oracle.libuv.Address;
import com.oracle.libuv.cb.AsyncCallback;
import com.oracle.libuv.cb.CallbackExceptionHandler;
import com.oracle.libuv.cb.CallbackHandler;
import com.oracle.libuv.cb.ProcessCloseCallback;
import com.oracle.libuv.cb.ProcessExitCallback;
import com.oracle.libuv.cb.StreamCloseCallback;
import com.oracle.libuv.cb.StreamConnectCallback;
import com.oracle.libuv.cb.StreamConnectionCallback;
import com.oracle.libuv.cb.StreamReadCallback;
import com.oracle.libuv.cb.StreamShutdownCallback;
import com.oracle.libuv.cb.StreamWriteCallback;
import com.oracle.libuv.cb.TimerCallback;
import com.oracle.libuv.cb.UDPCloseCallback;
import com.oracle.libuv.cb.UDPRecvCallback;
import com.oracle.libuv.cb.UDPSendCallback;

public final class LoopCallbackHandler implements CallbackHandler {

    private final CallbackExceptionHandler exceptionHandler;

    public LoopCallbackHandler(final CallbackExceptionHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }

    @Override
    public void handleAsyncCallback(final AsyncCallback cb, final int status) {
        try {
            cb.onSend(status);
        } catch (final Exception ex) {
            exceptionHandler.handle(ex);
        }
    }

    @Override
    public void handleStreamReadCallback(final StreamReadCallback cb, final ByteBuffer data) {
        try {
            cb.onRead(data);
        } catch (final Exception ex) {
            exceptionHandler.handle(ex);
        }
    }

    @Override
    public void handleStreamWriteCallback(final StreamWriteCallback cb, final int status, final Exception error) {
        try {
            cb.onWrite(status, error);
        } catch (final Exception ex) {
            exceptionHandler.handle(ex);
        }
    }

    @Override
    public void handleStreamConnectCallback(final StreamConnectCallback cb, final int status, final Exception error) {
        try {
            cb.onConnect(status, error);
        } catch (final Exception ex) {
            exceptionHandler.handle(ex);
        }
    }

    @Override
    public void handleStreamConnectionCallback(final StreamConnectionCallback cb, final int status, final Exception error) {
        try {
            cb.onConnection(status, error);
        } catch (final Exception ex) {
            exceptionHandler.handle(ex);
        }
    }

    @Override
    public void handleStreamCloseCallback(final StreamCloseCallback cb) {
        try {
            cb.onClose();
        } catch (final Exception ex) {
            exceptionHandler.handle(ex);
        }
    }

    @Override
    public void handleStreamShutdownCallback(final StreamShutdownCallback cb, final int status, final Exception error) {
        try {
            cb.onShutdown(status, error);
        } catch (final Exception ex) {
            exceptionHandler.handle(ex);
        }
    }

    @Override
    public void handleProcessCloseCallback(ProcessCloseCallback cb) {
        try {
            cb.onClose();
        } catch (Exception ex) {
            exceptionHandler.handle(ex);
        }
    }

    @Override
    public void handleProcessExitCallback(ProcessExitCallback cb, int status, int signal, Exception error) {
        try {
            cb.onExit(status, signal, error);
        } catch (Exception ex) {
            exceptionHandler.handle(ex);
        }
    }

    @Override
    public void handleTimerCallback(final TimerCallback cb, final int status) {
        try {
            cb.onTimer(status);
        } catch (final Exception ex) {
            exceptionHandler.handle(ex);
        }
    }

    @Override
    public void handleUDPRecvCallback(final UDPRecvCallback cb, final int nread, final ByteBuffer data, final Address address) {
        try {
            cb.onRecv(nread, data, address);
        } catch (final Exception ex) {
            exceptionHandler.handle(ex);
        }
    }

    @Override
    public void handleUDPSendCallback(final UDPSendCallback cb, final int status, final Exception error) {
        try {
            cb.onSend(status, error);
        } catch (final Exception ex) {
            exceptionHandler.handle(ex);
        }
    }

    @Override
    public void handleUDPCloseCallback(final UDPCloseCallback cb) {
        try {
            cb.onClose();
        } catch (final Exception ex) {
            exceptionHandler.handle(ex);
        }
    }
}
