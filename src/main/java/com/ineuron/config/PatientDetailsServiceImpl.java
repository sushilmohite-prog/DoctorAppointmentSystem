package com.ineuron.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.ineuron.dao.PatientRepository;
import com.ineuron.models.Patient;

public class PatientDetailsServiceImpl  implements UserDetailsService{

	@Autowired
	private PatientRepository patientRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	//fetching user from database
		
		Patient patient = patientRepository.getPatientByEmail(username);
		
		if(patient==null)
		{
			throw new UsernameNotFoundException("Could not found user !!");
		}
		
		
		CustomPatientDetails customPatientDetails = new CustomPatientDetails(patient); 
		
		return customPatientDetails ;
	}

}
