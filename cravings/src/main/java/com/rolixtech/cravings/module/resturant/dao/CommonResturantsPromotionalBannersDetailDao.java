package com.rolixtech.cravings.module.resturant.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rolixtech.cravings.module.resturant.model.CommonResturantsPromotionalBannersDetail;
import com.rolixtech.cravings.module.resturant.model.CommonUsersResturants;

@Repository
public interface CommonResturantsPromotionalBannersDetailDao extends JpaRepository<CommonResturantsPromotionalBannersDetail, Long>  {

	CommonResturantsPromotionalBannersDetail findById(long id);
	
	List<CommonResturantsPromotionalBannersDetail> findByPromotionalBannerId(long id);

	CommonResturantsPromotionalBannersDetail deleteByPromotionalBannerId(long promotionalBannerId);

	@Query(value="SELECT distinct resturant_id FROM common_resturants_promotional_banners_detail order by resturant_id desc",nativeQuery = true)
	List<Long> findDistinctResturantId();

	@Query(value="SELECT pb.id as pbId,pb_detail.id as pbDetailId, pb.start_date as validFrom ,pb.end_date as validTo ,pb.is_active as isActive,pb_detail.image_url as urlImg FROM common_resturants_promotional_banners pb join common_resturants_promotional_banners_detail pb_detail on pb.id=pb_detail.promotional_banner_id and pb_detail.resturant_id=?1",nativeQuery = true)
	List<customPromotionalBanner> findAllByResturantId(Long resturantId);
	
	@Query(value="SELECT pb.id as pbId,pb_detail.id as pbDetailId, pb.start_date as validFrom ,pb.end_date as validTo ,pb.is_active as isActive,pb_detail.image_url as urlImg FROM common_resturants_promotional_banners pb join common_resturants_promotional_banners_detail pb_detail on pb.id=pb_detail.promotional_banner_id and pb_detail.id=?1",nativeQuery = true)
	List<customPromotionalBanner> findAllByRecordId(Long recordId);
	
	public interface customPromotionalBanner{
		public Long getPbId();
		public Long getPbDetailId();
		public Date getValidFrom();
		public Date getValidTo();
		public Integer getisActive();
		public String getUrlImg();
		
	}

}
