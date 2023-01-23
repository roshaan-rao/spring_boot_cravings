package com.rolixtech.cravings.module.order.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.rolixtech.cravings.module.order.model.CustomerOrderStatus;


@Repository
public interface CustomerOrderStatusDao extends JpaRepository<CustomerOrderStatus, Long>  {

	CustomerOrderStatus findById(long id);

	

}
