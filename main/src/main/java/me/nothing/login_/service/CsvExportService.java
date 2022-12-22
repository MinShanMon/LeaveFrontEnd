package me.nothing.login_.service;



import java.io.IOException;
import java.io.Writer;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.nothing.login_.model.Leave;
import me.nothing.login_.model.Staff;
import me.nothing.login_.repository.StaffRepository;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class CsvExportService {

    private static final Logger log = getLogger(CsvExportService.class);

    private final StaffRepository staffRepository;

    @Autowired
    private LeaveServiceImpl leaveServiceImpl;

    public CsvExportService(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    public void leaveToCsv(Writer writer,String username) {

        int stfid = staffRepository.findByUsernameToGetId(username);
        System.out.print(stfid);
        List<Leave> leaves = leaveServiceImpl.findLeaveWithStaffId(stfid);

        Staff staff = staffRepository.findById(stfid);
     
  
        try (CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
            csvPrinter.printRecord("UserName","Leave Type","Start-date", "End-date","Period","Status");
           
            for (Leave leave : leaves) {
                csvPrinter.printRecord(staff.getUsername(),leave.getType(),leave.getStartDate(),leave.getEndDate(),leave.getPeriod(),leave.getStatus());
            }
        } catch (IOException e) {
            log.error("Error While writing CSV ", e);
        }
    }
}
