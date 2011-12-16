/*
 * Copyright 2011 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 * Use of this source code is governed by a BSD-style
 * license that can be found in the LICENSE file.
 */
package com.google.appengine.demos.helloorm;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @author Max Ross <maxr@google.com>
 */
public final class EMF {

    private static final EntityManagerFactory INSTANCE = Persistence.createEntityManagerFactory("default");

    public static EntityManagerFactory get() {
        return INSTANCE;
    }

    private EMF() {
    }
}
