package fr.atlantique.imt.inf211.jobmngt.dao;
// Generated 3 mars 2025, 15:44:52 by Hibernate Tools 5.6.15.Final


import fr.atlantique.imt.inf211.jobmngt.entity.*;
import jakarta.persistence.TypedQuery;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 * Home object for domain model class Company.
 * @see .Company
 * @author Hibernate Tools
 */
@Repository
public class CompanyDao {

    private static final Logger logger = Logger.getLogger(CompanyDao.class.getName());

    @PersistenceContext private EntityManager entityManager;

    @Transactional
    public void persist(Company transientInstance) {
        logger.log(Level.INFO, "persisting Company instance");
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
    public void remove(Company persistentInstance) {
        logger.log(Level.INFO, "removing Company instance");
        try {
            entityManager.remove(persistentInstance.getAppuser());
            logger.log(Level.INFO, "remove successful");
        }
        catch (RuntimeException re) {
            logger.log(Level.SEVERE, "remove failed", re);
            throw re;
        }
    }

    @Transactional
    public Company merge(Company detachedInstance) {
        logger.log(Level.INFO, "merging Company instance");
        try {
            Company result = entityManager.merge(detachedInstance);
            logger.log(Level.INFO, "merge successful");
            return result;
        }
        catch (RuntimeException re) {
            logger.log(Level.SEVERE, "merge failed", re);
            throw re;
        }
    }

    @Transactional(readOnly = true)
    public Company findById( int id) {
        logger.log(Level.INFO, "getting Company instance with id: " + id);
        try {
            Company instance = entityManager.find(Company.class, id);
            logger.log(Level.INFO, "get successful");
            return instance;
        }
        catch (RuntimeException re) {
            logger.log(Level.SEVERE, "get failed", re);
            throw re;
        }
    }

    @Transactional(readOnly = true)
    public List<Company> findAll(String sort, String order) {
        String r = "SELECT c FROM Company c ORDER BY c." + sort;
        if (order.equals("asc")) {
            r += " ASC";
        }
        else {
            r += " DESC";
        }
        TypedQuery<Company> q = entityManager.createQuery(r, Company.class);
        return q.getResultList();
    }
}

