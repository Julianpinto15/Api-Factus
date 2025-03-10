package factusBackend.application.services;

import factusBackend.domain.model.Invoice;
import factusBackend.infrastructure.adapters.FactusApiAdapter;
import factusBackend.presentation.controllers.InvoiceController;
import factusBackend.presentation.dtos.InvoiceRequestDTO;
import factusBackend.presentation.dtos.InvoiceResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class InvoiceService {

    private final FactusApiAdapter factusApiAdapter;
    private static final Logger logger = LoggerFactory.getLogger(InvoiceService.class);

    public InvoiceService(FactusApiAdapter factusApiAdapter) {
        this.factusApiAdapter = factusApiAdapter;
    }

    public InvoiceResponseDTO createInvoice(InvoiceRequestDTO requestDTO) {
        // Validar que el DTO no sea nulo
        if (requestDTO == null) {
            logger.error("El DTO de la factura no puede ser nulo");
            throw new IllegalArgumentException("El DTO de la factura no puede ser nulo");
        }

        // Log para depuración
        logger.debug("Transformando DTO a formato de Factus: {}", requestDTO);

        // Convertir el DTO a un formato compatible con la API de Factus
        Map<String, Object> invoiceData = transformToFactusFormat(requestDTO);

        // Log para depuración
        logger.debug("Datos transformados para Factus: {}", invoiceData);

        // Llamar a la API de Factus
        Map<String, Object> response = factusApiAdapter.createInvoice(invoiceData);

        // Log para depuración
        logger.debug("Respuesta de Factus: {}", response);

        // Transformar respuesta a nuestro DTO
        return transformToResponseDTO(response);
    }

    public Map<String, Object> validateInvoice(String invoiceId) {
        return factusApiAdapter.validateInvoice(invoiceId);
    }


    private Map<String, Object> transformToFactusFormat(InvoiceRequestDTO requestDTO) {
        Map<String, Object> invoiceData = new HashMap<>();

        // Validar campos obligatorios
        if (requestDTO.getNumbering_range_id() == null) {
            throw new IllegalArgumentException("El campo 'numbering_range_id' es requerido");
        }
        if (requestDTO.getCustomer() == null) {
            throw new IllegalArgumentException("El campo 'customer' es requerido");
        }

        // Mapear campos
        invoiceData.put("numbering_range_id", requestDTO.getNumbering_range_id());
        invoiceData.put("reference_code", requestDTO.getReference_code());
        invoiceData.put("observation", requestDTO.getObservation());
        invoiceData.put("payment_form", requestDTO.getPayment_form());
        invoiceData.put("payment_due_date", requestDTO.getPayment_due_date());
        invoiceData.put("payment_method_code", requestDTO.getPayment_method_code());

        // Billing Period
        Map<String, String> billingPeriod = new HashMap<>();
        billingPeriod.put("start_date", requestDTO.getBilling_period().getStart_date());
        billingPeriod.put("start_time", requestDTO.getBilling_period().getStart_time());
        billingPeriod.put("end_date", requestDTO.getBilling_period().getEnd_date());
        billingPeriod.put("end_time", requestDTO.getBilling_period().getEnd_time());
        invoiceData.put("billing_period", billingPeriod);

        // Customer
        Map<String, Object> customer = new HashMap<>();
        customer.put("identification", requestDTO.getCustomer().getIdentification());
        customer.put("dv", requestDTO.getCustomer().getDv());
        customer.put("company", requestDTO.getCustomer().getCompany());
        customer.put("trade_name", requestDTO.getCustomer().getTrade_name());
        customer.put("names", requestDTO.getCustomer().getNames());
        customer.put("address", requestDTO.getCustomer().getAddress());
        customer.put("email", requestDTO.getCustomer().getEmail());
        customer.put("phone", requestDTO.getCustomer().getPhone());
        customer.put("legal_organization_id", requestDTO.getCustomer().getLegal_organization_id());
        customer.put("tribute_id", requestDTO.getCustomer().getTribute_id());
        customer.put("identification_document_id", requestDTO.getCustomer().getIdentification_document_id());
        customer.put("municipality_id", requestDTO.getCustomer().getMunicipality_id());
        invoiceData.put("customer", customer);

        // Items
        invoiceData.put("items", requestDTO.getItems().stream().map(item -> {
            Map<String, Object> itemMap = new HashMap<>();
            itemMap.put("code_reference", item.getCode_reference());
            itemMap.put("name", item.getName());
            itemMap.put("quantity", item.getQuantity());
            itemMap.put("discount_rate", item.getDiscount_rate());
            itemMap.put("price", item.getPrice());
            itemMap.put("tax_rate", item.getTax_rate());
            itemMap.put("unit_measure_id", item.getUnit_measure_id());
            itemMap.put("standard_code_id", item.getStandard_code_id());
            itemMap.put("is_excluded", item.getIs_excluded());
            itemMap.put("tribute_id", item.getTribute_id());

            // Withholding Taxes
            itemMap.put("withholding_taxes", item.getWithholding_taxes().stream().map(tax -> {
                Map<String, String> taxMap = new HashMap<>();
                taxMap.put("code", tax.getCode());
                taxMap.put("withholding_tax_rate", tax.getWithholding_tax_rate());
                return taxMap;
            }).toList());

            return itemMap;
        }).toList());

        return invoiceData;
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