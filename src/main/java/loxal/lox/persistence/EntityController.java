/*
 * Copyright 2012 Alexander Orlov <alexander.orlov@loxal.net>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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

    public void create(Flight entity) {
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

    public List<Flight> retrieve() {
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

            final Flight flight = new Flight("origin", "destination");
            final Flight flight1 = new Flight("origin", "destination");
            create(flight);
            create(flight1);

            final String DEFAULT_QUERY = "SELECT f FROM Flight f";
//            final String DEFAULT_QUERY = "select f from " + Flight.class.getName() + " as f";
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

    public Flight find(final Class<?> cls, final long id) {
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
            return em.find(Flight.class, id);
//            return em.find(cls, id);
//            } finally {
//                rollbackIfStillActive(tx);
//            }
        } finally {
            em.close();
        }
    }
}
