package com.rolixtech.cravings.module.resturant.services;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.rolixtech.cravings.module.generic.services.GenericUtility;
import com.rolixtech.cravings.module.resturant.dao.CommonResturantsDao;
import com.rolixtech.cravings.module.resturant.dao.CommonResturantsProductsDao;
import com.rolixtech.cravings.module.resturant.model.CommonResturants;
import com.rolixtech.cravings.module.resturant.model.CommonResturantsProducts;
import com.rolixtech.cravings.module.resturant.model.CommonResturantsProductsAddOnDetail;
import com.rolixtech.cravings.module.resturant.model.CommonResturantsTimings;

@Service
public class CommonResturantsProductsService {
	
	@Autowired
	private CommonResturantsProductsDao ResturantsProductsDao;	
	
	@Autowired
	private CommonResturantsDao ResturantsDao;
	
	
	@Autowired
	private CommonCategoriesService CategoriesService;
	
	@Autowired
	private CommonResturantsService ResturantsService;
	
	@Autowired
	private CommonResturantsStatusService StatusService;
	
	@Autowired
	private CommonResturantsProductsAddOnService ProductsAddOnService;
	
	@Autowired
	private CommonResturantsProductsExtrasService ProductsExtrasService;
	
	
	@Autowired
	private GenericUtility utility;
	
	
	@Autowired
	private CommonResturantsCategoriesService ResturantsCategoriesService;
	
	
	@Autowired
	private CommonResturantsProductsGroupTypesService ProductsGroupTypesService;
	
	@Autowired
	private CommonUsersFavProductsService FavProductsService;
	
	@Autowired
	private CommonResturantsTimingsService ResturantsTimingsService;
	
	
	public List<CommonResturantsProducts> findAllByLabelContaining(String keyword) {
		
		List<CommonResturantsProducts> ResturantsProduct=ResturantsProductsDao.findAllByLabelContainingAndIsActiveAndIsDeleted(keyword,1,0);
		if(ResturantsProduct.isEmpty()) {
			ResturantsProduct=new ArrayList<>();
		}
		
		return ResturantsProduct;
	}
	
	
	public String findLabelById(long productId) {
		String label="";
		CommonResturantsProducts Products=ResturantsProductsDao.findById(productId);
		if(Products!=null) {
			label=Products.getLabel();
		}
		return label;
	}

	
	@Transactional
	public void saveProduct(long recordId,String label,String description,long resturantId,double salesTax,double salesTaxPercentage,double grossAmount,double netAmount,double discount,
			double rate,int isTimingEnable,Date availabilityFrom,Date availabilityTo,int isAvailable,MultipartFile productImgUrl,long resturantCategoryId,int isExtra) throws Exception {
	
		CommonResturantsProducts Product=null;
		if(recordId==0) {
			Product=new CommonResturantsProducts();
		}else {
			Product=ResturantsProductsDao.findByIdAndIsDeleted(recordId,0);
		}
		
		Product.setLabel(label);
		Product.setDescription(description);
		Product.setResturantId(resturantId);
		Product.setSalesTax(salesTax);
		Product.setSalesTaxPercentage(salesTaxPercentage);
		Product.setGrossAmount(grossAmount);
		Product.setNetAmount(netAmount);
		Product.setIsTimingEnable(isTimingEnable);
		if(isTimingEnable==1) {
			Product.setAvailabilityFrom(availabilityFrom);
			Product.setAvailabilityTo(availabilityTo);
		}
		
		Product.setDiscount(discount);
		Product.setRate(rate);
		Product.setRating(5.0);
		Product.setIsActive(1);
		Product.setIsAvailable(isAvailable);
		Product.setResturantCategoryId(resturantCategoryId);
		Product.setIsExtra(isExtra);
		ResturantsProductsDao.save(Product);
		
		
		// specify an abstract pathname in the File object 
		if(recordId==0) {
			
			if (productImgUrl != null && !productImgUrl.getOriginalFilename().equals("")) {
				String TargetFileName = "product_"+Product.getId()+"_"+ utility.getUniqueId()
						
						+ productImgUrl.getOriginalFilename().substring(productImgUrl.getOriginalFilename().lastIndexOf("."));
				Path copyLocation = Paths.get(StringUtils.cleanPath(utility.getOpenFileDirectoryPath()+File.separator+TargetFileName));
				Files.copy(productImgUrl.getInputStream(), copyLocation);
				
				Product.setProductImgUrl(TargetFileName);
				
			}	
			
			
		}else {
			
			
			if (productImgUrl != null && !productImgUrl.getOriginalFilename().equals("")) {
				
				String OldFile=utility.getOpenFileDirectoryPath()+File.separator+Product.getProductImgUrl();
				File f = new File(OldFile); 
				
				if(f.delete()) {
					System.out.println("File Delteted");
				}else {
					System.out.println("File Could not be Delteted");
				}
				
				String TargetFileName = "product_"+Product.getId()+"_"+ utility.getUniqueId()
						
						+ productImgUrl.getOriginalFilename().substring(productImgUrl.getOriginalFilename().lastIndexOf("."));
				Path copyLocation = Paths.get(StringUtils.cleanPath(utility.getOpenFileDirectoryPath()+File.separator+TargetFileName));
				Files.copy(productImgUrl.getInputStream(), copyLocation);
				
				Product.setProductImgUrl(TargetFileName);
				
				
			} 
			
			
		}
		
		ResturantsProductsDao.save(Product);
		
		
	}
	
	
	public List<Map> viewCategoryWiseProducts(long recordId,long resturantId,long resturantCategoryId) {

		
		List<Map> list=new ArrayList<>();
	
		List<CommonResturantsProducts> Products=new ArrayList<>();
		if(recordId!=0) {
			CommonResturantsProducts Product=ResturantsProductsDao.findByIdAndIsAvailableAndIsDeleted(recordId,1,0);
			Products.add(Product);
			
		}else {
			Products=ResturantsProductsDao.findAllByResturantIdAndResturantCategoryIdAndIsExtraAndIsAvailableAndIsDeleted(resturantId,resturantCategoryId,0,1,0);
		}		
				
				
		if(!Products.isEmpty()) {
			Products.stream().forEach(
					Product->{	
					
						Map Row=new HashMap<>();
						Row.put("id", Product.getId());
						Row.put("productAddOns",ProductsAddOnService.getProductsAddOn(Product.getId()));
						
						Row.put("label",Product.getLabel());
						Row.put("description", Product.getDescription());
						Row.put("resturantId", Product.getResturantId());
						Row.put("salesTax", Product.getSalesTax());
						Row.put("salesTaxPercentage", Product.getSalesTaxPercentage());
						Row.put("grossAmount", Product.getGrossAmount());
						Row.put("netAmount", Product.getNetAmount());
						Row.put("discount", Product.getDiscount());
						Row.put("rate", Product.getRate());
						
						if(Product.getIsTimingEnable()==0) {
							Row.put("isTimingEnable", Product.getIsActive());
							Row.put("isTimingEnableLable", "No");
							Row.put("availabilityFrom", "");
							Row.put("availabilityTo", "");
							Row.put("isClosed", "false");
							
						}else {
							Row.put("isTimingEnable", Product.getIsActive());
							Row.put("isTimingEnableLable", "Yes");
							
							System.out.println(Product.getAvailabilityFrom());
							
							Row.put("availabilityFrom", DateUtils.addHours(Product.getAvailabilityFrom(), 5).getTime());
							
							try { 
								Row.put("availabilityFromDisplay", utility.millisecondstoTime(DateUtils.addHours(Product.getAvailabilityFrom(), 5).getTime()));
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								Row.put("availabilityFromDisplay", "00:00:00");
								
							}
							
							Row.put("availabilityTo", DateUtils.addHours(Product.getAvailabilityTo(), 5).getTime());
							
//							Row.put("isClosed", getIsClosedTimingsByProductId(Product.getId()));
//							Row.put("isClosed","false");
							
							if(ResturantsTimingsService.checkResturantOpenStatus(Product.getResturantId()).equals("false")) {
								Row.put("isClosed",getIsClosedTimingsByProductId(Product.getId()));
							}else {
								Row.put("isClosed","true");
							}
							
						}
						
						
						
						Row.put("isAvailable", Product.getIsAvailable());
						if(Product.getIsActive()==0) {
							Row.put("isActive", Product.getIsActive());
							Row.put("isActiveLabel", "In-Active");
						}else {
							Row.put("isActive", Product.getIsActive());
							Row.put("isActiveLabel", "Active");
						}
						Row.put("productImgUrl", Product.getProductImgUrl());
						Row.put("rating", Product.getRating());
						Row.put("resturantCategoryId", Product.getResturantCategoryId());
						Row.put("resturantCategoryLabel", ResturantsCategoriesService.findLabelById(Product.getResturantCategoryId()));
						if(Product.getIsExtra()==0) {
							Row.put("isExtraLabel","No");
							Row.put("isExtra",Product.getIsExtra());
						}else {
							Row.put("isExtraLabel","Yes");
							Row.put("isExtra",Product.getIsExtra());
							 
							
						}	
						
						list.add(ResturantsService.getBasicChargesDetailByResturant(Row,Product.getResturantId()));
				}
				
			);
		}
		
		return list;
		
	}
	
	public List<Map> view(long recordId,long resturantId) {

		
		List<Map> list=new ArrayList<>();
	
		List<CommonResturantsProducts> Products=new ArrayList<>();
		if(recordId!=0) {
			CommonResturantsProducts Product=ResturantsProductsDao.findByIdAndIsDeleted(recordId,0);
			Products.add(Product);
			
		}else {
			Products=ResturantsProductsDao.findAllByResturantIdAndIsDeleted(resturantId,0);
		}		
				
				
		if(!Products.isEmpty()) {
			Products.stream().forEach(
					Product->{	
					
						Map Row=new HashMap<>();
						Row.put("id", Product.getId());
						Row.put("productAddOns",ProductsAddOnService.getProductsAddOn(Product.getId()));
						Row.put("productExtras",ProductsExtrasService.getProductsExtras(Product.getId()));
						Row.put("label",Product.getLabel());
						Row.put("description", Product.getDescription());
						Row.put("resturantId", Product.getResturantId());
						Row.put("salesTax", Product.getSalesTax());
						Row.put("salesTaxPercentage", Product.getSalesTaxPercentage());
						Row.put("grossAmount", Product.getGrossAmount());
						Row.put("netAmount", Product.getNetAmount());
						Row.put("discount", Product.getDiscount());
						Row.put("rate", Product.getRate());
						//Row.put("isTimingEnable", Product.getIsTimingEnable());
						
						if(Product.getIsTimingEnable()==0) {
							Row.put("isTimingEnable", Product.getIsActive());
							Row.put("isTimingEnableLable", "No");
							Row.put("availabilityFrom", "");
							Row.put("availabilityTo", "");
							
						}else {
							Row.put("isTimingEnable", Product.getIsActive());
							Row.put("isTimingEnableLable", "Yes");
							
							System.out.println(Product.getAvailabilityFrom());
							
							Row.put("availabilityFrom", DateUtils.addHours(Product.getAvailabilityFrom(), 5).getTime());
							
							try { 
								Row.put("availabilityFromDisplay", utility.millisecondstoTime(DateUtils.addHours(Product.getAvailabilityFrom(), 5).getTime()));
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								Row.put("availabilityFromDisplay", "00:00:00");
								
							}
							
							Row.put("availabilityTo", DateUtils.addHours(Product.getAvailabilityTo(), 5).getTime());
							
							try { 
								Row.put("availabilityToDisplay", utility.millisecondstoTime(DateUtils.addHours(Product.getAvailabilityTo(), 5).getTime()));
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								Row.put("availabilityToDisplay", "00:00:00");
								
							}
							
//							Row.put("availabilityFrom", DateUtils.addHours(Product.getAvailabilityFrom(), 5).getTime());
//							
//							try { 
//								Row.put("availabilityFromDisplay", utility.millisecondstoTime(DateUtils.addHours(Product.getAvailabilityFrom(), 5).getTime()));
//							} catch (ParseException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//								Row.put("availabilityFromDisplay", "00:00:00");
//								
//							}
//							
//							Row.put("availabilityTo", DateUtils.addHours(Product.getAvailabilityTo(), 5).getTime());
//							
//							try { 
//								Row.put("availabilityToDisplay", utility.millisecondstoTime(DateUtils.addHours(Product.getAvailabilityTo(), 5).getTime()));
//							} catch (ParseException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//								Row.put("availabilityToDisplay", "00:00:00");
//								
//							}
						}
						
						
						
						Row.put("isAvailable", Product.getIsAvailable());
						if(Product.getIsActive()==0) {
							Row.put("isActive", Product.getIsActive());
							Row.put("isActiveLabel", "In-Active");
						}else {
							Row.put("isActive", Product.getIsActive());
							Row.put("isActiveLabel", "Active");
						}
						Row.put("productImgUrl", Product.getProductImgUrl());
						Row.put("rating", Product.getRating());
						Row.put("resturantCategoryId", Product.getResturantCategoryId());
						Row.put("resturantCategoryLabel", ResturantsCategoriesService.findLabelById(Product.getResturantCategoryId()));
						if(Product.getIsExtra()==0) {
							Row.put("isExtraLabel","No");
							Row.put("isExtra",Product.getIsExtra());
						}else {
							Row.put("isExtraLabel","Yes");
							Row.put("isExtra",Product.getIsExtra());
							 
							
						}
						Row.put("isClosed", "false");
						list.add(Row);
				}
				
			);
		}
		
		return list;
		
	}
	
	
	public List<Map> dropDownView(long resturantId) {

		
		List<Map> list=new ArrayList<>();
	
		List<CommonResturantsProducts> Products=new ArrayList<>();
		
		Products=ResturantsProductsDao.findAllByResturantIdAndIsExtraAndIsDeleted(resturantId,0,0);
				
		if(!Products.isEmpty()) {
			Products.stream().forEach(
				Product->{	
				
					Map Row=new HashMap<>();
					Row.put("id", Product.getId());
					Row.put("label",Product.getLabel());	
					Row.put("price",Product.getRate());	
					list.add(Row);
				}
				
			);
		}
		
		return list;
		
	}
	
	
	public List<Map> addOnDropDownView(long resturantId) {

		List<Map> listAllProducts=new ArrayList<>();
		List<Map> listProducts=new ArrayList<>();
	
		List<CommonResturantsProducts> Products=new ArrayList<>();
				
		Products=ResturantsProductsDao.findAllByResturantIdAndIsExtraAndIsDeleted(resturantId,0,0);	
		if(!Products.isEmpty()) {
			Products.stream().forEach(
				Product->{
				
					Map Row=new HashMap<>();
					Row.put("productId", Product.getId());
					Row.put("productLabel","Normal - "+Product.getLabel());
					Row.put("price",Product.getGrossAmount());
					
					listProducts.add(Row);
				}
				
			);
			listAllProducts.addAll(listProducts);
		}
		
		List<Map> listExtraProducts=new ArrayList<>();
		
		List<CommonResturantsProducts> ExtraProducts=new ArrayList<>();
		ExtraProducts=ResturantsProductsDao.findAllByResturantIdAndIsExtraAndIsDeleted(resturantId,1,0);	
		if(!ExtraProducts.isEmpty()) {
			ExtraProducts.stream().forEach(
				Product->{
					Map Row=new HashMap<>();
					Row.put("productId", Product.getId());
					Row.put("productLabel","Extra - "+Product.getLabel());
					Row.put("price",Product.getGrossAmount());
					listExtraProducts.add(Row);
				}
				
			);
			
			listAllProducts.addAll(listExtraProducts);
		}
		
		
		
		return listAllProducts;
		
	}
	
	
	
	public List<Map> specificProductaddOnDropDownView(long productId) {

		List<Map> listAllProducts=new ArrayList<>();
		List<Map> listProducts=new ArrayList<>();
		
		List<Long> addOnIds=ProductsAddOnService.getProductsAddOnIdByProductId(productId);
		if(!addOnIds.isEmpty()) {
			listAllProducts=ProductsAddOnService.getProductsAddOnDetailByProductId(addOnIds);
		}
		
		
		return listAllProducts;
		
	}
	
	
	public List<Map> addOnProductsGroupsTypesDropDownView() {

		
		List<Map> list=new ArrayList<>();
		
		list=ProductsGroupTypesService.getAll();
		return list;
	}

	@Transactional
	public void ChangeActiveStatus(long productId, int isActive,long createdBy) {
		CommonResturantsProducts Products=ResturantsProductsDao.findById(productId);
		if(Products!=null) {
			Products.setIsActive(isActive);
//			Products.setActivatedOn(new Date());
//			Products.setActivatedBy(createdBy);
			ResturantsProductsDao.save(Products);
		}
		
	}
	
	@Transactional
	public void deleteProduct(long productId, long createdBy) {
		CommonResturantsProducts Products=ResturantsProductsDao.findById(productId);
		if(Products!=null) {
			Products.setIsDeleted(1);
			ResturantsProductsDao.save(Products);
		}
		
	}


	public Object getAllResturantProducts(long resturantId,long resturantCategoryId) {
		List<Map> list=new ArrayList<>();
		
		List<CommonResturantsProducts> Products=new ArrayList<>();
		if(resturantId!=0) {
			Products=ResturantsProductsDao.findAllByResturantIdAndResturantCategoryIdAndIsExtraAndIsAvailableAndIsDeleted(resturantId,resturantCategoryId,0,1,0);
			
		}		
				
				
		if(!Products.isEmpty()) {
			Products.stream().forEach(
					Product->{	
					
						Map Row=new HashMap<>();
						Row.put("id", Product.getId());
						Row.put("productAddOns",ProductsAddOnService.getProductsAddOn(Product.getId()));
						Row.put("label",Product.getLabel());
						Row.put("description", Product.getDescription());
						Row.put("resturantId", Product.getResturantId());
						Row.put("salesTax", Product.getSalesTax());
						Row.put("salesTaxPercentage", Product.getSalesTaxPercentage());
						Row.put("grossAmount", Product.getGrossAmount());
						Row.put("netAmount", Product.getNetAmount());
						Row.put("discount", Product.getDiscount());
						Row.put("rate", Product.getRate());
						//Row.put("isTimingEnable", Product.getIsTimingEnable());
						
						if(Product.getIsTimingEnable()==0) {
							Row.put("isTimingEnable", Product.getIsActive());
							Row.put("isTimingEnableLable", "No");
							Row.put("availabilityFrom", "");
							Row.put("availabilityTo", "");
							Row.put("isClosed","false");
							
						}else {
							Row.put("isTimingEnable", Product.getIsActive());
							Row.put("isTimingEnableLable", "Yes");
							
							System.out.println(Product.getAvailabilityFrom());
							
							Row.put("availabilityFrom", DateUtils.addHours(Product.getAvailabilityFrom(), 5).getTime());
							
							try { 
								Row.put("availabilityFromDisplay", utility.millisecondstoTime(DateUtils.addHours(Product.getAvailabilityFrom(), 5).getTime()));
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								Row.put("availabilityFromDisplay", "00:00:00");
								
							}
							
							Row.put("availabilityTo", DateUtils.addHours(Product.getAvailabilityTo(), 5).getTime());
							if(ResturantsTimingsService.checkResturantOpenStatus(Product.getResturantId()).equals("false")) {
								Row.put("isClosed",getIsClosedTimingsByProductId(Product.getId()));
							}else {
								Row.put("isClosed","true");
							}
						}
						
						
						
						Row.put("isAvailable", Product.getIsAvailable());
						if(Product.getIsActive()==0) {
							Row.put("isActive", Product.getIsActive());
							Row.put("isActiveLabel", "In-Active");
						}else {
							Row.put("isActive", Product.getIsActive());
							Row.put("isActiveLabel", "Active");
						}
						Row.put("productImgUrl", Product.getProductImgUrl());
						Row.put("rating", Product.getRating());
						Row.put("resturantCategoryId", Product.getResturantCategoryId());
						Row.put("resturantCategoryLabel", ResturantsCategoriesService.findLabelById(Product.getResturantCategoryId()));
						if(Product.getIsExtra()==0) {
							Row.put("isExtraLabel","No");
							Row.put("isExtra",Product.getIsExtra());
						}else {
							Row.put("isExtraLabel","Yes");
							Row.put("isExtra",Product.getIsExtra());
							 
							
						}	
						
						
						list.add(Row);
				}
				
			);
		}
		
		return list;
		
	}


	public boolean checkIfProductIsActive(long productId) {
		boolean isAvaliable=false;
		CommonResturantsProducts Products=ResturantsProductsDao.findByIdAndIsActiveAndIsDeleted(productId,1,0);
		if(Products!=null) {
			isAvaliable=true;
		}
		return isAvaliable;
	}

	


	public String getMostlyAddedResturantCatoryIdByResturantID(long resturantId) {
		String purpose="";
		if(utility.parseLong(ResturantsProductsDao.findResturantCategoryIdByResturantIdAndIsDeleted(resturantId,0))!=0) {
			long resturantCatoryId=utility.parseLong(ResturantsProductsDao.findResturantCategoryIdByResturantIdAndIsDeleted(resturantId,0));
			if(resturantCatoryId!=0) {
				long categoryId=utility.parseLong(ResturantsCategoriesService.getCategoryByResturantCatoryId(resturantCatoryId));
				if(categoryId!=0) {
					purpose=CategoriesService.findLabelById(categoryId);
				}
			}
		}
		return purpose;
	}
	
	
	public List<Map> viewSingleProduct(long recordId,long userId) {

		 
		List<Map> list=new ArrayList<>();
	
		List<CommonResturantsProducts> Products=new ArrayList<>();
		if(recordId!=0) {
			CommonResturantsProducts Product=ResturantsProductsDao.findByIdAndIsAvailableAndIsDeleted(recordId,1,0);
			if(Product!=null) {
				Products.add(Product);
			}
			
			
		}		
				
				
		if(!Products.isEmpty()) {
			Products.stream().forEach(
					Product->{	
					
						Map Row=new HashMap<>();
						Row.put("isLiked", FavProductsService.isProductLiked(Product.getId(), userId));
						Row.put("id", Product.getId());
						Row.put("productAddOns",ProductsAddOnService.getProductsAddOn(Product.getId()));
						Row.put("label",Product.getLabel());
						Row.put("description", Product.getDescription());
						Row.put("resturantId", Product.getResturantId());
						Row.put("salesTax", Product.getSalesTax());
						Row.put("salesTaxPercentage", Product.getSalesTaxPercentage());
						Row.put("grossAmount", Product.getGrossAmount());
						Row.put("netAmount", Product.getNetAmount());
						Row.put("discount", Product.getDiscount());
						Row.put("rate", Product.getRate());
						
						
						if(Product.getIsTimingEnable()==0) {
							Row.put("isTimingEnable", Product.getIsActive());
							Row.put("isTimingEnableLable", "No");
							Row.put("availabilityFrom", "");
							Row.put("availabilityTo", "");
							Row.put("isClosed","false");
							
						}else {
							Row.put("isTimingEnable", Product.getIsActive());
							Row.put("isTimingEnableLable", "Yes");
							
							System.out.println(Product.getAvailabilityFrom());
							
							Row.put("availabilityFrom", DateUtils.addHours(Product.getAvailabilityFrom(), 5).getTime());
							
							try { 
								Row.put("availabilityFromDisplay", utility.millisecondstoTime(DateUtils.addHours(Product.getAvailabilityFrom(), 5).getTime()));
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								Row.put("availabilityFromDisplay", "00:00:00");
								
							}
							
							Row.put("availabilityTo", DateUtils.addHours(Product.getAvailabilityTo(), 5).getTime());
							System.out.println(ResturantsTimingsService.checkResturantOpenStatus(Product.getResturantId()));
							if(ResturantsTimingsService.checkResturantOpenStatus(Product.getResturantId()).equals("false")) {
								Row.put("isClosed",getIsClosedTimingsByProductId(Product.getId()));
							}else {
								Row.put("isClosed","true");
							}
							//Row.put("isClosed", getIsClosedTimingsByProductId(Product.getId()));
						}
						
						
						
						Row.put("isAvailable", Product.getIsAvailable());
						if(Product.getIsActive()==0) {
							Row.put("isActive", Product.getIsActive());
							Row.put("isActiveLabel", "In-Active");
						}else {
							Row.put("isActive", Product.getIsActive());
							Row.put("isActiveLabel", "Active");
						}
						Row.put("productImgUrl", Product.getProductImgUrl());
						Row.put("rating", Product.getRating());
						Row.put("resturantCategoryId", Product.getResturantCategoryId());
						Row.put("resturantCategoryLabel", ResturantsCategoriesService.findLabelById(Product.getResturantCategoryId()));
						if(Product.getIsExtra()==0) {
							Row.put("isExtraLabel","No");
							Row.put("isExtra",Product.getIsExtra());
						}else {
							Row.put("isExtraLabel","Yes");
							Row.put("isExtra",Product.getIsExtra());
							 
							
						}
						//Row.put("isClosed", "false");
						list.add(ResturantsService.getBasicChargesDetailByResturant(Row,Product.getResturantId()));
				}
				
			);
		}
		
		return list;
		
	}
	
	
	
	@Transactional
	public void saveOrder(long recordId,String label,String description,long resturantId,double salesTax,double salesTaxPercentage,double grossAmount,double netAmount,double discount,
			double rate,int isTimingEnable,Date availabilityFrom,Date availabilityTo,int isAvailable,MultipartFile productImgUrl,long resturantCategoryId,int isExtra) throws Exception {
	
		CommonResturantsProducts Product=null;
		if(recordId==0) {
			Product=new CommonResturantsProducts();
		}else {
			Product=ResturantsProductsDao.findByIdAndIsDeleted(recordId,0);
		}
		
		Product.setLabel(label);
		Product.setDescription(description);
		Product.setResturantId(resturantId);
		Product.setSalesTax(salesTax);
		Product.setSalesTaxPercentage(salesTaxPercentage);
		Product.setGrossAmount(grossAmount);
		Product.setNetAmount(netAmount);
		Product.setIsTimingEnable(isTimingEnable);
		if(isTimingEnable==1) {
			Product.setAvailabilityFrom(availabilityFrom);
			Product.setAvailabilityTo(availabilityTo);
		}
		
		Product.setDiscount(discount);
		Product.setRate(rate);
		Product.setRating(5.0);
		Product.setIsActive(1);
		Product.setIsAvailable(isAvailable);
		Product.setResturantCategoryId(resturantCategoryId);
		Product.setIsExtra(isExtra);
		ResturantsProductsDao.save(Product);
		
		
		// specify an abstract pathname in the File object 
		if(recordId==0) {
			
			if (productImgUrl != null && !productImgUrl.getOriginalFilename().equals("")) {
				String TargetFileName = "product_"+Product.getId()+"_"+ utility.getUniqueId()
						
						+ productImgUrl.getOriginalFilename().substring(productImgUrl.getOriginalFilename().lastIndexOf("."));
				Path copyLocation = Paths.get(StringUtils.cleanPath(utility.getOpenFileDirectoryPath()+File.separator+TargetFileName));
				Files.copy(productImgUrl.getInputStream(), copyLocation);
				
				Product.setProductImgUrl(TargetFileName);
				
			}	
			
			
		}else {
			
			
			if (productImgUrl != null && !productImgUrl.getOriginalFilename().equals("")) {
				
				String OldFile=utility.getOpenFileDirectoryPath()+File.separator+Product.getProductImgUrl();
				File f = new File(OldFile); 
				
				if(f.delete()) {
					System.out.println("File Delteted");
				}else {
					System.out.println("File Could not be Delteted");
				}
				
				String TargetFileName = "product_"+Product.getId()+"_"+ utility.getUniqueId()
						
						+ productImgUrl.getOriginalFilename().substring(productImgUrl.getOriginalFilename().lastIndexOf("."));
				Path copyLocation = Paths.get(StringUtils.cleanPath(utility.getOpenFileDirectoryPath()+File.separator+TargetFileName));
				Files.copy(productImgUrl.getInputStream(), copyLocation);
				
				Product.setProductImgUrl(TargetFileName);
				
				
			} 
			
			
		}
		
		ResturantsProductsDao.save(Product);
		
		
	}
	
	
	
	@Transactional
	public void saveAddOnProduct(long recordId,long productId,String groupTitle,long groupTypeId,long selectionTypeId,int  minSelection,int maxSelection,long addOnProductId[],double[] price) throws Exception {
	
		if(addOnProductId!=null) {
			
				ProductsAddOnService.SaveProductsAddOn(recordId,productId, groupTitle,groupTypeId,selectionTypeId,minSelection,maxSelection,addOnProductId,price);
			
		}
		
	}
	
	
	@Transactional
	public List<Map> viewAddOnProducts(long id,long resturantId) throws Exception {
		List<Map> list=new ArrayList<>();
		if(id!=0) {
			
			list=ProductsAddOnService.getProductAddOnGroup(id);
			
		}else {
			List<Long> productIds=new ArrayList<>();
			productIds=ResturantsProductsDao.findAllByResturantIdAndIsExtraAndIsActiveAndIsDeleted(resturantId,0,1,0);
			if(!productIds.isEmpty()) {
				list=ProductsAddOnService.getAllProductAddOnGroup(productIds);
			}
		}
		return list;
		
	}
	
	@Transactional
	public void deleteAddOn(long recordId) {
		if(recordId!=0) {
			ProductsAddOnService.deleteProductAddOnGroup(recordId);
		}
	}


	public List<CommonResturantsProducts> findAllByLabelContainingAndIsActiveAndIsDeleted(String keyword, int i,int j) {
		// TODO Auto-generated method stub
		return ResturantsProductsDao.findAllByLabelContainingAndIsActiveAndIsDeleted(keyword, i, j);
	}


	public double getProuctRateByProductId(long productId) {
		double productPrice=0;
		CommonResturantsProducts ResturantsProducts=ResturantsProductsDao.findById(productId);
		if(ResturantsProducts!=null) {
			productPrice=ResturantsProducts.getRate();
		}
		return productPrice;
	} 
	
	
	public String getIsClosedTimingsByProductId(long productId) {
		String isClosed="true";
		//CommonResturantsProducts ResturantsProducts=ResturantsProductsDao.findByIdAndIsAvailableAndIsDeletedAndCurrentDateBetweenAvailabilityFromAndAvailabilityTo(productId, 1, 0);
		CommonResturantsProducts resturantProduct=ResturantsProductsDao.findByIdAndIsAvailableAndIsDeletedAndIsActive(productId, 1, 0,1);
		if(resturantProduct!=null) {
			if(resturantProduct.getIsTimingEnable()==1) {
				String TimeScale= ResturantsProductsDao.isClosingTimeOfProductIsPMOrAM(productId);
				if(TimeScale!=null) {
					if(TimeScale.equals("PM")) {
						CommonResturantsProducts ProductsTimings=ResturantsProductsDao.isOpenResturantNowWithInPMAndPM(productId);
						if(ProductsTimings!=null) {
							isClosed="false";
							
						}
					}else if(TimeScale.equals("AM")){
						CommonResturantsProducts ProductsTimings=ResturantsProductsDao.isOpenProductNowWithInPMAndAM(productId);
						if(ProductsTimings!=null) {
							isClosed="false";
							
						}
					}
				}
			}else {
				isClosed="true";
			}
			
		}
		
		
		
		
		
		
		
		return isClosed;
	} 

	

}
