package com.rolixtech.cravings.module.cravings.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rolixtech.cravings.module.cravings.model.CravingsPromotionalVouchers;
import com.rolixtech.cravings.module.order.model.CustomerOrder;
import com.rolixtech.cravings.module.resturant.model.CommonCategories;

@Repository
public interface CravingsPromotionalVouchersDao extends JpaRepository<CravingsPromotionalVouchers, Long>  {

	CravingsPromotionalVouchers findById(long id);

	boolean existsByPostFixStr(String postFix);

	boolean existsByCompleteString(String compeleteStr);

	List<CravingsPromotionalVouchers> findAllByPreFixStr(String prefixString);

	CravingsPromotionalVouchers findByCompleteString(String voucherCode);

	

	@Query(value="select * from cravings_promotional_vouchers where is_group=?1 and group_id in (select cpgv.id from cravings_promotional_group_vouchers cpgv where cpgv.group_title=?2)",nativeQuery = true)
	List<CravingsPromotionalVouchers> findAllByIsGroupAndGroupTitle(int i, String groupTitle);

	

	List<CravingsPromotionalVouchers> findAllByIsGroupAndCompleteString(int i, String keyWord);

	List<CravingsPromotionalVouchers> findAllByGroupId(long recordId);

	List<CravingsPromotionalVouchers> findAllByGroupIdAndIsRedeemed(long groupId,int isRedeemed);



	boolean existsByGroupId(long recordId);

	
}
