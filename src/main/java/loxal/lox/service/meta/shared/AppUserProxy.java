/*
 * Copyright 2011 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 * Use of this source code is governed by a BSD-style
 * license that can be found in the LICENSE file.
 */

package loxal.lox.service.meta.shared;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;
import loxal.lox.service.meta.model.AppUser;
import loxal.lox.service.meta.server.ObjectifyLocator;

@ProxyFor(value = AppUser.class, locator = ObjectifyLocator.class)
public interface AppUserProxy extends EntityProxy {
    String getEmail();
}
