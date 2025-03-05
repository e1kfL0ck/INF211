package fr.atlantique.imt.inf211.jobmngt.controller;

import fr.atlantique.imt.inf211.jobmngt.dao.ApplicationDao;
import fr.atlantique.imt.inf211.jobmngt.dao.CandidateDao;
import fr.atlantique.imt.inf211.jobmngt.dao.QualificationLevelDao;
import fr.atlantique.imt.inf211.jobmngt.dao.SectorDao;
import fr.atlantique.imt.inf211.jobmngt.entity.Application;
import fr.atlantique.imt.inf211.jobmngt.entity.Candidate;
import fr.atlantique.imt.inf211.jobmngt.entity.Sector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value = "/api/v1/applications")
public class TestApplicationDaoController {
    @Autowired
    private ApplicationDao applicationDao;
    @Autowired
    private CandidateDao candidateDao;
    @Autowired
    private SectorDao sectorDao;
    @Autowired
    private QualificationLevelDao qualificationLevelDao;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Application> all() {
        return applicationDao.findAll();
    }

    /*
     * Create a new application
     * ("candidateId": 1,
     * "qualificationLevel": "PhD",
     * "cv": "myCv",
     * "sectorIds": [1, 2, 3])
     * curl -X GET "http://localhost:8080/api/v1/applications/create?candidateId=1&qualificationLevel=5&cv=myCv&sectorIds=1&sectorIds=2&sectorIds=3"
     */
    @RequestMapping(value = "/create", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Application newApplication(@RequestParam int candidateId, @RequestParam int qualificationLevel, @RequestParam String cv, @RequestParam List<Integer> sectorIds) {
        Candidate candidate = candidateDao.findById(candidateId);
        if (candidate != null) {
            Application newApplication = new Application();
            newApplication.setCandidate(candidate);

            newApplication.setQualificationlevel(qualificationLevelDao.findById(qualificationLevel));

            newApplication.setCv(cv);
            Date applicationDate = new Date();
            newApplication.setApplicationdate(applicationDate);

            Set<Sector> sectors = new HashSet<>();
            for (int sectorId : sectorIds) {
                Sector sector = sectorDao.findById(sectorId);
                if (sector != null) {
                    sectors.add(sector);
                }
            }
            newApplication.setSectors(sectors);

            applicationDao.persist(newApplication);
            return newApplication;
        }
        return null;
    }

    // récupérer les informations d’une candidature à partir d’un secteur et d’un niveau de qualification passés en paramètre de la requête HTTP

    /*
     * Get information of an application by id
     * curl -X GET "http://localhost:8080/api/v1/applications/search?sectorId=1&qualificationLevel=5"
     */
    @RequestMapping(value = "/getBySectorAndQualification", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<Application> getBySectorAndQualification(@RequestParam int sectorId, @RequestParam int qualificationLevel) {
        return applicationDao.getApplications(qualificationLevelDao.findById(qualificationLevel).getId(), sectorId);
    }


    /*
     * Modify information of an application
     * ("id": 1,
     * "qualificationLevel": "Licence",
     * "cv": "myCvModif",
     * "sectorIds": [2, 3])
     * curl -X GET "http://localhost:8080/api/v1/applications/1/update?qualificationLevel=3&cv=myCvModif&sectorIds=2&sectorIds=3"
     */
    @RequestMapping(value = "/{id}/update", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Application updateApplication(@PathVariable int id, @RequestParam int qualificationLevel, @RequestParam String cv, @RequestParam List<Integer> sectorIds) {
        Application application = applicationDao.findById(id);
        if (application != null) {

            application.setQualificationlevel(qualificationLevelDao.findById(qualificationLevel));
            application.setCv(cv);
            Date applicationDate = new Date();
            application.setApplicationdate(applicationDate);

            Set<Sector> sectors = new HashSet<>();
            for (int sectorId : sectorIds) {
                Sector sector = sectorDao.findById(sectorId);
                if (sector != null) {
                    sectors.add(sector);
                }
            }
            application.setSectors(sectors);

            return applicationDao.merge(application);
        }
        return null;
    }

    /*
     * Delete an application
     * curl -X DELETE -v "http://localhost:8080/api/v1/applications/1"
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteApplication(@PathVariable int id) {
        Application application = applicationDao.findById(id);
        if (application != null) {
            applicationDao.remove(application);
        }
    }
}
