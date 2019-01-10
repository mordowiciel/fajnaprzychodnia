package app.repository.specification;

import static org.springframework.data.jpa.domain.Specification.where;

import java.time.ZoneId;
import java.util.Date;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import app.model.VisitStatus;
import app.model.entity.Visit;
import app.repository.specification.request.VisitRequest;

@Component
public class VisitSpecification extends BaseSpecification<Visit, VisitRequest> {

    @Override
    public Specification<Visit> getFilter(VisitRequest request) {
        return (root, query, cb) -> {
            query.distinct(true);
            return where(dateGreater(request.getFrom())
                    .and(dateLower(request.getTo()))
                    .and(statusEquals(request.getVisitStatus()))
                    .and(idEquals("patient", request.getPatientId())))
                    .and(idEquals("doctor", request.getDoctorId()))
                    .and(idEquals("healthcareUnit", request.getHealthcareUnitId()))
                    .toPredicate(root, query, cb);
        };
    }

    private Specification<Visit> statusEquals(VisitStatus value) {
        return (root, query, cb) -> {
            if (value == null) {
                return null;
            }
            return cb.equal(root.get("visitStatus"), value);
        };
    }

    private Specification<Visit> idEquals(String idName, Integer value) {
        return (root, query, cb) -> {
            if (value == null) {
                return null;
            }
            return cb.equal(root.get(idName).get("id"), value);
        };
    }

    private Specification<Visit> dateLower(Date value) {

        return (root, query, cb) -> {
            if (value == null) {
                return null;
            }
            return cb.lessThanOrEqualTo(root.get("visitDate"), value.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        };
    }

    private Specification<Visit> dateGreater(Date value) {
        return (root, query, cb) -> {
            if (value == null) {
                return null;
            }
            return cb.greaterThanOrEqualTo(root.get("visitDate"), value.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        };
    }
}
