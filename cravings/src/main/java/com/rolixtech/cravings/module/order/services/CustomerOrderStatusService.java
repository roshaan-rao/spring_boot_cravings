package com.rolixtech.cravings.module.order.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rolixtech.cravings.module.order.dao.CustomerOrderStatusDao;
import com.rolixtech.cravings.module.order.model.CustomerOrderStatus;

@Service
public class CustomerOrderStatusService {
	@Autowired
	private CustomerOrderStatusDao OrderStatusDao; 
	
	public List<Map> getAllAdmin(){
		
		List<Map> list=new ArrayList<>();
		List<CustomerOrderStatus> statuses=OrderStatusDao.findAll();
		if(!statuses.isEmpty()) {
			statuses.stream().forEach(
					status->{
						Map Row=new HashMap<>();
						Row.put("id", status.getId());
						Row.put("label", status.getAdminLabel());
						
						list.add(Row);
				}
			
			);
		}
		
		
		
		return list;
		
	} 
	
	/**
	 * @apiNote
	 * requirementType ==1 ===>> Admin
	 * requirementType ==2 ===>> Resturant
	 * requirementType ==3 ===>> Customer
	 * **/
	public String findLabelByIdAndRequirementType(long id,int requirementType) {
		String label="";
		CustomerOrderStatus Status=OrderStatusDao.findById(id);
		if(Status!=null) {
			if(requirementType==1) {
				label=Status.getAdminLabel();
			}else if(requirementType==2){
				label=Status.getResturantLabel();
			}else if(requirementType==3){
				label=Status.getCustomerLabel();
			}
			
		}
		return label;
	}
	
	
	
	
	
	
	
}
