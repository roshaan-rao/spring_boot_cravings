package com.rolixtech.cravings.module.order.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rolixtech.cravings.module.order.model.CustomerOrder;
import com.rolixtech.cravings.module.resturant.model.CommonCategories;

@Repository
public interface CustomerOrderDao extends JpaRepository<CustomerOrder, Long>  {

	CustomerOrder findById(long id);

	List<CustomerOrder> findAllByUserId(long userId);

	List<CustomerOrder> findAllById(long orderId);

	List<CustomerOrder> findAllByUserIdAndOrderStatusId(long userId, long i);

	CustomerOrder findByUserIdAndOrderStatusId(long orderIdd, long i);

	List<CustomerOrder> findAllByOrderStatusIdGreaterThan(long l);

	CustomerOrder findAllByIdAndOrderStatusIdGreaterThan(long orderId, long i);

}
