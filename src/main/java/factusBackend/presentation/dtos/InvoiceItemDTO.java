package factusBackend.presentation.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceItemDTO {
    private String code;
    private String description;
    private BigDecimal quantity;
    private BigDecimal price;
    private String unitMeasurementId;
    private List<TaxDTO> taxations;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getUnitMeasurementId() {
        return unitMeasurementId;
    }

    public void setUnitMeasurementId(String unitMeasurementId) {
        this.unitMeasurementId = unitMeasurementId;
    }

    public List<TaxDTO> getTaxations() {
        return taxations;
    }

    public void setTaxations(List<TaxDTO> taxations) {
        this.taxations = taxations;
    }
}