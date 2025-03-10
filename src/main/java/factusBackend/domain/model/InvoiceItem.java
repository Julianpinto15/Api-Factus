
package factusBackend.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "invoice_items")
public class InvoiceItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code_reference;
    private String name;
    private Integer quantity;
    private Double discount_rate;
    private Double price;
    private String tax_rate;
    private Integer unit_measure_id;
    private Integer standard_code_id;
    private Integer is_excluded;
    private Integer tribute_id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "invoice_item_id")
    private List<ItemTax> taxations = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode_reference() {
        return code_reference;
    }

    public void setCode_reference(String code_reference) {
        this.code_reference = code_reference;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getDiscount_rate() {
        return discount_rate;
    }

    public void setDiscount_rate(Double discount_rate) {
        this.discount_rate = discount_rate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getTax_rate() {
        return tax_rate;
    }

    public void setTax_rate(String tax_rate) {
        this.tax_rate = tax_rate;
    }

    public Integer getUnit_measure_id() {
        return unit_measure_id;
    }

    public void setUnit_measure_id(Integer unit_measure_id) {
        this.unit_measure_id = unit_measure_id;
    }

    public Integer getStandard_code_id() {
        return standard_code_id;
    }

    public void setStandard_code_id(Integer standard_code_id) {
        this.standard_code_id = standard_code_id;
    }

    public Integer getIs_excluded() {
        return is_excluded;
    }

    public void setIs_excluded(Integer is_excluded) {
        this.is_excluded = is_excluded;
    }

    public Integer getTribute_id() {
        return tribute_id;
    }

    public void setTribute_id(Integer tribute_id) {
        this.tribute_id = tribute_id;
    }

    public List<ItemTax> getTaxations() {
        return taxations;
    }

    public void setTaxations(List<ItemTax> taxations) {
        this.taxations = taxations;
    }
}