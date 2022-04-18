package edu.fsoft.spring.controller;

import edu.fsoft.spring.SecurityUtils;
import edu.fsoft.spring.model.Account;
import edu.fsoft.spring.model.Category;
import edu.fsoft.spring.model.Supplier;
import edu.fsoft.spring.service.AccountService;
import edu.fsoft.spring.service.CategoryService;
import edu.fsoft.spring.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class SupplierController {

    @Autowired
    private SupplierService service;
    @Autowired
    private AccountService accountService;
    @RequestMapping(value = "/saveSupplier", method = RequestMethod.POST)
    public String saveSupplier(@ModelAttribute("suppler") Supplier supplier) {
        service.save(supplier);
        return "redirect:/admin/supplier";
    }
    @RequestMapping(value = "/saveSupplierByStaff", method = RequestMethod.POST)
    public String saveSupplierByStaff(@ModelAttribute("suppler") Supplier supplier) {
        service.save(supplier);
        return "redirect:/staff/supplier";
    }
    @RequestMapping("/newSupplier")
    public String newSupplier(Model model) {
        Supplier supplier = new Supplier();
        model.addAttribute("supplier", supplier);
        return "supplier-list";
    }

    //list supplier
    @RequestMapping("/admin/supplier")
    public String viewSupplier(Model model) {
        Supplier supplier = new Supplier();
        model.addAttribute("supplier", supplier);
        List<Supplier> listSupplier = service.listAll();
        model.addAttribute("listSupllier", listSupplier);
//		Product product = new Product();
//		model.addAttribute("product", product);
        return "supplier-list";
    }
    @RequestMapping("/staff/supplier")
    public String viewSupplierByStaff(Model model) {
        Supplier supplier = new Supplier();
        model.addAttribute("supplier", supplier);
        List<Supplier> listSupplier = service.listAll();
        model.addAttribute("listSupllier", listSupplier);
        SecurityUtils securityUtils = new SecurityUtils();
        String username = securityUtils.getPrincipal();
        Account account2 = accountService.findByUsername(username);
        model.addAttribute("account", account2);
//		Product product = new Product();
//		model.addAttribute("product", product);
        return "supplier-list-staff";
    }
    //Delete Category
    @RequestMapping("/deleteSupplier/{id}")
    public String deleteSupplier(@PathVariable(name = "id") int id) {
        Supplier supplier = service.get(id);
        service.deleleSupplier(supplier);
        return "redirect:/admin/supplier";
    }
    @RequestMapping("/staff/deleteSupplier/{id}")
    public String deleteSupplierByStaff(@PathVariable(name = "id") int id) {
        Supplier supplier = service.get(id);
        service.deleleSupplier(supplier);
        return "redirect:/staff/supplier";
    }
    @RequestMapping("/editSupplier/{id}")
    public ModelAndView editSupplier(@PathVariable(name = "id") int id) {
        ModelAndView mav = new ModelAndView("editSupplier");
        Supplier supplier = service.get(id);
        mav.addObject("supplier", supplier);
        return mav;
    }
    @RequestMapping("/staff/editSupplier/{id}")
    public ModelAndView editSupplierByStaff(@PathVariable(name = "id") int id) {

        ModelAndView mav = new ModelAndView("editSupplierByStaff");
        SecurityUtils securityUtils = new SecurityUtils();
        String username = securityUtils.getPrincipal();
        Account account2 = accountService.findByUsername(username);
        mav.addObject("account", account2);
        Supplier supplier = service.get(id);
        mav.addObject("supplier", supplier);
        return mav;
    }
}
