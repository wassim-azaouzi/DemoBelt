package com.codingdojo.demobelt.controllers;

import java.awt.print.Book;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.codingdojo.demobelt.models.LoginUser;
import com.codingdojo.demobelt.models.Project;
import com.codingdojo.demobelt.models.User;
import com.codingdojo.demobelt.services.ProjectService;
import com.codingdojo.demobelt.services.UserService;

// .. don't forget to include all your imports! ..
    
@Controller
public class HomeController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private ProjectService projectService;
   
    @GetMapping("/")
    public String index( @ModelAttribute("newUser") User newUser, 
    		@ModelAttribute("newLogin") LoginUser newLogin) {
   
              return "index.jsp";
    }
    
    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("newUser") User newUser,
            BindingResult result, Model model, HttpSession session) {

        // Call the register method in the service for extra validations and user creation
        User registeredUser = userService.register(newUser, result);

        if (result.hasErrors()) {
            // Be sure to send in the empty LoginUser before re-rendering the page.
            model.addAttribute("newLogin", new LoginUser());
            return "index.jsp";
        }

        // No errors!
        // Store the user's ID from the DB in the session to log them in.
        session.setAttribute("userId", registeredUser.getId());

        return "redirect:/dashboard";
    }
    
    
    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("newLogin") LoginUser newLogin,
                        BindingResult result, Model model, HttpSession session) {
    	
         User user = userService.login(newLogin, result);

        if (result.hasErrors()) {
            model.addAttribute("newUser", new User());
            return "index.jsp";
        }

        // No errors!
        // TO-DO Later: Store their ID from the DB in session,
        // in other words, log them in.
        session.setAttribute("userId", user.getId());


        return "redirect:/dashboard";
    }
    
    @GetMapping("/dashboard")
    public String welcome(HttpSession session, Model model) {
        // Check if the user is logged in by verifying if the userId attribute exists in the session
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            // User is not logged in, redirect to the login page
            return "redirect:/";
        }

        // User is logged in, retrieve the user object from the session or database
        User user = userService.findUser(userId);
        if (user == null) {
            // User not found, redirect to the login page
            return "redirect:/";
        }
        
        List<Project> myProjects= projectService.findProjects(user);
        List<Project> otherProjects= projectService.findNonProjects(user);
        // User is authenticated, pass the user object to the view for display
        model.addAttribute("user", user);
        model.addAttribute("myProjects", myProjects);
        model.addAttribute("otherProjects", otherProjects);



        return "welcome.jsp";
    }
    
    @PostMapping("/logout")
    public String logout(HttpSession session) {
        // Clear the session
        session.invalidate();
        return "redirect:/";
    }
    
    
    @GetMapping("/projects/new")
    public String newProject( @ModelAttribute("newProject") Project newProject, Model model, HttpSession session) {
    	
    	// Check if the user is logged in by verifying if the userId attribute exists in the session
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            // User is not logged in, redirect to the login page
            return "redirect:/";
        }

        // User is logged in, retrieve the user object from the session or database
        User user = userService.findUser(userId);
        if (user == null) {
            // User not found, redirect to the login page
            return "redirect:/";
        }

        // User is authenticated, pass the user object to the view for display
        model.addAttribute("user", user);

        return "newProject.jsp";
    }
    
    
    
    @PostMapping("/projects/new")
    public String saveProject( @Valid @ModelAttribute("newProject") Project newProject, BindingResult result) {
    	
    	if (result.hasErrors()) {
            return "newProject.jsp";
        } 
    	
    	projectService.createProject(newProject);
		return "redirect:/dashboard";

    }
    
    @GetMapping("/projects/{id}")
    public String showProject( @PathVariable("id") Long projectId, Model model,  HttpSession session) {
    	// Check if the user is logged in by verifying if the userId attribute exists in the session
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            // User is not logged in, redirect to the login page
            return "redirect:/";
        }

        // User is logged in, retrieve the user object from the session or database
        User user = userService.findUser(userId);
        if (user == null) {
            // User not found, redirect to the login page
            return "redirect:/";
        }

        Project project = projectService.findProject(projectId);

        // User is authenticated, pass the user object to the view for display
        model.addAttribute("user", user);
        model.addAttribute("project" , project);
        return "showProject.jsp";
    }
    
    @GetMapping("/projects/{id}/edit")
    public String editProject( @PathVariable("id") Long id, Model model, HttpSession session) {
    	
    	// Check if the user is logged in by verifying if the userId attribute exists in the session
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            // User is not logged in, redirect to the login page
            return "redirect:/";
        }

        // User is logged in, retrieve the user object from the session or database
        User user = userService.findUser(userId);
        if (user == null) {
            // User not found, redirect to the login page
            return "redirect:/";
        }
        
        
        Project project = projectService.findProject(id);
        // User is the same user who posted the book to be edited
        if (!user.equals(project.getLeadUser())) {
            // not the user who posted the book
            return "redirect:/dashboard";
        }
        
        
        model.addAttribute("project", project);
        // User is authenticated, pass the user object to the view for display
        model.addAttribute("user", user);

        return "edit.jsp";
    }
    
    
    @RequestMapping(value="/projects/{id}/edit", method=RequestMethod.PUT)
    public String update(@Valid @ModelAttribute("project") Project project, BindingResult result) {
		System.out.println("updating....");

        if (result.hasErrors()) {
    		System.out.println("failure");

            return "edit.jsp";
        } else {
        	projectService.updateProject(project);
        		System.out.println("success");
    		return "redirect:/dashboard";
        }
    }
    
    @GetMapping("/projects/{id}/join")
    public String joinProject( @PathVariable("id") Long id, Model model, HttpSession session) {
    	
    	// Check if the user is logged in by verifying if the userId attribute exists in the session
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            // User is not logged in, redirect to the login page
            return "redirect:/";
        }

        // User is logged in, retrieve the user object from the session or database
        User user = userService.findUser(userId);
        if (user == null) {
            // User not found, redirect to the login page
            return "redirect:/";
        }
        
        
        Project project = projectService.findProject(id);
        // User should not be the same user who posted the project
        if (user.equals(project.getLeadUser())) {
            // not the user who posted the book
            return "redirect:/dashboard";
        }
        
        project.getMemberUsers().add(user);
        
        projectService.updateProject(project);


        return "redirect:/dashboard";  
     }
    
    
    
    @GetMapping("/projects/{id}/leave")
    public String leaveProject( @PathVariable("id") Long id, Model model, HttpSession session) {
    	
    	// Check if the user is logged in by verifying if the userId attribute exists in the session
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            // User is not logged in, redirect to the login page
            return "redirect:/";
        }

        // User is logged in, retrieve the user object from the session or database
        User user = userService.findUser(userId);
        if (user == null) {
            // User not found, redirect to the login page
            return "redirect:/";
        }
        
        
        Project project = projectService.findProject(id);
        // User should not be the same user who posted the project
        if (user.equals(project.getLeadUser())) {
            // not the user who posted the book
            return "redirect:/dashboard";
        }
        
        // Remove the user from the list of member users in the project
        project.getMemberUsers().remove(user);

        // Save the updated project back to the database
        projectService.updateProject(project);


        return "redirect:/dashboard";  
     }
    
    
    
//    
//    @RequestMapping(value="/books/{id}/delete", method=RequestMethod.DELETE)
//    public String destroy(@PathVariable("id") Long id) {
//    	
//    	bookService.deleteBook(id);
//        return "redirect:/welcome";
//    }
//    
//    
//    
//
}
    

