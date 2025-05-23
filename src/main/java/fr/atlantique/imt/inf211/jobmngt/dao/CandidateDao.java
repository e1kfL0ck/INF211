package fr.atlantique.imt.inf211.jobmngt.dao;
// Generated 3 mars 2025, 15:44:52 by Hibernate Tools 5.6.15.Final


import fr.atlantique.imt.inf211.jobmngt.entity.AppUser;
import fr.atlantique.imt.inf211.jobmngt.entity.Candidate;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Home object for domain model class Candidate.
 *
 * @author Hibernate Tools
 * @see .Candidate
 */
@Repository
public class CandidateDao {

    private static final Logger logger = Logger.getLogger(CandidateDao.class.getName());

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void persist(Candidate transientInstance) {
        logger.log(Level.INFO, "persisting Candidate instance");
        try {
            entityManager.persist(transientInstance);
            logger.log(Level.INFO, "persist successful");
        } catch (RuntimeException re) {
            logger.log(Level.SEVERE, "persist failed", re);
            throw re;
        }
    }

    @Transactional
    public void remove(Candidate persistentInstance) {
        logger.log(Level.INFO, "removing Candidate instance");
        try {
            AppUser appUser = persistentInstance.getAppuser();
            entityManager.remove(persistentInstance);
            if (appUser != null) {
                entityManager.remove(appUser);
            }
            logger.log(Level.INFO, "remove successful");
        } catch (RuntimeException re) {
            logger.log(Level.SEVERE, "remove failed", re);
            throw re;
        }
    }

    @Transactional
    public Candidate merge(Candidate detachedInstance) {
        logger.log(Level.INFO, "merging Candidate instance");
        try {
            Candidate result = entityManager.merge(detachedInstance);
            logger.log(Level.INFO, "merge successful");
            return result;
        } catch (RuntimeException re) {
            logger.log(Level.SEVERE, "merge failed", re);
            throw re;
        }
    }

    @Transactional(readOnly = true)
    public Candidate findById(int id) {
        logger.log(Level.INFO, "getting Candidate instance with id: " + id);
        try {
            Candidate instance = entityManager.find(Candidate.class, id);
            logger.log(Level.INFO, "get successful");
            return instance;
        } catch (RuntimeException re) {
            logger.log(Level.SEVERE, "get failed", re);
            throw re;
        }
    }

    @Transactional(readOnly = true)
    public List<Candidate> findAll(String sort, String order) {
        String r = "SELECT c FROM Candidate c ORDER BY c." + sort;
        if (order.equals("asc")) {
            r += " ASC";
        }
        else {
            r += " DESC";
        }
        TypedQuery<Candidate> q = entityManager.createQuery(r, Candidate.class);
        return q.getResultList();
    }
}

