package com.codingdojo.demobelt.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codingdojo.demobelt.models.Project;
import com.codingdojo.demobelt.models.User;
import com.codingdojo.demobelt.repositories.ProjectRepository;

@Service
public class ProjectService {
	@Autowired
    private ProjectRepository projectRepository;
	
	 // returns all the projects
    public List<Project> allProjects() {
        return (List<Project>) projectRepository.findAll();
    }
    
    
    // creates a project
    public Project createProject(Project b) {
        return projectRepository.save(b);
    }
    
    
    // retrieves a project
    public Project findProject(Long id) {
        Optional<Project> optionalProject = projectRepository.findById(id);
        if(optionalProject.isPresent()) {
            return optionalProject.get();
        } else {
            return null;
        }
    }
    
    
    //update a project 
    public Project updateProject(Project b) {
        return projectRepository.save(b);
    }
    
    
    
    // delete a project
    public void deleteProject(Long id) {
        Optional<Project> optionalProject = projectRepository.findById(id);
        if(optionalProject.isPresent()) {
        	projectRepository.deleteById(id);
        } 
    }
    
  //retrieves projects for a user
    public  List<Project> findProjects(User u) {
        return projectRepository.findAllByLeadUserOrMemberUsers(u,u);
    }
    //retrieves projects that do not belong to a user
    public  List<Project> findNonProjects(User u) {
        return projectRepository.findByLeadUserNotAndMemberUsersNotContains(u,u);
    }

}
