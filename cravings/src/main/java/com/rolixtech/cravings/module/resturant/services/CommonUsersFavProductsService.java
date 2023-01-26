package com.rolixtech.cravings.module.resturant.services;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Service;

import com.rolixtech.cravings.CravingsApplication;
import com.rolixtech.cravings.module.generic.services.GenericUtility;
import com.rolixtech.cravings.module.resturant.dao.CommonCategoriesDao;
import com.rolixtech.cravings.module.resturant.dao.CommonResturantsProductsDao;
import com.rolixtech.cravings.module.resturant.dao.CommonUsersFavProductsDao;
import com.rolixtech.cravings.module.resturant.dao.CommonUsersResturantsDao;
import com.rolixtech.cravings.module.resturant.model.CommonCategories;
import com.rolixtech.cravings.module.resturant.model.CommonResturantsProducts;
import com.rolixtech.cravings.module.resturant.model.CommonUsersFavProducts;
import com.rolixtech.cravings.module.resturant.model.CommonUsersResturants;
import com.rolixtech.cravings.module.users.services.CommonUsersService;

@Service
public class CommonUsersFavProductsService {
	
	@Autowired
	private CommonUsersFavProductsDao FavProductsDao;
	
	
	@Autowired
	private CommonUsersService UsersService;
	
	@Autowired
	private CommonResturantsProductsService ResturantsProductsService;
	
	@Autowired
	private CommonResturantsProductsDao ResturantsProductsDao;	
	
	@Autowired
	private GenericUtility utility;
	
	
	@Autowired
	private CommonResturantsCategoriesService ResturantsCategoriesService;
	
	@Transactional
	public void addProductToUsersFav(long productId,long userId,int actionId){
		
		if(FavProductsDao.existsByUserIdAndProductId(userId,productId)) {
			FavProductsDao.deleteByUserIdAndProductId(userId,productId);
		}
		
		if(actionId==1) {
			FavProductsDao.save(new CommonUsersFavProducts(userId, productId));
		}
		
		
	}
	
	
	public List<Map> viewAllFavProductToUsersFav(long userId){
		
		List<Map> list=new ArrayList<>();
		List<CommonUsersFavProducts> FavProducts=FavProductsDao.findAllByUserId(userId);
		if(!FavProducts.isEmpty()) {
			FavProducts.stream().forEach(
				product->{
					if(ResturantsProductsService.checkIfProductIsActive(product.getProductId())) {
						
						CommonResturantsProducts Product=ResturantsProductsDao.findByIdAndIsActiveAndIsDeleted(product.getProductId(),1,0);
						if(Product!=null) {
							Map Row=new HashMap<>();
							Row.put("userId", product.getUserId());
							Row.put("userLabel", UsersService.getUserName(product.getUserId()));
							Row.put("id", Product.getId());
							
							Row.put("label",Product.getLabel());
							Row.put("description", Product.getDescription());
							Row.put("resturantId", Product.getResturantId());
							Row.put("salesTax", Product.getSalesTax());
							Row.put("salesTaxPercentage", Product.getSalesTaxPercentage());
							Row.put("grossAmount", Product.getGrossAmount());
							Row.put("netAmount", Product.getNetAmount());
							Row.put("discount", Product.getDiscount());
							Row.put("rate", Product.getRate());
							//Row.put("isTimingEnable", Product.getIsTimingEnable());
							
							if(Product.getIsTimingEnable()==0) {
								Row.put("isTimingEnable", Product.getIsActive());
								Row.put("isTimingEnableLable", "No");
								Row.put("availabilityFrom", "");
								Row.put("availabilityTo", "");
								
							}else {
								Row.put("isTimingEnable", Product.getIsActive());
								Row.put("isTimingEnableLable", "Yes");
								
								System.out.println(Product.getAvailabilityFrom());
								
								Row.put("availabilityFrom", DateUtils.addHours(Product.getAvailabilityFrom(), 5).getTime());
								
								try { 
									Row.put("availabilityFromDisplay", utility.millisecondstoTime(DateUtils.addHours(Product.getAvailabilityFrom(), 5).getTime()));
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
									Row.put("availabilityFromDisplay", "00:00:00");
									
								}
								
								Row.put("availabilityTo", DateUtils.addHours(Product.getAvailabilityTo(), 5).getTime());
								
							}
							
							
							
							Row.put("isAvailable", Product.getIsAvailable());
							if(Product.getIsActive()==0) {
								Row.put("isActive", Product.getIsActive());
								Row.put("isActiveLabel", "In-Active");
							}else {
								Row.put("isActive", Product.getIsActive());
								Row.put("isActiveLabel", "Active");
							}
							Row.put("productImgUrl", Product.getProductImgUrl());
							Row.put("rating", Product.getRating());
							Row.put("resturantCategoryId", Product.getResturantCategoryId());
							Row.put("resturantCategoryLabel", ResturantsCategoriesService.findLabelById(Product.getResturantCategoryId()));
							if(Product.getIsExtra()==0) {
								Row.put("isExtraLabel","No");
								Row.put("isExtra",Product.getIsExtra());
							}else {
								Row.put("isExtraLabel","Yes");
								Row.put("isExtra",Product.getIsExtra());
								 
								
							}
							list.add(Row);
						}
						
						
					}
					
				}
			
			);
		}
		
		return list;
	}
	
	
	
	public int isProductLiked(long productId,long userId) {
		int isLiked=0;
		if(FavProductsDao.existsByUserIdAndProductId(userId,productId)) {
			isLiked=1;
		}
		return isLiked;
		
	}

	
	
	


}
