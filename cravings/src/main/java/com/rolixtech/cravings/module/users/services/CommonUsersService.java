package com.rolixtech.cravings.module.users.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;



import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.rolixtech.cravings.module.cravings.model.CravingsPromotionalVouchers;
import com.rolixtech.cravings.module.cravings.model.CravingsPromotionalVouchersChangeLog;
import com.rolixtech.cravings.module.generic.dao.CommonSmsLogDao;
import com.rolixtech.cravings.module.generic.model.CommonProvinces;
import com.rolixtech.cravings.module.generic.model.CommonSmsLog;
import com.rolixtech.cravings.module.generic.services.CommonCitiesService;
import com.rolixtech.cravings.module.generic.services.CommonCountriesService;
import com.rolixtech.cravings.module.generic.services.CommonProvincesService;
import com.rolixtech.cravings.module.generic.services.GenericUtility;
import com.rolixtech.cravings.module.resturant.services.CommonResturantsService;
import com.rolixtech.cravings.module.resturant.services.CommonUsersResturantsService;
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
	
	@Autowired
	private GenericUtility Utility;
	
	@Autowired
	private CommonSmsLogDao SmsLogDao;
	
	@Autowired
	private CommonUsersResturantsService UsersResturantsService;
	
	@Autowired
	private CommonCitiesService CitiesService;
	@Autowired
	private CommonProvincesService ProvincesService;
	@Autowired
	private CommonCountriesService CountriesService;
	
	@Autowired
	private CommonUsersFeaturesService UsersFeaturesService;
	
	
	@Transactional
	public void registerNewInternalWebAccount(long recordId, String firstName,String lastName,String email,String mobile,String password,String completeAddress,
			long countryId,long provinceId,long cityId,String postalCode,MultipartFile profileImg,long roleId) throws Exception {
		
		if(recordId==0) {
		    if (UsersDao.existsByEmailAndIsDeleted(email,0)) {
		        throw new Exception
		          ("There is an account with same email adress: " + email);
		    }
		    
		    if (UsersDao.existsByMobileAndIsDeleted(mobile,0)) {
		        throw new Exception
		          ("There is an account with same mobile: " + mobile);
		    }
		}
		
	    CommonUsers user = new CommonUsers();
	    String TargetFileName = "";
	    if(recordId!=0) {
	    	
	    	
	    	/**
	    	 * To check around other records other than this recordID
	    	 * */
	    	if (UsersDao.existsByEmailAndIsDeletedAndIdNot(email,0,recordId)) {
		        throw new Exception
		          ("There is an account with same email adress: " + email);
		    }
		    
		    if (UsersDao.existsByMobileAndIsDeletedAndIdNot(mobile,0,recordId)) {
		        throw new Exception
		          ("There is an account with same mobile: " + mobile);
		    }
	    	
	    	
	    	user = UsersDao.findById(recordId);
	    	if (profileImg != null ) {
	    		if (!profileImg.getOriginalFilename().equals("")) {
				
					String OldFile=Utility.getSecureFileDirectoryPath()+File.separator+"common"+File.separator+user.getProfileImgUrl();
					File f = new File(OldFile); 
					
					if(f.delete()) {
						System.out.println("File Delteted");
					}else {
						System.out.println("File Could not be Delteted");
					}
					
					 TargetFileName = "profile_Img_"+"user_"+user.getId()+"_"+ Utility.getUniqueId()
							
							+ profileImg.getOriginalFilename().substring(profileImg.getOriginalFilename().lastIndexOf("."));
					Path copyLocation = Paths.get(StringUtils.cleanPath(Utility.getSecureFileDirectoryPath()+File.separator+"common"+File.separator+TargetFileName));
					Files.copy(profileImg.getInputStream(), copyLocation);
					
					user.setProfileImgUrl(TargetFileName);
	    		} 
//	    		else {
//					throw new Exception("Please select a valid file");
//				}
				
	    		
	    	}
		    
	    }
	    
	    user.setFirstName(firstName);
	    user.setLastName(lastName);
	    if(recordId!=0) {
	    	if(!password.equals("")) {
	    		user.setPassword(bcryptEncoder.encode(password));
	    	}
	    	
	    }else {
	    	user.setPassword(bcryptEncoder.encode(password));
	    }
	    
	    user.setEmail(email);;
	    user.setMobile(mobile);
	    user.setCategoryId(1);
	    user.setIsActive(1);
	    Set<CommonRole> role = new HashSet<CommonRole> ();
	    CommonRole roleObj=RoleDao.findById(roleId);
	    role.add(roleObj);
	    user.setRoles(role);
	    UsersDao.save(user);
	    
	    if(recordId==0) {
	    	 if (profileImg != null && !profileImg.getOriginalFilename().equals("") ) {
	 			
				 TargetFileName = "profile_Img_"+"user_"+user.getId()+"_"+ Utility.getUniqueId()
						
						+ profileImg.getOriginalFilename().substring(profileImg.getOriginalFilename().lastIndexOf("."));
				Path copyLocation = Paths.get(StringUtils.cleanPath(Utility.getSecureFileDirectoryPath()+File.separator+"common"+File.separator+TargetFileName));
				Files.copy(profileImg.getInputStream(), copyLocation);
				
				user.setProfileImgUrl(TargetFileName);
				
			} 
//	    	 else {
//				throw new Exception("Please select a valid file");
//			}
	    }
	   
	    
		
	    List<CommonUsersAddress> userAddress=new ArrayList<CommonUsersAddress>();
	   
	    CommonUsersAddress addres=new CommonUsersAddress(user.getId(),countryId,provinceId, cityId ,completeAddress,postalCode,0.0,0.0,0.0);
	    userAddress.add(addres);
	    user.setAddresses(userAddress);	
	   
	    UsersDao.save(user);
	    
	}
	
	@Transactional
	public void registerNewCustomerWebAccount(long recordId, String firstName,String lastName,String email,String mobile,String password,String completeAddress,
			long countryId,long provinceId,long cityId,String postalCode,MultipartFile profileImg) throws Exception {
		
		if(recordId==0) {
		    if (UsersDao.existsByEmailAndIsDeleted(email,0)) {
		        throw new Exception
		          ("There is an account with same email adress: " + email);
		    }
		    
		    if (UsersDao.existsByMobileAndIsDeleted(mobile,0)) {
		        throw new Exception
		          ("There is an account with same mobile: " + mobile);
		    }
		}
		
	    CommonUsers user = new CommonUsers();
	    String TargetFileName = "";
	    if(recordId!=0) {
	    	
	    	
	    	/**
	    	 * To check around other records other than this recordID
	    	 * */
	    	if (UsersDao.existsByEmailAndIsDeletedAndIdNot(email,0,recordId)) {
		        throw new Exception
		          ("There is an account with same email adress: " + email);
		    }
		    
		    if (UsersDao.existsByMobileAndIsDeletedAndIdNot(mobile,0,recordId)) {
		        throw new Exception
		          ("There is an account with same mobile: " + mobile);
		    }
	    	
	    	
	    	user = UsersDao.findById(recordId);
	    	if (profileImg != null ) {
	    		if (!profileImg.getOriginalFilename().equals("")) {
				
					String OldFile=Utility.getSecureFileDirectoryPath()+File.separator+"common"+File.separator+user.getProfileImgUrl();
					File f = new File(OldFile); 
					
					if(f.delete()) {
						System.out.println("File Delteted");
					}else {
						System.out.println("File Could not be Delteted");
					}
					
					 TargetFileName = "profile_Img_"+"user_"+user.getId()+"_"+ Utility.getUniqueId()
							
							+ profileImg.getOriginalFilename().substring(profileImg.getOriginalFilename().lastIndexOf("."));
					Path copyLocation = Paths.get(StringUtils.cleanPath(Utility.getSecureFileDirectoryPath()+File.separator+"common"+File.separator+TargetFileName));
					Files.copy(profileImg.getInputStream(), copyLocation);
					
					user.setProfileImgUrl(TargetFileName);
	    		} 
//	    		else {
//					throw new Exception("Please select a valid file");
//				}
				
	    		
	    	}
		    
	    }
	    
	    user.setFirstName(firstName);
	    user.setLastName(lastName);
	    if(recordId!=0) {
	    	if(!password.equals("")) {
	    		user.setPassword(bcryptEncoder.encode(password));
	    	}
	    	
	    }else {
	    	user.setPassword(bcryptEncoder.encode(password));
	    }
	    
	    user.setEmail(email);;
	    user.setMobile(mobile);
	    user.setCategoryId(1);
	    user.setIsActive(1);
	    Set<CommonRole> role = new HashSet<CommonRole> ();
	    CommonRole roleObj=RoleDao.findById(3l);
	    role.add(roleObj);
	    user.setRoles(role);
	    UsersDao.save(user);
	    
	    if(recordId==0) {
	    	 if (profileImg != null && !profileImg.getOriginalFilename().equals("") ) {
	 			
				 TargetFileName = "profile_Img_"+"user_"+user.getId()+"_"+ Utility.getUniqueId()
						
						+ profileImg.getOriginalFilename().substring(profileImg.getOriginalFilename().lastIndexOf("."));
				Path copyLocation = Paths.get(StringUtils.cleanPath(Utility.getSecureFileDirectoryPath()+File.separator+"common"+File.separator+TargetFileName));
				Files.copy(profileImg.getInputStream(), copyLocation);
				
				user.setProfileImgUrl(TargetFileName);
				
			} 
//	    	 else {
//				throw new Exception("Please select a valid file");
//			}
	    }
	   
	    
		
	    List<CommonUsersAddress> userAddress=new ArrayList<CommonUsersAddress>();
	   
	    CommonUsersAddress addres=new CommonUsersAddress(user.getId(),countryId,provinceId, cityId ,completeAddress,postalCode,0.0,0.0,0.0);
	    userAddress.add(addres);
	    user.setAddresses(userAddress);	
	   
	    UsersDao.save(user);
	    
	}
	
	
	
	@Transactional
	public void registerNewResturantAccount(long recordId, String firstName,String lastName,String email,String mobile,String password,String completeAddress,
			long countryId,long provinceId,long cityId,String postalCode,MultipartFile profileImg) throws Exception {
		
		if(recordId==0) {
		    if (UsersDao.existsByEmailAndIsDeleted(email,0)) {
		        throw new Exception
		          ("There is an account with same email adress: " + email);
		    }
		    
		    if (UsersDao.existsByMobileAndIsDeleted(mobile,0)) {
		        throw new Exception
		          ("There is an account with same mobile: " + mobile);
		    }
		}
		
	    CommonUsers user = new CommonUsers();
	    String TargetFileName = "";
	    if(recordId!=0) {
	    	
	    	
	    	/**
	    	 * To check around other records other than this recordID
	    	 * */
	    	if (UsersDao.existsByEmailAndIsDeletedAndIdNot(email,0,recordId)) {
		        throw new Exception
		          ("There is an account with same email adress: " + email);
		    }
		    
		    if (UsersDao.existsByMobileAndIsDeletedAndIdNot(mobile,0,recordId)) {
		        throw new Exception
		          ("There is an account with same mobile: " + mobile);
		    }
	    	
	    	
	    	user = UsersDao.findById(recordId);
	    	if (profileImg != null ) {
	    		if (!profileImg.getOriginalFilename().equals("")) {
				
					String OldFile=Utility.getSecureFileDirectoryPath()+File.separator+"common"+File.separator+user.getProfileImgUrl();
					File f = new File(OldFile); 
					
					if(f.delete()) {
						System.out.println("File Delteted");
					}else {
						System.out.println("File Could not be Delteted");
					}
					
					 TargetFileName = "profile_Img_"+"user_"+user.getId()+"_"+ Utility.getUniqueId()
							
							+ profileImg.getOriginalFilename().substring(profileImg.getOriginalFilename().lastIndexOf("."));
					Path copyLocation = Paths.get(StringUtils.cleanPath(Utility.getSecureFileDirectoryPath()+File.separator+"common"+File.separator+TargetFileName));
					Files.copy(profileImg.getInputStream(), copyLocation);
					
					user.setProfileImgUrl(TargetFileName);
	    		} 
//	    		else {
//					throw new Exception("Please select a valid file");
//				}
				
	    		
	    	}
		    
	    }
	    
	    user.setFirstName(firstName);
	    user.setLastName(lastName);
	    if(recordId!=0) {
	    	if(!password.equals("")) {
	    		user.setPassword(bcryptEncoder.encode(password));
	    	}
	    	
	    }else {
	    	user.setPassword(bcryptEncoder.encode(password));
	    }
	    
	    user.setEmail(email);;
	    user.setMobile(mobile);
	    user.setCategoryId(1);
	    user.setIsActive(1);
	    Set<CommonRole> role = new HashSet<CommonRole> ();
	    CommonRole roleObj=RoleDao.findById(2l);
	    role.add(roleObj);
	    user.setRoles(role);
	    UsersDao.save(user);
	    
	    if(recordId==0) {
	    	 if (profileImg != null && !profileImg.getOriginalFilename().equals("") ) {
	 			
				 TargetFileName = "profile_Img_"+"user_"+user.getId()+"_"+ Utility.getUniqueId()
						
						+ profileImg.getOriginalFilename().substring(profileImg.getOriginalFilename().lastIndexOf("."));
				Path copyLocation = Paths.get(StringUtils.cleanPath(Utility.getSecureFileDirectoryPath()+File.separator+"common"+File.separator+TargetFileName));
				Files.copy(profileImg.getInputStream(), copyLocation);
				
				user.setProfileImgUrl(TargetFileName);
				
			} 
//	    	 else {
//				throw new Exception("Please select a valid file");
//			}
	    }
	   
	    
		
	    List<CommonUsersAddress> userAddress=new ArrayList<CommonUsersAddress>();
	   
	    CommonUsersAddress addres=new CommonUsersAddress(user.getId(),countryId,provinceId, cityId ,completeAddress,postalCode,0.0,0.0,0.0);
	    userAddress.add(addres);
	    user.setAddresses(userAddress);	
	   
	    UsersDao.save(user);
	    
	}
	
	
	public void registerNewCustomerUserAccount(String name,String email,String mobile,String password,String confirmPassword) throws Exception {
		
		
	    if (UsersDao.existsByEmailAndIsDeleted(email,0)) {
	        throw new Exception
	          ("There is an account with same email adress: " + email);
	    }
	    if(mobile!="923164849413") {
		    if (UsersDao.existsByMobileAndIsDeleted(mobile,0)) {
		        throw new Exception
		          ("There is an account with same mobile: " + mobile);
		    }
	    }
	    

	    
	    if (!password.equals(confirmPassword)) {
	        throw new Exception
	          ("Password and confirm password does not match.");
	    }
	    CommonUsers user = new CommonUsers();
	    
	    String[] FulName=name.split(" ");
	    user.setFirstName(FulName[0]);
	    if(FulName.length>1) {
	    	user.setLastName(FulName[1]);
	    }
	    
	    
	    
	    user.setPassword(bcryptEncoder.encode(password));
	    user.setEmail(email);;
	    user.setMobile(mobile);
	    user.setCategoryId(1);
	    user.setIsActive(1);
	    Set<CommonRole> role = new HashSet<CommonRole> ();
	    CommonRole roleObj=RoleDao.findById(3);
	    role.add(roleObj);
	    user.setRoles(role);
	    UsersDao.save(user);
	   
	   
	    
	}

	
	public List<Map> findAllInternalUsers(long recordId) {
		List purposeList=new ArrayList<>();
		List<CommonUsers> allusers=new ArrayList<>();
		List<Long> idsNotIn=new ArrayList<>();
		idsNotIn.add(2l);
		idsNotIn.add(3l);
		idsNotIn.add(4l);
		if(recordId==0) {
			List<Long> userIds=UsersDao.findAllUserIdsByIsActiveAndIsDeletedAndRoleIdNotIn(1,0,idsNotIn);
			allusers=UsersDao.findAllByIsActiveAndIsDeletedAndIdIn(1,0,userIds);
		}else {
			@SuppressWarnings("deprecation")
			CommonUsers user =UsersDao.findByIdAndIsActiveAndIsDeleted(recordId,1,0);
			if(user!=null) {
				allusers.add(user)	;
			}
		}
		
		if(!allusers.isEmpty()) {
			
			for(int i=0;i<allusers.size();i++) {
				Map Row=new HashMap();
				Row.put("id",allusers.get(i).getId());
				Row.put("firstName",allusers.get(i).getFirstName());
				Row.put("lastName",allusers.get(i).getLastName());
				Row.put("email",allusers.get(i).getEmail());
				Row.put("mobile",allusers.get(i).getMobile());
				Row.put("categoryId",allusers.get(i).getCategoryId());
				Row.put("profileImage",allusers.get(i).getProfileImgUrl());
				Row.put("roles",allusers.get(i).getRoles());
				Row.put("statusId",allusers.get(i).getIsActive());
				if(allusers.get(i).getIsActive()==1) {
					Row.put("statusLabel","Active");
				}else {
					Row.put("statusLabel","In-Active");
				}
				if(UsersResturantsService.existsResutantByUserId(allusers.get(i).getId())) {
					Row.put("resturantLabel",UsersResturantsService.getResutantByUserId(allusers.get(i).getId()));
					
				}else {
					Row.put("resturantLabel","");
				}
				
				
				
				CommonUsersAddress userAddress=UsersAddressDao.findByUserId(allusers.get(i).getId());
				if(userAddress!=null) {
					Row.put("completeAddress",userAddress.getAddress());
					Row.put("cityId",Utility.parseLong(userAddress.getCityId()) );
					Row.put("provinceId",Utility.parseLong(userAddress.getProvinceId()));
					Row.put("countryId",Utility.parseLong(userAddress.getCountryId()));
					Row.put("cityLabel",CitiesService.findLabelById(Utility.parseLong(userAddress.getCityId())));
					Row.put("provinceLabel",ProvincesService.findLabelById(Utility.parseLong(userAddress.getProvinceId())));
					Row.put("countryLabel", CountriesService.findLabelById(Utility.parseLong(userAddress.getCountryId())));
					Row.put("postalCode",userAddress.getPostalCode());
					Row.put("lat",Utility.parseDouble(userAddress.getLat()));
					Row.put("lng",Utility.parseDouble(userAddress.getLng()));
					Row.put("accuracy",Utility.parseDouble(userAddress.getAccuracy()));
					
				}else {
					Row.put("completeAddress","");
					Row.put("cityId",0);
					Row.put("provinceId",0);
					Row.put("countryId",0);
					Row.put("cityLabel",0);
					Row.put("provinceLabel","");
					Row.put("countryLabel","");
					Row.put("postalCode","");
					Row.put("lat",0.0);
					Row.put("lng",0.0);
					Row.put("accuracy",0.0);
					
				}
				
				purposeList.add(Row);
				
			}
		}
		
		return purposeList;
	}
	
	public List<Map> findAllCustomerUsers(long recordId) {
		List purposeList=new ArrayList<>();
		List<CommonUsers> allusers=new ArrayList<>();
		if(recordId==0) {
			List<Long> userIds=UsersDao.findAllUserIdsByIsActiveAndIsDeletedAndRoleId(1,0,3);
			allusers=UsersDao.findAllByIsActiveAndIsDeletedAndIdIn(1,0,userIds);
		}else {
			@SuppressWarnings("deprecation")
			CommonUsers user =UsersDao.findByIdAndIsActiveAndIsDeleted(recordId,1,0);
			if(user!=null) {
				allusers.add(user)	;
			}
		}
		
		if(!allusers.isEmpty()) {
			
			for(int i=0;i<allusers.size();i++) {
				Map Row=new HashMap();
				Row.put("id",allusers.get(i).getId());
				Row.put("firstName",allusers.get(i).getFirstName());
				Row.put("lastName",allusers.get(i).getLastName());
				Row.put("email",allusers.get(i).getEmail());
				Row.put("mobile",allusers.get(i).getMobile());
				Row.put("categoryId",allusers.get(i).getCategoryId());
				Row.put("profileImage",allusers.get(i).getProfileImgUrl());
				Row.put("roles",allusers.get(i).getRoles());
				Row.put("statusId",allusers.get(i).getIsActive());
				if(allusers.get(i).getIsActive()==1) {
					Row.put("statusLabel","Active");
				}else {
					Row.put("statusLabel","In-Active");
				}
				if(UsersResturantsService.existsResutantByUserId(allusers.get(i).getId())) {
					Row.put("resturantLabel",UsersResturantsService.getResutantByUserId(allusers.get(i).getId()));
					
				}else {
					Row.put("resturantLabel","");
				}
				
				
				
				CommonUsersAddress userAddress=UsersAddressDao.findByUserId(allusers.get(i).getId());
				if(userAddress!=null) {
					Row.put("completeAddress",userAddress.getAddress());
					Row.put("cityId",Utility.parseLong(userAddress.getCityId()) );
					Row.put("provinceId",Utility.parseLong(userAddress.getProvinceId()));
					Row.put("countryId",Utility.parseLong(userAddress.getCountryId()));
					Row.put("cityLabel",CitiesService.findLabelById(Utility.parseLong(userAddress.getCityId())));
					Row.put("provinceLabel",ProvincesService.findLabelById(Utility.parseLong(userAddress.getProvinceId())));
					Row.put("countryLabel", CountriesService.findLabelById(Utility.parseLong(userAddress.getCountryId())));
					Row.put("postalCode",userAddress.getPostalCode());
					Row.put("lat",Utility.parseDouble(userAddress.getLat()));
					Row.put("lng",Utility.parseDouble(userAddress.getLng()));
					Row.put("accuracy",Utility.parseDouble(userAddress.getAccuracy()));
					
				}else {
					Row.put("completeAddress","");
					Row.put("cityId",0);
					Row.put("provinceId",0);
					Row.put("countryId",0);
					Row.put("cityLabel",0);
					Row.put("provinceLabel","");
					Row.put("countryLabel","");
					Row.put("postalCode","");
					Row.put("lat",0.0);
					Row.put("lng",0.0);
					Row.put("accuracy",0.0);
					
				}
				
				purposeList.add(Row);
				
			}
		}
		
		return purposeList;
	}
	
	
	public List<Map> findAllResturantUsers(long recordId) {
		List purposeList=new ArrayList<>();
		List<CommonUsers> allusers=new ArrayList<>();
		if(recordId==0) {
			List<Long> userIds=UsersDao.findAllUserIdsByIsActiveAndIsDeletedAndRoleId(1,0,2);
			allusers=UsersDao.findAllByIsActiveAndIsDeletedAndIdIn(1,0,userIds);
		}else {
			@SuppressWarnings("deprecation")
			CommonUsers user =UsersDao.findByIdAndIsActiveAndIsDeleted(recordId,1,0);
			if(user!=null) {
				allusers.add(user)	;
			}
		}
		
		if(!allusers.isEmpty()) {
			
			for(int i=0;i<allusers.size();i++) {
				Map Row=new HashMap();
				Row.put("id",allusers.get(i).getId());
				Row.put("firstName",allusers.get(i).getFirstName());
				Row.put("lastName",allusers.get(i).getLastName());
				Row.put("email",allusers.get(i).getEmail());
				Row.put("mobile",allusers.get(i).getMobile());
				Row.put("categoryId",allusers.get(i).getCategoryId());
				Row.put("profileImage",allusers.get(i).getProfileImgUrl());
				Row.put("statusId",allusers.get(i).getIsActive());
				if(allusers.get(i).getIsActive()==1) {
					Row.put("statusLabel","Active");
				}else {
					Row.put("statusLabel","In-Active");
				}
				Row.put("roles",allusers.get(i).getRoles());
				
				if(UsersResturantsService.existsResutantByUserId(allusers.get(i).getId())) {
					Row.put("resturantLabel",UsersResturantsService.getResutantByUserId(allusers.get(i).getId()));
					
				}else {
					Row.put("resturantLabel","");
				}
				
				CommonUsersAddress userAddress=UsersAddressDao.findByUserId(allusers.get(i).getId());
				if(userAddress!=null) {
					Row.put("completeAddress",userAddress.getAddress());
					Row.put("cityId",Utility.parseLong(userAddress.getCityId()) );
					Row.put("provinceId",Utility.parseLong(userAddress.getProvinceId()));
					Row.put("countryId",Utility.parseLong(userAddress.getCountryId()));
					Row.put("cityLabel",CitiesService.findLabelById(Utility.parseLong(userAddress.getCityId())));
					Row.put("provinceLabel",ProvincesService.findLabelById(Utility.parseLong(userAddress.getProvinceId())));
					Row.put("countryLabel", CountriesService.findLabelById(Utility.parseLong(userAddress.getCountryId())));
					Row.put("postalCode",userAddress.getPostalCode());
					Row.put("lat",Utility.parseDouble(userAddress.getLat()));
					Row.put("lng",Utility.parseDouble(userAddress.getLng()));
					Row.put("accuracy",Utility.parseDouble(userAddress.getAccuracy()));
					
				}else {
					Row.put("completeAddress","");
					Row.put("cityId",0);
					Row.put("provinceId",0);
					Row.put("countryId",0);
					Row.put("cityLabel",0);
					Row.put("provinceLabel","");
					Row.put("countryLabel","");
					Row.put("postalCode","");
					Row.put("lat",0.0);
					Row.put("lng",0.0);
					Row.put("accuracy",0.0);
					
				}
				purposeList.add(Row);
				
			}
		}
		
		return purposeList;
	}
	
	
	public List<Map> findAllUsersWithResturantRole() {
		List purposeList=new ArrayList<>();
		List<Long> allIds=UsersDao.findAllByIsActiveAndIsDeletedAndRoleId(1,0,2);
		System.out.println(allIds.toString());
		if(!allIds.isEmpty()) {
			
			List<CommonUsers> User=UsersDao.findAllByIdIn(allIds);
			for(int i=0;i<User.size();i++) {	
				if(User!=null) {
					
					Map Row=new HashMap();
					Row.put("id",User.get(i).getId());
					Row.put("label",getUserName(User.get(i).getId()));
					
					purposeList.add(Row);
					
				}
				
			}
		}
		
		return purposeList;
	}
	
	
	
	public void deleteUser(long recordId,long createdBy) {
		CommonUsers user=UsersDao.findById(recordId);
		if(user!=null) {
			
			
			user.setIsDeleted(1);
			user.setDeletedBy(createdBy);
			user.setDeletedOn(new Date());
			UsersDao.save(user);
		}
	}
	
	
	
	
	public String getUserContactNumber(long userId) {
		String Label="";
		CommonUsers user=UsersDao.findById(userId);
		if(user!=null) {
			
			Label=user.getMobile();
		}
		return Label;
	}

	public String getUserName(long userId) {
		String Label="";
		CommonUsers user=UsersDao.findById(userId);
		if(user!=null) {
			if(user.getLastName()!=null) {
				Label=user.getFirstName()+" "+user.getLastName();
			}else {
				Label=user.getFirstName();
			}
			
		
		}
		return Label;
	}
	
	public String getFirstUserName(long userId) {
		String Label="";
		CommonUsers user=UsersDao.findById(userId);
		if(user!=null) {
			
			Label=user.getFirstName();
		}
		return Label;
	}
	
	public String getLastUserName(long userId) {
		String Label="";
		CommonUsers user=UsersDao.findById(userId);
		if(user!=null) {
			
			Label=user.getLastName();
		}
		return Label;
	}
	
	public void setNewPassword(long userId, String newPassword) {
		CommonUsers user = UsersDao.findById(userId);
		if(user!=null) {
			user.setPassword(bcryptEncoder.encode(newPassword));
			UsersDao.save(user);
		}
	}
	
	
	public void setForgotPassword(String mobile, String newPassword) {
		CommonUsers user = UsersDao.findByMobile(mobile);
		if(user!=null) {
			user.setPassword(bcryptEncoder.encode(newPassword));
			UsersDao.save(user);
		}
	}
	
	public List<Map> userProfile(long userId) {
		List purposeList=new ArrayList<>();
		CommonUsers user = UsersDao.findById(userId);
		if(user!=null) {
			Map Row=new HashMap();
			Row.put("id",user.getId());
			Row.put("user_name", user.getFirstName() +" "+user.getLastName());
			Row.put("profile_image_url", user.getProfileImgUrl());
			Row.put("email", user.getEmail());
			Row.put("mobile_no", user.getMobile());
			purposeList.add(Row);
		}
		return purposeList;
	}
    
    public List<Map> sendOTP(String phoneNo){
    	List OTPResponse=new ArrayList<>(); 
//  		Random rand = new Random();
//  		int OTP = rand.nextInt(10000);
  		String OTPString = Utility.createRandomNumericCode(4);
  		String CravingsNumber = " 04235948200";
        
        new Thread(){
        	public void run(){
        		try {
        			String Message = "Dear Customer,\nWELCOME To CRAVINGS,\nYour OTP is: "+OTPString+"\nON YOUR FIRST ORDER FROM CRAVINGS, WIN AN IPHONE.\nFor more info call:"+CravingsNumber;
        			String result = GenericUtility.sendSms(phoneNo,Message);
        			JSONParser parser = new JSONParser();
        			JSONObject json = (JSONObject) parser.parse(result);
        			JSONArray SmsDetail= (JSONArray) json.get("corpsms");
        			JSONObject Data=(JSONObject) SmsDetail.get(0);
        			
					CommonSmsLog smsLog=new CommonSmsLog();
					
					smsLog.setResponseCode(Data.get("code").toString());
					smsLog.setTransactionId(Integer.parseInt(Data.get("transactionID").toString()));
					smsLog.setSentOn(new Date());
					smsLog.setSentTo(phoneNo);
					smsLog.setOtp(Utility.parseLong(Utility.createRandomNumericCode(4)));
					smsLog.setSmsLogTypeId(1);
					smsLog.setDocumentId(1);
					SmsLogDao.save(smsLog);
        		} catch (Exception e) {
					// TODO Auto-generated catch block
        			System.out.print(e);
					e.printStackTrace();
				}
        	}
        }.start();

//        System.out.println(result);
		Map<String, String> Row=new HashMap<String, String>();
		Row.put("phone_no", phoneNo);
		Row.put("otp", OTPString);
		OTPResponse.add(Row);
        return OTPResponse;
    }
    
    
    
    public List<Map> updateProfileImage(long recordId,MultipartFile profileImg) throws Exception {
		
	    CommonUsers user = new CommonUsers();
	    List<Map> list=new ArrayList<>();
	    String TargetFileName = "";
	    if(recordId!=0) {
	    	
	    	user = UsersDao.findById(recordId);
	    	if(user!=null) {
		    	if (profileImg != null ) {
		    		if (!profileImg.getOriginalFilename().equals("")) {
					
						String OldFile=Utility.getSecureFileDirectoryPath()+File.separator+"common"+File.separator+user.getProfileImgUrl();
						File f = new File(OldFile); 
						
						if(f.delete()) {
							System.out.println("File Delteted");
						}else {
							System.out.println("File Could not be Delteted");
						}
						
						 TargetFileName = "profile_Img_"+"user_"+user.getId()+"_"+ Utility.getUniqueId()
								
								+ profileImg.getOriginalFilename().substring(profileImg.getOriginalFilename().lastIndexOf("."));
						Path copyLocation = Paths.get(StringUtils.cleanPath(Utility.getSecureFileDirectoryPath()+File.separator+"common"+File.separator+TargetFileName));
						Files.copy(profileImg.getInputStream(), copyLocation);
						
						user.setProfileImgUrl(TargetFileName);
						UsersDao.save(user);
						
						Map Row =new HashMap();
						Row.put("updatedImageUrl", TargetFileName);
						list.add(Row);
		    		} 
		    		else {
						throw new Exception("Please select a valid file");
					}
					
		    		
		    	}
	    	} 
	    } 
	    
	    return list;
	 
	}

    @Transactional
	public void registerNewRiderUserAccount(long recordId, String firstName,String lastName,String email,String mobile,String password,String completeAddress,
			MultipartFile profileImg, String cnic, String licenseNumber, MultipartFile cnicImage,MultipartFile licenseNumberImage, MultipartFile utilityBillImage,String userName) throws Exception {
		
		
		if(recordId==0) {
		    if (UsersDao.existsByEmailAndIsDeleted(email,0)) {
		        throw new Exception
		          ("There is an account with same email adress: " + email);
		    }
		    
		    if (UsersDao.existsByMobileAndIsDeleted(mobile,0)) {
		        throw new Exception
		          ("There is an account with same mobile: " + mobile);
		    }
		}
		
	    CommonUsers user = new CommonUsers();
	    String TargetFileName = "";
	    if(recordId!=0) {
	    	
	    	
	    	/**
	    	 * To check around other records other than this recordID
	    	 * */
	    	if (UsersDao.existsByEmailAndIsDeletedAndIdNot(email,0,recordId)) {
		        throw new Exception
		          ("There is an account with same email adress: " + email);
		    }
		    
		    if (UsersDao.existsByMobileAndIsDeletedAndIdNot(mobile,0,recordId)) {
		        throw new Exception
		          ("There is an account with same mobile: " + mobile);
		    }
	    	
	    	
	    	user = UsersDao.findById(recordId);
	    	if (profileImg != null ) {
	    		if (!profileImg.getOriginalFilename().equals("")) {
				
					String OldFile=Utility.getSecureFileDirectoryPath()+File.separator+"common"+File.separator+user.getProfileImgUrl();
					File f = new File(OldFile); 
					
					if(f.delete()) {
						System.out.println("File Delteted");
					}else {
						System.out.println("File Could not be Delteted");
					}
					
					 TargetFileName = "profile_Img_"+"user_"+user.getId()+"_"+ Utility.getUniqueId()
							
							+ profileImg.getOriginalFilename().substring(profileImg.getOriginalFilename().lastIndexOf("."));
					Path copyLocation = Paths.get(StringUtils.cleanPath(Utility.getSecureFileDirectoryPath()+File.separator+"common"+File.separator+TargetFileName));
					Files.copy(profileImg.getInputStream(), copyLocation);
					
					user.setProfileImgUrl(TargetFileName);
	    		} 	
	    		
	    	}
	    	
	    	
	    	if (cnicImage != null ) {
	    		if (!cnicImage.getOriginalFilename().equals("")) {
				
					String OldFile=Utility.getSecureFileDirectoryPath()+File.separator+"common"+File.separator+user.getProfileImgUrl();
					File f = new File(OldFile); 
					
					if(f.delete()) {
						System.out.println("File Delteted");
					}else {
						System.out.println("File Could not be Delteted");
					}
					
					 TargetFileName = "cnic_Img_"+"rider_"+user.getId()+"_"+ Utility.getUniqueId()
							
							+ cnicImage.getOriginalFilename().substring(cnicImage.getOriginalFilename().lastIndexOf("."));
					Path copyLocation = Paths.get(StringUtils.cleanPath(Utility.getSecureFileDirectoryPath()+File.separator+"common"+File.separator+TargetFileName));
					Files.copy(cnicImage.getInputStream(), copyLocation);
					
					user.setCnicImgUrl(TargetFileName);
	    		} 	
	    		
	    	}
	    	
	    	
	    	if (licenseNumberImage != null ) {
	    		if (!licenseNumberImage.getOriginalFilename().equals("")) {
				
					String OldFile=Utility.getSecureFileDirectoryPath()+File.separator+"common"+File.separator+user.getProfileImgUrl();
					File f = new File(OldFile); 
					
					if(f.delete()) {
						System.out.println("File Delteted");
					}else {
						System.out.println("File Could not be Delteted");
					}
					
					 TargetFileName = "license_number_Img_"+"rider_"+user.getId()+"_"+ Utility.getUniqueId()
							
							+ licenseNumberImage.getOriginalFilename().substring(licenseNumberImage.getOriginalFilename().lastIndexOf("."));
					Path copyLocation = Paths.get(StringUtils.cleanPath(Utility.getSecureFileDirectoryPath()+File.separator+"common"+File.separator+TargetFileName));
					Files.copy(licenseNumberImage.getInputStream(), copyLocation);
					
					user.setLicenseImgUrl(TargetFileName);
	    		} 	
	    		
	    	}
	    	
	    	if (utilityBillImage != null ) {
	    		if (!utilityBillImage.getOriginalFilename().equals("")) {
				
					String OldFile=Utility.getSecureFileDirectoryPath()+File.separator+"common"+File.separator+user.getProfileImgUrl();
					File f = new File(OldFile); 
					
					if(f.delete()) {
						System.out.println("File Delteted");
					}else {
						System.out.println("File Could not be Delteted");
					}
					
					 TargetFileName = "utility_Img_"+"rider_"+user.getId()+"_"+ Utility.getUniqueId()
							
							+ utilityBillImage.getOriginalFilename().substring(utilityBillImage.getOriginalFilename().lastIndexOf("."));
					Path copyLocation = Paths.get(StringUtils.cleanPath(Utility.getSecureFileDirectoryPath()+File.separator+"common"+File.separator+TargetFileName));
					Files.copy(utilityBillImage.getInputStream(), copyLocation);
					
					user.setUtilityBillImgUrl(TargetFileName);
	    		} 	
	    		
	    	}
		    
	    }
	    
	    user.setFirstName(firstName);
	    user.setLastName(lastName);
	    if(recordId!=0) {
	    	if(!password.equals("")) {
	    		user.setPassword(bcryptEncoder.encode(password));
	    	}
	    	
	    }else {
	    	user.setPassword(bcryptEncoder.encode(password));
	    }
	    
	    user.setEmail(email);;
	    user.setMobile(mobile);
	    user.setLicenseNumber(licenseNumber);
		
	    user.setCnic(cnic);
		
	    user.setCategoryId(1);
	    user.setIsActive(1);
	   
	    Set<CommonRole> role = new HashSet<CommonRole> ();
	    CommonRole roleObj=RoleDao.findById(4l);
	    role.add(roleObj);
	    user.setRoles(role);
	    UsersDao.save(user);
	    
	    if(recordId==0) {
	    	 if (profileImg != null && !profileImg.getOriginalFilename().equals("") ) {
	 			
				 TargetFileName = "profile_Img_"+"rider_"+user.getId()+"_"+ Utility.getUniqueId()
						
						+ profileImg.getOriginalFilename().substring(profileImg.getOriginalFilename().lastIndexOf("."));
				Path copyLocation = Paths.get(StringUtils.cleanPath(Utility.getSecureFileDirectoryPath()+File.separator+"common"+File.separator+TargetFileName));
				Files.copy(profileImg.getInputStream(), copyLocation);
				
				user.setProfileImgUrl(TargetFileName);
				
			} 
	    	 else {
				throw new Exception("Please select a valid file");
			}
	    	 
	    	 
	    	 
	    	 if (cnicImage != null && !cnicImage.getOriginalFilename().equals("") ) {
		 			
				 TargetFileName = "cnic_Img_"+"rider_"+user.getId()+"_"+ Utility.getUniqueId()
						
						+ cnicImage.getOriginalFilename().substring(cnicImage.getOriginalFilename().lastIndexOf("."));
				Path copyLocation = Paths.get(StringUtils.cleanPath(Utility.getSecureFileDirectoryPath()+File.separator+"common"+File.separator+TargetFileName));
				Files.copy(cnicImage.getInputStream(), copyLocation);
				
				user.setCnicImgUrl(TargetFileName);
				
				
			} 
	    	 else {
				throw new Exception("Please select a valid cnic file");
			}
	    	 
	    	 
	    	 if (licenseNumberImage != null && !licenseNumberImage.getOriginalFilename().equals("") ) {
		 			
				 TargetFileName = "license_number_Img_"+"rider_"+user.getId()+"_"+ Utility.getUniqueId()
						
						+ licenseNumberImage.getOriginalFilename().substring(licenseNumberImage.getOriginalFilename().lastIndexOf("."));
				Path copyLocation = Paths.get(StringUtils.cleanPath(Utility.getSecureFileDirectoryPath()+File.separator+"common"+File.separator+TargetFileName));
				Files.copy(licenseNumberImage.getInputStream(), copyLocation);
				
				user.setLicenseImgUrl(TargetFileName);
				
			} 
	    	 else {
				throw new Exception("Please select a valid license file");
			}
	    	 
	    	 if (utilityBillImage != null && !utilityBillImage.getOriginalFilename().equals("") ) {
		 			
				 TargetFileName = "utility_Img_"+"rider_"+user.getId()+"_"+ Utility.getUniqueId()
						
						+ utilityBillImage.getOriginalFilename().substring(utilityBillImage.getOriginalFilename().lastIndexOf("."));
				Path copyLocation = Paths.get(StringUtils.cleanPath(Utility.getSecureFileDirectoryPath()+File.separator+"common"+File.separator+TargetFileName));
				Files.copy(utilityBillImage.getInputStream(), copyLocation);
				
				user.setUtilityBillImgUrl(TargetFileName);
				
			} 
	    	 else {
				throw new Exception("Please select a valid license file");
			}
	    }
	   
	    
	    
	    
		
	    List<CommonUsersAddress> userAddress=new ArrayList<CommonUsersAddress>();
	   
	    CommonUsersAddress addres=new CommonUsersAddress(user.getId(), 1l,1l, 1l,completeAddress,
				"", 0.0, 0.0, 0.0);
	    userAddress.add(addres);
	    user.setAddresses(userAddress);	
	   
	    UsersDao.save(user);
		
	}
	
	public List<Map> findAllRiders(long recordId) {
		List purposeList=new ArrayList<>();
		List<CommonUsers> allusers=new ArrayList<>();
		if(recordId==0) {
			
			
			List<Long> riderIds=UsersDao.findAllByIsActiveAndIsDeletedAndRoleId(1,0,4);
			if(!riderIds.isEmpty()) {
				allusers=UsersDao.findAllByIsActiveAndIsDeletedAndIdIn(1,0,riderIds);
			}
		
			
		}else {
			@SuppressWarnings("deprecation")
			CommonUsers user =UsersDao.findByIdAndIsActiveAndIsDeleted(recordId,1,0);
			if(user!=null) {
				allusers.add(user)	;
			}
		}
		
		if(!allusers.isEmpty()) {
			
			for(int i=0;i<allusers.size();i++) {
				Map Row=new HashMap();
				Row.put("id",allusers.get(i).getId());
				Row.put("firstName",allusers.get(i).getFirstName());
				Row.put("lastName",allusers.get(i).getLastName());
				Row.put("email",allusers.get(i).getEmail());
				Row.put("mobile",allusers.get(i).getMobile());
				Row.put("categoryId",allusers.get(i).getCategoryId());
				Row.put("profileImage",allusers.get(i).getProfileImgUrl());
				Row.put("cnic",allusers.get(i).getCnic());
				Row.put("cnicImage",allusers.get(i).getCnicImgUrl());
				Row.put("licenseNumberImage",allusers.get(i).getLicenseImgUrl());
				Row.put("licenseNumber",allusers.get(i).getLicenseNumber());
				Row.put("utilityBillImage",allusers.get(i).getUtilityBillImgUrl());
				Row.put("userName",allusers.get(i).getUserName());
				if(allusers.get(i).getIsActive()==1) {
					Row.put("statusLabel","Active");
				}else {
					Row.put("statusLabel","In-Active");
				}
				
				Row.put("roles",allusers.get(i).getRoles());
				
				if(UsersResturantsService.existsResutantByUserId(allusers.get(i).getId())) {
					Row.put("resturantLabel",UsersResturantsService.getResutantByUserId(allusers.get(i).getId()));
					
				}else {
					Row.put("resturantLabel","");
				}
				
				CommonUsersAddress userAddress=UsersAddressDao.findByUserId(allusers.get(i).getId());
				if(userAddress!=null) {
					Row.put("completeAddress",userAddress.getAddress());
//					Row.put("cityId",Utility.parseLong(userAddress.getCityId()) );
//					Row.put("provinceId",Utility.parseLong(userAddress.getProvinceId()));
//					Row.put("countryId",Utility.parseLong(userAddress.getCountryId()));
//					Row.put("cityLabel",CitiesService.findLabelById(Utility.parseLong(userAddress.getCityId())));
//					Row.put("provinceLabel",ProvincesService.findLabelById(Utility.parseLong(userAddress.getProvinceId())));
//					Row.put("countryLabel", CountriesService.findLabelById(Utility.parseLong(userAddress.getCountryId())));
//					Row.put("postalCode",userAddress.getPostalCode());
//					Row.put("lat",Utility.parseDouble(userAddress.getLat()));
//					Row.put("lng",Utility.parseDouble(userAddress.getLng()));
//					Row.put("accuracy",Utility.parseDouble(userAddress.getAccuracy()));
					
				}else {
					Row.put("completeAddress","");
//					Row.put("cityId",0);
//					Row.put("provinceId",0);
//					Row.put("countryId",0);
//					Row.put("cityLabel",0);
//					Row.put("provinceLabel","");
//					Row.put("countryLabel","");
//					Row.put("postalCode","");
//					Row.put("lat",0.0);
//					Row.put("lng",0.0);
//					Row.put("accuracy",0.0);
					
				}
				
				purposeList.add(Row);
				
			}
		}
		
		return purposeList;
	}


	public void ChangeStatus(long recordId, int statusId, long userId) {
		CommonUsers iRealChange=UsersDao.findById(recordId);
		iRealChange.setIsActive(statusId);
		iRealChange.setStatusChangedBy(userId);
		iRealChange.setStatusChangedOn(new Date());
		UsersDao.save(iRealChange);
		
	}
	
	public List<Map> getAllUsers() {
		List purposeList=new ArrayList<>();
		List<Long> allIds=UsersDao.findAllUserIdsByIsActiveAndIsDeletedAndRoleId(1,0,3);
		if(!allIds.isEmpty()) {
			
			List<CommonUsers> User=UsersDao.findAllByIdIn(allIds);
			for(int i=0;i<User.size();i++) {	
				if(User!=null) {
					
					Map Row=new HashMap();
					Row.put("id",User.get(i).getId());
					Row.put("label",getUserName(User.get(i).getId()));
					
					purposeList.add(Row);
					
				}
				
			}
		}
		
		return purposeList;
	}
	
	public List<Map> findAllByUserIdOrMobile(long userId,String mobile) {
		List purposeList=new ArrayList<>();
		List<CommonUsers> allusers=new ArrayList<>();
		if(userId==0) {
			List<Long> allIds=UsersDao.findAllUserIdsByIsActiveAndIsDeletedAndRoleId(1,0,3);
			CommonUsers user =UsersDao.findByMobileAndIsActiveAndIsDeleted(mobile,1,0);
			if(user!=null) {
				allusers.add(user)	;
			}
		}else {
			
			CommonUsers user =UsersDao.findByIdAndIsActiveAndIsDeleted(userId,1,0);
			if(user!=null) {
				allusers.add(user);
			}
		}
		
		if(!allusers.isEmpty()) {
			
			for(int i=0;i<allusers.size();i++) {
				Map Row=new HashMap();
				Row.put("id",allusers.get(i).getId());
				Row.put("firstName",allusers.get(i).getFirstName());
				Row.put("lastName",allusers.get(i).getLastName());
				Row.put("email",allusers.get(i).getEmail());
				Row.put("mobile",allusers.get(i).getMobile());
				Row.put("categoryId",allusers.get(i).getCategoryId());
				Row.put("profileImage",allusers.get(i).getProfileImgUrl());
				Row.put("roles",allusers.get(i).getRoles());
				
				if(UsersResturantsService.existsResutantByUserId(allusers.get(i).getId())) {
					Row.put("resturantLabel",UsersResturantsService.getResutantByUserId(allusers.get(i).getId()));
					
				}else {
					Row.put("resturantLabel","");
				}
				
				CommonUsersAddress userAddress=UsersAddressDao.findByUserId(allusers.get(i).getId());
				if(userAddress!=null) {
					Row.put("completeAddress",userAddress.getAddress());
					Row.put("cityId",Utility.parseLong(userAddress.getCityId()) );
					Row.put("provinceId",Utility.parseLong(userAddress.getProvinceId()));
					Row.put("countryId",Utility.parseLong(userAddress.getCountryId()));
					Row.put("cityLabel",CitiesService.findLabelById(Utility.parseLong(userAddress.getCityId())));
					Row.put("provinceLabel",ProvincesService.findLabelById(Utility.parseLong(userAddress.getProvinceId())));
					Row.put("countryLabel", CountriesService.findLabelById(Utility.parseLong(userAddress.getCountryId())));
					Row.put("postalCode",userAddress.getPostalCode());
					Row.put("lat",Utility.parseDouble(userAddress.getLat()));
					Row.put("lng",Utility.parseDouble(userAddress.getLng()));
					Row.put("accuracy",Utility.parseDouble(userAddress.getAccuracy()));
					
				}else {
					Row.put("completeAddress","");
					Row.put("cityId",0);
					Row.put("provinceId",0);
					Row.put("countryId",0);
					Row.put("cityLabel",0);
					Row.put("provinceLabel","");
					Row.put("countryLabel","");
					Row.put("postalCode","");
					Row.put("lat",0.0);
					Row.put("lng",0.0);
					Row.put("accuracy",0.0);
					
				}
				purposeList.add(Row);
				
			}
		}
		
		return purposeList;
	}
	
	
	public List<Map> getAllInternalUsersDropDown() {
		List purposeList=new ArrayList<>();
		List<CommonUsers> allusers=new ArrayList<>();
		List<Long> idsNotIn=new ArrayList<>();
		idsNotIn.add(2l);
		idsNotIn.add(3l);
		idsNotIn.add(4l);
		
		List<Long> userIds=UsersDao.findAllUserIdsByIsActiveAndIsDeletedAndRoleIdNotIn(1,0,idsNotIn);
		allusers=UsersDao.findAllByIsActiveAndIsDeletedAndIdIn(1,0,userIds);
		
		
		if(!allusers.isEmpty()) {
			
			for(int i=0;i<allusers.size();i++) {
				Map Row=new HashMap();
				Row.put("id",allusers.get(i).getId());
				Row.put("name",getUserName(allusers.get(i).getId()));
				
				Set<CommonRole> roleSet=allusers.get(i).getRoles();
					
				 List<CommonRole> list = new ArrayList<CommonRole>(roleSet);
			     CommonRole obj = list.get(0);
				if(obj!=null) {
					Row.put("roleId",obj.getId());
					Row.put("roleLabel",obj.getName());
				}else {
					Row.put("roleId","");
					Row.put("roleLabel","");
				}
				
				purposeList.add(Row);
				
			}
		}
		
		return purposeList;
	}
	
	
	
	public List<Map> getAllInternalUsersUsersAssignedFeaturesList() {
		List purposeList=new ArrayList<>();
		List<CommonUsers> allusers=new ArrayList<>();
		List<Long> commonFeaturesUserId=UsersFeaturesService.getAllUsersFromUserFeatures();
		allusers=UsersDao.findAllByIsActiveAndIsDeletedAndIdIn(1,0,commonFeaturesUserId);
		
		
		if(!allusers.isEmpty()) {
			;
			for(int i=0;i<allusers.size();i++) {
				Map Row=new HashMap();
				Row.put("id",allusers.get(i).getId());
				Row.put("name",getUserName(allusers.get(i).getId()));
				
				Set<CommonRole> roleSet=allusers.get(i).getRoles();
					
				 List<CommonRole> list = new ArrayList<CommonRole>(roleSet);
			     CommonRole obj = list.get(0);
				if(obj!=null) {
					Row.put("roleId",obj.getId());
					Row.put("roleLabel",obj.getName());
				}else {
					Row.put("roleId","");
					Row.put("roleLabel","");
				} 
				
				
				List userAssignedFeatures=new ArrayList<>();
				userAssignedFeatures=UsersFeaturesService.getAllUserAssignedFeaturesGroupWise(allusers.get(i).getId());
				Row.put("user_rights", userAssignedFeatures);
				
				purposeList.add(Row);
				
			}
		}
		
		return purposeList;
	}
	
	
	
}
