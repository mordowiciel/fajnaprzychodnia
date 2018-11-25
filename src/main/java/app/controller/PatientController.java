package app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.model.Patient;
import app.repository.PatientRepository;

@RestController
public class PatientController {

    @Autowired
    PatientRepository patientRepository;

    @RequestMapping(value = "/patient/all", method = RequestMethod.GET)
    public ResponseEntity allDoctors() {
        List<Patient> patients = patientRepository.findAll();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(patients);
    }
}
