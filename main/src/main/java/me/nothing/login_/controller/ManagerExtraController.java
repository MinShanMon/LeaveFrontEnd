package me.nothing.login_.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import me.nothing.login_.model.Approve;
import me.nothing.login_.model.ExtraHour;
import me.nothing.login_.model.Staff;
import me.nothing.login_.model._StaffDetails;
import me.nothing.login_.service.ExtraHourService;
import me.nothing.login_.service.LeaveService;

@Controller
@RequestMapping("managere")
public class ManagerExtraController {
    @Autowired
    private ExtraHourService extraHourService;

    @Autowired
    private LeaveService leaveService;

    @RequestMapping(value="/overtime/pending")
    public String PendingApproval(Model model, Authentication auth){
        _StaffDetails usersession = (_StaffDetails) auth.getPrincipal();
        List<Staff> subordinates = leaveService.getSubordinate(usersession.getStaff().getStfId());
        Map<Staff, List<ExtraHour>> subordinateToExtMap =  new HashMap<>();
        for(Staff subo: subordinates){
            List<ExtraHour> extList = extraHourService.getpendingExtra(subo.getStfId());
            subordinateToExtMap.put(subo, extList);
        }
        model.addAttribute("pendinghistory", subordinateToExtMap);
        return "manager/extra-home";
    }


    @RequestMapping(value= "/overtime/subordinates-history")
    public String subordinatesHistory(Authentication auth, Model model){
        _StaffDetails usersession = (_StaffDetails) auth.getPrincipal();
        List<Staff> subordinates = leaveService.getSubordinate(usersession.getStaff().getStfId());
        Map<Staff, List<ExtraHour>> subordinateToExtMap = new HashMap<>();
        for(Staff subo: subordinates){
            List<ExtraHour> extList = extraHourService.suboExtraHour(subo.getStfId());
            subordinateToExtMap.put(subo, extList);
        }
        model.addAttribute("pendinghistory", subordinateToExtMap);
        return "manager/manager-subordinate-extra-history";
    }

    @GetMapping("/extra/display/{id}")
    public String newDepartmentPage(@PathVariable int id, Model model){
        ExtraHour ext = extraHourService.getExtraWithId(id);
        model.addAttribute("leave", ext);
        model.addAttribute("approve", new Approve());
        return "manager/manager-extra-detail";
    }


    @GetMapping("/extra/approve/{id}")
    public String approveExt(@PathVariable("id") Integer id){
        extraHourService.approvExtraHour(id);
        return "redirect:/managere/overtime/pending";
    }

    @GetMapping("/extra/reject/{id}")
    public String rejectExt(@PathVariable("id") Integer id){
        extraHourService.rejecExtraHour(id);
        return "redirect:/managere/overtime/pending";
    }
}
