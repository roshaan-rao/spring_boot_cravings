package com.rolixtech.cravings.module.order.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rolixtech.cravings.module.order.model.CustomerOrderRemarks;
import com.rolixtech.cravings.module.order.model.CustomerOrderStatus;


@Repository
public interface CustomerOrderRemarksDao extends JpaRepository<CustomerOrderRemarks, Long>  {

	CustomerOrderRemarks findById(long id);

	List<CustomerOrderRemarks> findByOrderId(long orderId);

	List<CustomerOrderRemarks> findByOrderIdOrderByIdDesc(long orderId);

	

}
