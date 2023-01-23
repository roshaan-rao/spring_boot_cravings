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
	
	public List<Map> getAll(){
		
		List<Map> list=new ArrayList<>();
		List<CustomerOrderStatus> statuses=OrderStatusDao.findAll();
		if(!statuses.isEmpty()) {
			statuses.stream().forEach(
					status->{
						Map Row=new HashMap<>();
						Row.put("id", status.getId());
						Row.put("label", status.getLabel());
						
						list.add(Row);
				}
			
			);
		}
		
		
		
		return list;
		
	}
	
	
	public String findLabelById(long id) {
		String label="";
		CustomerOrderStatus Status=OrderStatusDao.findById(id);
		if(Status!=null) {
			label=Status.getLabel();
		}
		return label;
	}
}
