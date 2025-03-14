package fr.atlantique.imt.inf211.jobmngt.controller;

import fr.atlantique.imt.inf211.jobmngt.entity.AppUser;
import fr.atlantique.imt.inf211.jobmngt.entity.JobOfferMessage;
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

    @GetMapping
    public ModelAndView getAllMessages(HttpSession session) {
        ModelAndView mav = new ModelAndView("message.html");
        List<JobOfferMessage> messages = messageService.listOfMessages( (AppUser) session.getAttribute("user"));
        mav.addObject("messages", messages);
        return mav;
    }
}
