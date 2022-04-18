package edu.fsoft.spring.interfaceService;

import java.util.List;

import edu.fsoft.spring.formobj.OrderFormObj;
import edu.fsoft.spring.model.Order;

public interface IOrderService {
	public void save(OrderFormObj form);
	public List<Order> findOrderByBill(int id);
}
