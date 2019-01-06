package app.model;

import java.util.List;
import java.util.stream.Collectors;

import app.model.dto.DoctorViewDto;
import app.model.dto.HealthcareUnitDto;
import app.model.dto.PatientViewDto;
import app.model.dto.PrescriptionDto;
import app.model.dto.PrescriptionSimpleDto;
import app.model.dto.VisitDto;
import app.model.entity.Doctor;
import app.model.entity.HealthcareUnit;
import app.model.entity.Patient;
import app.model.entity.Prescription;
import app.model.entity.Visit;

public class DtoMapper {

    public static PatientViewDto map(Patient patient) {
        return PatientViewDto.PatientViewDtoBuilder.builder()
                .withId(patient.getId())
                .withFirstName(patient.getUser().getFirstname())
                .withLastName(patient.getUser().getLastname())
                .withEmail(patient.getUser().getEmail())
                .withBirthDate(patient.getBirthDate())
                .withPesel(patient.getPesel())
                .withPhoneNumber(patient.getPhoneNumber())
                .build();
    }

    public static DoctorViewDto map(Doctor doctor) {
        return DoctorViewDto.builder()
                .id(doctor.getId())
                .firstName(doctor.getFirstName())
                .lastName(doctor.getLastName())
                .email(doctor.getEmail())
                .phoneNumber(doctor.getPhoneNumber())
                .specialisation(doctor.getSpecialisation())
                .build();
    }

    public static HealthcareUnitDto map(HealthcareUnit healthcareUnit) {
        return HealthcareUnitDto.builder()
                .id(healthcareUnit.getId())
                .unitName(healthcareUnit.getUnitName())
                .address(healthcareUnit.getAddress())
                .postalCode(healthcareUnit.getPostalCode())
                .city(healthcareUnit.getCity())
                .build();
    }

    public static PrescriptionDto map(Prescription prescription) {
        return PrescriptionDto.builder()
                .id(prescription.getId())
                .patient(map(prescription.getPatient()))
                .doctor(map(prescription.getDoctor()))
                .healthcareUnit(map(prescription.getHealthcareUnit()))
                .dateOfIssue(prescription.getDateOfIssue())
                .expirationDate(prescription.getExpirationDate())
                .content(prescription.getContent())
                .build();
    }

    public static PrescriptionSimpleDto map(PrescriptionDto prescriptionDto) {
        return PrescriptionSimpleDto.builder()
                .id(prescriptionDto.getId())
                .patientId(prescriptionDto.getPatient().getId())
                .doctorId(prescriptionDto.getDoctor().getId())
                .healthcareUnitId(prescriptionDto.getHealthcareUnit().getId())
                .dateOfIssue(prescriptionDto.getDateOfIssue())
                .expirationDate(prescriptionDto.getExpirationDate())
                .content(prescriptionDto.getContent())
                .build();
    }

    public static VisitDto map(Visit visit) {

        List<PrescriptionSimpleDto> visitPrescriptions = visit.getPrescriptions()
                .stream()
                .map(DtoMapper::map)
                .map(DtoMapper::map)
                .collect(Collectors.toList());

        return VisitDto.builder()
                .id(visit.getId())
                .patient(map(visit.getPatient()))
                .doctor(map(visit.getDoctor()))
                .healthcareUnit(map(visit.getHealthcareUnit()))
                .visitDate(visit.getVisitDate())
                .symptoms(visit.getSymptoms())
                .diagnosis(visit.getDiagnosis())
                .prescriptions(visitPrescriptions)
                .build();
    }
}
