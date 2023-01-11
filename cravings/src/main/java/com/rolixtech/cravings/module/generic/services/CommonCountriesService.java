package com.rolixtech.cravings.module.generic.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rolixtech.cravings.module.generic.model.CommonCountries;
import com.rolixtech.cravings.module.generic.model.CommonProvinces;
import com.rolixtech.cravings.module.users.dao.CommonCountriesDao;
import com.rolixtech.cravings.module.users.dao.CommonProvincesDao;
import com.rolixtech.cravings.module.users.dao.CommonRoleDao;
import com.rolixtech.cravings.module.users.models.CommonRole;

@Service
public class CommonCountriesService {

	
	@Autowired
	private CommonCountriesDao CountriesDao;
	
	
	public String findLabelById(long id) {
		CommonCountries Countries = CountriesDao.findById(id);
		if(Countries!=null) {
			 return Countries.getLabel();
		}else {
			return "";
		}
	    
	}
	
	public List<Map> getAll() {
		List<Map> list=new ArrayList<>();
		List<CommonCountries> Countries = CountriesDao.findAll();
		if(!Countries.isEmpty()) {
			Countries.stream().forEach(
					Country-> {
						Map Row=new HashMap<>();
						Row.put("id", Country.getId());
						Row.put("label", Country.getLabel());
						list.add(Row);
					}
			);
		}
		return list;
		
	}
	
}
