package edu.fsoft.spring.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "category")
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	@Column
	private String isDeleted;

	public String getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

	@Column(name = "type")
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
	private Set<Product> listProduct = new HashSet<>();

	public Set<Product> getListProduct() {
		return listProduct;
	}

	public void setListProduct(Set<Product> listProduct) {
		this.listProduct = listProduct;
	}
	
}
