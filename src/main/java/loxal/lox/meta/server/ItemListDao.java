/*
 * Copyright 2011 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 * Use of this source code is governed by a BSD-style
 * license that can be found in the LICENSE file.
 */

package loxal.lox.meta.server;

import loxal.lox.meta.model.AppUser;
import loxal.lox.meta.model.ItemList;

import java.util.List;

public class ItemListDao extends ObjectifyDao<ItemList> {
    @Override
    public List<ItemList> listAll() {
        return this.listAllForUser();
    }

    /**
     * Wraps put() so as not to return a Key, which RF can't handle
     */
    public void save(ItemList list) {
//        AppUser loggedInUser = LoginService.getLoggedInUser();
//            list.setOwner(loggedInUser);
        this.put(list);
    }

    public ItemList saveAndReturn(ItemList list) {
//        AppUser loggedInUser = LoginService.getLoggedInUser();
//        list.setOwner(loggedInUser);
//        Key<ItemList> key = this.put(list);
//        try {
//            return this.get(key);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
        return null;
    }

    /**
     * Remove a list. Since items are embedded, they are removed automatically.
     *
     * @param list
     */
    public void removeList(ItemList list) {
//        this.delete(list);
    }
}
