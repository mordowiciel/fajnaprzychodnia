package app.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@JsonSerialize
@JsonDeserialize
@AllArgsConstructor
@NoArgsConstructor
public class HealthcareUnitDto {
    private int id;
    private String unitName;
    private String address;
    private String postalCode;
    private String city;
}
