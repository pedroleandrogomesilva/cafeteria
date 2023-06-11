package com.maidahealth.cafeteria.controllers;

import com.maidahealth.cafeteria.dtos.ClientDto;
import com.maidahealth.cafeteria.models.ClientModel;
import com.maidahealth.cafeteria.services.ClientService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/client")
public class ClientController {

    final ClientService clientService;
    final PasswordEncoder passwordEncoder;

    public ClientController(ClientService clientService, PasswordEncoder passwordEncoder) {
        this.clientService = clientService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    public ResponseEntity<Object> registerClient(@RequestBody @Valid ClientDto clientDto) {
        var clientModel = new ClientModel();
        BeanUtils.copyProperties(clientDto, clientModel);
        clientModel.setPassword(passwordEncoder.encode(clientModel.getPassword()));
        ClientModel clientModelResponse = clientService.create(clientModel);
        clientModel.add(linkTo(methodOn(ClientController.class).getOneClientModel(clientModelResponse.getId())).withSelfRel());
        return ResponseEntity.status(HttpStatus.CREATED).body(clientModel);
    }

    @GetMapping
    public ResponseEntity<List<ClientModel>> getAllClients() {
        List<ClientModel> clientModelList = clientService.findAll();

        if (clientModelList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            for (ClientModel clientModel : clientModelList) {
                UUID id = clientModel.getId();
                clientModel.add(linkTo(methodOn(ClientController.class).getOneClientModel(id)).withSelfRel());
            }
            return ResponseEntity.status(HttpStatus.OK).body(clientModelList);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneClientModel(@PathVariable(value = "id") UUID uuid) {
        Optional<ClientModel> clientModelOptional = clientService.findById(uuid);
        if (!clientModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client not found.");
        } else {
            clientModelOptional.get().add(linkTo(methodOn(ClientController.class).getAllClients()).withSelfRel());
            return ResponseEntity.status(HttpStatus.OK).body(clientModelOptional.get());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteOneClientModel(@PathVariable(value = "id") UUID uuid) {
        Optional<ClientModel> clientModelOptional = clientService.findById(uuid);
        if (!clientModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client not found.");
        }

        clientService.delete(clientModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Client deleted successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateClientModel(@PathVariable(value = "id") UUID uuid, @RequestBody @Valid ClientDto clientDto) {
        Optional<ClientModel> clientModelOptional = clientService.findById(uuid);
        if (!clientModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client not found.");
        }

        var clientModel = new ClientModel();
        BeanUtils.copyProperties(clientDto, clientModel);
        clientModel.setId(clientModelOptional.get().getId());
        clientModel.setPassword(passwordEncoder.encode(clientModel.getPassword()));
        ClientModel clientModelResponse = clientService.create(clientModel);
        clientModel.add(linkTo(methodOn(ClientController.class).getOneClientModel(clientModelResponse.getId())).withSelfRel());
        return ResponseEntity.status(HttpStatus.OK).body(clientModel);
    }

}
