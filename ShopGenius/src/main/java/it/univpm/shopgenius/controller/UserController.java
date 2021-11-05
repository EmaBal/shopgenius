package it.univpm.shopgenius.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.univpm.shopgenius.model.dao.RoleDAO;
import it.univpm.shopgenius.model.entities.Role;
import it.univpm.shopgenius.model.entities.User;
import it.univpm.shopgenius.services.UserService;
import it.univpm.shopgenius.utils.Utilities;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private RoleDAO roleDAO; 
    
    private Utilities utilities = new Utilities();

    @GetMapping("/list")
    public String listCustomers(Model model) {
        List <User> users = userService.getUsers();
        model.addAttribute("users", users);
        
    	model.addAttribute("role",utilities.getCurrentUserMajorRole());
		try {
	    	model.addAttribute("current_firstName", userService.findUserByEmail(utilities.getCurrentUserName()).getFirstName());
	    	model.addAttribute("current_lastName", userService.findUserByEmail(utilities.getCurrentUserName()).getLastName());
			} catch (Exception e) {
		    	model.addAttribute("current_firstName", "anonymous");
		    	model.addAttribute("current_lastName", "anonymous");
			}
        return "tiles_list_users";
    }

    @GetMapping("/showForm")
    public String showFormForAdd(@RequestParam(value = "error", required = false) String error, Model model) {
    	model.addAttribute("role",utilities.getCurrentUserMajorRole());
		try {
	    	model.addAttribute("current_firstName", userService.findUserByEmail(utilities.getCurrentUserName()).getFirstName());
	    	model.addAttribute("current_lastName", userService.findUserByEmail(utilities.getCurrentUserName()).getLastName());
			} catch (Exception e) {
		    	model.addAttribute("current_firstName", "anonymous");
		    	model.addAttribute("current_lastName", "anonymous");
			}
    	
		User user = new User();
		model.addAttribute("user", user);
		model.addAttribute("error", error);
		model.addAttribute("update_role", null);
		List<String> roleNamesList = new ArrayList<String>();
		for (Role role: roleDAO.getRoles()) {
			roleNamesList.add(role.getName());
		}
		model.addAttribute("roleNamesList", roleNamesList);
        return "tiles_register";
    }

    @PostMapping("/saveUser")
    public String saveUser(@Valid @ModelAttribute("user") User user, BindingResult br, 
    		@RequestParam(value = "makeAdmin", required = false) boolean makeAdmin, 
    		@RequestParam(value = "makeEmp", required = false) boolean makeEmp, 
    		@RequestParam(value = "updateRole", required = false, defaultValue="") String updateRole,
    		@RequestParam(value = "oldUserEmail", required=false) String oldUserEmail,
    		@RequestParam("roleName") String roleName,
    		Model model) {
    	if (br.hasErrors()) {
        	model.addAttribute("role",utilities.getCurrentUserMajorRole());
    		try {
    	    	model.addAttribute("current_firstName", userService.findUserByEmail(utilities.getCurrentUserName()).getFirstName());
    	    	model.addAttribute("current_lastName", userService.findUserByEmail(utilities.getCurrentUserName()).getLastName());
    			} catch (Exception e) {
    		    	model.addAttribute("current_firstName", "anonymous");
    		    	model.addAttribute("current_lastName", "anonymous");
    			}
    		return "tiles_register";
    	} else {
	    	if (updateRole != null && !updateRole.equals("") ) {
	    		if (oldUserEmail.equals(user.getEmail())) {
	    			user.setRoles(userService.getUser(user.getId()).getRoles());
	    			user.setEnabled(true);
		    		if (updateRole.equals("admin") && roleName.equals("employee")) {
			    		userService.removeRole(user, "admin");
		    		} else if (updateRole.equals("admin") && roleName.equals("user")) {
			    		userService.removeRole(user, "admin");
			    		userService.removeRole(user, "employee");
		    		} else if (updateRole.equals("employee") && roleName.equals("user")) {
			    		userService.removeRole(user, "employee");
		    		} else if (updateRole.equals("employee") && roleName.equals("admin")) {
			    		userService.addRole(user, "admin");
		    		} else if (updateRole.equals("user") && roleName.equals("admin")) {
		    			userService.addRole(user, "employee");
			    		userService.addRole(user, "admin");
		    		} else if (updateRole.equals("user") && roleName.equals("employee")) {
		    			userService.addRole(user, "employee");
		    		}
		    		userService.update(user);
	    		} else {
		    		try {
		    			userService.findUserByEmail(user.getEmail());
		    			model.addAttribute("error", "Email already used");
		    			model.addAttribute("userId", user.getId());
		    			return "redirect:/user/updateForm";
		    		} catch (Exception e) {
		    			userService.saveUser(user);
			    		userService.update(user);
		    		}
	    		}
	    	} else {
	    		try {
	    			userService.findUserByEmail(user.getEmail());
	    			model.addAttribute("error", "Email already used");
	    			return "redirect:/user/showForm";
	    		} catch (Exception e) {
	    			userService.saveUser(user);
		    		userService.update(user);
	    			if (roleName.equals("employee") || roleName.equals("admin")) {
	    				userService.addRole(user, "employee");
	    			}
	    			if (roleName.equals("admin")) {
	    				userService.addRole(user, "admin");
	    			}
//	    			userService.saveUser(user);
		    		userService.update(user);
	    		}
	    	}
	        return "redirect:/";
    	}
    }

    @GetMapping("/updateForm")
    public String showFormForUpdate(@RequestParam("userId") int id, Model model) {
        User user = userService.getUser(id);
        model.addAttribute("user", user);
        List<String> currentUserRoleNamesList = new ArrayList<String>();
        for (Role role: user.getRoles()) {
        	currentUserRoleNamesList.add(role.getName());
        }
    	if (currentUserRoleNamesList.contains("admin")) {
    		model.addAttribute("update_role", "admin");
    	} else if (currentUserRoleNamesList.contains("employee")) {
    		model.addAttribute("update_role", "employee");
    	} else if (currentUserRoleNamesList.contains("user")) {
    		model.addAttribute("update_role", "user");
    	}
    	model.addAttribute("role",utilities.getCurrentUserMajorRole());
		try {
	    	model.addAttribute("current_firstName", userService.findUserByEmail(utilities.getCurrentUserName()).getFirstName());
	    	model.addAttribute("current_lastName", userService.findUserByEmail(utilities.getCurrentUserName()).getLastName());
		} catch (Exception e) {
	    	model.addAttribute("current_firstName", "anonymous");
	    	model.addAttribute("current_lastName", "anonymous");
		}
		model.addAttribute("roleNamesList", currentUserRoleNamesList);
		List<String> roleNamesList = new ArrayList<String>();
		for (Role role: roleDAO.getRoles()) {
			roleNamesList.add(role.getName());
		}
		model.addAttribute("roleNamesList", roleNamesList);
        return "tiles_register";
    }

    @GetMapping("/delete")
    public String deleteUser(@RequestParam("userId") int id) {
    	userService.deleteUser(id);
        return "redirect:/user/list";
    }
    
    @GetMapping("details")
    public String userDetails(Model model) {
    	model.addAttribute("user", userService.findUserByEmail(utilities.getCurrentUserName()));
    	model.addAttribute("role",utilities.getCurrentUserMajorRole());
		try {
	    	model.addAttribute("current_firstName", userService.findUserByEmail(utilities.getCurrentUserName()).getFirstName());
	    	model.addAttribute("current_lastName", userService.findUserByEmail(utilities.getCurrentUserName()).getLastName());
		} catch (Exception e) {
	    	model.addAttribute("current_firstName", "anonymous");
	    	model.addAttribute("current_lastName", "anonymous");
		}
    	return "tiles_user_details";
    }
}
