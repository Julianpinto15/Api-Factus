package factusBackend.presentation.dtos;

import lombok.Data;

@Data
public class NumberingRangeDTO {
    private String prefix;
    private long from;
    private long to;
    private String resolutionNumber;
    private String startDate;
    private String endDate;
    private int months;
}