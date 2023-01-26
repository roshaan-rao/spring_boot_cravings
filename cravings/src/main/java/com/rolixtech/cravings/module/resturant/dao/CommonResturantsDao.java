package com.rolixtech.cravings.module.resturant.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rolixtech.cravings.module.generic.model.CommonCities;
import com.rolixtech.cravings.module.resturant.model.CommonResturants;

@Repository
public interface CommonResturantsDao extends JpaRepository<CommonResturants, Long>  {

	
	@Query(value="Select * from common_resturants order by rating desc limit ?1 ",nativeQuery = true)
	List<CommonResturants> findAllByRatingOrderByIDDescLimit(int limit,double lat, double lng);

	@Query(value="Select * from common_resturants ",nativeQuery = true)
	List<CommonResturants>  findAllByLatitudeAndLongitude(double lat, double lng);

	List<CommonResturants> findAllByLabelContaining(String keyword);
	
	
	CommonResturants findById(long id);

	
	@Query(value="Select * from common_resturants cr where cr.is_deleted=?2 and cr.id in (SELECT curs.resturant_id FROM common_users_resturants curs where curs.user_id=?1) ",nativeQuery = true)
	List<CommonResturants> findAllByUserIdAndIsDeleted(long userId,int isDeleted);

	List<CommonResturants> findAllByStatusAndIsDeleted(int status,int isDeleted);

	CommonResturants findByIdAndIsDeleted(long recordId, int isDeleted);


	List<CommonResturants> findAllByIsDeleted(int isDeleted);

	CommonResturants findByIdAndIsActiveAndIsDeleted(long recordId, int i, int j);

	List<CommonResturants> findAllByIsActiveAndIsDeleted(int i, int j);

	@Query(value="Select * from common_resturants where id in (SELECT distinct resturant_id FROM common_resturants_categories where category_id=?1 and is_active=1 and is_deleted=0)",nativeQuery = true)
	List<CommonResturants> findAllByCommonCategoryIdWise(long categoryId);

	

	

}
