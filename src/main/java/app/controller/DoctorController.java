package app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.model.Doctor;
import app.repository.DoctorRepository;

@RestController
public class DoctorController {

    @Autowired
    DoctorRepository doctorRepository;

    @RequestMapping(value = "/doctor/all", method = RequestMethod.GET)
    public ResponseEntity allDoctors() {
        List<Doctor> doctors = doctorRepository.findAll();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(doctors);
    }
}