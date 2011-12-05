/*
 * Copyright 2011 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 * Use of this source code is governed by a BSD-style
 * license that can be found in the LICENSE file.
 */

package loxal.lox.service.meta.shared;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;
import loxal.lox.service.meta.model.ListItem;

import java.util.Date;

@ProxyFor(value = ListItem.class)
public interface ListItemProxy extends ValueProxy {
    String getItemText();

    void setItemText(String itemText);

    Date getDateCreated();
}
