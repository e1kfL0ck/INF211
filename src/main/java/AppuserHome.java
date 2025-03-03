// default package
// Generated 3 mars 2025, 15:44:52 by Hibernate Tools 5.6.15.Final


import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Home object for domain model class Appuser.
 * @see .Appuser
 * @author Hibernate Tools
 */
@Stateless
public class AppuserHome {

    private static final Logger logger = Logger.getLogger(AppuserHome.class.getName());

    @PersistenceContext private EntityManager entityManager;
    
    public void persist(Appuser transientInstance) {
        logger.log(Level.INFO, "persisting Appuser instance");
        try {
            entityManager.persist(transientInstance);
            logger.log(Level.INFO, "persist successful");
        }
        catch (RuntimeException re) {
            logger.log(Level.SEVERE, "persist failed", re);
            throw re;
        }
    }
    
    public void remove(Appuser persistentInstance) {
        logger.log(Level.INFO, "removing Appuser instance");
        try {
            entityManager.remove(persistentInstance);
            logger.log(Level.INFO, "remove successful");
        }
        catch (RuntimeException re) {
            logger.log(Level.SEVERE, "remove failed", re);
            throw re;
        }
    }
    
    public Appuser merge(Appuser detachedInstance) {
        logger.log(Level.INFO, "merging Appuser instance");
        try {
            Appuser result = entityManager.merge(detachedInstance);
            logger.log(Level.INFO, "merge successful");
            return result;
        }
        catch (RuntimeException re) {
            logger.log(Level.SEVERE, "merge failed", re);
            throw re;
        }
    }
    
    public Appuser findById( int id) {
        logger.log(Level.INFO, "getting Appuser instance with id: " + id);
        try {
            Appuser instance = entityManager.find(Appuser.class, id);
            logger.log(Level.INFO, "get successful");
            return instance;
        }
        catch (RuntimeException re) {
            logger.log(Level.SEVERE, "get failed", re);
            throw re;
        }
    }
}

