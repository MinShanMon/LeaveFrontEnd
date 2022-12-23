package me.nothing.login_.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import me.nothing.login_.model.Approve;
import me.nothing.login_.model.Leave;
import me.nothing.login_.model.LeaveStatusEnum;
import me.nothing.login_.model.LeaveTypeEnum;
public class ApproveValidator implements Validator{

    @Override
    public boolean supports(Class<?> clazz) {
        return Approve.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Approve app = (Approve) target;

        if(app.getDecision() == "REJECTED"){
            errors.rejectValue("reason","error.reason","Add Reason");
        }
        
    }
    
}
