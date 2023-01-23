package com.rolixtech.cravings.module.resturant.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rolixtech.cravings.module.resturant.model.CommonResturantsPromotionalBanners;


@Repository
public interface CommonResturantsPromotionalBannersDao extends JpaRepository<CommonResturantsPromotionalBanners, Long>  {

	CommonResturantsPromotionalBanners findById(long id);

}
