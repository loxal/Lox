/*
 * Copyright 2011 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 * Use of this source code is governed by a BSD-style
 * license that can be found in the LICENSE file.
 */

package loxal.lox.persistence;

import com.google.appengine.demos.helloorm.EMF;
import com.google.appengine.demos.helloorm.Flight;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class EntityController {
    public void rollbackIfStillActive(final EntityTransaction tx) {
        if (tx.isActive()) {
            tx.rollback();
            System.err.println("[ROLLBACK]");
        }
    }

    public EntityManager getEntityManager() {
        final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        try {
            return entityManagerFactory.createEntityManager();
        } catch (final IllegalStateException e) {
            e.printStackTrace();
            entityManagerFactory.close();
        }
        return null;
    }

    public void create(final Object entity) {
//        final EntityManager em = getEntityManager();
//        final EntityManager em = DatastoreSingleton.getSingleton().getEntityManager();
//        final EntityManager em = DatastoreSingleton.getEmf().createEntityManager();
//        final EntityManager em = DatastoreSingleton.getSingleton().getEntityManager();
//        DatastoreSingleton ds = new DatastoreSingleton();
        EntityManager em = EMF.get().createEntityManager();
        try {
//            final EntityTransaction tx = em.getTransaction();
//            try {
//                tx.begin();
            em.persist(entity);
//                em.merge(entity);
//                em.flush();
//                tx.commit();
//                em.clear();
//            } finally {
//                rollbackIfStillActive(tx);
//            }
        } finally {
            em.close();
        }
    }

    public List<?> retrieve(final Class<?> cls) {
//        final EntityManager em = getEntityManager();
//        final EntityManager em = DatastoreSingleton.getSingleton().getEntityManager();
        EntityManager em = EMF.get().createEntityManager();
//        final EntityManager em = DatastoreSingleton.getEmf().createEntityManager();
        try {
//            final EntityTransaction tx = em.getTransaction();
//            try {
//                tx.begin();
//                final CriteriaBuilder cb = em.getCriteriaBuilder();
//                final CriteriaQuery<?> cq = cb.createQuery(cls);
//                final TypedQuery<?> q = em.createQuery(cq);
//                final List<?> entities = q.getResultList();
            final String DEFAULT_QUERY = "select f from " + Flight.class.getName() + " as f";
            List<Flight> entities = em.createQuery(DEFAULT_QUERY).getResultList();
//                List<Flight> entities = em.createQuery("select f from Flight f").getResultList();
//                tx.commit();
            System.out.println("entitiesSIZE = " + entities.size());

            return entities;
//            } finally {
//                rollbackIfStillActive(tx);
//            }
        } finally {
            em.close();
        }
    }

    public Object find(final Class<?> cls, final Long id) {
        EntityManager em = EMF.get().createEntityManager();
//        final EntityManager em = DatastoreSingleton.getEmf().createEntityManager();
//        final EntityManager em = getEntityManager();
        try {
//            final EntityTransaction tx = em.getTransaction();
//            try {
//                tx.begin();
//                final Object entity = em.find(cls, id);
//                tx.commit();
//                return entity;
            return em.find(cls, id);
//            } finally {
//                rollbackIfStillActive(tx);
//            }
        } finally {
            em.close();
        }
    }
}
