package com.rolixtech.cravings.module.users.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rolixtech.cravings.module.generic.model.CommonCities;
import com.rolixtech.cravings.module.users.dao.CommonRoleDao;
import com.rolixtech.cravings.module.users.dao.CommonUsersStatusesDao;
import com.rolixtech.cravings.module.users.models.CommonRole;
import com.rolixtech.cravings.module.users.models.CommonUsersStatuses;

@Service
public class CommonUsersStatusesService {

	
	@Autowired
	private CommonUsersStatusesDao StatusesDao;
	
	
	public String findLabelById(long id) {
		String label="";
		CommonUsersStatuses statuses = StatusesDao.findById(id);
		if(statuses!=null) {
			label=statuses.getLabel();
		}
	    return label;
	}
	
	
	public List<Map> getAll() {
		List<Map> list=new ArrayList<>();
		List<CommonUsersStatuses> Roles = StatusesDao.findAll();
		if(!Roles.isEmpty()) {
			Roles.forEach(
					Role-> {
						Map Row=new HashMap<>();
						Row.put("id", Role.getId());
						Row.put("label", Role.getLabel());
						list.add(Row);
					}
			);
		}
		return list;
		
	}
	
}
