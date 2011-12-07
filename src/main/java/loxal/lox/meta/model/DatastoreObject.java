/*
 * Copyright 2011 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 * Use of this source code is governed by a BSD-style
 * license that can be found in the LICENSE file.
 */

package loxal.lox.meta.model;

import javax.persistence.Id;
import javax.persistence.PrePersist;

public class DatastoreObject {
    @Id
    private Long id;
    private Integer version = 0;

    @PrePersist
    void onPersist() {
        this.version++;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}