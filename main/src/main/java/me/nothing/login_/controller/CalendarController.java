package me.nothing.login_.controller;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import javax.validation.Valid;

import javax.servlet.http.HttpSession;

import java.time.format.DateTimeFormatter;
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
import me.nothing.login_.model._StaffDetails;
import me.nothing.login_.service.LeaveService;
import org.springframework.security.core.Authentication;

@Controller
public class CalendarController {
    @Autowired
    private LeaveService leaveService;

    @GetMapping("/calendar")
    public String newLeavePage(Model model){
        return "/calendar";
    }
    
    @GetMapping("/calendar/list/{day}")
    public String dayLeaveHistory(@PathVariable String day, Model model, Authentication authentication){
       // LocalDate parseLocalDate = LocalDate.parse(day, DateTimeFormatter.ofPattern("yyyyMMdd"));       
        List<Leave> dayLeave = leaveService.findLeaveWithDay(day);
        for(Leave l: dayLeave){
            System.out.println(l);
        }
        model.addAttribute("dayLeave", dayLeave);
        return "/calendar-list";
    }
}

