package app.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import app.model.security.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="doctor")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name="birth_date")
    private Date birthDate;
    @Column(name="pesel")
    private String pesel;
    @Column(name="phone_number")
    private String phoneNumber;
    @Column(name="specialisation")
    private String specialisation;

    @OneToOne
    private HealthcareUnit healthcareUnit;
    @OneToOne
    private User user;
}
