package com.rolixtech.cravings.module.users.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
