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
import com.rolixtech.cravings.module.resturant.dao.CommonResturantsProductsGroupTypesDao;
import com.rolixtech.cravings.module.resturant.model.CommonCategories;
import com.rolixtech.cravings.module.resturant.model.CommonResturants;
import com.rolixtech.cravings.module.resturant.model.CommonResturantsProductsGroupTypes;

@Service
public class CommonResturantsProductsGroupTypesService {
	
	@Autowired
	private CommonResturantsProductsGroupTypesDao ProductsGroupTypesDao; 
	
	public List<Map> getAll(){
		
		List<Map> list=new ArrayList<>();
		List<CommonResturantsProductsGroupTypes> ProductsGroupTypes=ProductsGroupTypesDao.findAll();
		if(!ProductsGroupTypes.isEmpty()) {
			ProductsGroupTypes.stream().forEach(
					productsGroupType->{
						Map Row=new HashMap<>();
						Row.put("id", productsGroupType.getId());
						Row.put("label", productsGroupType.getLabel());
						
						list.add(Row);
				}
			
			);
		}
		
		
		
		return list;
		
	}
	
	
	public String findLabelById(long id) {
		String label="";
		CommonResturantsProductsGroupTypes GroupType=ProductsGroupTypesDao.findById(id);
		if(GroupType!=null) {
			label=GroupType.getLabel();
		}
		return label;
	}

}
