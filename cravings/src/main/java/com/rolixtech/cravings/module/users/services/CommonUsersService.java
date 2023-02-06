package com.rolixtech.cravings.module.users.services;

import java.io.File;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.rolixtech.cravings.module.generic.dao.CommonSmsLogDao;
import com.rolixtech.cravings.module.generic.model.CommonSmsLog;
import com.rolixtech.cravings.module.generic.services.GenericUtility;
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
	
	public void registerNewUserAccount(long recordId, String firstName,String lastName,String email,String mobile,String password,String completeAddress,
			long countryId,long provinceId,long cityId,String postalCode,double lat,double lng,double accuracy,long roleId,MultipartFile profileImg) throws Exception {
		
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
	   
	    CommonUsersAddress addres=new CommonUsersAddress(user.getId(), countryId, provinceId, cityId, completeAddress, postalCode, provinceId, cityId, accuracy);
	    userAddress.add(addres);
	    user.setAddresses(userAddress);	
	   
	    UsersDao.save(user);
	    
	}
	
	
	public void registerNewCustomerUserAccount(String name,String email,String mobile,String password,String confirmPassword) throws Exception {
		
		
	    if (UsersDao.existsByEmailAndIsDeleted(email,0)) {
	        throw new Exception
	          ("There is an account with same email adress: " + email);
	    }
	    
	    if (UsersDao.existsByMobileAndIsDeleted(mobile,0)) {
	        throw new Exception
	          ("There is an account with same mobile: " + mobile);
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

	public List<Map> findAllUsers(long recordId) {
		List purposeList=new ArrayList<>();
		List<CommonUsers> allusers=new ArrayList<>();
		if(recordId==0) {
			allusers=UsersDao.findAllByIsActiveAndIsDeleted(1,0);
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
				List<CommonUsersAddress> userAddress=UsersAddressDao.findByUserId(allusers.get(i).getId());
				if(!userAddress.isEmpty()) {
					Row.put("address",userAddress);
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
					Row.put("label",User.get(i).getFirstName()+" "+User.get(i).getLastName());
					
					purposeList.add(Row);
					
				}
				
			}
		}
		
		return purposeList;
	}
	
	
	
	public void deleteUser(long recordId,long createdBy) {
		CommonUsers user=UsersDao.findById(recordId);
		if(user!=null) {
			System.out.println("user"+user);
			
			user.setIsDeleted(1);
			user.setDeletedBy(createdBy);
			user.setDeletedOn(new Date());
			UsersDao.save(user);
		}
	}

	public String getUserName(long userId) {
		String Label="";
		CommonUsers user=UsersDao.findById(userId);
		if(user!=null) {
			
			Label=user.getFirstName()+" "+user.getLastName();
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
	
	
	
	
}
