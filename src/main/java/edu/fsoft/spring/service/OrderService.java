package edu.fsoft.spring.service;

import java.util.List;

import edu.fsoft.spring.model.Bill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.fsoft.spring.formobj.OrderFormObj;
import edu.fsoft.spring.interfaceService.IOrderService;
import edu.fsoft.spring.model.Order;
import edu.fsoft.spring.model.Product;
import edu.fsoft.spring.repository.OrderRepository;

@Service
public class OrderService implements IOrderService {

	@Autowired
	private OrderRepository repo;

	public void saveOrderDetails(OrderFormObj form) {
		Order order = null;
		Bill bill = new Bill();
		Product product = new Product();
		if (form.getId() > 0) {
			order = repo.findById(form.getId()).get();
			bill.setId((form.getId()));
			product.setId(form.getProduct());
			order.setBill(bill);
			order.setProduct(product);
			order.setQuantity(form.getQuantity());
			order.setUnitPrice(form.getUnitPrice());
		}else {
			order = new Order();
			bill.setId((form.getBill()));
			product.setId(form.getId());
			order.setBill(bill);
			order.setProduct(product);
			order.setQuantity(form.getQuantity());
			order.setUnitPrice(form.getUnitPrice());
		}
		repo.save(order);
	}
	public void save(OrderFormObj form) {
		Order order = null;
		Product product = new Product();
		Bill bill = new Bill();
		product.setId(form.getProduct());
		bill.setId(form.getBill());
		if (form.getId() > 0) {
			order = repo.findById(form.getId()).get();
			bill.setId(form.getBill());
			product.setId(form.getProduct());
			order.setBill(bill);
			order.setProduct(product);
			order.setQuantity(form.getQuantity());
			order.setUnitPrice(form.getUnitPrice());
		} else {
			order = new Order();
			bill.setId(form.getBill());
			product.setId(form.getProduct());
			order.setBill(bill);
			order.setProduct(product);
			order.setQuantity(form.getQuantity());
			order.setUnitPrice(form.getUnitPrice());
		}
		repo.save(order);
	}

	public Order get(int id) {
		return repo.findById(id).get();
	}
	public List<Order> findOrderByBill(int id){
		Bill bill = new Bill();	
		bill.setId(id);
		Order order = new Order();
		order.setBill(bill);
		return repo.findByBill(order.getBill());	
	}
}