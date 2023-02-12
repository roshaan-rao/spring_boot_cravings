package com.rolixtech.cravings.module.cravings.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rolixtech.cravings.module.cravings.model.CravingsPromotionalVouchers;
import com.rolixtech.cravings.module.cravings.model.CravingsPromotionalVouchersStatusTypes;
import com.rolixtech.cravings.module.cravings.model.CravingsPromotionalVouchersValueTypes;
import com.rolixtech.cravings.module.order.model.CustomerOrder;
import com.rolixtech.cravings.module.resturant.model.CommonCategories;

@Repository
public interface CravingsPromotionalVouchersValueTypesDao extends JpaRepository<CravingsPromotionalVouchersValueTypes, Long>  {

	CravingsPromotionalVouchersValueTypes findById(long id);



	
}
