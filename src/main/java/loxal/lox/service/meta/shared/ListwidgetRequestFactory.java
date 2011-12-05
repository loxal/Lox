/*
 * Copyright 2011 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 * Use of this source code is governed by a BSD-style
 * license that can be found in the LICENSE file.
 */

package loxal.lox.service.meta.shared;

import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.RequestFactory;
import com.google.web.bindery.requestfactory.shared.Service;
import loxal.lox.service.meta.server.DAOServiceLocator;
import loxal.lox.service.meta.server.ItemListDao;

public interface ListwidgetRequestFactory extends RequestFactory {
    /**
     * Service stub for methods in ItemListDao
     * <p/>
     * TODO Enhance RequestFactory to enable service stubs to extend a base interface
     * so we don't have to repeat methods from the base ObjectifyDao in each stub
     */
    @Service(value = ItemListDao.class, locator = DAOServiceLocator.class)
    interface ItemListRequestContext extends RequestContext {
//        Request<List<ItemListProxy>> listAll();
//
//        Request<Void> save(ItemListProxy list);
//
//        Request<ItemListProxy> saveAndReturn(ItemListProxy newList);
//
//        Request<Void> removeList(ItemListProxy list);
    }

    ItemListRequestContext itemListRequest();
}
