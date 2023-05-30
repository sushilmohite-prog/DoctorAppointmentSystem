package com.ineuron.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ineuron.models.Doctor;



public interface DoctorRepository extends JpaRepository<Doctor,Integer>{
	@Query("select d from Doctor d where d.email = :email")
	public Doctor getDoctorByEmail(@Param("email")String email);
	
	@Query("select d from Doctor d where d.docId = :docId")
	public Doctor getDoctorById(@Param("docId")String docId);
	
	@Query("SELECT d FROM Doctor d WHERE LOWER(d.fname) LIKE %:query% OR LOWER(d.lname) LIKE %:query% OR LOWER(d.city) LIKE %:query%")
	public List<Doctor> search(@Param("query")String query);
}
