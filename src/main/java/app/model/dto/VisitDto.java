package app.model.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonSerialize
@JsonDeserialize
public class VisitDto {
    private int id;
    private PatientViewDto patient;
    private DoctorViewDto doctor;
    private HealthcareUnitDto healthcareUnit;
    private Date visitDate;
    private String symptoms;
    private String diagnosis;
    private List<PrescriptionDto> prescriptions;
}
