package fr.atlantique.imt.inf211.jobmngt.controller;

import fr.atlantique.imt.inf211.jobmngt.entity.*;
import fr.atlantique.imt.inf211.jobmngt.service.ApplicationService;
import fr.atlantique.imt.inf211.jobmngt.service.JobOfferService;
import fr.atlantique.imt.inf211.jobmngt.service.QualificationLevelService;
import fr.atlantique.imt.inf211.jobmngt.service.SectorService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.HashSet;
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
    @Autowired
    private JobOfferService jobOfferService;

    private static final String APPLICATIONS_LIST = "applications/applicationsList.html";
    private static final String APPLICATIONS_FORM = "applications/applicationsForm.html";
    private static final String APPLICATIONS_REDIRECT = "redirect:/applications";
    private static final String APPLICATIONS_LIST_OBJECT = "applicationslist";
    private static final String HOME_REDIRECT = "redirect:/";

    @GetMapping("")
    public ModelAndView listOfApplication(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView(APPLICATIONS_LIST);
        AppUser appUser = (AppUser) request.getSession().getAttribute("user");

//        List<Application> applications = applicationService.getApplication(appUser.getCandidate().getId());
        List<Application> applications = applicationService.listOfApplications();
        mav.addObject(APPLICATIONS_LIST_OBJECT, applications);

        mav.addObject("appUser", request.getSession().getAttribute("user"));
        return mav;
    }

    @GetMapping("/create")
    public ModelAndView createApplication() {
        ModelAndView mav = new ModelAndView(APPLICATIONS_FORM);
        Application application = new Application();
        List<QualificationLevel> qualificationLevel = qualificationLevelService.listOfQualificationLevels();
        List<Sector> sectors = sectorService.listOfSectors();
        mav.addObject("applications", application);
        mav.addObject("qualificationLevel", qualificationLevel);
        mav.addObject("sectors", sectors);
        mav.addObject("action", "create");
        return mav;
    }

    @PostMapping("/createApplicationData")
    public ModelAndView createApplicationData(@ModelAttribute Application application, @RequestParam List<Integer> selectedSectors, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        if (!request.getSession().getAttribute("usertype").equals("candidate")) {
            return new ModelAndView(HOME_REDIRECT);
        }

        AppUser appUser = (AppUser) request.getSession().getAttribute("user");
        Set<Sector> sectors = selectedSectors.stream().map(sectorService::getSectorById).collect(Collectors.toSet());

        application.setSectors(sectors);
        Application app = applicationService.createApplication(appUser.getCandidate().getId(), application.getQualificationlevel().getId(), application.getCv(), selectedSectors);

        Set<Company> companies = new HashSet<>(applicationService.sendMessageToCompany(app.getId(), app));

        redirectAttributes.addFlashAttribute("applicationCreated", true);
        redirectAttributes.addFlashAttribute("companiesList", companies);

        return new ModelAndView(APPLICATIONS_REDIRECT);
    }


    @PostMapping("/remove")
    public ModelAndView removeApplication(@ModelAttribute Application application) {
        applicationService.deleteApplication(application.getId());
        return new ModelAndView(APPLICATIONS_REDIRECT);
    }

    @GetMapping("/{id}/update")
    public ModelAndView editApplication(@PathVariable("id") int id) {
        ModelAndView mav = new ModelAndView(APPLICATIONS_FORM);
        Application application = applicationService.getApplicationById(id);
        List<QualificationLevel> qualificationLevels = qualificationLevelService.listOfQualificationLevels();
        List<Sector> sectors = sectorService.listOfSectors();
        mav.addObject("applications", application);
        mav.addObject("qualificationLevel", qualificationLevels);
        mav.addObject("sectors", sectors);
        mav.addObject("action", "edit");
        return mav;
    }

    @PostMapping("/{id}/updateApplicationData")
    public ModelAndView updateApplication(@PathVariable("id") int id, @ModelAttribute Application application, @RequestParam List<Integer> selectedSectors, HttpServletRequest request) {
        if (!request.getSession().getAttribute("usertype").equals("candidate")) {
            return new ModelAndView(HOME_REDIRECT);
        }

        Application persistedApplication = applicationService.getApplicationById(id);
        application.setApplicationdate(new Date());

        if (persistedApplication.getCandidate().getAppuser().getId() != ((AppUser) request.getSession().getAttribute("user")).getId()) {
            return new ModelAndView(APPLICATIONS_REDIRECT);
        }

        Set<Sector> sectors = selectedSectors.stream().map(sectorService::getSectorById).collect(Collectors.toSet());
        application.setSectors(sectors);
        applicationService.updateApplication(application);
        return new ModelAndView(APPLICATIONS_REDIRECT);
    }

    @GetMapping("/get")
    public ModelAndView searchApplication(@RequestParam("id") int id, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView(APPLICATIONS_LIST);
        Application application = applicationService.getApplicationById(id);
        if (application != null) {
            mav.addObject(APPLICATIONS_LIST_OBJECT, List.of(application));
        }
        else {
            mav.addObject(APPLICATIONS_LIST_OBJECT, List.of());
        }
        return mav;
    }

    @GetMapping("/{id}/jobOffers")
    public ModelAndView viewJobOffers(@PathVariable("id") int id) {
        ModelAndView mav = new ModelAndView("applications/applicationsJobOffer.html");
        Application application = applicationService.getApplicationById(id);
        mav.addObject("sector", sectorService.listOfSectors());
        mav.addObject("application", application);
        mav.addObject("jobOffers", applicationService.getJobOffersByApplicationId(id));
        return mav;
    }
}
