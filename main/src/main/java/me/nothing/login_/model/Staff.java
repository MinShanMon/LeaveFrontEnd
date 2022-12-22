package me.nothing.login_.model;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import org.hibernate.type.LocalDateTimeType;

import com.fasterxml.jackson.annotation.JsonIgnore;

// import org.apache.catalina.Manager;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//import me.nothing.login_.model.LeaveTypes.LeaveType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Staffs")
public class Staff {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "staff_id", nullable = false)
	private int stfId;

	@Column(name = "managerid", nullable = true)
	private int managerId;

	@Column(nullable = false, length = 20)
	private String username;

	@Column(nullable = false, length = 64)
	private String password;

	@Column(nullable = false, unique = true, length = 45)
	private String email;

	@Column(name = "job_title", nullable = false)
	private String title;

	private String firstname;

	private String lastname;

	private boolean status;

	@Column(name = "one_time_password")
	private String otp;

	@Column(name = "otp_requested_time")
	private Date otpReqTime;

	@Column(name="anual_leave_entitlemnt")
	private int anuLeave;

	@Column(name="medi_requested_entitlement")
	private int mediLeave;

	@Column(name="comp_requested_entitlement")
	private double compLeave;

	@Column(name = "failed_attempt", nullable = false)
	private int failedAttempt;


	// @JsonIgnore
    // @OneToMany(mappedBy = "leave")
	// private List<Leave> staffLeave;
	
    @JsonIgnore
    @ManyToMany(targetEntity = Role.class, cascade = {CascadeType.ALL, CascadeType.PERSIST}, fetch = FetchType.EAGER)
	@JoinTable(name = "staff_role", joinColumns = {@JoinColumn(name = "staff_id", referencedColumnName = "staff_id")}, 
    inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "role_id")})
	private List<Role> roles;

	//private static final long otpDuration = 3 * 6 * 1000; // valid in 3 min

	public boolean isOTPRequired() {
		if (this.getOtp() == null) {
			
			System.out.println("no need otp");
			return false;
		}

		// long currentTimeInMillis = System.currentTimeMillis();
		// long otpRequestedTimeInMillis = this.otpReqTime.getTime();

		// if (otpRequestedTimeInMillis + otpDuration < currentTimeInMillis) {
		// 	// OTP expires
		// 	return false;
		// }

		return true;
	}

	public boolean hasRole(String roleName) {
		Iterator<Role> iterator = roles.iterator();

		while (iterator.hasNext()) {
			Role role = iterator.next();
			if (role.getName().equals(roleName)) {
				return true;
			}
		}
		return false;
	}

	    
    public Staff(int managerId, String username, String password,String title,
            String firstname, String lastname, boolean status, String email, int anuLeave, int mediLeave,
            double compLeave) {
        this.managerId = managerId;
        this.username = username;
        this.password = password;
        this.title = title;
        this.firstname = firstname;
        this.lastname = lastname;
        this.status = status;
        this.email = email;
        this.anuLeave = anuLeave;
        this.mediLeave = mediLeave;
        this.compLeave = compLeave;
    }

}
