package com.rolixtech.cravings.module.order.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

	CustomerOrder findByIdAndOrderStatusId(long orderId, long statusId);

	@Query(value="SELECT cop.product_id,count(*) FROM customer_order_products  cop join common_resturants_products crp on crp.id=cop.product_id and crp.is_active=?1 and crp.is_deleted=?2 group by cop.product_id order by count(*) desc limit ?3",nativeQuery = true)
	List<Long> findAllPopularProductsByIsActiveAndIsDeletedAndLimit(int isActive, int isDeleted,int limit);
	
	@Query(value="SELECT cop.product_id,count(*) FROM customer_order_products  cop join common_resturants_products crp on crp.id=cop.product_id and crp.is_active=?1 and crp.is_deleted=?2 group by cop.product_id order by count(*) desc ",nativeQuery = true)
	List<Long> findAllPopularProductsByIsActiveAndIsDeleted(int isActive, int isDeleted);

	List<CustomerOrder> findAllByOrderStatusIdGreaterThanOrderByIdDesc(long l);

}
