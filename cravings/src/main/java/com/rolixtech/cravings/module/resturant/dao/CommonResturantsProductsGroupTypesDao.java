package com.rolixtech.cravings.module.resturant.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rolixtech.cravings.module.resturant.model.CommonCategories;
import com.rolixtech.cravings.module.resturant.model.CommonResturantsProductsGroupTypes;

@Repository
public interface CommonResturantsProductsGroupTypesDao extends JpaRepository<CommonResturantsProductsGroupTypes, Long>  {

	CommonResturantsProductsGroupTypes findById(long id);

}
