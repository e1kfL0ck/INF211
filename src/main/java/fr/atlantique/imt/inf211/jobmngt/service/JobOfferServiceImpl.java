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

    public List<JobOffer> listOfJobOffers() {
        return jobOfferDao.findAll("title", "ASC");
    }

    public JobOffer getJobOfferById(int id) {
        return jobOfferDao.findById(id);
    }

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
    public List<Application> getApplicationsByJobOffer(int id) {
        JobOffer jobOffer = jobOfferDao.findById(id);
        Integer qualificationLevelId = jobOffer.getQualificationlevel().getId();
        Set<Sector> sectors = jobOffer.getSectors();
        ArrayList<Application> listApplication = new ArrayList<>();

        for (Sector sector : sectors) {
            Optional<List<Application>> optionalApplications = applicationDao.getApplications(qualificationLevelId, sector.getId());
            optionalApplications.ifPresent(listApplication::addAll);
        }

        return listApplication;
    }

    @Override
    public Optional<List<JobOffer>> getBySectorAndQualification(int sectorId, int qualificationLevel) {
        return jobOfferDao.getJobOffers(qualificationLevelDao.findById(qualificationLevel).getId(), sectorId);
    }

    public void deleteJobOffer(int id) {
        JobOffer jobOffer = jobOfferDao.findById(id);
        if (jobOffer != null) {
            jobOfferDao.remove(jobOffer);
        }
    }

    public void updateJobOffer(JobOffer jobOffer, List<Integer> sectorIds) {
        JobOffer persistedJobOffer = jobOfferDao.findById(jobOffer.getId());
        persistedJobOffer.setTitle(jobOffer.getTitle());
        persistedJobOffer.setDescription(jobOffer.getDescription());
        persistedJobOffer.setQualificationlevel(qualificationLevelDao.findById(jobOffer.getQualificationlevel().getId()));

        Set<Sector> sectors = new HashSet<>();
        for (int sectorId : sectorIds) {
            Sector sector = sectorDao.findById(sectorId);
            if (sector != null) {
                sectors.add(sector);
            }
        }
        persistedJobOffer.setSectors(sectors);

        jobOfferDao.merge(persistedJobOffer);
    }
}
