package com.rolixtech.cravings.module.resturant.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rolixtech.cravings.module.resturant.model.CommonResturantsDealsProducts;

@Repository
public interface CommonResturantsDealsProductsDao extends JpaRepository<CommonResturantsDealsProducts, Long>  {

	

}
