/*
 * Copyright 2011 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 * Use of this source code is governed by a BSD-style
 * license that can be found in the LICENSE file.
 */

package loxal.lox.contact.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author Alexander Orlov <alexander.orlov@loxal.net>
 */
public interface MailSvcAsync {
    void sendMail(MailMsg mailMsg, AsyncCallback<Void> callback);
}
