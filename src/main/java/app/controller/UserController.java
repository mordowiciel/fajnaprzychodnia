package app.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.model.VisitStatus;
import app.model.dto.CancelVisitDto;
import app.model.dto.RegisterVisitDto;
import app.model.entity.Doctor;
import app.model.entity.HealthcareUnit;
import app.model.entity.Patient;
import app.model.entity.Visit;
import app.model.security.User;
import app.repository.DoctorRepository;
import app.repository.HealthcareUnitRepository;
import app.repository.PatientRepository;
import app.repository.VisitRepository;
import app.security.JwtUser;

@RestController
@RequestMapping("/me")
public class UserController {

    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private HealthcareUnitRepository healthcareUnitRepository;
    @Autowired
    private VisitRepository visitRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity userProfile() {

        JwtUser principalID = (JwtUser) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        User user = new User();
        user.setId(principalID.getId());
        Patient patient = patientRepository.findByUser(user);
        return ResponseEntity.ok(patient.getFirstName());
    }

    @RequestMapping(value = "/visit/add", method = RequestMethod.POST)
    public ResponseEntity arrangeVisit(@RequestBody RegisterVisitDto registerVisitDto) {

        JwtUser principal = (JwtUser) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        User user = new User();
        user.setId(principal.getId());

        // TODO: tutaj zabezpieczenie - user moze dodac wizyte jedynie sobie
        Patient patient = patientRepository.findByUser(user);
        Doctor doctor = doctorRepository.findById(registerVisitDto.getDoctorId()).get();
        HealthcareUnit healthcareUnit = healthcareUnitRepository.findById(registerVisitDto.getHealthcareUnitId()).get();

        Visit visit = Visit.builder()
                .patient(patient)
                .doctor(doctor)
                .healthcareUnit(healthcareUnit)
                .visitDate(registerVisitDto.getVisitDate())
                .symptoms(registerVisitDto.getSymptoms())
                .visitStatus(VisitStatus.AWAITING)
                .build();

        visitRepository.save(visit);
        return ResponseEntity.ok("Visit saved!");
    }

    @RequestMapping(value = "/visit/cancel", method = RequestMethod.POST)
    public ResponseEntity cancelVisit(@RequestBody CancelVisitDto cancelVisitDto) {

        JwtUser principal = (JwtUser) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        User user = new User();
        user.setId(principal.getId());

        Patient patient = patientRepository.findByUser(user);
        List<Integer> patientVisitsIds = visitRepository.findByPatient(patient)
                .stream()
                .map(Visit::getId)
                .collect(Collectors.toList());

        if (patientVisitsIds.contains(cancelVisitDto.getVisitId())) {
            Visit visitToCancel = visitRepository.findById(cancelVisitDto.getVisitId()).get();
            visitToCancel.setVisitStatus(VisitStatus.CANCELLED);
            visitRepository.save(visitToCancel);
        }
        else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Cannot cancel visit which isn't assigned to user");
        }

        return ResponseEntity.ok("Visit cancelled!");
    }
}
