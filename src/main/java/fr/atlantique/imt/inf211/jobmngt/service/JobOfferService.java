package fr.atlantique.imt.inf211.jobmngt.service;

import fr.atlantique.imt.inf211.jobmngt.entity.AppUser;
import fr.atlantique.imt.inf211.jobmngt.entity.JobOffer;
import fr.atlantique.imt.inf211.jobmngt.entity.Sector;

import java.util.List;
import java.util.Set;

public interface JobOfferService {
    public List<JobOffer> listOfJobOffers();

    public void createJobOffer(JobOffer jobOffer, AppUser appUser, List<Integer> sectorIds);

    public JobOffer getJobOfferByApplication(int id);
}
