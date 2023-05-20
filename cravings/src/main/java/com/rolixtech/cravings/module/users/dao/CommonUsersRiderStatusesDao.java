package com.rolixtech.cravings.module.users.dao;

import com.rolixtech.cravings.module.users.models.CommonUsers;
import com.rolixtech.cravings.module.users.models.CommonUsersRiderStatuses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommonUsersRiderStatusesDao extends JpaRepository<CommonUsersRiderStatuses,Long> {

    @Query(value="select status_string_value from common_users_rider_statuses where id in (?1)",  nativeQuery = true)
    String findCommonUsersRiderStatusesById(Long id);

    @Query(value="SELECT id ,first_name, mobile FROM common_users where id in (SELECT user_id FROM common_user_role where role_id=4)",  nativeQuery = true)
    List<String> findCommonUsersRiderNameByUserRoleId(int i);

    @Query(value="SELECT id FROM common_users where id in (SELECT user_id FROM common_user_role where role_id=4)",  nativeQuery = true)
    List<Long> findCommonUsersRiderIdByUserRoleId(int i);

    @Query(value="select first_name from common_users where id in (select user_id from customer_order where id = ?1)", nativeQuery = true)
    String findRiderNameByOrderId(Long orderId);
}
