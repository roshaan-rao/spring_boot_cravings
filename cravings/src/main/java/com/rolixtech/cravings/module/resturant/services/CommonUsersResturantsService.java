package com.rolixtech.cravings.module.resturant.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Service;

import com.rolixtech.cravings.CravingsApplication;
import com.rolixtech.cravings.module.resturant.dao.CommonCategoriesDao;
import com.rolixtech.cravings.module.resturant.dao.CommonResturantsPromotionalBannersDao;
import com.rolixtech.cravings.module.resturant.dao.CommonResturantsPromotionalBannersDetailDao;
import com.rolixtech.cravings.module.resturant.dao.CommonUsersResturantsDao;
import com.rolixtech.cravings.module.resturant.model.CommonCategories;
import com.rolixtech.cravings.module.resturant.model.CommonResturantsPromotionalBanners;
import com.rolixtech.cravings.module.resturant.model.CommonResturantsPromotionalBannersDetail;
import com.rolixtech.cravings.module.resturant.model.CommonUsersResturants;
import com.rolixtech.cravings.module.users.services.CommonUsersService;

@Service
public class CommonUsersResturantsService {
	
	@Autowired
	private CommonUsersResturantsDao UsersResturantsDao;
	
	
	@Autowired
	private CommonUsersService UsersService;
	
	@Autowired
	private CommonResturantsPromotionalBannersDao ResturantsPromotionalBannersDao;
	
	@Autowired
	private CommonResturantsPromotionalBannersDetailDao ResturantsPromotionalBannersDetailsDao;
	
	@Transactional
	public void AssignUserToResturant(long resturantId,long userId){
		if(UsersResturantsDao.existsByResturantId(resturantId)) {
			UsersResturantsDao.deleteAllByResturantId(resturantId);
		}
		CommonUsersResturants resturants=new CommonUsersResturants();
		resturants.setResturantId(resturantId);
		resturants.setUserId(userId);
		UsersResturantsDao.save(resturants);
		
	}
	
	
	public List<Map> getUsersOfResturant(long resturantId){
		
		List<Map> list=new ArrayList<>();
		List<CommonUsersResturants> ResturantsUsers=UsersResturantsDao.findAllByResturantId(resturantId);
		if(!ResturantsUsers.isEmpty()) {
			ResturantsUsers.stream().forEach(
				user->{
					Map Row=new HashMap<>();
					Row.put("id", user.getUserId());
					Row.put("label", UsersService.getUserName(user.getUserId()));
					
				
					list.add(Row);
				}
			
			);
		}
		
		return list;
	}
	
	
	public List<Map> bannersView() {
		List<Map> list=new ArrayList<>();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date currentDate= new Date();
//		String TodayDate = formatter.format(currentDate);
//		Date date1 =new SimpleDateFormat("dd-MM-yyyy").parse(TodayDate); 
		List<CommonResturantsPromotionalBanners> PromotionalBanners = ResturantsPromotionalBannersDao.findAll();
		for(int i=0; i<PromotionalBanners.size(); i++) {
			if(PromotionalBanners.get(i).getEndDate().after(currentDate) && PromotionalBanners.get(i).getIsActive()==1) {
				List<CommonResturantsPromotionalBannersDetail> PromotionalBannersDetails = ResturantsPromotionalBannersDetailsDao.findByPromotionalBannerId(PromotionalBanners.get(i).getId());
				if(PromotionalBannersDetails.isEmpty()) {
					PromotionalBannersDetails.stream().forEach(
					PromotionalBannerDetail->{Map Row=new HashMap<>();
					Row.put("id", PromotionalBannerDetail.getPromotionalBannerId());
					Row.put("resturantId", PromotionalBannerDetail.getResturantId());
					Row.put("fileName", PromotionalBannerDetail.getImageUrl());
					list.add(Row);});
				}
			}
		}
		return list;
	}

	
	
	


}
