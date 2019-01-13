package app.model.dto;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonSerialize
@JsonDeserialize
public class PrescriptionDto {
    private int id;
    private PatientViewDto patient;
    private DoctorViewDto doctor;
    private HealthcareUnitDto healthcareUnit;
    private Date dateOfIssue;
    private Date expirationDate;
    private String content;
}
