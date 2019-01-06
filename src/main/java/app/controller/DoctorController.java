package app.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.model.DtoMapper;
import app.model.dto.DoctorViewDto;
import app.model.entity.Doctor;
import app.model.entity.HealthcareUnit;
import app.repository.DoctorRepository;
import app.repository.HealthcareUnitRepository;

@RestController()
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private HealthcareUnitRepository healthcareUnitRepository;

    @RequestMapping(value = "/allDoctors", method = RequestMethod.GET)
    public ResponseEntity allDoctors() {
        List<Doctor> doctors = doctorRepository.findAll();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(doctors);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity getDoctorsInHealthcareUnit(@RequestParam(value = "unit") Integer unitId) {

        HealthcareUnit healthcareUnit = healthcareUnitRepository.findById(unitId).get();
        List<DoctorViewDto> doctors = doctorRepository.findByHealthcareUnit(healthcareUnit)
                .stream()
                .map(DtoMapper::map)
                .collect(Collectors.toList());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(doctors);
    }
}