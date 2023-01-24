package com.rolixtech.cravings.module.resturant.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rolixtech.cravings.module.resturant.model.CommonResturantsPromotionalBannersDetail;
import com.rolixtech.cravings.module.resturant.model.CommonUsersResturants;

@Repository
public interface CommonResturantsPromotionalBannersDetailDao extends JpaRepository<CommonResturantsPromotionalBannersDetail, Long>  {

	CommonResturantsPromotionalBannersDetail findById(long id);
	
	List<CommonResturantsPromotionalBannersDetail> findByPromotionalBannerId(long id);

}
