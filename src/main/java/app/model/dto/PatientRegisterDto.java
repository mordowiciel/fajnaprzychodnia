package app.model.dto;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
@JsonDeserialize
public class PatientRegisterDto {
    public String username;
    public String password;
    public String firstName;
    public String lastName;
    public String email;
    public String pesel;
    public String phoneNumber;
    public Date birthDate;
}
