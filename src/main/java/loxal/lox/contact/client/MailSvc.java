/*
 * Copyright 2011 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 * Use of this source code is governed by a BSD-style
 * license that can be found in the LICENSE file.
 */

package loxal.lox.contact.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * @author Alexander Orlov <alexander.orlov@loxal.net>
 */
@RemoteServiceRelativePath("Mail")
public interface MailSvc extends RemoteService {
    void sendMail(MailMsg mailMsg);
}
