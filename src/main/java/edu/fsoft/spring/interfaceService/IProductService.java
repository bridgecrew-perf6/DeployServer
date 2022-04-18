package edu.fsoft.spring.interfaceService;

import java.text.ParseException;
import java.util.List;

import edu.fsoft.spring.formobj.ProductByCount;
import edu.fsoft.spring.formobj.ProductFormObj;
import edu.fsoft.spring.model.Product;
import org.springframework.data.repository.query.Param;

public interface IProductService {

	public List<Product> listAll();
	void save(ProductFormObj product) throws ParseException;
	public void delete(int id);
	public Product get(int id);
	public List<Product> findByProductName(String name);
	public List<Product> findByCategory(int category);
	public List<Product> findTop4Category(int id);
	public void getCategoryId(Product product);
	public List<Object[]> findProductByCountCategory();
	public List<Product> findProductExpiration(String date1, String date2);
	public List<Product> checkProductQuantity();
	public void deleteProduct(Product product);

}
