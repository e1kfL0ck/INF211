package fr.atlantique.imt.inf211.jobmngt.controllertest;

import fr.atlantique.imt.inf211.jobmngt.dao.CompanyDao;
import fr.atlantique.imt.inf211.jobmngt.dao.JobOfferDao;
import fr.atlantique.imt.inf211.jobmngt.dao.QualificationLevelDao;
import fr.atlantique.imt.inf211.jobmngt.dao.SectorDao;
import fr.atlantique.imt.inf211.jobmngt.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/api/v1/joboffer")
public class TestJobOfferDaoController {

    @Autowired
    private JobOfferDao jobOfferDao;
    @Autowired
    private CompanyDao companyDao;
    @Autowired
    private QualificationLevelDao qualificationLevelDao;
    @Autowired
    private SectorDao sectorDao;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<JobOffer> all() {
        List<JobOffer> list = jobOfferDao.findAll("id", "ASC");
        return list;
    }

    //curl "localhost:8080/api/v1/joboffer/create?companyId=1&title=testeur&description=Description%20de%20l%27offre&qualificationlevel=1&sectorIds=1&sectorIds=2"
    @RequestMapping(value = "/create", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JobOffer newJobOffer(@RequestParam int companyId, @RequestParam String title,
                                @RequestParam String description, @RequestParam int qualificationlevel,
                                @RequestParam List<Integer> sectorIds) {
        Company company = companyDao.findById(companyId);
        QualificationLevel qualificationLevel = qualificationLevelDao.findById(qualificationlevel);

        Set<Sector> sectorsSet = new HashSet<>();
        for(Integer sectorId : sectorIds){
            Sector sector = sectorDao.findById(sectorId);
            sectorsSet.add(sector);
        }

        JobOffer aNewJobOffer = new JobOffer();
        aNewJobOffer.setCompany(company);
        aNewJobOffer.setTitle(title);
        aNewJobOffer.setDescription(description);
        aNewJobOffer.setQualificationlevel(qualificationLevel);
        aNewJobOffer.setSectors(sectorsSet);
        aNewJobOffer.setPublicationdate(new java.util.Date());
        jobOfferDao.persist(aNewJobOffer);
        return aNewJobOffer;
    }

    //récupérer les informations d’une offre à partir d’un secteur et d’un niveau de qualification passés en paramètre de la requête HTTP
    //curl "localhost:8080/api/v1/joboffer/sector?sectorId=1&qualificationLevelId=1"
    /*@RequestMapping(value = "/sector", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JobOffer getJobOfferBySectorAndQualificationLevel(@RequestParam int sectorId, @RequestParam int qualificationLevelId) {
        return jobOfferDao.getJobOffers(qualificationLevelId, sectorId).orElse(null);
    }*/

    //modifier les informations d’une offre dont les informations sont passées en paramètre de la requête HTTP
    //curl "localhost:8080/api/v1/joboffer/update?id=4&title=Testeur%20de%20gode&description=Description%20de%20l%27offre"
    @RequestMapping(value = "/{id}/update", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JobOffer replaceJobOffer(@RequestParam int id, @RequestParam String title, @RequestParam String description, @RequestParam int qualificationLevel, @RequestParam List<Integer> sectorIds) {
        JobOffer jobOffer = jobOfferDao.findById(id);

        if (jobOffer != null) {
            jobOffer.setTitle(title);
            jobOffer.setDescription(description);
            jobOffer.setPublicationdate(new Date());
            jobOffer.setQualificationlevel(qualificationLevelDao.findById(qualificationLevel));

            Set<Sector> sectors = new HashSet<>();
            for (int sectorId : sectorIds) {
                Sector sector = sectorDao.findById(sectorId);
                if (sector != null) {
                    sectors.add(sector);
                }
            }
            jobOffer.setSectors(sectors);

            jobOfferDao.merge(jobOffer);
        }
        return jobOffer;
    }

    /*
     * Delete an application
     * curl -X DELETE -v "http://localhost:8080/api/v1/joboffer/1"
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteJobOffer(@PathVariable int id) {
        JobOffer jobOffer = jobOfferDao.findById(id);
        if (jobOffer != null) {
            jobOfferDao.remove(jobOffer);
        }
    }
}
