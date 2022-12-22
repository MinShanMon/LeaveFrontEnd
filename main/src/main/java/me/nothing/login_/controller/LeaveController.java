package me.nothing.login_.controller;

import java.util.Collection;
import java.util.List;
import javax.validation.Valid;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import me.nothing.login_.model.Leave;
import me.nothing.login_.model.LeaveStatusEnum;
import me.nothing.login_.model.Role;
import me.nothing.login_.model._StaffDetails;
import me.nothing.login_.service.LeaveService;
import org.springframework.security.core.Authentication;
import me.nothing.login_.validator.LeaveValidator;;
@Controller
@RequestMapping("/staff")
public class LeaveController {
    @Autowired
    private LeaveService leaveService;

    @Autowired
    private LeaveValidator leaveValidator;

    @InitBinder("leave")
    private void initLeaveBinder(WebDataBinder binder){
        binder.addValidators(leaveValidator);
    }
    
    @GetMapping("leave/history")
    public String employeeLeaveHistory( Model model, Authentication authentication, HttpSession session){
        _StaffDetails staffDetails = (_StaffDetails) authentication.getPrincipal();  
        // _StaffDetails staffDetails=(_StaffDetails)session.getAttribute("usession");
        List<Role> roles = staffDetails.getStaff().getRoles();
		Role role= new Role();
		for(Role r: roles){
			role = r;
		}
		System.out.println("myprint "+ role.getName());
		session.setAttribute("usession", role);
        List<Leave> employeeLeave = leaveService.findLeaveWithStaffId(staffDetails.getStaff().getStfId());
        for(Leave l: employeeLeave){
            System.out.println(l);
        }
        model.addAttribute("staffDetails", staffDetails);
        model.addAttribute("lhistory", employeeLeave);
        return "staff/home";
    }

    @GetMapping("leave/create")
    public String newLeavePage(Model model){
        model.addAttribute("leave", new Leave());
        return "staff/leave-new";
    }

    @PostMapping("/leave/create")
    public String createNewLeave(@ModelAttribute("leave") @Valid Leave leave, BindingResult result,Model model, Authentication authentication){
        try{
            if(result.hasErrors()){
                return "staff/leave-new";
            }
    
            _StaffDetails staffDetails = (_StaffDetails) authentication.getPrincipal();
            leave.setLeave(staffDetails.getStaff());        
            if(leave.isHalfday()){
                leave.setPeriod(.5);
            }
            leave.setPeriod(leave.getEndDate().toEpochDay()-leave.getStartDate().toEpochDay());
            leaveService.createLeaveHistory(staffDetails.getStaff().getStfId(), leave);
            return "redirect:/staff/leave/history";
        }
        catch(Exception e){
            model.addAttribute("erro", e.getMessage());
            return "redirect:/staff/leave/create?error=true";
        }

    }

    @GetMapping("leave/edit/{id}")
    public String editLeavePage(@PathVariable int id, Model model){
        Leave leave = leaveService.getLeaveWithLeaveId(id);
        model.addAttribute("leave", leave);
        return "staff/leave-edit";
    }

    @PostMapping("/leave/edit")
    public String editLeave(@ModelAttribute @Valid Leave leave, BindingResult result, Model model)
    {
        if(result.hasErrors()){
            // model.addAttribute("leave", leaveService.getLeaveWithLeaveId(id));
            return "staff/leave-edit";
        }
        leaveService.updateLeaveHistory(leave);
        return "redirect:/staff/leave/history";
    }

    @GetMapping("/leave/withdraw/{id}")
    public String withdrawLeave(@PathVariable("id") Integer id){
        // Leave leave = leaveService.getLeaveWithLeaveId(id);
        leaveService.withdrawLeave(id);
        return "redirect:/staff/leave/history";
        
    }

    @GetMapping("/leave/delete/{id}")
    public String deleteLeave(@PathVariable("id") Integer id){
        leaveService.deleteLeave(id);
        return "redirect:/staff/leave/history";
    }


}
