package com.rolixtech.cravings.module.generic.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rolixtech.cravings.module.generic.dao.CommonSmsLogDao;
import com.rolixtech.cravings.module.generic.model.CommonSmsLog;



@Service
public class CommonSmsLogService {
	
	@Autowired	
	private CommonSmsLogDao Dao;
	
	

	@Transactional
	public void saveLog(long logTypeId,long userId,String sentTo,String transactionId,String responseCode) throws DataAccessException, Exception {
		
		
		CommonSmsLog smsLog=new CommonSmsLog();
		smsLog.setSmsLogTypeId(logTypeId);
		smsLog.setOtp(userId);
		smsLog.setSentTo(sentTo);
		smsLog.setSentOn(new Date());
		smsLog.setTransactionId(Integer.parseInt(transactionId));
		smsLog.setResponseCode(responseCode);
		Dao.save(smsLog);
	}

	
	


}
