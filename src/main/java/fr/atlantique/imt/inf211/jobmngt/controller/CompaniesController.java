package fr.atlantique.imt.inf211.jobmngt.controller;

import fr.atlantique.imt.inf211.jobmngt.entity.AppUser;
import fr.atlantique.imt.inf211.jobmngt.entity.Company;
import fr.atlantique.imt.inf211.jobmngt.service.CompaniesService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/companies")
public class CompaniesController {

    @Autowired
    private CompaniesService companiesService;

    private static final String COMPANY_LIST = "company/companyList.html";
    private static final String COMPANY_FORM = "company/companyForm.html";
    private static final String COMPANY_VIEW = "company/companyView.html";
    private static final String REDIRECT_COMPANIES = "redirect:/companies";
    private static final String LOGOUT_REDIRECT = "redirect:/logout";


    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView getAllCompanies(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView(COMPANY_LIST);
        //TODO: les variables de sessions sont dispos dans thymleaf avec session.usetr
        mav.addObject("appUser", request.getSession().getAttribute("user"));
        mav.addObject("usertype", request.getSession().getAttribute("usertype"));
        mav.addObject("companieslist", companiesService.listOfCompanies());
        return mav;
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView showCompanyForm() {
        ModelAndView mav = new ModelAndView(COMPANY_FORM);
        AppUser appUser = new AppUser();
        Company company = new Company();
        company.setAppuser(appUser);
        mav.addObject("company", company);
        return mav;
    }

    @PostMapping(value = "/create")
    public ModelAndView createCompany(@ModelAttribute Company company) {
        companiesService.createCompany(company);
        return new ModelAndView(REDIRECT_COMPANIES);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView getCompany(@PathVariable("id") int id) {
        ModelAndView mav = new ModelAndView(COMPANY_VIEW);
        Company company = companiesService.getCompanyById(id);
        mav.addObject("company", company);
        return mav;
    }

    @RequestMapping(value = "/{id}/update", method = RequestMethod.GET)
    public ModelAndView editCompany(@PathVariable("id") int id) {
        ModelAndView mav = new ModelAndView(COMPANY_FORM);

        Company company = companiesService.getCompanyById(id);

        if (company == null) {
            return new ModelAndView(REDIRECT_COMPANIES);
        }

        mav.addObject("company", company);
        mav.addObject("action", "edit"); // Pass the action to indicate editing mode
        return mav;
    }

    @PostMapping(value = "/{id}/update")
    public ModelAndView updateCompany(@PathVariable("id") int id, @ModelAttribute Company company, HttpServletRequest request) {
        Company persistedCompany = companiesService.getCompanyById(id);
        if (persistedCompany.getAppuser().getId() != ((AppUser) request.getSession().getAttribute("user")).getId()) {
            return new ModelAndView(REDIRECT_COMPANIES);
        }
        companiesService.updateCompany(company);
        return new ModelAndView(REDIRECT_COMPANIES);
    }

    @PostMapping(value = "/{id}/delete")
    public ModelAndView deleteCompany(@PathVariable("id") int id, HttpServletRequest request) {
        companiesService.deleteCompany(id);
        return new ModelAndView(LOGOUT_REDIRECT);
    }
}
