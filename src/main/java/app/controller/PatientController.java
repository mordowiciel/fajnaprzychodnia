package app.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.model.Patient;
import app.model.Prescription;
import app.model.Visit;
import app.model.VisitStatus;
import app.repository.PatientRepository;
import app.repository.PrescriptionRepository;
import app.repository.VisitRepository;
import app.repository.specification.PrescriptionSpecification;
import app.repository.specification.VisitSpecification;
import app.repository.specification.request.PrescriptionRequest;
import app.repository.specification.request.VisitRequest;

@RestController()
@RequestMapping(value = "/patient")
public class PatientController {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PrescriptionRepository prescriptionRepository;
    @Autowired
    private PrescriptionSpecification prescriptionSpecification;

    @Autowired
    private VisitRepository visitRepository;
    @Autowired
    private VisitSpecification visitSpecification;

    @RequestMapping(value = "/allPatients", method = RequestMethod.GET)
    public ResponseEntity allPatients() {
        List<Patient> patients = patientRepository.findAll();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(patients);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity getPatientInfo(@PathVariable(value = "id") Integer patientId) {
        Patient patientInfo = patientRepository.findById(patientId).get();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(patientInfo);
    }

    @RequestMapping(value = "/{id}/prescriptions", method = RequestMethod.GET)
    public ResponseEntity getPatientPrescriptions(
            @PathVariable(value = "id") Integer patientId,
            @RequestParam(value = "doctorId", required = false) Integer doctorId,
            @RequestParam(value = "healthcareUnitId", required = false) Integer healthCareUnitId,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @RequestParam(value = "from", required = false) Date from,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @RequestParam(value = "to", required = false) Date to) {

        PrescriptionRequest visitRequest = new PrescriptionRequest(from, to, patientId, doctorId, healthCareUnitId);
        List<Prescription> patientPrescriptions = prescriptionRepository.findAll(prescriptionSpecification
                .getFilter(visitRequest));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(patientPrescriptions);
    }

    @RequestMapping(value = "/{id}/visits", method = RequestMethod.GET)
    public ResponseEntity getPatientVisits(
            @PathVariable(value = "id") Integer patientId,
            @RequestParam(value = "status", required = false) VisitStatus visitStatus,
            @RequestParam(value = "doctorId", required = false) Integer doctorId,
            @RequestParam(value = "healthcareUnitId", required = false) Integer healthCareUnitId,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @RequestParam(value = "from", required = false) Date from,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @RequestParam(value = "to", required = false) Date to) {

        VisitRequest visitRequest = new VisitRequest(from, to, visitStatus, patientId, doctorId, healthCareUnitId);
        List<Visit> prescriptions = visitRepository.findAll(visitSpecification.getFilter(visitRequest));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(prescriptions);
    }
}
