package com.rolixtech.cravings.module.order.dao;

import com.rolixtech.cravings.module.order.model.ChangeStatusRemarks;
import com.rolixtech.cravings.module.users.models.RiderAssignDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChangeStatusRemarksDao extends JpaRepository<ChangeStatusRemarks, Long> {

    @Query(value="select * from change_status_remarks where order_id = ?1 order by id desc", nativeQuery = true)
    List<ChangeStatusRemarks> findChangeStatusRemarksByOrderId(Long orderId);

}
