package com.rolixtech.cravings.module.users.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rolixtech.cravings.module.users.dao.CommonFeaturesDao;
import com.rolixtech.cravings.module.users.dao.CommonFeaturesDao.CustomCommonFeatures;
import com.rolixtech.cravings.module.users.dao.CommonFeaturesGroupsDao;
import com.rolixtech.cravings.module.users.dao.CommonRoleDao;
import com.rolixtech.cravings.module.users.dao.CommonUsersFeaturesDao;
import com.rolixtech.cravings.module.users.models.CommonFeatures;
import com.rolixtech.cravings.module.users.models.CommonFeaturesGroups;
import com.rolixtech.cravings.module.users.models.CommonRole;
import com.rolixtech.cravings.module.users.models.CommonUsersFeatures;
@Service
public class CommonUsersFeaturesService {
	
	@Autowired
	private CommonUsersFeaturesDao UsersFeaturesDao;
	
	@Autowired
	private CommonFeaturesDao FeaturesDao;
	
	
	@Autowired
	private CommonFeaturesGroupsDao FeaturesGroupsDao;
	
	@Autowired
	private CommonRoleBasedFeaturesService RoleBasedFeaturesService;
	
	
	public List<Map> getAllFeaturesGroupWise() {
		List<Map> list=new ArrayList<>();
		List<CommonFeaturesGroups> Groups = FeaturesGroupsDao.findAll();
		if(!Groups.isEmpty()) {
			Groups.forEach(
				group-> {
					Map Row=new HashMap<>();
					Row.put("id", group.getId());
					Row.put("label", group.getLabel());
					Row.put("iconClass", group.getIconClass());
					Row.put("iconName", group.getIconName());
					Row.put("ariaLabel", group.getAriaLabel());
					List<Map> featureslist=new ArrayList<>();
					List<CommonFeatures> Features = FeaturesDao.findAllByGroupId(group.getId());
					if(!Features.isEmpty()) {
						Features.forEach(
								feature-> {
									Map featureRow=new HashMap<>();
									featureRow.put("id", feature.getId());
									featureRow.put("url", feature.getUrl());
									featureRow.put("label", feature.getLabel());
									featureslist.add(featureRow);
								}
						);
					}
					
					Row.put("features",featureslist);
					list.add(Row);
				}
			);
		}
		return list;
		
	}
	
	@Transactional
	public void Save(long[] userIds,long[] userFeaturesId,long assignedBy) {
		if(userIds!=null) {
			for(int i=0;i<userIds.length;i++) {
				long userId=userIds[i];
				if(UsersFeaturesDao.existsByUserId(userId)){
					UsersFeaturesDao.deleteAllByUserId(userId);
				}
				
				if(userFeaturesId!=null) {
					for(int j=0;j<userFeaturesId.length;j++) {
						long featureId=userFeaturesId[j];
						CommonUsersFeatures userFeatures=new CommonUsersFeatures();
						userFeatures.setUserId(userId);
						userFeatures.setFeatureId(featureId);
						userFeatures.setAssignedBy(assignedBy);
						userFeatures.setAssignedOn(new Date());
						UsersFeaturesDao.save(userFeatures);
					}
				}
				
			}
			
			
		}
		
	}
	
	
	public List<Map> getAllUserAllowedFeaturesGroupWise(long userId) {
		List<Map> list=new ArrayList<>();
		List<CommonFeaturesGroups> Groups = FeaturesGroupsDao.findAll();
		if(!Groups.isEmpty()) {
			Groups.forEach(
				group-> {
					Map Row=new HashMap<>();
					Row.put("id", group.getId());
					Row.put("label", group.getLabel());
					Row.put("iconClass", group.getIconClass());
					Row.put("iconName", group.getIconName());
					Row.put("ariaLabel", group.getAriaLabel());
					List<Map> featureslist=new ArrayList<>();
					List<CommonFeatures> Features = FeaturesDao.findAllByGroupId(group.getId());
					if(!Features.isEmpty()) {
						Features.forEach(
								feature-> {
									Map featureRow=new HashMap<>();
									featureRow.put("id", feature.getId());
									featureRow.put("url", feature.getUrl());
									featureRow.put("label", feature.getLabel());
									if(isChecked(userId,feature.getId()).equals("true")) {
										featureslist.add(featureRow);
									}
									
								}
						);
					}
					if(!featureslist.isEmpty()) {
						Row.put("features",featureslist);
						list.add(Row);
					}
					
					
					
				}
			);
		}
		
		
		return list;
		
	}
//	public List<Map> () {
//		List<Map> list=new ArrayList<>();
//		List<CommonFeaturesGroups> Groups = FeaturesGroupsDao.findAll();
//		if(!Groups.isEmpty()) {
//			Groups.forEach(
//				group-> {
//					Map Row=new HashMap<>();
//					Row.put("id", group.getId());
//					Row.put("label", group.getLabel());
//					Row.put("iconClass", group.getIconClass());
//					Row.put("iconName", group.getIconName());
//					Row.put("ariaLabel", group.getAriaLabel());
//					List<Map> featureslist=new ArrayList<>();
//					List<CommonFeatures> Features = FeaturesDao.findAllByGroupId(group.getId());
//					if(!Features.isEmpty()) {
//						Features.forEach(
//								feature-> {
//									Map featureRow=new HashMap<>();
//									featureRow.put("id", feature.getId());
//									featureRow.put("url", feature.getUrl());
//									featureRow.put("label", feature.getLabel());
//									featureslist.add(featureRow);
//								}
//						);
//					}
//					
//					Row.put("features",featureslist);
//					list.add(Row);
//				}
//			);
//		}
//		return list;
//		
//	}
	
	
	public List<Map> getAllUserAssignedFeaturesGroupWiseWithAllFeaturesForEditAndRoleId(long userId,long roleId) {
		List<Map> list=new ArrayList<>();
		List<CommonFeaturesGroups> Groups = FeaturesGroupsDao.findAll();
		if(!Groups.isEmpty()) {
			Groups.forEach(
				group-> {
					Map Row=new HashMap<>();
					Row.put("id", group.getId());
					Row.put("label", group.getLabel());
					Row.put("iconClass", group.getIconClass());
					Row.put("iconName", group.getIconName());
					Row.put("ariaLabel", group.getAriaLabel());
					List<Map> featureslist=new ArrayList<>();
					List<CommonFeatures> Features = FeaturesDao.findAllByGroupId(group.getId());
					if(!Features.isEmpty()) {
						Features.forEach(
								feature-> {
									Map featureRow=new HashMap<>();
									featureRow.put("id", feature.getId());
									featureRow.put("url", feature.getUrl());
									featureRow.put("label", feature.getLabel());
									
									if(roleId==0) {
										featureRow.put("isChecked",isChecked(userId,feature.getId()));
										featureRow.put("isCheckedBol",isChecked(userId,feature.getId())=="true"?true:false);
										featureRow.put("isCheckedNum",isChecked(userId,feature.getId())=="true"?1:0);
									}else {
										featureRow.put("isChecked",RoleBasedFeaturesService.isChecked(roleId,feature.getId()));
										featureRow.put("isCheckedBol",RoleBasedFeaturesService.isChecked(roleId,feature.getId())=="true"?true:false);
										featureRow.put("isCheckedNum",RoleBasedFeaturesService.isChecked(roleId,feature.getId())=="true"?1:0);
										
									}
									
									
									featureslist.add(featureRow);
								}
						);
					}
					
					Row.put("features",featureslist);
					list.add(Row);
					
					
				}
			);
		}
		
		
		return list;
		
	}
	
	@Transactional
	public void deleteAllUserFeaturesRights(long userId) {
		
		UsersFeaturesDao.deleteAllByUserId(userId);
		
	}

	
	
	public List<Map> getAllUserAssignedFeaturesGroupWise(long userId) {
		List<Map> list=new ArrayList<>();
		List<CommonFeaturesGroups> Groups = FeaturesGroupsDao.findAll();
		if(!Groups.isEmpty()) {
			Groups.forEach(
				group-> {
					Map Row=new HashMap<>();
					Row.put("id", group.getId());
					Row.put("label", group.getLabel());
					Row.put("iconClass", group.getIconClass());
					Row.put("iconName", group.getIconName());
					Row.put("ariaLabel", group.getAriaLabel());
					List<Map> featureslist=new ArrayList<>();
					List<CustomCommonFeatures> Features = FeaturesDao.findAllByGroupIdAndUserId(group.getId(),userId);
					if(!Features.isEmpty()) {
						Features.forEach(
								feature-> {
									Map featureRow=new HashMap<>();
									featureRow.put("id", feature.getId());
									featureRow.put("url", feature.getUrl());
									featureRow.put("label", feature.getLabel());
									featureslist.add(featureRow);
								}
						);
					}
					if(!featureslist.isEmpty()) {
						Row.put("features",featureslist);
						list.add(Row);
					}
					
				}
			);
		}
		
		
		return list;
		
	}


	public List<Long> getAllUsersFromUserFeatures() {
		List<Long> purpseList=new ArrayList<>();
		purpseList=UsersFeaturesDao.findAllUserIds();
		
		return purpseList;
	}
	
	
	public String isChecked(long userId,long featureId) {
		
		String isChecked="false";
		if(UsersFeaturesDao.existsByUserIdAndFeatureId(userId,featureId)) {
			isChecked="true";
		}
		
		return isChecked;
	}
	
}
