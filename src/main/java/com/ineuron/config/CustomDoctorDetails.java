package com.ineuron.config;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ineuron.models.Doctor;

public class CustomDoctorDetails implements UserDetails   {
	
	private static final long serialVersionUID = 1L;
	private Doctor doctor;

	
	
	
	public CustomDoctorDetails(Doctor doctor) {
		super();
		this.doctor = doctor;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		SimpleGrantedAuthority simpleGrantedAuthority =	new SimpleGrantedAuthority(doctor.getRole());
		return List.of(simpleGrantedAuthority);
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return doctor.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return doctor.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
