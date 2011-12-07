/*
 * Copyright 2011 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 * Use of this source code is governed by a BSD-style
 * license that can be found in the LICENSE file.
 */

package loxal.lox.meta.shared;

import com.google.web.bindery.requestfactory.shared.RequestFactory;


public interface ListwidgetRequestFactory extends RequestFactory {
    ItemListRequestContext itemListRequest();
}
