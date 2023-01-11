package com.rolixtech.cravings.module.resturant.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rolixtech.cravings.module.resturant.model.CommonUsersResturants;

@Repository
public interface CommonUsersResturantsDao extends JpaRepository<CommonUsersResturants, Long>  {

	List<CommonUsersResturants> findAllByResturantId(long resturantId);

	boolean existsByResturantId(long resturantId);

	void deleteAllByResturantId(long resturantId);

	CommonUsersResturants findByUserId(long id);

	
	

}
