package com.ineuron.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ineuron.config.CustomDoctorDetails;
import com.ineuron.config.CustomPatientDetails;
import com.ineuron.models.Doctor;
import com.ineuron.models.Patient;
import com.ineuron.dao.DoctorRepository;
import com.ineuron.dao.PatientRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    @Autowired
    public UserDetailsServiceImpl(DoctorRepository doctorRepository, PatientRepository patientRepository) {
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	System.out.println(username);
        if (username.startsWith("doctor_")) {
            Doctor doctor = doctorRepository.getDoctorByEmail(username);
            if (doctor == null) {
                throw new UsernameNotFoundException("Doctor not found");
            }
            return new CustomDoctorDetails(doctor);
        } else if (username.startsWith("patient_")) {
            Patient patient = patientRepository.getPatientByEmail(username);
            if (patient == null) {
                throw new UsernameNotFoundException("Patient not found");
            }
            return new CustomPatientDetails(patient);
        } else {
            throw new UsernameNotFoundException("Invalid username: " + username);
        }
    }
}
