package app.model;

import java.util.Date;

public class Prescription {

    private int id;
    private Patient patient;
    private Doctor doctor;
    private HealthcareUnit healthcareUnit;
    private Date dateOfIssue;
    private Date expirationDate;
    private String content;

    public Prescription(int id, Patient patient, Doctor doctor, HealthcareUnit healthcareUnit,
                        Date dateOfIssue, Date expirationDate, String content) {
        this.id = id;
        this.patient = patient;
        this.doctor = doctor;
        this.healthcareUnit = healthcareUnit;
        this.dateOfIssue = dateOfIssue;
        this.expirationDate = expirationDate;
        this.content = content;
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

    public Date getDateOfIssue() {
        return dateOfIssue;
    }

    public void setDateOfIssue(Date dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
