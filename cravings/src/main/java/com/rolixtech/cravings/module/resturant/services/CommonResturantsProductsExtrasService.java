package com.rolixtech.cravings.module.resturant.services;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.aspectj.apache.bcel.classfile.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Service;

import com.rolixtech.cravings.CravingsApplication;
import com.rolixtech.cravings.module.generic.services.GenericUtility;
import com.rolixtech.cravings.module.resturant.dao.CommonCategoriesDao;
import com.rolixtech.cravings.module.resturant.dao.CommonResturantsProductsAddOnDao;
import com.rolixtech.cravings.module.resturant.dao.CommonResturantsProductsExtrasDao;
import com.rolixtech.cravings.module.resturant.dao.CommonResturantsTimingsDao;
import com.rolixtech.cravings.module.resturant.dao.CommonUsersResturantsDao;
import com.rolixtech.cravings.module.resturant.model.CommonCategories;
import com.rolixtech.cravings.module.resturant.model.CommonResturantsCategories;
import com.rolixtech.cravings.module.resturant.model.CommonResturantsProductsAddOn;
import com.rolixtech.cravings.module.resturant.model.CommonResturantsProductsExtras;
import com.rolixtech.cravings.module.resturant.model.CommonResturantsTimings;
import com.rolixtech.cravings.module.resturant.model.CommonUsersResturants;
import com.rolixtech.cravings.module.users.services.CommonUsersService;
import org.apache.commons.lang3.time.DateUtils;
@Service
public class CommonResturantsProductsExtrasService {
	
	@Autowired
	private CommonResturantsProductsExtrasDao ProductsExtrasDao;
	
	@Autowired
	private CommonResturantsProductsService ProductsService;
	
	@Autowired
	private GenericUtility utility;
	
	@Transactional
	public void SaveProductsExtras(long productId,Long extraProductId[]){
		if(extraProductId!=null) {
			if(extraProductId.length>0) {
				ProductsExtrasDao.deleteAllByProductId(productId);
				for(int i=0;i<extraProductId.length;i++) {
					CommonResturantsProductsExtras ProductsExtras=new CommonResturantsProductsExtras();
					ProductsExtras.setProductId(productId);
					ProductsExtras.setExtraProductId(extraProductId[i]);
					ProductsExtrasDao.save(ProductsExtras);
				}
			}
		}
		
		
		
	}
	
	public List<Map> getProductsExtras(long productId){
		
		List<Map> list=new ArrayList<>();
		List<CommonResturantsProductsExtras> ProductsAddOns=ProductsExtrasDao.findAllByProductId(productId);
		if(!ProductsAddOns.isEmpty()) {
			ProductsAddOns.stream().forEach(
					ProductsAddOn->{
					Map Row=new HashMap<>();
					Row.put("productId", ProductsAddOn.getProductId());
					Row.put("productLabel",ProductsService.findLabelById(ProductsAddOn.getProductId()));
					Row.put("ExtraProductId", ProductsAddOn.getExtraProductId());
					Row.put("ExtraProductLabel",ProductsService.findLabelById(ProductsAddOn.getExtraProductId()));
					list.add(Row);
				}
			
			);
		}
		
		return list;
	}

	
	
	


}
