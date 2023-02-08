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
import org.springframework.web.bind.annotation.DeleteMapping;
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
import com.rolixtech.cravings.module.cravings.services.CravingsPromotionalVouchersStatusTypesService;
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
public class CravingsPromotionalVouchersCustomersController {
	
	
	@Autowired
	private GenericUtility utility;
	
	@Autowired
	private CravingsPromotionalVouchersService VouchersService;
	
	
	@Autowired
	private CravingsPromotionalVouchersStatusTypesService VouchersStatusTypesService;
	
	public static final String CONTROLLER_URL = GenericUtility.APPLICATION_CONTEXT + "/customer/voucher";


	@PostMapping(CONTROLLER_URL+"/apply" )
	public ResponseEntity<?> Save(String voucherCode , @RequestHeader("authorization") String token)  { 
    		long UserId=utility.getUserIDByToken(token);
    	
			ResponseEntityOutput response=new ResponseEntityOutput();
			Map map=new HashMap<>();
			
			try {
				
				response.CODE="1";
				response.USER_MESSAGE="";
				response.SYSTEM_MESSAGE="Success";
				response.DATA= VouchersService.validateVoucher(voucherCode);
				
			
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
