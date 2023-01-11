package com.rolixtech.cravings.module.resturant.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
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

}
