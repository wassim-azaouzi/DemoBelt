package com.codingdojo.demobelt.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.codingdojo.demobelt.models.Project;
import com.codingdojo.demobelt.models.User;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {
	// Retrieves a list of all projects for a particular user
    List<Project> findAllByLeadUserOrMemberUsers(User leadUser, User memberUsers);
    
    // Retrieves a list of any projects a particular user
    // does not belong to.
    List<Project> findByLeadUserNotAndMemberUsersNotContains(User leaderUser, User memberUsers);
}
