package com.rolixtech.cravings.module.resturant.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rolixtech.cravings.module.resturant.model.CommonCategories;

@Repository
public interface CommonCategoriesDao extends JpaRepository<CommonCategories, Long>  {

	CommonCategories findById(long id);

}
