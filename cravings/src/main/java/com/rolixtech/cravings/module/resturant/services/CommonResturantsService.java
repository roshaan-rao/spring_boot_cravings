package com.rolixtech.cravings.module.resturant.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.internal.build.AllowSysOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.rolixtech.cravings.module.generic.services.CommonCitiesService;
import com.rolixtech.cravings.module.generic.services.CommonCountriesService;
import com.rolixtech.cravings.module.generic.services.CommonProvincesService;
import com.rolixtech.cravings.module.generic.services.GenericUtility;
import com.rolixtech.cravings.module.resturant.dao.CommonResturantsDao;
import com.rolixtech.cravings.module.resturant.dao.CommonResturantsProductsDao;
import com.rolixtech.cravings.module.resturant.dao.CommonResturantsPromotionalBannersDao;
import com.rolixtech.cravings.module.resturant.dao.CommonResturantsPromotionalBannersDetailDao;
import com.rolixtech.cravings.module.resturant.model.CommonCategories;
import com.rolixtech.cravings.module.resturant.model.CommonResturants;
import com.rolixtech.cravings.module.resturant.model.CommonResturantsProducts;
import com.rolixtech.cravings.module.resturant.model.CommonResturantsPromotionalBanners;
import com.rolixtech.cravings.module.resturant.model.CommonResturantsPromotionalBannersDetail;
import com.rolixtech.cravings.module.resturant.model.CommonResturantsTimings;
import com.rolixtech.cravings.module.users.dao.CommonRoleDao;
import com.rolixtech.cravings.module.users.models.CommonRole;

@Service
public class CommonResturantsService {

	
	@Autowired
	private CommonResturantsDao ResturantsDao;
	
	
	@Autowired
	private CommonResturantsProductsService ProductsService;
	
	
	@Autowired
	private CommonCategoriesService CategoriesService;
	
	@Autowired
	private CommonCitiesService CitiesService;
	@Autowired
	private CommonCountriesService CountriesService;
	@Autowired
	private CommonProvincesService ProvincesService;
	
	@Autowired
	private CommonUsersResturantsService UsersResturantsService;
	
	@Autowired
	private CommonResturantsStatusService StatusService;
	
	@Autowired
	private CommonResturantsTimingsService ResturantsTimingsService;
	
	@Autowired
	private CommonResturantsCategoriesService ResturantsCategoriesService;
	
	@Autowired
	private CommonResturantsPromotionalBannersDao ResturantsPromotionalBannersDao;
	
	@Autowired
	private CommonResturantsPromotionalBannersDetailDao ResturantsPromotionalBannersDetailsDao;
	
	
	@Autowired
	private GenericUtility utility;
	
	
	
	public List<Map> getAllResturantsByCategoryWise(long categoryId){
		
		List<Map> list=new ArrayList<>();			
		List<CommonResturants> Resturants=ResturantsDao.findAllByCommonCategoryIdWise(categoryId);	
				
		if(!Resturants.isEmpty()) {
			Resturants.stream().forEach(
					resturant->{
						Map Row=new HashMap<>();
						Row.put("id", resturant.getId());
						Row.put("label", resturant.getLabel());
						Row.put("directory", resturant.getDirectoryUrl());
						Row.put("address", resturant.getAddress());
						Row.put("countryId", resturant.getCountryId());
						Row.put("countryLabel",CountriesService.findLabelById(resturant.getCountryId()));
						Row.put("provinceId", resturant.getProvinceId());
						Row.put("provinceLabel",ProvincesService.findLabelById(resturant.getProvinceId()));
						Row.put("cityId", resturant.getCityId());
						Row.put("cityLabel",CitiesService.findLabelById(resturant.getCityId()));
						Row.put("latitude", resturant.getLatitude());
						Row.put("longitude", resturant.getLongitude());
						Row.put("accuracy", resturant.getAccuracy());
						Row.put("logoImgUrl", resturant.getLogoImgUrl());
						Row.put("profileImgUrl", resturant.getProfileImgUrl());
						Row.put("bannerImgUrl", resturant.getBannerImgUrl());
						Row.put("contactNo", resturant.getContactNo());
						Row.put("email", resturant.getEmail());
						Row.put("rating", resturant.getRating());
						Row.put("totalNumberOfRatings", resturant.getRating());
						Row.put("foodCategory", getMostlyAddedProductsCatgoryByResturant(resturant.getId()));
						Row.put("deliveryTime", "30 min");
						Row.put("distance", "1.5km away");
						Row.put("minOrderPrice", "Rs.250");						
						
						if(resturant.getIsActive()==0) {
							Row.put("isActive", resturant.getIsActive());
							Row.put("isActiveLabel", "In-Active");
						}else {
							Row.put("isActive", resturant.getIsActive());
							Row.put("isActiveLabel", "Active");
						}
						
						Row.put("status", resturant.getStatus());
						Row.put("statusLabel", StatusService.findLabelById(resturant.getStatus()));
						Row.put("users",UsersResturantsService.getUsersOfResturant(resturant.getId()));
						List<Map> ResturantsTimings=ResturantsTimingsService.getResturantTimings((resturant.getId()));
						Row.put("resturantTimings", ResturantsTimings);
						list.add(Row);
					}
				
				);
		}
		
		return list;
		
	}
	
	
	
	public List<Map> getAllPopularResturantsByLatLngLimit(double lat,double lng,int limit){
		
		List<Map> list=new ArrayList<>();
	
		List<CommonResturants> Resturants=new ArrayList<>();
		if(limit==0) {
			Resturants=ResturantsDao.findAllByLatitudeAndLongitude(lat,lng);
			
		}else if(limit>0){
			Resturants=ResturantsDao.findAllByRatingOrderByIDDescLimit(limit,lat,lng);
		}		
				
				
		if(!Resturants.isEmpty()) {
			Resturants.stream().forEach(
					resturant->{
						Map Row=new HashMap<>();
						Row.put("id", resturant.getId());
						Row.put("label", resturant.getLabel());
						Row.put("directory", resturant.getDirectoryUrl());
						Row.put("address", resturant.getAddress());
						Row.put("countryId", resturant.getCountryId());
						Row.put("countryLabel",CountriesService.findLabelById(resturant.getCountryId()));
						Row.put("provinceId", resturant.getProvinceId());
						Row.put("provinceLabel",ProvincesService.findLabelById(resturant.getProvinceId()));
						Row.put("cityId", resturant.getCityId());
						Row.put("cityLabel",CitiesService.findLabelById(resturant.getCityId()));
						Row.put("latitude", resturant.getLatitude());
						Row.put("longitude", resturant.getLongitude());
						Row.put("accuracy", resturant.getAccuracy());
						Row.put("logoImgUrl", resturant.getLogoImgUrl());
						Row.put("profileImgUrl", resturant.getProfileImgUrl());
						Row.put("bannerImgUrl", resturant.getBannerImgUrl());
						Row.put("contactNo", resturant.getContactNo());
						Row.put("email", resturant.getEmail());
						Row.put("rating", resturant.getRating());
						Row.put("totalNumberOfRatings", resturant.getRating());
						Row.put("foodCategory", getMostlyAddedProductsCatgoryByResturant(resturant.getId()));
						Row.put("deliveryTime", "30 min");
						Row.put("distance", "1.5km away");
						Row.put("minOrderPrice", "Rs.250");						
						
						if(resturant.getIsActive()==0) {
							Row.put("isActive", resturant.getIsActive());
							Row.put("isActiveLabel", "In-Active");
						}else {
							Row.put("isActive", resturant.getIsActive());
							Row.put("isActiveLabel", "Active");
						}
						
						Row.put("status", resturant.getStatus());
						Row.put("statusLabel", StatusService.findLabelById(resturant.getStatus()));
						Row.put("users",UsersResturantsService.getUsersOfResturant(resturant.getId()));
						List<Map> ResturantsTimings=ResturantsTimingsService.getResturantTimings((resturant.getId()));
						Row.put("resturantTimings", ResturantsTimings);
						list.add(Row);
					}
				
				);
		}
		
		return list;
		
	}
	
	public List<Map> getAllPopularResturantsByLatLngLimitV2(double lat,double lng,int limit){
		
		List<Map> list=new ArrayList<>();
	
		List<CommonResturants> Resturants=new ArrayList<>();
 	
		Resturants=ResturantsDao.findAllByIsActiveAndIsDeleted(1,0);		
				
		if(!Resturants.isEmpty()) {
			Resturants=Resturants.stream()
			.filter(distance -> utility.distanceCalculate(distance.getLatitude(), distance.getLongitude(), lat, lng, 'k')*2  <= 10.0  )
			.sorted((p1, p2) -> ((Double)p2.getRating()).compareTo(p1.getRating()))
			.collect(Collectors.toList());
			
			
			
			if(!Resturants.isEmpty()) {
				Resturants.stream().forEach(
						resturant->{
							Map Row=new HashMap<>();
							Row.put("id", resturant.getId());
							Row.put("label", resturant.getLabel());
							Row.put("directory", resturant.getDirectoryUrl());
							Row.put("address", resturant.getAddress());
							Row.put("countryId", resturant.getCountryId());
							Row.put("countryLabel",CountriesService.findLabelById(resturant.getCountryId()));
							Row.put("provinceId", resturant.getProvinceId());
							Row.put("provinceLabel",ProvincesService.findLabelById(resturant.getProvinceId()));
							Row.put("cityId", resturant.getCityId());
							Row.put("cityLabel",CitiesService.findLabelById(resturant.getCityId()));
							Row.put("latitude", resturant.getLatitude());
							Row.put("longitude", resturant.getLongitude());
							Row.put("accuracy", resturant.getAccuracy());
							Row.put("logoImgUrl", resturant.getLogoImgUrl());
							Row.put("profileImgUrl", resturant.getProfileImgUrl());
							Row.put("bannerImgUrl", resturant.getBannerImgUrl());
							Row.put("contactNo", resturant.getContactNo());
							Row.put("email", resturant.getEmail());
							Row.put("rating", resturant.getRating());
							Row.put("totalNumberOfRatings", resturant.getRating());
							Row.put("foodCategory", "Pakistani");
							Row.put("deliveryTime", "30 min");
							Row.put("distance", "1.5km away");
							Row.put("minOrderPrice", "Rs.250");	
							if(resturant.getIsActive()==0) {
								Row.put("isActive", resturant.getIsActive());
								Row.put("isActiveLabel", "In-Active");
							}else {
								Row.put("isActive", resturant.getIsActive());
								Row.put("isActiveLabel", "Active");
							}
							
							Row.put("status", resturant.getStatus());
							Row.put("statusLabel", StatusService.findLabelById(resturant.getStatus()));
							Row.put("users",UsersResturantsService.getUsersOfResturant(resturant.getId()));
							List<Map> ResturantsTimings=ResturantsTimingsService.getResturantTimings((resturant.getId()));
							Row.put("resturantTimings", ResturantsTimings);
							list.add(Row);
						}
					
				);
			}
		}
		
		return list;
		
	}
	
	
	public List<Map> getAllByResturantNameORProductsName(String keyword,int searchType){
		
		List<Map> list=new ArrayList<>();
	
		List<CommonResturants> Resturants=new ArrayList<>();
		List<CommonResturantsProducts> Products=new ArrayList<>();
		if(searchType==1) {
			Resturants=ResturantsDao.findAllByLabelContaining(keyword);
			
		}else if(searchType==2) {
			Products=ProductsService.findAllByLabelContaining(keyword);
		}		
				
				
		if(!Resturants.isEmpty()) {
			Resturants.stream().forEach(
					resturant->{
						Map Row=new HashMap<>();
						Row.put("id", resturant.getId());
						Row.put("label", resturant.getLabel());
						Row.put("directory", resturant.getDirectoryUrl());
						Row.put("address", resturant.getAddress());
						Row.put("countryId", resturant.getCountryId());
						Row.put("countryLabel",CountriesService.findLabelById(resturant.getCountryId()));
						Row.put("provinceId", resturant.getProvinceId());
						Row.put("provinceLabel",ProvincesService.findLabelById(resturant.getProvinceId()));
						Row.put("cityId", resturant.getCityId());
						Row.put("cityLabel",CitiesService.findLabelById(resturant.getCityId()));
						Row.put("latitude", resturant.getLatitude());
						Row.put("longitude", resturant.getLongitude());
						Row.put("accuracy", resturant.getAccuracy());
						Row.put("logoImgUrl", resturant.getLogoImgUrl());
						Row.put("profileImgUrl", resturant.getProfileImgUrl());
						Row.put("bannerImgUrl", resturant.getBannerImgUrl());
						Row.put("contactNo", resturant.getContactNo());
						Row.put("email", resturant.getEmail());
						Row.put("rating", resturant.getRating());
						Row.put("totalNumberOfRatings", resturant.getRating());
						Row.put("foodCategory", "Pakistani");
						Row.put("deliveryTime", "30 min");
						Row.put("distance", "1.5km away");
						Row.put("minOrderPrice", "Rs.250");	
						if(resturant.getIsActive()==0) {
							Row.put("isActive", resturant.getIsActive());
							Row.put("isActiveLabel", "In-Active");
						}else {
							Row.put("isActive", resturant.getIsActive());
							Row.put("isActiveLabel", "Active");
						}
						
						Row.put("status", resturant.getStatus());
						Row.put("statusLabel", StatusService.findLabelById(resturant.getStatus()));
						Row.put("users",UsersResturantsService.getUsersOfResturant(resturant.getId()));
						List<Map> ResturantsTimings=ResturantsTimingsService.getResturantTimings((resturant.getId()));
						Row.put("resturantTimings", ResturantsTimings);
						list.add(Row);
					}
				
			);
		}
		
		if(!Products.isEmpty()) {
			Products.stream().forEach(
					product->{
							Map Row=new HashMap<>();
							Row.put("id", product.getId());
							Row.put("label", product.getLabel());
							Row.put("description", product.getDescription());
							Row.put("resturantId", product.getResturantId());
							Row.put("resturantLabel", findLabelById(product.getResturantId()));
							Row.put("discount", product.getDiscount());
							Row.put("productImgUrl", product.getProductImgUrl());
							Row.put("rate", product.getRate());
							Row.put("rating", product.getRating());
							list.add(Row);
					}
				
			);
		}
		
		return list;
		
	}
	
	
	public String findLogoImgById(long resturantId) {
		String label="";
		CommonResturants Resturant=ResturantsDao.findById(resturantId);
		if(Resturant!=null) {
			label=Resturant.getLogoImgUrl();
		}
		return label;
	}
	
	public String findBannerImgById(long resturantId) {
		String label="";
		CommonResturants Resturant=ResturantsDao.findById(resturantId);
		if(Resturant!=null) {
			label=Resturant.getBannerImgUrl();
		}
		return label;
	}
	
	public String findLabelById(long resturantId) {
		String label="";
		CommonResturants Resturant=ResturantsDao.findById(resturantId);
		if(Resturant!=null) {
			label=Resturant.getLabel();
		}
		return label;
	}
	
	public String findResturantDirectoryById(long resturantId) {
		String label="";
		CommonResturants Resturant=ResturantsDao.findById(resturantId);
		if(Resturant!=null) {
			label=Resturant.getDirectoryUrl();
		}
		return label;
	}

	
	/********************************************************************************************************/
	/************************************Resturant Admin Starts**********************************************/
	/********************************************************************************************************/
	
	public void saveResturantAdmin(long recordId,long userId,String label,String address,long countryId,long provinceId,long cityId,double lat,double lng,double accuracy,
			MultipartFile logoImg,MultipartFile profileImg,MultipartFile bannerImg,long dayIds[],Date openTimings[],Date closeTimings[],String contactNo,String email,long createdBy) throws Exception {
	
		CommonResturants Resturants=null;
		if(recordId==0) {
			Resturants=new CommonResturants();
		}else {
			Resturants=ResturantsDao.findByIdAndIsDeleted(recordId,0);
		}
		
		Resturants.setAddress(address);
		Resturants.setContactNo(contactNo);
		Resturants.setEmail(email);
		Resturants.setCityId(cityId);
		Resturants.setCountryId(countryId);
		Resturants.setProvinceId(provinceId);
		Resturants.setLabel(label);
		Resturants.setLatitude(lat);
		Resturants.setLongitude(lng);
		Resturants.setAccuracy(accuracy);
		Resturants.setIsActive(1);
		Resturants.setActivatedBy(createdBy);
		Resturants.setActivatedOn(new Date());
		Resturants.setStatus(2);
		Resturants.setStatusChangedBy(createdBy);
		Resturants.setStatusChangedOn(new Date());
		ResturantsDao.save(Resturants);
		
		// specify an abstract pathname in the File object 
		if(recordId==0) {
			
			

				if (logoImg != null && !logoImg.getOriginalFilename().equals("")) {
					
					String TargetFileName = "logo_"+"Restr_"+Resturants.getId()+"_"+ utility.getUniqueId()
							
							+ logoImg.getOriginalFilename().substring(logoImg.getOriginalFilename().lastIndexOf("."));
					Path copyLocation = Paths.get(StringUtils.cleanPath(utility.getOpenFileDirectoryPath()+File.separator+TargetFileName));
					
					Files.copy(logoImg.getInputStream(), copyLocation);
					
					Resturants.setLogoImgUrl(TargetFileName);
					//Resturants.setDirectoryUrl(restrauntDirectory);
					
				} 
//				else {
//					throw new Exception("Please select a valid file");
//				}
				
				if (profileImg != null && !profileImg.getOriginalFilename().equals("")) {
					
					String TargetFileName = "profile_"+"Restr_"+Resturants.getId()+"_"+ utility.getUniqueId()
							
							+ profileImg.getOriginalFilename().substring(profileImg.getOriginalFilename().lastIndexOf("."));
					Path copyLocation = Paths.get(StringUtils.cleanPath(utility.getOpenFileDirectoryPath()+File.separator+TargetFileName));
					
					Files.copy(profileImg.getInputStream(), copyLocation);
					
					Resturants.setProfileImgUrl(TargetFileName);
					//Resturants.setDirectoryUrl(restrauntDirectory);
				}	
//				} else {
//					throw new Exception("Please select a valid file");
//				}
				
				
				if (bannerImg != null && !bannerImg.getOriginalFilename().equals("")) {
					
					String TargetFileName = "banner_"+"Restr_"+Resturants.getId()+"_"+ utility.getUniqueId()
							
							+ bannerImg.getOriginalFilename().substring(bannerImg.getOriginalFilename().lastIndexOf("."));
					Path copyLocation = Paths.get(StringUtils.cleanPath(utility.getOpenFileDirectoryPath()+File.separator+TargetFileName));
					
					Files.copy(bannerImg.getInputStream(), copyLocation);
					
					Resturants.setBannerImgUrl(TargetFileName);
					
				}	

			 
			
		}else {
			
			
			if (logoImg != null && !logoImg.getOriginalFilename().equals("")) {
				
				String OldFile=utility.getOpenFileDirectoryPath()+File.separator+"common"+File.separator+Resturants.getLogoImgUrl();
				File f = new File(OldFile); 
				
				if(f.delete()) {
					System.out.println("File Delteted");
				}else {
					System.out.println("File Could not be Delteted");
				}
				
				String TargetFileName = "logo_"+"Restr_"+Resturants.getId()+"_"+ utility.getUniqueId()
						
						+ logoImg.getOriginalFilename().substring(logoImg.getOriginalFilename().lastIndexOf("."));
				Path copyLocation = Paths.get(StringUtils.cleanPath(utility.getOpenFileDirectoryPath()+File.separator+TargetFileName));
				
				Files.copy(logoImg.getInputStream(), copyLocation);
				
				Resturants.setLogoImgUrl(TargetFileName);
				
				
			} 
			
			if (profileImg != null && !profileImg.getOriginalFilename().equals("")) {
				
				String OldFile=utility.getOpenFileDirectoryPath()+File.separator+Resturants.getProfileImgUrl();
				File f = new File(OldFile); 
				
				if(f.delete()) {
					System.out.println("File Delteted");
				}else {
					System.out.println("File Could not be Delteted");
				}
				
				String TargetFileName = "profile_"+"Restr_"+Resturants.getId()+"_"+ utility.getUniqueId()
						
						+ profileImg.getOriginalFilename().substring(profileImg.getOriginalFilename().lastIndexOf("."));
				Path copyLocation = Paths.get(StringUtils.cleanPath(utility.getOpenFileDirectoryPath()+File.separator+TargetFileName));
				
				Files.copy(profileImg.getInputStream(), copyLocation);
				
				Resturants.setProfileImgUrl(TargetFileName);
				
				
			}
			if (bannerImg != null && !bannerImg.getOriginalFilename().equals("")) {
				
				String OldFile=utility.getOpenFileDirectoryPath()+File.separator+Resturants.getBannerImgUrl();
				File f = new File(OldFile); 
				if(f.delete()) {
					System.out.println("File Delteted");
				}else {
					System.out.println("File Could not be Delteted");
				}
				String TargetFileName = "banner_"+"Restr_"+Resturants.getId()+"_"+ utility.getUniqueId()
						
						+ bannerImg.getOriginalFilename().substring(bannerImg.getOriginalFilename().lastIndexOf("."));
				Path copyLocation = Paths.get(StringUtils.cleanPath(utility.getOpenFileDirectoryPath()+File.separator+TargetFileName));
				
				Files.copy(bannerImg.getInputStream(), copyLocation);
				
				Resturants.setBannerImgUrl(TargetFileName);
				
				
			}
			
			
		}
		
		
		ResturantsDao.save(Resturants);
		
		ResturantsTimingsService.SaveResturantTimings(Resturants.getId(), dayIds, openTimings, closeTimings);
		
		UsersResturantsService.AssignUserToResturant(Resturants.getId(), userId);
		
		
	}


	public List<Map> view(long recordId) {

		
		List<Map> list=new ArrayList<>();
	
		List<CommonResturants> Resturants=new ArrayList<>();
		if(recordId!=0) {
			CommonResturants Resturant=ResturantsDao.findByIdAndIsDeleted(recordId,0);
			Resturants.add(Resturant);
			
		}else {
			Resturants=ResturantsDao.findAllByIsDeleted(0);
		}	 	
				
				
		if(!Resturants.isEmpty()) {
			Resturants.stream().forEach(
				resturant->{
						Map Row=new HashMap<>();
						Row.put("id", resturant.getId());
						Row.put("label", resturant.getLabel());
						Row.put("directory", resturant.getDirectoryUrl());
						Row.put("address", resturant.getAddress());
						Row.put("countryId", resturant.getCountryId());
						Row.put("countryLabel",CountriesService.findLabelById(resturant.getCountryId()));
						Row.put("provinceId", resturant.getProvinceId());
						Row.put("provinceLabel",ProvincesService.findLabelById(resturant.getProvinceId()));
						Row.put("cityId", resturant.getCityId());
						Row.put("cityLabel",CitiesService.findLabelById(resturant.getCityId()));
						Row.put("latitude", resturant.getLatitude());
						Row.put("longitude", resturant.getLongitude());
						Row.put("accuracy", resturant.getAccuracy());
						Row.put("logoImgUrl", resturant.getLogoImgUrl());
						Row.put("profileImgUrl", resturant.getProfileImgUrl());
						Row.put("bannerImgUrl", resturant.getBannerImgUrl());
						Row.put("contactNo", resturant.getContactNo());
						Row.put("email", resturant.getEmail());
						Row.put("rating", resturant.getRating());
						Row.put("totalNumberOfRatings", resturant.getRating());
						Row.put("foodCategory", "Pakistani");
						Row.put("deliveryTime", "30 min");
						Row.put("distance", "1.5km away");
						Row.put("minOrderPrice", "Rs.250");	
						if(resturant.getIsActive()==0) {
							Row.put("isActive", resturant.getIsActive());
							Row.put("isActiveLabel", "In-Active");
						}else {
							Row.put("isActive", resturant.getIsActive());
							Row.put("isActiveLabel", "Active");
						}
						
						Row.put("status", resturant.getStatus());
						Row.put("statusLabel", StatusService.findLabelById(resturant.getStatus()));
						Row.put("users",UsersResturantsService.getUsersOfResturant(resturant.getId()));
						List<Map> ResturantsTimings=ResturantsTimingsService.getResturantTimings((resturant.getId()));
						Row.put("resturantTimings", ResturantsTimings);
						list.add(Row);
				}
				
			);
		}
		
		return list;
		
	}
	
	
	public List<Map> viewCompleteResturant(long recordId) throws Exception {

		
		List<Map> list=new ArrayList<>();
	
		List<CommonResturants> Resturants=new ArrayList<>();
		if(recordId!=0) {
			CommonResturants resturant=ResturantsDao.findByIdAndIsActiveAndIsDeleted(recordId,1,0);
			Resturants.add(resturant);
			
		}else {
			Resturants=ResturantsDao.findAllByIsDeleted(0);
		}	 	
			
		
		if(!Resturants.isEmpty()) {
			Resturants.stream().forEach(
				resturant->{
				Map Row=new HashMap<>();
				Row.put("id", resturant.getId());
				Row.put("label", resturant.getLabel());
				Row.put("directory", resturant.getDirectoryUrl());
				Row.put("address", resturant.getAddress());
				Row.put("rating", resturant.getRating());
				Row.put("logoImgUrl", resturant.getLogoImgUrl());
				Row.put("profileImgUrl", resturant.getProfileImgUrl());
				Row.put("bannerImgUrl", resturant.getBannerImgUrl());
				Row.put("totalNumberOfRatings", resturant.getRating());
				Row.put("foodCategory", "Pakistani");
				Row.put("deliveryTime", "30 min");
				Row.put("distance", "1.5km away");
				Row.put("minOrderPrice", "Rs.250");	
				CommonResturantsTimings Timings= ResturantsTimingsService.getResturantTimingForToday(resturant.getId());
				if(Timings==null) {
					Row.put("restuarntOpeningStatus",1);
					Row.put("openingTime","-");
					Row.put("closingTime","-");
					
				}else {
					ResturantsTimingsService.checkResturantOpenStatus(resturant.getId());
					
					Row.put("restuarntOpeningStatus",1);
					try {
						if(Timings.getClosingTime()!=null) {
							Row.put("closingTime", utility.millisecondstoTime(DateUtils.addHours(Timings.getClosingTime(), 5).getTime()));
							
						}
					
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					try {
						
						if(Timings.getClosingTime()!=null) {
							Row.put("openingTime", utility.millisecondstoTime(DateUtils.addHours(Timings.getOpeningTime(), 5).getTime()));
							
						}
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				}
				
				Row.put("resturant-categories",ResturantsCategoriesService.getAllCategoriesWithProducts(resturant.getId()));
				
				list.add(Row);	
			   }
			
			);
		}
		
		
		
		return list;
		
	}
	
	
	public List<Map> pendingResturantView() {
		
		List<Map> list=new ArrayList<>();
	
		List<CommonResturants> Resturants=new ArrayList<>();
		Resturants=ResturantsDao.findAllByStatusAndIsDeleted(2,0);
				
		if(!Resturants.isEmpty()) {
			Resturants.stream().forEach(
				resturant->{
					Map Row=new HashMap<>();
					Row.put("id", resturant.getId());
					Row.put("label", resturant.getLabel());
					Row.put("directory", resturant.getDirectoryUrl());
					Row.put("address", resturant.getAddress());
					Row.put("countryId", resturant.getCountryId());
					Row.put("countryLabel",CountriesService.findLabelById(resturant.getCountryId()));
					Row.put("provinceId", resturant.getProvinceId());
					Row.put("provinceLabel",ProvincesService.findLabelById(resturant.getProvinceId()));
					Row.put("cityId", resturant.getCityId());
					Row.put("cityLabel",CitiesService.findLabelById(resturant.getCityId()));
					Row.put("latitude", resturant.getLatitude());
					Row.put("longitude", resturant.getLongitude());
					Row.put("accuracy", resturant.getAccuracy());
					Row.put("logoImgUrl", resturant.getLogoImgUrl());
					Row.put("profileImgUrl", resturant.getProfileImgUrl());
					Row.put("bannerImgUrl", resturant.getBannerImgUrl());
					Row.put("rating", resturant.getRating());
					
					if(resturant.getIsActive()==0) {
						Row.put("isActive", resturant.getIsActive());
						Row.put("isActiveLabel", "In-Active");
					}else {
						Row.put("isActive", resturant.getIsActive());
						Row.put("isActiveLabel", "Active");
					}
					Row.put("status", resturant.getStatus());
					Row.put("statusLabel", StatusService.findLabelById(resturant.getStatus()));
					Row.put("users",UsersResturantsService.getUsersOfResturant(resturant.getId()));
					List<Map> ResturantsTimings=ResturantsTimingsService.getResturantTimings((resturant.getId()));
					Row.put("contactNo", resturant.getContactNo());
					Row.put("email", resturant.getEmail());
					Row.put("resturantTimings", ResturantsTimings);
					list.add(Row);
				}
				
			);
		}
		
		return list;
		
	}
	
	
	public void ChangeStatus(long resturantId, int status,long createdBy) {
		CommonResturants Resturant=ResturantsDao.findById(resturantId);
		if(Resturant!=null) {
			Resturant.setStatus(status);
			Resturant.setStatusChangedOn(new Date());
			Resturant.setStatusChangedBy(createdBy);
			ResturantsDao.save(Resturant);
		}
		
	}
	
	public void ChangeActiveStatus(long resturantId, int isActive,long createdBy) {
		CommonResturants Resturant=ResturantsDao.findById(resturantId);
		if(Resturant!=null) {
			Resturant.setIsActive(isActive);
			Resturant.setActivatedOn(new Date());
			Resturant.setActivatedBy(createdBy);
			ResturantsDao.save(Resturant);
		}
		
	}
	
	
	public void deleteResturant(long resturantId, long createdBy) {
		CommonResturants Resturant=ResturantsDao.findById(resturantId);
		if(Resturant!=null) {
			Resturant.setIsDeleted(1);
			ResturantsDao.save(Resturant);
		}
		
	}

	/********************************************************************************************************/
	/************************************Resturant Admin Ends**********************************************/
	/********************************************************************************************************/
	

	
	/********************************************************************************************************/
	/************************************Resturant Owner Starts**********************************************/
	/********************************************************************************************************/
	
	public void saveResturantOwner(long recordId,String label,String address,long countryId,long provinceId,long cityId,double lat,double lng,double accuracy,
			MultipartFile logoImg,MultipartFile profileImg,MultipartFile bannerImg,long createdBy,long dayId[],Date openTimings[],Date closeTimings[],String contactNo,String email) throws Exception {
	
		CommonResturants Resturants=null;
		if(recordId==0) {
			Resturants=new CommonResturants();
		}else {
			Resturants=ResturantsDao.findByIdAndIsDeleted(recordId,0);
		}
		
		Resturants.setContactNo(contactNo);
		Resturants.setEmail(email);
		Resturants.setAddress(address);
		Resturants.setCityId(cityId);
		Resturants.setCountryId(countryId);
		Resturants.setProvinceId(provinceId);
		Resturants.setLabel(label);
		Resturants.setLatitude(lat);
		Resturants.setLongitude(lng);
		Resturants.setIsActive(0);
		Resturants.setStatus(1);
		ResturantsDao.save(Resturants);
		
		// specify an abstract pathname in the File object 
		if(recordId==0) {
			
				if (logoImg != null && !logoImg.getOriginalFilename().equals("")) {
					
					String TargetFileName = "logo_"+"Restr_"+Resturants.getId()+"_"+ utility.getUniqueId()
							
							+ logoImg.getOriginalFilename().substring(logoImg.getOriginalFilename().lastIndexOf("."));
					Path copyLocation = Paths.get(StringUtils.cleanPath(utility.getOpenFileDirectoryPath()+File.separator+TargetFileName));
					Files.copy(logoImg.getInputStream(), copyLocation);
					
					Resturants.setLogoImgUrl(TargetFileName);
					
					
				} 

				
				if (profileImg != null && !profileImg.getOriginalFilename().equals("")) {
					
					String TargetFileName = "profile_"+"Restr_"+Resturants.getId()+"_"+ utility.getUniqueId()
							
							+ profileImg.getOriginalFilename().substring(profileImg.getOriginalFilename().lastIndexOf("."));
					Path copyLocation = Paths.get(StringUtils.cleanPath(utility.getOpenFileDirectoryPath()+File.separator+TargetFileName));
					
					Files.copy(profileImg.getInputStream(), copyLocation);
					
					Resturants.setProfileImgUrl(TargetFileName);
					
					
				} 

				
				if (bannerImg != null && !bannerImg.getOriginalFilename().equals("")) {
					
					String TargetFileName = "banner_"+"Restr_"+Resturants.getId()+"_"+ utility.getUniqueId()
							
							+ bannerImg.getOriginalFilename().substring(bannerImg.getOriginalFilename().lastIndexOf("."));
					Path copyLocation = Paths.get(StringUtils.cleanPath(utility.getOpenFileDirectoryPath()+File.separator+TargetFileName));
					
					Files.copy(bannerImg.getInputStream(), copyLocation);
					
					Resturants.setBannerImgUrl(TargetFileName);
				}		
				
				
			
			
			
			
		}else {
			
			if (logoImg != null && !logoImg.getOriginalFilename().equals("")) {
				
				String OldFile=utility.getOpenFileDirectoryPath()+File.separator+Resturants.getLogoImgUrl();
				File f = new File(OldFile); 
				
				if(f.delete()) {
					System.out.println("File Delteted");
				}else {
					System.out.println("File Could not be Delteted");
				}
				
				String TargetFileName = "logo_"+"Restr_"+Resturants.getId()+"_"+ utility.getUniqueId()
						
						+ logoImg.getOriginalFilename().substring(logoImg.getOriginalFilename().lastIndexOf("."));
				Path copyLocation = Paths.get(StringUtils.cleanPath(utility.getOpenFileDirectoryPath()+File.separator+TargetFileName));
				
				Files.copy(logoImg.getInputStream(), copyLocation);
				
				Resturants.setLogoImgUrl(TargetFileName);
				
				
			}

			
			if (profileImg != null && !profileImg.getOriginalFilename().equals("")) {
				
				String OldFile=utility.getOpenFileDirectoryPath()+File.separator+Resturants.getProfileImgUrl();
				File f = new File(OldFile); 
				
				if(f.delete()) {
					System.out.println("File Delteted");
				}else {
					System.out.println("File Could not be Delteted");
				}
				
				String TargetFileName = "profile_"+"Restr_"+Resturants.getId()+"_"+ utility.getUniqueId()
						
						+ profileImg.getOriginalFilename().substring(profileImg.getOriginalFilename().lastIndexOf("."));
				Path copyLocation = Paths.get(StringUtils.cleanPath(utility.getOpenFileDirectoryPath()+File.separator+TargetFileName));
				
				Files.copy(profileImg.getInputStream(), copyLocation);
				
				Resturants.setProfileImgUrl(TargetFileName);
				
				
			}

			
			if (bannerImg != null && !bannerImg.getOriginalFilename().equals("")) {
				
				String OldFile=utility.getOpenFileDirectoryPath()+File.separator+Resturants.getBannerImgUrl();
				File f = new File(OldFile); 
				if(f.delete()) {
					System.out.println("File Delteted");
				}else {
					System.out.println("File Could not be Delteted");
				}
				String TargetFileName = "banner_"+"Restr_"+Resturants.getId()+"_"+ utility.getUniqueId()
						
						+ bannerImg.getOriginalFilename().substring(bannerImg.getOriginalFilename().lastIndexOf("."));
				Path copyLocation = Paths.get(StringUtils.cleanPath(utility.getOpenFileDirectoryPath()+File.separator+TargetFileName));
				
				Files.copy(bannerImg.getInputStream(), copyLocation);
				
				Resturants.setBannerImgUrl(TargetFileName);
				
				
			} 
//			
		}
		
		ResturantsDao.save(Resturants);
		UsersResturantsService.AssignUserToResturant(Resturants.getId(), createdBy);
		
		
	}


	public List<Map> viewResturant(long userId) {
		
		List<Map> list=new ArrayList<>();
		
		if(userId!=0) { 
			List<CommonResturants> userResturants=ResturantsDao.findAllByUserIdAndIsDeleted(userId,0);
			
			if(!userResturants.isEmpty()) {
				userResturants.stream().forEach(
					resturant->{
							Map Row=new HashMap<>();
							Row.put("id", resturant.getId());
							Row.put("label", resturant.getLabel());
							Row.put("directory", resturant.getDirectoryUrl());
							Row.put("address", resturant.getAddress());
							Row.put("countryId", resturant.getCountryId());
							Row.put("countryLabel",CountriesService.findLabelById(resturant.getCountryId()));
							Row.put("provinceId", resturant.getProvinceId());
							Row.put("provinceLabel",ProvincesService.findLabelById(resturant.getProvinceId()));
							Row.put("cityId", resturant.getCityId());
							Row.put("cityLabel",CitiesService.findLabelById(resturant.getCityId()));
							Row.put("latitude", resturant.getLatitude());
							Row.put("longitude", resturant.getLongitude());
							Row.put("logoImgUrl", resturant.getLogoImgUrl());
							Row.put("profileImgUrl", resturant.getProfileImgUrl());
							Row.put("bannerImgUrl", resturant.getBannerImgUrl());
							Row.put("contactNo", resturant.getContactNo());
							Row.put("email", resturant.getEmail());
							
							Row.put("rating", resturant.getRating());
							Row.put("totalNumberOfRatings", resturant.getRating());
							Row.put("foodCategory", "Pakistani");
							Row.put("deliveryTime", "30 min");
							Row.put("distance", "1.5km away");
							Row.put("minOrderPrice", "Rs.250");	
							if(resturant.getIsActive()==0) {
								Row.put("isActive", resturant.getIsActive());
								Row.put("isActiveLabel", "In-Active");
							}else {
								Row.put("isActive", resturant.getIsActive());
								Row.put("isActiveLabel", "Active");
							}
							
							List<Map> ResturantsTimings=ResturantsTimingsService.getResturantTimings((resturant.getId()));
							Row.put("resturantTimings", ResturantsTimings);
							Row.put("status", resturant.getStatus());
							Row.put("statusLabel", StatusService.findLabelById(resturant.getStatus()));
							list.add(Row);
					}
					
				);
			}
		}		
				
				
		
		
		return list;
		
	}
	
	
	public String getMostlyAddedProductsCatgoryByResturant(long resturantId) {
		
		String purpose="";
		if(ProductsService.getMostlyAddedResturantCatoryIdByResturantID(resturantId)!=null) {
			purpose=ProductsService.getMostlyAddedResturantCatoryIdByResturantID(resturantId);
		}
		
				
				
		
		
		return purpose;
		
	}
	
	
	public List<Map> bannersView() {
		List<Map> list=new ArrayList<>();
//		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
//		Date currentDate= new Date();
		List<CommonResturantsPromotionalBanners> PromotionalBanners = ResturantsPromotionalBannersDao.findAll();
		for(int i=0; i<PromotionalBanners.size(); i++) {	
//			PromotionalBanners.get(i).getEndDate().after(currentDate) && 
			if(PromotionalBanners.get(i).getIsActive()==1) {
				List<CommonResturantsPromotionalBannersDetail> PromotionalBannersDetails = ResturantsPromotionalBannersDetailsDao.findByPromotionalBannerId(PromotionalBanners.get(i).getId());
				if(!PromotionalBannersDetails.isEmpty()) {
					PromotionalBannersDetails.stream().forEach(
					PromotionalBannerDetail->{Map Row=new HashMap<>();
					Row.put("id", PromotionalBannerDetail.getId());
					Row.put("promotionalBannerId", PromotionalBannerDetail.getPromotionalBannerId());
					Row.put("resturantId", PromotionalBannerDetail.getResturantId());
					Row.put("fileName", PromotionalBannerDetail.getImageUrl());
					list.add(Row);});
				}
			}
		}
		return list;
	}


	
	
	
	/********************************************************************************************************/
	/************************************Resturant Owner Ends**********************************************/
	/********************************************************************************************************/
	
}
