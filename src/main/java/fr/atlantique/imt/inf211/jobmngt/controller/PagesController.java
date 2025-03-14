package fr.atlantique.imt.inf211.jobmngt.controller;

import fr.atlantique.imt.inf211.jobmngt.service.CandidateService;
import fr.atlantique.imt.inf211.jobmngt.service.CompaniesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import org.springframework.stereotype.Controller;

@Controller
public class PagesController {

	@Autowired
	CompaniesService companiesService;
	@Autowired
	CandidateService candidateService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView welcomePage() {
		ModelAndView modelAndView = new ModelAndView("index");
		//TODO : add count of companies and candidates
		modelAndView.addObject("countCompanies", companiesService.listOfCompanies().size());
		modelAndView.addObject("countCandidates", candidateService.listOfCandidates().size());

		return modelAndView;

	}
}