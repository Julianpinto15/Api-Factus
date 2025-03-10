package factusBackend.presentation.dtos;

import lombok.Data;

@Data
public class InvoiceDataDTO {
    private CompanyDTO company;
    private CustomerDTO customer;
    private NumberingRangeDTO numberingRange;
    private List<BillingPeriodDTO> billingPeriod;
    private BillDTO bill;
    private List<RelatedDocumentDTO> relatedDocuments;
    private List<ItemDTO> items;
    private List<WithholdingTaxDTO> withholdingTaxes;
    private List<CreditNoteDTO> creditNotes;
    private List<DebitNoteDTO> debitNotes;
}