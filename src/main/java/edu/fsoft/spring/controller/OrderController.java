package edu.fsoft.spring.controller;

import edu.fsoft.spring.SecurityUtils;
import edu.fsoft.spring.formobj.GlobalData;
import edu.fsoft.spring.model.*;
import edu.fsoft.spring.repository.AccountRepository;
import edu.fsoft.spring.repository.ProductRepository;
import edu.fsoft.spring.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import edu.fsoft.spring.formobj.OrderFormObj;
import edu.fsoft.spring.service.BillService;
import edu.fsoft.spring.service.OrderService;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class OrderController {
	@Autowired
	private OrderService service;
	@Autowired
	private BillService billService;
	@Autowired
	private ProductService productService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private AccountRepository accountRepository;

	public boolean checkProduct(int id) {
		for(int i = 0; i < GlobalData.cart.size(); i++) {
			if(GlobalData.cart.get(i).getId() == id)
				return true;
		}
		return false;
	}

	public boolean checkProductQuantity(int id) {
		if(productService.get(id).getQuantity() <= 0)
			return true;
		return false;
	}
	// Add order
//	@RequestMapping("/newOrder")
//	public String showOrder(Model model) {
//		Order order = new Order();
//		model.addAttribute("order", order);
//		return "newOrder";
//	}

//	@RequestMapping(value = "/saveNewOrder", method = RequestMethod.POST)
//	public String saveProduct(@ModelAttribute("order") OrderFormObj order, Bill bill, Product product) {
//		bill.setId(billService.findMaxIdBill());
//		order.setUnitPrice(product.getPrice());
//		order.setQuantity(1);
//		order.setBill(bill.getId());
//		service.save(order);
//		return "redirect:/staff/order";
//	}

	@GetMapping("/cart/removeItem/{id}")
	@ResponseBody
	public String removeItem(@PathVariable int id){
		if (GlobalData.cart!=null){
			for (Product p : GlobalData.cart) {
				if (p.getId() == id){
					GlobalData.cart.remove(p);
					break;
				}
			}
		}
		System.out.println(GlobalData.cart);
		return "success";
	}


	@GetMapping("/addToCart/{id}")
	@ResponseBody
	public String addToCart(@PathVariable int id){
		if(checkProduct(id)) {

			return "existed";
		}
		else if(checkProductQuantity(id)) {
			return "zero";
		}
		else {
			GlobalData.cart.add(productService.get(id));
			return "success";
		}
	}

	@GetMapping("/cart")
	public ModelAndView cartGet(Model model){
		ModelAndView mav = new ModelAndView("cart");
		SecurityUtils securityUtils = new SecurityUtils();
		String username = securityUtils.getPrincipal();
		Account account2 = accountRepository.findByUsername(username);
		mav.addObject("account", account2);
		model.addAttribute("cartCount", GlobalData.cart.size());
		model.addAttribute("total", GlobalData.cart.stream().mapToDouble(Product::getPrice).sum());
		model.addAttribute("cart", GlobalData.cart);
		List<Integer> quantityList = new ArrayList<>();
		model.addAttribute("quantityList", quantityList);
return mav;
	}

	@RequestMapping(value = "/cart/get-account/{phoneNumber}", method = RequestMethod.POST)
	@ResponseBody
	public Account cartPost(@PathVariable String phoneNumber){
		Account account = accountRepository.findByPhone(phoneNumber);
		return account;
	}
	@RequestMapping(value = "/cart/addOrder", method = RequestMethod.POST)
	@ResponseBody
	public ResponseModel saveOrder(CreateNewOrderDto dto) {
		if (GlobalData.cart.size() == 0)
			return new ResponseModel(false, "Cart is empty!");
		else {
			Bill bill = new Bill();
			Account account = accountRepository.findByPhone(dto.getCustomerPhone());
			float discount = dto.getPoint() * 1000;
			billService.save(bill);
			float total = 0;

			List<Integer> list = dto.getListItems();
			for (int i = 0; i < GlobalData.cart.size(); i++) {
				OrderFormObj order = new OrderFormObj();
				order.setBill(bill.getId());
				order.setProduct(GlobalData.cart.get(i).getId());
				order.setQuantity(list.get(i).intValue());
				order.setUnitPrice(GlobalData.cart.get(i).getPrice());
				orderService.save(order);
				total = total + order.getQuantity() * order.getUnitPrice();
				Product product = productService.get(GlobalData.cart.get(i).getId());
				product.setQuantity(product.getQuantity() - list.get(i).intValue());
				productRepository.save(product);
			}
			total = total - discount;
			int plusPoint = (int) total / 50000;
			if (account != null) {
				bill.setCustomerPhone(account.getPhone());
				account.setPoint(account.getPoint() + plusPoint);
				account.setPoint(account.getPoint() - dto.getPoint());
			} else bill.setCustomerPhone(null);
			bill.setDiscount(discount);
			bill.setTotal(total);
			billService.save(bill);
			GlobalData.cart.clear();
			return new ResponseModel(true, "Add successfully!");
		}
	}
}
