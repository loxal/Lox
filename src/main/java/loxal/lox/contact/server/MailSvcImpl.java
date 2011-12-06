/*
 * Copyright 2011 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 * Use of this source code is governed by a BSD-style
 * license that can be found in the LICENSE file.
 */

package loxal.lox.contact.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import loxal.lox.contact.client.MailMsg;
import loxal.lox.contact.client.MailSvc;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 * @author Alexander Orlov <alexander.orlov@loxal.net>
 */
public class MailSvcImpl extends RemoteServiceServlet implements MailSvc {
    public void sendMail(MailMsg mailMsg) {
        MimeMessage message = new MimeMessage(
                Session.getDefaultInstance(
                        new Properties()));
        try {
            message.setFrom(
                    new InternetAddress(
                            System.getProperty("admin.mail"), mailMsg.getSenderName()));
            message.addRecipient(
                    Message.RecipientType.TO,
                    new InternetAddress(System.getProperty("admin.mail"),
                            System.getProperty("admin.name")));
            message.setSubject(mailMsg.getSubject().isEmpty() ? "[No Subject]" : mailMsg.getSubject()); // must not be empty
            message.setText("From: " + mailMsg.getSenderAddress() + "\n\n" + mailMsg.getMessage());
            Transport.send(message);
        } catch (AddressException e) {
            System.err.println(e);
        } catch (MessagingException e) {
            System.err.println(e);
        } catch (UnsupportedEncodingException e) {
            System.err.println(e);
        }
    }
}
