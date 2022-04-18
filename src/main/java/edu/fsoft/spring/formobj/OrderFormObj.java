package edu.fsoft.spring.formobj;

public class OrderFormObj {
	private int id;
	private int bill;
	private int product;
	private int quantity;
	private float unitPrice;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBill() {
		return bill;
	}

	public void setBill(int order_id) {
		this.bill = order_id;
	}

	public int getProduct() {
		return product;
	}

	public void setProduct(int product_id) {
		this.product = product_id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public float getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(float unitPrice) {
		this.unitPrice = unitPrice;
	}
}
