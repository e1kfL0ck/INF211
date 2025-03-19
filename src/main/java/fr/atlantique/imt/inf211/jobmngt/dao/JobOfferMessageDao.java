package fr.atlantique.imt.inf211.jobmngt.dao;
// Generated 3 mars 2025, 15:44:52 by Hibernate Tools 5.6.15.Final


import fr.atlantique.imt.inf211.jobmngt.entity.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

/**
 * Home object for domain model class Joboffermessage.
 * @see .Joboffermessage
 * @author Hibernate Tools
 */
@Repository
public class JobOfferMessageDao {

    private static final Logger logger = Logger.getLogger(JobOfferMessageDao.class.getName());

    @PersistenceContext private EntityManager entityManager;

    @Transactional
    public void persist(JobOfferMessage transientInstance) {
        logger.log(Level.INFO, "persisting Joboffermessage instance");
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
    public void remove(JobOfferMessage persistentInstance) {
        logger.log(Level.INFO, "removing Joboffermessage instance");
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
    public JobOfferMessage merge(JobOfferMessage detachedInstance) {
        logger.log(Level.INFO, "merging Joboffermessage instance");
        try {
            JobOfferMessage result = entityManager.merge(detachedInstance);
            logger.log(Level.INFO, "merge successful");
            return result;
        }
        catch (RuntimeException re) {
            logger.log(Level.SEVERE, "merge failed", re);
            throw re;
        }
    }

    @Transactional(readOnly = true)
    public JobOfferMessage findById(int id) {
        logger.log(Level.INFO, "getting Joboffermessage instance with id: " + id);
        try {
            JobOfferMessage instance = entityManager.find(JobOfferMessage.class, id);
            logger.log(Level.INFO, "get successful");
            return instance;
        }
        catch (RuntimeException re) {
            logger.log(Level.SEVERE, "get failed", re);
            throw re;
        }
    }

    @Transactional(readOnly = true)
    public List<JobOfferMessage> findAll(String sort, String order) {
        String r = "SELECT m FROM JobOfferMessage m ORDER BY m." + sort;
        if (order.equals("asc")) {
            r += " ASC";
        }
        else {
            r += " DESC";
        }
        TypedQuery<JobOfferMessage> q = entityManager.createQuery(r, JobOfferMessage.class);
        return q.getResultList();
    }

    @Transactional(readOnly = true)
    public List<JobOfferMessage> findByCandidateId(String sort, String order, int candidateId) {
        String r = "SELECT m FROM JobOfferMessage m JOIN m.application a JOIN a.candidate c WHERE c.id = :CandidateId ORDER BY m." + sort;
        if (order.equals("asc")) {
            r += " ASC";
        }
        else {
            r += " DESC";
        }
        TypedQuery<JobOfferMessage> q = entityManager.createQuery(r, JobOfferMessage.class);
        q.setParameter("CandidateId", candidateId);
        return q.getResultList();
    }

    @Transactional(readOnly = true)
    public List<JobOfferMessage> findByCompanyId(String sort, String order, int companyId){
        String r = "SELECT m FROM JobOfferMessage m JOIN m.joboffer j JOIN j.company c WHERE c.id = :CompanyId ORDER BY m." + sort;
        if (order.equals("asc")) {
            r += " ASC";
        }
        else {
            r += " DESC";
        }
        TypedQuery<JobOfferMessage> q = entityManager.createQuery(r, JobOfferMessage.class);
        q.setParameter("CompanyId", companyId);
        return q.getResultList();
    }
}

