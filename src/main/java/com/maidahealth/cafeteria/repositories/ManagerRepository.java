package com.maidahealth.cafeteria.repositories;

import com.maidahealth.cafeteria.models.ManagerModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ManagerRepository extends JpaRepository<ManagerModel, UUID> {
}
