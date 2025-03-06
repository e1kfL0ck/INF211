package fr.atlantique.imt.inf211.jobmngt.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import fr.atlantique.imt.inf211.jobmngt.dao.AppUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import fr.atlantique.imt.inf211.jobmngt.dao.CompanyDao;
import fr.atlantique.imt.inf211.jobmngt.entity.AppUser;
import fr.atlantique.imt.inf211.jobmngt.entity.Company;

@RestController
@RequestMapping(value = "/api/v1/companies")
public class TestCompanyDaoController {
    @Autowired
    private CompanyDao companyDao;
    @Autowired
    private AppUserDao appUserDao;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Company> all() {
        List<Company> list = companyDao.findAll("name", "ASC");
        return list;
    }

    /*
     * Create a company with no joboffers
     * ("name": "myFirstCompany",
     * "description": "Desc of my new company",
     * "mail":"mnc@imt.fr", "password":"2580", "city": "Brest")
     *
     * curl "localhost:8080/api/v1/companies/create?mail=mnc@imt.fr&password=2580&city=Brest&name=myFirstCompany&description=Desc%20of%20my%20new%20company"
     */
    @RequestMapping(value = "/create", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Company newCompany(@RequestParam String mail, @RequestParam String password, @RequestParam String city, @RequestParam String name, @RequestParam String description) {

        AppUser appUser = new AppUser();
        appUser.setMail(mail);
        appUser.setPassword(password);
        appUser.setCity(city);
        appUser.setUsertype("company");

        appUserDao.persist(appUser);

        Company aNewCompany = new Company();
        aNewCompany.setAppuser(appUser);
        aNewCompany.setName(name);
        aNewCompany.setDescription(description);

        companyDao.persist(aNewCompany);
        return aNewCompany;
    }

    // Get information of a company by id
    // curl localhost:8080/api/companies/1
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Company one(@PathVariable int id) {
        return companyDao.findById(id);
    }

    // Modify (basic) information about a company
    // ("denomination": "IMT Atlantique",
    // "description": "Une école d\'ingénieurs généraliste",
    // ""mail": "atlantique@imt.fr", "password": "5678", "city": "Brest")
    // curl "localhost:8080/api/v1/companies/1/update?mail=atlantique@imt.fr&password=5678&name=IMT%20Atlantique&description=Une%20ecole%20d'ingenieurs%20generaliste"
    // TODO : régler les pbs d encodage
    @RequestMapping(value = "/{id}/update", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Company replaceCompany(@PathVariable int id, @RequestParam String mail, @RequestParam String password, @RequestParam String name, @RequestParam String description) {
        Company company = companyDao.findById(id);
        if (company != null) {
            company.getAppuser().setMail(mail);
            company.getAppuser().setPassword(password);
            company.setName(name);
            company.setDescription(description);

            return companyDao.merge(company);
        }
        return null;
    }

    // Delete a company that doesn't have joboffers
    // curl -X DELETE localhost:8080/api/companies/7
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteCompany(@PathVariable int id) {
        Company company = companyDao.findById(id);
        appUserDao.remove(company.getAppuser());
        if (company != null) {
            companyDao.remove(company);
        }
    }
}
