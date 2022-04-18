package edu.fsoft.spring.interfaceService;

import edu.fsoft.spring.model.Bill;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IBillService {
	 public void save(Bill bill);
	 public int findMaxIdBill();
	 public Bill get(int id);
	 public void update(Bill bill);
	 public List<Bill> listAll();
	 public  List<Object[]> calculateRevenueByMonth();
	 public List<Object[]> calculateRevenueByYear();
	 public List<Object[]> calculateRevenueByProduct();
	public List<Object[]> calculateRevenueByCategory();
	public List<Object[]> calculateRevenueByStaff();
	public List<Bill> ListBillByStaff(String name);
	public List<Object[]> calculateRevenueByMonthPickYear(int year);
	public List<Object[]> calculateRevenueByProductByPick(int month, int year);
	public List<Object[]> calculateRevenueByCategoryByPick(int month, int year);
	public List<Object[]> calculateRevenueByStaffPickDate(int month, int year);
	public List<Bill> findByCustomer(String customer);
}
