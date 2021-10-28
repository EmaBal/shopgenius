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
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

	@NotNull
	@Size(min=1, max=30, message="Required field")
    @Column(name = "first_name")
    private String firstName;

	@NotNull
	@Size(min=1, max=30, message="Required field")
    @Column(name = "last_name")
    private String lastName;

	@NotNull
	@Size(min=1, max=30, message="Required field")
    @Column(name = "email")
    private String email;
    
	@NotNull
	@Size(min=1, max=30, message="Required field")
    @Column(name = "password", nullable = false)
    private String password;
    
    @Column(name = "ENABLED", nullable = false)
    private boolean enabled;

    public User() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() {
  	  return this.password;
    }
    
    public void setPassword(String password) {
  	  this.password = password;
    }
    
    public boolean isEnabled() {
  	  return this.enabled;
    }
    
    public void setEnabled(boolean enabled) {
  	  this.enabled = enabled;
    } 

    @Override
    public String toString() {
        return "Customer [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", password= "+ password +"]";
    }
    
    @ManyToMany
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
    @ManyToMany(fetch = FetchType.LAZY,         cascade =
        {
                CascadeType.DETACH,
                CascadeType.MERGE,
                CascadeType.REFRESH,
                CascadeType.PERSIST
        })
    @JoinTable(name = "fav_list", joinColumns = @JoinColumn(name = "id"), inverseJoinColumns = @JoinColumn(name = "product_id"))
    private Set<Product> products = new HashSet<Product>();
    
    public Set<Product> getProducts() {
    	return this.products;
    }
    
    public void setProducts(Set<Product> products) {
    	this.products = products;
    }
    
	public void addProduct(Product p) {
		this.products.add(p);

	}
    
	public void removeProduct(Product p) {
		this.products.remove(p);
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, enabled, firstName, id, lastName, password, products, roles);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(email, other.email) && enabled == other.enabled
				&& Objects.equals(firstName, other.firstName) && id == other.id
				&& Objects.equals(lastName, other.lastName) && Objects.equals(password, other.password)
				&& Objects.equals(products, other.products) && Objects.equals(roles, other.roles);
	}

	public void addRole(Role role) {
		System.out.println(role);
		this.roles.add(role);
	}
}
