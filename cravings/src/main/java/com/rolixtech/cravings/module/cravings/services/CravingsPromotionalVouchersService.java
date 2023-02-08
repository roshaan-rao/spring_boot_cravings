package com.rolixtech.cravings.module.cravings.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rolixtech.cravings.module.cravings.dao.CravingsPromotionalVouchersChangeLogDao;
import com.rolixtech.cravings.module.cravings.dao.CravingsPromotionalVouchersDao;
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
	private GenericUtility utility;
	
	public void savePromtionalVouchers(String prefixStr,long userId,int totalNumber,double amount,double percetageVal,Date validFrom,Date validTo,int isPercentage ) {
		
		if(totalNumber>0) {
			List<String> alreadyCreatedPromos=new ArrayList<>();
			for(int i=1;i<totalNumber;i++) {
				CravingsPromotionalVouchers PromotionalVouchers=new CravingsPromotionalVouchers();
				
				
				
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
						
						PromotionalVouchers.setCompleteString(uniqueVoucherCode);
						PromotionalVouchers.setCreatedBy(userId);
						PromotionalVouchers.setCreatedOn(new Date());
						PromotionalVouchers.setStatus(1);
						PromotionalVouchers.setPostFixStr(postFix);
						PromotionalVouchers.setPreFixStr(prefixStr);
						PromotionalVouchers.setIsPercentage(isPercentage);
						if(isPercentage==1) {
							PromotionalVouchers.setPercentageVal(percetageVal);
						}else {
							PromotionalVouchers.setAmount(amount);
						}
						
						
						PromotionalVouchers.setValidFrom(validFrom);
						PromotionalVouchers.setValidTo(validTo);
						PromotionalVouchersDao.save(PromotionalVouchers);
						
						alreadyCreatedPromos.add(uniqueVoucherCode);
					
					}
				
				
			}	
		
			
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
						if(utility.parseInt(voucher.getIsPercentage())==1) {
							Row.put("percentageVal",voucher.getPercentageVal());
							Row.put("amount",0.0);
							
						}else {
							Row.put("amount",voucher.getAmount());
							Row.put("percentageVal",0.0);
							
						}
						
						Row.put("isRedeemed", voucher.getIsRedeemed());
						Row.put("statusId", voucher.getStatus());
						Row.put("statusLabel", VouchersStatusTypesService.findLabelById(voucher.getStatus()));
						Row.put("createdOn",voucher.getCreatedOn());
						Row.put("createdBy",voucher.getCreatedBy());
					
						
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
						if(utility.parseInt(voucher.getIsPercentage())==1) {
							Row.put("percentageVal",voucher.getPercentageVal());
							Row.put("amount",0.0);
							
						}else {
							Row.put("amount",voucher.getAmount());
							Row.put("percentageVal",0.0);
							
						}
						
						list.add(Row);
				}
				
			);
		}
		
		return list;
	}





	public void ChangeStatus(long recordId, int status, long userId) {
		// TODO Auto-generated method stub
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
	}






	public void EditSave(long recordId, String prefixStr, Double amount, Double percentageVal,Date validFrom,Date validTo,int isPercentage ,long userId) {
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
			
			
					
			PromotionalVouchers.setCompleteString(uniqueVoucherCode);
			PromotionalVouchers.setCreatedBy(userId);
			PromotionalVouchers.setCreatedOn(new Date());
			PromotionalVouchers.setStatus(1);
			PromotionalVouchers.setPostFixStr(postFix);
			PromotionalVouchers.setPreFixStr(prefixStr);
			PromotionalVouchers.setIsPercentage(isPercentage);
			if(isPercentage==1) {
				PromotionalVouchers.setPercentageVal(percentageVal);
			}else {
				PromotionalVouchers.setAmount(amount);
			}
			
			PromotionalVouchers.setValidFrom(validFrom);
			PromotionalVouchers.setValidTo(validTo);
			PromotionalVouchersDao.save(PromotionalVouchers);
				
			
		}
		
		
		
	}
	
	
	@Transactional
	public void deleteVoucher(long recordId, long userId) {
		// TODO Auto-generated method stub
		CravingsPromotionalVouchersChangeLog PromotionalVouchersLog=new CravingsPromotionalVouchersChangeLog();
		CravingsPromotionalVouchers iRealChange=PromotionalVouchersDao.findById(recordId);
				BeanUtils.copyProperties(iRealChange, PromotionalVouchersLog);
				
				PromotionalVouchersLog.setId(0);
				PromotionalVouchersLog.setRecordId(recordId);
				PromotionalVouchersLog.setLogCreatedBy(userId);
				PromotionalVouchersLog.setLogCreatedOn(new Date());
				PromotionalVouchersLog.setLogReason("Voucher Delete");
				PromotionalVouchersLog.setLogTypeId(utility.parseLong("1"));
				VouchersChangeLogDao.save(PromotionalVouchersLog);
				
				
				PromotionalVouchersDao.deleteById(recordId);
	}





	public List<Map> validateVoucher(String voucherCode) throws Exception {
		List<Map> list=new ArrayList<>();
		CravingsPromotionalVouchers voucher=PromotionalVouchersDao.findByCompleteString(voucherCode);
		if(voucher!=null) {
			
			
			if(voucher.getIsRedeemed()==1) {
				throw new Exception("Voucher has already been used.");
			}else {
				System.out.println(utility.removeTime(new Date()));
				System.out.println(utility.getDateInyyyyMMddFromDate(new Date()));
				System.out.println("VALID FROM"+utility.removeTime(voucher.getValidFrom()));
				System.out.println("VALID To"+utility.removeTime(voucher.getValidTo()));
				
				
				if(DateUtils.addDays(new Date(), 0).after(DateUtils.addDays(voucher.getValidFrom(), 0)) && DateUtils.addDays(voucher.getValidTo(), 0).before(DateUtils.addDays(new Date(), 0))) {
					Map Row=new HashMap<>();
					Row.put("voucherId", voucher.getId());
					
					if(voucher.getIsPercentage()==1) { 
						Row.put("percentageVal",voucher.getPercentageVal());
						
					}else {
						Row.put("amountVal",voucher.getAmount());
					}
					
					list.add(Row);
				}else {
					
					throw new Exception("Voucher has been expired.");
					
				}
			} 
			
			
			
			
		}else{
			throw new Exception("Voucher is not valid.");
		}
		return list;
	}






	
}
