package edu.fsoft.spring.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import edu.fsoft.spring.SecurityUtils;
import edu.fsoft.spring.formobj.ProductByCount;
import edu.fsoft.spring.interfaceService.*;
import edu.fsoft.spring.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.fsoft.spring.formobj.ProductFormObj;

@Controller
public class ProductController {

	@Autowired 
	private IProductService service;
	
	@Autowired 
	private IBillService billService;
	
	@Autowired
	private IOrderService orderService;
	@Autowired
	private ICategoryService categoryService;

	@Autowired
	private ISupplierRepository supplierService;

	@Autowired
	private IAccountService accountService;




	@RequestMapping("/newProduct")
	public String showNewProductForm(Model model) {
		Product product = new Product();
		model.addAttribute("product", product);
		return "newProduct";
	}
	@RequestMapping(value = "/saveNewProduct", method = RequestMethod.POST)
	public String saveProduct(@ModelAttribute("product") ProductFormObj product) throws ParseException {
		service.save(product);
		return "redirect:/admin/product";
	}

	//listProduct
	@RequestMapping("/admin/product")
	public String viewHomePage(Model model) {
		Product product = new Product();
		model.addAttribute("product", product);
		List<Product> listProducts = service.listAll();
		model.addAttribute("listProducts", listProducts);

		//listCategory
		Category category = new Category();
		model.addAttribute("category", category);
		List<Category> listCategory = categoryService.listAll();
		model.addAttribute("listCategory", listCategory);

		//listSupplier
		Supplier supplier = new Supplier();
		model.addAttribute("supplier", supplier);
		List<Supplier> listSupplier = supplierService.listAll();
		model.addAttribute("listSupllier", listSupplier);

//		Product product = new Product();
//		model.addAttribute("product", product);
		return "product-list";
	}
	//Delete Product
	@RequestMapping("/deleteProduct/{id}")
	public String deleteProduct(@PathVariable(name = "id") int id) {
		Product product = service.get(id);
		service.deleteProduct(product);
		return "redirect:/admin/product";
	}
	@RequestMapping("/staff/deleteProduct/{id}")
	public String deleteProductByStaff(@PathVariable(name = "id") int id) {
		Product product = service.get(id);
		service.deleteProduct(product);
		return "redirect:/staff/product";
	}
	//Edit Product
	@RequestMapping("/editProduct/{id}")
	public ModelAndView showEditProductForm(@PathVariable(name = "id") int id, Model model) {
		ModelAndView mav = new ModelAndView("editProduct");
		Product product = service.get(id);
		mav.addObject("product", product);
		Category category = new Category();
		model.addAttribute("category", category);
		List<Category> listCategory = categoryService.listAll();
		model.addAttribute("listCategory", listCategory);

		//listSupplier
		Supplier supplier = new Supplier();
		model.addAttribute("supplier", supplier);
		List<Supplier> listSupplier = supplierService.listAll();
		model.addAttribute("listSupllier", listSupplier);
		return mav;
	}
	@RequestMapping("/staff/editProduct/{id}")
	public ModelAndView showEditProductByStaff(@PathVariable(name = "id") int id, Model model) {
		ModelAndView mav = new ModelAndView("editProductByStaff");
		SecurityUtils securityUtils = new SecurityUtils();
		String username = securityUtils.getPrincipal();
		Account account2 = accountService.findByUsername(username);
		Product product = service.get(id);
		mav.addObject("product", product);
		mav.addObject("account", account2);
		Category category = new Category();
		model.addAttribute("category", category);
		List<Category> listCategory = categoryService.listAll();
		model.addAttribute("listCategory", listCategory);
		//listSupplier
		Supplier supplier = new Supplier();
		model.addAttribute("supplier", supplier);
		List<Supplier> listSupplier = supplierService.listAll();
		model.addAttribute("listSupllier", listSupplier);
		return mav;
	}
	@RequestMapping("/product/{id}")
	public ModelAndView showProductDetail(@PathVariable(name = "id") int id, Model model) {
		ModelAndView mav = new ModelAndView("product-detail");
		Product product = service.get(id);
		mav.addObject("product", product);
//List Top4 by category
		Category category = product.getCategory();
		Product product2 = new Product();
		model.addAttribute("product2", product2);
		List<Product> listProducts = service.findTop4Category(category.getId());
		model.addAttribute("listProducts", listProducts);
		return mav;

	}
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveProduct1(@ModelAttribute("product") ProductFormObj product) throws ParseException {
		service.save(product);
		return "redirect:/admin/product";
	}
	@RequestMapping(value = "/saveProductByStaff", method = RequestMethod.POST)
	public String saveProductByStaff(@ModelAttribute("product") ProductFormObj product) throws ParseException {
		service.save(product);
		return "redirect:/staff/product";
	}
	
	@RequestMapping("/staff/order")
	public String viewHomePageUser(Model model) {
		Bill bill = billService.get(billService.findMaxIdBill());
		List<Product> listDrinks = service.findByCategory(1);
		model.addAttribute("listDrinks", listDrinks);
		List<Product> listFoods = service.findByCategory(2);
		model.addAttribute("listFoods", listFoods);
		System.out.println(billService.findMaxIdBill());
		List<Order> listOrder = orderService.findOrderByBill(billService.findMaxIdBill());
		model.addAttribute("listOrder", listOrder);
		model.addAttribute("bill", bill);
		Bill bill3 = billService.get(billService.findMaxIdBill());
		model.addAttribute("listBill", bill3);	
		List<Order> listOrder2 = orderService.findOrderByBill(billService.findMaxIdBill());
		model.addAttribute("listOrder2", listOrder2);
		return "order";
	}
	
	@GetMapping("/staff/order/search")
	public String searchNameUser(@RequestParam("productName") String productName, Model model) {
		if (productName.equals("")) {
			return "redirect:/staff/order";
		}
		model.addAttribute("listProducts", service.findByProductName("%"+productName+"%"));
		return "product-detail";
	}
	@GetMapping("/search")
	public String searchNameProduct(@RequestParam("productName") String productName, Model model) {
		if (productName.equals("")) {
			return "redirect:/home";
		}
		model.addAttribute("listProducts", service.findByProductName("%"+productName+"%"));
		return "listProductSearch";
	}

	@GetMapping("/searchByStaff")
	public ModelAndView searchNameProductByStaff(@RequestParam("productName") String productName, Model model) {
		ModelAndView mav = new ModelAndView("listProductSearchByStaff");
		SecurityUtils securityUtils = new SecurityUtils();
		String username = securityUtils.getPrincipal();
		Account account2 = accountService.findByUsername(username);
		mav.addObject("account", account2);
		model.addAttribute("listProducts", service.findByProductName("%"+productName+"%"));
		return mav;
	}

	//SearchByAdmin
	@GetMapping("/admin/product/search")
	public String searchNameProductByAdmin(@RequestParam("productName") String productName, Model model) {
		Product product = new Product();
		model.addAttribute("product", product);
		List<Product> listProducts = service.listAll();
		model.addAttribute("listProducts", listProducts);

		//listCategory
		Category category = new Category();
		model.addAttribute("category", category);
		List<Category> listCategory = categoryService.listAll();
		model.addAttribute("listCategory", listCategory);

		//listSupplier
		Supplier supplier = new Supplier();
		model.addAttribute("supplier", supplier);
		List<Supplier> listSupplier = supplierService.listAll();
		model.addAttribute("listSupllier", listSupplier);
		if (productName.equals("")) {
			return "redirect:/admin/product";
		}
		model.addAttribute("listProductSearch", service.findByProductName("%"+productName+"%"));
		return "product-list-search";
	}


	@RequestMapping("/admin/statistic/productByCategory")
	public String viewStatisticProductByCategory(Model model) {
		ProductByCount productByCount = new ProductByCount();
		model.addAttribute("product", productByCount);
		List<Object[]> listProducts = service.findProductByCountCategory();
		List<ProductByCount> list = new ArrayList<ProductByCount>();
		int num = listProducts.size();
		ProductByCount arr[] = new ProductByCount[num];
		int i = 0;
		for (Object[] a : listProducts) {
			arr[i] = new ProductByCount();
					arr[i].setText(a[16].toString());
					arr[i].setCount(Float.parseFloat(a[15].toString()));
					list.add(arr[i]);
					i++;
		}
		model.addAttribute("listProducts", list);
		return "statistic-product-by-category";
	}
	@GetMapping("/product/{date1}/{date2}")
	public ModelAndView showProductExpiration(@PathVariable(name = "date1") String date1, @PathVariable(name = "date2") String date2, Model model) {
		ModelAndView mav = new ModelAndView("list-product-expiration");
		Product product = new Product();
		List<Product> list = service.findProductExpiration(date1, date2);
		model.addAttribute("list", list);
		mav.addObject("product", product);
		return mav;

	}
	@RequestMapping("/admin/checkProductExpiry")
	public String viewCheck(Model model) {
		Product product = new Product();
		model.addAttribute("product", product);
		List<Product> listProducts = service.listAll();
		model.addAttribute("listProducts", listProducts);
		return "check-product-expiry";
	}

	@RequestMapping("/admin/checkProductQuantity")
	public String viewCheckQuantityProduct(Model model) {
		Product product = new Product();
		model.addAttribute("product", product);
		List<Product> listProducts = service.checkProductQuantity();
		model.addAttribute("list", listProducts);
		return "list-product-quantity";
	}
	@RequestMapping("/staff/product")
	public String manageProductByStaff(Model model) {
		Product product = new Product();
		model.addAttribute("product", product);
		List<Product> listProducts = service.listAll();
		model.addAttribute("listProducts", listProducts);
		SecurityUtils securityUtils = new SecurityUtils();
		String username = securityUtils.getPrincipal();
		Account account2 = accountService.findByUsername(username);
		model.addAttribute("account", account2);
		//listCategory
		Category category = new Category();
		model.addAttribute("category", category);
		List<Category> listCategory = categoryService.listAll();
		model.addAttribute("listCategory", listCategory);

		//listSupplier
		Supplier supplier = new Supplier();
		model.addAttribute("supplier", supplier);
		List<Supplier> listSupplier = supplierService.listAll();
		model.addAttribute("listSupllier", listSupplier);

//		Product product = new Product();
//		model.addAttribute("product", product);
		return "product-list-staff";
	}
}
