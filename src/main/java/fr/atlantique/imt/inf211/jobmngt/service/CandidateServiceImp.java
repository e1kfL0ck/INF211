package fr.atlantique.imt.inf211.jobmngt.service;

import fr.atlantique.imt.inf211.jobmngt.dao.AppUserDao;
import fr.atlantique.imt.inf211.jobmngt.dao.CandidateDao;
import fr.atlantique.imt.inf211.jobmngt.entity.AppUser;
import fr.atlantique.imt.inf211.jobmngt.entity.Candidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CandidateServiceImp implements CandidateService {

    @Autowired
    CandidateDao candidateDao;

    @Autowired
    AppUserDao appUserDao;

    @Override
    public Candidate createCandidate(String mail, String password, String city, String lastname, String firstname) {
        AppUser appUser = new AppUser();
        appUser.setMail(mail);
        appUser.setPassword(password);
        appUser.setCity(city);
        appUser.setUsertype("candidate");

        appUserDao.persist(appUser);

        Candidate newCandidate = new Candidate();
        newCandidate.setAppuser(appUser);
        newCandidate.setLastname(lastname);
        newCandidate.setFirstname(firstname);

        candidateDao.persist(newCandidate);
        return newCandidate;
    }

    @Override
    public void removeCandidate(int id) {
        Candidate candidate = candidateDao.findById(id);
        if (candidate != null) {
            candidateDao.remove(candidate);
        }
    }

    @Override
    public Candidate updateCandidate(Candidate candidate) {
        Candidate persistedCandidate = candidateDao.findById(candidate.getId());
        AppUser persistedAppUser = appUserDao.findById(persistedCandidate.getAppuser().getId());

        persistedAppUser.setMail(candidate.getAppuser().getMail());
        persistedAppUser.setPassword(candidate.getAppuser().getPassword());
        persistedAppUser.setCity(candidate.getAppuser().getCity());

        appUserDao.merge(persistedAppUser);
        persistedCandidate.setAppuser(persistedAppUser);

        persistedCandidate.setLastname(candidate.getLastname());
        persistedCandidate.setFirstname(candidate.getFirstname());

        return candidateDao.merge(persistedCandidate);
    }

    @Override
    public Candidate getCandidate(int id) {
        return candidateDao.findById(id);
    }

    @Override
    public List<Candidate> listOfCandidates() {
        List<Candidate> list = candidateDao.findAll("firstname", "ASC");
        return list;
    }
}
