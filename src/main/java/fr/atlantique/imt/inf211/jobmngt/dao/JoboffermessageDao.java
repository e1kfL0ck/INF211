package fr.atlantique.imt.inf211.jobmngt.dao;
// Generated 3 mars 2025, 15:44:52 by Hibernate Tools 5.6.15.Final


import fr.atlantique.imt.inf211.jobmngt.entity.*;
 import org.springframework.transaction.annotation.Transactional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 * Home object for domain model class Joboffermessage.
 * @see .Joboffermessage
 * @author Hibernate Tools
 */
@Repository
public class JoboffermessageDao {

    private static final Logger logger = Logger.getLogger(JoboffermessageDao.class.getName());

    @PersistenceContext private EntityManager entityManager;
    
    public void persist(Joboffermessage transientInstance) {
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
    
    public void remove(Joboffermessage persistentInstance) {
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
    
    public Joboffermessage merge(Joboffermessage detachedInstance) {
        logger.log(Level.INFO, "merging Joboffermessage instance");
        try {
            Joboffermessage result = entityManager.merge(detachedInstance);
            logger.log(Level.INFO, "merge successful");
            return result;
        }
        catch (RuntimeException re) {
            logger.log(Level.SEVERE, "merge failed", re);
            throw re;
        }
    }
    
    public Joboffermessage findById( int id) {
        logger.log(Level.INFO, "getting Joboffermessage instance with id: " + id);
        try {
            Joboffermessage instance = entityManager.find(Joboffermessage.class, id);
            logger.log(Level.INFO, "get successful");
            return instance;
        }
        catch (RuntimeException re) {
            logger.log(Level.SEVERE, "get failed", re);
            throw re;
        }
    }
}

