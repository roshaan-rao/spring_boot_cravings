package com.rolixtech.cravings.module.resturant.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rolixtech.cravings.module.resturant.model.CommonResturantsProductsAddOn;

@Repository
public interface CommonResturantsProductsAddOnDao extends JpaRepository<CommonResturantsProductsAddOn, Long>  {



	void deleteAllByProductId(long productId);

	List<CommonResturantsProductsAddOn> findAllByProductId(long productId);

	List<CommonResturantsProductsAddOn> findAllById(long id);

	List<CommonResturantsProductsAddOn> findAllByProductIdIn(List<Long> productId);
	
	
	CommonResturantsProductsAddOn findById(long recordId);

	List<CommonResturantsProductsAddOn> findAllByProductIdInAndIsDeleted(List<Long> productId, int i);

	List<CommonResturantsProductsAddOn> findAllByProductIdAndIsDeleted(long productId, int i);

	List<CommonResturantsProductsAddOn> findAllByIdAndIsDeleted(long id, int i);

	

}
