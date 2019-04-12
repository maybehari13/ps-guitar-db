/*
 * Copyright (C) 2019, Liberty Mutual Group
 *
 * Created on Mar 20, 2019
 */

package com.guitar.db.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.guitar.db.model.Model;

/**
 * @author n0172808
 *
 */
@Repository
public interface ModelJpaRepository extends JpaRepository<Model, Long> {
	List<Model> findByPriceBetween(BigDecimal a, BigDecimal b);
	
	List<Model> findByPriceGreaterThanEqualAndPriceLessThanEqual(BigDecimal a, BigDecimal b);
	
//	2 Ways of comparing collections
//	List<Model> findByModelTypeIn(List<ModelType> types);
	List<Model> findByModelTypeNameIn(List<String> types);
	
//	Paging example - to format output for specific needs
	@Query("select m from Model m where m.price >= :lowest and m.price <= :highest and m.woodType like :wood")
	Page<Model> queryByPriceRangeAndWoodType(@Param("lowest") BigDecimal low, 
											 @Param("highest") BigDecimal high,
											 @Param("wood") String wood,
											 Pageable page);

//	Named Query Example - two ways
//	List<Model> findAllModelsByType(@Param("name") String name);
	@Query(name="Model.findAllModelsByType")
	List<Model> queryAllModelsByType(@Param("name") String name);
	
}
