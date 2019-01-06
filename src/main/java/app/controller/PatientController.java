package app.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.model.DtoMapper;
import app.model.VisitStatus;
import app.model.dto.PatientRegisterDto;
import app.model.dto.PatientViewDto;
import app.model.dto.PrescriptionDto;
import app.model.dto.VisitDto;
import app.model.entity.Patient;
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
import app.security.repository.AuthorityRepository;
import app.security.repository.UserRepository;

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

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthorityRepository authorityRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity getPatientInfo(@PathVariable(value = "id") Integer patientId) {
        Patient patientInfo = patientRepository.findById(patientId).get();
        PatientViewDto patientViewDto = DtoMapper.map(patientInfo);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(patientViewDto);
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
        List<PrescriptionDto> patientPrescriptionDtos =
                prescriptionRepository.findAll(prescriptionSpecification.getFilter(visitRequest)).stream()
                        .map(DtoMapper::map)
                        .collect(Collectors.toList());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(patientPrescriptionDtos);
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
        List<VisitDto> visitDtos = visitRepository.findAll(visitSpecification.getFilter(visitRequest))
                .stream()
                .map(DtoMapper::map)
                .collect(Collectors.toList());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(visitDtos);
    }

//    // TODO: return DTO
//    @RequestMapping(path = "/me", method = RequestMethod.GET)
//    public ResponseEntity userProfile() {
//
//        JwtUser principalID = (JwtUser) SecurityContextHolder.getContext()
//                .getAuthentication()
//                .getPrincipal();
//
//        User user = new User();
//        user.setId(principalID.getId());
//        Patient patient = patientRepository.findByUser(user);
//        return ResponseEntity.ok(patient.getFirstName());
//
//    }

    // TODO: use builder
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
        patient.setUser(userToRegister);

        patientRepository.save(patient);
        return ResponseEntity.ok("Patient created successfully!");
    }
}
