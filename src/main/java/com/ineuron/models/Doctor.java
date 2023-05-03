package com.ineuron.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="Doctor")
public class Doctor {
	@Id
	@GeneratedValue(generator="doctor_gen",strategy=GenerationType.AUTO)
	@SequenceGenerator(name="doctor_gen",sequenceName="doctor_seq",initialValue=100000,allocationSize=100)
	private int docId;
	
	@NotBlank(message= "Cannot be empty")
	private String registrationType;
	
	
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
	

	@NotBlank(message="Pin Code Required !!")
	private String pin;
	
	@NotBlank(message="Please enter Date of Birth !!")
	private String dob;
	
	
	@NotBlank(message="Please select your qualification !!")
	private String qualification;
	
	@NotBlank(message="Please select your specialiazation !!")
	private String specialiazation;
	
	@Column(unique= true)
	@NotBlank(message="Email required !!")
	@Email(regexp="^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$" ,message="Enter valid email !!")
	private String email;
	
	@NotBlank(message="Password should not be blank !!")
	@Pattern(regexp="(?!^[0-9]*$)(?!^[a-zA-Z]*$)^([a-zA-Z0-9]{6,15})$" ,message="The match must be alphanumeric with at least one number, one letter, and be between 6-15 character in length.!! ")
	private String password;
	
	@NotBlank(message="Please enter your contact !!")
	@Column(unique= true)
	@Pattern(regexp="^[89][0-9]{9}" ,message="Please enter valid phone number !!")
	private String phone;
	
	
	private String image;
	
	@OneToMany(cascade = CascadeType.ALL,fetch=FetchType.LAZY)
	private List<Appointment> appointments = new ArrayList<>();
	
	
	
	
	public Doctor() {
		super();
	}
	
	
	public int getDocId() {
		return docId;
	}
	public void setDocId(int docId) {
		this.docId = docId;
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
	public String getRegistrationType() {
		return registrationType;
	}
	public void setRegistrationType(String registrationType) {
		this.registrationType = registrationType;
	}
	public String getQualification() {
		return qualification;
	}
	public void setQualification(String qualification) {
		this.qualification = qualification;
	}
	public String getSpecialiazation() {
		return specialiazation;
	}
	public void setSpecialiazation(String specialiazation) {
		this.specialiazation = specialiazation;
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
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public List<Appointment> getAppointments() {
		return appointments;
	}
	public void setAppointments(List<Appointment> appointments) {
		this.appointments = appointments;
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


	@Override
	public String toString() {
		return "Doctor [docId=" + docId + ", registrationType=" + registrationType + ", fname=" + fname + ", lname="
				+ lname + ", streetAddress=" + streetAddress + ", landmark=" + landmark + ", city=" + city + ", state="
				+ state + ", pin=" + pin + ", dob=" + dob + ", qualification=" + qualification + ", specialiazation="
				+ specialiazation + ", email=" + email + ", password=" + password + ", phone=" + phone + ", image="
				+ image + ", appointments=" + appointments + "]";
	}
	
	
	
	
	
}
