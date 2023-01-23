package com.rolixtech.cravings.module.order.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rolixtech.cravings.module.order.model.CustomerOrder;
import com.rolixtech.cravings.module.order.model.CustomerOrderProducts;
import com.rolixtech.cravings.module.resturant.model.CommonCategories;

@Repository
public interface CustomerOrderProductsDao extends JpaRepository<CustomerOrderProducts, Long>  {

	CustomerOrderProducts findById(long id);

	CustomerOrderProducts findByOrderId(long orderId);

	List<CustomerOrderProducts> findAllByOrderId(long orderId);

}
