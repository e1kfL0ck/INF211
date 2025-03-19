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
    public ModelAndView showJobOfferForm(HttpServletRequest request) {
        if (request.getSession().getAttribute("usertype") != "company") {
            return new ModelAndView("redirect:/joboffers");
        }
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
        if (request.getSession().getAttribute("usertype") != "company") {
            return new ModelAndView("redirect:/joboffers");
        }
        JobOffer jb = jobOfferService.createJobOffer(jobOffer, (AppUser) request.getSession().getAttribute("user"), selectedSectors);
        jobOfferService.sendMessageToCandidate(jb.getId(), jb);
        return new ModelAndView("redirect:/joboffers");
    }

    @GetMapping(value = "/{id}/applications")
    public ModelAndView getApplicationForJobOffer(@PathVariable("id") int id, HttpServletRequest request) {
        if (!"company".equals(request.getSession().getAttribute("usertype"))) {
            return new ModelAndView("redirect:/joboffers");
        }
        ModelAndView mav = new ModelAndView("jobOffer/jobOfferApplication.html");
        mav.addObject("jobOffer", jobOfferService.getJobOfferById(id));
        mav.addObject("applications", jobOfferService.getApplicationsByJobOffer(id));
        return mav;

    }

    @GetMapping(value = "/{id}/update")
    public ModelAndView editJobOffer(@PathVariable("id") int id, HttpServletRequest request) {
        if (!"company".equals(request.getSession().getAttribute("usertype"))) {
            return new ModelAndView("redirect:/joboffers");
        }
        ModelAndView mav = new ModelAndView("jobOffer/jobOfferForm.html");
        JobOffer jobOffer = jobOfferService.getJobOfferById(id);
        if (jobOffer == null) {
            return new ModelAndView("redirect:/joboffers");
        }
        mav.addObject("action", "edit");
        mav.addObject("jobOffer", jobOffer);
        mav.addObject("qualificationLevels", qualificationLevelService.listOfQualificationLevels());
        mav.addObject("sectors", sectorService.listOfSectors());
        return mav;
    }

    @PostMapping(value = "/{id}/update")
    public ModelAndView updateJobOffer(@PathVariable("id") int id, @ModelAttribute JobOffer jobOffer, @RequestParam List<Integer> selectedSectors, HttpServletRequest request) {
        JobOffer persistedJobOffer = jobOfferService.getJobOfferById(id);
        if (persistedJobOffer.getCompany().getId() != ((AppUser) request.getSession().getAttribute("user")).getCompany().getId()) {
            return new ModelAndView("redirect:/joboffers");
        }
        jobOfferService.updateJobOffer(jobOffer, selectedSectors);
        return new ModelAndView("redirect:/joboffers");
    }

    @PostMapping(value = "/{id}/delete")
    public ModelAndView deleteJobOffer(@PathVariable("id") int id, HttpServletRequest request) {
        JobOffer jobOffer = jobOfferService.getJobOfferById(id);
        if (jobOffer.getCompany().getId() != ((AppUser) request.getSession().getAttribute("user")).getCompany().getId()) {
            return new ModelAndView("redirect:/joboffers");
        }
        jobOfferService.deleteJobOffer(id);
        return new ModelAndView("redirect:/joboffers");
    }
}
