package it.univpm.shopgenius.model.entities;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "product_types")
public class ProductType {

	@OneToMany(mappedBy="productType", cascade=CascadeType.ALL)
	private Set<Product> products = new HashSet<Product>();
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_type_id")
    private int id;
	
	@Column(name = "type_name")
	private String typeName;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	public Set<Product> getProducts() {
		return this.products;
	}
	
	@Override
	public String toString() {
		return "ProductType [id=" + id + ", typeName=" + typeName + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, typeName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductType other = (ProductType) obj;
		return id == other.id && Objects.equals(typeName, other.typeName);
	}
}