package factusBackend.domain.model;


import lombok.Data;


@Data
public class NumberingRange {
    private String id;
    private String prefix;
    private Integer fromNumber;
    private Integer toNumber;
    private Integer currentNumber;
    private Boolean active;
    private String resolutionNumber;
    private String startDate;
    private String endDate;
    private String technicalKey;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public Integer getFromNumber() {
        return fromNumber;
    }

    public void setFromNumber(Integer fromNumber) {
        this.fromNumber = fromNumber;
    }

    public Integer getToNumber() {
        return toNumber;
    }

    public void setToNumber(Integer toNumber) {
        this.toNumber = toNumber;
    }

    public Integer getCurrentNumber() {
        return currentNumber;
    }

    public void setCurrentNumber(Integer currentNumber) {
        this.currentNumber = currentNumber;
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