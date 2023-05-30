package com.ineuron.controller;

import java.security.Principal;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ineuron.dao.AppointmentRepository;
import com.ineuron.dao.DoctorRepository;
import com.ineuron.dao.PatientRepository;
import com.ineuron.helper.Message;
import com.ineuron.models.Appointment;
import com.ineuron.models.Doctor;
import com.ineuron.models.Patient;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/doctor")
public class DoctorController {

	@Autowired
	private DoctorRepository doctorRepository;

	@Autowired
	private AppointmentRepository appointmentRepository;
	
	@Autowired
	private PatientRepository patientRepository;

	// method for adding common data to all handler
	@ModelAttribute
	public void addCommonData(Model model, Principal principal) {
		String username = principal.getName(); // get the user using email Doctor
		Doctor doctor = doctorRepository.getDoctorByEmail(username);
		model.addAttribute("doctor", doctor);
	}

	@RequestMapping("/doctor-dashboard")
	public String docDashboard(@RequestParam(value = "page", defaultValue = "0") Integer page, Model model,
			Principal principal) {
		String username = principal.getName();

		Doctor doctor = doctorRepository.getDoctorByEmail(username);

		int docId = doctor.getDocId();

		LocalDate currentDate = LocalDate.now();
		Pageable pageable = PageRequest.of(page, 10);

		long todayAppointment = appointmentRepository.countByDocIdAndDate(docId, currentDate);
		long totalAppointment = appointmentRepository.countByDocId(docId);
		Page<Appointment> appointments = appointmentRepository.findAppointmentByDocIdAndDate(docId, currentDate,
				pageable);

		model.addAttribute("appointment", appointments);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", appointments.getTotalPages());
		model.addAttribute("today", todayAppointment);
		model.addAttribute("currentDate", currentDate);
		model.addAttribute("totalApp", totalAppointment);
		model.addAttribute("title", "Doctor's Dashboard");
		return "normal/dashboard";
	}

	@GetMapping(value = "/appointment-update")
	public String updateApp(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam("appId") int appId, Model model, Principal principal) {

		Pageable pageable = PageRequest.of(page, 10);
		String username = principal.getName();

		Doctor doctor = doctorRepository.getDoctorByEmail(username);

		int docId = doctor.getDocId();
		LocalDate currentDate = LocalDate.now();
		long todayAppointment = appointmentRepository.countByDocIdAndDate(docId, currentDate);
		long totalAppointment = appointmentRepository.countByDocId(docId);
		Page<Appointment> appointments = appointmentRepository.findAppointmentByDocIdAndDate(docId, currentDate,
				pageable);

		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", appointments.getTotalPages());
		Appointment appointment = appointmentRepository.getAppointmentById(appId);
		appointment.setStatus("Visited");
		this.appointmentRepository.save(appointment);
		model.addAttribute("appointment", appointments);
		model.addAttribute("today", todayAppointment);
		model.addAttribute("currentDate", currentDate);
		model.addAttribute("totalApp", totalAppointment);
		return "normal/dashboard";
	}

	@GetMapping(value = "/display-patient-info")
	public String displayPatientInfo(@RequestParam("patientId") int patientId, Model model) {
		Patient patient = patientRepository.getPatientById(patientId);
		model.addAttribute("patient",patient);
		model.addAttribute("title","Patient Information");
		return "normal/patient_info";
	}
	
	
	@GetMapping(value = "/appointment-delete")
	public String deleteApp(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam("appId") int appId, Model model, Principal principal) {
		appointmentRepository.deleteById(appId);
		Pageable pageable = PageRequest.of(page, 10);
		String username = principal.getName();
		Doctor doctor = doctorRepository.getDoctorByEmail(username);
		int docId = doctor.getDocId();
		LocalDate currentDate = LocalDate.now();
		long todayAppointment = appointmentRepository.countByDocIdAndDate(docId, currentDate);
		long totalAppointment = appointmentRepository.countByDocId(docId);
		Page<Appointment> appointments = appointmentRepository.findAppointmentByDocIdAndDate(docId, currentDate,
				pageable);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", appointments.getTotalPages());
		model.addAttribute("appointment", appointments);
		model.addAttribute("today", todayAppointment);
		model.addAttribute("currentDate", currentDate);
		model.addAttribute("totalApp", totalAppointment);
		return "normal/dashboard";
	}

	@GetMapping(value = "/appointment-reschedule")
	public String rescheduleApp(@RequestParam("appId") int appId, Model model, Principal principal) {
		Appointment appointment = appointmentRepository.getAppointmentById(appId);
		model.addAttribute("appointment", appointment);
		return "normal/appointment_reschedule";
	}

	@PostMapping(value = "/rescheduling-appointment")
	public String updateRescheduleApp(@ModelAttribute("appointment") Appointment appointment, BindingResult result,
			Model model, HttpSession session) {

		try {
			Appointment existingAppointment = appointmentRepository.getAppointmentById(appointment.getAppId());
			if (existingAppointment == null) {

				session.setAttribute("message", new Message("Appointment not found", "alert-danger"));
				return "normal/appointment_reschedule";
			}


			existingAppointment.setDate(appointment.getDate());


			appointmentRepository.save(existingAppointment);

			session.setAttribute("message", new Message("Successfully Rescheduled", "alert-success"));
			return "redirect:doctor-dashboard";
		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("message", new Message("Something Went Wrong", "alert-danger"));
			return "normal/appointment_reschedule";
		}
	}

	@RequestMapping("/patient-list")
	public String patientList(@RequestParam(value = "page", defaultValue = "0") Integer page, Model model,
			Principal principal) {
		Pageable pageable = PageRequest.of(page, 10);
		String username = principal.getName();
		Doctor doctor = doctorRepository.getDoctorByEmail(username);
		int docId = doctor.getDocId();
		Page<Appointment> appointments = appointmentRepository.findAppointmentByDocId(docId, pageable);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", appointments.getTotalPages());
		model.addAttribute("appointment", appointments);
		model.addAttribute("title", "Patient List");
		return "normal/patients";
	}

	@GetMapping("/retrieve-appointments")
	@ResponseBody
	public Page<Appointment> retrieveAppointments(@RequestParam("selectedDate") LocalDate selectedDate,
			@RequestParam(value = "page", defaultValue = "0") Integer page, Principal principal) {
		Pageable pageable = PageRequest.of(page, 10);
		String username = principal.getName();
		Doctor doctor = doctorRepository.getDoctorByEmail(username);
		int docId = doctor.getDocId();
		Page<Appointment> appointments = appointmentRepository.findAppointmentByDocIdAndDate(docId, selectedDate,
				pageable);
		return appointments;
	}

	@RequestMapping("/appointment-list")
	public String appointmentList(@RequestParam(value = "page", defaultValue = "0") Integer page, Model model,
			Principal principal) {
		String username = principal.getName();

		Doctor doctor = doctorRepository.getDoctorByEmail(username);

		int docId = doctor.getDocId();

		Pageable pageable = PageRequest.of(page, 10);

		Page<Appointment> appointments = appointmentRepository.findAppointmentByDocId(docId, pageable);

		model.addAttribute("appointment", appointments);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", appointments.getTotalPages());
		model.addAttribute("title", "Appointments");
		return "normal/appointmentlist";
	}
}
