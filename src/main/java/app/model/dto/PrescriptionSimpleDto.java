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
@JsonSerialize
@JsonDeserialize
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionSimpleDto {
    private int id;
    private Integer patientId;
    private Integer doctorId;
    private Integer healthcareUnitId;
    private Date dateOfIssue;
    private Date expirationDate;
    private String content;
}
