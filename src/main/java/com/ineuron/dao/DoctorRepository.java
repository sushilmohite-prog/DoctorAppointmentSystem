package com.ineuron.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ineuron.models.Doctor;


public interface DoctorRepository extends JpaRepository<Doctor,Integer>{

}
