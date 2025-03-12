package fr.atlantique.imt.inf211.jobmngt.service;

import fr.atlantique.imt.inf211.jobmngt.dao.AppUserDao;
import fr.atlantique.imt.inf211.jobmngt.dao.CompanyDao;
import fr.atlantique.imt.inf211.jobmngt.entity.AppUser;
import fr.atlantique.imt.inf211.jobmngt.entity.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class CompaniesServiceImpl implements CompaniesService {

    @Autowired
    private CompanyDao companyDao;
    @Autowired
    private AppUserDao appUserDao;

    @Transactional
    public List<Company> listOfCompanies() {
        return companyDao.findAll("name", "ASC");
    }

    @Transactional
    public void createCompany(Company company) {
        AppUser appUser = company.getAppuser();
        appUser.setUsertype("company");
        company.setAppuser(appUser);
        appUser.setCompany(company);//TODO: check le code de max pour avoir le lien appuser-company
        appUserDao.persist(appUser);
        companyDao.persist(company);
    }

    @Transactional
    public Company getCompanyById(int id) {
        return companyDao.findById(id);
    }

    @Transactional
    public void updateCompany(Company company) {
        Company persistedCompany = companyDao.findById(company.getId());
        AppUser persistedAppUser = appUserDao.findById(persistedCompany.getAppuser().getId());

        persistedAppUser.setMail(company.getAppuser().getMail());
        persistedAppUser.setPassword(company.getAppuser().getPassword());
        persistedAppUser.setCity(company.getAppuser().getCity());

        appUserDao.merge(persistedAppUser);
        persistedCompany.setAppuser(persistedAppUser);

        persistedCompany.setName(company.getName());
        persistedCompany.setDescription(company.getDescription());

        companyDao.merge(persistedCompany);
    }

    @Transactional
    public void deleteCompany(int id) {
        Company company = companyDao.findById(id);
        if (company != null) {
            companyDao.remove(company);
        }
    }

}
