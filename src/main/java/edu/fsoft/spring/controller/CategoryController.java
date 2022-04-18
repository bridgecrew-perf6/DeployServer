package edu.fsoft.spring.controller;

import edu.fsoft.spring.SecurityUtils;
import edu.fsoft.spring.model.Account;
import edu.fsoft.spring.model.Category;
import edu.fsoft.spring.model.Feedback;
import edu.fsoft.spring.model.Product;
import edu.fsoft.spring.service.AccountService;
import edu.fsoft.spring.service.CategoryService;
import edu.fsoft.spring.service.FeedbackService;
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
public class CategoryController {
    @Autowired
    private CategoryService service;
    @Autowired
    private AccountService accountService;
    @RequestMapping(value = "/saveCategory", method = RequestMethod.POST)
    public String saveCategory(@ModelAttribute("category")Category category) {
        service.save(category);
        return "redirect:/admin/category";
    }
    @RequestMapping(value = "/saveCategoryByStaff", method = RequestMethod.POST)
    public String saveCategoryByStaff(@ModelAttribute("category")Category category) {
        service.save(category);
        return "redirect:/staff/category";
    }

    @RequestMapping("/newCategory")
    public String newCategory(Model model) {
        Category category = new Category();
        model.addAttribute("category", category);
        return "category-list";
    }

    //list category
    @RequestMapping("/admin/category")
    public String viewCategory(Model model) {
        Category category = new Category();
        model.addAttribute("category", category);
        List<Category> listCategory = service.listAll();
        model.addAttribute("listCategory", listCategory);
//		Product product = new Product();
//		model.addAttribute("product", product);
        return "category-list";
    }
    //Delete Category
    @RequestMapping("/deleteCategory/{id}")
    public String deleteCategory(@PathVariable(name = "id") int id) {
        Category category = service.get(id);
        service.deleteCategory(category);
        return "redirect:/admin/category";
    }
    @RequestMapping("/staff/deleteCategory/{id}")
    public String deleteCategoryByStaff(@PathVariable(name = "id") int id) {
        Category category = service.get(id);
        service.deleteCategory(category);
        return "redirect:/staff/category";
    }
    @RequestMapping("/editCategory/{id}")
    public ModelAndView editCategory(@PathVariable(name = "id") int id) {
        ModelAndView mav = new ModelAndView("editCategory");
        Category category = service.get(id);
        mav.addObject("category", category);
        return mav;
    }
    @RequestMapping("/category/{id}")
    public ModelAndView confirmCategory(@PathVariable(name = "id") int id) {
        ModelAndView mav = new ModelAndView("category-list");
        Category category = service.get(id);
        mav.addObject("category", category);
        return mav;
    }

//    //list in product
//    @RequestMapping("/admin/product")
//    public String pickCategory(Model model) {
//        Category category = new Category();
//        model.addAttribute("category", category);
//        List<Category> listCategory = service.listAll();
//        model.addAttribute("listCategory", listCategory);
////		Product product = new Product();
////		model.addAttribute("product", product);
//        return "product-list";
//    }
@RequestMapping("/staff/category")
public String manageCategoryByStaff(Model model) {
    SecurityUtils securityUtils = new SecurityUtils();
    String username = securityUtils.getPrincipal();
    Account account2 = accountService.findByUsername(username);
    model.addAttribute("account", account2);
    //listCategory
    Category category = new Category();
    model.addAttribute("category", category);
    List<Category> listCategory = service.listAll();
    model.addAttribute("listCategory", listCategory);

    return "category-list-staff";
}
    @RequestMapping("staff/editCategory/{id}")
    public ModelAndView editCategoryByStaff(@PathVariable(name = "id") int id) {
        SecurityUtils securityUtils = new SecurityUtils();
        String username = securityUtils.getPrincipal();
        Account account2 = accountService.findByUsername(username);
        ModelAndView mav = new ModelAndView("editCategoryByStaff");
        Category category = service.get(id);
        mav.addObject("category", category);
        mav.addObject("account", account2);
        return mav;
    }
}
