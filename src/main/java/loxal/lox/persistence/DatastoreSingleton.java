/*
 * Copyright 2011 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 * Use of this source code is governed by a BSD-style
 * license that can be found in the LICENSE file.
 */

package loxal.lox.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

/**
 * Thread-safe singleton EntityManager and Transaction creator
 */
public final class DatastoreSingleton {
    private static DatastoreSingleton INSTANCE;
    private EntityManager em;
    public static EntityManagerFactory emf; // should be STATIC!
    //    private static EntityManagerFactory emf; // should be STATIC!
    //    private static EntityManager em;
//    private static EntityTransaction tx;
    private static final Map<String, Object> puConfig = new HashMap<String, Object>();

    private DatastoreSingleton() {
        final String puHandle = "default";
        emf = Persistence.createEntityManagerFactory(puHandle, puConfig); // should be STATIC!
        em = emf.createEntityManager();

//        tx = em.getTransaction();
    }

    //    public synchronized static DatastoreSingleton getSingleton() {
    public static DatastoreSingleton getSingleton() {
        if (INSTANCE == null) INSTANCE = new DatastoreSingleton(); // lazy init
        return INSTANCE;
//        return INSTANCE == null ? new DatastoreSingleton() : INSTANCE; // this kind of LAZY INIT does not work
    }

    public static EntityManagerFactory getEmf() {
        return emf;
    }

    public EntityManager getEntityManager() {
        return em;
    }

//    public static EntityTransaction getTx() {
//        return tx;
//    }

}