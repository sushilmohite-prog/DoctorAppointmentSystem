package com.ineuron.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ineuron.models.Patient;

public interface PatientRepository extends JpaRepository<Patient,Integer>{
	
	@Query("select p from Patient p where p.email = :email")
	public Patient getPatientByEmail(@Param("email")String email);
	
	@Query("select p from Patient p where p.patientId = :patientId")
	public Patient getPatientById(@Param("patientId")int patientId);
}
