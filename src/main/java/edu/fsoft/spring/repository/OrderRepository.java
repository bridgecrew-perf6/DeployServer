package edu.fsoft.spring.repository;

import java.util.List;

import edu.fsoft.spring.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

import edu.fsoft.spring.model.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {
	List<Order> findByBill(Bill bill);
}
