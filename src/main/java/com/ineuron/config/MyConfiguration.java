package com.ineuron.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.ineuron.dao.DoctorRepository;
import com.ineuron.dao.PatientRepository;




@EnableWebSecurity
@Configuration
public class MyConfiguration{
	
	
	@Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;
	
	@Autowired
	private AuthenticationSuccessHandler customsuccessHandler;
	
	@Bean 
	public UserDetailsService getUserDetailsService() {
		return new UserDetailsServiceImpl(doctorRepository, patientRepository);
	}
	
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		
		daoAuthenticationProvider.setUserDetailsService(this.getUserDetailsService());
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		
		return daoAuthenticationProvider;
	}
	
	
	//Configure Method
	    
	    public static final String LOGIN_URL = "/dologin";
	    public static final String LOGOUT_URL = "/logout";
	    public static final String DEFAULT_SUCCESS_URL = "/doctor/doctor-dashboard";
	    public static final String USERNAME = "username";
	    public static final String PASSWORD = "password";
	    
	    
	  

	    @Bean
	    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	        http.authorizeHttpRequests()
	                .requestMatchers("/patient/**").hasRole("PATIENT")
	                .requestMatchers("/doctor/**").hasRole("DOCTOR")
	                .requestMatchers("/**").permitAll()
	                .anyRequest().authenticated()
	            .and()
	                .csrf().disable()
	                .formLogin(form -> form
	                        .loginPage("/signin")
	                        .loginProcessingUrl(LOGIN_URL)
	                        .usernameParameter(USERNAME)
	                        .passwordParameter(PASSWORD)
	                        .successHandler(customsuccessHandler))
	                .logout(logout -> logout
	                        .logoutUrl("/logout")
	                        .invalidateHttpSession(true)
	                        .deleteCookies("JSESSIONID")
	                        .logoutSuccessUrl("/signin" + "?logout"));
	        return http.build();
	    }

	    
	    
}

