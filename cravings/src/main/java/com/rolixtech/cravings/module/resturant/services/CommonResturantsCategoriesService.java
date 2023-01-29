package com.rolixtech.cravings.module.resturant.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Service;

import com.rolixtech.cravings.CravingsApplication;
import com.rolixtech.cravings.module.resturant.dao.CommonCategoriesDao;
import com.rolixtech.cravings.module.resturant.dao.CommonResturantsCategoriesDao;
import com.rolixtech.cravings.module.resturant.model.CommonCategories;
import com.rolixtech.cravings.module.resturant.model.CommonResturants;
import com.rolixtech.cravings.module.resturant.model.CommonResturantsCategories;
import com.rolixtech.cravings.module.resturant.model.CommonResturantsStatus;
import com.rolixtech.cravings.module.resturant.model.CommonResturantsTimings;

@Service
public class CommonResturantsCategoriesService {
	
	@Autowired
	private CommonResturantsCategoriesDao ResturantsCategoriesDao; 
	
	@Autowired
	private CommonCategoriesService CategoriesService; 
	
	@Autowired
	private CommonResturantsProductsService ProductsService; 
	
	public List<Map> getAll(long recordId,long resturantId){
		
		List<Map> list=new ArrayList<>();
		List<CommonResturantsCategories> categories=new ArrayList<>();
		if(recordId!=0) {
			CommonResturantsCategories catObj=ResturantsCategoriesDao.findByIdAndIsDeleted(recordId,0);
			categories.add(catObj);
					
		}else {
			categories=ResturantsCategoriesDao.findAllByResturantIdAndIsDeleted(resturantId,0);
		}
		 
		if(!categories.isEmpty()) {
			categories.stream().forEach(
				category->{
						Map Row=new HashMap<>();
						Row.put("id", category.getId());
						Row.put("label", category.getLabel());
						Row.put("categoryId", category.getCategoryId());
						
						Row.put("categoryLabel", CategoriesService.findLabelById(category.getCategoryId()));
						//Row.put("resturantId", category.getResturantId());
						Row.put("isActive", category.getIsActive());
						if(category.getIsActive()==0) {
							
							Row.put("isActiveLabel", "In-Active");
						}else {
							
							Row.put("isActiveLabel", "Active");
						}
						list.add(Row); 
				}
			
			);
		} 
		return list;
		
	}
	
	
	 
	public void SaveResturantsCategories(long recordId ,String label,long resturantId,long categoryId,long createdBy){
		
		CommonResturantsCategories ResturantsCategories=null;
		if(recordId!=0) {
			 ResturantsCategories=ResturantsCategoriesDao.findById(recordId);
			
		}else {
			
			ResturantsCategories=new CommonResturantsCategories();
		}
		
		if(ResturantsCategories!=null) {
			
			ResturantsCategories.setCategoryId(categoryId);
			ResturantsCategories.setResturantId(resturantId);
			ResturantsCategories.setLabel(label);
			ResturantsCategories.setIsActive(1);
			
		}
		ResturantsCategoriesDao.save(ResturantsCategories);
		
		
	}
	
	public void changeResturantCategoryStatus(long recordId ,int isActive,long createdBy){
		
		CommonResturantsCategories ResturantsCategories=ResturantsCategoriesDao.findById(recordId);
		if(ResturantsCategories!=null) {
			ResturantsCategories.setIsActive(isActive);			
			ResturantsCategoriesDao.save(ResturantsCategories);
		}
		
	}
	
	public void deleteResturantCategory(long recordId ,long createdBy){
		
		CommonResturantsCategories ResturantsCategories=ResturantsCategoriesDao.findById(recordId);
		if(ResturantsCategories!=null) {
			ResturantsCategories.setIsDeleted(1);		
			ResturantsCategoriesDao.save(ResturantsCategories);
		}
		
	}



	public List<Map> getAllDropDown(Long resturantId) {
		List<Map> list=new ArrayList<>();
		List<CommonResturantsCategories> categories=ResturantsCategoriesDao.findAll();
		
		categories=ResturantsCategoriesDao.findAllByResturantIdAndIsActive(resturantId,1);
		
		
		if(!categories.isEmpty()) {
			categories.stream().forEach(
				category->{
						Map Row=new HashMap<>();
						Row.put("id", category.getId());
						Row.put("label", category.getLabel());
						Row.put("categoryId", category.getCategoryId());
						
						Row.put("categoryLabel", CategoriesService.findLabelById(category.getCategoryId()));
						//Row.put("resturantId", category.getResturantId());
						Row.put("isActive", category.getIsActive());
						if(category.getIsActive()==0) {
							
							Row.put("isActiveLabel", "In-Active");
						}else {
							
							Row.put("isActiveLabel", "Active");
						}
						list.add(Row); 
				}
			
			);
		} 
		return list;
	}
	
	
	public List<Map> getAllCategoriesWithProducts(long resturantId) {
		List<Map> list=new ArrayList<>();
		List<CommonResturantsCategories> categories=ResturantsCategoriesDao.findAllByResturantIdAndIsActiveAndIsDeleted(resturantId,1,0);
		if(!categories.isEmpty()) {
			categories.stream().forEach(
				category->{
						Map Row=new HashMap<>();
						Row.put("id", category.getId());
						Row.put("label", category.getLabel());
						Row.put("products", ProductsService.getAllResturantProducts(resturantId,category.getId()));
						
						
						list.add(Row); 
				}
			
			);
		} 
		return list;
	}



	public String findLabelById(long id) {
		String label="";
		CommonResturantsCategories Status = ResturantsCategoriesDao.findById(id);
	    if(Status!=null) {
	    	label=Status.getLabel();
	    }
	    return label;
	}



	public Long  getCategoryByResturantCatoryId(long resturantCatoryId) {
		Long categoryId=0l;
		CommonResturantsCategories ResturantsCategories=null;
		if(resturantCatoryId!=0) {
			 ResturantsCategories=ResturantsCategoriesDao.findById(resturantCatoryId);
			 if(ResturantsCategories!=null) {
				 categoryId= ResturantsCategories.getCategoryId();
			 }
		}
		
		return categoryId;
	}
	
	public long  getCommonCategoryLabelByResturantCatoryId(long resturantCatoryId) {
		Long categoryId=0l;
		CommonResturantsCategories ResturantsCategories=null;
		if(resturantCatoryId!=0) {
			 ResturantsCategories=ResturantsCategoriesDao.findById(resturantCatoryId);
			 if(ResturantsCategories!=null) {
				 categoryId= ResturantsCategories.getCategoryId();
			 }
		}
		
		return categoryId;
	}
	
	


}
