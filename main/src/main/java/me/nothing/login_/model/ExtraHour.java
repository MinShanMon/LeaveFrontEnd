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
import net.bytebuddy.asm.Advice.Local;


@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ExtraHour")
public class ExtraHour {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="extraHour_id",nullable=false)
    @Id
    private int id;

    private int Staff_id;

    private LocalDate date;

    @Column(name = "status", columnDefinition = "ENUM('SUBMITTED', 'APPROVED', 'UPDATED', 'REJECTED') default 'SUBMITTED'")
    @Enumerated(EnumType.STRING)
    private LeaveStatusEnum status;

    private double working_hour;

    public ExtraHour(int id, int staff_id, double working_hour){
        this.id = id;
        this.Staff_id = staff_id;
        this.date = LocalDate.now();
        this.status = LeaveStatusEnum.SUBMITTED;
        this.working_hour = working_hour;
    }
    
}
