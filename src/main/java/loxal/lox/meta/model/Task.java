/*
 * Copyright 2011 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 * Use of this source code is governed by a BSD-style
 * license that can be found in the LICENSE file.
 */

package loxal.lox.meta.model;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;

import javax.persistence.Embedded;
import java.util.List;

/**
 * @author Alexander Orlov <alexander.orlov@loxal.net>
 */
@Entity
public class Task extends DatastoreObject { // "Problem"?
    private String name;
    private String description;
    private String userEmail;
    private Integer priority;
    private String category;
    private Key<AppUser> owner;
    //    private ListType listType;
    @Embedded
    private List<ListItem> items;


    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}