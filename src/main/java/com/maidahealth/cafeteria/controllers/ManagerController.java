package com.maidahealth.cafeteria.controllers;

import com.maidahealth.cafeteria.dtos.ManagerDto;
import com.maidahealth.cafeteria.models.ManagerModel;
import com.maidahealth.cafeteria.services.ManagerService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/manager")
public class ManagerController {

    final ManagerService managerService;
    final PasswordEncoder passwordEncoder;

    public ManagerController(ManagerService managerService, PasswordEncoder passwordEncoder) {
        this.managerService = managerService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    public ResponseEntity<Object> registerManager(@RequestBody @Valid ManagerDto managerDto) {

        List<ManagerModel> managerModelsList = managerService.findAll();

        if (!managerModelsList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Whooops!!! It is not possible to create another manager because there is already a registered manager for the cafeteria.");
        }

        var managerModel = new ManagerModel();
        BeanUtils.copyProperties(managerDto, managerModel);
        managerModel.setPassword(passwordEncoder.encode(managerModel.getPassword()));
        ManagerModel managerModelResponse = managerService.create(managerModel);
        managerModel.add(linkTo(methodOn(ManagerController.class).getOneManagerModel(managerModelResponse.getId())).withSelfRel());
        return ResponseEntity.status(HttpStatus.CREATED).body(managerModel);
    }

    @GetMapping
    public ResponseEntity<List<ManagerModel>> getAllManagers() {
        List<ManagerModel> managerModelsList = managerService.findAll();

        if (managerModelsList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            for (ManagerModel managerModel : managerModelsList) {
                UUID id = managerModel.getId();
                managerModel.add(linkTo(methodOn(ManagerController.class).getOneManagerModel(id)).withSelfRel());
            }
            return ResponseEntity.status(HttpStatus.OK).body(managerModelsList);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneManagerModel(@PathVariable(value = "id") UUID uuid) {
        Optional<ManagerModel> managerModelOptional = managerService.findById(uuid);
        if (!managerModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Manager not found.");
        }else{
            managerModelOptional.get().add(linkTo(methodOn(ManagerController.class).getAllManagers()).withSelfRel());
            return ResponseEntity.status(HttpStatus.OK).body(managerModelOptional.get());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteOneManagerModel(@PathVariable(value = "id") UUID uuid) {
        Optional<ManagerModel> managerModelOptional = managerService.findById(uuid);
        if (!managerModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Manager not found.");
        }

        managerService.delete(managerModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Manager deleted successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateManagerModel(@PathVariable(value = "id") UUID uuid, @RequestBody @Valid ManagerDto managerDto) {
        Optional<ManagerModel> managerModelOptional = managerService.findById(uuid);
        if (!managerModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Manager not found.");
        }

        var managerModel = new ManagerModel();
        BeanUtils.copyProperties(managerDto, managerModel);
        managerModel.setId(managerModelOptional.get().getId());
        managerModel.setPassword(passwordEncoder.encode(managerModel.getPassword()));
        ManagerModel managerModelResponse = managerService.create(managerModel);
        managerModel.add(linkTo(methodOn(ManagerController.class).getOneManagerModel(managerModelResponse.getId())).withSelfRel());
        return ResponseEntity.status(HttpStatus.OK).body(managerModel);
    }

}
