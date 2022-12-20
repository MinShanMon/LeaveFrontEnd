package me.nothing.login_.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import me.nothing.login_.filter.CustomAuthenticationFilter;
import me.nothing.login_.handler.LoginSuccessHandler;
import me.nothing.login_.service.StaffService;

@Deprecated
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public UserDetailsService userDetailsService() {
		return new StaffService();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());

		return authProvider;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/login").permitAll()
				.antMatchers("/admin/**").hasAuthority("admin")
				.antMatchers("/manager/**").hasAuthority("manager")
				.antMatchers("/staff/**").hasAuthority("staff")
				.and()
				.addFilterBefore(customAuthenticationFilter, CustomAuthenticationFilter.class)
				.formLogin()
				.loginPage("/login")
				.usernameParameter("username")
				.successHandler(successHandler).permitAll()
				.permitAll()
				.and()
				.logout().logoutSuccessUrl("/login?logout=true").permitAll()
				.and()
				.sessionManagement()
				.invalidSessionUrl("/login?logout=true");

	}

	@Autowired
	private LoginSuccessHandler successHandler;

	// @Bean
	// public SessionRegistry sessionRegistry() {
	// 	SessionRegistry sessionRegistry = new SessionRegistryImpl();
	// 	return sessionRegistry;
	// }


	// @Bean
	// public HttpSessionEventPublisher httpSessionEventPublisher() {
	// 	return new HttpSessionEventPublisher();
	// }

	@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Autowired
	private CustomAuthenticationFilter customAuthenticationFilter;

}
