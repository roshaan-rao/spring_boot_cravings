package com.rolixtech.cravings.module.users.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
public class CommonUsers {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String mobile;
	private long categoryId;
	private int isActive;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "common_user_role",
            joinColumns = {
            @JoinColumn(name = "user_id")},
            inverseJoinColumns = {
            @JoinColumn(name = "ROLE_ID") })
    private Set<CommonRole> roles; 
	
	
	@OneToMany(targetEntity = CommonUsersAddress.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id",referencedColumnName = "id") 
	private List<CommonUsersAddress> addresses; 

	public List<CommonUsersAddress> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<CommonUsersAddress> addresses) {
		this.addresses = addresses;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
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
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getMobile() {
		return mobile;
	}


	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	public long getCategoryId() {
		return categoryId;
	}


	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}


	public int getIsActive() {
		return isActive;
	}


	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}
	
	
	
  public Set<CommonRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<CommonRole> roles) {
        this.roles = roles;
    }




	@Override
	public String toString() {
		return "CommonUsers [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", password=" + password + ", mobile=" + mobile + ", categoryId=" + categoryId + ", isActive="
				+ isActive + ", roles=" + roles + "]";
	}


	public String getUsername() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
	
	
	
}
