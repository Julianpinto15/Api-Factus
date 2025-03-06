package factusBackend.presentation.controllers;

import factusBackend.application.usecases.CreateInvoiceUseCase;
import factusBackend.application.usecases.ValidateInvoiceUseCase;
import factusBackend.common.constans.ApiConstants;
import factusBackend.common.dto.ResponseWrapper;
import factusBackend.presentation.dtos.InvoiceRequestDTO;
import factusBackend.presentation.dtos.InvoiceResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(ApiConstants.API_BASE_PATH + ApiConstants.INVOICES_PATH)
public class InvoiceController {

    private final CreateInvoiceUseCase createInvoiceUseCase;
    private final ValidateInvoiceUseCase validateInvoiceUseCase;

    public InvoiceController(CreateInvoiceUseCase createInvoiceUseCase, ValidateInvoiceUseCase validateInvoiceUseCase) {
        this.createInvoiceUseCase = createInvoiceUseCase;
        this.validateInvoiceUseCase = validateInvoiceUseCase;
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<InvoiceResponseDTO>> createInvoice(@RequestBody InvoiceRequestDTO requestDTO) {
        InvoiceResponseDTO response = createInvoiceUseCase.execute(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseWrapper<>(true, ApiConstants.SUCCESS_MESSAGE, response));
    }

    @PostMapping("/{id}/validate")
    public ResponseEntity<ResponseWrapper<Map<String, Object>>> validateInvoice(@PathVariable String id) {
        Map<String, Object> response = validateInvoiceUseCase.execute(id);
        return ResponseEntity.ok(new ResponseWrapper<>(true, ApiConstants.SUCCESS_MESSAGE, response));
    }
}