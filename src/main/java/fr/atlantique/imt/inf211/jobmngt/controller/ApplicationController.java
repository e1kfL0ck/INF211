package fr.atlantique.imt.inf211.jobmngt.controller;

import fr.atlantique.imt.inf211.jobmngt.entity.AppUser;
import fr.atlantique.imt.inf211.jobmngt.entity.Application;
import fr.atlantique.imt.inf211.jobmngt.entity.QualificationLevel;
import fr.atlantique.imt.inf211.jobmngt.entity.Sector;
import fr.atlantique.imt.inf211.jobmngt.service.ApplicationService;
import fr.atlantique.imt.inf211.jobmngt.service.QualificationLevelService;
import fr.atlantique.imt.inf211.jobmngt.service.SectorService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller()
@RequestMapping("/applications")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private QualificationLevelService qualificationLevelService;
    @Autowired
    private SectorService sectorService;

    @GetMapping("")
    public ModelAndView listOfApplication(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("applications/applicationsList.html");
        AppUser appUser = (AppUser) request.getSession().getAttribute("user");
        if (appUser != null) {
            List<Application> applications = applicationService.getApplication(appUser.getCandidate().getId());
            mav.addObject("applicationslist", applications);
        }
        return mav;
    }

    @GetMapping("/create")
    public ModelAndView createApplication() {
        ModelAndView mav = new ModelAndView("applications/applicationsForm.html");
        Application application = new Application();
        List<QualificationLevel> qualificationLevels = qualificationLevelService.listOfQualificationLevels();
        List<Sector> sectors = sectorService.listOfSectors();
        mav.addObject("applications", application);
        mav.addObject("qualificationLevels", qualificationLevels);
        mav.addObject("sectors", sectors);
        return mav;
    }

    @PostMapping("/createApplicationData")
    public ModelAndView createApplicationData(@ModelAttribute Application application, @RequestParam List<Integer> selectedSectors, HttpServletRequest request) {
        AppUser appUser = (AppUser) request.getSession().getAttribute("user");
        Set<Sector> sectors = selectedSectors.stream().map(sectorService::getSectorById).collect(Collectors.toSet());
        application.setSectors(sectors);
        applicationService.createApplication(appUser.getCandidate().getId(), application.getQualificationlevel().getId(), application.getCv(), selectedSectors);
        return new ModelAndView("redirect:/applications");
    }

    @PostMapping("/remove")
    public ModelAndView removeApplication(@ModelAttribute Application application) {
        applicationService.deleteApplication(application.getId());
        return new ModelAndView("redirect:/applications");
    }

}
