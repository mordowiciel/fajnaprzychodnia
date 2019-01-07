package app.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AllArgsConstructor;
import lombok.Data;

@JsonSerialize
@JsonDeserialize
@AllArgsConstructor
@Data
public class UserUpdatedResponseDto {
    private String newToken;
}
