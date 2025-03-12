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

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView getAllCompanies(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("company/companyList.html");
        //TODO: les variables de sessions sont dispos dans thymleaf avec session.usetr
        mav.addObject("appUser", request.getSession().getAttribute("user"));
        mav.addObject("usertype", request.getSession().getAttribute("usertype"));
        mav.addObject("companieslist", companiesService.listOfCompanies());
        return mav;
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView showCompanyForm() {
        ModelAndView mav = new ModelAndView("company/companyForm.html");
        AppUser appUser = new AppUser();
        Company company = new Company();
        company.setAppuser(appUser);
        mav.addObject("company", company);
        return mav;
    }

    @PostMapping(value = "/create")
    public ModelAndView createCompany(@ModelAttribute Company company) {
        companiesService.createCompany(company);
        return new ModelAndView("redirect:/companies");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView getCompany(@PathVariable("id") int id) {
        ModelAndView mav = new ModelAndView("company/companyView.html");
        Company company = companiesService.getCompanyById(id);
        mav.addObject("company", company);
        return mav;
    }

    @RequestMapping(value = "/{id}/update", method = RequestMethod.GET)
    public ModelAndView editCompany(@PathVariable("id") int id) {
        ModelAndView mav = new ModelAndView("company/companyForm.html");

        Company company = companiesService.getCompanyById(id);

        if (company == null) {
            return new ModelAndView("redirect:/companies");
        }

        mav.addObject("company", company);
        mav.addObject("action", "edit"); // Pass the action to indicate editing mode
        return mav;
    }

    @PostMapping(value = "/{id}/update")
    public ModelAndView updateCompany(@PathVariable("id") int id, @ModelAttribute Company company, HttpServletRequest request) {
        Company persistedCompany = companiesService.getCompanyById(id);
        if(persistedCompany.getAppuser().getId() != ((AppUser) request.getSession().getAttribute("user")).getId()) {
            return new ModelAndView("redirect:/companies");
        }
        companiesService.updateCompany(company);
        return new ModelAndView("redirect:/companies");
    }

    @DeleteMapping(value = "/{id}/delete")
    public ModelAndView deleteCompany(@PathVariable("id") int id) {
        companiesService.deleteCompany(id);
        return new ModelAndView("redirect:/companies");
    }
}
