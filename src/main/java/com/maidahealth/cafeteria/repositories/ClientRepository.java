package com.maidahealth.cafeteria.repositories;

import com.maidahealth.cafeteria.models.ClientModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClientRepository extends JpaRepository<ClientModel, UUID> {
}
