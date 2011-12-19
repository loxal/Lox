/*
 * Copyright 2011 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 * Use of this source code is governed by a BSD-style
 * license that can be found in the LICENSE file.
 */

package loxal.lox.persistence;

import com.google.appengine.api.datastore.*;
import com.google.appengine.demos.helloorm.Flight;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static com.google.appengine.api.datastore.FetchOptions.Builder.withLimit;
import static junit.framework.Assert.assertEquals;

public class InitDatastore extends Common {
    private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

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
        final EntityController ec = new EntityController();
        final Flight flight = new Flight("origin", "destination");
        ec.create(flight);
    }

    @Test
    public void create1() {
        final EntityController ec = new EntityController();
        final Flight flight = new Flight("origin", "destination");
        ec.create(flight);
    }

    @Test
    public void retrieve() {
        final EntityController ec = new EntityController();
        System.err.println("isEmpty = " + ec.retrieve(Flight.class).isEmpty());
    }

    @Test
    public void zFind() {
        final EntityController ec = new EntityController();
        System.out.println("flight###1 = " + ec.find(Flight.class, 1));
    }

    private void doTest() {
        DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
        assertEquals(0, ds.prepare(new Query("yam")).countEntities(withLimit(10)));
        ds.put(new Entity("yam"));
        Key k = ds.put(new Entity("yam"));
        System.out.println("k = " + k);
        assertEquals(2, ds.prepare(new Query("yam")).countEntities(withLimit(10)));
    }

    @Test
    public void testInsert1() {
        doTest();
    }

    @Test
    public void testInsert2() {
        doTest();
    }
}
