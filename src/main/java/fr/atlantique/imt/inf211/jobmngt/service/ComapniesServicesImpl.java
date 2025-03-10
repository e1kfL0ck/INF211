package fr.atlantique.imt.inf211.jobmngt.service;

import fr.atlantique.imt.inf211.jobmngt.dao.CompanyDao;
import fr.atlantique.imt.inf211.jobmngt.entity.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ComapniesServicesImpl implements CompaniesServices {

    @Autowired
    private CompanyDao companyDao;

    public List<Company> listOfCompanies() {
        return companyDao.findAll("name", "ASC");
    }
}
