package app.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonSerialize
@JsonDeserialize
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdatePasswordDto {
    private String newPassword;
}
