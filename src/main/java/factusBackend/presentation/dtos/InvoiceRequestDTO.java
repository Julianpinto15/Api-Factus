package factusBackend.presentation.dtos;

import lombok.Data;

import java.util.List;

@Data
public class InvoiceRequestDTO {
    private String numberingRangeId;
    private String referenceCode;
    private String observation;
    private String paymentForm;
    private String paymentDueDate;
    private String paymentMethodCode;
    private CustomerDTO customer;
    private List<ItemDTO> items;
}