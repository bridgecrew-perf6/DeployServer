package edu.fsoft.spring.repository;

import java.util.List;

import edu.fsoft.spring.formobj.ProductByCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.fsoft.spring.model.Category;
import edu.fsoft.spring.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
	@Query(value="Select * from product order by id desc", nativeQuery = true)
	List<Product> find12Product();
	@Query(value="select * from product where product.product_name like :name and is_deleted = 'false'", nativeQuery = true)
	List<Product> findByProductNameLike(String name);
	@Query(value="SELECT * FROM product where product.category_id = :id and product.is_deleted ='false';", nativeQuery = true)
	List<Product> findByCategory(@Param("id") int id);
	@Query(value="SELECT * FROM product where category_id = :id order by price desc limit 4;", nativeQuery = true)
	List<Product> findTop4Category(@Param("id") int id);
	@Query(value="SELECT *, count(id) as SoLuong FROM product group by category_id", nativeQuery = true)
	List<Product> findNumberOfProductByCategory();
	@Query(value = "SELECT p.* , count(p.id), c.type  FROM Product p inner join category c where p.category_id = c.id GROUP BY category_id", nativeQuery = true)
	List<Object[]> findProductByCountCategory();
	@Query(value = "select * from  product where product.expiration_date between :date1 and :date2 and is_deleted = 'false' ", nativeQuery = true)
	List<Product> findProductExpiration(String date1, String date2);

	@Query(value="SELECT * FROM product where product.quantity < 20 and product.is_deleted = 'false';", nativeQuery = true)
	List<Product> checkProductQuantity();


	@Query(value="SELECT * FROM product where product.is_deleted = 'false';", nativeQuery = true)
	List<Product> listAllProduct();
}

