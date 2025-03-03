package fr.atlantique.imt.inf211.jobmngt.dao;
// Generated 3 mars 2025, 15:44:52 by Hibernate Tools 5.6.15.Final


import fr.atlantique.imt.inf211.jobmngt.entity.Sector;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Home object for domain model class Sector.
 *
 * @author Hibernate Tools
 * @see .Sector
 */
@Repository
public class SectorDao {

    private static final Logger logger = Logger.getLogger(SectorDao.class.getName());

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void persist(Sector transientInstance) {
        logger.log(Level.INFO, "persisting Sector instance");
        try {
            entityManager.persist(transientInstance);
            logger.log(Level.INFO, "persist successful");
        } catch (RuntimeException re) {
            logger.log(Level.SEVERE, "persist failed", re);
            throw re;
        }
    }

    @Transactional
    public void remove(Sector persistentInstance) {
        logger.log(Level.INFO, "removing Sector instance");
        try {
            entityManager.remove(persistentInstance);
            logger.log(Level.INFO, "remove successful");
        } catch (RuntimeException re) {
            logger.log(Level.SEVERE, "remove failed", re);
            throw re;
        }
    }

    @Transactional
    public Sector merge(Sector detachedInstance) {
        logger.log(Level.INFO, "merging Sector instance");
        try {
            Sector result = entityManager.merge(detachedInstance);
            logger.log(Level.INFO, "merge successful");
            return result;
        } catch (RuntimeException re) {
            logger.log(Level.SEVERE, "merge failed", re);
            throw re;
        }
    }

    @Transactional(readOnly = true)
    public Sector findById(int id) {
        logger.log(Level.INFO, "getting Sector instance with id: " + id);
        try {
            Sector instance = entityManager.find(Sector.class, id);
            logger.log(Level.INFO, "get successful");
            return instance;
        } catch (RuntimeException re) {
            logger.log(Level.SEVERE, "get failed", re);
            throw re;
        }
    }

    @Transactional(readOnly = true)
    public Long count() {
        String r = "select count(sector) from Sector sector";
        TypedQuery<Long> q = entityManager.createQuery(r, Long.class);
        return q.getSingleResult();
    }

    @Transactional(readOnly = true)
    public List<Sector> findAll(String sort, String order) {
        String r = "SELECT sector FROM Sector sector ORDER BY sector." + sort;
        if (order.equals("asc")) {
            r += " ASC";
        }
        else {
            r += " DESC";
        }
        TypedQuery<Sector> q = entityManager.createQuery(r, Sector.class);
        return q.getResultList();
    }
}

