package factusBackend.presentation.dtos;

import lombok.Data;

@Data
public class InvoiceResponseDTO {
    private String status;
    private String message;
    private InvoiceDataDTO data;
    // Otros campos necesarios

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public InvoiceDataDTO getData() {
        return data;
    }

    public void setData(InvoiceDataDTO data) {
        this.data = data;
    }
}