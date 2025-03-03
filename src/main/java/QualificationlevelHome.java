// default package
// Generated 3 mars 2025, 15:44:52 by Hibernate Tools 5.6.15.Final


import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Home object for domain model class Qualificationlevel.
 * @see .Qualificationlevel
 * @author Hibernate Tools
 */
@Stateless
public class QualificationlevelHome {

    private static final Logger logger = Logger.getLogger(QualificationlevelHome.class.getName());

    @PersistenceContext private EntityManager entityManager;
    
    public void persist(Qualificationlevel transientInstance) {
        logger.log(Level.INFO, "persisting Qualificationlevel instance");
        try {
            entityManager.persist(transientInstance);
            logger.log(Level.INFO, "persist successful");
        }
        catch (RuntimeException re) {
            logger.log(Level.SEVERE, "persist failed", re);
            throw re;
        }
    }
    
    public void remove(Qualificationlevel persistentInstance) {
        logger.log(Level.INFO, "removing Qualificationlevel instance");
        try {
            entityManager.remove(persistentInstance);
            logger.log(Level.INFO, "remove successful");
        }
        catch (RuntimeException re) {
            logger.log(Level.SEVERE, "remove failed", re);
            throw re;
        }
    }
    
    public Qualificationlevel merge(Qualificationlevel detachedInstance) {
        logger.log(Level.INFO, "merging Qualificationlevel instance");
        try {
            Qualificationlevel result = entityManager.merge(detachedInstance);
            logger.log(Level.INFO, "merge successful");
            return result;
        }
        catch (RuntimeException re) {
            logger.log(Level.SEVERE, "merge failed", re);
            throw re;
        }
    }
    
    public Qualificationlevel findById( int id) {
        logger.log(Level.INFO, "getting Qualificationlevel instance with id: " + id);
        try {
            Qualificationlevel instance = entityManager.find(Qualificationlevel.class, id);
            logger.log(Level.INFO, "get successful");
            return instance;
        }
        catch (RuntimeException re) {
            logger.log(Level.SEVERE, "get failed", re);
            throw re;
        }
    }
}

