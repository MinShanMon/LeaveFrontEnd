
package me.nothing.login_.controller;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.annotations.Parameter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import me.nothing.login_.service.CsvExportService;

@Controller
public class CsvController {

    private final CsvExportService csvExportService;

    public CsvController(CsvExportService csvExportService) {
        this.csvExportService = csvExportService;
    }

    @RequestMapping(path = "/manager/leave/export/{username}")
    public void getAllEmployeesInCsv(HttpServletResponse servletResponse,@PathVariable String username) throws IOException {
        servletResponse.setContentType("text/csv");
        servletResponse.addHeader("Content-Disposition","attachment; filename=\"_"+username+".csv");
        csvExportService.leaveToCsv(servletResponse.getWriter(),username);
    }

}