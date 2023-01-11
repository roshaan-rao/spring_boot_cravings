package com.rolixtech.cravings.module.resturant.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rolixtech.cravings.module.resturant.model.CommonResturantsProductsExtras;

@Repository
public interface CommonResturantsProductsExtrasDao extends JpaRepository<CommonResturantsProductsExtras, Long>  {

	void deleteAllByProductId(long productId);

	List<CommonResturantsProductsExtras> findAllByProductId(long productId);

	

}
