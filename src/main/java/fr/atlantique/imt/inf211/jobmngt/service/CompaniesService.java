package fr.atlantique.imt.inf211.jobmngt.service;

import fr.atlantique.imt.inf211.jobmngt.entity.Company;

import java.util.List;

public interface CompaniesService {

    public List<Company> listOfCompanies();

    public void createCompany(Company company);

    public void updateCompany(Company company);

    public Company getCompanyById(int id);

    public void deleteCompany(int id);

}
