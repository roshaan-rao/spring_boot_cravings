package com.rolixtech.cravings.module.users.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
public class CommonUsers {
	
	@Override
	public String toString() {
		return "CommonUsers [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", password=" + password + ", mobile=" + mobile + ", categoryId=" + categoryId + ", isDeleted="
				+ isDeleted + ", deletedOn=" + deletedOn + ", deletedBy=" + deletedBy + ", profileImgUrl="
				+ profileImgUrl + ", isActive=" + isActive + ", roles=" + roles + ", addresses=" + addresses + "]";
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String mobile;
	private long categoryId;
	private int isDeleted;
	private Date deletedOn;
	private Long deletedBy;
	private String profileImgUrl;
	
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
	  System.out.println("ROLE"+roles);
        return roles;
    }

    public void setRoles(Set<CommonRole> roles) {
        this.roles = roles;
    }



	public int getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Date getDeletedOn() {
		return deletedOn;
	}

	public void setDeletedOn(Date deletedOn) {
		this.deletedOn = deletedOn;
	}

	public long getDeletedBy() {
		return deletedBy;
	}

	public void setDeletedBy(long deletedBy) {
		this.deletedBy = deletedBy;
	}

	


	public CommonUsers(String firstName, String lastName, String email, String password, String mobile, long categoryId,
			int isDeleted, Date deletedOn, long deletedBy, int isActive, Set<CommonRole> roles,
			List<CommonUsersAddress> addresses) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.mobile = mobile;
		this.categoryId = categoryId;
		this.isDeleted = isDeleted;
		this.deletedOn = deletedOn;
		this.deletedBy = deletedBy;
		this.isActive = isActive;
		this.roles = roles;
		this.addresses = addresses;
	}

	public CommonUsers(long id, String firstName, String lastName, String email, String password, String mobile,
			long categoryId, int isDeleted, Date deletedOn, long deletedBy, int isActive, Set<CommonRole> roles,
			List<CommonUsersAddress> addresses) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.mobile = mobile;
		this.categoryId = categoryId;
		this.isDeleted = isDeleted;
		this.deletedOn = deletedOn;
		this.deletedBy = deletedBy;
		this.isActive = isActive;
		this.roles = roles;
		this.addresses = addresses;
	}
	
	
	

	public CommonUsers() {
		super();
	}

	public String getUsername() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getProfileImgUrl() {
		return profileImgUrl;
	}

	public void setProfileImgUrl(String profileImgUrl) {
		this.profileImgUrl = profileImgUrl;
	}

	public void setDeletedBy(Long deletedBy) {
		this.deletedBy = deletedBy;
	}

	

	
	
	
	
	
	
	
}
