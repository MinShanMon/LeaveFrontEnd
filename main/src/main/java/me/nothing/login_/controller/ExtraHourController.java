package me.nothing.login_.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import me.nothing.login_.model.ExtraHour;
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

    // @GetMapping("leave/history")
    // public String employeeLeaveHistory( Model model, Authentication authentication){
    //     _StaffDetails staffDetails = (_StaffDetails) authentication.getPrincipal();        
    //     List<Leave> employeeLeave = leaveService.findLeaveWithStaffId(staffDetails.getStaff().getStfId());
    //     for(Leave l: employeeLeave){
    //         System.out.println(l);
    //     }
    //     model.addAttribute("lhistory", employeeLeave);
    //     return "staff/home";
    // }
    // @GetMapping("leave/both")
    // public String bothLeavePage(){
    //     return "staff/both-leave";
    // }

    // @GetMapping("leave/create")
    // public String newLeavePage(Model model){
    //     model.addAttribute("leave", new Leave());
    //     return "staff/leave-new";
    // }

    // @PostMapping("/leave/create")
    // public String createNewLeave(@ModelAttribute("leave") @Valid Leave leave, BindingResult result, Authentication authentication){
    //     if(result.hasErrors()){
    //         return "staff/leave-new";
    //     }

    //     _StaffDetails staffDetails = (_StaffDetails) authentication.getPrincipal();
    //     leave.setLeave(staffDetails.getStaff());        
    //     if(leave.isHalfday()){
    //         leave.setPeriod(.5);
    //     }
    //     leave.setPeriod(leave.getEndDate().toEpochDay()-leave.getStartDate().toEpochDay());
    //     leaveService.createLeaveHistory(staffDetails.getStaff().getStfId(), leave);
    //     return "redirect:/staff/leave/history";
    // }

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
