package me.nothing.login_;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import me.nothing.login_.repository.RoleRepository;
import me.nothing.login_.repository.StaffRepository;
import me.nothing.login_.service.LeaveService;
import me.nothing.login_.model.Leave;
import me.nothing.login_.model.LeaveStatusEnum;
import me.nothing.login_.model.LeaveTypeEnum;
// /import me.nothing.login_.model.LeaveStatusEnum;
import me.nothing.login_.model.Role;
import me.nothing.login_.model.Staff;
@SpringBootApplication
public class LoginApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoginApplication.class, args);
	}


	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Bean
	public CommandLineRunner run(StaffRepository staffRepository, RoleRepository roleRepository, LeaveService leaveService) {
		return args -> {
			// Role role1 = roleRepository.save(new Role("admin"));
			// Role role2 = roleRepository.save(new Role("manager"));
			Role role3 = roleRepository.save(new Role("staff"));			
			// String passwd= "root";
			String encodedPassword = passwordEncoder.encode("root");
			Staff staff1 = staffRepository.save(new Staff(1, 0, "root", encodedPassword, "lapyae.945@gmail.com", "notitile", "shan", "mon", true, null, null, 60, 10, 0, 1, null));
			// Staff staff2 = staffRepository.save(new Staff(2, 1, "staff", encodedPassword, "chaile@gmail.com", "notitile", "ch", "lay", true, null, null, 60, 10, 0, 0, null));
			// List<Role> manager = new ArrayList<>();
			List<Role> staff = new ArrayList<>();
			// manager.add(role2);
			staff.add(role3);
			staff1.setRoles(staff);
			// staff1.setRoles(manager);
			staffRepository.saveAndFlush(staff1);
			// staffRepository.saveAndFlush(staff1);
			// Leave leave2 = new Leave(LeaveTypeEnum.MEDICAL_LEAVE, LocalDate.now().plusDays(2), LocalDate.now().plusDays(4), 2, LeaveStatusEnum.APPROVED, "null", "null");
			// Leave leave22 = leaveService.createLeaveHistory(2, leave2);

			// 			List<Leave> leaves = leaveService.findLeaveWithStaffId(2);

			
			// for(Leave l: leaves){
			// 	System.out.println("find leave with staff id");
			// 	System.out.println(l);
			// }
			// Leave leave23 = new Leave(1,LeaveTypeEnum.ANNUAL_LEAVE, LocalDate.now().plusDays(1), LocalDate.now().plusDays(15), 2, LeaveStatusEnum.SUBMITTED, "null", "null",staff2);
			
			// System.out.println("created leave");
			// System.out.println(leave22);

			// Leave leave = leaveService.updateLeaveHistory(leave22);
			// System.out.println("updated leave");
			// System.out.println(leave);
			
		};
	}
}
