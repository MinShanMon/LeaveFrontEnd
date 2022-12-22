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
import me.nothing.login_.service.ExtraHourService;
import me.nothing.login_.service.ExtraHourServiceImp;
import me.nothing.login_.service.LeaveService;
import me.nothing.login_.model.ExtraHour;
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
	public CommandLineRunner run(StaffRepository staffRepository, RoleRepository roleRepository, LeaveService leaveService, ExtraHourService extraHourService) {
		return args -> {


						
			Role role2 = roleRepository.save(new Role("manager"));
			Role role3 = roleRepository.save(new Role("staff"));			
			List<Role> manager = new ArrayList<>();
			List<Role> staff = new ArrayList<>();
			manager.add(role2);
			staff.add(role3);

			// String passwd= "root";
			String encodedPassword = passwordEncoder.encode("root");
			Staff may = staffRepository.save(new Staff(0, "may",encodedPassword, "Project Manager", "May","Tan",
			true, "May_Tan@gmail.com", 18, 60, 0));
			may.setRoles(manager);
			staffRepository.saveAndFlush(may);
			
			Staff john = staffRepository.saveAndFlush(new Staff(0, "john",encodedPassword, "IT Manager", "John","Tan",
			true, "John_Tan@gmail.com", 18, 60, 0));
			john.setRoles(manager);
			staffRepository.saveAndFlush(john);
			Staff lynn =  staffRepository.saveAndFlush(new Staff(1, "lynn", encodedPassword,"Data Analyst", "Ying", "Li",
			true, "Ying_Li@gmail.com", 18, 60, 0));
			lynn.setRoles(staff);
			staffRepository.saveAndFlush(lynn);
			Staff lexi = staffRepository.saveAndFlush(new Staff(1,"lexi",encodedPassword, "Business Analyst", "Shan", "Feng", 
			true, "Shan_Feng@gmail.com", 18, 60, 0));
			lexi.setRoles(staff);
			staffRepository.saveAndFlush(lexi);
			Staff cailei = staffRepository.saveAndFlush(new Staff(1,"cailei", encodedPassword, "Admin", "Cai Lei", "Zhang", 
			true, "Cailei_Zhang@gmail.com", 14, 60, 0));
			cailei.setRoles(staff);
			staffRepository.saveAndFlush(cailei);
			Staff oscar = staffRepository.saveAndFlush(new Staff(2,"oscar", encodedPassword, "Software Developer", "Shan Mon", "Min", 
			true, "ShanMon_Min@gmail.com", 18, 60, 0));
			oscar.setRoles(staff);
			staffRepository.saveAndFlush(oscar);
			Staff travis = staffRepository.saveAndFlush(new Staff(2,"travis", encodedPassword, "Software Architect", "La Pyae Htun", "Soe", 
			true, "e1045754@u.nus.edu", 18, 60, 0));
			travis.setRoles(staff);
			staffRepository.saveAndFlush(travis);
			Staff ivan = staffRepository.saveAndFlush(new Staff(2,"ivan", encodedPassword, "Admin", "Ivan Tse Khiang ", "Eng", 
			true, "Ivan_Eng@gmail.com", 14, 60, 0));
			ivan.setRoles(staff);
			staffRepository.saveAndFlush(ivan);

		};

	}
}