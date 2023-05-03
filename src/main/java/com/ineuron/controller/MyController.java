package com.ineuron.controller;


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
	
		@RequestMapping("index")
		public String home() {
			return "home";
		}
		
		@RequestMapping("/contact")
		public String contact() {
			return "contact";
		}
		
		@RequestMapping("/appointment")
			public String appointment() {
				return "appointment";
			}
		
		@RequestMapping("/dashboard")
		public String dashboard() {
			return "dashboard";
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
				
				patient.setRole("USER");
				patient.setPassword(bCryptPasswordEncoder.encode(patient.getPassword()));
				
				this.patientRepository.save(patient);
				
				model.addAttribute("patient",new Patient());
				
				session.setAttribute("message", new Message("Successfully Registered", "alert-success"));
				return "patientregistration";
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				model.addAttribute("patient",patient);
				session.setAttribute("message", new Message("Something Went Wrong "+e.getMessage(), "alert-danger"));
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
				
				
				Doctor result1 = this.doctorRepository.save(doctor);
				
				System.out.println(result1);
				
				model.addAttribute("doctor",new Doctor());
				
				session.setAttribute("message", new Message("Successfully Registered", "alert-success"));
				return "doctorregistration";
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				model.addAttribute("doctor",doctor);
				session.setAttribute("message", new Message("Something Went Wrong "+e.getMessage(), "alert-danger"));
				return "doctorregistration";
			}
			
		}
		
		
		//handler for login
		@GetMapping("/login")
		public  String customLogin(Model model)
		{
			model.addAttribute("title","Login Page");
			return "login";
		}
		
		
}
