package me.nothing.login_.handler;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import me.nothing.login_.model.Staff;
import me.nothing.login_.model._StaffDetails;
import me.nothing.login_.service.StaffService;

@Component
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private StaffService staffService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        _StaffDetails staffDetails = (_StaffDetails) authentication.getPrincipal();

        Collection<? extends GrantedAuthority> authorities = staffDetails.getAuthorities();
        authorities.forEach(auth -> System.out.println(auth.getAuthority()));
        String currentUrl = request.getContextPath();

        if (staffDetails.hasRole("admin")) {
            currentUrl += "/admin";
        }

        if (staffDetails.hasRole("staff")) {
            currentUrl += "/staff/leave/history";
        }

        if (staffDetails.hasRole("manager")) {
            currentUrl += "/manager/pending";
        }

        Staff staff = staffDetails.getStaff();
        if (staff.isOTPRequired()) {
            staffService.clearOTP(staff);
        }
        response.sendRedirect(currentUrl);

        staffService.clearFailedAttempt(staff);

    }

}
