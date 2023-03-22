package com.rolixtech.cravings.module.resturant.services;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.aspectj.apache.bcel.classfile.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Service;

import com.rolixtech.cravings.CravingsApplication;
import com.rolixtech.cravings.module.generic.services.GenericUtility;
import com.rolixtech.cravings.module.resturant.dao.CommonCategoriesDao;
import com.rolixtech.cravings.module.resturant.dao.CommonResturantsTimingsDao;
import com.rolixtech.cravings.module.resturant.dao.CommonUsersResturantsDao;
import com.rolixtech.cravings.module.resturant.model.CommonCategories;
import com.rolixtech.cravings.module.resturant.model.CommonResturantsTimings;
import com.rolixtech.cravings.module.resturant.model.CommonUsersResturants;
import com.rolixtech.cravings.module.users.services.CommonUsersService;
import org.apache.commons.lang3.time.DateUtils;
@Service
public class CommonResturantsTimingsService {
	
	@Autowired
	private CommonResturantsTimingsDao ResturantsTimingsDao;
	
	@Autowired
	private GenericUtility utility;
	
	@Transactional
	public void SaveResturantTimings(long resturantId,long dayId[],Date openingTime[],Date closeTime[]){
		
		if(dayId.length>0) {
			ResturantsTimingsDao.deleteAllByResturantId(resturantId);
			for(int i=0;i<dayId.length;i++) {
				CommonResturantsTimings timings=new CommonResturantsTimings();
				timings.setResturantId(resturantId);
				timings.setDayId(dayId[i]);
				if(openingTime[i]+""!="") {
					timings.setOpeningTime(openingTime[i]);
					timings.setClosingTime(closeTime[i]);
				}
				
				ResturantsTimingsDao.save(timings);
			}
		}
		
		
	}
	
	/**
	 * By Using DateUtils.addHours(Date,5) adding 5 hrs for GMT
	 * */
	public List<Map> getResturantTimings(long resturantId){
		
		List<Map> list=new ArrayList<>();
		
		List<CommonResturantsTimings> ResturantsTimings=ResturantsTimingsDao.findAllByResturantId(resturantId);
		if(!ResturantsTimings.isEmpty()) {
			ResturantsTimings.stream().forEach(
				ResturantsTiming->{
					Map Row=new HashMap<>();
					if(ResturantsTiming.getClosingTime()!=null && ResturantsTiming.getOpeningTime()!=null) {
					
						Row.put("closeTiming", (ResturantsTiming.getClosingTime()).getTime());
						Row.put("closeTimingDisplay", utility.getDisplayTimeFromDate(ResturantsTiming.getClosingTime()));
						
						
						Row.put("openTiming", ResturantsTiming.getOpeningTime().getTime());
						Row.put("openTimingDisplay",utility.getDisplayTimeFromDate(ResturantsTiming.getOpeningTime()) );
//						try {
//							
//						} catch (ParseException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//						try { 
//							
//							//Row.put("openTimingDisplay", utility.millisecondstoTime(DateUtils.addHours(ResturantsTiming.getOpeningTime(), 5).getTime()));
//						} catch (ParseException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//							Row.put("openTimingDisplay", "00:00:00");
//							
//						}
					}else {
						Row.put("closeTimingDisplay", "");
						Row.put("closeTiming", "");
						Row.put("closeTimingDisplay", "");
						Row.put("openTiming", "");
					}
					
					Row.put("dayId", ResturantsTiming.getDayId());
					list.add(Row);
				}
			
			);
		}
		
		return list;
	}

	
	public CommonResturantsTimings getResturantTimingForToday(long resturantId){
		
		
		
		int dayOfWeek=utility.getDayNumberOFWeek();
		CommonResturantsTimings ResturantsTimings=ResturantsTimingsDao.findByResturantIdAndDayId(resturantId,dayOfWeek);
		
		
		
		return ResturantsTimings;
	}
	
	
	public String checkResturantOpenStatus(long resturantId){
		
		String isClosed="true";
		int dayOfWeek=utility.getDayNumberOFWeek();
		String TimeScale= ResturantsTimingsDao.isClosingTimeOfResturantIsPMOrAM(resturantId, dayOfWeek);
		if(TimeScale!=null) {
			if(TimeScale.equals("PM")) {
				CommonResturantsTimings ResturantsTimings=ResturantsTimingsDao.isOpenResturantNowWithInPMAndPM(resturantId, dayOfWeek);
				if(ResturantsTimings!=null) {
					isClosed="false";
					
				}
			}else if(TimeScale.equals("AM")){
				CommonResturantsTimings ResturantsTimings=ResturantsTimingsDao.isOpenResturantNowWithInPMAndAM(resturantId, dayOfWeek);
				if(ResturantsTimings!=null) {
					isClosed="false";
					
				}
			}
		}else {
			isClosed="false";
		}
		
		
		
		
		
		
		
		return isClosed;
	}
	
	
	
	
	


}
