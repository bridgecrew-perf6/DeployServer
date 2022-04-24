package edu.fsoft.spring.repository;

import edu.fsoft.spring.model.Bill;
import edu.fsoft.spring.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BillRepository extends JpaRepository<Bill, Integer> {

    @Query(value = " SELECT * FROM bill where created_by = :name and is_cancel = false order by id desc", nativeQuery = true)
    List<Bill> listBillByStaff(@Param("name") String name);
	@Query(value = "select MAX(id) from bill", nativeQuery = true)
    int findBillByIdDesc();
    @Query(value = "SELECT bill.*, month(bill.created_date) as Month,sum(bill.total) as sum FROM bill where is_cancel = false and  year(bill.created_date) = Year(now()) and month(bill.created_date) between 1 and 12 group by Month(bill.created_date) order by month(bill.created_date) asc", nativeQuery = true)
    List<Object[]> calculateRevenueByMonth();
    @Query(value = "SELECT bill.*, Year(bill.created_date) as Year,sum(bill.total) as sum FROM bill where is_cancel = false group by Year(bill.created_date) order by Year(bill.created_date) asc", nativeQuery = true)
    List<Object[]> calculateRevenueByYear();
    @Query(value = "select bill.*,`order-details`.unit_price, product.product_name, product.price, sum(`order-details`.quantity*product.price) as sum from bill  inner join `order-details`  on bill.id = `order-details`.bill_id inner join product on product.id = `order-details`.product_id where is_cancel = false and month(bill.created_date) =  month(now()) - 1 and year(bill.created_date) = year(now()) group  by product.product_name", nativeQuery = true)
    List<Object[]> calculateRevenueByProduct();
    @Query(value = "select bill.*,`order-details`.unit_price , product.product_name, product.price, category.type , sum(`order-details`.quantity*product.price) as sum from bill  inner join `order-details`  on bill.id = `order-details`.bill_id inner join product on product.id = `order-details`.product_id inner join category on category.id = product.category_id  where is_cancel = false and month(bill.created_date) = month (now())-1 and year(bill.created_date) = year(now())  group  by category.type", nativeQuery = true)
    List<Object[]> calculateRevenueByCategory();
    @Query(value = "SELECT bill.*, month(bill.created_date) as Month,sum(bill.total) as sum FROM bill where is_cancel = false and  year(bill.created_date) = :year and month(bill.created_date) between 1 and 12 group by Month(bill.created_date) order by month(bill.created_date) asc", nativeQuery = true)
    List<Object[]> calculateRevenueByMonthPickYear(int year);
    @Query(value = "select *, sum(total) from bill where is_cancel = false and month(created_date) = month(now())-1 and year(created_date) = 2022 group by created_by ", nativeQuery = true)
    List<Object[]> calculateRevenueByStaff();
    @Query(value = "select bill.*,`order-details`.unit_price, product.product_name, product.price, sum(`order-details`.quantity*product.price) as sum from bill  inner join `order-details`  on bill.id = `order-details`.bill_id inner join product on product.id = `order-details`.product_id where is_cancel = false and month(bill.created_date) = :month and year(bill.created_date) = :year group  by product.product_name", nativeQuery = true)
    List<Object[]> calculateRevenueByProductByPick(int month, int year);
    @Query(value = "select bill.*,`order-details`.unit_price , product.product_name, product.price, category.type , sum(`order-details`.quantity*product.price) as sum from bill  inner join `order-details`  on bill.id = `order-details`.bill_id inner join product on product.id = `order-details`.product_id inner join category on category.id = product.category_id  where is_cancel = false and month(bill.created_date) = :month  and year(bill.created_date) = :year group  by category.type", nativeQuery = true)
    List<Object[]> calculateRevenueByCategoryByPick(int month, int year);
    @Query(value = "select *, sum(total) from bill where is_cancel = false and month(created_date) = :month and year(created_date) = :year group by created_by ", nativeQuery = true)
    List<Object[]> calculateRevenueByStaffPickDate(int month, int year);
    @Query(value="SELECT * FROM bill where is_cancel = false and customer_phone = :phone order by id desc", nativeQuery = true)
    List<Bill> findByCustomerPhone(String phone);
}
