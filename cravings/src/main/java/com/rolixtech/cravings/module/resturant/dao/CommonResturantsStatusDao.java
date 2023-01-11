package com.rolixtech.cravings.module.resturant.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rolixtech.cravings.module.resturant.model.CommonResturantsStatus;

@Repository
public interface CommonResturantsStatusDao extends JpaRepository<CommonResturantsStatus, Long>  {

	CommonResturantsStatus findById(long id);

}
