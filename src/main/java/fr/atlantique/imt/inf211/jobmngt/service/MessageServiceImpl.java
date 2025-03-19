package fr.atlantique.imt.inf211.jobmngt.service;

import fr.atlantique.imt.inf211.jobmngt.dao.ApplicationMessageDao;
import fr.atlantique.imt.inf211.jobmngt.dao.JobOfferMessageDao;
import fr.atlantique.imt.inf211.jobmngt.entity.AppUser;
import fr.atlantique.imt.inf211.jobmngt.entity.ApplicationMessage;
import fr.atlantique.imt.inf211.jobmngt.entity.JobOfferMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MessageServiceImpl implements MessageService {

    @Autowired
    private JobOfferMessageDao jobOfferMessageDao;
    @Autowired
    private ApplicationMessageDao applicationMessageDao;

    @Override
    public List<JobOfferMessage> listOfMessagesJobOffer(AppUser appUser) {
        if (appUser.getUsertype().equals("candidate")) {
            return jobOfferMessageDao.findByCandidateId("date", "DESC", appUser.getCandidate().getId());
        } else {
            return jobOfferMessageDao.findByCompanyId("date", "DESC", appUser.getCompany().getId());
        }
    }

    @Override
    public List<ApplicationMessage> listOfMessagesApplication(AppUser appUser) {
        return null;
    }
}
