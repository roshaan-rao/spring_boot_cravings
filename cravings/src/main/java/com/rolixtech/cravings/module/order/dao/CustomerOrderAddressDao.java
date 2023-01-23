package com.rolixtech.cravings.module.order.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.rolixtech.cravings.module.order.model.CustomerOrderAddress;


@Repository
public interface CustomerOrderAddressDao extends JpaRepository<CustomerOrderAddress, Long>  {

	CustomerOrderAddress findById(long id);

	CustomerOrderAddress findByOrderId(long orderId);

}
