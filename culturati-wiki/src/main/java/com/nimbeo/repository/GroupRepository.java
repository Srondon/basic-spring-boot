package com.nimbeo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.nimbeo.entity.GroupEntity;

@Repository
public interface GroupRepository extends CrudRepository<GroupEntity, Long>{
	
}