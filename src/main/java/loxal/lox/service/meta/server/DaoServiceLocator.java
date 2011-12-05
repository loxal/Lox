/*
 * Copyright 2011 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 * Use of this source code is governed by a BSD-style
 * license that can be found in the LICENSE file.
 */

package loxal.lox.service.meta.server;

import com.google.web.bindery.requestfactory.shared.ServiceLocator;

/**
 * @author Alexander Orlov <a.orlov@digitalpublishing.de>
 */
public class DAOServiceLocator implements ServiceLocator {
    @Override
    public Object getInstance(final Class<?> clazz) {
        try {
            return clazz.newInstance();
        } catch (final InstantiationException e) {
            throw new RuntimeException(e);
        } catch (final IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
