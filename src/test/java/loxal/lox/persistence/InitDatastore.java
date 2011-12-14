/*
 * Copyright 2011 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 * Use of this source code is governed by a BSD-style
 * license that can be found in the LICENSE file.
 */

package loxal.lox.persistence;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.appengine.tools.development.testing.LocalTaskQueueTestConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class InitDatastore extends Common {
    private final LocalServiceTestHelper helper =
            new LocalServiceTestHelper(new LocalTaskQueueTestConfig());

    @Before
    public void setUp() {
        helper.setUp();
    }

    @After
    public void tearDown() {
        helper.tearDown();
    }

    @Test
    // run this test twice to prove we're not leaking any state across tests
    public void doTest() {
        DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
//        assertEquals(0, ds.prepare(new Query("yam")).countEntities(withLimit(10)));
        ds.put(new Entity("yam"));
        ds.put(new Entity("yam"));
//        assertEquals(2, ds.prepare(new Query("yam")).countEntities(withLimit(10)));
    }
}
