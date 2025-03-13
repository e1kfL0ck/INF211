package fr.atlantique.imt.inf211.jobmngt.service;

import fr.atlantique.imt.inf211.jobmngt.dao.ApplicationDao;
import fr.atlantique.imt.inf211.jobmngt.dao.CandidateDao;
import fr.atlantique.imt.inf211.jobmngt.dao.QualificationLevelDao;
import fr.atlantique.imt.inf211.jobmngt.dao.SectorDao;
import fr.atlantique.imt.inf211.jobmngt.entity.Application;
import fr.atlantique.imt.inf211.jobmngt.entity.Candidate;
import fr.atlantique.imt.inf211.jobmngt.entity.Sector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

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
}
