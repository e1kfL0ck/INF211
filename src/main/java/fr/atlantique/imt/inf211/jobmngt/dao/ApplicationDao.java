package fr.atlantique.imt.inf211.jobmngt.dao;
// Generated 3 mars 2025, 15:44:52 by Hibernate Tools 5.6.15.Final


import fr.atlantique.imt.inf211.jobmngt.entity.Application;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Home object for domain model class Application.
 *
 * @author Hibernate Tools
 * @see .Application
 */
@Repository
public class ApplicationDao {

    private static final Logger logger = Logger.getLogger(ApplicationDao.class.getName());

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void persist(Application transientInstance) {
        logger.log(Level.INFO, "persisting Application instance");
        try {
            entityManager.persist(transientInstance);
            logger.log(Level.INFO, "persist successful");
        } catch (RuntimeException re) {
            logger.log(Level.SEVERE, "persist failed", re);
            throw re;
        }
    }

    @Transactional
    public void remove(Application persistentInstance) {
        logger.log(Level.INFO, "removing Application instance");
        try {
            entityManager.remove(persistentInstance);
            logger.log(Level.INFO, "remove successful");
        } catch (RuntimeException re) {
            logger.log(Level.SEVERE, "remove failed", re);
            throw re;
        }
    }

    @Transactional
    public Application merge(Application detachedInstance) {
        logger.log(Level.INFO, "merging Application instance");
        try {
            Application result = entityManager.merge(detachedInstance);
            logger.log(Level.INFO, "merge successful");
            return result;
        } catch (RuntimeException re) {
            logger.log(Level.SEVERE, "merge failed", re);
            throw re;
        }
    }

    @Transactional(readOnly = true)
    public Application findById(int id) {
        logger.log(Level.INFO, "getting Application instance with id: " + id);
        try {
            Application instance = entityManager.find(Application.class, id);
            logger.log(Level.INFO, "get successful");
            return instance;
        } catch (RuntimeException re) {
            logger.log(Level.SEVERE, "get failed", re);
            throw re;
        }
    }

    @Transactional(readOnly = true)
    public List<Application> findAll() {
        logger.log(Level.INFO, "getting all Application instances");
        try {
            TypedQuery<Application> query = entityManager.createQuery("SELECT a FROM Application a", Application.class);
            List<Application> res = query.getResultList();
            logger.log(Level.INFO, "get successful");
            return res;
        } catch (RuntimeException e) {
            logger.log(Level.SEVERE, "get failed", e);
            throw e;
        }
    }

    @Transactional
    public List<Application> getByCandidateId(int id) {
        logger.log(Level.INFO, "getting Application instance with candidate id: " + id);
        String r = "SELECT a FROM Application a " + "JOIN a.candidate c " + "WHERE c.id = :id";

        TypedQuery<Application> q = entityManager.createQuery(r, Application.class);

        q.setParameter("id", id);
        List<Application> res = q.getResultList();

        logger.log(Level.INFO, "get successful");
        return res;
    }


    @Transactional(readOnly = true)
    public Optional<List<Application>> getApplications(int idQualificationLevel, int idSector) {
        logger.log(Level.INFO, "getting Application instance with idQualificationLevel: " + idQualificationLevel + " and idSector: " + idSector);
        String r = "SELECT a FROM Application a " + "JOIN a.qualificationlevel q " + "JOIN a.sectors s " + "WHERE q.id = :idQualificationLevel AND s.id = :idSector";

        TypedQuery<Application> q = entityManager.createQuery(r, Application.class);

        q.setParameter("idQualificationLevel", idQualificationLevel);
        q.setParameter("idSector", idSector);
        List<Application> res = q.getResultList();

        if (res.isEmpty()) {
            return Optional.empty();
        }
        logger.log(Level.INFO, "get successful");
        return Optional.of(res);
    }
}

