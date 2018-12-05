package app.model;

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

    public Visit() {

    }

    public Visit(int id, Patient patient, Doctor doctor, HealthcareUnit healthcareUnit, Date visitDate,
                 String symptoms, String diagnosis, VisitStatus visitStatus) {
        this.id = id;
        this.patient = patient;
        this.doctor = doctor;
        this.healthcareUnit = healthcareUnit;
        this.visitDate = visitDate;
        this.symptoms = symptoms;
        this.diagnosis = diagnosis;
        this.visitStatus = visitStatus;
    }

    public Visit(int id, Patient patient, Doctor doctor, HealthcareUnit healthcareUnit, Date visitDate,
                 String symptoms, String diagnosis, List<Prescription> prescriptions, VisitStatus visitStatus) {
        this.id = id;
        this.patient = patient;
        this.doctor = doctor;
        this.healthcareUnit = healthcareUnit;
        this.visitDate = visitDate;
        this.symptoms = symptoms;
        this.diagnosis = diagnosis;
        this.prescriptions = prescriptions;
        this.visitStatus = visitStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public HealthcareUnit getHealthcareUnit() {
        return healthcareUnit;
    }

    public void setHealthcareUnit(HealthcareUnit healthcareUnit) {
        this.healthcareUnit = healthcareUnit;
    }

    public Date getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public List<Prescription> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(List<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
    }

    public VisitStatus getVisitStatus() {
        return visitStatus;
    }

    public void setVisitStatus(VisitStatus visitStatus) {
        this.visitStatus = visitStatus;
    }
}
