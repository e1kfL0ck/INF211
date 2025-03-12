package fr.atlantique.imt.inf211.jobmngt.controller;

import fr.atlantique.imt.inf211.jobmngt.entity.AppUser;
import fr.atlantique.imt.inf211.jobmngt.entity.Candidate;
import fr.atlantique.imt.inf211.jobmngt.service.CandidateService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller()
@RequestMapping("/candidates")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @GetMapping("")
    public ModelAndView listOfCandidates(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("candidate/candidateList.html");
        mav.addObject("appUser", request.getSession().getAttribute("user"));
        mav.addObject("candidateslist", candidateService.listOfCandidates());
        return mav;
    }

    @GetMapping("/create")
    public ModelAndView createCandidate() {
        ModelAndView mav = new ModelAndView("candidate/candidateForm.html");
        AppUser appUser = new AppUser();
        Candidate candidate = new Candidate();
        candidate.setAppuser(appUser);
        mav.addObject("candidate", candidate);
        return mav;
    }

    @PostMapping("/createCandidateData")
    public ModelAndView createCandidateData(@ModelAttribute Candidate candidate) {
        candidateService.createCandidate(candidate.getAppuser().getMail(), candidate.getAppuser().getPassword(), candidate.getAppuser().getCity(), candidate.getLastname(), candidate.getFirstname());
        return new ModelAndView("redirect:/candidates");
    }

    // Endpoint pour supprimer un candidat
    @PostMapping("/remove")
    public ModelAndView removeCandidate(@ModelAttribute Candidate candidate) {
        candidateService.removeCandidate(candidate.getId());
        return new ModelAndView("redirect:/candidates");
    }

    @GetMapping("/{id}/update")
    public ModelAndView editCandidate(@PathVariable("id") int id) {
        ModelAndView mav = new ModelAndView("candidate/candidateForm.html");

        Candidate candidate = candidateService.getCandidate(id);

        if (candidate == null) {
            return new ModelAndView("redirect:/candidates");
        }

        mav.addObject("candidate", candidate);
        mav.addObject("action", "edit"); // Pass the action to indicate editing mode
        return mav;
    }

    @PostMapping("/{id}/updateCandidateData")
    public ModelAndView updateCandidate(@PathVariable("id") int id, @ModelAttribute Candidate candidate, HttpServletRequest request) {
        Candidate persistedCandidate = candidateService.getCandidate(id);

        if (persistedCandidate.getAppuser().getId() != ((AppUser) request.getSession().getAttribute("user")).getId()) {
            return new ModelAndView("redirect:/candidates");
        }
        candidateService.updateCandidate(candidate);
        return new ModelAndView("redirect:/candidates");
    }

    @GetMapping("/get")
    public ResponseEntity<Candidate> getCandidate(@RequestBody Candidate request) {
        Candidate candidate = candidateService.getCandidate(request.getId());
        return ResponseEntity.ok(candidate);
    }
}



