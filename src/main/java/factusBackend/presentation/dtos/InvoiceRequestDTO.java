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

    public String getReferenceCode() {
        return referenceCode;
    }

    public void setReferenceCode(String referenceCode) {
        this.referenceCode = referenceCode;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public String getPaymentForm() {
        return paymentForm;
    }

    public void setPaymentForm(String paymentForm) {
        this.paymentForm = paymentForm;
    }

    public String getPaymentDueDate() {
        return paymentDueDate;
    }

    public void setPaymentDueDate(String paymentDueDate) {
        this.paymentDueDate = paymentDueDate;
    }

    public String getPaymentMethodCode() {
        return paymentMethodCode;
    }

    public void setPaymentMethodCode(String paymentMethodCode) {
        this.paymentMethodCode = paymentMethodCode;
    }

    public CustomerDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }

    public List<ItemDTO> getItems() {
        return items;
    }

    public void setItems(List<ItemDTO> items) {
        this.items = items;
    }

    public String getNumberingRangeId() {

        return numberingRangeId;
    }

    public void setNumberingRangeId(String numberingRangeId) {
        this.numberingRangeId = numberingRangeId;
    }
}