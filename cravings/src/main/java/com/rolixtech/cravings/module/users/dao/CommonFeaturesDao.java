package com.rolixtech.cravings.module.users.dao;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.stereotype.Repository;

import com.rolixtech.cravings.module.users.models.CommonFeatures;
import com.rolixtech.cravings.module.users.models.CommonRole;
import com.rolixtech.cravings.module.users.models.CommonUsers;


@Repository
public interface CommonFeaturesDao extends JpaRepository<CommonFeatures, Long>  {

	CommonFeatures findById(long Id);

	List<CommonFeatures> findAllByGroupId(long id);

	@Query(value="SELECT cf.id,cf.label,cf.url  FROM common_features cf join common_users_features cuf on cuf.feature_id=cf.id where cf.group_id=?1 and cuf.user_id=?2 order by cf.id",nativeQuery = true)
	List<CustomCommonFeatures> findAllByGroupIdAndUserId(long grouid,long userId);

	public interface CustomCommonFeatures {
		public Long getId();
		public String  getLabel();
		public String  getUrl();
	}

}
