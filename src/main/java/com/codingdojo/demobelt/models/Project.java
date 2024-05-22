package com.codingdojo.demobelt.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="projects")
public class Project {
	
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    
	    @NotEmpty(message="Title is required!")
	    @Size(min=3, max=30, message="Title must be between 3 and 30 characters")
	    private String title;
	    
	    @NotEmpty(message="Description is required!")
	    @Size(min=10, message="Description must be at least 10 characters")
	    private String description;
	    
	    @DateTimeFormat(pattern="yyyy-MM-dd")
	    @NotNull(message="Due Date is required!")
	    @Future(message = "Due Date must be in the future")
	    private Date dueDate;
	    
	    

		@Column(updatable=false)
	    private Date createdAt;
	    private Date updatedAt;
	    
	    @PrePersist
	    protected void onCreate(){
	        this.createdAt = new Date();
	    }
	    @PreUpdate
	    protected void onUpdate(){
	        this.updatedAt = new Date();
	    } 
	    
	    
	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name="user_id")
	    private User leadUser;
	    
	    @ManyToMany(fetch = FetchType.LAZY)
	    @JoinTable(
	        name = "users_projects", 
	        joinColumns = @JoinColumn(name = "project_id"), 
	        inverseJoinColumns = @JoinColumn(name = "user_id")
	    )
	    private List<User> memberUsers;
	    
	    
	    
	    
	    public Project() {
	        
	    }
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public User getLeadUser() {
			return leadUser;
		}
		public void setLeadUser(User leadUser) {
			this.leadUser = leadUser;
		}
		public List<User> getMemberUsers() {
			return memberUsers;
		}
		public void setMemberUsers(List<User> memberUsers) {
			this.memberUsers = memberUsers;
		}
		
	    public Date getDueDate() {
			return dueDate;
		}
		public void setDueDate(Date dueDate) {
			this.dueDate = dueDate;
		}

		
	    
}
