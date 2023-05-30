package com.ineuron.models;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.Size;

@Entity
@Table(name="Patient")
public class Patient {
	@Id
	@GeneratedValue(generator="patient_gen",strategy=GenerationType.AUTO)
	@SequenceGenerator(name="patient_gen",sequenceName="patient_seq",initialValue=100,allocationSize=1)
	private int patientId;
	
	@NotBlank(message="First name should not be empty !!")
	@Size(min=3,max=12,message="First Name must be between 3 - 12 characters")
	private String fname;
	
	@NotBlank(message="Last name should not be empty !!")
	@Size(min=3,max=12,message="Last Name must be between 3 - 12 characters")
	private String lname;
	
	@NotBlank(message="Street Address Required")
	private String streetAddress;
	
	private String landmark;
	
	@NotBlank(message="City Required")
	private String city;
	

	@NotBlank(message="State Required")
	private String state;
	
	private String role;
	
	@NotBlank(message="Pin Code Required !!")
	private String pin;
	
	@NotBlank(message="Please enter Date of Birth !!")
	private String dob;
	
	@NotBlank(message="Please select appropriate value !!")
	private String maritalStatus;
	
	@NotBlank(message="Email required !!")
	@Column(unique= true)
	@Email(regexp="^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$" ,message="Enter valid email !!")
	private String email;

    @NotBlank(message="Password should not be blank !!")
	private String password;
	
	@NotBlank(message="Please enter your contact !!")
	@Column(unique= true)
	private String phone;
	
	private String gender;
	@NotBlank(message="Please select appropriate value !!")
	private String medicalHistory;
	@NotBlank(message="Please select appropriate value !!")
	private String familyHistory;
	
	@NotBlank(message="Please select appropriate value !!")
	private String alcoholUse;
	@NotBlank(message="Please select appropriate value !!")
	private String tobaccoUse;
	@NotBlank(message="Emergency details are required !!")
	private String emergencyName;
    @NotBlank(message="Emergency contact required")
	private String emergencyContact;
    @NotBlank(message="Please select appropriate value !!")
	private String emergencyRelation;
	
	
	
	
	
	
	//Default Constructor
	public Patient() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
	//Getters Setters
	
	
	
	public int getPatientId() {
		return patientId;
	}
	public String getGender() {
		return gender;
	}





	public void setGender(String gender) {
		this.gender = gender;
	}





	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getLandmark() {
		return landmark;
	}

	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}


	public String getPin() {
		return pin;
	}


	public void setPin(String pin) {
		this.pin = pin;
	}


	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getMaritalStatus() {
		return maritalStatus;
	}
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMedicalHistory() {
		return medicalHistory;
	}
	public void setMedicalHistory(String medicalHistory) {
		this.medicalHistory = medicalHistory;
	}
	public String getFamilyHistory() {
		return familyHistory;
	}
	public void setFamilyHistory(String familyHistory) {
		this.familyHistory = familyHistory;
	}
	public String getAlcoholUse() {
		return alcoholUse;
	}
	public void setAlcoholUse(String alcoholUse) {
		this.alcoholUse = alcoholUse;
	}
	public String getTobaccoUse() {
		return tobaccoUse;
	}
	public void setTobaccoUse(String tobaccoUse) {
		this.tobaccoUse = tobaccoUse;
	}
	public String getEmergencyName() {
		return emergencyName;
	}
	public void setEmergencyName(String emergencyName) {
		this.emergencyName = emergencyName;
	}
	public String getEmergencyContact() {
		return emergencyContact;
	}
	public void setEmergencyContact(String emergencyContact) {
		this.emergencyContact = emergencyContact;
	}
	public String getEmergencyRelation() {
		return emergencyRelation;
	}
	public void setEmergencyRelation(String emergencyRelation) {
		this.emergencyRelation = emergencyRelation;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "Patient [patientId=" + patientId + ", fname=" + fname + ", lname=" + lname + ", streetAddress="
				+ streetAddress + ", landmark=" + landmark + ", city=" + city + ", state=" + state + ", pin=" + pin
				+ ", dob=" + dob + ", maritalStatus=" + maritalStatus + ", email=" + email + ", password=" + password
				+ ", phone=" + phone + ", medicalHistory=" + medicalHistory + ", familyHistory=" + familyHistory
				+ ", alcoholUse=" + alcoholUse + ", tobaccoUse=" + tobaccoUse + ", emergencyName=" + emergencyName
				+ ", emergencyContact=" + emergencyContact + ", emergencyRelation=" + emergencyRelation + "]";
	}
	
	
	
	
	
	
}
