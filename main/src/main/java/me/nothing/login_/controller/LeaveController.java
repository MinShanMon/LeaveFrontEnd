package me.nothing.login_.controller;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import me.nothing.login_.model.Leave;
import me.nothing.login_.model._StaffDetails;
import me.nothing.login_.service.LeaveService;
import org.springframework.security.core.Authentication;

@Controller
@RequestMapping("/staff")
public class LeaveController {
    @Autowired
    LeaveService leaveService;

    @GetMapping("leave/history")
    public String employeeLeaveHistory(HttpSession session, Model model, Authentication authentication){
        _StaffDetails staffDetails = (_StaffDetails) authentication.getPrincipal();        
        List<Leave> employeeLeave = leaveService.findLeaveWithStaffId(staffDetails.getStaff().getStfId());
        for(Leave l: employeeLeave){
            System.out.println(l);
        }
        model.addAttribute("lhistory", employeeLeave);
        return "staff/home";
    }

    @GetMapping("leave/create")
    public String newLeavePage(Model model){
        model.addAttribute("leave", new Leave());
        return "leave-new";
    }
}
