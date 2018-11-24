package model;

import java.util.Date;
import java.util.List;

public class Visit {

    private int id;
    private Patient patient;
    private Doctor doctor;
    private HealthcareUnit healthcareUnit;
    private Date visitDate;
    private String symptoms;
    private String diagnosis;
    private List<Prescription> prescriptions;
    private VisitStatus visitStatus;

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
