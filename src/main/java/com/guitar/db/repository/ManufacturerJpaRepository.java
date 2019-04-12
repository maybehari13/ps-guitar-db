/*
 * Copyright (C) 2019, Liberty Mutual Group
 *
 * Created on Mar 20, 2019
 */

package com.guitar.db.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.guitar.db.model.Manufacturer;

/**
 * @author n0172808
 *
 */
@Repository
public interface ManufacturerJpaRepository extends JpaRepository<Manufacturer, Long> {

	List<Manufacturer> findByFoundedDateBefore(Date date);
	
	List<Manufacturer> findByActiveTrue();
	
//	Named Native Query Example
	@Query(name = "Manufacturer.getAllThatSellAcoustics")
	List<Manufacturer> queryAllThatSellAcoustics(String name);
	
}
