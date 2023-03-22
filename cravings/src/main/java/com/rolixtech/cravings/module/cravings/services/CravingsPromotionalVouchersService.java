package com.rolixtech.cravings.module.cravings.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rolixtech.cravings.module.cravings.dao.CravingsPromotionalGroupVouchersDao;
import com.rolixtech.cravings.module.cravings.dao.CravingsPromotionalVouchersChangeLogDao;
import com.rolixtech.cravings.module.cravings.dao.CravingsPromotionalVouchersDao;
import com.rolixtech.cravings.module.cravings.model.CravingsPromotionalGroupVouchers;
import com.rolixtech.cravings.module.cravings.model.CravingsPromotionalVouchers;
import com.rolixtech.cravings.module.cravings.model.CravingsPromotionalVouchersChangeLog;
import com.rolixtech.cravings.module.generic.dao.CommonCitiesDao;
import com.rolixtech.cravings.module.generic.dao.CommonProvincesDao;
import com.rolixtech.cravings.module.generic.model.CommonCities;
import com.rolixtech.cravings.module.generic.model.CommonProvinces;
import com.rolixtech.cravings.module.generic.services.GenericUtility;
import com.rolixtech.cravings.module.resturant.model.CommonResturants;
import com.rolixtech.cravings.module.users.dao.CommonRoleDao;
import com.rolixtech.cravings.module.users.models.CommonRole;

@Service
public class CravingsPromotionalVouchersService {

	
	@Autowired
	private CravingsPromotionalVouchersDao PromotionalVouchersDao; 
	
	
	@Autowired
	private CravingsPromotionalVouchersChangeLogDao VouchersChangeLogDao; 
	
	@Autowired
	private CravingsPromotionalVouchersStatusTypesService VouchersStatusTypesService;
	
	@Autowired
	private CravingsPromotionalVouchersPurposeTypesService VouchersPurposeTypesService;
	
	@Autowired
	private GenericUtility utility;
	
	@Autowired
	private CravingsPromotionalGroupVouchersDao PromotionalGroupVouchersDao; 
	
	@Transactional
	public void savePromtionalVouchers(int isGroup, long recordId, String groupTitle,String prefixStr,long userId,int totalNumber,double amount,double percetageVal,Date validFrom,Date validTo,long valueType,long purposeId,int statusId ) throws Exception {
		
		if(recordId==0) {
			if(PromotionalGroupVouchersDao.existsByGroupTitle(groupTitle)) {
				throw new Exception("Group Title Already Exists.");
			}
			CravingsPromotionalGroupVouchers group=new CravingsPromotionalGroupVouchers();
			
			if(groupTitle!=null && !groupTitle.equals("")) {
				
				group.setGroupTitle(groupTitle);
				PromotionalGroupVouchersDao.save(group);
				
			}
			
			if(totalNumber>0) {
				List<String> alreadyCreatedPromos=new ArrayList<>();
				for(int i=1;i<=totalNumber;i++) {
					
					String uniqueVoucherCode="";
					String  postFix="";
					
					while(true) {
					  postFix=utility.createRandomCode(8); 
					  String CompeleteStr=prefixStr+postFix;
					  if(!PromotionalVouchersDao.existsByCompleteString(CompeleteStr)) {
						   uniqueVoucherCode=CompeleteStr; 
					      break;
					   }
					}
						
					if(!alreadyCreatedPromos.contains(uniqueVoucherCode)) { 
						CravingsPromotionalVouchers PromotionalVouchers=new CravingsPromotionalVouchers();
						if(groupTitle!=null && !groupTitle.equals("")) {
							
							PromotionalVouchers.setGroupId(group.getId());
							PromotionalVouchers.setIsGroup(1);
						}else {
							PromotionalVouchers.setIsGroup(0);
						}
						
						PromotionalVouchers.setCompleteString(uniqueVoucherCode);
						PromotionalVouchers.setCreatedBy(userId);
						PromotionalVouchers.setCreatedOn(new Date());
						PromotionalVouchers.setStatus(0);
						PromotionalVouchers.setPostFixStr(postFix);
						PromotionalVouchers.setPreFixStr(prefixStr);
						PromotionalVouchers.setValueType(valueType);
						if(valueType==1) {
							PromotionalVouchers.setAmount(amount);
						}else {
							PromotionalVouchers.setPercentageVal(percetageVal);
						}
						 
					
						PromotionalVouchers.setValidFrom(validFrom);
						PromotionalVouchers.setValidTo(validTo);
						PromotionalVouchers.setVoucherPurposeId(valueType);
						PromotionalVouchersDao.save(PromotionalVouchers);
						
						alreadyCreatedPromos.add(uniqueVoucherCode);
					
					}
					
					
				}	
			
				
			}
		}else {
			if(isGroup==1) {
				if(PromotionalGroupVouchersDao.existsByGroupTitleAndIdNot(groupTitle,recordId)) {
					throw new Exception("Group Title Already Exists.");
				}
				
				CravingsPromotionalGroupVouchers group=PromotionalGroupVouchersDao.findById(recordId);
				if(group!=null) {
					if(groupTitle!=null && !groupTitle.equals("")) {
						
						group.setGroupTitle(groupTitle);
						PromotionalGroupVouchersDao.save(group);
						
					}
					
				}
				
				List<CravingsPromotionalVouchers> PromotionalVouchers=PromotionalVouchersDao.findAllByGroupIdAndIsRedeemed(recordId, 0);
				if(!PromotionalVouchers.isEmpty()) {
					for(int j=0;j<PromotionalVouchers.size();j++) {
						
						CravingsPromotionalVouchersChangeLog PromotionalVouchersLog=new CravingsPromotionalVouchersChangeLog();
						CravingsPromotionalVouchers iRealChange=PromotionalVouchers.get(j);
								BeanUtils.copyProperties(iRealChange, PromotionalVouchersLog);
								
								PromotionalVouchersLog.setId(0);
								PromotionalVouchersLog.setRecordId(recordId);
								PromotionalVouchersLog.setLogCreatedBy(userId);
								PromotionalVouchersLog.setLogCreatedOn(new Date());
								PromotionalVouchersLog.setLogReason("Voucher Edit Save");
								PromotionalVouchersLog.setLogTypeId(utility.parseLong("1"));
								VouchersChangeLogDao.save(PromotionalVouchersLog);
						
//						CravingsPromotionalVouchers oldVoucher=PromotionalVouchers.get(j);
						
						iRealChange.setCompleteString(prefixStr+iRealChange.getPostFixStr());
						iRealChange.setPreFixStr(prefixStr);
						iRealChange.setValueType(valueType);
						iRealChange.setStatus(statusId);
						if(valueType==1) {
							iRealChange.setAmount(amount);
						}else {
							iRealChange.setPercentageVal(percetageVal);
						}
						iRealChange.setValidFrom(validFrom);
						iRealChange.setValidTo(validTo);
						iRealChange.setVoucherPurposeId(valueType);
						PromotionalVouchersDao.save(iRealChange);
					}
				}
				
				
			}else if(isGroup==0) {
				
				CravingsPromotionalVouchersChangeLog PromotionalVouchersLog=new CravingsPromotionalVouchersChangeLog();
				CravingsPromotionalVouchers iRealChange=PromotionalVouchersDao.findById(recordId);
						BeanUtils.copyProperties(iRealChange, PromotionalVouchersLog);
						
						PromotionalVouchersLog.setId(0);
						PromotionalVouchersLog.setRecordId(recordId);
						PromotionalVouchersLog.setLogCreatedBy(userId);
						PromotionalVouchersLog.setLogCreatedOn(new Date());
						PromotionalVouchersLog.setLogReason("Voucher Edit Save");
						PromotionalVouchersLog.setLogTypeId(utility.parseLong("1"));
						VouchersChangeLogDao.save(PromotionalVouchersLog);
			
				if(iRealChange!=null) {
					iRealChange.setCompleteString(prefixStr+iRealChange.getPostFixStr());
					iRealChange.setPreFixStr(prefixStr);
					iRealChange.setValueType(valueType);
					iRealChange.setStatus(statusId);
					if(valueType==1) {
						iRealChange.setAmount(amount);
					}else {
						iRealChange.setPercentageVal(percetageVal);
					}
					iRealChange.setValidFrom(validFrom);
					iRealChange.setValidTo(validTo);
					iRealChange.setVoucherPurposeId(valueType);
					PromotionalVouchersDao.save(iRealChange);
				}
				
			}
			
			
		}	
		
	}
	
	
	@Transactional
	public void EditSave(long recordId, String prefixStr, Double amount, Double percentageVal,Date validFrom,Date validTo,long valueType ,long userId) {
		CravingsPromotionalVouchers PromotionalVouchers=PromotionalVouchersDao.findById(recordId);
		if(PromotionalVouchers!=null) {
			
			CravingsPromotionalVouchersChangeLog PromotionalVouchersLog=new CravingsPromotionalVouchersChangeLog();
			CravingsPromotionalVouchers iRealChange=PromotionalVouchersDao.findById(recordId);
					BeanUtils.copyProperties(iRealChange, PromotionalVouchersLog);
					
					PromotionalVouchersLog.setId(0);
					PromotionalVouchersLog.setRecordId(recordId);
					PromotionalVouchersLog.setLogCreatedBy(userId);
					PromotionalVouchersLog.setLogCreatedOn(new Date());
					PromotionalVouchersLog.setLogReason("Voucher Edit Save");
					PromotionalVouchersLog.setLogTypeId(utility.parseLong("1"));
					VouchersChangeLogDao.save(PromotionalVouchersLog);
									
					
							
		}
		
		
		
	}

	public List<Map> view(long recordId,String prefixString) {
		List<Map> list=new ArrayList<>();
		
		List<CravingsPromotionalVouchers> vouchers=new ArrayList<>();
		if(recordId!=0) {
			CravingsPromotionalVouchers PromotionalVouchers=PromotionalVouchersDao.findById(recordId);
			if(PromotionalVouchers!=null) {
				vouchers.add(PromotionalVouchers);
			}
		}else {
			if(!prefixString.equals("")) {
				vouchers=PromotionalVouchersDao.findAllByPreFixStr(prefixString);
			} 	
		}		
				
		if(!vouchers.isEmpty()) {
			vouchers.stream().forEach(
					voucher->{
						Map Row=new HashMap<>();
						Row.put("id", voucher.getId());
						Row.put("voucherCode", voucher.getCompleteString());
						Row.put("voucherPreFix", voucher.getPreFixStr());
						Row.put("voucherPostFix", voucher.getPostFixStr());
						Row.put("validFrom", voucher.getValidFrom());
						Row.put("validTo", voucher.getValidTo());
						Row.put("validFromToDisplay", utility.getDisplayDateddMMYYYY(voucher.getValidFrom()));
						Row.put("validToToDisplay", utility.getDisplayDateddMMYYYY(voucher.getValidTo()));
						Row.put("isGroup", voucher.getIsGroup());
						if(utility.parseInt(voucher.getIsGroup())!=0) {
							long groupId=voucher.getGroupId();
							CravingsPromotionalGroupVouchers group=PromotionalGroupVouchersDao.findById(groupId);
							Row.put("isGroupLabel","Yes");
							Row.put("groupTitle",group.getGroupTitle());
							Row.put("groupId",group.getId());
						}else {
							Row.put("groupId","");
							Row.put("isGrouplabel","No");
							Row.put("groupTitle","");
						}
						
						if(utility.parseLong(voucher.getValueType())==1) {
							Row.put("amount",voucher.getAmount());
							Row.put("percentageVal",0.0);
						
							
						}else {
							Row.put("percentageVal",voucher.getPercentageVal());
							Row.put("amount",0.0);
						}
						
						Row.put("isRedeemed", voucher.getIsRedeemed());
						Row.put("statusId", voucher.getStatus());
						Row.put("statusLabel", VouchersStatusTypesService.findLabelById(voucher.getStatus()));
						Row.put("createdOn",voucher.getCreatedOn());
						Row.put("createdBy",voucher.getCreatedBy());
						Row.put("purposelabel",(voucher.getVoucherPurposeId()));
						Row.put("purposeId",VouchersPurposeTypesService.findLabelById(voucher.getVoucherPurposeId()));
					
						
						list.add(Row);
				}
				
			);
		}
		
		return list;
	}
	
	
	public List<Map> view(long recordId) {
		List<Map> list=new ArrayList<>();
		List<CravingsPromotionalVouchers> vouchers=new ArrayList<>();
		if(recordId!=0) {
			CravingsPromotionalVouchers PromotionalVouchers=PromotionalVouchersDao.findById(recordId);
			if(PromotionalVouchers!=null) {
				vouchers.add(PromotionalVouchers);
			}
		}else {
			vouchers=PromotionalVouchersDao.findAll();	
		}
		
				
		if(!vouchers.isEmpty()) {
			vouchers.stream().forEach(
					voucher->{
						Map Row=new HashMap<>();
						Row.put("id", voucher.getId());
						Row.put("voucherCode", voucher.getCompleteString());
						Row.put("voucherPreFix", voucher.getPreFixStr());
						Row.put("voucherPostFix", voucher.getPostFixStr());
						Row.put("validFrom", voucher.getValidFrom());
						Row.put("validTo", voucher.getValidTo());
						Row.put("validFromToDisplay", utility.getDisplayDateddMMYYYY(voucher.getValidFrom()));
						Row.put("validToToDisplay", utility.getDisplayDateddMMYYYY(voucher.getValidTo()));
						Row.put("isRedeemed", voucher.getIsRedeemed());
						Row.put("statusId", voucher.getStatus());
						Row.put("statusLabel", VouchersStatusTypesService.findLabelById(voucher.getStatus()));
						Row.put("createdOn",voucher.getCreatedOn());
						Row.put("createdBy",voucher.getCreatedBy());
						Row.put("isGroup",voucher.getIsGroup());
						if(utility.parseInt(voucher.getIsGroup())!=0) {
							long groupId=voucher.getGroupId();
							CravingsPromotionalGroupVouchers group=PromotionalGroupVouchersDao.findById(groupId);
							Row.put("isGroupLabel","Yes");
							Row.put("groupTitle",group.getGroupTitle());
							Row.put("groupId",group.getId());
						}else {
							Row.put("groupId","");
							Row.put("isGrouplabel","No");
							Row.put("groupTitle","");
						}
						
						if(utility.parseLong(voucher.getValueType())==1) {
							Row.put("amount",voucher.getAmount());
							Row.put("percentageVal",0.0);
						
							
						}else {
							Row.put("percentageVal",voucher.getPercentageVal());
							Row.put("amount",0.0);
						}
						Row.put("purposeId",voucher.getVoucherPurposeId());
						Row.put("purposeLabel",VouchersPurposeTypesService.findLabelById(voucher.getVoucherPurposeId()));
						
						list.add(Row);
				}
				
			);
		}
		
		return list;
	}
	
	
	public List<Map> searchView(int isGroup, String keyWord) {
		List<Map> list=new ArrayList<>();
		List<CravingsPromotionalVouchers> vouchers=new ArrayList<>();
		if(isGroup==1 && !keyWord.equals("")) {
			vouchers=PromotionalVouchersDao.findAllByIsGroupAndGroupTitle(1,keyWord);	
		}else if(isGroup==0 && !keyWord.equals("")) {
			vouchers=PromotionalVouchersDao.findAllByIsGroupAndCompleteString(0,keyWord);	
		}else {
			vouchers=PromotionalVouchersDao.findAll();
		}
		
				
		if(!vouchers.isEmpty()) {
			vouchers.stream().forEach(
					voucher->{
						Map Row=new HashMap<>();
						Row.put("id", voucher.getId());
						Row.put("voucherCode", voucher.getCompleteString());
						Row.put("voucherPreFix", voucher.getPreFixStr());
						Row.put("voucherPostFix", voucher.getPostFixStr());
						Row.put("validFrom", voucher.getValidFrom());
						Row.put("validTo", voucher.getValidTo());
						Row.put("validFromToDisplay", utility.getDisplayDateddMMYYYY(voucher.getValidFrom()));
						Row.put("validToToDisplay", utility.getDisplayDateddMMYYYY(voucher.getValidTo()));
						Row.put("isRedeemed", voucher.getIsRedeemed());
						Row.put("statusId", voucher.getStatus());
						Row.put("statusLabel", VouchersStatusTypesService.findLabelById(voucher.getStatus()));
						Row.put("createdOn",voucher.getCreatedOn());
						Row.put("createdBy",voucher.getCreatedBy());
						Row.put("isGroup",voucher.getIsGroup());
						if(utility.parseInt(voucher.getIsGroup())!=0) {
							long groupId=voucher.getGroupId();
							CravingsPromotionalGroupVouchers group=PromotionalGroupVouchersDao.findById(groupId);
							Row.put("isGroupLabel","Yes");
							Row.put("groupTitle",group.getGroupTitle());
							Row.put("groupId",group.getId());
						}else {
							Row.put("groupId","");
							Row.put("isGrouplabel","No");
							Row.put("groupTitle","");
						}
						
						if(utility.parseLong(voucher.getValueType())==1) {
							Row.put("amount",voucher.getAmount());
							Row.put("percentageVal",0.0);
						
							
						}else {
							Row.put("percentageVal",voucher.getPercentageVal());
							Row.put("amount",0.0);
						}
						Row.put("purposeId",voucher.getVoucherPurposeId());
						Row.put("purposeLabel",VouchersPurposeTypesService.findLabelById(voucher.getVoucherPurposeId()));
						
						list.add(Row);
				}
				
			);
		}
		
		return list;
	}





	public void ChangeStatus(int isGroup,long recordId, int status, long userId) {
		// TODO Auto-generated method stub
		
				
				
				
				if(isGroup==0) {
					CravingsPromotionalVouchersChangeLog PromotionalVouchersLog=new CravingsPromotionalVouchersChangeLog();
					CravingsPromotionalVouchers iRealChange=PromotionalVouchersDao.findById(recordId);
							BeanUtils.copyProperties(iRealChange, PromotionalVouchersLog);
							
							PromotionalVouchersLog.setId(0);
							PromotionalVouchersLog.setRecordId(recordId);
							PromotionalVouchersLog.setLogCreatedBy(userId);
							PromotionalVouchersLog.setLogCreatedOn(new Date());
							PromotionalVouchersLog.setLogReason("Voucher Status Change");
							PromotionalVouchersLog.setLogTypeId(utility.parseLong("1"));
							VouchersChangeLogDao.save(PromotionalVouchersLog);
							
							iRealChange.setStatus(status);
							iRealChange.setStatusChangeBy(userId);
							iRealChange.setStatusChangedOn(new Date());
							PromotionalVouchersDao.save(iRealChange);
				}else if(isGroup==1){
					
					List<CravingsPromotionalVouchers> PromotionalVouchersList=PromotionalVouchersDao.findAllByGroupIdAndIsRedeemed(recordId,0);
					if(!PromotionalVouchersList.isEmpty()) {
						for(int i=0;i<PromotionalVouchersList.size();i++) {
							CravingsPromotionalVouchersChangeLog PromotionalVouchersLog=new CravingsPromotionalVouchersChangeLog();
							CravingsPromotionalVouchers iRealChange=PromotionalVouchersDao.findById(PromotionalVouchersList.get(i).getId());
									BeanUtils.copyProperties(iRealChange, PromotionalVouchersLog);
									
									PromotionalVouchersLog.setId(0);
									PromotionalVouchersLog.setRecordId(iRealChange.getId());
									PromotionalVouchersLog.setLogCreatedBy(userId);
									PromotionalVouchersLog.setLogCreatedOn(new Date());
									PromotionalVouchersLog.setLogReason("Voucher Group Status Change");
									PromotionalVouchersLog.setLogTypeId(utility.parseLong("1"));
									VouchersChangeLogDao.save(PromotionalVouchersLog);
									
									iRealChange.setStatus(status);
									iRealChange.setStatusChangeBy(userId);
									iRealChange.setStatusChangedOn(new Date());
									PromotionalVouchersDao.save(iRealChange);
						}
					}
					
					
					
				}		
	}






	
	
	
	@Transactional
	public void deleteVoucher(int isGroup, long recordId, long userId) {
		// TODO Auto-generated method stub
		
		
		if(isGroup==0) {
			CravingsPromotionalVouchersChangeLog PromotionalVouchersLog=new CravingsPromotionalVouchersChangeLog();
			CravingsPromotionalVouchers iRealChange=PromotionalVouchersDao.findById(recordId);
					BeanUtils.copyProperties(iRealChange, PromotionalVouchersLog);
					
					PromotionalVouchersLog.setId(0);
					PromotionalVouchersLog.setRecordId(recordId);
					PromotionalVouchersLog.setLogCreatedBy(userId);
					PromotionalVouchersLog.setLogCreatedOn(new Date());
					PromotionalVouchersLog.setLogReason("Voucher Single Delete");
					PromotionalVouchersLog.setLogTypeId(utility.parseLong("1"));
					VouchersChangeLogDao.save(PromotionalVouchersLog);
					
					
					PromotionalVouchersDao.deleteById(recordId);
		}else if(isGroup==1){
			
			List<CravingsPromotionalVouchers> PromotionalVouchersList=PromotionalVouchersDao.findAllByGroupIdAndIsRedeemed(recordId,0);
			if(!PromotionalVouchersList.isEmpty()) {
				for(int i=0;i<PromotionalVouchersList.size();i++) {
					CravingsPromotionalVouchersChangeLog PromotionalVouchersLog=new CravingsPromotionalVouchersChangeLog();
					CravingsPromotionalVouchers iRealChange=PromotionalVouchersDao.findById(PromotionalVouchersList.get(i).getId());
							BeanUtils.copyProperties(iRealChange, PromotionalVouchersLog);
							
							PromotionalVouchersLog.setId(0);
							PromotionalVouchersLog.setRecordId(iRealChange.getId());
							PromotionalVouchersLog.setLogCreatedBy(userId);
							PromotionalVouchersLog.setLogCreatedOn(new Date());
							PromotionalVouchersLog.setLogReason("Voucher Group Delete");
							PromotionalVouchersLog.setLogTypeId(utility.parseLong("1"));
							VouchersChangeLogDao.save(PromotionalVouchersLog);
							PromotionalVouchersDao.deleteById(iRealChange.getId());
				}
			}
			
			if(!PromotionalVouchersDao.existsByGroupId(recordId)) {
				PromotionalGroupVouchersDao.deleteById(recordId);
			}
			
		}
		
	}





	public List<Map> validateVoucher(String voucherCode) throws Exception {
		List<Map> list=new ArrayList<>();
		CravingsPromotionalVouchers voucher=PromotionalVouchersDao.findByCompleteString(voucherCode);
		if(voucher!=null) {
			
			
			if(voucher.getIsRedeemed()==1) {
				throw new Exception("Voucher has already been used.");
			}else {
//				System.out.println(DateUtils.addDays(new Date(), 0));
//				System.out.println(DateUtils.addDays(voucher.getValidFrom(), 0));
//				if(DateUtils.addDays(new Date(), 0).after(DateUtils.addDays(voucher.getValidFrom(), 0))) {
//					System.out.println("Yooo 1");
//				}
//				
//				System.out.println(DateUtils.addDays(voucher.getValidTo(), 0));
//				System.out.println(DateUtils.addDays(new Date(), 0));
//				
//				if(DateUtils.addDays(voucher.getValidTo(), 0).before()) {
//					System.out.println("Yooo 2");
//				}
				
				
				
				
				if(DateUtils.addDays(new Date(), 0).after(DateUtils.addDays(voucher.getValidFrom(), 0)) && DateUtils.addDays(new Date() , 0).before(DateUtils.addDays(voucher.getValidTo(), 0))) {
					Map Row=new HashMap<>();
					Row.put("voucherId", voucher.getId());
					if(voucher.getValueType()==1) {
						Row.put("isPercentage","false");
						Row.put("amount",voucher.getAmount());
					}else if(voucher.getValueType()==2){
						Row.put("isPercentage","true");
						Row.put("amount",voucher.getPercentageVal());
						
					}
					
//					if(voucher.getValueType()==1) { 
//						
//						
//					}else {
//						
//					}
//					
					list.add(Row);
				}else {
//					
					throw new Exception("Voucher has been expired.");
//					
				}
			} 
			
			
			
			
		}else{
			throw new Exception("Voucher is not valid.");
		}
		return list;
	}


	public void voucherRedeemed(long voucherId) {
		
		CravingsPromotionalVouchers voucher=PromotionalVouchersDao.findById(voucherId);
		if(voucher!=null) {
			voucher.setIsRedeemed(1);
			PromotionalVouchersDao.save(voucher);
		}
		
		
	}






	
}
