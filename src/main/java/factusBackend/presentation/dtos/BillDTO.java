package factusBackend.presentation.dtos;

import lombok.Data;

import java.util.List;

@Data
public class BillDTO {
    private Long id;
    private DocumentDTO document;
    private String number;
    private String referenceCode;
    private Integer status;
    private Integer sendEmail;
    private String qr;
    private String cufe;
    private String validated;
    private String discountRate;
    private String discount;
    private String grossValue;
    private String taxableAmount;
    private String taxAmount;
    private String total;
    private String observation;
    private List<String> errors;
    private String createdAt;
    private String paymentDueDate;
    private String qrImage;
    private Integer hasClaim;
    private Integer isNegotiableInstrument;
    private PaymentFormDTO paymentForm;
    private PaymentMethodDTO paymentMethod;
}