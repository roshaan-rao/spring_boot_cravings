package com.rolixtech.cravings.module.cravings.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rolixtech.cravings.module.auth.model.ResponseEntityOutput;
import com.rolixtech.cravings.module.cravings.services.CravingsPromotionalVouchersService;
import com.rolixtech.cravings.module.generic.model.CommonCountries;
import com.rolixtech.cravings.module.generic.services.CommonCitiesService;
import com.rolixtech.cravings.module.generic.services.CommonCountriesService;
import com.rolixtech.cravings.module.generic.services.CommonFileService;
import com.rolixtech.cravings.module.generic.services.CommonProvincesService;
import com.rolixtech.cravings.module.generic.services.GenericUtility;
import com.rolixtech.cravings.module.order.POJO.OrderPOJO;
import com.rolixtech.cravings.module.resturant.services.CommonResturantsStatusService;
import com.rolixtech.cravings.module.generic.services.CustomFileNotFoundException;





@RestController
public class CravingsPromotionalVouchersController {
	
	
	@Autowired
	private GenericUtility utility;
	
	@Autowired
	private CravingsPromotionalVouchersService VouchersService;
	
	
	public static final String CONTROLLER_URL = GenericUtility.APPLICATION_CONTEXT + "/promotional/voucher";


	@PostMapping(CONTROLLER_URL+"/save" )
	public ResponseEntity<?> Save(String prefixStr ,Integer totalNumber,Double amount,Double percentageVal,@DateTimeFormat(pattern="dd/MM/yyyy") Date validFrom,@DateTimeFormat(pattern="dd/MM/yyyy") Date validTo,@RequestHeader("authorization") String token)  { 
    		long AdminUserId=utility.getUserIDByToken(token);
    	
			ResponseEntityOutput response=new ResponseEntityOutput();
			Map map=new HashMap<>();
			
			try {
				
				response.CODE="1";
				response.USER_MESSAGE="";
				response.SYSTEM_MESSAGE="Saved";
				VouchersService.savePromtionalVouchers(prefixStr, AdminUserId,totalNumber,utility.parseDouble(amount),utility.parseDouble(percentageVal),validFrom,validTo)	;
				
			
			}

			catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				response.CODE="2";
				response.USER_MESSAGE="Error";
				response.SYSTEM_MESSAGE=e.toString();
				
			}
			
			
			return ResponseEntity.ok(response);
	}	
	
	
	  @PostMapping(CONTROLLER_URL+"/search/view")
	   public ResponseEntity<?> ViewSearch(long recordId,String prefixString,@RequestHeader("authorization") String token)  { 
	       		long UserId=utility.getUserIDByToken(token);
	       	
	   			ResponseEntityOutput response=new ResponseEntityOutput();
	   			Map map=new HashMap<>();
	   			
	   			try {
	   				
	   				response.CODE="1";
	   				response.USER_MESSAGE="";
	   				response.SYSTEM_MESSAGE="Success";
	   				response.DATA=VouchersService.view(recordId,prefixString);	
	   				
	   			
	   			}

	   			catch (Exception e) {
	   				// TODO: handle exception
	   				e.printStackTrace();
	   				response.CODE="2";
	   				response.USER_MESSAGE="Error";
	   				response.SYSTEM_MESSAGE=e.toString();
	   				
	   			}
	   			
	   			
	   			return ResponseEntity.ok(response);
	   	}
	  
	  
	  @PostMapping(CONTROLLER_URL+"/view")
	   public ResponseEntity<?> ViewAll(Long recordId,@RequestHeader("authorization") String token)  { 
	       		long UserId=utility.getUserIDByToken(token);
	       	
	   			ResponseEntityOutput response=new ResponseEntityOutput();
	   			Map map=new HashMap<>();
	   			
	   			try {
	   				
	   				response.CODE="1";
	   				response.USER_MESSAGE="";
	   				response.SYSTEM_MESSAGE="Success";
	   				response.DATA=VouchersService.view(utility.parseLong(recordId));	
	   				
	   			
	   			}

	   			catch (Exception e) {
	   				// TODO: handle exception
	   				e.printStackTrace();
	   				response.CODE="2";
	   				response.USER_MESSAGE="Error";
	   				response.SYSTEM_MESSAGE=e.toString();
	   				
	   			}
	   			
	   			
	   			return ResponseEntity.ok(response);
	   	}
	    
	    
	    
	    @PutMapping(CONTROLLER_URL+"/changeStatus")
	   	public ResponseEntity<?> changeStatus(String recordId,Integer status,@RequestHeader("authorization") String token)  { 
	       		long UserId=utility.getUserIDByToken(token);
	       	
	   			ResponseEntityOutput response=new ResponseEntityOutput();
	   			Map map=new HashMap<>();
	   			
	   			try {
	   				
	   				response.CODE="1";
	   				response.USER_MESSAGE="";
	   				response.SYSTEM_MESSAGE="Updated";
	   				VouchersService.ChangeStatus(Long.parseLong(recordId),status.intValue(),UserId);	
	   				
	   			
	   			}

	   			catch (Exception e) {
	   				// TODO: handle exception
	   				e.printStackTrace();
	   				response.CODE="2";
	   				response.USER_MESSAGE=e.getMessage();
	   				response.SYSTEM_MESSAGE=e.toString();
	   				
	   			}
	   			
	   			
	   			return ResponseEntity.ok(response);
	   	}
	    
	    
	    @PostMapping(CONTROLLER_URL+"/edit/save")
	   	public ResponseEntity<?> singleSave(String recordId,String prefixStr ,Double amount,Double percentageVal,@DateTimeFormat(pattern="dd/MM/yyyy") Date validFrom,@DateTimeFormat(pattern="dd/MM/yyyy") Date validTo,@RequestHeader("authorization") String token)  { 
	       		long UserId=utility.getUserIDByToken(token);
	       	
	   			ResponseEntityOutput response=new ResponseEntityOutput();
	   			Map map=new HashMap<>();
	   			
	   			try {
	   				
	   				response.CODE="1";
	   				response.USER_MESSAGE="";
	   				response.SYSTEM_MESSAGE="Updated";
	   				VouchersService.EditSave(Long.parseLong(recordId), prefixStr ,amount,percentageVal,validFrom,validTo,UserId);	
	   				
	   			
	   			}

	   			catch (Exception e) {
	   				// TODO: handle exception
	   				e.printStackTrace();
	   				response.CODE="2";
	   				response.USER_MESSAGE=e.getMessage();
	   				response.SYSTEM_MESSAGE=e.toString();
	   				
	   			}
	   			
	   			
	   			return ResponseEntity.ok(response);
	   	}
}
