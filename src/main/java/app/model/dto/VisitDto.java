package app.model.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import app.model.VisitStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@JsonSerialize
@JsonDeserialize
@NoArgsConstructor
@AllArgsConstructor
public class VisitDto {
    private int id;
    private PatientViewDto patient;
    private DoctorViewDto doctor;
    private HealthcareUnitDto healthcareUnit;
    private LocalDateTime visitDate;
    private String symptoms;
    private String diagnosis;
    private List<PrescriptionSimpleDto> prescriptions;
    private VisitStatus visitStatus;
}
