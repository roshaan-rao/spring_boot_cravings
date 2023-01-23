package com.rolixtech.cravings.module.resturant.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rolixtech.cravings.module.resturant.model.CommonResturantsProducts;

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

}
