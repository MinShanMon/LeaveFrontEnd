package me.nothing.login_.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import me.nothing.login_.model.ExtraHour;
import me.nothing.login_.model.LeaveStatusEnum;
import me.nothing.login_.model._StaffDetails;
import me.nothing.login_.service.ExtraHourService;

@Controller
@RequestMapping("/exstaff")
public class ExtraHourController {
    
    @Autowired
    ExtraHourService extraHourService;


    @GetMapping("overtime/create")
    public String newovertime(Model model){
        model.addAttribute("ExtraHour", new ExtraHour());
        return "staff/extrahour-new";
    }

    @PostMapping("/overtime/create")
    public String createNewLeave(@ModelAttribute("ExtraHour") @Valid ExtraHour ExtraHour, BindingResult result, Authentication authentication){
        _StaffDetails staff = (_StaffDetails) authentication.getPrincipal();
        ExtraHour.setStaff_id(staff.getStaff().getStfId());
        ExtraHour.setStatus(LeaveStatusEnum.SUBMITTED);
        extraHourService.createExtraHour(ExtraHour);
        return "redirect:/exstaff/extra/history";
    }

    @GetMapping("extra/history")
    public String employeeLeaveHistory( Model model, Authentication authentication){
        _StaffDetails staffDetails = (_StaffDetails) authentication.getPrincipal();        
        List<ExtraHour> employeeExtraHour = extraHourService.suboExtraHour(staffDetails.getStaff().getStfId());
        model.addAttribute("ehistory", employeeExtraHour);
        return "staff/extrahour-history";
    }

    @GetMapping("extra/put/{id}")
    public String newovertimepage(@PathVariable int id, Model model){
        ExtraHour ext = extraHourService.getExtraWithId(id);
        model.addAttribute("extra", ext);
        return "staff/extrahour-edit";
    }

    

    // @GetMapping("leave/edit/{id}")
    // public String editLeavePage(@PathVariable int id, Model model){
    //     Leave leave = leaveService.getLeaveWithLeaveId(id);
    //     model.addAttribute("leave", leave);
    //     return "staff/leave-edit";
    // }

    // @PostMapping("/leave/edit/{id}")
    // public String editLeave(@ModelAttribute @Valid Leave leave, BindingResult result, @PathVariable Integer id, Model model)
    // {
    //     if(result.hasErrors()){
    //         // model.addAttribute("leave", leaveService.getLeaveWithLeaveId(id));
    //         return "staff/leave-edit";
    //     }
    //     leave.setStartDate(leave.getStartDate());
    //     leave.setEndDate(leave.getEndDate());
    //     leave.setType(leave.getType());
    //     leaveService.updateLeaveHistory(leave);
    //     return "redirect:/staff/leave/history";
    // }

    // @GetMapping("/leave/withdraw/{id}")
    // public String withdrawLeave(@PathVariable("id") Integer id){
    //     // Leave leave = leaveService.getLeaveWithLeaveId(id);
    //     leaveService.withdrawLeave(id);
    //     return "redirect:/staff/leave/history";
        
    // }

    // @GetMapping("/leave/delete/{id}")
    // public String deleteLeave(@PathVariable("id") Integer id){
    //     leaveService.deleteLeave(id);
    //     return "redirect:/staff/leave/history";
    // }


}
