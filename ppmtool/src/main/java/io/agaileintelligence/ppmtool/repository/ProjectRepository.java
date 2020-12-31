package io.agaileintelligence.ppmtool.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.agaileintelligence.ppmtool.entity.Project;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {

	@Override
	Iterable<Project> findAllById(Iterable<Long> ids);
		
	@Override
	Iterable<Project> findAll();
	
	Iterable<Project> findAllByProjectLeader(String username);

	//	JPQ
	Project findByProjectIdentifier(String projectId);
	
	
	
	

}
