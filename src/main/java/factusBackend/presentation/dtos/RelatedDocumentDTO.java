package factusBackend.presentation.dtos;

import lombok.Data;

@Data
public class RelatedDocumentDTO {
    private Long id;
    private String code;
    private String number;
    private String issueDate;
}