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
import com.rolixtech.cravings.module.resturant.dao.CommonResturantsProductsAddOnSelectionTypeDao;
import com.rolixtech.cravings.module.resturant.dao.CommonResturantsProductsGroupTypesDao;
import com.rolixtech.cravings.module.resturant.model.CommonCategories;
import com.rolixtech.cravings.module.resturant.model.CommonResturants;
import com.rolixtech.cravings.module.resturant.model.CommonResturantsProductsAddOnSelectionType;
import com.rolixtech.cravings.module.resturant.model.CommonResturantsProductsGroupTypes;

@Service
public class CommonResturantsProductsAddOnSelectionTypeService {
	
	@Autowired
	private CommonResturantsProductsAddOnSelectionTypeDao AddOnSelectionTypeDao; 
	
	public List<Map> getAll(){
		
		List<Map> list=new ArrayList<>();
		List<CommonResturantsProductsAddOnSelectionType> AddOnSelectionTypes=AddOnSelectionTypeDao.findAll();
		if(!AddOnSelectionTypes.isEmpty()) {
			AddOnSelectionTypes.stream().forEach(
					AddOnSelectionType->{
						Map Row=new HashMap<>();
						Row.put("id", AddOnSelectionType.getId());
						Row.put("label", AddOnSelectionType.getLabel());
						
						list.add(Row);
				}
			
			);
		}
		
		
		
		return list;
		
	}
	
	
	public String findLabelById(long id) {
		String label="";
		CommonResturantsProductsAddOnSelectionType GroupType=AddOnSelectionTypeDao.findById(id);
		if(GroupType!=null) {
			label=GroupType.getLabel();
		}
		return label;
	}

}
