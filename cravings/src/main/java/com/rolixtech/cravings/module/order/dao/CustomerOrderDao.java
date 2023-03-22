package com.rolixtech.cravings.module.order.dao;

import java.util.Date;
import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rolixtech.cravings.module.order.model.CustomerOrder;
import com.rolixtech.cravings.module.resturant.model.CommonCategories;

@Repository
public interface CustomerOrderDao extends JpaRepository<CustomerOrder, Long>  {

	CustomerOrder findById(long id);

	List<CustomerOrder> findAllByUserId(long userId);

	List<CustomerOrder> findAllById(long orderId);
	@Query(value="SELECT count(*) FROM customer_order  where user_id=?1 and order_status_id =?2 ",nativeQuery = true)
	Integer countAllByUserIdAndOrderStatusId(long userId, long i);

	CustomerOrder findByUserIdAndOrderStatusId(long orderIdd, long i);

	List<CustomerOrder> findAllByOrderStatusIdGreaterThan(long l);

	CustomerOrder findAllByIdAndOrderStatusIdGreaterThan(long orderId, long i);

	CustomerOrder findByIdAndOrderStatusId(long orderId, long statusId);

	@Query(value="SELECT cop.product_id,count(*) FROM customer_order_products  cop join common_resturants_products crp on crp.id=cop.product_id and crp.is_active=?1 and crp.is_deleted=?2 group by cop.product_id order by count(*) desc limit ?3",nativeQuery = true)
	List<Long> findAllPopularProductsByIsActiveAndIsDeletedAndLimit(int isActive, int isDeleted,int limit);
	
	@Query(value="SELECT cop.product_id,count(*) FROM customer_order_products  cop join common_resturants_products crp on crp.id=cop.product_id and crp.is_active=?1 and crp.is_deleted=?2 group by cop.product_id order by count(*) desc ",nativeQuery = true)
	List<Long> findAllPopularProductsByIsActiveAndIsDeleted(int isActive, int isDeleted);

	List<CustomerOrder> findAllByOrderStatusIdGreaterThanOrderByIdDesc(long l);

	

	@Query(value="SELECT * FROM customer_order  where order_status_id >?1 and order_status_id not in (?2) order by id desc",nativeQuery = true)
	List<CustomerOrder> findAllByOrderStatusIdGreaterThanOrderAndOrderStatusIdNotByIdDesc(long l,List<Long> statusIdNotIn);

	List<CustomerOrder> findAllByUserIdAndOrderStatusIdIn(long userId, List<Long> statusIds);
	
	/*
	 * LIMIT 1 Added on 08/Feb/2023
	 * **/
	@Query(value="SELECT * FROM customer_order  where order_status_id >?1 and order_status_id not in (?2) and user_id=?3 order by id desc",nativeQuery = true)
	List<CustomerOrder> findAllByOrderStatusIdGreaterThanOrderAndOrderStatusIdNotInAndUserIdOrderByIdDesc(long l,List<Long> statusIdNotIn,long userId);

	

	List<CustomerOrder> findAllByOrderByIdDesc();
	
	
	@Query(value="SELECT datediff(?1,?2) ;",nativeQuery = true)
	Integer findDateDiffByOrderDeliveryTimeAndCurrentDate(String dilveryDateTime,String current);

	

	List<CustomerOrder> findAllByOrderStatusIdAndResturantIdOrderByIdDesc(long l, long resturantId);

	List<CustomerOrder> findAllByOrderStatusIdInAndResturantIdOrderByIdDesc(List<Long> orderStatuslist,
			long resturantId);

	@Modifying
	@Query(value="update customer_order set is_help_required=1 where id=?1",nativeQuery = true)
	void UpdateIsHelpRequiredById(long i);

	@Query(value="SELECT * FROM customer_order  where order_status_id in (?1) order by id desc",nativeQuery = true)
	List<CustomerOrder> findAllByOrderStatusIdInOrderByIdDesc(List<Long> orderStatusIds);

	@Query(value="SELECT * FROM customer_order  where order_status_id in (?1) and resturant_id=?2 and created_on between STR_TO_DATE(CONCAT(CURDATE(), '00:00:00'), '%Y-%m-%d %H:%i:%s') and STR_TO_DATE(CONCAT(CURDATE(), '23:59:00'), '%Y-%m-%d %H:%i:%s') order by id desc",nativeQuery = true)
	List<CustomerOrder> findAllByOrderStatusIdInAndResturantIdAndCreatedOnBetween24hrsOrderByIdDesc(List<Long> orderStatuslist, long resturantId);


}
