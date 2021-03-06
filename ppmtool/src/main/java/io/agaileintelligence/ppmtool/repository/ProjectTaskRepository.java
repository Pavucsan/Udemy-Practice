package io.agaileintelligence.ppmtool.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.agaileintelligence.ppmtool.entity.ProjectTask;

@Repository
public interface ProjectTaskRepository extends CrudRepository<ProjectTask, Long> {

	List<ProjectTask> findByProjectIdentifierOrderByPriority(String id);

	ProjectTask findByProjectSequence(String sequence);
}