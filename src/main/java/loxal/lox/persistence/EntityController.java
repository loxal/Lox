/*
 * Copyright 2011 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 * Use of this source code is governed by a BSD-style
 * license that can be found in the LICENSE file.
 */

package loxal.lox.persistence;

import javax.persistence.EntityTransaction;

public class EntityController {
    public void rollbackIfStillActive(final EntityTransaction tx) {
        if (tx.isActive()) {
            tx.rollback();
            System.err.println("[ROLLBACK]");
        }
    }
}
