package edu.fsoft.spring.controller;

import java.util.List;

import edu.fsoft.spring.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.fsoft.spring.model.Account;
import edu.fsoft.spring.model.Feedback;
import edu.fsoft.spring.model.News;
import edu.fsoft.spring.model.Product;
import edu.fsoft.spring.service.AccountService;
import edu.fsoft.spring.service.FeedbackService;
import edu.fsoft.spring.service.NewsService;
import edu.fsoft.spring.service.ProductService;

@Controller
public class HomeController {
	@Autowired
	private ProductService pservice;
	@Autowired
	private NewsService nservice;
	@Autowired
	private FeedbackService fservice;
	@Autowired
	private AccountService accountService;
	
	@RequestMapping("/home")
	public String index(Model model) {
		SecurityUtils securityUtils = new SecurityUtils();
		String username = securityUtils.getPrincipal();
		Account account2 = accountService.findByUsername(username);
		model.addAttribute("account", account2);
		Product product = new Product();
		model.addAttribute("product", product);
		List<Product> listDrinks = pservice.findByCategory(1);
		model.addAttribute("listDrinks", listDrinks);
		List<Product> listFoods = pservice.findByCategory(2);
		model.addAttribute("listFoods", listFoods);
		List<Product> listCandy = pservice.findByCategory(3);
		model.addAttribute("listCandy", listCandy);
		List<Product> listIce = pservice.findByCategory(4);
		model.addAttribute("listIce", listIce);
		List<Product> listVegetable = pservice.findByCategory(6);
		model.addAttribute("listVegetable", listVegetable);
		List<Product> listSnack = pservice.findByCategory(5);
		model.addAttribute("listSnack", listSnack);
		List<Product> listMilk = pservice.findByCategory(8);
		model.addAttribute("listMilk", listMilk);
        return "index";
    }
	
	@RequestMapping("/menu")
	public String menu(Model model) {
		SecurityUtils securityUtils = new SecurityUtils();
		String username = securityUtils.getPrincipal();
		Account account2 = accountService.findByUsername(username);
		model.addAttribute("account", account2);
		Product product = new Product();
		model.addAttribute("product", product);
//		List<Product> listProduct = pservice.listAll();
//		model.addAttribute("listProduct", listProduct);
		List<Product> listDrinks = pservice.findByCategory(1);
		model.addAttribute("listDrinks", listDrinks);
		List<Product> listFoods = pservice.findByCategory(2);
		model.addAttribute("listFoods", listFoods);
		List<Product> listCandy = pservice.findByCategory(3);
		model.addAttribute("listCandy", listCandy);
		List<Product> listIce = pservice.findByCategory(4);
		model.addAttribute("listIce", listIce);
		List<Product> listVegetable = pservice.findByCategory(6);
		model.addAttribute("listVegetable", listVegetable);
		List<Product> listSnack = pservice.findByCategory(5);
		model.addAttribute("listSnack", listSnack);
		List<Product> listMilk = pservice.findByCategory(8);
		model.addAttribute("listMilk", listMilk);
        return "menu";
    }
	
	@RequestMapping("/about")
	public String about() {

        return "about";
    }
	
	@RequestMapping("/admin")
	public String admin() {
        return "admin-index";
    }
	@RequestMapping("/contact")
	public String contact(Model model) {
		SecurityUtils securityUtils = new SecurityUtils();
		String username = securityUtils.getPrincipal();
		Account account2 = accountService.findByUsername(username);
		model.addAttribute("account", account2);
		Feedback feedback = new Feedback();
		model.addAttribute("feedback", feedback);
        return "contact";
    }
	
	@RequestMapping("/news")
	public String viewNews(Model model) {
		SecurityUtils securityUtils = new SecurityUtils();
		String username = securityUtils.getPrincipal();
		Account account2 = accountService.findByUsername(username);
		model.addAttribute("account", account2);
		News news = new News();
		model.addAttribute("news", news);
		List<News> listNews = nservice.listAll();
		model.addAttribute("listNews", listNews);
        return "news";
	}
	
	@RequestMapping("/staff")
	public String staff() {
        return "staff-index";
    }
	
	 @GetMapping("/403")
	 public String error403() {
		return "/error/403";
	 }
}
