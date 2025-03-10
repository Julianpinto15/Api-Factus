package factusBackend.presentation.dtos;

import lombok.Data;

@Data
public class NumberingRangeDTO {
    private String prefix;
    private Integer from;
    private Integer to;
    private Integer current;
    private Boolean active;
    private String resolutionNumber;
    private String startDate;
    private String endDate;
    private String technicalKey;

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public Integer getFrom() {
        return from;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }

    public Integer getTo() {
        return to;
    }

    public void setTo(Integer to) {
        this.to = to;
    }

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getResolutionNumber() {
        return resolutionNumber;
    }

    public void setResolutionNumber(String resolutionNumber) {
        this.resolutionNumber = resolutionNumber;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getTechnicalKey() {
        return technicalKey;
    }

    public void setTechnicalKey(String technicalKey) {
        this.technicalKey = technicalKey;
    }

}

