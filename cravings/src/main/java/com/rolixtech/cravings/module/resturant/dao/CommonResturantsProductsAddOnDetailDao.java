package com.rolixtech.cravings.module.resturant.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rolixtech.cravings.module.resturant.model.CommonResturantsProductsAddOn;
import com.rolixtech.cravings.module.resturant.model.CommonResturantsProductsAddOnDetail;

@Repository
public interface CommonResturantsProductsAddOnDetailDao extends JpaRepository<CommonResturantsProductsAddOnDetail, Long>  {



	void deleteAllByProductId(long productId);

	List<CommonResturantsProductsAddOnDetail> findAllByProductId(long productId);

	List<CommonResturantsProductsAddOnDetail> findAllByProductAddOnId(long id);

	boolean existsByProductAddOnId(long productAddOnId);

	void deleteAllByProductAddOnId(long productAddOnId);

	

}
