package app.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.model.Patient;
import app.model.PatientRegisterDto;
import app.model.Prescription;
import app.model.Visit;
import app.model.VisitStatus;
import app.model.security.Authority;
import app.model.security.AuthorityName;
import app.model.security.User;
import app.repository.PatientRepository;
import app.repository.PrescriptionRepository;
import app.repository.VisitRepository;
import app.repository.specification.PrescriptionSpecification;
import app.repository.specification.VisitSpecification;
import app.repository.specification.request.PrescriptionRequest;
import app.repository.specification.request.VisitRequest;
import app.security.JwtUser;
import app.security.repository.AuthorityRepository;
import app.security.repository.UserRepository;

@RestController()
//@RequestMapping(value = "/patient")
public class PatientController {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PrescriptionRepository prescriptionRepository;
    @Autowired
    private PrescriptionSpecification prescriptionSpecification;

    @Autowired
    private VisitRepository visitRepository;
    @Autowired
    private VisitSpecification visitSpecification;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(value = "/patients/allPatients", method = RequestMethod.GET)
    public ResponseEntity allPatients() {
        List<Patient> patients = patientRepository.findAll();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(patients);
    }

    @RequestMapping(value = "/patients/{id}", method = RequestMethod.GET)
    public ResponseEntity getPatientInfo(@PathVariable(value = "id") Integer patientId) {
        Patient patientInfo = patientRepository.findById(patientId).get();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(patientInfo);
    }

    @RequestMapping(value = "/patients/{id}/prescriptions", method = RequestMethod.GET)
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

    @RequestMapping(value = "/patients/{id}/visits", method = RequestMethod.GET)
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

    // TODO: return DTO
    @RequestMapping(path = "/me", method = RequestMethod.GET)
    public ResponseEntity userProfile() {

        JwtUser principalID = (JwtUser) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        User user = new User();
        user.setId(principalID.getId());
        Patient patient = patientRepository.findByUser(user);

        return ResponseEntity.ok(patient.getFirstName());

    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public ResponseEntity registerUser(@RequestBody PatientRegisterDto userRegisterRequest) {

        User userToRegister = new User();
        userToRegister.setUsername(userRegisterRequest.username);
        userToRegister.setFirstname(userRegisterRequest.firstName);
        userToRegister.setLastname(userRegisterRequest.lastName);
        userToRegister.setPassword(passwordEncoder.encode(userRegisterRequest.password));
        userToRegister.setEmail(userRegisterRequest.email);

        List<Authority> userAuthorities = new ArrayList<>();
        userAuthorities.add(authorityRepository.findByName(AuthorityName.ROLE_USER));

        userToRegister.setAuthorities(userAuthorities);
        userToRegister.setEnabled(true);
        userToRegister.setLastPasswordResetDate(new Date());
        userToRegister = userRepository.save(userToRegister);

        Patient patient = new Patient();
        patient.setFirstName(userRegisterRequest.firstName);
        patient.setLastName(userRegisterRequest.lastName);
        patient.setEmail(userRegisterRequest.email);
        patient.setPassword(userRegisterRequest.password);
        patient.setBirthDate(userRegisterRequest.birthDate);
        patient.setPesel(userRegisterRequest.pesel);
        patient.setPhoneNumber(userRegisterRequest.phoneNumber);
        patient.setUserData(userToRegister);

        patientRepository.save(patient);
        return ResponseEntity.ok("Patient created successfully!");
    }
}
