package me.nothing.login_.service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import me.nothing.login_.model._StaffDetails;
import me.nothing.login_.model.Staff;
import me.nothing.login_.repository.StaffRepository;
//import net.bytebuddy.utility.RandomString;

@Service
public class StaffService implements UserDetailsService {

	@Autowired
	private StaffRepository staffRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Staff staff = staffRepo.findByUsername(username);
		if (staff == null) {
			throw new UsernameNotFoundException("Staff not found");
		}
		return new _StaffDetails(staff);
	}

	public Staff getUserbyUsername(String username) throws UsernameNotFoundException {
		return staffRepo.findByUsername(username);

	}

	public void generateOneTimePassword(Staff staff) throws UnsupportedEncodingException, MessagingException {
		// String OTP = RandomString.make(8);

		Random rnd = new Random();
		String OTP = Integer.toString(rnd.nextInt(999999));

		System.out.println("OTP is: " + OTP);

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encoderOTP = passwordEncoder.encode(OTP);
		staff.setOtp(encoderOTP);
	
		staff.setOtpReqTime(new Date());

		staffRepo.save(staff);
		sendOTPEmail(staff, OTP);
	}

	private void sendOTPEmail(Staff staff, String OTP) throws UnsupportedEncodingException, MessagingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom("contact@hello.com", "Hello company");
		System.out.println(staff.getEmail());
		helper.setTo(staff.getEmail());

		String subject = "Here's your One Time Password (OTP) - Expire in 5 minutes!";

		String content = "<p>Hello " + staff.getUsername() + "</p>"
				+ "<p>For security reason, you're required to use the following "
				+ "One Time Password to login:</p>"
				+ "<p><b>" + OTP + "</b></p>"
				+ "<br>"
				+ "<p>Note: this OTP is set to expire in 5 minutes.</p>";

		helper.setSubject(subject);

		helper.setText(content, true);

		mailSender.send(message);

		System.out.println("email was sent");
	}

	@Autowired
	JavaMailSender mailSender;

	public void clearOTP(Staff staff) {
		staff.setOtp(null);
		staff.setOtpReqTime(null);

		staffRepo.save(staff);

		System.out.println("clear otp");
	}

	public void increaseFailedAttempt(Staff staff) {
		int newFailed = staff.getFailedAttempt() + 1;
		System.out.println(newFailed);
		staffRepo.updateFailedAttempt(newFailed, staff.getUsername());
	}

	public void clearFailedAttempt(Staff staff) {
		staffRepo.updateFailedAttempt(0, staff.getUsername());
	}
}
