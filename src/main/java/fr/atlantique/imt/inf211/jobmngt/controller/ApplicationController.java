package fr.atlantique.imt.inf211.jobmngt.controller;

import fr.atlantique.imt.inf211.jobmngt.entity.Application;
import fr.atlantique.imt.inf211.jobmngt.entity.Sector;
import fr.atlantique.imt.inf211.jobmngt.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller()
@RequestMapping("/applications")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @GetMapping("")
    public ModelAndView listOfApplication() {
        ModelAndView mav = new ModelAndView("applications/applicationsList.html");
        List<Application> li = applicationService.listOfApplications();
        mav.addObject("applicationslist", li);
        return mav;
    }

    @GetMapping("/create")
    public ModelAndView createApplication() {
        ModelAndView mav = new ModelAndView("applications/applicationsForm.html");
        Application application = new Application();
        mav.addObject("application", application);
        return mav;
    }

    @PostMapping("/createApplicationData")
    public ModelAndView createApplicationData(@ModelAttribute Application application) {
        List<Integer> sectorIds = application.getSectors().stream().map(Sector::getId).collect(Collectors.toList());
        applicationService.createApplication(application.getCandidate().getId(), application.getQualificationlevel().getId(), application.getCv(), sectorIds);
        return new ModelAndView("redirect:/applications");
    }

}
