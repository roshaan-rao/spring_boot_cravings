package com.rolixtech.cravings.module.users.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rolixtech.cravings.module.users.dao.CommonRoleDao;
import com.rolixtech.cravings.module.users.dao.CommonUsersAddressDao;
import com.rolixtech.cravings.module.users.dao.CommonUsersDao;
import com.rolixtech.cravings.module.users.models.CommonRole;
import com.rolixtech.cravings.module.users.models.CommonUsers;
import com.rolixtech.cravings.module.users.models.CommonUsersAddress;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class CommonUsersService {
	
	 @Autowired
	 private PasswordEncoder bcryptEncoder;
	 
	@Autowired
	private CommonUsersDao UsersDao;
	
	@Autowired
	private CommonRoleDao RoleDao;
	
	@Autowired
	private CommonUsersAddressDao UsersAddressDao;
	
	public void registerNewUserAccount(String firstName,String lastName,String email,String mobile,String password,String completeAddress,
			long countryId,long provinceId,long cityId,String postalCode,double lat,double lng,double accuracy) throws Exception {
		 
	    if (UsersDao.existsByEmail(email)) {
	        throw new Exception
	          ("There is an account with same email adress: " + email);
	    }
	    CommonUsers user = new CommonUsers();
	    user.setFirstName(firstName);
	    user.setLastName(lastName);
	    user.setPassword(bcryptEncoder.encode(password));
	    user.setEmail(email);;
	    user.setMobile(mobile);
	    user.setCategoryId(1);
	    Set<CommonRole> role = new HashSet<CommonRole> ();
	    role.add(RoleDao.findByName("USER"));
	    user.setRoles(role);
	    UsersDao.save(user);
	    List<CommonUsersAddress> userAddress=new ArrayList<CommonUsersAddress>();
	   
	    CommonUsersAddress addres=new CommonUsersAddress(user.getId(), countryId, provinceId, cityId, completeAddress, postalCode, provinceId, cityId, accuracy);
	    userAddress.add(addres);
	    user.setAddresses(userAddress);	
	   
	    UsersDao.save(user);
	    
	}

	public List<Map> findAllUsersAccounts() {
		List purposeList=new ArrayList<>();
		List<CommonUsers> allusers=UsersDao.findAll();
		if(!allusers.isEmpty()) {
			
			for(int i=0;i<allusers.size();i++) {
				Map Row=new HashMap();
				Row.put("id",allusers.get(i).getId());
				Row.put("firstName",allusers.get(i).getFirstName());
				Row.put("lastName",allusers.get(i).getLastName());
				Row.put("email",allusers.get(i).getEmail());
				Row.put("mobile",allusers.get(i).getMobile());
				Row.put("categoryId",allusers.get(i).getCategoryId());
				Row.put("roles",allusers.get(i).getRoles());
				List<CommonUsersAddress> userAddress=UsersAddressDao.findByUserId(allusers.get(i).getId());
				if(!userAddress.isEmpty()) {
					Row.put("address",userAddress);
				}
				
				purposeList.add(Row);
				
			}
		}
		
		return purposeList;
	}
	
}
