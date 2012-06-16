/*
 * JBoss, Home of Professional Open Source.
 * Copyright (c) 2011, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.as.test.integration.web.servlet.enc.empty;

import java.io.IOException;

import javax.annotation.Resource;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;

import org.junit.Assert;


/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
@WebServlet(name="SimpleServlet", urlPatterns={"/simple"})
public class SingleServlet extends HttpServlet {

    @Resource(name = "userTransaction")
    private UserTransaction userTransaction;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            final InitialContext ic = new InitialContext();
            ic.lookup("java:comp/env");
            final NamingEnumeration<NameClassPair> list = ic.list("java:comp/env");
            Assert.assertTrue(list.hasMore());
            Assert.assertEquals("userTransaction", list.next().getName());
            Assert.assertFalse(list.hasMore());
            resp.getWriter().write("ok");
            resp.getWriter().close();
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }

    }
}
