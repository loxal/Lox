/*
 * Copyright 2011 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 * Use of this source code is governed by a BSD-style
 * license that can be found in the LICENSE file.
 */

package loxal.lox.meta.shared;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;
import loxal.lox.meta.model.ItemList;
import loxal.lox.meta.server.ObjectifyLocator;

import java.util.List;

@ProxyFor(value = ItemList.class, locator = ObjectifyLocator.class)
public interface ItemListProxy extends EntityProxy {
    public enum ListType {
        NOTES, TODO,
    }

    String getName();

    void setName(String name);

    List<ListItemProxy> getItems();

    ListType getListType();

//    AppUserProxy getOwner();
//    Key<AppUser> getOwner();

    void setListType(ListType type);

    void setItems(List<ListItemProxy> asList);
}
