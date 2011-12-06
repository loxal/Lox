/*
 * Copyright 2011 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 * Use of this source code is governed by a BSD-style
 * license that can be found in the LICENSE file.
 */

package loxal.lox.contact.client;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @author Alexander Orlov <alexander.orlov@loxal.net>
 */
public class MailMsg implements IsSerializable {
    private String senderAddress;
    private String senderName;
    private String subject;
    private String message;

    public String getSenderAddress() {
        return senderAddress;
    }

    public void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName() {
        this.senderName = "Mail Form";
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
