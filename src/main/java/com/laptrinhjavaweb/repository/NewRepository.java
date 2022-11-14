package com.laptrinhjavaweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.laptrinhjavaweb.entity.NewEntity;

// repository extend from JpaRepository which has full API CrudRepository and PagingAndSortingRepository. 
//So, basically, Jpa Repository contains the APIs for basic CRUD operations, the APIS for pagination, and the APIs for sorting.

public interface NewRepository extends JpaRepository<NewEntity, Long>{
	
}