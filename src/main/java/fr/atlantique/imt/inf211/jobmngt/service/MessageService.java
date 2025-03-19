package fr.atlantique.imt.inf211.jobmngt.service;

import fr.atlantique.imt.inf211.jobmngt.entity.AppUser;
import fr.atlantique.imt.inf211.jobmngt.entity.JobOfferMessage;

import java.util.List;

public interface MessageService {

    public List<JobOfferMessage> listOfMessagesJobOffer(AppUser appUser);
}
