/*
 * Copyright 2011 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 * Use of this source code is governed by a BSD-style
 * license that can be found in the LICENSE file.
 */

package loxal.lox.persistence;

import com.google.appengine.demos.helloorm.Flight;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class InitDatastore extends Common {
    private final LocalServiceTestHelper helper =
            new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
    final EntityController ec = new EntityController();

    @Before
    public void setUp() {
        helper.setUp();
    }

    @After
    public void tearDown() {
        helper.tearDown();
    }

    @Test
    public void create() {
        final Flight flight = new Flight("origin", "destination");
        ec.create(flight);
    }

    @Test
    public void retrieve() {
        System.out.println("isEmpty = " + ec.retrieve(Flight.class).isEmpty());
    }

    @Test
    public void rebelFind() {
        System.out.println("flight#1 = " + ec.find(Flight.class, 1));
    }
}
