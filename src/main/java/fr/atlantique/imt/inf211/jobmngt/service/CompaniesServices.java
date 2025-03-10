package fr.atlantique.imt.inf211.jobmngt.service;

import fr.atlantique.imt.inf211.jobmngt.entity.Company;

import java.util.List;

public interface CompaniesServices {

    public List<Company> listOfCompanies();

    public void createCompany(Company company);

    public Company getCompanyById(int id);
}
