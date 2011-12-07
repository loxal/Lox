/*
 * Copyright 2011 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 * Use of this source code is governed by a BSD-style
 * license that can be found in the LICENSE file.
 */

package loxal.lox.meta.model;

import javax.persistence.Entity;

/**
 * An application user, named with a prefix to avoid confusion with GAE User type
 */
@Entity
public class AppUser extends DatastoreObject {
    private String email;

    public AppUser() { // required by Objectify
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
