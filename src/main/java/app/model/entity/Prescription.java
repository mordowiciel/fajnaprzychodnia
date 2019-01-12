package app.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "prescription")
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @OneToOne
    private Patient patient;
    @OneToOne
    private Doctor doctor;
    @OneToOne
    private HealthcareUnit healthcareUnit;
    @Column(name="date_of_issue")
    private Date dateOfIssue;
    @Column(name="date_of_expiration")
    private Date expirationDate;
    @Column(name= "content")
    private String content;

}
