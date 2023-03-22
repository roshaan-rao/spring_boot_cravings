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
import com.rolixtech.cravings.module.users.dao.CommonRoleBasedFeaturesDao;
import com.rolixtech.cravings.module.users.dao.CommonRoleDao;
import com.rolixtech.cravings.module.users.dao.CommonUsersFeaturesDao;
import com.rolixtech.cravings.module.users.models.CommonFeatures;
import com.rolixtech.cravings.module.users.models.CommonFeaturesGroups;
import com.rolixtech.cravings.module.users.models.CommonRole;
import com.rolixtech.cravings.module.users.models.CommonRoleBasedFeatures;
import com.rolixtech.cravings.module.users.models.CommonUsersFeatures;
@Service
public class CommonRoleBasedFeaturesService {
	
	@Autowired
	private CommonRoleBasedFeaturesDao RoleBasedFeaturesDao;
	
	@Autowired
	private CommonFeaturesDao FeaturesDao;
	
	
	@Autowired
	private CommonFeaturesGroupsDao FeaturesGroupsDao;
	
	

	
	
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
	public void Save(long[] roleIds,long[] roleFeaturesId,long assignedBy) {
		if(roleIds!=null) {
			for(int i=0;i<roleIds.length;i++) {
				long roleId=roleIds[i];
				if(RoleBasedFeaturesDao.existsByRoleId(roleId)){
					RoleBasedFeaturesDao.deleteAllByRoleId(roleId);
				}
				
				if(roleFeaturesId!=null) {
					for(int j=0;j<roleFeaturesId.length;j++) {
						long featureId=roleFeaturesId[j];
						CommonRoleBasedFeatures roleFeatures=new CommonRoleBasedFeatures();
						roleFeatures.setRoleId(roleId);
						roleFeatures.setFeatureId(featureId);
						roleFeatures.setAssignedBy(assignedBy);
						roleFeatures.setAssignedOn(new Date());
						RoleBasedFeaturesDao.save(roleFeatures);
					}
				}
				
			}
			
			
		}
		
	}
	
	
	public List<Map> getAllRolesAllowedFeaturesGroupWise(long roleId) {
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
									if(isChecked(roleId,feature.getId()).equals("true")) {
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

	
	public List<Map> getAllRoleAssignedFeaturesGroupWiseWithAllFeaturesForEdit(long roleId) {
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
									featureRow.put("isChecked",isChecked(roleId,feature.getId()));
									featureRow.put("isCheckedBol",isChecked(roleId,feature.getId())=="true"?true:false);
									featureRow.put("isCheckedNum",isChecked(roleId,feature.getId())=="true"?1:0);
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
	public void deleteAllRoleBasedFeaturesRights(long roleId) {
		
		RoleBasedFeaturesDao.deleteAllByRoleId(roleId);
		
	}

	
	
	


	public List<Long> getAllRolesFromRoleBasedFeatures() {
		List<Long> purpseList=new ArrayList<>();
		purpseList=RoleBasedFeaturesDao.findAllRoleIds();
		
		return purpseList;
	}
	
	
	public String isChecked(long roleId,long featureId) {
		
		String isChecked="false";
		if(RoleBasedFeaturesDao.existsByRoleIdAndFeatureId(roleId,featureId)) {
			isChecked="true";
		}
		
		return isChecked;
	}
	
}
