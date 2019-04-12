/*
 * Copyright (C) 2019, Liberty Mutual Group
 *
 * Created on Mar 19, 2019
 */

package com.guitar.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.guitar.db.model.ModelType;

/**
 * @author n0172808
 *
 */
@Repository
public interface ModelTypeJpaRepository extends JpaRepository<ModelType, Long> {
	List<ModelType> findByNameIsNull();
	List<ModelType> findByNameIsNotNull();
}
