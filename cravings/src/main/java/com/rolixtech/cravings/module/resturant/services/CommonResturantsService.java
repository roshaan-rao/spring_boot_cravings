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
import org.springframework.transaction.annotation.Transactional;
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
import com.rolixtech.cravings.module.resturant.dao.CommonResturantsPromotionalBannersDetailDao.customPromotionalBanner;
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
						Row.put("deliveryTime", utility.getCravingsDeliveryTime()+" mins");
					 //   Row.put("distance", utility.distanceCalculate(resturant.getLatitude(), resturant.getLongitude(), lat, lng, 'k')+"km away");
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
						Row.put("isGst", resturant.getIsGst());
						if(utility.parseInt(resturant.getIsGst())==1) {
							Row.put("gstPercentage", resturant.getGstPercentage());
						}else {
							
							Row.put("gstPercentage", 0.0);
							
						}
						
						Row.put("deliverCharges", utility.parseDouble(resturant.getDeliveryCharges()));
						if(resturant.getContactNo2()!=null && !resturant.getContactNo2().equals("") )  {
							Row.put("contactNo2", resturant.getContactNo2());
						}else {
							Row.put("contactNo2", "");
						}
						
						if(resturant.getContactNo3()!=null && !resturant.getContactNo3().equals("") ) {
							Row.put("contactNo3", resturant.getContactNo3());
						}else {
							Row.put("contactNo3", "");
						}
						
						
						if(resturant.getContactNo4()!=null && !resturant.getContactNo4().equals("") ) {
							Row.put("contactNo4", resturant.getContactNo4());
						}else {
							Row.put("contactNo4", "");
						}
						Row.put("resturantDiscount", resturant.getDiscount());
						Row.put("isFeatured",utility.isFeatured());
						Row.put("isClosed", ResturantsTimingsService.checkResturantOpenStatus(resturant.getId()));
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
						Row.put("deliveryTime", utility.getCravingsDeliveryTime()+" mins");
						Row.put("distance", utility.distanceCalculate(resturant.getLatitude(),resturant.getLongitude(), lat, lng, 'k')+"km away");
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
						Row.put("isGst", resturant.getIsGst());
						if(utility.parseInt(resturant.getIsGst())==1) {
							Row.put("gstPercentage", resturant.getGstPercentage());
						}else {
							
							Row.put("gstPercentage", 0.0);
							
						}
						
						Row.put("deliverCharges", utility.parseDouble(resturant.getDeliveryCharges()));
						if(resturant.getContactNo2()!=null && !resturant.getContactNo2().equals("") )  {
							Row.put("contactNo2", resturant.getContactNo2());
						}else {
							Row.put("contactNo2", "");
						}
						
						if(resturant.getContactNo3()!=null && !resturant.getContactNo3().equals("") ) {
							Row.put("contactNo3", resturant.getContactNo3());
						}else {
							Row.put("contactNo3", "");
						}
						
						
						if(resturant.getContactNo4()!=null && !resturant.getContactNo4().equals("") ) {
							Row.put("contactNo4", resturant.getContactNo4());
						}else {
							Row.put("contactNo4", "");
						}
						Row.put("resturantDiscount", resturant.getDiscount());
						Row.put("isFeatured",utility.isFeatured());
						Row.put("isClosed", "false");
						list.add(Row);
					}
				
				);
		}
		
		return list;
		
	}
	
	public List<Map> getAllPopularResturantsByLatLngLimitV2(double lat,double lng,int limit){
		
		List<Map> list=new ArrayList<>();
	
		List<CommonResturants> ResturantsTOShow=new ArrayList<>();
 	
		ResturantsTOShow=ResturantsDao.findAllByIsActiveAndIsDeleted(1,0);		
				
		if(!ResturantsTOShow.isEmpty()) {
			
			List<CommonResturants> Resturants=new ArrayList<>();
			Resturants=ResturantsTOShow.stream()
			.filter(distance -> utility.distanceCalculate(distance.getLatitude(), distance.getLongitude(), lat, lng, 'k') <= 5.0  )
			.sorted((p1, p2) -> ((Double)p2.getRating()).compareTo(p1.getRating()))
			.collect(Collectors.toList());
			int counter=0;
			for(int i=0;i<Resturants.size();i++) {
				if(limit!=0 &&  limit>=counter) {
					Map Row=new HashMap<>();
					Row.put("id", Resturants.get(i).getId());
					Row.put("label", Resturants.get(i).getLabel());
					Row.put("directory", Resturants.get(i).getDirectoryUrl());
					Row.put("address", Resturants.get(i).getAddress());
					Row.put("countryId", Resturants.get(i).getCountryId());
					Row.put("countryLabel",CountriesService.findLabelById(Resturants.get(i).getCountryId()));
					Row.put("provinceId", Resturants.get(i).getProvinceId());
					Row.put("provinceLabel",ProvincesService.findLabelById(Resturants.get(i).getProvinceId()));
					Row.put("cityId", Resturants.get(i).getCityId());
					Row.put("cityLabel",CitiesService.findLabelById(Resturants.get(i).getCityId()));
					Row.put("latitude", Resturants.get(i).getLatitude());
					Row.put("longitude", Resturants.get(i).getLongitude());
					Row.put("accuracy", Resturants.get(i).getAccuracy());
					Row.put("logoImgUrl", Resturants.get(i).getLogoImgUrl());
					Row.put("profileImgUrl", Resturants.get(i).getProfileImgUrl());
					Row.put("bannerImgUrl", Resturants.get(i).getBannerImgUrl());
					Row.put("contactNo", Resturants.get(i).getContactNo());
					Row.put("email", Resturants.get(i).getEmail());
					Row.put("rating", Resturants.get(i).getRating());
					Row.put("totalNumberOfRatings", Resturants.get(i).getRating());
					Row.put("foodCategory", getMostlyAddedProductsCatgoryByResturant( Resturants.get(i).getId()));
					Row.put("deliveryTime", utility.getCravingsDeliveryTime()+" mins");
					Row.put("distance",utility.roundToOneDecimal(utility.distanceCalculate(Resturants.get(i).getLatitude(), Resturants.get(i).getLongitude(), lat, lng, 'k'))+"km away");
					Row.put("minOrderPrice", "Rs.250");	
					if(Resturants.get(i).getIsActive()==0) {
						Row.put("isActive", Resturants.get(i).getIsActive());
						Row.put("isActiveLabel", "In-Active");
					}else {
						Row.put("isActive", Resturants.get(i).getIsActive());
						Row.put("isActiveLabel", "Active");
					}
					
					Row.put("status", Resturants.get(i).getStatus());
					Row.put("statusLabel", StatusService.findLabelById(Resturants.get(i).getStatus()));
					Row.put("users",UsersResturantsService.getUsersOfResturant(Resturants.get(i).getId()));
					List<Map> ResturantsTimings=ResturantsTimingsService.getResturantTimings((Resturants.get(i).getId()));
					Row.put("resturantTimings", ResturantsTimings);
					Row.put("isGst", Resturants.get(i).getIsGst());
					if(utility.parseInt(Resturants.get(i).getIsGst())==1) {
						Row.put("gstPercentage", Resturants.get(i).getGstPercentage());
					}else {
						
						Row.put("gstPercentage", 0.0);
						
					}
					
					Row.put("isFeatured",utility.isFeatured());
					Row.put("resturantDiscount", Resturants.get(i).getDiscount());
					Row.put("serviceFee", utility.getCravingsSericeFee());
					Row.put("isFeatured",utility.isFeatured());
					Row.put("deliverCharges",utility.parseDouble(Resturants.get(i).getDeliveryCharges()));
					//if(Resturants.get(i).get) {}
					
					
					Row.put("isClosed", ResturantsTimingsService.checkResturantOpenStatus(Resturants.get(i).getId()));
					list.add(Row);
					counter++;
					
				}else {
					Map Row=new HashMap<>();
					Row.put("id", Resturants.get(i).getId());
					Row.put("label", Resturants.get(i).getLabel());
					Row.put("directory", Resturants.get(i).getDirectoryUrl());
					Row.put("address", Resturants.get(i).getAddress());
					Row.put("countryId", Resturants.get(i).getCountryId());
					Row.put("countryLabel",CountriesService.findLabelById(Resturants.get(i).getCountryId()));
					Row.put("provinceId", Resturants.get(i).getProvinceId());
					Row.put("provinceLabel",ProvincesService.findLabelById(Resturants.get(i).getProvinceId()));
					Row.put("cityId", Resturants.get(i).getCityId());
					Row.put("cityLabel",CitiesService.findLabelById(Resturants.get(i).getCityId()));
					Row.put("latitude", Resturants.get(i).getLatitude());
					Row.put("longitude", Resturants.get(i).getLongitude());
					Row.put("accuracy", Resturants.get(i).getAccuracy());
					Row.put("logoImgUrl", Resturants.get(i).getLogoImgUrl());
					Row.put("profileImgUrl", Resturants.get(i).getProfileImgUrl());
					Row.put("bannerImgUrl", Resturants.get(i).getBannerImgUrl());
					Row.put("contactNo", Resturants.get(i).getContactNo());
					Row.put("email", Resturants.get(i).getEmail());
					Row.put("rating", Resturants.get(i).getRating());
					Row.put("totalNumberOfRatings", Resturants.get(i).getRating());
					Row.put("foodCategory", getMostlyAddedProductsCatgoryByResturant( Resturants.get(i).getId()));
					Row.put("deliveryTime", utility.getCravingsDeliveryTime()+" mins");
					Row.put("distance",utility.roundToOneDecimal(utility.distanceCalculate(Resturants.get(i).getLatitude(), Resturants.get(i).getLongitude(), lat, lng, 'k'))+"km away");
					
					//Row.put("distance", utility.distanceCalculate(Resturants.get(i).getLatitude(), Resturants.get(i).getLongitude(), lat, lng, 'k')+"km away");
					Row.put("minOrderPrice", "Rs.250");	
					if(Resturants.get(i).getIsActive()==0) {
						Row.put("isActive", Resturants.get(i).getIsActive());
						Row.put("isActiveLabel", "In-Active");
					}else {
						Row.put("isActive", Resturants.get(i).getIsActive());
						Row.put("isActiveLabel", "Active");
					}
					
					Row.put("status", Resturants.get(i).getStatus());
					Row.put("statusLabel", StatusService.findLabelById(Resturants.get(i).getStatus()));
					Row.put("users",UsersResturantsService.getUsersOfResturant(Resturants.get(i).getId()));
					List<Map> ResturantsTimings=ResturantsTimingsService.getResturantTimings((Resturants.get(i).getId()));
					Row.put("resturantTimings", ResturantsTimings);
					Row.put("isGst", Resturants.get(i).getIsGst());
					if(utility.parseInt(Resturants.get(i).getIsGst())==1) {
						Row.put("gstPercentage", Resturants.get(i).getGstPercentage());
					}else {
						
						Row.put("gstPercentage", 0.0);
						
					}
					
					Row.put("isFeatured",utility.isFeatured());
					Row.put("resturantDiscount", Resturants.get(i).getDiscount());
					Row.put("serviceFee", utility.getCravingsSericeFee());
					Row.put("isFeatured",utility.isFeatured());
					Row.put("deliverCharges",utility.parseDouble(Resturants.get(i).getDeliveryCharges()));
					Row.put("isClosed", ResturantsTimingsService.checkResturantOpenStatus(Resturants.get(i).getId()));
					
					
					
					list.add(Row);
					
				}
			}
			
							
							
		}
					
				
		
		return list;
		
	}
	
	
	public List<Map> getAllByResturantNameORProductsName(String keyword,int searchType){
		
		List<Map> list=new ArrayList<>();
	
		List<CommonResturants> Resturants=new ArrayList<>();
		List<CommonResturantsProducts> Products=new ArrayList<>();
		if(searchType==1) {
			Resturants=ResturantsDao.findAllByLabelContainingAndIsActiveAndIsDeleted(keyword,1,0);
			
		}else if(searchType==2) {
			Products=ProductsService.findAllByLabelContainingAndIsActiveAndIsDeleted(keyword,1,0);
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
						Row.put("deliveryTime", utility.getCravingsDeliveryTime()+" mins");
						//Row.put("distance", utility.distanceCalculate(Resturants.get(i).getLatitude(), Resturants.get(i).getLongitude(), lat, lng, 'k')+"km away");
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
						Row.put("isGst", resturant.getIsGst());
						if(utility.parseInt(resturant.getIsGst())==1) {
							Row.put("gstPercentage", resturant.getGstPercentage());
						}else {
							
							Row.put("gstPercentage", 0.0);
							
						}
						
						
						Row.put("resturantDiscount", resturant.getDiscount());
						Row.put("serviceFee", utility.getCravingsSericeFee());
						Row.put("isFeatured",utility.isFeatured());
						Row.put("deliverCharges",utility.parseDouble(resturant.getDeliveryCharges()));
						Row.put("isClosed", ResturantsTimingsService.checkResturantOpenStatus(resturant.getId()));
						list.add(Row);
					}
				
			);
		}
		
		if(!Products.isEmpty()) {
			Products.stream().forEach(
					product->{
						
						  // if(ResturantsTimingsService.checkResturantOpenStatus(product.getResturantId()).equals("true")) {
							   
						   
								Map Row=new HashMap<>();
								Row.put("id", product.getId());
								Row.put("label", product.getLabel());
								Row.put("description", product.getDescription());
								Row.put("resturantId", product.getResturantId());
								Row.put("resturantLabel", findLabelById(product.getResturantId()));
	//							CommonResturants Resturant=ResturantsDao.findById(product.getResturantId());
	//							if(Resturant!=null) {
	//								Row.put("resturantDiscount", 10.0);
	//								Row.put("gstPercentage", Resturant.getGstPercentage());
	//								Row.put("serviceFee", utility.getCravingsSericeFee());
	//							} 
								
								
								Row.put("discount", product.getDiscount());
								Row.put("productImgUrl", product.getProductImgUrl());
								Row.put("rate", product.getRate());
								Row.put("rating", product.getRating());
//								Row.put("isClosed", ProductsService.getIsClosedTimingsByProductId(product.getId()));
//								Row.put("isClosed",ResturantsTimingsService.checkResturantOpenStatus(product.getResturantId()));
//								if(ResturantsTimingsService.checkResturantOpenStatus(product.getResturantId()).equals("false")) {
//									Row.put("isClosed", ProductsService.getIsClosedTimingsByProductId(product.getId()));
//										
//								}
								
								if(ResturantsTimingsService.checkResturantOpenStatus(product.getResturantId()).equals("false")) {
									Row.put("isClosed",ProductsService.getIsClosedTimingsByProductId(product.getId()));
								}else {
									Row.put("isClosed","false");
								}
								list.add(getBasicChargesDetailByResturant(Row,product.getResturantId()));
						  // }
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
	
	public Double findResturantLatById(long resturantId) {
		Double lat=0.0;
		CommonResturants Resturant=ResturantsDao.findById(resturantId);
		if(Resturant!=null) {
			lat=Resturant.getLatitude();
		}
		return lat;
	}
	
	public Double findResturantLngById(long resturantId) {
		Double lat=0.0;
		CommonResturants Resturant=ResturantsDao.findById(resturantId);
		if(Resturant!=null) {
			lat=Resturant.getLongitude();
		}
		return lat;
	}
	
	public String findResturantAddressById(long resturantId) {
		String address="";
		CommonResturants Resturant=ResturantsDao.findById(resturantId);
		if(Resturant!=null) {
			address=Resturant.getAddress();
		}
		return address;
	}
	
	public String findResturantContactById(long resturantId) {
		String contact="";
		CommonResturants Resturant=ResturantsDao.findById(resturantId);
		if(Resturant!=null) {
			contact=Resturant.getContactNo();
		}
		return contact;
	}
	
	public String findResturantContact2ById(long resturantId) {
		String contact="";
		CommonResturants Resturant=ResturantsDao.findById(resturantId);
		if(Resturant!=null) {
			contact=Resturant.getContactNo2();
		}
		return contact;
	}
	
	public String findResturantContact3ById(long resturantId) {
		String contact="";
		CommonResturants Resturant=ResturantsDao.findById(resturantId);
		if(Resturant!=null) {
			contact=Resturant.getContactNo3();
		}
		return contact;
	}
	
	
	public String findResturantContact4ById(long resturantId) {
		String contact="";
		CommonResturants Resturant=ResturantsDao.findById(resturantId);
		if(Resturant!=null) {
			contact=Resturant.getContactNo4();
		}
		return contact;
	}

	
	/********************************************************************************************************/
	/************************************Resturant Admin Starts**********************************************/
	/********************************************************************************************************/
	
	public void saveResturantAdmin(long recordId,long userId,String label,String address,long countryId,long provinceId,long cityId,double lat,double lng,double accuracy,
			MultipartFile logoImg,MultipartFile profileImg,MultipartFile bannerImg,long dayIds[],Date openTimings[],Date closeTimings[],String contactNo,String email,int isActive,int isGst,double gstGstPercentage,double deliveryCharges,String contactNo2,String contactNo3,String contactNo4,int deliveryTime,double discount,long createdBy) throws Exception {
	
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
		Resturants.setIsActive(isActive);
		Resturants.setActivatedBy(createdBy);
		Resturants.setActivatedOn(new Date());
		Resturants.setStatus(2);
		Resturants.setStatusChangedBy(createdBy);
		Resturants.setStatusChangedOn(new Date());
		Resturants.setIsGst(isGst);
		Resturants.setGstPercentage(gstGstPercentage);
		Resturants.setDeliveryCharges(deliveryCharges);
		if(contactNo2.equals("") && contactNo2!=null) {
			Resturants.setContactNo2(contactNo2);
		}
		
		if(contactNo3.equals("") && contactNo3!=null) {
			Resturants.setContactNo3(contactNo3);		
		}
		
		if(contactNo4.equals("") && contactNo4!=null) {
			Resturants.setContactNo4(contactNo4);
		}
		
		Resturants.setDeliveryTime(deliveryTime);
		Resturants.setDiscount(discount);
		
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
			if(Resturant!=null){
				Resturants.add(Resturant);
			}
			
			
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
						if(resturant.getContactNo2()!=null && !resturant.getContactNo2().equals("") )  {
							Row.put("contactNo2", resturant.getContactNo2());
						}else {
							Row.put("contactNo2", "");
						}
						
						if(resturant.getContactNo3()!=null && !resturant.getContactNo3().equals("") ) {
							Row.put("contactNo3", resturant.getContactNo3());
						}else {
							Row.put("contactNo3", "");
						}
						
						
						if(resturant.getContactNo4()!=null && !resturant.getContactNo4().equals("") ) {
							Row.put("contactNo4", resturant.getContactNo4());
						}else {
							Row.put("contactNo4", "");
						}
						
						
						
						Row.put("email", resturant.getEmail());
						Row.put("rating", resturant.getRating());
						Row.put("totalNumberOfRatings", resturant.getRating());
						Row.put("foodCategory", getMostlyAddedProductsCatgoryByResturant( resturant.getId()));
						Row.put("deliveryTime", utility.getCravingsDeliveryTime()+" mins");
						//Row.put("distance", utility.distanceCalculate(Resturants.get(i).getLatitude(), Resturants.get(i).getLongitude(), lat, lng, 'k')+"km away");
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
						Row.put("isGst", resturant.getIsGst());
						if(utility.parseInt(resturant.getIsGst())==1) {
							Row.put("isGstLabel","Yes" );
							Row.put("gstPercentage", resturant.getGstPercentage());
						}else {
							
							Row.put("isGstLabel","No" );
							Row.put("gstPercentage",0.0);
							
						}
						Row.put("isClosed", ResturantsTimingsService.checkResturantOpenStatus(resturant.getId()));
						Row.put("deliverCharges", resturant.getDeliveryCharges());
						Row.put("deliveryTime", utility.parseInt(resturant.getDeliveryTime()));
						Row.put("discount",utility.parseDouble(resturant.getDiscount()));
						
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
			if(resturant!=null) {
				Resturants.add(resturant);
			}
			
			
		}else {
			Resturants=ResturantsDao.findAllByIsActiveAndIsDeleted(1,0);
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
				Row.put("foodCategory", getMostlyAddedProductsCatgoryByResturant( resturant.getId()));
				if(resturant.getContactNo2()!=null && !resturant.getContactNo2().equals("") )  {
					Row.put("contactNo2", resturant.getContactNo2());
				}else {
					Row.put("contactNo2", "");
				}
				
				if(resturant.getContactNo3()!=null && !resturant.getContactNo3().equals("") ) {
					Row.put("contactNo3", resturant.getContactNo3());
				}else {
					Row.put("contactNo3", "");
				}
				
				
				if(resturant.getContactNo4()!=null && !resturant.getContactNo4().equals("") ) {
					Row.put("contactNo4", resturant.getContactNo4());
				}else {
					Row.put("contactNo4", "");
				}
				Row.put("deliveryTime", utility.getCravingsDeliveryTime()+" mins");
				Row.put("resturantDiscount", 10.0);
				Row.put("serviceFee", utility.getCravingsSericeFee());
				//Row.put("distance", utility.distanceCalculate(Resturants.get(i).getLatitude(), Resturants.get(i).getLongitude(), lat, lng, 'k')+"km away");
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
				Row.put("isClosed", ResturantsTimingsService.checkResturantOpenStatus(resturant.getId()));
				
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
					Row.put("isGst", resturant.getIsGst());
					if(utility.parseInt(resturant.getIsGst())==1) {
						Row.put("isGstLabel","Yes" );
						Row.put("gstPercentage", resturant.getGstPercentage());
					}else {
						
						Row.put("isGstLabel","No" );
						Row.put("gstPercentage",0.0);
						
					}
					Row.put("deliverCharges", resturant.getDeliveryCharges());
					if(resturant.getContactNo2()!=null && !resturant.getContactNo2().equals("") )  {
						Row.put("contactNo2", resturant.getContactNo2());
					}else {
						Row.put("contactNo2", "");
					}
					
					if(resturant.getContactNo3()!=null && !resturant.getContactNo3().equals("") ) {
						Row.put("contactNo3", resturant.getContactNo3());
					}else {
						Row.put("contactNo3", "");
					}
					
					
					if(resturant.getContactNo4()!=null && !resturant.getContactNo4().equals("") ) {
						Row.put("contactNo4", resturant.getContactNo4());
					}else {
						Row.put("contactNo4", "");
					}
					Row.put("isClosed", ResturantsTimingsService.checkResturantOpenStatus(resturant.getId()));
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
	
	
	public Map getBasicChargesDetailByResturant(Map Row,long resturantId) {
		CommonResturants Resturant=ResturantsDao.findById(resturantId);
		
		if(Resturant!=null) {
			
			Row.put("isGst", Resturant.getIsGst());
			if(utility.parseInt(Resturant.getIsGst())==1) {
				Row.put("gstPercentage", Resturant.getGstPercentage());
			}else {
				Row.put("gstPercentage", 0.0);
			}

			Row.put("deliverCharges", utility.parseDouble(Resturant.getDeliveryCharges()) );
			Row.put("serviceFee", utility.getCravingsSericeFee());
			
		}else {
			Row.put("isGst", 0);
			Row.put("gstPercentage", 0.0);
			Row.put("deliverCharges", 0.0);
			Row.put("serviceFee", utility.getCravingsSericeFee());
			
		}
		Row.put("deliveryTime", utility.getCravingsDeliveryTime());
		return Row;
	}

	
	
	

	public List<Map> viewSingleResturant(long recordId) {

		
		List<Map> list=new ArrayList<>();
	
		CommonResturants resturant=ResturantsDao.findByIdAndIsActiveAndIsDeleted(recordId,1,0);
		if(resturant!=null) {	
				
		
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
						if(resturant.getContactNo2()!=null && !resturant.getContactNo2().equals("") )  {
							Row.put("contactNo2", resturant.getContactNo2());
						}else {
							Row.put("contactNo2", "");
						}
						
						if(resturant.getContactNo3()!=null && !resturant.getContactNo3().equals("") ) {
							Row.put("contactNo3", resturant.getContactNo3());
						}else {
							Row.put("contactNo3", "");
						}
						
						
						if(resturant.getContactNo4()!=null && !resturant.getContactNo4().equals("") ) {
							Row.put("contactNo4", resturant.getContactNo4());
						}else {
							Row.put("contactNo4", "");
						}
						
						
						
						Row.put("email", resturant.getEmail());
						Row.put("rating", resturant.getRating());
						Row.put("totalNumberOfRatings", resturant.getRating());
						Row.put("foodCategory", getMostlyAddedProductsCatgoryByResturant( resturant.getId()));
						Row.put("deliveryTime", utility.getCravingsDeliveryTime()+" mins");
						//Row.put("distance", utility.distanceCalculate(Resturants.get(i).getLatitude(), Resturants.get(i).getLongitude(), lat, lng, 'k')+"km away");
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
						Row.put("isGst", resturant.getIsGst());
						if(utility.parseInt(resturant.getIsGst())==1) {
							Row.put("isGstLabel","Yes" );
							Row.put("gstPercentage", resturant.getGstPercentage());
						}else {
							
							Row.put("isGstLabel","No" );
							Row.put("gstPercentage",0.0);
							
						}
						Row.put("isClosed", ResturantsTimingsService.checkResturantOpenStatus(resturant.getId()));
						Row.put("deliverCharges", resturant.getDeliveryCharges());
						
						Row.put("resturantDiscount", resturant.getDiscount());
						Row.put("serviceFee", utility.getCravingsSericeFee());
						list.add(Row);
			
		}
		
		return list;
		
	}
	
	

	public List<Map> viewDropDown() {

		
		List<Map> list=new ArrayList<>();
	
		List<CommonResturants> Resturants=ResturantsDao.findAllByIsActiveAndIsDeleted(1,0);
				
				
		if(!Resturants.isEmpty()) {
			Resturants.stream().forEach(
				resturant->{
						Map Row=new HashMap<>();
						Row.put("id", resturant.getId());
						Row.put("label", resturant.getLabel());
						
						list.add(Row);
				}
				
			);
		}
		
		return list;
		
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
							Row.put("foodCategory", getMostlyAddedProductsCatgoryByResturant( resturant.getId()));
							Row.put("deliveryTime", utility.getCravingsDeliveryTime()+" mins");
							//Row.put("distance", utility.distanceCalculate(resturant.getLatitude(), resturant.getLongitude(), lat, lng, 'k')+"km away");
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
							Row.put("isGst", resturant.getIsGst());
							if(utility.parseInt(resturant.getIsGst())==1) {
								Row.put("isGstLabel","Yes" );
								Row.put("gstPercentage", resturant.getGstPercentage());
							}else {
								
								Row.put("isGstLabel","No" );
								Row.put("gstPercentage",0.0);
								
							}
							Row.put("deliverCharges", resturant.getDeliveryCharges());
							if(resturant.getContactNo2()!=null && !resturant.getContactNo2().equals("") )  {
								Row.put("contactNo2", resturant.getContactNo2());
							}else {
								Row.put("contactNo2", "");
							}
							
							if(resturant.getContactNo3()!=null && !resturant.getContactNo3().equals("") ) {
								Row.put("contactNo3", resturant.getContactNo3());
							}else {
								Row.put("contactNo3", "");
							}
							
							
							if(resturant.getContactNo4()!=null && !resturant.getContactNo4().equals("") ) {
								Row.put("contactNo4", resturant.getContactNo4());
							}else {
								Row.put("contactNo4", "");
							}
							Row.put("isClosed", "false");
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
	
	
//	public void saveResturantBanner(long recordId,Date startDate, Date endDate, long resturantId, String imageUrl) throws Exception {
//	
//		
//	}
	
	
	public void deleteResturantBanner(long promotionalBannerId) throws Exception {
		ResturantsPromotionalBannersDao.deleteById(promotionalBannerId);
		ResturantsPromotionalBannersDetailsDao.deleteByPromotionalBannerId(promotionalBannerId);
	}



	public long getResturantUserIdByResturantId(long resturantId) {
		long resturantUserId=utility.parseLong(UsersResturantsService.getUserIdByResturantId(resturantId));
	
		return resturantUserId;
	}



	public void savePromotionalBanner(long recordId, long resturantId, Date validFrom, Date validTo, int isActive,MultipartFile promotionImg,long createdBy) throws IOException {
		CommonResturantsPromotionalBanners Banner= new CommonResturantsPromotionalBanners();
		if(recordId!=0) {
			CommonResturantsPromotionalBannersDetail BannerDeatils= ResturantsPromotionalBannersDetailsDao.findById(recordId);
			if(BannerDeatils!=null) {
				Banner=ResturantsPromotionalBannersDao.findById(BannerDeatils.getPromotionalBannerId());
				
				ResturantsPromotionalBannersDetailsDao.deleteById(recordId);
			}
		}
		Banner.setStartDate(validFrom);
		Banner.setEndDate(validTo);
		Banner.setIsActive(isActive);
		Banner.setCreatedBy(createdBy);
		Banner.setCreatedOn(new Date());
		ResturantsPromotionalBannersDao.save(Banner);
		if(Banner.getId()!=0) {
			String TargetFileName="";
			CommonResturantsPromotionalBannersDetail BannerDeatils= new CommonResturantsPromotionalBannersDetail();
			BannerDeatils.setResturantId(resturantId);
			
			if (promotionImg != null && !promotionImg.getOriginalFilename().equals("")) {
				
				 TargetFileName = "promotional_banner_restr_"+resturantId+"_"+ utility.getUniqueId()
						
						+ promotionImg.getOriginalFilename().substring(promotionImg.getOriginalFilename().lastIndexOf("."));
				Path copyLocation = Paths.get(StringUtils.cleanPath(utility.getOpenFileDirectoryPath()+File.separator+TargetFileName));
				
				Files.copy(promotionImg.getInputStream(), copyLocation);
				
				
				
				
			} 
			BannerDeatils.setImageUrl(TargetFileName);
			BannerDeatils.setPromotionalBannerId(Banner.getId());
			ResturantsPromotionalBannersDetailsDao.save(BannerDeatils);
		}	
		
	}


	public List<Map> bannersViewResturantWise() {
		List<Map> list=new ArrayList<>();
		List<Long> resturantIds=ResturantsPromotionalBannersDetailsDao.findDistinctResturantId();
		if(!resturantIds.isEmpty()) {
			for(int i=0; i<resturantIds.size(); i++) {
				//Map Row=new HashMap<>();
				
				List<Map> listDetail=new ArrayList<>();
				List<customPromotionalBanner> PromotionalBannersDetail= ResturantsPromotionalBannersDetailsDao.findAllByResturantId(resturantIds.get(i));
				if(!PromotionalBannersDetail.isEmpty()) {
					for(int k=0;k<PromotionalBannersDetail.size();k++) {
						Map Row=new HashMap<>();
						Row.put("resturantId", resturantIds.get(i));
						Row.put("resturantLabel", findLabelById(resturantIds.get(i)));
						Row.put("id", PromotionalBannersDetail.get(k).getPbDetailId());
						Row.put("validFrom", PromotionalBannersDetail.get(k).getValidFrom());
						Row.put("validTo", PromotionalBannersDetail.get(k).getValidTo());
						Row.put("validFromToDisplay", utility.getDisplayDateddMMYYYY(PromotionalBannersDetail.get(k).getValidFrom()));
						Row.put("validToToDisplay", utility.getDisplayDateddMMYYYY(PromotionalBannersDetail.get(k).getValidTo()));
						Row.put("isActive", PromotionalBannersDetail.get(k).getisActive());
						Row.put("isActiveLabel", PromotionalBannersDetail.get(k).getisActive()==1? "Active": "In-Active");
						Row.put("fileName", PromotionalBannersDetail.get(k).getUrlImg());
						list.add(Row);
					}
				}
					
					
				//Row.put("detail", listDetail);
				
//				list.add(Row);
			}
		}
		
		return list;
	}
	
	
	public List<Map> bannersViewSingleResturant(long resturantId) {
		List<Map> list=new ArrayList<>();
		List<customPromotionalBanner> PromotionalBannersDetail= ResturantsPromotionalBannersDetailsDao.findAllByResturantId(resturantId);
		if(!PromotionalBannersDetail.isEmpty()) {
			for(int k=0;k<PromotionalBannersDetail.size();k++) {
				Map RowDetail=new HashMap<>();
				RowDetail.put("id", PromotionalBannersDetail.get(k).getPbDetailId());
				RowDetail.put("validFrom", PromotionalBannersDetail.get(k).getValidFrom());
				RowDetail.put("validTo", PromotionalBannersDetail.get(k).getValidTo());
				RowDetail.put("validFromToDisplay", utility.getDisplayDateddMMYYYY(PromotionalBannersDetail.get(k).getValidFrom()));
				RowDetail.put("validToToDisplay", utility.getDisplayDateddMMYYYY(PromotionalBannersDetail.get(k).getValidTo()));
				RowDetail.put("isActive", PromotionalBannersDetail.get(k).getisActive());
				RowDetail.put("isActiveLabel", PromotionalBannersDetail.get(k).getisActive()==1? "Active": "In-Active");
				RowDetail.put("fileName", PromotionalBannersDetail.get(k).getUrlImg());
				list.add(RowDetail);
			}
		}
		
		return list;
	}
	
	
	
	public List<Map> bannersViewRecordId(long recordId) {
		List<Map> list=new ArrayList<>();
		List<customPromotionalBanner> PromotionalBannersDetail= ResturantsPromotionalBannersDetailsDao.findAllByRecordId(recordId);
		if(!PromotionalBannersDetail.isEmpty()) {
			for(int k=0;k<PromotionalBannersDetail.size();k++) {
				Map RowDetail=new HashMap<>();
				RowDetail.put("id", PromotionalBannersDetail.get(k).getPbDetailId());
				RowDetail.put("validFrom", PromotionalBannersDetail.get(k).getValidFrom());
				RowDetail.put("validTo", PromotionalBannersDetail.get(k).getValidTo());
				RowDetail.put("validFromToDisplay", utility.getDisplayDateddMMYYYY(PromotionalBannersDetail.get(k).getValidFrom()));
				RowDetail.put("validToToDisplay", utility.getDisplayDateddMMYYYY(PromotionalBannersDetail.get(k).getValidTo()));
				RowDetail.put("isActive", PromotionalBannersDetail.get(k).getisActive());
				RowDetail.put("isActiveLabel", PromotionalBannersDetail.get(k).getisActive()==1? "Active": "In-Active");
				RowDetail.put("fileName", PromotionalBannersDetail.get(k).getUrlImg());
				RowDetail.put("resturantId", PromotionalBannersDetail.get(k).getResturantId());
				RowDetail.put("resturantLabel", findLabelById(PromotionalBannersDetail.get(k).getResturantId()));
				list.add(RowDetail);
			}
		}
		
		return list;
	}
	
	@Transactional
	public void deleteResturantBannerByDetailId(long recordId) throws Exception {
		CommonResturantsPromotionalBannersDetail BannerDeatils= ResturantsPromotionalBannersDetailsDao.findById(recordId);
		if(BannerDeatils!=null) {
			ResturantsPromotionalBannersDetailsDao.deleteById(recordId);
			ResturantsPromotionalBannersDao.deleteById(BannerDeatils.getPromotionalBannerId());
		}
		
		
	}

	
	/********************************************************************************************************/
	/************************************Resturant Owner Ends**********************************************/
	/********************************************************************************************************/
	
}
