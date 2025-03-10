package factusBackend.presentation.dtos;

import lombok.Data;

@Data
public class CustomerDTO {
    private String identification;
    private String dv;
    private String company;
    private String tradeName;
    private String names;
    private String address;
    private String email;
    private String phone;
    private String legalOrganizationId;
    private String tributeId;
    private String identificationDocumentId;
    private String municipalityId;
}
