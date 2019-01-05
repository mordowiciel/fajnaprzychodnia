package app.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonSerialize
@JsonDeserialize
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CancelVisitDto {
    private int visitId;
}
