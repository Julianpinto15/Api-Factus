package factusBackend.application.services;

import factusBackend.domain.model.Invoice;
import factusBackend.infrastructure.adapters.FactusApiAdapter;
import factusBackend.presentation.dtos.InvoiceRequestDTO;
import factusBackend.presentation.dtos.InvoiceResponseDTO;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class InvoiceService {

    private final FactusApiAdapter factusApiAdapter;

    public InvoiceService(FactusApiAdapter factusApiAdapter) {
        this.factusApiAdapter = factusApiAdapter;
    }

    public InvoiceResponseDTO createInvoice(InvoiceRequestDTO requestDTO) {
        // Convertir el DTO a un formato compatible con la API de Factus
        Map<String, Object> invoiceData = transformToFactusFormat(requestDTO);

        // Llamar a la API de Factus
        Map<String, Object> response = factusApiAdapter.createInvoice(invoiceData);

        // Transformar respuesta a nuestro DTO
        return transformToResponseDTO(response);
    }

    public Map<String, Object> validateInvoice(String invoiceId) {
        return factusApiAdapter.validateInvoice(invoiceId);
    }

    private Map<String, Object> transformToFactusFormat(InvoiceRequestDTO requestDTO) {
        // Aquí implementarías la lógica para transformar tu DTO a la estructura esperada por Factus
        // Sería un mapa con la estructura específica que solicita Factus
        // Esto dependerá de la documentación de la API y el formato que espera

        // Este es un ejemplo basado en la estructura típica, ajústalo según necesites
        return Map.of(
                "invoice", Map.of(
                        "number_range_id", requestDTO.getNumberRangeId(),
                        "currency_id", "COP",
                        "generation_date", requestDTO.getGenerationDate(),
                        "customer", Map.of(
                                "type_document_identification_id", requestDTO.getClient().getTypeDocumentId(),
                                "identification_number", requestDTO.getClient().getIdentificationNumber(),
                                "name", requestDTO.getClient().getName(),
                                "country_id", requestDTO.getClient().getCountryId(),
                                "municipality_id", requestDTO.getClient().getMunicipalityId(),
                                "address", requestDTO.getClient().getAddress(),
                                "email", requestDTO.getClient().getEmail()
                        ),
                        "detail", requestDTO.getItems().stream().map(item -> Map.of(
                                "code", item.getCode(),
                                "description", item.getDescription(),
                                "quantity", item.getQuantity(),
                                "price", item.getPrice(),
                                "unit_measurement_id", item.getUnitMeasurementId(),
                                "taxation", item.getTaxations().stream().map(tax -> Map.of(
                                        "id", tax.getId(),
                                        "value", tax.getValue()
                                )).toList()
                        )).toList()
                )
        );
    }

    private InvoiceResponseDTO transformToResponseDTO(Map<String, Object> response) {
        // Implementa la transformación de la respuesta de Factus a tu DTO
        // Este es sólo un ejemplo básico
        InvoiceResponseDTO dto = new InvoiceResponseDTO();
        Map<String, Object> invoiceData = (Map<String, Object>) response.get("invoice");

        if (invoiceData != null) {
            dto.setId((String) invoiceData.get("id"));
            dto.setInvoiceNumber((String) invoiceData.get("invoice_number"));
            dto.setStatus((String) invoiceData.get("status"));
            dto.setGenerationDate((String) invoiceData.get("generation_date"));
            dto.setTotalAmount((Double) invoiceData.get("total_amount"));
        }

        return dto;
    }
}