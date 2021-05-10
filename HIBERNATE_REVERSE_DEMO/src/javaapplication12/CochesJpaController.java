/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication12;

import java.io.Serializable;
import java.util.List;
import javaapplication12.exceptions.NonexistentEntityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author davidf
 */
public class CochesJpaController implements Serializable {

    public CochesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Coches coches) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Persona persona = coches.getPersona();
            if (persona != null) {
                persona = em.getReference(persona.getClass(), persona.getId());
                coches.setPersona(persona);
            }
            em.persist(coches);
            if (persona != null) {
                persona.getCocheses().add(coches);
                persona = em.merge(persona);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Coches coches) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Coches persistentCoches = em.find(Coches.class, coches.getId());
            Persona personaOld = persistentCoches.getPersona();
            Persona personaNew = coches.getPersona();
            if (personaNew != null) {
                personaNew = em.getReference(personaNew.getClass(), personaNew.getId());
                coches.setPersona(personaNew);
            }
            coches = em.merge(coches);
            if (personaOld != null && !personaOld.equals(personaNew)) {
                personaOld.getCocheses().remove(coches);
                personaOld = em.merge(personaOld);
            }
            if (personaNew != null && !personaNew.equals(personaOld)) {
                personaNew.getCocheses().add(coches);
                personaNew = em.merge(personaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = coches.getId();
                if (findCoches(id) == null) {
                    throw new NonexistentEntityException("The coches with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Coches coches;
            try {
                coches = em.getReference(Coches.class, id);
                coches.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The coches with id " + id + " no longer exists.", enfe);
            }
            Persona persona = coches.getPersona();
            if (persona != null) {
                persona.getCocheses().remove(coches);
                persona = em.merge(persona);
            }
            em.remove(coches);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Coches> findCochesEntities() {
        return findCochesEntities(true, -1, -1);
    }

    public List<Coches> findCochesEntities(int maxResults, int firstResult) {
        return findCochesEntities(false, maxResults, firstResult);
    }

    private List<Coches> findCochesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Coches.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Coches findCoches(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Coches.class, id);
        } finally {
            em.close();
        }
    }

    public int getCochesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Coches> rt = cq.from(Coches.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
