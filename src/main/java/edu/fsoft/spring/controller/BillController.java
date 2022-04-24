package edu.fsoft.spring.controller;

import java.util.ArrayList;
import java.util.List;

import edu.fsoft.spring.SecurityUtils;
import edu.fsoft.spring.formobj.ProductByCount;
import edu.fsoft.spring.model.Account;
import edu.fsoft.spring.model.Bill;
import edu.fsoft.spring.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import edu.fsoft.spring.model.Order;
import edu.fsoft.spring.service.BillService;
import edu.fsoft.spring.service.OrderService;

@Controller
public class BillController {
	@Autowired
	private BillService service;
	@Autowired
	private AccountService accountService;
	@Autowired
	private OrderService orderService;
	//Add product
	@RequestMapping("/newBill")
	public String showNewNewsForm(Model model) {
		Bill bill = new Bill();
		model.addAttribute("bill", bill);
		return "newBill";
	}
	@RequestMapping(value = "/saveNewBill", method = RequestMethod.POST)
	public String saveNews(@ModelAttribute("bill") Bill bill,Model model) {
		service.update(bill);
		Bill bill2 = new Bill();
		service.save(bill2);	
		return "redirect:/staff/order";
	}
	
	@RequestMapping("/bill")
	public String viewBill(Model model) {
		Bill bill = new Bill();
		model.addAttribute("bill", bill);
		List<Bill> listBills = service.listAll();
		model.addAttribute("listBills", listBills);
		return "bill";
		}
	@GetMapping("/billStaff")
	public ModelAndView viewBillByStaff(Model model) {
		ModelAndView mav = new ModelAndView("billStaff");
		SecurityUtils securityUtils = new SecurityUtils();
		String username = securityUtils.getPrincipal();
		Account account2 = accountService.findByUsername(username);
		mav.addObject("account", account2);
		Bill bill = new Bill();
		model.addAttribute("bill", bill);
		List<Bill> listBills = service.ListBillByStaff(account2.getUsername());
		model.addAttribute("listBills", listBills);
		return mav;
	}
	@RequestMapping("/bill/{id}")
	public ModelAndView viewBill(@PathVariable(name = "id") int id, Model model) {
		ModelAndView mav = new ModelAndView("bill-detail");
		SecurityUtils securityUtils = new SecurityUtils();
		String username = securityUtils.getPrincipal();
		Account account2 = accountService.findByUsername(username);
		model.addAttribute("account", account2);
		Bill bill = service.get(id);
		List<Order> listOrder = orderService.findOrderByBill(bill.getId());
		//Ten khach hang
		Account account = new Account();
		if(bill.getCustomerPhone() == null){
			account.setFullname("");
			account.setPhone("");
			account.setEmail("");
		}
		else {
			account = accountService.findByPhone(bill.getCustomerPhone());
		}
		//Ten nhan vien
		List<Account> account1 = accountService.findUsername(bill.getCreatedBy());
		model.addAttribute("account1", account1);
		model.addAttribute("account2", account);
		model.addAttribute("listOrder", listOrder);
		mav.addObject("bill", bill);
		return mav;
	}
	@RequestMapping("/billStaffDetail/{id}")
	public ModelAndView viewBillByStaffDetails(@PathVariable(name = "id") int id, Model model) {
		ModelAndView mav = new ModelAndView("bill-details-by-staff");
		Bill bill = service.get(id);
		List<Order> listOrder = orderService.findOrderByBill(bill.getId());
		SecurityUtils securityUtils = new SecurityUtils();
		String username = securityUtils.getPrincipal();
		Account account2 = accountService.findByUsername(username);
		model.addAttribute("account", account2);
		model.addAttribute("listOrder", listOrder);
		Account account = new Account();
		if(bill.getCustomerPhone() == null){
			account.setFullname("");
			account.setPhone("");
			account.setEmail("");
		}
		else {
			account = accountService.findByPhone(bill.getCustomerPhone());
		}
		System.out.println(account.getPhone());
		//Ten nhan vien
		Account account1 = accountService.findByUsername(bill.getCreatedBy());
		model.addAttribute("account1", account1);
		model.addAttribute("account2", account);
		mav.addObject("bill", bill);
		return mav;
	}
	@RequestMapping("/incomeform")
	public String viewIncome(Model model) {
		return "redirect:/admin/statistic/revenueByCategory";
		}

	@RequestMapping("/admin/statistic/revenueByMonth")
	public String viewStatisticRevenueByMonth(Model model) {
		ProductByCount productByCount = new ProductByCount();
		model.addAttribute("bill", productByCount);
		List<Object[]> listProducts = service.calculateRevenueByMonth();
		List<ProductByCount> list = new ArrayList<ProductByCount>();
		int num = listProducts.size();
		ProductByCount arr[] = new ProductByCount[num];
		int i = 0;
		for (Object[] a : listProducts) {
			arr[i] = new ProductByCount();
			arr[i].setText(a[7].toString());
			arr[i].setCount(Float.parseFloat(a[8].toString()));
			list.add(arr[i]);
			i++;
		}
		model.addAttribute("list", list);
		return "statistic-revenue-by-month";
	}

	@RequestMapping("/admin/statistic/revenueByYear")
	public String viewStatisticRevenueByYear(Model model) {
		ProductByCount productByCount = new ProductByCount();
		model.addAttribute("bill", productByCount);
		List<Object[]> listProducts = service.calculateRevenueByYear();
		List<ProductByCount> list = new ArrayList<ProductByCount>();
		int num = listProducts.size();
		ProductByCount arr[] = new ProductByCount[num];
		int i = 0;
		for (Object[] a : listProducts) {
			arr[i] = new ProductByCount();
			arr[i].setText(a[7].toString());
			arr[i].setCount(Float.parseFloat(a[8].toString()));
			list.add(arr[i]);
			i++;
		}
		model.addAttribute("list", list);
		return "statistic-revenue-by-year";
	}

	@RequestMapping("/admin/statistic/revenueByProduct")
	public String viewStatisticRevenueByProduct(Model model) {
		ProductByCount productByCount = new ProductByCount();
		model.addAttribute("bill", productByCount);
		List<Object[]> listProducts = service.calculateRevenueByProduct();
		List<ProductByCount> list = new ArrayList<ProductByCount>();
		int num = listProducts.size();
		ProductByCount arr[] = new ProductByCount[num];
		int i = 0;
		for (Object[] a : listProducts) {
			arr[i] = new ProductByCount();
			arr[i].setText(a[8].toString());
			arr[i].setCount(Float.parseFloat(a[9].toString()));
			list.add(arr[i]);
			i++;
		}
		model.addAttribute("list", list);
		return "statistic-revenue-by-product";
	}

	@RequestMapping("/admin/statistic/revenueByProduct/{month}/{year}")
	public String viewStatisticRevenueByProductByPick(@PathVariable(name = "month") int month, @PathVariable(name = "year") int year,Model model) {
		ProductByCount productByCount = new ProductByCount();
		model.addAttribute("bill", productByCount);
		List<Object[]> listProducts = service.calculateRevenueByProductByPick(month,year);
		List<ProductByCount> list = new ArrayList<ProductByCount>();
		int num = listProducts.size();
		ProductByCount arr[] = new ProductByCount[num];
		int i = 0;
		for (Object[] a : listProducts) {
			arr[i] = new ProductByCount();
			arr[i].setText(a[8].toString());
			arr[i].setCount(Float.parseFloat(a[9].toString()));
			list.add(arr[i]);
			i++;
		}
		model.addAttribute("list", list);
		return "statistic-revenue-by-product-pick";
	}

	@RequestMapping("/admin/statistic/revenueByCategory")
	public String viewStatisticRevenueByCategory(Model model) {
		ProductByCount productByCount = new ProductByCount();
		model.addAttribute("bill", productByCount);
		List<Object[]> listProducts = service.calculateRevenueByCategory();
		List<ProductByCount> list = new ArrayList<ProductByCount>();
		int num = listProducts.size();
		ProductByCount arr[] = new ProductByCount[num];
		int i = 0;
		for (Object[] a : listProducts) {
			arr[i] = new ProductByCount();
			arr[i].setText(a[10].toString());
			arr[i].setCount(Float.parseFloat(a[11].toString()));
			list.add(arr[i]);
			i++;
		}
		model.addAttribute("list", list);
		return "statistic-revenue-by-category";
	}
	@RequestMapping("/admin/statistic/revenueByCategory/{month}/{year}")
	public String viewStatisticRevenueByCategoryByPick(@PathVariable(name = "month") int month, @PathVariable(name = "year") int year, Model model) {
		ProductByCount productByCount = new ProductByCount();
		model.addAttribute("bill", productByCount);
		List<Object[]> listProducts = service.calculateRevenueByCategoryByPick(month, year);
		List<ProductByCount> list = new ArrayList<ProductByCount>();
		int num = listProducts.size();
		ProductByCount arr[] = new ProductByCount[num];
		int i = 0;
		for (Object[] a : listProducts) {
			arr[i] = new ProductByCount();
			arr[i].setText(a[10].toString());
			arr[i].setCount(Float.parseFloat(a[11].toString()));
			list.add(arr[i]);
			i++;
		}
		model.addAttribute("list", list);
		return "statistic-revenue-by-category-pick";
	}
	@RequestMapping("/admin/statistic/revenueByStaff")
	public String viewStatisticRevenueByStaff(Model model) {
		ProductByCount productByCount = new ProductByCount();
		model.addAttribute("bill", productByCount);
		List<Object[]> listProducts = service.calculateRevenueByStaff();
		List<ProductByCount> list = new ArrayList<ProductByCount>();
		int num = listProducts.size();
		ProductByCount arr[] = new ProductByCount[num];
		int i = 0;
		for (Object[] a : listProducts) {
			arr[i] = new ProductByCount();
			arr[i].setText(a[1].toString());
			arr[i].setCount(Float.parseFloat(a[7].toString()));
			list.add(arr[i]);
			i++;
		}
		model.addAttribute("list", list);
		return "statistic-revenue-by-staff";
	}
	//Pick choice
	@RequestMapping("/admin/statistic/revenueByMonth/{year}")
	public String viewStatisticRevenueByMonthPickMonth(@PathVariable int year,Model model) {
		ProductByCount productByCount = new ProductByCount();
		model.addAttribute("bill", productByCount);
		List<Object[]> listProducts = service.calculateRevenueByMonthPickYear(year);
		List<ProductByCount> list = new ArrayList<ProductByCount>();
		int num = listProducts.size();
		ProductByCount arr[] = new ProductByCount[num];
		int i = 0;
		for (Object[] a : listProducts) {
			arr[i] = new ProductByCount();
			arr[i].setText(a[7].toString());
			arr[i].setCount(Float.parseFloat(a[8].toString()));
			list.add(arr[i]);
			i++;
		}
		model.addAttribute("list", list);
		return "statistic-revenue-by-month-pick";
	}
	@RequestMapping("/admin/statistic/revenueByStaff/{month}/{year}")
	public String viewStatisticRevenueByStaffPickDate(@PathVariable(name = "month") int month, @PathVariable(name = "year") int year,Model model) {
		ProductByCount productByCount = new ProductByCount();
		model.addAttribute("bill", productByCount);
		List<Object[]> listProducts = service.calculateRevenueByStaffPickDate(month, year);
		List<ProductByCount> list = new ArrayList<ProductByCount>();
		int num = listProducts.size();
		ProductByCount arr[] = new ProductByCount[num];
		int i = 0;
		for (Object[] a : listProducts) {
			arr[i] = new ProductByCount();
			arr[i].setText(a[1].toString());
			arr[i].setCount(Float.parseFloat(a[7].toString()));
			list.add(arr[i]);
			i++;
		}
		model.addAttribute("list", list);
		return "statistic-revenue-by-staff-pick";
	}
	@GetMapping("/historyOrder")
	public ModelAndView historyOrder(Model model) {
		ModelAndView mav = new ModelAndView("historyOrder");
		SecurityUtils securityUtils = new SecurityUtils();
		String username = securityUtils.getPrincipal();
		Account account2 = accountService.findByUsername(username);
		mav.addObject("account", account2);
		Bill bill = new Bill();
		model.addAttribute("bill", bill);
		List<Bill> listBills = service.findByCustomer(account2.getPhone());
		model.addAttribute("listBills", listBills);
		return mav;
	}
	@RequestMapping("/viewBill/{id}")
	public ModelAndView viewBillByCustomer(@PathVariable(name = "id") int id, Model model) {
		ModelAndView mav = new ModelAndView("historyOrderDetails");
		SecurityUtils securityUtils = new SecurityUtils();
		String username = securityUtils.getPrincipal();
		Account account2 = accountService.findByUsername(username);
		model.addAttribute("account", account2);
		Bill bill = service.get(id);
		List<Order> listOrder = orderService.findOrderByBill(bill.getId());
		//Ten khach hang
		Account account = new Account();
		if(bill.getCustomerPhone() == null){
			account.setFullname("");
			account.setPhone("");
			account.setEmail("");
		}
		else {
			account = accountService.findByPhone(bill.getCustomerPhone());
		}
		//Ten nhan vien
		List<Account> account1 = accountService.findUsername(bill.getCreatedBy());
		model.addAttribute("account1", account1);
		model.addAttribute("account2", account);
		model.addAttribute("listOrder", listOrder);
		mav.addObject("bill", bill);
		return mav;
	}

}
