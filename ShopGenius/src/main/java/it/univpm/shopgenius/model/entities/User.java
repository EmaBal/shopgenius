package it.univpm.shopgenius.model.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import it.univpm.shopgenius.model.entities.Role;

@Entity
@Table(name = "users")
public class User {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;
    
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
	
    @ManyToMany
    @JoinTable(name = "fav_list", joinColumns = @JoinColumn(name = "id"), inverseJoinColumns = @JoinColumn(name = "product_id"))
    private Set<Product> products;
}
