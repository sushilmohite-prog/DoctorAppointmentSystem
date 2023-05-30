package com.ineuron.dao;


import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ineuron.models.Appointment;


public interface AppointmentRepository extends JpaRepository<Appointment,Integer>  {
	 
	long countByDocIdAndDate(int docId,LocalDate date);
	
	long countByDocId(int docid);
	
	@Query("select a from Appointment a where a.appId = :appId")
	public Appointment getAppointmentById(@Param("appId")int appId);
	
	@Query("from Appointment as a where a.patientEmail= :email ORDER BY a.id DESC")
	public Page<Appointment> findAppointmentByEmail(@Param("email")String email,Pageable pageable); 
	
	@Query("from Appointment as a where a.docId= :docId")
	public Page<Appointment> findAppointmentByDocId(@Param("docId")int docId,Pageable pageable); 
	
	@Query("from Appointment as a where a.docId= :docId AND a.date= :date")
	public Page<Appointment> findAppointmentByDocIdAndDate(@Param("docId")int docId,LocalDate date,Pageable pageable );

}
