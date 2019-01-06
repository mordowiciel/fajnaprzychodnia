package app.repository.specification.request;

import java.util.Date;

import app.model.VisitStatus;

public class VisitRequest {
    private Date from;
    private Date to;
    private VisitStatus visitStatus;
    private Integer patientId;
    private Integer doctorId;
    private Integer healthcareUnitId;

    public VisitRequest(Date from, Date to, VisitStatus visitStatus, Integer patientId,
                        Integer doctorId, Integer healthcareUnitId) {
        this.from = from;
        this.to = to;
        this.visitStatus = visitStatus;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.healthcareUnitId = healthcareUnitId;
    }

    public Date getFrom() {
        return from;
    }

    public Date getTo() {
        return to;
    }

    public VisitStatus getVisitStatus() {
        return visitStatus;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public Integer getDoctorId() {
        return doctorId;
    }

    public Integer getHealthcareUnitId() {
        return healthcareUnitId;
    }
}
