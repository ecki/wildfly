/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2012, Red Hat, Inc., and individual contributors
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

package org.jboss.as.pojo.service;

import org.jboss.as.util.security.GetContextClassLoaderAction;
import org.jboss.as.util.security.SetContextClassLoaderAction;

import static java.lang.System.getSecurityManager;
import static java.lang.Thread.currentThread;
import static java.security.AccessController.doPrivileged;

/**
 * SecurityActions.
 *
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
final class SecurityActions {
    private SecurityActions() {
        // forbidden inheritance
    }

    /**
     * Gets context classloader.
     *
     * @return the current context classloader
     */
    static ClassLoader getContextClassLoader() {
        return getSecurityManager() == null ? currentThread().getContextClassLoader() : doPrivileged(GetContextClassLoaderAction.getInstance());
    }

    /**
     * Sets context classloader.
     *
     * @param classLoader
     *            the classloader
     */
    static void setContextClassLoader(final ClassLoader classLoader) {
        if (getSecurityManager() == null) {
            currentThread().setContextClassLoader(classLoader);
        } else {
            doPrivileged(new SetContextClassLoaderAction(classLoader));
        }
    }
}