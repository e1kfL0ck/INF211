package fr.atlantique.imt.inf211.jobmngt.controller;

import fr.atlantique.imt.inf211.jobmngt.entity.AppUser;
import fr.atlantique.imt.inf211.jobmngt.entity.ApplicationMessage;
import fr.atlantique.imt.inf211.jobmngt.entity.JobOfferMessage;
import fr.atlantique.imt.inf211.jobmngt.service.ApplicationService;
import fr.atlantique.imt.inf211.jobmngt.service.MessageService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    private static final String REDIRECT_HOME = "redirect:/";

    @GetMapping
    public ModelAndView getAllMessages(HttpSession session) {
        if(session.getAttribute("user") == null) {
            return new ModelAndView(REDIRECT_HOME);
        }
        ModelAndView mav = new ModelAndView("message.html");
        List<JobOfferMessage> messagesJobOffer = messageService.listOfMessagesJobOffer( (AppUser) session.getAttribute("user"));
        List<ApplicationMessage> messagesApplication = messageService.listOfMessagesApplication( (AppUser) session.getAttribute("user"));

        mav.addObject("messagesJobOffer", messagesJobOffer);
        return mav;
    }
}
