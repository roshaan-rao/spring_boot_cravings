package com.rolixtech.cravings.module.resturant.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rolixtech.cravings.module.resturant.model.CommonResturantsCategories;

@Repository
public interface CommonResturantsCategoriesDao extends JpaRepository<CommonResturantsCategories, Long>  {

	void deleteAllByResturantId(long resturantId);
 
	CommonResturantsCategories findById(long resturant);

	List<CommonResturantsCategories> findAllByResturantId(long resturantId);

	List<CommonResturantsCategories> findAllByResturantIdAndIsActive(Long resturantId, int i);

	

	CommonResturantsCategories findByIdAndIsDeleted(long recordId, int i);

	List<CommonResturantsCategories> findAllByResturantIdAndIsDeleted(long resturantId, int i);

	List<CommonResturantsCategories> findAllByIsDeleted(int i);

	List<CommonResturantsCategories> findAllByResturantIdAndIsActiveAndIsDeleted(Long resturantId, int i, int j);

}
