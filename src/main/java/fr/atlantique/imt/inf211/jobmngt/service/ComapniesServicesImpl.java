package fr.atlantique.imt.inf211.jobmngt.service;

import fr.atlantique.imt.inf211.jobmngt.dao.AppUserDao;
import fr.atlantique.imt.inf211.jobmngt.dao.CompanyDao;
import fr.atlantique.imt.inf211.jobmngt.entity.AppUser;
import fr.atlantique.imt.inf211.jobmngt.entity.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ComapniesServicesImpl implements CompaniesServices {

    @Autowired
    private CompanyDao companyDao;
    @Autowired
    private AppUserDao appUserDao;

    public List<Company> listOfCompanies() {
        return companyDao.findAll("name", "ASC");
    }

    public void createCompany(Company company) {
        AppUser appUser = company.getAppuser();
        appUser.setUsertype("company");
        appUserDao.persist(appUser);
        company.setAppuser(appUser);
        companyDao.persist(company);
    }

    public Company getCompanyById(int id) {
        return companyDao.findById(id);
    }
}
