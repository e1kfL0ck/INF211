package fr.atlantique.imt.inf211.jobmngt.controller;

import fr.atlantique.imt.inf211.jobmngt.entity.AppUser;
import fr.atlantique.imt.inf211.jobmngt.entity.JobOffer;
import fr.atlantique.imt.inf211.jobmngt.service.JobOfferService;
import fr.atlantique.imt.inf211.jobmngt.service.QualificationLevelService;
import fr.atlantique.imt.inf211.jobmngt.service.SectorService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping(value = "/joboffers")
public class JobOfferController {

    @Autowired
    private JobOfferService jobOfferService;
    @Autowired
    private QualificationLevelService qualificationLevelService;
    @Autowired
    private SectorService sectorService;

    @GetMapping(value = "")
    public ModelAndView getAllJobOffers() {
        ModelAndView mav = new ModelAndView("jobOffer/jobOfferList.html");
        mav.addObject("jobOffersList", jobOfferService.listOfJobOffers());
        return mav;
    }

    @GetMapping(value = "/create")
    public ModelAndView showJobOfferForm() {
        ModelAndView mav = new ModelAndView("jobOffer/jobOfferForm.html");
        JobOffer jobOffer = new JobOffer();
        mav.addObject("jobOffer", jobOffer);
        mav.addObject("qualificationLevels", qualificationLevelService.listOfQualificationLevels());
        mav.addObject("sectors", sectorService.listOfSectors());
        return mav;
    }

    @PostMapping(value = "/create")
    //TODO : Voir pour supprimer le selectedSectors
    public ModelAndView createJobOffer(@ModelAttribute JobOffer jobOffer, @RequestParam List<Integer> selectedSectors, HttpServletRequest request) {
        jobOfferService.createJobOffer(jobOffer, (AppUser) request.getSession().getAttribute("user"), selectedSectors);
        return new ModelAndView("redirect:/joboffers");
    }

    // 	Une fois connectée, l’entreprise accède à la liste d’offres d’emploi qu’elle a soumises et, pour chacune, à la liste des candidatures susceptibles de correspondre à l’offre. Cette fonctionnalité est similaire à la liste des candidatures accessible à tout utilisateur (qui en afﬁche la totalité) mais elle opère une sélection.
    @GetMapping(value = "/{id}/applications")
    public ModelAndView getApplicationForJobOffer(@PathVariable("id") int id) {
        ModelAndView mav = new ModelAndView("jobOffer/jobOfferApplication.html");
        mav.addObject("jobOffer", jobOfferService.getJobOfferById(id));
        mav.addObject("applications", jobOfferService.getApplicationsByJobOffer(id));
        return mav;

    }
}
