package fr.atlantique.imt.inf211.jobmngt.service;

import fr.atlantique.imt.inf211.jobmngt.entity.Application;
import fr.atlantique.imt.inf211.jobmngt.entity.JobOffer;
import fr.atlantique.imt.inf211.jobmngt.entity.Sector;

import java.util.List;
import java.util.Optional;

public interface ApplicationService {

    public List<Application> listOfApplications();

    public Application createApplication(int candidateId, int qualificationLevel, String cv, List<Integer> sectorIds);

    public List<Application> getApplication(int id);

    public Optional<List<Application>> getBySectorAndQualification(int sectorId, int qualificationLevel);

    public Application updateApplication(Application application);

    public Application getApplicationById(int id);

    public void deleteApplication(int id);

    public List<JobOffer> getSectorByApplicationId(int id);
}
