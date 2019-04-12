/*
 * Copyright (C) 2019, Liberty Mutual Group
 *
 * Created on Mar 19, 2019
 */

package com.guitar.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.guitar.db.model.Location;

/**
 * @author n0172808
 *
 */
@Repository
public interface LocationJpaRepository extends JpaRepository<Location, Long> {
	
	List<Location> findByStateIgnoreCaseLike(String state);
	
	List<Location> findByStateLikeOrId(String state, Long id);
	
	List<Location> findByStateNotAndCountryEquals(String state, String country);
	
	List<Location> findByStateContaining(String state);
	
	List<Location> findByStateIgnoreCaseStartingWithOrderByIdDesc(String state);
	
	List<Location> findDistinctCountryByStateContaining(String state);
	
}
