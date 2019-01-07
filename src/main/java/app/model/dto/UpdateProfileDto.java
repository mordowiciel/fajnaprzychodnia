package app.model.dto;

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
public class UpdateProfileDto {
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
}
