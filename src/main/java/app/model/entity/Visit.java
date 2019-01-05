package app.model.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import app.model.VisitStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="visit")
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    @OneToOne
    private Patient patient;
    @OneToOne
    private Doctor doctor;
    @OneToOne
    private HealthcareUnit healthcareUnit;
    @Column(name = "date")
    private Date visitDate;
    @Column(name = "symptoms")
    private String symptoms;
    @Column(name = "diagnosis")
    private String diagnosis;

    @ManyToMany
    @JoinTable(name = "visit_prescription",
            joinColumns=@JoinColumn(name="visit_id", referencedColumnName="id"),
            inverseJoinColumns=@JoinColumn(name="prescription_id", referencedColumnName="id"))
    private List<Prescription> prescriptions;

    @Enumerated(EnumType.STRING)
    @Column(name="status")
    private VisitStatus visitStatus;
}
