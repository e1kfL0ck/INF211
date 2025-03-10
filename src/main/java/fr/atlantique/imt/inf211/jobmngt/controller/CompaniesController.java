package fr.atlantique.imt.inf211.jobmngt.controller;

import fr.atlantique.imt.inf211.jobmngt.dao.CompanyDao;
import fr.atlantique.imt.inf211.jobmngt.entity.AppUser;
import fr.atlantique.imt.inf211.jobmngt.entity.Application;
import fr.atlantique.imt.inf211.jobmngt.entity.Company;
import fr.atlantique.imt.inf211.jobmngt.service.CompaniesServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping(value = "/companies")
public class CompaniesController {

    @Autowired
    private CompaniesServices companiesServices;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView getAllJobOffers() {
        ModelAndView mav = new ModelAndView("company/companyList.html");
        mav.addObject("companieslist", companiesServices.listOfCompanies());
        return mav;
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView createCompany() {
        ModelAndView mav = new ModelAndView("company/companyForm.html");
        AppUser appUser = new AppUser();
//        appUser.setCity("VILLE DE TES MORTS");
        Company company = new Company();
        company.setAppuser(appUser);
        mav.addObject("company", company);
        return mav;
    }

}
