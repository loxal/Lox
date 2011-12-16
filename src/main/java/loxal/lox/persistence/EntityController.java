/*
 * Copyright 2011 Alexander Orlov <alexander.orlov@loxal.net>. All rights reserved.
 * Use of this source code is governed by a BSD-style
 * license that can be found in the LICENSE file.
 */

package loxal.lox.persistence;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
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
        final EntityManager em = getEntityManager();
        try {
            final EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                em.merge(entity);
                tx.commit();
                em.clear();
            } finally {
                rollbackIfStillActive(tx);
            }
        } finally {
            em.close();
        }
    }

    public List<?> retrieve(final Class<?> cls) {
        final EntityManager em = getEntityManager();
        try {
            final EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                final CriteriaBuilder cb = em.getCriteriaBuilder();
                final CriteriaQuery<?> cq = cb.createQuery(cls);
                final TypedQuery<?> q = em.createQuery(cq);
                final List<?> entities = q.getResultList();
                tx.commit();

                return entities;
            } finally {
                rollbackIfStillActive(tx);
            }
        } finally {
            em.close();
        }
    }

    public Object find(final Class<?> cls, final int id) {
        final EntityManager em = getEntityManager();
        try {
            final EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                final Object entity = em.find(cls, id);
                tx.commit();
                return entity;
            } finally {
                rollbackIfStillActive(tx);
            }
        } finally {
            em.close();
        }
    }
}
