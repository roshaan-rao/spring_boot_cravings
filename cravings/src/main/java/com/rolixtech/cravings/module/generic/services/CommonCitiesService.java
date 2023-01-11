package com.rolixtech.cravings.module.generic.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rolixtech.cravings.module.generic.model.CommonCities;
import com.rolixtech.cravings.module.generic.model.CommonProvinces;
import com.rolixtech.cravings.module.users.dao.CommonCitiesDao;
import com.rolixtech.cravings.module.users.dao.CommonProvincesDao;
import com.rolixtech.cravings.module.users.dao.CommonRoleDao;
import com.rolixtech.cravings.module.users.models.CommonRole;

@Service
public class CommonCitiesService {

	
	@Autowired
	private CommonCitiesDao CitiesDao;
	
	
	public String findLabelById(long id) {
		CommonCities Province = CitiesDao.findById(id);
		if(Province!=null) {
			 return Province.getLabel();
		}else {
			return "";
		}
	    
	}
	
	public List<Map> getAllByProviceId(long countryId) {
		List<Map> list=new ArrayList<>();
		List<CommonCities> Cities = CitiesDao.findAllByProvinceId(countryId);
		if(!Cities.isEmpty()) {
			Cities.forEach(
					City-> {
						Map Row=new HashMap<>();
						Row.put("id", City.getId());
						Row.put("label", City.getLabel());
						list.add(Row);
					}
			);
		}
		return list;
		
	}
	
}
