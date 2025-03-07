package factusBackend.presentation.dtos;

public class WithholdingTaxDTO {

    private String code; // Código del impuesto retenido
    private String withholding_tax_rate; // Tasa del impuesto retenido

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getWithholding_tax_rate() {
        return withholding_tax_rate;
    }

    public void setWithholding_tax_rate(String withholding_tax_rate) {
        this.withholding_tax_rate = withholding_tax_rate;
    }
}
