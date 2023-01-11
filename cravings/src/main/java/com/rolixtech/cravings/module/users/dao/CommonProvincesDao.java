package com.rolixtech.cravings.module.users.dao;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.stereotype.Repository;

import com.rolixtech.cravings.module.generic.model.CommonProvinces;

@Repository
public interface CommonProvincesDao extends JpaRepository<CommonProvinces, Long>  {

	CommonProvinces findById(long id);

	

	List<CommonProvinces> findAllByCountryId(long countryId);

}
