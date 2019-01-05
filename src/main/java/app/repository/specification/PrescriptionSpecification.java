package app.repository.specification;

import static org.springframework.data.jpa.domain.Specification.where;

import java.util.Date;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import app.model.entity.Prescription;
import app.repository.specification.request.PrescriptionRequest;

@Component
public class PrescriptionSpecification extends BaseSpecification<Prescription, PrescriptionRequest> {

    @Override
    public Specification<Prescription> getFilter(PrescriptionRequest request) {
        return (root, query, cb) -> {
            query.distinct(true);
            return where(dateGreater(request.getFrom())
                    .and(dateLower(request.getTo()))
                    .and(idEquals("patient", request.getPatientId())))
                    .and(idEquals("doctor", request.getDoctorId()))
                    .and(idEquals("healthcareUnit", request.getHealthcareUnitId()))
                    .toPredicate(root, query, cb);
        };
    }

    private Specification<Prescription> idEquals(String idName, Integer value) {
        return (root, query, cb) -> {
            if (value == null) {
                return null;
            }
            return cb.equal(root.get(idName).get("id"), value);
        };
    }

    private Specification<Prescription> dateLower(Date value) {

        return (root, query, cb) -> {
            if (value == null) {
                return null;
            }
            return cb.lessThanOrEqualTo(root.get("expirationDate"), value);
        };
    }

    private Specification<Prescription> dateGreater(Date value) {
        return (root, query, cb) -> {
            if (value == null) {
                return null;
            }
            return cb.greaterThanOrEqualTo(root.get("dateOfIssue"), value);
        };
    }
}
