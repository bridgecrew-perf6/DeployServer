package edu.fsoft.spring.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import edu.fsoft.spring.formobj.ProductByCount;
import edu.fsoft.spring.model.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.fsoft.spring.formobj.ProductFormObj;
import edu.fsoft.spring.interfaceService.IProductService;
import edu.fsoft.spring.model.Category;
import edu.fsoft.spring.model.Product;
import edu.fsoft.spring.repository.ProductRepository;

import javax.mail.Session;

@Service
public class ProductService implements IProductService {

	@Autowired
	ProductRepository repo;
	
	public List<Product> listAll() {
		return repo.listAllProduct();
	}
	public List<Product> findTop4Category(int id) {
		return repo.findTop4Category(id);
	}
	public List<Object[]> findProductByCountCategory() {
		return repo.findProductByCountCategory();
	}
	public List<Product> list12() {
		return repo.find12Product();
	}
	
	public List<Product> findByProductName(String name){
		return repo.findByProductNameLike(name);
	}
	public List<Product> findProductExpiration(String date1, String date2){
	return repo.findProductExpiration(date1, date2);
	}
	public List<Product> checkProductQuantity(){
		return repo.checkProductQuantity();
	}
	public List<Product> findByCategory(int cate){
		return repo.findByCategory(cate);
	}
	public void getCategoryId(Product product){
		ProductFormObj form = new ProductFormObj();
		Category category = new Category();
		if (product.getId()>0){
			product = repo.findById(form.getId()).get();
			form.setCategory(category.getId());
			product.setCategory(category);
			System.out.println(form.getId());
		}
		else
		System.out.println(form.getId());

	}
	public void save(ProductFormObj form) throws ParseException {
		Product product = null;
		Category category = new Category();
		Supplier supplier = new Supplier();
		if (form.getId() > 0) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

			String a = form.getManufactureDate();
			String b = form.getExpirationDate();
			Date ngaySX = format.parse(a);
			Date ngayHD = format.parse(b);
			product = repo.findById(form.getId()).get();
			category.setId(form.getCategory());
			supplier.setId(form.getSupplier());
			product.setCategory(category);
			product.setSupplier(supplier);
			product.setDescription(form.getDescription());
			product.setPrice(form.getPrice());
			product.setProductName(form.getProductName());
			product.setImage(form.getImage());
			product.setQuantity(form.getQuantity());
			product.setIngredient(form.getIngredient());
			product.setProductCode(form.getProductCode());
			product.setIntruction(form.getIntruction());
			product.setPreservation(form.getPreservation());
			product.setIsDeleted("false");
			product.setExpirationDate(ngayHD);
			product.setManufactureDate(ngaySX);
			product.setOrigin(form.getOrigin());
			System.out.println(a);

		}else {
			product = new Product();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String a = form.getManufactureDate();
			String b = form.getExpirationDate();
			Date ngaySX = format.parse(a);
			Date ngayHD = format.parse(b);
			category.setId(form.getCategory());
			supplier.setId(form.getSupplier());
			product.setCategory(category);
			product.setSupplier(supplier);
			product.setDescription(form.getDescription());
			product.setPrice(form.getPrice());
			product.setProductName(form.getProductName());
			product.setImage(form.getImage());
			product.setQuantity(form.getQuantity());
			product.setIngredient(form.getIngredient());
			product.setProductCode(form.getProductCode());
			product.setIntruction(form.getIntruction());
			product.setPreservation(form.getPreservation());
			product.setIsDeleted("false");
			product.setExpirationDate(ngayHD);
			product.setManufactureDate(ngaySX);
			product.setOrigin(form.getOrigin());
		}
		repo.save(product);
	}
	public void deleteProduct(Product product) {
		if (product.getId() > 0) {
			product.setIsDeleted("true");

		}else {
			product.setIsDeleted("false");
		}
		repo.save(product);
	}
	public void delete(int id) {
		repo.deleteById(id);
	}
	public Product get(int id) {
		return repo.findById(id).get();
	}



}
