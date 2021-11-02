package it.univpm.shopgenius.model.entities;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(name = "products")
public class Product {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private int id;
	
	@NotNull
	@Size(min=1, max=30, message="Invalid character number (must be between 1 and 30)")
	@Column(name = "name", nullable = false, unique=true)
	private String name;
	
	@NotNull
	@Min(value=0, message="Value must be 0 or positive")
	@Column(name = "price", nullable = false)
	private float price;
	
	@NotNull
	@Min(value=0, message="Value must be 0 or positive")
	@Column(name = "quantity", nullable = false)
	private int quantity;
	
	@ManyToOne
	@JoinColumn(name = "product_type")
	private ProductType productType;
	
	@NotNull
	@Size(min=1, max=10, message="Invalid character number (must be between 1 and 30)")
	@Column(name = "location_detail", nullable = false)
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

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getLocationDetail() {
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
	
	@ManyToMany (fetch = FetchType.LAZY, cascade =
        {
                CascadeType.DETACH,
                CascadeType.MERGE,
                CascadeType.REFRESH,
                CascadeType.PERSIST
        } ,mappedBy = "products")
	Set<User> users = new HashSet<User>();
	
	public Set<User> getUsers() {
		return this.users;
	}
	
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	
//	public void addUser(User u) {
//		this.users.add(u);
//		u.getProducts().add(this);
//	}
//	
//	public void removeUser(User u) {
//		this.users.remove(u);
//		u.getProducts().remove(this);
//	}
	
	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", price=" + price + ", quantity=" + quantity + ", productType="
				+ productType + ", locationDetail=" + locationDetail + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, locationDetail, name, price, productType, quantity);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		return id == other.id && Objects.equals(locationDetail, other.locationDetail)
				&& Objects.equals(name, other.name) && Float.floatToIntBits(price) == Float.floatToIntBits(other.price)
				&& Objects.equals(productType, other.productType) && quantity == other.quantity;
	}

}
