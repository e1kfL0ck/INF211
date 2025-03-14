package fr.atlantique.imt.inf211.jobmngt.dao;
// Generated 3 mars 2025, 15:44:52 by Hibernate Tools 5.6.15.Final


import fr.atlantique.imt.inf211.jobmngt.entity.Company;
import fr.atlantique.imt.inf211.jobmngt.entity.JobOffer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Home object for domain model class Joboffer.
 *
 * @author Hibernate Tools
 * @see .Joboffer
 */
@Repository
public class JobOfferDao {

    private static final Logger logger = Logger.getLogger(JobOfferDao.class.getName());

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void persist(JobOffer transientInstance) {
        logger.log(Level.INFO, "persisting Joboffer instance");
        Company company = transientInstance.getCompany();
        try {
            entityManager.persist(transientInstance);

            Set<JobOffer> jobOffers = company.getJobOffers();
            if (jobOffers == null) {
                jobOffers = new HashSet<>();
            }

            jobOffers.add(transientInstance);
            company.setJobOffers(jobOffers);
            entityManager.merge(company);

            logger.log(Level.INFO, "persist successful");
        } catch (RuntimeException re) {
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
        } catch (RuntimeException re) {
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
        } catch (RuntimeException re) {
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
        } catch (RuntimeException re) {
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

    @Transactional
    public Optional<List<JobOffer>> getJobOffers(int idQualificationLevel, int idSector) {
        logger.log(Level.INFO, "getting Joboffer instances with idQualificationLevel: " + idQualificationLevel + " and idSector: " + idSector);
        String r = "SELECT j FROM JobOffer j " + "JOIN j.qualificationlevel q " + "JOIN j.sectors s " + "WHERE q.id = :idQualificationLevel AND s.id = :idSector";

        TypedQuery<JobOffer> q = entityManager.createQuery(r, JobOffer.class);
        q.setParameter("idQualificationLevel", idQualificationLevel);
        q.setParameter("idSector", idSector);
        List<JobOffer> res = q.getResultList();

        logger.log(Level.INFO, "get successful");
        return res.isEmpty() ? Optional.empty() : Optional.of(res);
    }

    @Transactional(readOnly = true)
    public List<JobOffer> findAll(String sort, String order) {
        String r = "SELECT j FROM JobOffer j ORDER BY j." + sort;
        if (order.equals("asc")) {
            r += " ASC";
        }
        else {
            r += " DESC";
        }
        TypedQuery<JobOffer> q = entityManager.createQuery(r, JobOffer.class);
        return q.getResultList();
    }
}

