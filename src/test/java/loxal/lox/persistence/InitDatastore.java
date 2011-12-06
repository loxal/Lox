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
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import loxal.lox.meta.model.ItemList;
import loxal.lox.meta.model.ListItem;
import loxal.lox.meta.server.ItemListDao;
import loxal.lox.meta.shared.ItemListProxy;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

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
    public void testSave() throws Exception {
        ListItem listItem = new ListItem();
        listItem.setItemText("my text");
        listItem.setDateCreated(new Date());

        ItemList itemList = new ItemList();
        itemList.setName("name");
        itemList.setListType(ItemListProxy.ListType.TODO);

        ItemListDao itemListDao = new ItemListDao();
        itemListDao.save(itemList);
    }

    @Test
    public void testList() throws Exception {  // TODO throws Exception because user isn't set
        ItemListDao itemListDao = new ItemListDao();
        List<ItemList> itemLists = itemListDao.listAll();
        System.out.println("itemLists = " + itemLists.size());
//        for (ItemList listItem: itemLists) {
//            System.out.println("listItem = " + itemLists.size());
//        }
    }

    @Test
    public void test() throws Exception {
        ObjectifyService.register(Car.class);
        Objectify ofy = ObjectifyService.begin();
        ofy.put(new Car("123123", "red"));
        Car c = ofy.get(Car.class, "123123");
        ofy.delete(c);
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
