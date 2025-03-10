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
}
