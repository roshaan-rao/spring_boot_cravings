package com.rolixtech.cravings.module.users.dao;

import com.rolixtech.cravings.module.order.model.CustomerOrderRemarks;
import com.rolixtech.cravings.module.users.models.RiderAssignDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RiderAssignDetailsDao extends JpaRepository<RiderAssignDetails, Long> {


    @Query(value="select rider_name from rider_assign_details where order_id = ?1", nativeQuery = true)
    String findRiderNameByOrderId ( Long orderId);


    @Query(value="select * from rider_assign_details where order_id = ?1 order by id desc", nativeQuery = true)
    List<RiderAssignDetails> findRiderDetailsByOrderId(Long orderId);

}
