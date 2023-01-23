package com.rolixtech.cravings.module.resturant.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rolixtech.cravings.module.resturant.model.CommonResturantsPromotionalBannersDetail;

@Repository
public interface CommonResturantsPromotionalBannersDetailDao extends JpaRepository<CommonResturantsPromotionalBannersDetail, Long>  {

	CommonResturantsPromotionalBannersDetail findById(long id);

}
