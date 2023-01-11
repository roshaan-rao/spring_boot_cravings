package com.rolixtech.cravings.module.users.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rolixtech.cravings.module.generic.model.CommonCities;
import com.rolixtech.cravings.module.users.dao.CommonRoleDao;
import com.rolixtech.cravings.module.users.models.CommonRole;

@Service
public class CommonRoleService {

	
	@Autowired
	private CommonRoleDao roleDao;
	
	
	public CommonRole findByName(String name) {
	    CommonRole role = roleDao.findByName(name);
	    return role;
	}
	
	
	public List<Map> getAll() {
		List<Map> list=new ArrayList<>();
		List<CommonRole> Roles = roleDao.findAll();
		if(!Roles.isEmpty()) {
			Roles.forEach(
					Role-> {
						Map Row=new HashMap<>();
						Row.put("id", Role.getId());
						Row.put("label", Role.getName());
						list.add(Row);
					}
			);
		}
		return list;
		
	}
	
}
