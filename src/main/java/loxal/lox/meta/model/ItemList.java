/*
 * Copyright 2011 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 * Use of this source code is governed by a BSD-style
 * license that can be found in the LICENSE file.
 */

package loxal.lox.meta.model;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import loxal.lox.meta.shared.ItemListProxy;

import javax.persistence.Embedded;
import java.util.List;

/**
 * POJO that represents a list of items such as a ToDo list.
 * The items are stored as an embedded object.
 */
@Entity
public class ItemList extends DatastoreObject {
    private String name;
    private Key<AppUser> owner;
    private ItemListProxy.ListType listType;
    @Embedded
    private List<ListItem> items;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Key<AppUser> getOwner() {
        return owner;
    }

    public void setOwner(Key<AppUser> owner) {
        this.owner = owner;
    }

    public ItemListProxy.ListType getListType() {
        return listType;
    }

    public void setListType(ItemListProxy.ListType listType) {
        this.listType = listType;
    }

    public List<ListItem> getItems() {
        return items;
    }

    public void setItems(List<ListItem> items) {
        this.items = items;
    }
}
