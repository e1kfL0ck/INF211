package fr.atlantique.imt.inf211.jobmngt.service;

import fr.atlantique.imt.inf211.jobmngt.dao.*;
import fr.atlantique.imt.inf211.jobmngt.entity.AppUser;
import fr.atlantique.imt.inf211.jobmngt.entity.Application;
import fr.atlantique.imt.inf211.jobmngt.entity.JobOffer;
import fr.atlantique.imt.inf211.jobmngt.entity.Sector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
public class JobOfferServiceImpl implements JobOfferService {

    @Autowired
    private JobOfferDao jobOfferDao;
    @Autowired
    private SectorDao sectorDao;
    @Autowired
    private QualificationLevelDao qualificationLevelDao;
    @Autowired
    private CompanyDao companyDao;
    @Autowired
    private ApplicationDao applicationDao;

    @Transactional
    public List<JobOffer> listOfJobOffers() {
        return jobOfferDao.findAll("title", "ASC");
    }

    @Transactional
    public JobOffer getJobOfferById(int id) {
        return jobOfferDao.findById(id);
    }

    @Transactional
    public void createJobOffer(JobOffer jobOffer, AppUser appUser, List<Integer> sectorIds) {
        JobOffer newJobOffer = new JobOffer();
        newJobOffer.setCompany(companyDao.findById(appUser.getCompany().getId()));
        newJobOffer.setQualificationlevel(qualificationLevelDao.findById(jobOffer.getQualificationlevel().getId()));

        Set<Sector> sectors = new HashSet<>();
        for (int sectorId : sectorIds) {
            Sector sector = sectorDao.findById(sectorId);
            if (sector != null) {
                sectors.add(sector);
            }
        }
        newJobOffer.setSectors(sectors);
        newJobOffer.setTitle(jobOffer.getTitle());
        newJobOffer.setDescription(jobOffer.getDescription());
        newJobOffer.setPublicationdate(new java.util.Date());
        jobOfferDao.persist(newJobOffer);
    }

    @Transactional
    public List<Application> getJobOfferByApplication(int id) {
        JobOffer jobOffer = jobOfferDao.findById(id);
        Integer qualificationLevelId = jobOffer.getQualificationlevel().getId();
        Set<Sector> sectors = jobOffer.getSectors();
        ArrayList<Application> listApplication = new ArrayList<>();

        Optional<Application> app = applicationDao.getApplications(4, 12);

        for(Sector sector : sectors) {
            Optional<Application> optionalApplication = applicationDao.getApplications(qualificationLevelId, sector.getId());
            optionalApplication.ifPresent(listApplication::add);
        }

        return listApplication;
    }
}
