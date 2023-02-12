package com.rolixtech.cravings.module.cravings.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rolixtech.cravings.module.cravings.dao.CravingsPromotionalVouchersPurposeTypesDao;
import com.rolixtech.cravings.module.cravings.dao.CravingsPromotionalVouchersStatusTypesDao;
import com.rolixtech.cravings.module.cravings.dao.CravingsPromotionalVouchersValueTypesDao;
import com.rolixtech.cravings.module.cravings.model.CravingsPromotionalVouchersPurposeTypes;
import com.rolixtech.cravings.module.cravings.model.CravingsPromotionalVouchersStatusTypes;
import com.rolixtech.cravings.module.cravings.model.CravingsPromotionalVouchersValueTypes;
import com.rolixtech.cravings.module.generic.dao.CommonCitiesDao;
import com.rolixtech.cravings.module.generic.dao.CommonProvincesDao;
import com.rolixtech.cravings.module.generic.model.CommonCities;
import com.rolixtech.cravings.module.generic.model.CommonProvinces;
import com.rolixtech.cravings.module.users.dao.CommonRoleDao;
import com.rolixtech.cravings.module.users.models.CommonRole;

@Service
public class CravingsPromotionalVouchersPurposeTypesService {

	
	@Autowired
	private CravingsPromotionalVouchersPurposeTypesDao VouchersPurposeTypesDao;
	
	
	public String findLabelById(long id) {
		CravingsPromotionalVouchersPurposeTypes types = VouchersPurposeTypesDao.findById(id);
		if(types!=null) {
			 return types.getLabel();
		}else {
			return "";
		}
	    
	}
	
	public List<Map> getAll() {
		List<Map> list=new ArrayList<>();
		List<CravingsPromotionalVouchersPurposeTypes> types = VouchersPurposeTypesDao.findAll();
		if(!types.isEmpty()) {
			types.forEach(
					type-> {
						Map Row=new HashMap<>();
						Row.put("id", type.getId());
						Row.put("label", type.getLabel());
						list.add(Row);
					}
			);
		}
		return list;
		
	}
	
}
