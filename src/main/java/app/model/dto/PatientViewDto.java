package app.model.dto;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
@JsonDeserialize
public class PatientViewDto {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String pesel;
    private String phoneNumber;
    private Date birthDate;

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPesel() {
        return pesel;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Date getBirthDate() {
        return birthDate;
    }


    public static final class PatientViewDtoBuilder {
        private int id;
        private String firstName;
        private String lastName;
        private String email;
        private String pesel;
        private String phoneNumber;
        private Date birthDate;

        private PatientViewDtoBuilder() {
        }

        public static PatientViewDtoBuilder builder() {
            return new PatientViewDtoBuilder();
        }

        public PatientViewDtoBuilder withId(int id) {
            this.id = id;
            return this;
        }

        public PatientViewDtoBuilder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public PatientViewDtoBuilder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public PatientViewDtoBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public PatientViewDtoBuilder withPesel(String pesel) {
            this.pesel = pesel;
            return this;
        }

        public PatientViewDtoBuilder withPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public PatientViewDtoBuilder withBirthDate(Date birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        public PatientViewDto build() {
            PatientViewDto patientViewDto = new PatientViewDto();
            patientViewDto.lastName = this.lastName;
            patientViewDto.id = this.id;
            patientViewDto.pesel = this.pesel;
            patientViewDto.firstName = this.firstName;
            patientViewDto.phoneNumber = this.phoneNumber;
            patientViewDto.birthDate = this.birthDate;
            patientViewDto.email = this.email;
            return patientViewDto;
        }
    }
}
