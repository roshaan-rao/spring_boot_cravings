package com.rolixtech.cravings.module.resturant.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rolixtech.cravings.module.resturant.model.CommonUsersFavProducts;
import com.rolixtech.cravings.module.resturant.model.CommonUsersResturants;

@Repository
public interface CommonUsersFavProductsDao extends JpaRepository<CommonUsersFavProducts, Long>  {

	List<CommonUsersFavProducts> findAllByUserId(long userId);

	boolean existsByUserId(long resturantId);

	void deleteAllByUserId(long resturantId);

	boolean existsByUserIdAndProductId(long productId, long userId);

	void deleteByUserIdAndProductId(long productId, long userId);

	

}
