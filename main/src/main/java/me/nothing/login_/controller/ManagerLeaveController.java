package me.nothing.login_.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import me.nothing.login_.model._StaffDetails;
import me.nothing.login_.service.ExtraHourService;
import me.nothing.login_.service.LeaveService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import me.nothing.login_.model.Staff;
import me.nothing.login_.model.Approve;
import me.nothing.login_.model.ExtraHour;
import me.nothing.login_.model.Leave;
import me.nothing.login_.model.LeaveStatusEnum;
import me.nothing.login_.model.Role;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.validation.BindingResult;
import javax.validation.Valid;

@Controller
@RequestMapping("/manager")
public class ManagerLeaveController {


    @Autowired
    private LeaveService leaveService;

    @RequestMapping(value = "/pending")
    public String pendingAarpvals(Model model, Authentication auth, HttpSession session){
         
        _StaffDetails usersession = (_StaffDetails) auth.getPrincipal();
        List<Role> roles = usersession.getStaff().getRoles();
		Role role= new Role();
		for(Role r: roles){
			role = r;
		}
		System.out.println("myprint "+ role.getName());
		session.setAttribute("usession", role);
        List<Staff> surbodinates = leaveService.getSubordinate(usersession.getStaff().getStfId());
        Map<Staff, List<Leave>> subordinateToLeavesMap = new HashMap<>();
        for(Staff subordinate: surbodinates){
            List<Leave> leaveList = leaveService.getpendingLeave(subordinate.getStfId());
            subordinateToLeavesMap.put(subordinate, leaveList);
        }
        model.addAttribute("pendinghistory", subordinateToLeavesMap);
        
        return "manager/home";
    }

    @RequestMapping(value = "/subordinates-history")
    public String subordinatesHistory(Authentication auth, Model model){
        _StaffDetails usersession = (_StaffDetails) auth.getPrincipal();
        List<Staff> surbodinates = leaveService.getSubordinate(usersession.getStaff().getStfId());
        Map<Staff, List<Leave>> subordinateToLeavesMap = new HashMap<>();
        for(Staff subordinate: surbodinates){
            List<Leave> leaveList = leaveService.findLeaveWithStaffId(subordinate.getStfId());
            subordinateToLeavesMap.put(subordinate, leaveList);
        }
        model.addAttribute("pendinghistory", subordinateToLeavesMap);
        return "manager/manager-subordinate-leave-history";
    }

    @GetMapping("/leave/display/{id}")
    public String newDepartmentPage(@PathVariable int id, Model model){
        Leave leave = leaveService.getLeaveWithLeaveId(id);
        model.addAttribute("leave", leave);
        model.addAttribute("approve", new Approve());
        return "manager/manager-leave-detail";
    }

    @PostMapping("/leave/edit/{id}")
    public String approveOrRejectCourse(@ModelAttribute("approve") @Valid Approve approve, BindingResult result,
    @PathVariable Integer id){
        if(result.hasErrors()){
            return "manager-leave-detail";
        }

        Leave l = leaveService.getLeaveWithLeaveId(id);
        if(approve.getDecision().trim().equalsIgnoreCase(LeaveStatusEnum.APPROVED.toString())){
            l.setStatus(LeaveStatusEnum.APPROVED);            
            l.setReason(approve.getReason());
        }
        else{
            l.setStatus(LeaveStatusEnum.REJECTED);
            l.setReason(approve.getReason());
        }
        leaveService.approvLeave(l);
        return "redirect:/manager/pending";
    }   
}
