package com.rolixtech.cravings.module.order.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rolixtech.cravings.module.order.model.CustomerOrder;
import com.rolixtech.cravings.module.order.model.CustomerOrderProductsOptionalAddOn;
import com.rolixtech.cravings.module.resturant.model.CommonCategories;

@Repository
public interface CustomerOrderProductsOptionalAddOnDao extends JpaRepository<CustomerOrderProductsOptionalAddOn, Long>  {

	CustomerOrderProductsOptionalAddOn findById(long id);

	List<CustomerOrderProductsOptionalAddOn> findAllByOrderProductId(long id);

}
