package com.maidahealth.cafeteria.services;

import com.maidahealth.cafeteria.models.ManagerModel;
import com.maidahealth.cafeteria.repositories.ManagerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ManagerService {

    final ManagerRepository managerRepository;

    public ManagerService(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }


    public List<ManagerModel> findAll() {
        return managerRepository.findAll();
    }

    public ManagerModel create(ManagerModel managerModel) {
        return managerRepository.save(managerModel);
    }

    public Optional<ManagerModel> findById(UUID uuid) {
        return managerRepository.findById(uuid);
    }

    public void delete(ManagerModel managerModel) {
        managerRepository.delete(managerModel);
    }
}
