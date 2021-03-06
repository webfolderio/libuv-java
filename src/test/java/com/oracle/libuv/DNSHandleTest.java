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

import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.Assert;
import org.junit.Test;

public class DNSHandleTest extends TestBase {

    @Test
    public void testDns() throws Throwable {
        final AtomicBoolean gotCallback = new AtomicBoolean(false);
        final AtomicBoolean gotClose = new AtomicBoolean(false);

        final HandleFactory handleFactory = new DefaultHandleFactory(new LoopHandle());
        final LoopHandle loop = handleFactory.getLoopHandle();

        final DNSHandle dnsHandleWf = handleFactory.newDnsHandle("webfolder.io");

        dnsHandleWf.setDnsCallback(new DnsCallback() {

            @Override
            public void onAddress(Address address, int status) throws Exception {
                Assert.assertEquals("138.68.14.207", address.getIp());
                Assert.assertEquals("IPv4", address.getFamily());
                gotCallback.compareAndSet(false, true);
                gotClose.compareAndSet(false, true);
            }
        });

        int ret = dnsHandleWf.execute();
        Assert.assertEquals(0, ret);

        final DNSHandle dnsHandleLocal = handleFactory.newDnsHandle("localhost");

        dnsHandleLocal.setDnsCallback(new DnsCallback() {

            @Override
            public void onAddress(Address address, int status) throws Exception {
            	if ("IPv4".equals(address.getFamily())) {
                    Assert.assertEquals("127.0.0.1", address.getIp());
                    Assert.assertEquals("IPv4", address.getFamily());            		
            	} else {
                    Assert.assertEquals("::1", address.getIp());
                    Assert.assertEquals("IPv6", address.getFamily());
            	}
                gotCallback.compareAndSet(false, true);
                gotClose.compareAndSet(false, true);
            }
        });

        int ret2 = dnsHandleLocal.execute();
        Assert.assertEquals(0, ret2);

        final long start = System.currentTimeMillis();
        while (!gotCallback.get() || !gotClose.get()) {
            if (System.currentTimeMillis() - start > TestBase.TIMEOUT) {
                Assert.fail("timeout waiting for idle");
            }
            loop.runNoWait();
        }

        Assert.assertTrue(gotCallback.get());
        Assert.assertTrue(gotClose.get());
    }
}
