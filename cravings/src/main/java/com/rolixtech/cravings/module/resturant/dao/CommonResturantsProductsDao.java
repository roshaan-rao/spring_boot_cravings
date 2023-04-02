package com.rolixtech.cravings.module.resturant.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rolixtech.cravings.module.resturant.model.CommonResturantsProducts;
import com.rolixtech.cravings.module.resturant.model.CommonResturantsTimings;

@Repository
public interface CommonResturantsProductsDao extends JpaRepository<CommonResturantsProducts, Long>  {

	List<CommonResturantsProducts> findByLabelContaining(String keyword);

	CommonResturantsProducts findById(long id);

	List<CommonResturantsProducts> findAllByResturantId(long resturantId);

	List<CommonResturantsProducts> findAllByResturantIdAndIsExtra(long resturantId, int i);

	CommonResturantsProducts findByIdAndIsDeleted(long recordId, int i);

	List<CommonResturantsProducts> findAllByResturantIdAndIsDeleted(long resturantId, int i);

	List<CommonResturantsProducts> findAllByResturantIdAndIsExtraAndIsDeleted(long resturantId, int i, int j);

	List<CommonResturantsProducts> findAllByResturantIdAndResturantCategoryIdAndIsDeleted(long resturantId,
			long resturantCategoryId, int i);

	CommonResturantsProducts findByIdAndIsActiveAndIsDeleted(long recordId, int i, int j);

	List<CommonResturantsProducts> findAllByResturantIdAndResturantCategoryIdAndIsExtraAndIsActiveAndIsDeleted(
			long resturantId, long resturantCategoryId, int i, int j, int k);

	@Query(value="select distinct id from common_resturants_products where resturant_id=?1 and is_extra=?2 and is_active=?3 and is_deleted=?4",nativeQuery = true)
	List<Long> findAllByResturantIdAndIsExtraAndIsActiveAndIsDeleted(long resturantId, int i, int j, int k);

	@Query(value="SELECT resturant_category_id ,count(id) as maxCount FROM common_resturants_products where resturant_id=?1 and is_deleted=?2  group by resturant_category_id order by maxCount desc limit 1",nativeQuery = true)
	Long findResturantCategoryIdByResturantIdAndIsDeleted(long resturantId,long isDeleted);

	List<CommonResturantsProducts> findAllByLabelContainingAndIsActiveAndIsDeleted(String keyword, int i, int j);

	List<CommonResturantsProducts> findAllByResturantIdAndResturantCategoryIdAndIsExtraAndIsAvailableAndIsDeleted(
			long resturantId, long resturantCategoryId, int i, int j, int k);

	CommonResturantsProducts findByIdAndIsAvailableAndIsDeleted(long recordId, int i, int j);
	
	@Query(value="SELECT * FROM common_resturants_products WHERE id=?1 and is_avaliable=?2 and is_deleted=?3 AND CURRENT_TIME() BETWEEN availability_from AND availability_to",nativeQuery = true)
	CommonResturantsProducts findByIdAndIsAvailableAndIsDeletedAndCurrentDateBetweenAvailabilityFromAndAvailabilityTo(long recordId, int isAvaliable, int isDeleted);
	
	
	
	@Query(value="SELECT * FROM common_resturants_products WHERE id=?1 and is_available=?2 and is_deleted=?3 and is_active=?4",nativeQuery = true)
	CommonResturantsProducts findByIdAndIsAvailableAndIsDeletedAndIsActive(long recordId, int isAvaliable, int isDeleted,int isActive);

	//String isClosingTimeOfResturantIsPMOrAM(long resturantId);
	
	
	/////////////////////////////////////////NEW FOR TIME/////////////
	@Query(value="SELECT * FROM common_resturants_products where id=?1 and (CURTIME() BETWEEN availability_from AND '23:59:59' OR CURTIME() BETWEEN '00:00:00' AND availability_to) ",nativeQuery = true)
	CommonResturantsProducts isOpenProductNowWithInPMAndAM(long productId); 
	 
	@Query(value="SELECT IF(SUBSTR(availability_to, 1, 2) <= 12, 'AM', 'PM') AS closing_time_period FROM common_resturants_products WHERE id=?1 and availability_to is not null",nativeQuery = true)
	String isClosingTimeOfProductIsPMOrAM(long productId);

	@Query(value="SELECT * FROM common_resturants_products WHERE id=?1 AND CURRENT_TIME() BETWEEN availability_from AND availability_to",nativeQuery = true)
	CommonResturantsProducts isOpenResturantNowWithInPMAndPM(long productId); 

}
