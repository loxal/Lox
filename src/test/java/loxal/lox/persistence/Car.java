/*
 * Copyright 2011 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 * Use of this source code is governed by a BSD-style
 * license that can be found in the LICENSE file.
 */

package loxal.lox.persistence;

import javax.persistence.Id;

class Car {
    @Id
    String vin; // Can be Long, long, or String
    String color;

    Car(String vin, String color) {
        this.vin = vin;
        this.color = color;
    }

    Car() {
    }
}