package it.univpm.shopgenius.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "products")
public class Product {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private int id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "price")
	private float price;
	
	@Column(name = "quantity")
	private int quantity;
	
	@ManyToOne
	@JoinColumn(name = "product_type", nullable=false)
	private ProductType productType;
	
	@Column(name = "location_detail")
	private String locationDetail;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getLocation_detail() {
		return locationDetail;
	}

	public void setLocationDetail(String locationDetail) {
		this.locationDetail = locationDetail;
	}
	

	public ProductType getProductType() {
		return this.productType;
	}
	
	public void setProductType(ProductType productType) {
		this.productType = productType;
	}

}
