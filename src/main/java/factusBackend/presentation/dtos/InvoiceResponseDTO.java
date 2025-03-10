package factusBackend.presentation.dtos;

import lombok.Data;

@Data
public class InvoiceResponseDTO {
    private String status;
    private String message;
    private InvoiceDataDTO data;
    // Otros campos necesarios
}