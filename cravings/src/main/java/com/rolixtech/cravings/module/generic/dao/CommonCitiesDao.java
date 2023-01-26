package com.rolixtech.cravings.module.generic.dao;

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

import com.rolixtech.cravings.module.generic.model.CommonCities;
import com.rolixtech.cravings.module.generic.model.CommonCountries;

@Repository
public interface CommonCitiesDao extends JpaRepository<CommonCities, Long>  {

	CommonCities findById(long id);

	List<CommonCities> findAllByProvinceId(long countryId);

}
