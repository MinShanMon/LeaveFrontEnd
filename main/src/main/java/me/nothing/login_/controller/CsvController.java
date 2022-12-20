package me.nothing.login_.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import me.nothing.login_.repository.StaffRepository;
import me.nothing.login_.service.StaffService;



@Controller
@RequestMapping("/staff")
public class CsvController {

    @Autowired
    private StaffService staffService;

    @Autowired
    private StaffRepository staffRepository;

    @GetMapping("/users/export")
    public void exportToCvs(HttpServletResponse response){

    }


    
}
