package me.nothing.login_.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import me.nothing.login_.model.Role;
import me.nothing.login_.model.Staff;
import me.nothing.login_.model._StaffDetails;
import me.nothing.login_.service.LeaveService;
import me.nothing.login_.service.StaffService;

@Controller
public class LoginController {

	@Autowired
	StaffService staffService;

	@Autowired
	LeaveService leaveService;
	
	@RequestMapping("")
	public String index() {
		return "redirect:login";
	}

	@RequestMapping("/login")
	public String login(String username, HttpSession session) {
		// if(bindingResult.hasErrors()){
		// 	return "login";
		// }
		// Staff staff = staffService.getUserbyUsername(username);	
		
		// _StaffDetails usersession = new _StaffDetails();
		// usersession.setStaff(staff);
		// session.setAttribute("usession", usersession);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			return "login";
		}
		
		_StaffDetails staffDetails = (_StaffDetails) authentication.getPrincipal();

		System.out.println("this is " + authentication);
		System.out.println("this is " + staffDetails.hasRole("admin"));
		// List<Staff> surbodinates = leaveService.getSubordinate(staffDetails.getStaff().getStfId());
		// staffDetails.setSubordinates(surbodinates);
		// session.setAttribute("usersession", staffDetails);
		if (staffDetails.hasRole("staff")) {
			return "redirect:/staff";
		} else if (staffDetails.hasRole("manager")) {
			return "redirect:/manager";
		} else if (staffDetails.hasRole("admin")) {
			return "redirect:/admin";
		} else {
			return "redirect:/staff";
		}

	}

	@GetMapping("/admin")
	public String admin() {
		return "admin/home";
	}

	@GetMapping("/staff")
	public String staff(HttpSession session, Model model) {
		// _StaffDetails staff = (_StaffDetails) session.getAttribute("usession");
		// System.out.println(staff.getStaff());
		return "staff/home";
	}

	@GetMapping("/manager")
	public String manager() {
		return "manager/home";
	}

}
