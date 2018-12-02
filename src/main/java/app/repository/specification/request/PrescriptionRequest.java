package app.repository.specification.request;

import java.util.Date;

public class PrescriptionRequest {

    private Date from;
    private Date to;
    private Integer patientId;
    private Integer doctorId;
    private Integer healthcareUnitId;

    public PrescriptionRequest(Date from, Date to, Integer patientId, Integer doctorId, Integer healthcareUnitId) {
        this.from = from;
        this.to = to;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.healthcareUnitId = healthcareUnitId;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    public Integer getHealthcareUnitId() {
        return healthcareUnitId;
    }

    public void setHealthcareUnitId(Integer healthcareUnitId) {
        this.healthcareUnitId = healthcareUnitId;
    }
}
