package com.rolixtech.cravings.module.generic.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rolixtech.cravings.module.generic.dao.CommonProvincesDao;
import com.rolixtech.cravings.module.generic.model.CommonProvinces;
import com.rolixtech.cravings.module.users.dao.CommonRoleDao;
import com.rolixtech.cravings.module.users.models.CommonRole;

@Service
public class CommonProvincesService {

	
	@Autowired
	private CommonProvincesDao ProvincesDao;
	
	
	public String findLabelById(long id) {
		CommonProvinces Province = ProvincesDao.findById(id);
		if(Province!=null) {
			 return Province.getLabel();
		}else {
			return "";
		}
	    
	}
	
	public List<Map> getAllByCountryId(long countryId) {
		List<Map> list=new ArrayList<>();
		List<CommonProvinces> Provinces = ProvincesDao.findAllByCountryId(countryId);
		if(!Provinces.isEmpty()) {
			Provinces.forEach(
					Province-> {
						Map Row=new HashMap<>();
						Row.put("id", Province.getId());
						Row.put("label", Province.getLabel());
						list.add(Row);
					}
			);
		}
		return list;
		
	}
	
}
