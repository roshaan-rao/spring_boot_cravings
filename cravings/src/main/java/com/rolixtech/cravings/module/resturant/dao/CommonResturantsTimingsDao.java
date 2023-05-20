package com.rolixtech.cravings.module.resturant.dao;

import java.util.List;

import java.util.Date;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rolixtech.cravings.module.resturant.model.CommonResturantsTimings;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface CommonResturantsTimingsDao extends JpaRepository<CommonResturantsTimings, Long>  {

	void deleteAllByResturantId(long resturantId);

	List<CommonResturantsTimings> findAllByResturantId(long resturantId);

	CommonResturantsTimings findByResturantIdAndDayId(long resturantId, long dayOfWeek);

	@Query(value="SELECT * FROM common_resturants_timings WHERE resturant_id=?1 AND day_id=?2 AND CURRENT_TIME() BETWEEN opening_time AND closing_time",nativeQuery = true)
	CommonResturantsTimings isOpenResturantNowWithInPMAndPM(long resturantId, long dayOfWeek); 
	 
	@Query(value="SELECT * FROM common_resturants_timings where resturant_id=?1 and day_id=?2 and (CURTIME() BETWEEN opening_time AND '23:59:59' OR CURTIME() BETWEEN '00:00:00' AND closing_time) ",nativeQuery = true)
	CommonResturantsTimings isOpenResturantNowWithInPMAndAM(long resturantId, long dayOfWeek); 
	
	 
	@Query(value="SELECT IF(SUBSTR(closing_time, 1, 2) <= 12, 'AM', 'PM') AS closing_time_period FROM common_resturants_timings WHERE resturant_id=?1 AND day_id=?2 and closing_time is not null",nativeQuery = true)
	String isClosingTimeOfResturantIsPMOrAM(long resturantId, long dayOfWeek);

	@Query(value= "select closing_time from common_resturants_timings where resturant_id = ?1 && day_id =?2", nativeQuery = true)
	Date findClosingTimeByResturantIdAndDayId(Long resturantId,short dayId);

	@Query(value= "select opening_time from common_resturants_timings where resturant_id = ?1 && day_id =?2", nativeQuery = true)
	Date findOpeningTimeByResturantIdAndDayId(Long resturantId,short dayId);
}  
