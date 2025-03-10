package factusBackend.presentation.dtos;

import lombok.Data;

import java.util.List;

@Data
public class ItemDTO {
    private String codeReference;
    private String name;
    private Integer quantity;
    private Double discountRate;
    private Double price;
    private String taxRate;
    private Integer unitMeasureId;
    private Integer standardCodeId;
    private Integer isExcluded;
    private Integer tributeId;
    private List<WithholdingTaxDTO> withholdingTaxes;

    public String getCodeReference() {
        return codeReference;
    }

    public void setCodeReference(String codeReference) {
        this.codeReference = codeReference;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(Double discountRate) {
        this.discountRate = discountRate;
    }

    public String getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(String taxRate) {
        this.taxRate = taxRate;
    }

    public Integer getUnitMeasureId() {
        return unitMeasureId;
    }

    public void setUnitMeasureId(Integer unitMeasureId) {
        this.unitMeasureId = unitMeasureId;
    }

    public Integer getStandardCodeId() {
        return standardCodeId;
    }

    public void setStandardCodeId(Integer standardCodeId) {
        this.standardCodeId = standardCodeId;
    }

    public Integer getIsExcluded() {
        return isExcluded;
    }

    public void setIsExcluded(Integer isExcluded) {
        this.isExcluded = isExcluded;
    }

    public Integer getTributeId() {
        return tributeId;
    }

    public void setTributeId(Integer tributeId) {
        this.tributeId = tributeId;
    }

    public List<WithholdingTaxDTO> getWithholdingTaxes() {
        return withholdingTaxes;
    }

    public void setWithholdingTaxes(List<WithholdingTaxDTO> withholdingTaxes) {
        this.withholdingTaxes = withholdingTaxes;
    }
}
