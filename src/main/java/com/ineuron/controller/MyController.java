package com.ineuron.controller;


import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ineuron.dao.AppointmentRepository;
import com.ineuron.dao.DoctorRepository;
import com.ineuron.dao.PatientRepository;
import com.ineuron.helper.Message;

import com.ineuron.models.Doctor;
import com.ineuron.models.Patient;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


@Controller
public class MyController {
	
	@Autowired
	private PatientRepository patientRepository;
	

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private DoctorRepository doctorRepository;
	
	@Autowired
	private AppointmentRepository appointmentRepository;
	



	@RequestMapping("/appointment")
	public String appointment(Model model) {
		List<Doctor> doctor = doctorRepository.findAll();
	    model.addAttribute("doctors", doctor);
	    model.addAttribute("title","Appointments");
		return "appointment";
	}


	
		@RequestMapping("index")
		public String home(Model model) {
			model.addAttribute("title","CareConnect - homepage");
			return "home";
		}
		
		@RequestMapping("/contact")
		public String contact(Model model) {
			model.addAttribute("title","Contact Us");
			return "contact";
		}
		

		
		@RequestMapping("/patient-registration")
		public String patient(Model model){
			model.addAttribute("patient",new Patient());
			model.addAttribute("title","Patient Registration Form");
			return "patientregistration";
		}
		
		//handler for registering patient
		@RequestMapping(value="/patient_register",method= RequestMethod.POST)
		public String registerPatient(@Valid @ModelAttribute(name= "patient") Patient patient ,BindingResult result,Model model,@RequestParam(value="agreement", defaultValue="false") boolean agreement,
				HttpSession session)
		{
						
			try {
					
				if(!agreement) {
					throw new Exception("You have not agreed terms and conditions");
				}
				
		
				if(result.hasErrors()) {
					System.out.println("Error"+result.toString());
					model.addAttribute("patient" , patient);
					return "patientregistration";
				}
				
				
				
				System.out.println("Agreement"+agreement);
				System.out.println("User"+patient);
				
				patient.setRole("ROLE_PATIENT");
				patient.setPassword(bCryptPasswordEncoder.encode(patient.getPassword()));
				patient.setEmail("patient_".concat(patient.getEmail()));
				
				this.patientRepository.save(patient);
				
				model.addAttribute("patient",new Patient());
				
				session.setAttribute("message", new Message("Successfully Registered Your Username is "+patient.getEmail(), "alert-success"));
				return "patientregistration";
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				model.addAttribute("patient",patient);
				session.setAttribute("message", new Message("Something Went Wrong ", "alert-danger"));
				return "patientregistration";
			}
			
		}
		
		
		
		
		@RequestMapping("/doctor-registration")
		public String doctor(Model model) {
			model.addAttribute("doctor",new Doctor());
			model.addAttribute("title","Doctor Registartion Form");
			return "doctorregistration";
		}
		
		
		
		
		//Handler for doctor registration
		@RequestMapping(value="/doctor_register",method= RequestMethod.POST)
		public String registerDoctor(@Valid @ModelAttribute(name= "doctor") Doctor doctor ,BindingResult result,Model model,@RequestParam(value="agreement", defaultValue="false") boolean agreement,
				HttpSession session)
		{
						
			try {
					
				if(!agreement) {
					throw new Exception("You have not agreed terms and conditions");
				}
				
		
				if(result.hasErrors()) {
					System.out.println("Error"+result.toString());
					model.addAttribute("doctor" , doctor);
					return "doctorregistration";
				}
				
				
				
				System.out.println("Agreement"+agreement);
				System.out.println("User"+doctor);
				doctor.setRole("ROLE_DOCTOR");
				doctor.setPassword(bCryptPasswordEncoder.encode(doctor.getPassword()));
				doctor.setEmail("doctor_".concat(doctor.getEmail()));
				
				
				Doctor result1 = this.doctorRepository.save(doctor);
				
				System.out.println(result1);
				
				model.addAttribute("doctor",new Doctor());
				
				session.setAttribute("message", new Message("Successfully Registered"+doctor.getEmail(), "alert-success"));
				return "doctorregistration";
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				model.addAttribute("doctor",doctor);
				session.setAttribute("message", new Message("Something Went Wrong ", "alert-danger"));
				return "doctorregistration";
			}
			
		}
		
		

		@GetMapping("/get-available-appointments")
		@ResponseBody
		public Map<Long, String> getAvailableAppointments(@RequestParam("docId") int docId,
		        @RequestParam("date") LocalDate date) {
		    // Perform the necessary logic to get the number of available appointments
			
			
			long availableAppointments = appointmentRepository.countByDocIdAndDate(docId,date);
		    long remainingAppointments = Math.max(30 - availableAppointments, 0);
			
		    System.out.println(remainingAppointments);	
		    // Create a map to hold the response
		    Map<Long, String> response = new HashMap<>();
		    response.put(remainingAppointments, "availableAppointments");

		    return response;
		}
		
		
		@GetMapping("/search")
		public String searchDoctor(@RequestParam("query") String query, Model model) {
			List<Doctor> doctors = doctorRepository.search(query);
			System.out.println(doctors);
			model.addAttribute("doctors",doctors);
			return "appointment";
		}
		
		
	@RequestMapping("/doctor-list")
	public String docList(Model model) {
		List<Doctor> doctors = doctorRepository.findAll();
		model.addAttribute("doctor",doctors);
		model.addAttribute("title","Doctors list");
		return "doctors";
	}
		
	@RequestMapping("/signin")
	public String signin(Model model){
		model.addAttribute("title","Sign In Form");
		return "signin";
	}
	
}
