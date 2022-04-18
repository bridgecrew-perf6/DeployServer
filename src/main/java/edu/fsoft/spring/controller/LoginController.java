package edu.fsoft.spring.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import edu.fsoft.spring.interfaceService.IAccountService;
import edu.fsoft.spring.model.Account;
import edu.fsoft.spring.SecurityUtils;


@Controller
public class LoginController {
	@Autowired
	private IAccountService accountService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@RequestMapping("/register")
	public String showNewProductForm(Model model) {
		Account account = new Account();
		model.addAttribute("account", account);
		
		return "register";
	}
	
	@RequestMapping(value = "/addAccount", method = RequestMethod.POST)
	public String saveProduct(@ModelAttribute("account") Account account) {	
		if(accountService.findByUsername(account.getUsername()) == null) {
			account.setRole("ADMIN");
			account.setPassword(passwordEncoder.encode(account.getPassword()));
			account.setPoint(0);
			accountService.save(account);	
			return "login";
		} else {
			return "registerFail";
		}
	}
	@RequestMapping("/updateProfile")
	public String updateProfile(@ModelAttribute("account") Account account){
		accountService.save(account);
		return "redirect:/home";
	}

//	@RequestMapping("/profile/{id}")
//	public ModelAndView showEditNewsForm(@PathVariable(name = "id") Long id) {
//		ModelAndView mav = new ModelAndView("profile");
//		Account account = accountService.get(id);
//		mav.addObject("account", account);
//		return mav;
//	}
	
	
	@RequestMapping("/login")
	public String viewLogin(Model model) {
		Account account = new Account();
		model.addAttribute("account", account);
		return "login";
	}
	@RequestMapping("/loginfail")
	public String viewLoginFail(Model model) {
		Account account = new Account();
		model.addAttribute("account", account);
		return "loginFail";
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return new ModelAndView("redirect:/home");
	}
	
	@RequestMapping("/profile")
	public ModelAndView showEditAccountForm() {
		ModelAndView mav = new ModelAndView("profile");
		SecurityUtils securityUtils = new SecurityUtils();
		String username = securityUtils.getPrincipal();
		Account account2 = accountService.findByUsername(username);
		mav.addObject("account", account2);
		return mav;
	}
	
	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	public String changePassword(@ModelAttribute("changePassword") Account account) {
		account.setPassword(passwordEncoder.encode(account.getPassword()));
		accountService.save(account);
		return "redirect:/home";
	}
//
//
//	@RequestMapping(value = "/account/login",  method = RequestMethod.POST)
//	public String login(@ModelAttribute("account") Account account) {
//		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//		Account login;
//		login = accountService.findByUsername(account.getUsername());
//	
//		if (encoder.matches(account.getPassword(), login.getPassword())){
//			return "success";
//		}else{
//			return "fail";
//		}
//	}
}
