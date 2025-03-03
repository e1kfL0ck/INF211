// default package
// Generated 3 mars 2025, 15:44:52 by Hibernate Tools 5.6.15.Final


import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Home object for domain model class Candidate.
 * @see .Candidate
 * @author Hibernate Tools
 */
@Stateless
public class CandidateHome {

    private static final Logger logger = Logger.getLogger(CandidateHome.class.getName());

    @PersistenceContext private EntityManager entityManager;
    
    public void persist(Candidate transientInstance) {
        logger.log(Level.INFO, "persisting Candidate instance");
        try {
            entityManager.persist(transientInstance);
            logger.log(Level.INFO, "persist successful");
        }
        catch (RuntimeException re) {
            logger.log(Level.SEVERE, "persist failed", re);
            throw re;
        }
    }
    
    public void remove(Candidate persistentInstance) {
        logger.log(Level.INFO, "removing Candidate instance");
        try {
            entityManager.remove(persistentInstance);
            logger.log(Level.INFO, "remove successful");
        }
        catch (RuntimeException re) {
            logger.log(Level.SEVERE, "remove failed", re);
            throw re;
        }
    }
    
    public Candidate merge(Candidate detachedInstance) {
        logger.log(Level.INFO, "merging Candidate instance");
        try {
            Candidate result = entityManager.merge(detachedInstance);
            logger.log(Level.INFO, "merge successful");
            return result;
        }
        catch (RuntimeException re) {
            logger.log(Level.SEVERE, "merge failed", re);
            throw re;
        }
    }
    
    public Candidate findById( int id) {
        logger.log(Level.INFO, "getting Candidate instance with id: " + id);
        try {
            Candidate instance = entityManager.find(Candidate.class, id);
            logger.log(Level.INFO, "get successful");
            return instance;
        }
        catch (RuntimeException re) {
            logger.log(Level.SEVERE, "get failed", re);
            throw re;
        }
    }
}

