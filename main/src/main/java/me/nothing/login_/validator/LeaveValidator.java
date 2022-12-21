package me.nothing.login_.validator;

import java.time.LocalDate;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import me.nothing.login_.model.Leave;
import me.nothing.login_.model.LeaveStatusEnum;
import me.nothing.login_.model.LeaveTypeEnum;
import me.nothing.login_.model.Staff;

@Component
public class LeaveValidator implements Validator{
    @Override
    public boolean supports(Class<?> clasz){
        return Leave.class.isAssignableFrom(clasz);
    }

    @Override
    public void validate(Object target, Errors errors){
        Leave leave = (Leave) target;
        Staff staff = leave.getLeave();
        if(!leave.isHalfday()){
            if ((leave.getStartDate() != null && leave.getEndDate() != null) &&
            (leave.getStartDate().toEpochDay() > leave.getEndDate().toEpochDay())) {            
                errors.rejectValue("startDate", "error.dates", "End date should be greater than start date.");  
            }            
    
            if((leave.getStartDate() != null && leave.getEndDate() != null) &&
            (leave.getEndDate().toEpochDay() == LocalDate.now().toEpochDay())){
                errors.rejectValue("startDate", "error.dates", "End Date Cannot be current Date");
            }
            
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "endDate", "error.endDate","End Date is required.");


        }

        if((leave.getStartDate() != null && leave.getEndDate() != null) &&
            ((leave.getStartDate().toEpochDay() < LocalDate.now().toEpochDay()) || (leave.getEndDate().toEpochDay() < LocalDate.now().toEpochDay()))){
                errors.rejectValue("startDate", "error.dates", "You are choosing invalid date. Pls Choose Current Date");
            }
            
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "startDate", "error.startDate", "Start Date is required.");
        if((leave.isHalfday()) && (leave.getType()!= LeaveTypeEnum.COMPENSATION_LEAVE)){
            errors.rejectValue("halfday", "error.halfday", "Half Day is available for Compensation Leave Only");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "type", "error.type", "Leave Type is required.");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "work", "error.work", "wrok Dissemination field is required");
    }
}
