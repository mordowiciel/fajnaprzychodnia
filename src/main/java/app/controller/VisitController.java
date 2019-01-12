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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.model.DtoMapper;
import app.model.VisitStatus;
import app.model.dto.CancelVisitDto;
import app.model.dto.RegisterVisitDto;
import app.model.dto.VisitDto;
import app.model.entity.Doctor;
import app.model.entity.HealthcareUnit;
import app.model.entity.Patient;
import app.model.entity.Prescription;
import app.model.entity.Visit;
import app.model.security.AuthorityName;
import app.model.security.User;
import app.repository.DoctorRepository;
import app.repository.HealthcareUnitRepository;
import app.repository.PatientRepository;
import app.repository.PrescriptionRepository;
import app.repository.VisitRepository;
import app.repository.specification.VisitSpecification;
import app.repository.specification.request.VisitRequest;
import app.security.JwtUser;

@RestController
@RequestMapping("/visit")
public class VisitController {

    private SimpleGrantedAuthority patientAuthority = new SimpleGrantedAuthority(AuthorityName.ROLE_USER.toString());
    private SimpleGrantedAuthority doctorAuthority = new SimpleGrantedAuthority(AuthorityName.ROLE_ADMIN.toString());

    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private HealthcareUnitRepository healthcareUnitRepository;
    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private VisitRepository visitRepository;
    @Autowired
    private VisitSpecification visitSpecification;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity allVisits() {
        List<Visit> visits = visitRepository.findAll();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(visits);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity getVisitDetails(@PathVariable(value = "id") Integer id) {

        JwtUser principal = (JwtUser) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        Collection<? extends GrantedAuthority> authorities = principal.getAuthorities();
        User user = new User();
        user.setId(principal.getId());

        if (authorities.contains(patientAuthority)) {
            Patient patient = patientRepository.findByUser(user);
            Visit requestedVisit = visitRepository.findById(id).get();
            List<Visit> patientVisits = visitRepository.findByPatient(patient);

            if (patientVisits.contains(requestedVisit)) {
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(DtoMapper.map(requestedVisit));
            } else {
                return ResponseEntity
                        .status(HttpStatus.FORBIDDEN)
                        .body("Cannot return visit details which are not assigned to authenticated patient");
            }
        }
        else if (authorities.contains(doctorAuthority)) {
            VisitDto visitDto = DtoMapper.map(visitRepository.findById(id).get());
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(visitDto);
        }

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body("No proper authority found");
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity search(@RequestParam(value = "status", required = false) VisitStatus visitStatus,
                                 @RequestParam(value = "patientId", required = false) Integer patientId,
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

        VisitRequest visitRequest = new VisitRequest(from, to, visitStatus, patientId, doctorId, healthCareUnitId);
        List<VisitDto> visits = visitRepository.findAll(visitSpecification.getFilter(visitRequest))
                .stream()
                .map(DtoMapper::map)
                .collect(Collectors.toList());


        if (authorities.contains(patientAuthority)) {
            Patient patient = patientRepository.findByUser(user);
            visits = visits.stream()
                    .filter(visit -> !(visit.getPatient().getId() == patient.getId()))
                    .collect(Collectors.toList());
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(visits);
        } else if (authorities.contains(doctorAuthority)) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(visits);
        }

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body("No proper authority found.");

    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity arrangeVisit(@RequestBody RegisterVisitDto registerVisitDto) {

        JwtUser principal = (JwtUser) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        User user = new User();
        user.setId(principal.getId());

        Patient patient;
        Collection<? extends GrantedAuthority> authorities = principal.getAuthorities();
        if (authorities.contains(patientAuthority)) {
            patient = patientRepository.findByUser(user);
        } else {
            patient = patientRepository.findById(registerVisitDto.getPatientId()).get();
        }

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

        visit = visitRepository.save(visit);
        return ResponseEntity.ok(DtoMapper.map(visit));
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity finishVisit(@RequestBody VisitDto visitFinishRequest) {

        JwtUser principal = (JwtUser) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        User user = new User();
        user.setId(principal.getId());

        Collection<? extends GrantedAuthority> authorities = principal.getAuthorities();
        if (authorities.contains(doctorAuthority)) {

            // TODO: move to mapper
            List<Prescription> prescriptionEntities = visitFinishRequest.getPrescriptions().stream()
                    .map(prescriptionDto -> {

                        Patient patientEntity = patientRepository.findById(prescriptionDto.getPatientId()).get();
                        Doctor doctorEntity = doctorRepository.findById(prescriptionDto.getDoctorId()).get();
                        HealthcareUnit healthcareUnitEntity = healthcareUnitRepository.findById(
                                prescriptionDto.getHealthcareUnitId()).get();

                        return Prescription.builder()
                                .id(prescriptionDto.getId())
                                .patient(patientEntity)
                                .doctor(doctorEntity)
                                .healthcareUnit(healthcareUnitEntity)
                                .dateOfIssue(prescriptionDto.getDateOfIssue())
                                .expirationDate(prescriptionDto.getExpirationDate())
                                .content(prescriptionDto.getContent())
                                .build();
                    })
                    .collect(Collectors.toList());
            prescriptionRepository.saveAll(prescriptionEntities);

            Visit visitToFinish = visitRepository.findById(visitFinishRequest.getId()).get();
            visitToFinish.setSymptoms(visitFinishRequest.getSymptoms());
            visitToFinish.setDiagnosis(visitFinishRequest.getDiagnosis());
            visitToFinish.setPrescriptions(prescriptionEntities);
            visitToFinish.setVisitStatus(visitFinishRequest.getVisitStatus());
            visitToFinish = visitRepository.save(visitToFinish);

            return ResponseEntity.ok(DtoMapper.map(visitToFinish));
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body("No proper authority found");
    }

    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    public ResponseEntity cancelVisit(@RequestBody CancelVisitDto cancelVisitDto) {

        JwtUser principal = (JwtUser) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        User user = new User();
        user.setId(principal.getId());

        Collection<? extends GrantedAuthority> authorities = principal.getAuthorities();
        if (authorities.contains(patientAuthority)) {

            Patient patient = patientRepository.findByUser(user);
            List<Integer> patientVisitsIds = visitRepository.findByPatient(patient)
                    .stream()
                    .map(Visit::getId)
                    .collect(Collectors.toList());

            if (patientVisitsIds.contains(cancelVisitDto.getVisitId())) {
                Visit visitToCancel = visitRepository.findById(cancelVisitDto.getVisitId()).get();
                visitToCancel.setVisitStatus(VisitStatus.CANCELLED);
                visitToCancel = visitRepository.save(visitToCancel);
                return ResponseEntity.ok(DtoMapper.map(visitToCancel));
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("Cannot cancel visit which isn't assigned to user");
            }
        } else if (authorities.contains(doctorAuthority)) {
            Visit visitToCancel = visitRepository.findById(cancelVisitDto.getVisitId()).get();
            visitToCancel.setVisitStatus(VisitStatus.CANCELLED);
            visitToCancel = visitRepository.save(visitToCancel);
            return ResponseEntity.ok(DtoMapper.map(visitToCancel));
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No proper authority found");
    }


}
