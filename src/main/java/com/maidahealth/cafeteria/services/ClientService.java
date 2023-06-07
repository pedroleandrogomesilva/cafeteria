package com.maidahealth.cafeteria.services;

import com.maidahealth.cafeteria.models.ClientModel;
import com.maidahealth.cafeteria.repositories.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClientService {

    final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<ClientModel> findAll() {
        return clientRepository.findAll();
    }

    public ClientModel create(ClientModel clientModel) {
        return clientRepository.save(clientModel);
    }

    public Optional<ClientModel> findById(UUID uuid) {
        return clientRepository.findById(uuid);
    }

    public void delete(ClientModel clientModel) {
        clientRepository.delete(clientModel);
    }
}
