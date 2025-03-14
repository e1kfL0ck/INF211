package fr.atlantique.imt.inf211.jobmngt.service;

import fr.atlantique.imt.inf211.jobmngt.entity.AppUser;
import fr.atlantique.imt.inf211.jobmngt.entity.Application;
import fr.atlantique.imt.inf211.jobmngt.entity.JobOffer;

import java.util.List;
import java.util.Optional;

public interface JobOfferService {
    public List<JobOffer> listOfJobOffers();

    public JobOffer createJobOffer(JobOffer jobOffer, AppUser appUser, List<Integer> sectorIds);

    public JobOffer getJobOfferById(int id);

    public List<Application> getApplicationsByJobOffer(int id);

    public Optional<List<JobOffer>> getBySectorAndQualification(int sectorId, int qualificationLevel);

    public void deleteJobOffer(int id);

    public void updateJobOffer(JobOffer jobOffer, List<Integer> sectorIds);

    public void sendMessageToCandidate(int id, JobOffer jobOffer);

}
