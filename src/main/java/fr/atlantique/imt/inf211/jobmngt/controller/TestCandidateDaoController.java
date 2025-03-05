package fr.atlantique.imt.inf211.jobmngt.controller;

import fr.atlantique.imt.inf211.jobmngt.dao.AppUserDao;
import fr.atlantique.imt.inf211.jobmngt.dao.CandidateDao;
import fr.atlantique.imt.inf211.jobmngt.entity.AppUser;
import fr.atlantique.imt.inf211.jobmngt.entity.Candidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/candidates")
public class TestCandidateDaoController {
    @Autowired
    private CandidateDao candidateDao;
    @Autowired
    private AppUserDao appUserDao;

    /*
     * Get all candidates
     * c
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Candidate> all() {
        List<Candidate> list = candidateDao.findAll("firstname", "ASC");
        return list;
    }

    /*
     * Create a new candidate
     * ("mail":"fou@imt.fr",
     * "password":"1234",
     * "city": "Toulouse"
     * "first": "toma",
     * "lastname": "Troux")
     *
     * curl -X GET "http://localhost:8080/api/v1/candidates/create?mail=fou@imt.fr&password=1234&city=Toulouse&lastname=Troux&firstname=toma"
     */
    @RequestMapping(value = "/create", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Candidate newCandidate(@RequestParam String mail, @RequestParam String password, @RequestParam String city, @RequestParam String lastname, @RequestParam String firstname) {

        AppUser appUser = new AppUser();
        appUser.setMail(mail);
        appUser.setPassword(password);
        appUser.setCity(city);

        appUserDao.persist(appUser);

        Candidate newCandidate = new Candidate();
        newCandidate.setAppuser(appUser);
        newCandidate.setLastname(lastname);
        newCandidate.setFirstname(firstname);

        candidateDao.persist(newCandidate);
        return newCandidate;
    }

    /*
     * Get information of a candidate by id
     * curl -X GET localhost:8080/api/v1/candidates/7
     * */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Candidate getCandidateById(@PathVariable int id) {
        return candidateDao.findById(id);
    }

    /*
     * Modify information of a candidate
     * ("mail":"newmailtest@imt.fr",
     * "password":"newpassword",
     * "city": "Brest",
     * "firstname": "Thomas",
     * "lastname": "Roux")
     *
     * curl -X GET "http://localhost:8080/api/v1/candidates/{id}/update?mail=newmailtest@imt.fr&password=newpassword&city=Brest&firstname=Thomas&lastname=Roux"
     */
    @RequestMapping(value = "/{id}/update", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Candidate updateCandidate(@PathVariable int id, @RequestParam String mail, @RequestParam String password, @RequestParam String city, @RequestParam String firstname, @RequestParam String lastname) {
        Candidate candidate = candidateDao.findById(id);
        if (candidate != null) {
            AppUser appUser = candidate.getAppuser();
            appUser.setMail(mail);
            appUser.setPassword(password);
            appUser.setCity(city);
            appUserDao.merge(appUser);

            candidate.setFirstname(firstname);
            candidate.setLastname(lastname);
            return candidateDao.merge(candidate);
        }
        return null;
    }

    /*
     * Delete a candidate
     * curl -X DELETE -V "http://localhost:8080/api/v1/candidates/3"
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteCandidate(@PathVariable int id) {
        Candidate candidate = candidateDao.findById(id);
        if (candidate != null) {
            candidateDao.remove(candidate);
        }
    }
}
