package app.controller;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.model.DtoMapper;
import app.model.dto.PrescriptionDto;
import app.model.entity.Patient;
import app.model.entity.Prescription;
import app.model.security.AuthorityName;
import app.model.security.User;
import app.repository.PatientRepository;
import app.repository.PrescriptionRepository;
import app.repository.specification.PrescriptionSpecification;
import app.repository.specification.request.PrescriptionRequest;
import app.security.JwtUser;

@RestController
@RequestMapping(value="/prescription")
public class PrescriptionController {

    private SimpleGrantedAuthority patientAuthority = new SimpleGrantedAuthority(AuthorityName.ROLE_USER.toString());
    private SimpleGrantedAuthority doctorAuthority = new SimpleGrantedAuthority(AuthorityName.ROLE_ADMIN.toString());

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PrescriptionRepository prescriptionRepository;
    @Autowired
    private PrescriptionSpecification prescriptionSpecification;

    @RequestMapping(value = "/allPrescriptions", method = RequestMethod.GET)
    public ResponseEntity allPrescriptions() {
        List<Prescription> prescriptions = prescriptionRepository.findAll();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(prescriptions);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity search(@RequestParam(value = "patientId", required = false) Integer patientId,
                                 @RequestParam(value = "doctorId", required = false) Integer doctorId,
                                 @RequestParam(value = "healthcareUnitId", required = false) Integer healthCareUnitId,
                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                 @RequestParam(value = "from", required = false) Date from,
                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                 @RequestParam(value = "to", required = false) Date to) {

        JwtUser principal = (JwtUser) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        Collection<? extends GrantedAuthority> authorities = principal.getAuthorities();
        User user = new User();
        user.setId(principal.getId());

        PrescriptionRequest visitRequest = PrescriptionRequest.builder()
                .from(from)
                .to(to)
                .patientId(patientId)
                .doctorId(doctorId)
                .healthcareUnitId(healthCareUnitId)
                .build();

        List<PrescriptionDto> prescriptions = prescriptionRepository.findAll(prescriptionSpecification.getFilter(visitRequest))
                .stream()
                .map(DtoMapper::map)
                .collect(Collectors.toList());

        if (authorities.contains(patientAuthority)) {
            Patient patient = patientRepository.findByUser(user);
            prescriptions = prescriptions.stream()
                    .filter(visit -> !(visit.getPatient().getId() == patient.getId()))
                    .collect(Collectors.toList());
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(prescriptions);
        } else if (authorities.contains(doctorAuthority)) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(prescriptions);
        }

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body("No proper authority found.");

    }
}