/*
 * Copyright 2011 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 * Use of this source code is governed by a BSD-style
 * license that can be found in the LICENSE file.
 */

package com.google.appengine.demos.helloorm;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author Max Ross <maxr@google.com>
 */
@Entity
//@PersistenceCapable(detachable = "true")
public class Flight {
    protected Flight() {
    }

    //    @PrimaryKey
//    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    private Long id;

    //    @Persistent
    private String orig;

    //    @Persistent
    private String dest;

    public Flight(String orig, String dest) {
        this.orig = orig;
        this.dest = dest;
    }

    public Long getId() {
        return id;
    }

    public String getOrig() {
        return orig;
    }

    public String getDest() {
        return dest;
    }

    public void setOrig(String orig) {
        this.orig = orig;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }
}
