package fr.atlantique.imt.inf211.jobmngt.service;

import fr.atlantique.imt.inf211.jobmngt.dao.JobOfferMessageDao;
import fr.atlantique.imt.inf211.jobmngt.entity.JobOfferMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MessageServiceImpl implements MessageService {

    @Autowired
    private JobOfferMessageDao jobOfferMessageDao;

    @Override
    public List<JobOfferMessage> listOfMessages() {
        return jobOfferMessageDao.findAll("date", "DESC");
    }
}
