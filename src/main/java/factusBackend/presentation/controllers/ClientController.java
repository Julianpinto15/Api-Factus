package factusBackend.presentation.controllers;

import factusBackend.application.services.ClientService;
import factusBackend.common.constans.ApiConstants;
import factusBackend.common.dto.ResponseWrapper;
import factusBackend.common.exceptions.ResourceNotFoundException;
import factusBackend.domain.model.Client;
import factusBackend.presentation.dtos.CustomerDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiConstants.API_BASE_PATH + ApiConstants.CLIENTS_PATH)
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<CustomerDTO>> createClient(@RequestBody CustomerDTO customerDTO) {
        Client client = convertToEntity(customerDTO);
        Client savedClient = clientService.saveClient(client);
        CustomerDTO savedCustomerDTO = convertToDTO(savedClient);

        ResponseWrapper<CustomerDTO> response = new ResponseWrapper<>(
                true,
                ApiConstants.SUCCESS_MESSAGE,
                savedCustomerDTO
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<CustomerDTO>> getClientById(@PathVariable Long id) {
        Client client = clientService.findClientById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente con ID " + id + " no encontrado"));

        CustomerDTO customerDTO = convertToDTO(client);
        ResponseWrapper<CustomerDTO> response = new ResponseWrapper<>(
                true,
                ApiConstants.SUCCESS_MESSAGE,
                customerDTO
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/identification/{identification}")
    public ResponseEntity<ResponseWrapper<CustomerDTO>> getClientByIdentification(
            @PathVariable String identification) {
        Client client = clientService.findClientByIdentification(identification)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cliente con número de identificación " + identification + " no encontrado"));

        CustomerDTO customerDTO = convertToDTO(client);
        ResponseWrapper<CustomerDTO> response = new ResponseWrapper<>(
                true,
                ApiConstants.SUCCESS_MESSAGE,
                customerDTO
        );

        return ResponseEntity.ok(response);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper<CustomerDTO>> updateClient(
            @PathVariable Long id,
            @RequestBody CustomerDTO customerDTO) {

        // Check if client exists
        clientService.findClientById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente con ID " + id + " no encontrado"));

        Client client = convertToEntity(customerDTO);
        client.setId(id);
        Client updatedClient = clientService.saveClient(client);

        CustomerDTO updatedCustomerDTO = convertToDTO(updatedClient);
        ResponseWrapper<CustomerDTO> response = new ResponseWrapper<>(
                true,
                ApiConstants.SUCCESS_MESSAGE,
                updatedCustomerDTO
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Void>> deleteClient(@PathVariable Long id) {
        // Check if client exists
        clientService.findClientById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente con ID " + id + " no encontrado"));

        clientService.deleteClientById(id);

        ResponseWrapper<Void> response = new ResponseWrapper<>(
                true,
                "Cliente eliminado exitosamente",
                null
        );

        return ResponseEntity.ok(response);
    }

    private Client convertToEntity(CustomerDTO customerDTO) {
        Client client = new Client();
        client.setIdentification_document_id(customerDTO.getIdentification_document_id());
        client.setIdentification(customerDTO.getIdentification());
        client.setNames(customerDTO.getNames());
        client.setAddress(customerDTO.getAddress());
        client.setEmail(customerDTO.getEmail());
        client.setPhone(customerDTO.getPhone());
        client.setMunicipality_id(customerDTO.getMunicipality_id());
        client.setLegal_organization_id(customerDTO.getLegal_organization_id());
        client.setTribute_id(customerDTO.getTribute_id());
        client.setTrade_name(customerDTO.getTrade_name());
        // Si `legal_organization_id` y `tribute_id` tienen relación directa con `Client`, puedes mapearlos.
        // Agrega más mapeos si corresponde según la estructura de tu entidad `Client`.
        return client;
    }

    private CustomerDTO convertToDTO(Client client) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setIdentification_document_id(client.getIdentification_document_id());
        customerDTO.setIdentification(client.getIdentification());
        customerDTO.setNames(client.getNames());
        customerDTO.setAddress(client.getAddress());
        customerDTO.setEmail(client.getEmail());
        customerDTO.setPhone(client.getPhone());
        customerDTO.setMunicipality_id(client.getMunicipality_id());
        customerDTO.setLegal_organization_id(client.getLegal_organization_id());
        customerDTO.setTribute_id(client.getTribute_id());
        customerDTO.setTrade_name(client.getTrade_name());
        // Si `legal_organization_id` y `tribute_id` deben derivarse de propiedades del `Client`, agrega la lógica aquí.
        return customerDTO;
    }

}