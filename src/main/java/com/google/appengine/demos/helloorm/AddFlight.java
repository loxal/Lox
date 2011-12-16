/*
 * Copyright 2011 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 * Use of this source code is governed by a BSD-style
 * license that can be found in the LICENSE file.
 */
package com.google.appengine.demos.helloorm;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Max Ross <maxr@google.com>
 */
public class AddFlight extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String orig = req.getParameter("orig");
        String dest = req.getParameter("dest");
        Flight f = new Flight(orig, dest);
        if (PersistenceStandard.get() == PersistenceStandard.JPA) {
            doPostJPA(f);
        } else {
//      doPostJDO(f);
        }
        resp.sendRedirect("/");
    }

//  private void doPostJDO(Flight f) {
//    PersistenceManager pm = PMF.get().getPersistenceManager();
//    try {
//      pm.makePersistent(f);
//    } finally {
//      pm.close();
//    }
//  }

    private void doPostJPA(Flight f) {
        EntityManager em = EMF.get().createEntityManager();
        try {
            em.persist(f);
        } finally {
            em.close();
        }
    }
}
