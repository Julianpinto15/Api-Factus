package factusBackend.presentation.controllers;

import factusBackend.application.services.ClientService;
import factusBackend.common.constans.ApiConstants;
import factusBackend.common.dto.ResponseWrapper;
import factusBackend.common.exceptions.ResourceNotFoundException;
import factusBackend.domain.model.Client;
import factusBackend.presentation.dtos.ClientDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(ApiConstants.API_BASE_PATH + ApiConstants.CLIENTS_PATH)
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<ClientDTO>> createClient(@RequestBody ClientDTO clientDTO) {
        Client client = convertToEntity(clientDTO);
        Client savedClient = clientService.saveClient(client);
        ClientDTO savedClientDTO = convertToDTO(savedClient);

        ResponseWrapper<ClientDTO> response = new ResponseWrapper<>(
                true,
                ApiConstants.SUCCESS_MESSAGE,
                savedClientDTO
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<ClientDTO>> getClientById(@PathVariable Long id) {
        Client client = clientService.findClientById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente con ID " + id + " no encontrado"));

        ClientDTO clientDTO = convertToDTO(client);
        ResponseWrapper<ClientDTO> response = new ResponseWrapper<>(
                true,
                ApiConstants.SUCCESS_MESSAGE,
                clientDTO
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/identification/{identificationNumber}")
    public ResponseEntity<ResponseWrapper<ClientDTO>> getClientByIdentificationNumber(
            @PathVariable String identificationNumber) {
        Client client = clientService.findClientByIdentificationNumber(identificationNumber)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cliente con número de identificación " + identificationNumber + " no encontrado"));

        ClientDTO clientDTO = convertToDTO(client);
        ResponseWrapper<ClientDTO> response = new ResponseWrapper<>(
                true,
                ApiConstants.SUCCESS_MESSAGE,
                clientDTO
        );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper<ClientDTO>> updateClient(
            @PathVariable Long id,
            @RequestBody ClientDTO clientDTO) {

        // Check if client exists
        clientService.findClientById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente con ID " + id + " no encontrado"));

        Client client = convertToEntity(clientDTO);
        client.setId(id);
        Client updatedClient = clientService.saveClient(client);

        ClientDTO updatedClientDTO = convertToDTO(updatedClient);
        ResponseWrapper<ClientDTO> response = new ResponseWrapper<>(
                true,
                ApiConstants.SUCCESS_MESSAGE,
                updatedClientDTO
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

    private Client convertToEntity(ClientDTO clientDTO) {
        Client client = new Client();
        client.setTypeDocumentId(clientDTO.getTypeDocumentId());
        client.setIdentificationNumber(clientDTO.getIdentificationNumber());
        client.setName(clientDTO.getName());
        client.setCountryId(clientDTO.getCountryId());
        client.setMunicipalityId(clientDTO.getMunicipalityId());
        client.setAddress(clientDTO.getAddress());
        client.setEmail(clientDTO.getEmail());
        client.setPhone(clientDTO.getPhone());
        return client;
    }

    private ClientDTO convertToDTO(Client client) {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setTypeDocumentId(client.getTypeDocumentId());
        clientDTO.setIdentificationNumber(client.getIdentificationNumber());
        clientDTO.setName(client.getName());
        clientDTO.setCountryId(client.getCountryId());
        clientDTO.setMunicipalityId(client.getMunicipalityId());
        clientDTO.setAddress(client.getAddress());
        clientDTO.setEmail(client.getEmail());
        clientDTO.setPhone(client.getPhone());
        return clientDTO;
    }
}