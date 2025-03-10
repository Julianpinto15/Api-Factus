package factusBackend.presentation.dtos;

import lombok.Data;

@Data
public class WithholdingTaxDTO {
    private String code;
    private String withholdingTaxRate;
}