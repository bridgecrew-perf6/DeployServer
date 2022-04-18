package edu.fsoft.spring.service;

import java.time.LocalDate;
import java.util.List;

import edu.fsoft.spring.model.Bill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import edu.fsoft.spring.interfaceService.IBillService;
import edu.fsoft.spring.repository.BillRepository;

@Service
public class BillService implements IBillService {

	@Autowired
	private BillRepository repo;
	
	public List<Bill> listAll() {
		return repo.findAll();
	}
	public List<Bill> ListBillByStaff(String name){
		System.out.println(name);
		return repo.listBillByStaff(name);
	}
	public void save(Bill bill) {
		LocalDate ld =  LocalDate.now();
		bill.setCreatedDate(ld);
		repo.save(bill);
	}
	public int findMaxIdBill() {
		 return repo.findBillByIdDesc();
	}
	public void update(Bill bill) {
		LocalDate ld =  LocalDate.now();
		Bill bill2= null;
		if (bill.getId()>0) {
			bill2 = repo.findById(bill.getId()).get();
			bill2.setCreatedDate(ld);
			bill2.setCreatedBy(bill.getCreatedBy());
		}
		else {
			bill2 = new Bill();
			bill2.setCreatedDate(ld);
			bill2.setCreatedBy(bill.getCreatedBy());
		}
		repo.save(bill);
	}
	public Bill get(int id) {
		return repo.findById(id).get();
	}

	public List<Object[]> calculateRevenueByMonth(){
		return  repo.calculateRevenueByMonth();
	}
	public List<Object[]> calculateRevenueByMonthPickYear(int year){
		return  repo.calculateRevenueByMonthPickYear(year);
	}
	public List<Object[]> calculateRevenueByYear(){
		return  repo.calculateRevenueByYear();
	}
	public List<Object[]> calculateRevenueByProduct(){
		return  repo.calculateRevenueByProduct();
	}
	public List<Object[]> calculateRevenueByProductByPick(int month, int year){
		return  repo.calculateRevenueByProductByPick(month, year);
	}
	public List<Object[]> calculateRevenueByCategory(){
		return  repo.calculateRevenueByCategory();
	}
	public List<Object[]> calculateRevenueByStaff(){
		return  repo.calculateRevenueByStaff();
	}
	public List<Object[]> calculateRevenueByCategoryByPick(int month, int year){
		return  repo.calculateRevenueByCategoryByPick(month, year);
	}
	public List<Object[]> calculateRevenueByStaffPickDate(int month, int year){
		return  repo.calculateRevenueByStaffPickDate(month, year);
	}

	public List<Bill> findByCustomer(String customer){
		return repo.findByCustomerPhone(customer);
	}



}