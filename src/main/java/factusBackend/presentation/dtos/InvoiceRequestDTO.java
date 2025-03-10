package factusBackend.presentation.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceRequestDTO {

    private Integer numbering_range_id; // Ajustado a lo que la API espera
    private String reference_code; // Nuevo campo
    private String observation; // Nuevo campo
    private String payment_form; // Nuevo campo
    private String payment_due_date; // Nuevo campo
    private String payment_method_code; // Nuevo campo
    private BillingPeriodDTO billing_period; // Nuevo campo
    private CustomerDTO customer; // Ajustado a lo que la API espera
    private List<ItemDTO> items; // Ajustado a lo que la API espera

    public Integer getNumbering_range_id() {
        return numbering_range_id;
    }

    public void setNumbering_range_id(Integer numbering_range_id) {
        this.numbering_range_id = numbering_range_id;
    }

    public String getReference_code() {
        return reference_code;
    }

    public void setReference_code(String reference_code) {
        this.reference_code = reference_code;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public String getPayment_form() {
        return payment_form;
    }

    public void setPayment_form(String payment_form) {
        this.payment_form = payment_form;
    }

    public String getPayment_due_date() {
        return payment_due_date;
    }

    public void setPayment_due_date(String payment_due_date) {
        this.payment_due_date = payment_due_date;
    }

    public String getPayment_method_code() {
        return payment_method_code;
    }

    public void setPayment_method_code(String payment_method_code) {
        this.payment_method_code = payment_method_code;
    }

    public BillingPeriodDTO getBilling_period() {
        return billing_period;
    }

    public void setBilling_period(BillingPeriodDTO billing_period) {
        this.billing_period = billing_period;
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
}