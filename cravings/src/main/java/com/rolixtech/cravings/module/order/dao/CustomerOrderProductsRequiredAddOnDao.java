package com.rolixtech.cravings.module.order.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.rolixtech.cravings.module.order.model.CustomerOrderAddress;
import com.rolixtech.cravings.module.order.model.CustomerOrderProductsRequiredAddOn;


@Repository
public interface CustomerOrderProductsRequiredAddOnDao extends JpaRepository<CustomerOrderProductsRequiredAddOn, Long>  {

	CustomerOrderProductsRequiredAddOn findById(long id);

	List<CustomerOrderProductsRequiredAddOn> findAllByOrderProductId(long id);

}
