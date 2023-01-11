package com.rolixtech.cravings.module.users.dao;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.stereotype.Repository;

import com.rolixtech.cravings.module.users.models.CommonUsers;

@Repository
public interface CommonUsersDao extends JpaRepository<CommonUsers, Long>  {

	

	CommonUsers findByEmail(String username);

	boolean existsByEmail(String email);
	CommonUsers findById(long  Id);

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
	

}
