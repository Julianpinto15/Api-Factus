package factusBackend.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String identification;
    private String dv;
    private String company;
    private String trade_name;
    private String names;
    private String address;
    private String email;
    private String phone;
    private String legal_organization_id;
    private String tribute_id;
    private String identification_document_id;
    private String municipality_id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getDv() {
        return dv;
    }

    public void setDv(String dv) {
        this.dv = dv;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getTrade_name() {
        return trade_name;
    }

    public void setTrade_name(String trade_name) {
        this.trade_name = trade_name;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLegal_organization_id() {
        return legal_organization_id;
    }

    public void setLegal_organization_id(String legal_organization_id) {
        this.legal_organization_id = legal_organization_id;
    }

    public String getTribute_id() {
        return tribute_id;
    }

    public void setTribute_id(String tribute_id) {
        this.tribute_id = tribute_id;
    }

    public String getIdentification_document_id() {
        return identification_document_id;
    }

    public void setIdentification_document_id(String identification_document_id) {
        this.identification_document_id = identification_document_id;
    }

    public String getMunicipality_id() {
        return municipality_id;
    }

    public void setMunicipality_id(String municipality_id) {
        this.municipality_id = municipality_id;
    }
}