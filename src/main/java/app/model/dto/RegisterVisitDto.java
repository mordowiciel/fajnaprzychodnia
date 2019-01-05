package app.model.dto;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonSerialize
@JsonDeserialize
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterVisitDto {
    private int doctorId;
    private int healthcareUnitId;
    private Date visitDate;
    private String symptoms;
}
