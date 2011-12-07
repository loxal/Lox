/*
 * Copyright 2011 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 * Use of this source code is governed by a BSD-style
 * license that can be found in the LICENSE file.
 */

package loxal.lox.meta.shared;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;
import loxal.lox.meta.server.DAOServiceLocator;
import loxal.lox.meta.server.ItemListDao;

import java.util.List;

@Service(value = ItemListDao.class, locator = DAOServiceLocator.class)
interface ItemListRequestContext extends RequestContext {
    Request<List<ItemListProxy>> listAll();

    Request<Void> save(ItemListProxy list);

    Request<ItemListProxy> saveAndReturn(ItemListProxy newList);

    Request<Void> removeList(ItemListProxy list);
}