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

    @GetMapping("/extra/history")
    public String employeeLeaveHistory( Model model, Authentication authentication){
        _StaffDetails staffDetails = (_StaffDetails) authentication.getPrincipal();        
        List<ExtraHour> employeeExtraHour = extraHourService.suboExtraHour(staffDetails.getStaff().getStfId());
        model.addAttribute("ehistory", employeeExtraHour);
        return "staff/extrahour-history";
    }


    // @GetMapping("leave/edit/{id}")
    // public String editLeavePage(@PathVariable int id, Model model){
    //     Leave leave = leaveService.getLeaveWithLeaveId(id);
    //     model.addAttribute("leave", leave);
    //     return "staff/leave-edit";
    // }

    @GetMapping("extra/put/{id}")
    public String newovertimepage(@PathVariable int id, Model model){
        ExtraHour ext = extraHourService.getExtraWithId(id);
        model.addAttribute("extra", ext);
        return "staff/extrahour-edit";
    }


    @PostMapping("extra/put")
    public String editextra(@ModelAttribute @Valid ExtraHour extra, BindingResult result, Model model){
        if(result.hasErrors()){
            return "staff/extrahour-edit";
        }
        extraHourService.updExtraHour(extra);

        return "redirect:/exstaff/extra/history";
    }
    
    @RequestMapping(value="extra/delete/{id}")
    public String deleteextra(@PathVariable Integer id){
        // ExtraHour extra = extraHourService.getExtraWithId(id);
        extraHourService.delExtraHour(id);
        return "redirect:/exstaff/extra/history";
    }
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
