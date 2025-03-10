package factusBackend.presentation.controllers;

import factusBackend.application.usecases.CreateInvoiceUseCase;
import factusBackend.application.usecases.ValidateInvoiceUseCase;
import factusBackend.common.constans.ApiConstants;
import factusBackend.common.dto.ResponseWrapper;
import factusBackend.presentation.dtos.InvoiceRequestDTO;
import factusBackend.presentation.dtos.InvoiceResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(ApiConstants.API_BASE_PATH + ApiConstants.INVOICES_PATH)
public class InvoiceController {

    private static final Logger logger = LoggerFactory.getLogger(InvoiceController.class);

    private final CreateInvoiceUseCase createInvoiceUseCase;
    private final ValidateInvoiceUseCase validateInvoiceUseCase;

    public InvoiceController(CreateInvoiceUseCase createInvoiceUseCase, ValidateInvoiceUseCase validateInvoiceUseCase) {
        this.createInvoiceUseCase = createInvoiceUseCase;
        this.validateInvoiceUseCase = validateInvoiceUseCase;
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<InvoiceResponseDTO>> createInvoice(@RequestBody InvoiceRequestDTO requestDTO) {
        // Validar que el DTO no sea nulo
        if (requestDTO == null) {
            logger.error("El cuerpo de la solicitud no puede ser nulo");
            throw new IllegalArgumentException("El cuerpo de la solicitud no puede ser nulo");
        }

        // Log para depuración
        logger.debug("Recibida solicitud para crear factura: {}", requestDTO);

        try {
            InvoiceResponseDTO response = createInvoiceUseCase.execute(requestDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseWrapper<>(true, ApiConstants.SUCCESS_MESSAGE, response));
        } catch (Exception e) {
            logger.error("Error al crear factura: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseWrapper<>(false, "Error al crear factura: " + e.getMessage(), null));
        }
    }

    @PostMapping("/{id}/validate")
    public ResponseEntity<ResponseWrapper<Map<String, Object>>> validateInvoice(@PathVariable String id) {
        Map<String, Object> response = validateInvoiceUseCase.execute(id);
        return ResponseEntity.ok(new ResponseWrapper<>(true, ApiConstants.SUCCESS_MESSAGE, response));
    }
}