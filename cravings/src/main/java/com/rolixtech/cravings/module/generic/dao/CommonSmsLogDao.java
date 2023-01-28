package com.rolixtech.cravings.module.generic.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rolixtech.cravings.module.generic.model.CommonSmsLog;



public interface CommonSmsLogDao extends JpaRepository<CommonSmsLog, Long> {
	CommonSmsLog findById(long Id);
	
}
