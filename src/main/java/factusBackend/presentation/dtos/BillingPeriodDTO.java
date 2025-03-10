package factusBackend.presentation.dtos;

import lombok.Data;

@Data
public class BillingPeriodDTO {
    private String startDate;
    private String startTime;
    private String endDate;
    private String endTime;
}