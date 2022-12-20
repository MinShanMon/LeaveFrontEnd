package me.nothing.login_.service;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;

import me.nothing.login_.model.Leave;
import me.nothing.login_.model.Staff;

public interface LeaveService {
    public List<Leave> findLeaveWithStaffId(Integer id);

    public Leave createLeaveHistory(Integer stfid, Leave leaves);

    public Leave updateLeaveHistory(Leave leave);

    public Leave getLeaveWithLeaveId(Integer id);

    public List<Staff> getSubordinate(Integer id);

    public Leave approvLeave(Leave leave);

    public Leave deleteLeave(int id);

    public Leave withdrawLeave(int id);

    
}
