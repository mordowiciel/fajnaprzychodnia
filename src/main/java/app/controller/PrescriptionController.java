package app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.model.entity.Prescription;
import app.repository.PrescriptionRepository;

@RestController
@RequestMapping(value="/prescription")
public class PrescriptionController {

    @Autowired
    PrescriptionRepository prescriptionRepository;

    @RequestMapping(value = "/allVisits", method = RequestMethod.GET)
    public ResponseEntity allPrescriptions() {
        List<Prescription> prescriptions = prescriptionRepository.findAll();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(prescriptions);
    }
}