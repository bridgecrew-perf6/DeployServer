package edu.fsoft.spring.controller;

import edu.fsoft.spring.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.fsoft.spring.service.FeedbackService;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class FeedbackController {
 
	@Autowired
	private FeedbackService service;
	@RequestMapping(value = "/saveFeedback", method = RequestMethod.POST)
	public String saveNewss(@ModelAttribute("feedback") Feedback feedback) {
		service.save(feedback);
		return "redirect:/";
	}
	@RequestMapping("/admin/feedback")
	public String viewHomePage(Model model) {
		Feedback feedback = new Feedback();
		model.addAttribute("feedback", feedback);
		List<Feedback> listFeedback = service.listAll();
		model.addAttribute("listFeedback", listFeedback);
		return "feedback-list";
	}
	@RequestMapping("/deleteFeedback/{id}")
	public String deleteFeedback(@PathVariable(name = "id") int id) {
		Feedback feedback = service.get(id);
		System.out.println(id);
		service.deleteFeedback(feedback);
		return "redirect:/admin/feedback";
	}
	@RequestMapping("/viewFeedback/{id}")
	public ModelAndView editCategory(@PathVariable(name = "id") int id) {
		ModelAndView mav = new ModelAndView("viewFeedback");
		Feedback feedback = service.get(id);
		mav.addObject("feedback", feedback);
		return mav;
	}
}
