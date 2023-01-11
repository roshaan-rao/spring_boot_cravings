package com.rolixtech.cravings.module.resturant.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rolixtech.cravings.module.resturant.model.CommonResturantsTimings;

@Repository
public interface CommonResturantsTimingsDao extends JpaRepository<CommonResturantsTimings, Long>  {

	void deleteAllByResturantId(long resturantId);

	List<CommonResturantsTimings> findAllByResturantId(long resturantId);

	CommonResturantsTimings findByResturantIdAndDayId(long resturantId, long dayOfWeek);

	

}
