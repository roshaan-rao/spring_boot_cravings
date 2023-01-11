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
import com.rolixtech.cravings.module.resturant.dao.CommonResturantsStatusDao;
import com.rolixtech.cravings.module.resturant.model.CommonCategories;
import com.rolixtech.cravings.module.resturant.model.CommonResturantsStatus;
import com.rolixtech.cravings.module.users.models.CommonRole;

@Service
public class CommonResturantsStatusService {
	
	@Autowired
	private CommonResturantsStatusDao StatusDao; 
	
	public List<Map> getAll(){
		
		List<Map> list=new ArrayList<>();
		List<CommonResturantsStatus> statuses=StatusDao.findAll();
		if(!statuses.isEmpty()) {
			statuses.stream().forEach(
					status->{
						Map Row=new HashMap<>();
						Row.put("id", status.getId());
						Row.put("label", status.getLabel());
						
						list.add(Row);
				}
			
			);
		}
		
		
		
		return list;
		
	}
	
	public String findLabelById(long id) {
		String label="";
	    CommonResturantsStatus Status = StatusDao.findById(id);
	    if(Status!=null) {
	    	label=Status.getLabel();
	    }
	    return label;
	}
	
	
	


}
