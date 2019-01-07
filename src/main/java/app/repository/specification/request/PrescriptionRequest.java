package app.repository.specification.request;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionRequest {
    private Date from;
    private Date to;
    private Integer patientId;
    private Integer doctorId;
    private Integer healthcareUnitId;
}
