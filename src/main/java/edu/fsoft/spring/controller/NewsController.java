package edu.fsoft.spring.controller;

import java.util.List;

import edu.fsoft.spring.SecurityUtils;
import edu.fsoft.spring.model.Account;
import edu.fsoft.spring.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import edu.fsoft.spring.model.News;
import edu.fsoft.spring.model.Product;
import edu.fsoft.spring.service.NewsService;

@Controller
public class NewsController {
	
	
	@Autowired
	private NewsService service;
	@Autowired
	private AccountService accountService;
	
	//Add product
	@RequestMapping("/admin/news")
	public String showNewNewsForm(Model model) {
		News news = new News();
		model.addAttribute("news", news);
		List<News> listNews = service.listAll();
		model.addAttribute("listNews", listNews);
		return "admin-news";
	}
	@RequestMapping("/staff/news")
	public String showNewNewsFormByStaff(Model model) {
		SecurityUtils securityUtils = new SecurityUtils();
		String username = securityUtils.getPrincipal();
		Account account2 = accountService.findByUsername(username);
		model.addAttribute("account", account2);
		News news = new News();
		model.addAttribute("news", news);
		List<News> listNews = service.listAll();
		model.addAttribute("listNews", listNews);
		return "list-news-staff";
	}
	
//	@RequestMapping("viewer/news")
//	public String showNews(Model model) {
//		News news = new News();
//		model.addAttribute("news", news);
//		List<News> listNews = service.listAll();
//		model.addAttribute("listNews", listNews);
//		return "news";
//	}
	
	@RequestMapping(value = "/saveNewNews", method = RequestMethod.POST)
	public String saveNews(@ModelAttribute("news") News news) {
		service.save(news);
		return "redirect:/admin/news";
	}
	@RequestMapping(value = "/staffSaveNewNews", method = RequestMethod.POST)
	public String saveNewsByStaff(@ModelAttribute("news") News news) {
		service.save(news);
		return "redirect:/staff/news";
	}
	@RequestMapping("/staff/news/edit/{id}")
	public ModelAndView showEditNewsFormByStaff(@PathVariable(name = "id") int id) {
		ModelAndView mav = new ModelAndView("staff-news-edit");
		SecurityUtils securityUtils = new SecurityUtils();
		String username = securityUtils.getPrincipal();
		Account account2 = accountService.findByUsername(username);
		mav.addObject("account", account2);
		News news = service.get(id);
		mav.addObject("news", news);
		return mav;
	}
	@RequestMapping("/staff/deleteNews/{id}")
	public String deleteProductByStaff(@PathVariable(name = "id") int id) {
		service.delete(id);
		return "redirect:/staff/news";
	}
//	//List news
//	@RequestMapping("/admin/new")
//	public String viewHomePage(Model model) {
//		List<News> listNews = service.listAll();
//		model.addAttribute("listNews", listNews);
//		return "admin-new";
//	}
	//Delete News
	@RequestMapping("/deleteNews/{id}")
	public String deleteProduct(@PathVariable(name = "id") int id) {
		service.delete(id);
		return "redirect:/admin/news";
	}
	//Edit News
	@RequestMapping("/admin/news/edit/{id}")
	public ModelAndView showEditNewsForm(@PathVariable(name = "id") int id) {
		ModelAndView mav = new ModelAndView("admin-news-edit");
		News news = service.get(id);
		mav.addObject("news", news);
		return mav;
	}	
	@RequestMapping(value = "/saveNews", method = RequestMethod.POST)
	public String saveNewss(@ModelAttribute("news") News news) {
		service.save(news);
		return "redirect:/admin/news";
	}
	
	@RequestMapping("/news/{id}")
	public ModelAndView showDetailsNew(@PathVariable(name = "id") int id, Model model){
		SecurityUtils securityUtils = new SecurityUtils();
		String username = securityUtils.getPrincipal();
		Account account2 = accountService.findByUsername(username);
		model.addAttribute("account", account2);
		ModelAndView mav = new ModelAndView("news-detail");
		News news = service.get(id);
		mav.addObject("news", news);
		model.addAttribute("news", news);
		List<News> listNews = service.listAll();
		model.addAttribute("listNews", listNews);
		return mav;
	}
}