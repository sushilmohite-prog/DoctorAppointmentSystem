package com.ineuron.models;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import jakarta.validation.constraints.NotNull;


@Entity
@Table(name="Appointment")
public class Appointment {

	@Id
	@GeneratedValue(generator="appointment_gen",strategy=GenerationType.AUTO)
	@SequenceGenerator(name="appointment_gen",sequenceName="appointment_seq",initialValue=10000,allocationSize=100)
	private int appId;
	
	private int patientId;
	private String patientName;
	private String patientEmail;
	private String patientPhone;
	private int docId;
	private String docName;
	private String hospitalName;
	private String status;
	
	@NotNull(message="Please Enter Date")
	private LocalDate date;
	
	
	
	
	public String getHospitalName() {
		return hospitalName;
	}
	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	
	public String getPatientEmail() {
		return patientEmail;
	}
	public void setPatientEmail(String patientEmail) {
		this.patientEmail = patientEmail;
	}
	
	public String getDocName() {
		return docName;
	}
	public void setDocName(String docName) {
		this.docName = docName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
	
	
	/*
	 * public String getDate() { return date; } public void setDate(String date) {
	 * this.date = date; }
	 */
		
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public String getPatientPhone() {
		return patientPhone;
	}
	public void setPatientPhone(String patientPhone) {
		this.patientPhone = patientPhone;
	}
	
	public int getAppId() {
		return appId;
	}
	public void setAppId(int appId) {
		this.appId = appId;
	}
	public int getPatientId() {
		return patientId;
	}
	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}
	public int getDocId() {
		return docId;
	}
	public void setDocId(int docId) {
		this.docId = docId;
	}
	public Appointment() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		return "Appointment [appId=" + appId + ", patientId=" + patientId + ", patientName=" + patientName
				+ ", patientEmail=" + patientEmail + ", patientPhone=" + patientPhone + ", docId=" + docId
				+ ", docName=" + docName + ", hospitalName=" + hospitalName + ", status=" + status + ", date=" + date
				+ "]";
	}
	
	
	
}
