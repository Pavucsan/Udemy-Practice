package io.agaileintelligence.ppmtool.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.agaileintelligence.ppmtool.entity.Backlog;
import io.agaileintelligence.ppmtool.entity.Project;
import io.agaileintelligence.ppmtool.entity.User;
import io.agaileintelligence.ppmtool.exception.ProjectIdException;
import io.agaileintelligence.ppmtool.exception.ProjectNotFoundException;
import io.agaileintelligence.ppmtool.repository.BacklogRepository;
import io.agaileintelligence.ppmtool.repository.ProjectRepository;
import io.agaileintelligence.ppmtool.repository.UserRepository;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private BacklogRepository backlogRepository;

	@Autowired
	private UserRepository userRepository;

	public Project saveOrUpdateProject(Project project, String username) {
		try {

			if (project.getId() != null) {
				Project existingProject = projectRepository.findByProjectIdentifier(project.getProjectIdentifier());
				if (existingProject != null && (!existingProject.getProjectLeader().equals(username))) {
					throw new ProjectNotFoundException("Project not found in your account");
				} else if (existingProject == null) {
					throw new ProjectNotFoundException("Project with ID: '" + project.getProjectIdentifier()
							+ "' cannot be updated because it doesn't exist");
				}
			}

			String projectIdentifiler = project.getProjectIdentifier().toUpperCase();

			User user = userRepository.findByUsername(username);

			project.setUser(user);
			project.setProjectLeader(user.getUsername());
			project.setProjectIdentifier(projectIdentifiler);

			if (project.getId() == null) {
				Backlog backlog = new Backlog();
				project.setBacklog(backlog);
				backlog.setProject(project);
				backlog.setProjectIdentifier(projectIdentifiler);
			}

			if (project.getId() != null) {
				project.setBacklog(backlogRepository.findByProjectIdentifier(projectIdentifiler));
			}

			return projectRepository.save(project);
		} catch (Exception e) {
			throw new ProjectIdException(
					"Project ID '" + project.getProjectIdentifier().toUpperCase() + "' already exists");
		}

	}

	public Project findProjectByIdentifier(String projectId, String username) {

		Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

		if (project == null) {
			throw new ProjectIdException("Project ID '" + projectId + "' does not exist");
		}
		if (!project.getProjectLeader().equals(username)) {
			throw new ProjectNotFoundException("Project not found in your account");
		}
		return project;
	}

	public Iterable<Project> findAllProjects(String username) {
		return projectRepository.findAllByProjectLeader(username);
	}

	public void deleteProjectByIdentifier(String projectid, String username) {
		Project project = projectRepository.findByProjectIdentifier(projectid.toUpperCase());

		if (project == null) {
			throw new ProjectIdException("Cannot Project with ID '" + projectid + "'. This project does not exist");
		}

		projectRepository.delete(findProjectByIdentifier(projectid, username));
	}

}