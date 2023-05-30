package com.ineuron.controller;



import java.security.Principal;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.ineuron.dao.AppointmentRepository;
import com.ineuron.dao.DoctorRepository;
import com.ineuron.dao.PatientRepository;
import com.ineuron.helper.Message;
import com.ineuron.models.Appointment;
import com.ineuron.models.Doctor;
import com.ineuron.models.Patient;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


@Controller
@RequestMapping("/patient")
public class PatientController {
		
	@Autowired
	private PatientRepository patientRepository; 
	
	@Autowired
	private DoctorRepository doctorRepository;
	
	@Autowired
	private AppointmentRepository appointmentRepository;
	
	
	@ModelAttribute
	public void addCommonData(Model model,Principal principal) {
		 String username = principal.getName(); //get the user using email Doctor
		 Patient patient = patientRepository.getPatientByEmail(username);
		 model.addAttribute("patient",patient);
	}
	
	@GetMapping(value = "/appointment-delete")
	public String deleteApp(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam("appId") int appId, Model model, Principal principal) {
		appointmentRepository.deleteById(appId);
		
		Pageable pageable = PageRequest.of(page, 5);
		String username = principal.getName();
		Page<Appointment> appointments = appointmentRepository.findAppointmentByEmail(username,pageable);
		
		model.addAttribute("message","Appointment Cancelled");	
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", appointments.getTotalPages());
		model.addAttribute("appointment", appointments);
		return "normal/patient_dashboard";
	}
	
	@GetMapping("/search")
	public String searchDoctor(@RequestParam("query") String query, Model model) {
		List<Doctor> doctors = doctorRepository.search(query);
		System.out.println(doctors);
		model.addAttribute("doctors",doctors);
		return "normal/patient_appointment_request";
	}
	
	
	@RequestMapping("/patient-appointment-request")
	public String appointmentRequest(Model model,Principal principal) {
	
		List<Doctor> doctors = doctorRepository.findAll();
	
	    model.addAttribute("doctors", doctors);
	    model.addAttribute("title","Book Appointment");
		return "normal/patient_appointment_request";
	}
	

	@RequestMapping(value="/appointment_register",method= RequestMethod.POST)
	public String appointmentregister(@Valid @ModelAttribute(name= "appointment") Appointment appointment ,BindingResult result,Model model,
			HttpSession session) {
		
		try {
			
		
			if(result.hasErrors()) {
				System.out.println("Error"+result.toString());
				model.addAttribute("appointment" , appointment);
				return "normal/patient_appointment_request";
			}
		
			
			appointment.setStatus("Not Visited");
			
			this.appointmentRepository.save(appointment);
			
			model.addAttribute("appointment",new Appointment());
			
			session.setAttribute("message", new Message("Appointment Booked", "alert-success"));
			return "redirect:patient_app_success";
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			model.addAttribute("appointment",appointment);
			session.setAttribute("message", new Message("Something Went Wrong ", "alert-danger"));
			return "redirect:patient_appointment_request";
		}
	}
		
	@RequestMapping("/patient_app_success")
	public String success(Model model, Principal principal) {
		return "normal/patient_app_success";
	}
	
	
	@RequestMapping("/patient-user-setup")
	public String patientSettings(Model model,Principal principal) {
		model.addAttribute("title","Patient details");
		return "normal/patient_user_setup";
	}
	
	@RequestMapping(value="/update_user_patient",method= RequestMethod.POST)
	public String updateUserPatient( @ModelAttribute(name= "patient") Patient patient ,BindingResult result,Model model,@RequestParam(value="agreement", defaultValue="false") boolean agreement,
			HttpSession session) {
		try {	
		this.patientRepository.save(patient);
		session.setAttribute("message", new Message("Successfully Updated", "alert-success"));
		return "normal/patient_user_setup";
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			session.setAttribute("message", new Message("Something Went Wrong ", "alert-danger"));
			return "normal/patient_user_setup";
		}
		
	}
	
	@RequestMapping("/appointment-request")
	public String requestAppointment(@RequestParam("docId") String docId,Model model, Principal principal) {
		
		 Doctor doctor = doctorRepository.getDoctorById(docId);
		  //get the user using email Doctor
		 model.addAttribute("doctor",doctor);
		 model.addAttribute("appointment",new Appointment());
		 model.addAttribute("title","Appointment Request Form");
		return "normal/appointment_request_form";
	}
	
	@RequestMapping("/patient-dashboard")
	public String patientDashboard(@RequestParam(value = "page", defaultValue = "0") Integer page,Model model, Principal principal) {
		String username = principal.getName();
	
		Pageable pageable = PageRequest.of(page, 5);
		
		
		Page<Appointment> appointments = appointmentRepository.findAppointmentByEmail(username,pageable);
		
	
		model.addAttribute("appointment",appointments);
		model.addAttribute("currentPage",page);
		model.addAttribute("totalPages",appointments.getTotalPages());
		model.addAttribute("title","Patient's Dashboard");
		return "normal/patient_dashboard";
	}
	
	
}
