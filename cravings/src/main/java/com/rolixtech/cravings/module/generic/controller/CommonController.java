package com.rolixtech.cravings.module.generic.controller;

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
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.rolixtech.cravings.module.auth.model.ResponseEntityOutput;
import com.rolixtech.cravings.module.generic.model.CommonCountries;
import com.rolixtech.cravings.module.generic.services.CommonCitiesService;
import com.rolixtech.cravings.module.generic.services.CommonCountriesService;
import com.rolixtech.cravings.module.generic.services.CommonFileService;
import com.rolixtech.cravings.module.generic.services.CommonProvincesService;
import com.rolixtech.cravings.module.generic.services.GenericUtility;
import com.rolixtech.cravings.module.resturant.services.CommonResturantsStatusService;
import com.rolixtech.cravings.module.generic.services.CustomFileNotFoundException;





@RestController
public class CommonController {
	@Autowired
	private CommonFileService fileStorageService;
	
	@Autowired
	private GenericUtility util;
	@Autowired
	private ServletContext servletContext;
	Logger log = LoggerFactory.getLogger(this.getClass().getName());
	
	@Autowired
	private CommonCountriesService CountriesService;
	
	@Autowired
	private CommonProvincesService ProvincesService;
	
	@Autowired
	private CommonCitiesService CitiesService;
	
	@Autowired
	private CommonResturantsStatusService ResturantsStatusService;
	
	public static final String CONTROLLER_URL = GenericUtility.APPLICATION_CONTEXT + "/generic";
	
	@RequestMapping(CONTROLLER_URL+"/image/view")
	public void ViewImage(HttpServletResponse response,String fileName) {
		
		ResponseEntityOutput jresponse=new ResponseEntityOutput();
		try {
			InputStream in = new FileInputStream(util.getFileDirectoryPath()+ File.separator + "common" + File.separator+fileName);
			response.setContentType(MediaType.ALL_VALUE);
		    IOUtils.copy(in, response.getOutputStream());
			
			
			////System.out.println(1/0);
		}  catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error(e.toString());
			jresponse.CODE="2";
			jresponse.USER_MESSAGE=e.getMessage();
			jresponse.SYSTEM_MESSAGE=e.toString();
		}
		
	}
	
	
	@RequestMapping(CONTROLLER_URL+"/resturant-image/view")
	public void ViewImage(HttpServletResponse response,String fileName,long resturantId) {
		
		ResponseEntityOutput jresponse=new ResponseEntityOutput();
		try {
			String restrauntDirectory="Restr_"+resturantId;
			InputStream in = new FileInputStream(util.getFileDirectoryPath()+File.separator+restrauntDirectory+File.separator+fileName);
			response.setContentType(MediaType.ALL_VALUE);
		    IOUtils.copy(in, response.getOutputStream());
			
			
			////System.out.println(1/0);
		}  catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error(e.toString());
			jresponse.CODE="2";
			jresponse.USER_MESSAGE=e.getMessage();
			jresponse.SYSTEM_MESSAGE=e.toString();
		}
		
	}
	
		
	
	@RequestMapping(CONTROLLER_URL+"/file/view")
	public ResponseEntity<?> ViewFile(String fileName) throws MalformedURLException,CustomFileNotFoundException {
		
		System.out.println("FileName"+fileName);
		ResponseEntityOutput jresponse=new ResponseEntityOutput();
		Map map=new HashMap<>();
		jresponse.CODE="1";
		
		 Resource file = fileStorageService.loadFileAsResource(fileName);
		  return
		  ResponseEntity.ok() .header(HttpHeaders.CONTENT_DISPOSITION,
		  "attachment; filename=\"" + file.getFilename() + "\"") .body(file);
		
	}
	
	@GetMapping(CONTROLLER_URL+"/countries/view")
	public ResponseEntity<?> ViewCountries()  { 
				
				ResponseEntityOutput response=new ResponseEntityOutput();
				Map map=new HashMap<>();
				
				try {
					
					response.CODE="1";
					response.USER_MESSAGE="";
					response.SYSTEM_MESSAGE="";
					response.DATA=CountriesService.getAll();
				
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
	 
	 @PostMapping(CONTROLLER_URL+"/provinces/view")
	 public ResponseEntity<?> ViewProvinces(long countryId)  { 
			
			ResponseEntityOutput response=new ResponseEntityOutput();
			Map map=new HashMap<>();
			
			try {
				
				response.CODE="1";
				response.USER_MESSAGE="";
				response.SYSTEM_MESSAGE="";
				response.DATA=ProvincesService.getAllByCountryId(countryId);
			
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
	 
	@PostMapping(CONTROLLER_URL+"/cities/view")
	public ResponseEntity<?> ViewCities(long provinceId)  { 
			
			ResponseEntityOutput response=new ResponseEntityOutput();
			Map map=new HashMap<>();
			
			try {
				
				response.CODE="1";
				response.USER_MESSAGE="";
				response.SYSTEM_MESSAGE="";
				response.DATA=CitiesService.getAllByProviceId(provinceId);
			
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
	
	@PostMapping(CONTROLLER_URL+"/common-resturants-status/view")
	public ResponseEntity<?> common(long provinceId)  { 
			
			ResponseEntityOutput response=new ResponseEntityOutput();
			Map map=new HashMap<>();
			
			try {
				
				response.CODE="1";
				response.USER_MESSAGE="";
				response.SYSTEM_MESSAGE="";
				response.DATA=ResturantsStatusService.getAll();
			
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

}
