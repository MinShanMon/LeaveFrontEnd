package me.nothing.login_.controller;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
        
        // System.out.println("start/leave/history");
        // _StaffDetails usession = (_StaffDetails) session.getAttribute("usession");
        // List<Leave> employeeLeave = leaveService.findLeaveWithStaffId(usession.getStaff().getStfId());
        // for(Leave l: employeeLeave){
        //     System.out.println("My print");
        //     System.out.println(l);
        // }
        // System.out.println("html work");
        // model.addAttribute("lhistory", employeeLeave);
        _StaffDetails staffDetails = (_StaffDetails) authentication.getPrincipal();

        // Collection<? extends GrantedAuthority> authorities = staffDetails.getAuthorities();
        List<Leave> employeeLeave = leaveService.findLeaveWithStaffId(staffDetails.getStaff().getStfId());
        for(Leave l: employeeLeave){
            System.out.println(l);
        }
        
        return "staff/home";
    }
}
