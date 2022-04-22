package edu.fsoft.spring.controller;

import java.util.List;

import edu.fsoft.spring.SecurityUtils;
import edu.fsoft.spring.interfaceService.IProductService;
import edu.fsoft.spring.model.Product;
import edu.fsoft.spring.model.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import edu.fsoft.spring.interfaceService.IAccountService;
import edu.fsoft.spring.model.Account;
import edu.fsoft.spring.model.News;
import edu.fsoft.spring.service.AccountService;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class StaffController {
	@Autowired
	private AccountService aservice;
	
	@Autowired
	private IAccountService iaccountService;
	@Autowired
	private IProductService pservice;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@RequestMapping("/admin/staff")
	public String staffManager(Model model) {
		Account account = new Account();
		model.addAttribute("account", account);
		List<Account> listStaff = aservice.findByRole("STAFF");
		model.addAttribute("listStaff", listStaff);
		return "admin-staff";
	}
	
	@RequestMapping(value = "/saveNewStaff", method = RequestMethod.POST)
	@ResponseBody
	public ResponseModel saveStaff(@ModelAttribute("account") Account account) {
		if(aservice.findByUsername(account.getUsername()) != null) {
//			String usernameErr = "";
//			model.addAttribute("Tên đăng nhập đã được sủ dụng!", usernameErr);
//			mode
			return new ResponseModel(false,"Tên đăng nhập đã được sủ dụng!");
		} else if(aservice.findByEmail(account.getEmail()) != null) {
//			String emailErr = "";
//			model.addAttribute("Email đã được sủ dụng!", emailErr);
			System.out.println(account.getEmail());
			return new ResponseModel(false,"Email đã được sủ dụng!");
		} else if(aservice.findByPhone(account.getPhone()) != null) {
//			String phoneErr = "";
//			model.addAttribute("Số điện thoại đã được sủ dụng!", phoneErr);
			return new ResponseModel(false,"Số điện thoại đã được sủ dụng!");
		} else {
			account.setRole("STAFF");
			account.setPassword(passwordEncoder.encode(account.getPassword()));
			account.setPoint(0);
			aservice.save(account);
			return new ResponseModel(true,"Đăng ký tài khoản thành công");
		}
	}
	
	@RequestMapping("admin/deleteStaff/{id}")
	public String deleteStaff(@PathVariable(name = "id") long id) {
		aservice.delete(id);
		return "redirect:/admin/staff";
	}

	@RequestMapping("/staff/menu")
	public ModelAndView menu(Model model) {
		ModelAndView mav = new ModelAndView("menuStaff");
		SecurityUtils securityUtils = new SecurityUtils();
		String username = securityUtils.getPrincipal();
		Account account2 = iaccountService.findByUsername(username);
		mav.addObject("account", account2);
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
		return mav;
	}


}
