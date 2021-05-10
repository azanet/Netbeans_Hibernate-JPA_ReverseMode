/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication12;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;
import javaapplication12.exceptions.IllegalOrphanException;
import javaapplication12.exceptions.NonexistentEntityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author davidf
 */
public class PersonaJpaController implements Serializable {

    public PersonaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Persona persona) {
        if (persona.getCocheses() == null) {
            persona.setCocheses(new HashSet<Coches>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Set<Coches> attachedCocheses = new HashSet<Coches>();
            for (Coches cochesesCochesToAttach : persona.getCocheses()) {
                cochesesCochesToAttach = em.getReference(cochesesCochesToAttach.getClass(), cochesesCochesToAttach.getId());
                attachedCocheses.add(cochesesCochesToAttach);
            }
            persona.setCocheses(attachedCocheses);
            em.persist(persona);
            for (Coches cochesesCoches : persona.getCocheses()) {
                Persona oldPersonaOfCochesesCoches = cochesesCoches.getPersona();
                cochesesCoches.setPersona(persona);
                cochesesCoches = em.merge(cochesesCoches);
                if (oldPersonaOfCochesesCoches != null) {
                    oldPersonaOfCochesesCoches.getCocheses().remove(cochesesCoches);
                    oldPersonaOfCochesesCoches = em.merge(oldPersonaOfCochesesCoches);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Persona persona) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Persona persistentPersona = em.find(Persona.class, persona.getId());
            Set<Coches> cochesesOld = persistentPersona.getCocheses();
            Set<Coches> cochesesNew = persona.getCocheses();
            List<String> illegalOrphanMessages = null;
            for (Coches cochesesOldCoches : cochesesOld) {
                if (!cochesesNew.contains(cochesesOldCoches)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Coches " + cochesesOldCoches + " since its persona field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Set<Coches> attachedCochesesNew = new HashSet<Coches>();
            for (Coches cochesesNewCochesToAttach : cochesesNew) {
                cochesesNewCochesToAttach = em.getReference(cochesesNewCochesToAttach.getClass(), cochesesNewCochesToAttach.getId());
                attachedCochesesNew.add(cochesesNewCochesToAttach);
            }
            cochesesNew = attachedCochesesNew;
            persona.setCocheses(cochesesNew);
            persona = em.merge(persona);
            for (Coches cochesesNewCoches : cochesesNew) {
                if (!cochesesOld.contains(cochesesNewCoches)) {
                    Persona oldPersonaOfCochesesNewCoches = cochesesNewCoches.getPersona();
                    cochesesNewCoches.setPersona(persona);
                    cochesesNewCoches = em.merge(cochesesNewCoches);
                    if (oldPersonaOfCochesesNewCoches != null && !oldPersonaOfCochesesNewCoches.equals(persona)) {
                        oldPersonaOfCochesesNewCoches.getCocheses().remove(cochesesNewCoches);
                        oldPersonaOfCochesesNewCoches = em.merge(oldPersonaOfCochesesNewCoches);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = persona.getId();
                if (findPersona(id) == null) {
                    throw new NonexistentEntityException("The persona with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Persona persona;
            try {
                persona = em.getReference(Persona.class, id);
                persona.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The persona with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Set<Coches> cochesesOrphanCheck = persona.getCocheses();
            for (Coches cochesesOrphanCheckCoches : cochesesOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Persona (" + persona + ") cannot be destroyed since the Coches " + cochesesOrphanCheckCoches + " in its cocheses field has a non-nullable persona field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(persona);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Persona> findPersonaEntities() {
        return findPersonaEntities(true, -1, -1);
    }

    public List<Persona> findPersonaEntities(int maxResults, int firstResult) {
        return findPersonaEntities(false, maxResults, firstResult);
    }

    private List<Persona> findPersonaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Persona.class));
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

    public Persona findPersona(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Persona.class, id);
        } finally {
            em.close();
        }
    }

    public int getPersonaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Persona> rt = cq.from(Persona.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
