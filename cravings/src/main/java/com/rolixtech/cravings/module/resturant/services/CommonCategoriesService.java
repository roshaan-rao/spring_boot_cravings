package com.rolixtech.cravings.module.resturant.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Service;

import com.rolixtech.cravings.CravingsApplication;
import com.rolixtech.cravings.module.resturant.dao.CommonCategoriesDao;
import com.rolixtech.cravings.module.resturant.model.CommonCategories;
import com.rolixtech.cravings.module.resturant.model.CommonResturants;

@Service
public class CommonCategoriesService {
	
	@Autowired
	private CommonCategoriesDao CategoriesDao; 
	
	public List<Map> getAll(){
		
		List<Map> list=new ArrayList<>();
		List<CommonCategories> categories=CategoriesDao.findAll();
		if(!categories.isEmpty()) {
			categories.stream().forEach(
				category->{
						Map Row=new HashMap<>();
						Row.put("id", category.getId());
						Row.put("label", category.getLabel());
						Row.put("categoryImg", category.getCategoryImgUrl());
						Row.put("directory", category.getDirectoryUrl());
						list.add(Row);
				}
			
			);
		}
		
		
		
		return list;
		
	}
	
	
	public String findLabelById(long categoryId) {
		String label="";
		CommonCategories Categories=CategoriesDao.findById(categoryId);
		if(Categories!=null) {
			label=Categories.getLabel();
		}
		return label;
	}
	
	public void getAAAAAll(){
		
//		List<Map> list=new ArrayList<>();
//		List<CommonCategories> categories=CategoriesDao.findAll();
//		if(!categories.isEmpty()) {
//			categories.stream().forEach(
//				category->{
//						Map Row=new HashMap<>();
//						Row.put("id", category.getId());
//						Row.put("label", category.getLabel());
//						Row.put("categoryImg", category.getCategoryImgUrl());
//						Row.put("directory", category.getDirectoryUrl());
//						list.add(Row);
//				}
//			
//			);
//		}
//		
		
		
//		return list;
		
	}

}
