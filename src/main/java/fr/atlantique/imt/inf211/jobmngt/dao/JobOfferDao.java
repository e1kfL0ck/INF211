package fr.atlantique.imt.inf211.jobmngt.dao;
// Generated 3 mars 2025, 15:44:52 by Hibernate Tools 5.6.15.Final


import fr.atlantique.imt.inf211.jobmngt.entity.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

/**
 * Home object for domain model class Joboffer.
 * @see .Joboffer
 * @author Hibernate Tools
 */
@Repository
public class JobOfferDao {

    private static final Logger logger = Logger.getLogger(JobOfferDao.class.getName());

    @PersistenceContext private EntityManager entityManager;

    @Transactional
    public void persist(JobOffer transientInstance) {
        logger.log(Level.INFO, "persisting Joboffer instance");
        try {
            entityManager.persist(transientInstance);
            logger.log(Level.INFO, "persist successful");
        }
        catch (RuntimeException re) {
            logger.log(Level.SEVERE, "persist failed", re);
            throw re;
        }
    }

    @Transactional
    public void remove(JobOffer persistentInstance) {
        logger.log(Level.INFO, "removing Joboffer instance");
        try {
            entityManager.remove(persistentInstance);
            logger.log(Level.INFO, "remove successful");
        }
        catch (RuntimeException re) {
            logger.log(Level.SEVERE, "remove failed", re);
            throw re;
        }
    }

    @Transactional
    public JobOffer merge(JobOffer detachedInstance) {
        logger.log(Level.INFO, "merging Joboffer instance");
        try {
            JobOffer result = entityManager.merge(detachedInstance);
            logger.log(Level.INFO, "merge successful");
            return result;
        }
        catch (RuntimeException re) {
            logger.log(Level.SEVERE, "merge failed", re);
            throw re;
        }
    }

    @Transactional(readOnly = true)
    public JobOffer findById(int id) {
        logger.log(Level.INFO, "getting Joboffer instance with id: " + id);
        try {
            JobOffer instance = entityManager.find(JobOffer.class, id);
            logger.log(Level.INFO, "get successful");
            return instance;
        }
        catch (RuntimeException re) {
            logger.log(Level.SEVERE, "get failed", re);
            throw re;
        }
    }

    @Transactional
    public Optional<JobOffer> findByCompany(int companyId) {
        logger.log(Level.INFO, "getting Joboffer instance with company id: " + companyId);
        String r = "SELECT j FROM JobOffer j WHERE j.company.id = :companyId";

        TypedQuery<JobOffer> q = entityManager.createQuery(r, JobOffer.class);
        q.setParameter("companyId", companyId);
        List<JobOffer> res = q.getResultList();

        if (res.isEmpty()) {
            return Optional.empty();
        }
        logger.log(Level.INFO, "get successful");
        return Optional.of(res.get(0));
    }
}

