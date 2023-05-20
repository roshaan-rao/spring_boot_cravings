package com.rolixtech.cravings.module.users.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rolixtech.cravings.module.users.models.CommonUsers;

@Repository
public interface CommonUsersDao extends JpaRepository<CommonUsers, Long>  {


	CommonUsers findByEmail(String username);

	boolean existsByEmail(String email);

	CommonUsers findById(long  Id);

	CommonUsers findByMobile(String  mobile);

	CommonUsers findByIdAndIsActiveAndIsDeleted(long recordId, int i, int j);

	boolean existsByEmailAndIsDeleted(String email, int i);

	CommonUsers findByEmailAndIsDeleted(String usernameFromToken, int i);


	@Query(value="SELECT distinct cu.id FROM common_users cu join common_user_role cur on cu.id=cur.user_id where cu.is_active=?1 and cu.is_deleted=?2 and cur.role_id=?3 and cu.id not in (select distinct cur2.user_id from common_users_resturants cur2)",nativeQuery = true)
	List<Long> findAllByIsActiveAndIsDeletedAndRoleId(int isAcive,int isDeleted,long roleId);


	List<CommonUsers> findAllByIsActiveAndIsDeleted(int i, int j);

	boolean existsByMobileAndIsDeleted(String mobile, int i);

	boolean existsByEmailAndIsDeletedAndIdNot(String email, int i, long recordId);

	boolean existsByMobileAndIsDeletedAndIdNot(String mobile, int i, long recordId);

	List<CommonUsers> findAllByIdIn(List<Long> allIds);

	List<CommonUsers> findAllByIsActiveAndIsDeletedAndIdIn(int isActive, int isDeleted, List<Long> riderIds);

	CommonUsers findByMobileAndIsActiveAndIsDeleted(String mobile, int i, int j);

	@Query(value="SELECT distinct cu.id FROM common_users cu join common_user_role cur on cu.id=cur.user_id where cu.is_active=?1 and cu.is_deleted=?2 and cur.role_id=?3 ",nativeQuery = true)
	List<Long> findAllUserIdsByIsActiveAndIsDeletedAndRoleId(int isAcive,int isDeleted,long roleId);

	@Query(value="SELECT distinct cu.id FROM common_users cu join common_user_role cur on cu.id=cur.user_id where cu.is_active=?1 and cu.is_deleted=?2 and cur.role_id not in (?3)",nativeQuery = true)
	List<Long> findAllByIsActiveAndIsDeletedAndRoleIdNot(int i, int j, int k);


	@Query(value="SELECT distinct cu.id FROM common_users cu join common_user_role cur on cu.id=cur.user_id where cu.is_active=?1 and cu.is_deleted=?2 and cur.role_id in (?3) ",nativeQuery = true)
	List<Long> findAllUserIdsByIsActiveAndIsDeletedAndRoleIdIn(int isAcive,int isDeleted,List<Long> roleIds);

	@Query(value="SELECT distinct cu.id FROM common_users cu join common_user_role cur on cu.id=cur.user_id where cu.is_active=?1 and cu.is_deleted=?2 and cur.role_id not in (?3) ",nativeQuery = true)
	List<Long> findAllUserIdsByIsActiveAndIsDeletedAndRoleIdNotIn(int isAcive,int isDeleted,List<Long> roleIds);

	@Query(value="SELECT first_name from common_users cu where cu.id = ?", nativeQuery = true)
	String findFirstNameById(Long id);

	@Query(value="SELECT last_name from common_users cu where cu.id = ?", nativeQuery = true)
	String findLastNameById(Long id);

	@Query(value="SELECT mobile from common_users cu where cu.id = ?", nativeQuery = true)
	String findMobileNumberById(Long id);

	@Query(value="SELECT first_name from common_users cu where cu.id = (select distinct remarks_added_by from customer_order_remarks where order_id = ?)" , nativeQuery = true)
	String findRemarksAddedByOrderId(Long id);

	@Query(value="SELECT cu.first_name FROM common_users cu where cu.id in (SELECT user_id FROM common_user_role where role_id=4)",  nativeQuery = true)
	List<CommonUsers>findRidersIdAndName(Long i);

	@Query(value="SELECT first_name from common_users cu where cu.id = ?", nativeQuery = true)
	String findFirstNameByUserId(Long userId);

	@Query(value="SELECT first_name from common_users cu where cu.id = ?", nativeQuery = true)
	String findFirstNameByRiderDetailsUserId(Long id);
//	@Query(value="select mobile from common_users where id in (select user_id from customer_order where id = ?1)", nativeQuery = true)
//	String findRiderContactNumberByOrderId(Long id);


}
