package app.model.dto;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonSerialize
@JsonDeserialize
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class DoctorProfileDto {
    private Long userId;
    private Integer doctorId;
    private Integer healthcareUnitId;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String phoneNumber;
    private String pesel;
    private String specialisation;
}
