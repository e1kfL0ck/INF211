package fr.atlantique.imt.inf211.jobmngt.service;

import fr.atlantique.imt.inf211.jobmngt.dao.*;
import fr.atlantique.imt.inf211.jobmngt.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class ApplicationServiceImp implements ApplicationService {

    @Autowired
    private ApplicationDao applicationDao;
    @Autowired
    private CandidateDao candidateDao;
    @Autowired
    private SectorDao sectorDao;
    @Autowired
    private QualificationLevelDao qualificationLevelDao;
    @Autowired
    private JobOfferService jobOfferService;
    @Autowired
    private ApplicationMessageDao applicationMessageDao;

    @Override
    public List<Application> listOfApplications() {
        return applicationDao.findAll();
    }

    @Override
    public Application createApplication(int candidateId, int qualificationLevel, String cv, List<Integer> sectorIds) {
        Candidate candidate = candidateDao.findById(candidateId);
        if (candidate != null) {
            Application newApplication = new Application();
            newApplication.setCandidate(candidate);

            newApplication.setQualificationlevel(qualificationLevelDao.findById(qualificationLevel));

            newApplication.setCv(cv);
            newApplication.setApplicationdate(new Date());

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

    @Override
    public List<Application> getApplication(int candidateId) {
        return applicationDao.getByCandidateId(candidateId);
    }

    @Override
    public Optional<List<Application>> getBySectorAndQualification(int sectorId, int qualificationLevel) {
        return applicationDao.getApplications(qualificationLevelDao.findById(qualificationLevel).getId(), sectorId);
    }

    @Override
    public Application updateApplication(Application application) {
        Application persistedApplication = applicationDao.findById(application.getId());

        persistedApplication.setQualificationlevel(application.getQualificationlevel());
        persistedApplication.setCv(application.getCv());
        persistedApplication.setApplicationdate(application.getApplicationdate());
        persistedApplication.setSectors(application.getSectors());

        return applicationDao.merge(persistedApplication);
    }

    @Override
    public Application getApplicationById(int id) {
        return applicationDao.findById(id);
    }

    @Override
    public void deleteApplication(int id) {
        Application application = applicationDao.findById(id);
        if (application != null) {
            applicationDao.remove(application);
        }
    }

    @Override
    public List<JobOffer> getJobOffersByApplicationId(int id) {
        Application application = applicationDao.findById(id);
        Integer qualificationLevelId = application.getQualificationlevel().getId();
        Set<Sector> sectors = application.getSectors();
        ArrayList<JobOffer> jobOffers = new ArrayList<>();

        for (Sector sector : sectors) {
            Optional<List<JobOffer>> sectorJobOffers = jobOfferService.getBySectorAndQualification(sector.getId(), qualificationLevelId);
            sectorJobOffers.ifPresent(jobOffers::addAll);
        }

        return jobOffers;
    }

    @Override
    public List<Company> sendMessageToCompany(int id, Application application) {
        List<JobOffer> jobOfferList = getJobOffersByApplicationId(id);
        List<Company> companyList = new ArrayList<>();

        for (JobOffer jobOffer : jobOfferList) {
            ApplicationMessage applicationMessage = new ApplicationMessage();
            applicationMessage.setJoboffer(jobOffer);
            applicationMessage.setApplication(applicationDao.findById(id)); // fonctionne ?
            applicationMessage.setMessage("New application:\n" + "Candidate Email: " + application.getCandidate().getAppuser().getMail() + "\n" + "Candidate Name: " + application.getCandidate().getFirstname() + " " + application.getCandidate().getLastname() + "\n" + "Application ID: " + application.getId() + "\n" + "Job Offer ID: " + jobOffer.getId() + "\n" + "Job Title: " + jobOffer.getTitle() + "\n" + "Job Description: " + jobOffer.getDescription() + "\n" + "Company: " + jobOffer.getCompany().getName() + "\n" + "Qualification Level: " + application.getQualificationlevel().getLabel() + "\n" + "Company Sectors: " + application.getSectors().stream().map(Sector::getLabel).collect(Collectors.joining(", ")) + "\n" + "Application Date: " + application.getApplicationdate() + "\n" + "Current Date: " + new java.util.Date().toString());
            applicationMessage.setDate(new Date());
            applicationMessageDao.persist(applicationMessage);
            companyList.add(jobOffer.getCompany());
        }

        return companyList;
    }
}
