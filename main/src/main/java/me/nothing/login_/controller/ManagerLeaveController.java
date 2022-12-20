package me.nothing.login_.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import me.nothing.login_.model._StaffDetails;
import me.nothing.login_.service.LeaveService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import me.nothing.login_.model.Staff;
import me.nothing.login_.model.Leave;

@Controller
@RequestMapping("/manager")
public class ManagerLeaveController {

    @Autowired
    private LeaveService leaveService;

    @RequestMapping(value = "/pending")
    public String pendingAarpvals(Model model, Authentication auth){
        // _StaffDetails usersession = (_StaffDetails) session.getAttribute("userssion");  
        _StaffDetails usersession = (_StaffDetails) auth.getPrincipal();
        List<Staff> surbodinates = leaveService.getSubordinate(usersession.getStaff().getStfId());
        Map<Staff, List<Leave>> subordinateToLeavesMap = new HashMap<>();
        for(Staff subordinate: surbodinates){
            List<Leave> leaveList = leaveService.findLeaveWithStaffId(subordinate.getStfId());
            subordinateToLeavesMap.put(subordinate, leaveList);
        }
        model.addAttribute("pendinghistory", subordinateToLeavesMap);
        
        return "manager/home";
    }

}
