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
import com.rolixtech.cravings.module.resturant.dao.CommonResturantsProductsAddOnDetailDao;
import com.rolixtech.cravings.module.resturant.dao.CommonResturantsTimingsDao;
import com.rolixtech.cravings.module.resturant.dao.CommonUsersResturantsDao;
import com.rolixtech.cravings.module.resturant.model.CommonCategories;
import com.rolixtech.cravings.module.resturant.model.CommonResturantsCategories;
import com.rolixtech.cravings.module.resturant.model.CommonResturantsProductsAddOn;
import com.rolixtech.cravings.module.resturant.model.CommonResturantsProductsAddOnDetail;
import com.rolixtech.cravings.module.resturant.model.CommonResturantsTimings;
import com.rolixtech.cravings.module.resturant.model.CommonUsersResturants;
import com.rolixtech.cravings.module.users.services.CommonUsersService;
import org.apache.commons.lang3.time.DateUtils;
@Service
public class CommonResturantsProductsAddOnService {
	
	@Autowired
	private CommonResturantsProductsAddOnDao ProductsAddODao;
	
	@Autowired
	private CommonResturantsProductsAddOnDetailDao ProductsAddODaoDetail;
	
	@Autowired
	private CommonResturantsProductsService ProductsService;
	
	@Autowired
	private CommonResturantsProductsGroupTypesService ProductsGroupTypesService;
	
	@Autowired
	private GenericUtility utility;
	
	
	@Autowired
	private CommonResturantsProductsAddOnSelectionTypeService AddOnSelectionTypeService;
	
	@Transactional
	public void SaveProductsAddOn(long recordId, long productId, String groupTitle, long groupTypeId, long selectionTypeId,int minSelection,int maxSelectionint ,
			long[] addOnProductId, double[] price) {
		
		CommonResturantsProductsAddOn ProductsAddOn=new CommonResturantsProductsAddOn();
		if(recordId!=0) {
			ProductsAddOn=ProductsAddODao.findById(recordId);
		}
		ProductsAddOn.setGroupTitle(groupTitle);
		ProductsAddOn.setGroupTypeId(groupTypeId);
		ProductsAddOn.setSelectionTypeId(selectionTypeId);
		if(selectionTypeId==1) {
			ProductsAddOn.setMinSelection(minSelection);
			ProductsAddOn.setMaxSelection(maxSelectionint);
			
		}else {
			ProductsAddOn.setMinSelection(1);
		}
		
		ProductsAddOn.setProductId(productId);
		ProductsAddODao.save(ProductsAddOn);
		SaveProductsAddOnDetail(ProductsAddOn.getId(),addOnProductId,price);
		
	}
	
	@Transactional
	public void SaveProductsAddOnDetail(long ProductAddOnId,long ProductId[],double[] Prices){
		
		if(ProductId!=null) {
			
			if(ProductId.length>0) {
				if(ProductsAddODaoDetail.existsByProductAddOnId(ProductAddOnId)) {
					ProductsAddODaoDetail.deleteAllByProductAddOnId(ProductAddOnId);
				}
				
				for(int i=0;i<ProductId.length;i++) {
					CommonResturantsProductsAddOnDetail ProductsAddOnDetail=new CommonResturantsProductsAddOnDetail();
					ProductsAddOnDetail.setProductAddOnId(ProductAddOnId);
					ProductsAddOnDetail.setProductId(ProductId[i]);
					ProductsAddOnDetail.setPrice(Prices[i]); 
					ProductsAddODaoDetail.save(ProductsAddOnDetail);
				}
			}
		}
		
		
		
	}
	
	
	public List<Map> getProductsAddOn(long productId){
		
		List<Map> list=new ArrayList<>();
		List<CommonResturantsProductsAddOn> ProductsAddOns=ProductsAddODao.findAllByProductIdAndIsDeleted(productId,0);
		if(!ProductsAddOns.isEmpty()) {
			ProductsAddOns.stream().forEach(
					ProductsAddOn->{
					Map Row=new HashMap<>();
					Row.put("id", ProductsAddOn.getId());
					Row.put("productId", ProductsAddOn.getProductId());
					Row.put("productLabel",ProductsService.findLabelById(ProductsAddOn.getProductId()));
					Row.put("groupTitle", ProductsAddOn.getGroupTitle());
					Row.put("groupTypeLabel", ProductsGroupTypesService .findLabelById(ProductsAddOn.getGroupTypeId()));
					Row.put("groupTypeId", ProductsAddOn.getGroupTypeId());
					Row.put("selectionTypeId",ProductsAddOn.getSelectionTypeId());
					Row.put("selectionTypeLabel",AddOnSelectionTypeService.findLabelById(ProductsAddOn.getSelectionTypeId()));
					if(ProductsAddOn.getSelectionTypeId()==1) {
						
						Row.put("minSelection", ProductsAddOn.getMinSelection());
						Row.put("maxSelection", ProductsAddOn.getMaxSelection());
					}else {
						Row.put("minSelection", ProductsAddOn.getMinSelection());
						Row.put("maxSelection", 0);
						
					}
					
					Row.put("detail",getProductsAddOnDetail(ProductsAddOn.getId()));
					
					list.add(Row);
				}
			
			);
		}
		
		return list;
	}
	
	public List<Map> getProductsAddOnDetail(long id){
		
		List<Map> list=new ArrayList<>();
		List<CommonResturantsProductsAddOnDetail> ProductsAddOnsDetails=ProductsAddODaoDetail.findAllByProductAddOnId(id);
		if(!ProductsAddOnsDetails.isEmpty()) {
			ProductsAddOnsDetails.stream().forEach(
					detail->{
					Map Row=new HashMap<>();
					Row.put("id", detail.getId());
					Row.put("productId", detail.getProductId());
					Row.put("productLabel",ProductsService.findLabelById(detail.getProductId()));
					Row.put("ProductsAddOnId",detail.getProductAddOnId());
					Row.put("price",detail.getPrice());
					list.add(Row);
				}
			
			);
		}
		
		return list;
	}
	
	
	public List<Map> getProductAddOnGroup(long id){
		
		List<Map> list=new ArrayList<>();
		List<CommonResturantsProductsAddOn> ProductsAddOns=ProductsAddODao.findAllByIdAndIsDeleted(id,0);
		if(!ProductsAddOns.isEmpty()) {
			ProductsAddOns.stream().forEach(
					ProductsAddOn->{
					Map Row=new HashMap<>();
					Row.put("id", ProductsAddOn.getId());
					Row.put("productId", ProductsAddOn.getProductId());
					Row.put("productLabel",ProductsService.findLabelById(ProductsAddOn.getProductId()));
					Row.put("groupTitle", ProductsAddOn.getGroupTitle());
					Row.put("groupTypeLabel", ProductsGroupTypesService .findLabelById(ProductsAddOn.getGroupTypeId()));
					Row.put("groupTypeId", ProductsAddOn.getGroupTypeId());
					Row.put("selectionTypeId",ProductsAddOn.getSelectionTypeId());
					Row.put("selectionTypeLabel",AddOnSelectionTypeService.findLabelById(ProductsAddOn.getSelectionTypeId()));
					if(ProductsAddOn.getSelectionTypeId()==1) {
						Row.put("minSelection", ProductsAddOn.getMinSelection());
						Row.put("maxSelection", ProductsAddOn.getMaxSelection());
						
					
					}else {
						Row.put("minSelection", ProductsAddOn.getMinSelection());
						Row.put("maxSelection", 0);
					}
					
					Row.put("detail",getProductsAddOnDetail(ProductsAddOn.getId()));
					
					list.add(Row);
				}
			
			);
		}
		
		return list;
	}
	
	public List<Map> getAllProductAddOnGroup(List<Long> productId){
		
		List<Map> list=new ArrayList<>();
		List<CommonResturantsProductsAddOn> ProductsAddOns=ProductsAddODao.findAllByProductIdInAndIsDeleted(productId,0);
		if(!ProductsAddOns.isEmpty()) {
			ProductsAddOns.stream().forEach(
					ProductsAddOn->{
					Map Row=new HashMap<>();
					Row.put("id", ProductsAddOn.getId());
					Row.put("productId", ProductsAddOn.getProductId());
					Row.put("productLabel",ProductsService.findLabelById(ProductsAddOn.getProductId()));
					Row.put("groupTitle", ProductsAddOn.getGroupTitle());
					Row.put("groupTypeId", ProductsAddOn.getGroupTypeId());
					Row.put("groupTypeLabel", ProductsGroupTypesService .findLabelById(ProductsAddOn.getGroupTypeId()));
					Row.put("selectionTypeId",ProductsAddOn.getSelectionTypeId());
					Row.put("selectionTypeLabel",AddOnSelectionTypeService.findLabelById(ProductsAddOn.getSelectionTypeId()));
					if(ProductsAddOn.getSelectionTypeId()==1) {
						Row.put("minSelection", ProductsAddOn.getMinSelection());
						Row.put("maxSelection", ProductsAddOn.getMaxSelection());
					
					}else {
						Row.put("minSelection", ProductsAddOn.getMinSelection());
						Row.put("maxSelection", 0);
						
					}
					
					Row.put("detail",getProductsAddOnDetail(ProductsAddOn.getId()));
					
					list.add(Row);
				}
			
			);
		}
		
		return list;
	}

	public void deleteProductAddOnGroup(long recordId) {
		CommonResturantsProductsAddOn ProductsAddOn=ProductsAddODao.findById(recordId);
		if(ProductsAddOn!=null) {
			ProductsAddOn.setIsDeleted(1);
			ProductsAddODao.save(ProductsAddOn);
		}
		
	}


	
	
	
	

	
	
	


}
