/*
 * Copyright 2011 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 * Use of this source code is governed by a BSD-style
 * license that can be found in the LICENSE file.
 */

package loxal.lox.service.meta.server;

import loxal.lox.service.meta.model.ItemList;

import java.util.List;

public class ItemListDao {
    //public class ItemListDao extends ObjectifyDao<ItemList> {
//    @Override
    public List<ItemList> listAll() {
//        return this.listAllForUser();
        return null;
    }

    /**
     * Wraps put() so as not to return a Key, which RF can't handle
     */
    public void save(ItemList list) {
//        final UserService userService = UserServiceFactory.getUserService();
//        final User user = userService.getCurrentUser();
//        AppUser loggedInUser =  user;
//        AppUser loggedInUser =  LoginService.getLoggedInUser();
//        AppUser loggedInUser = new AppUser();

//        list.setOwner(loggedInUser);
//        this.put(list);
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
