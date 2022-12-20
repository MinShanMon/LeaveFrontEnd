package me.nothing.login_.model;

import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="Leave_Application")
public class Leave {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="leave_id",nullable=false)
    private int id;

    @Column(name="leaveType",nullable = false, columnDefinition = "ENUM('MEDICAL_LEAVE', 'ANNUAL_LEAVE', 'COMPENSATION_LEAVE')")
    @Enumerated(EnumType.STRING)
    private LeaveTypeEnum type;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    private int period;

    @Column(name = "status", columnDefinition = "ENUM('SUBMITTED', 'APPROVED', 'WITHDRAWN', 'UPDATED', 'REJECTED', 'DELETED')")
    @Enumerated(EnumType.STRING)
    private LeaveStatusEnum status;

    @Column(name="rejectReason",nullable = true)
    private String reason;

    @Column(name="workDissemination")
    private String work;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="staff_id", nullable = true)
    private Staff leave;

    public Leave(LeaveTypeEnum type,LocalDate startDate,LocalDate endDate,int period,
        LeaveStatusEnum status,String reason, String work){ 

        this.type=type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.period = period;
        this.status = status;
        this.reason = reason;
        this.work = work;
        // this.leave = staff;
    }
    public Leave(int id, LeaveTypeEnum type,LocalDate startDate,LocalDate endDate,int period,
    LeaveStatusEnum status,String reason, String work){ 
    this.id = id;
    this.type=type;
    this.startDate = startDate;
    this.endDate = endDate;
    this.period = period;
    this.status = status;
    this.reason = reason;
    this.work = work;
    // this.leave = staff;
}
}
