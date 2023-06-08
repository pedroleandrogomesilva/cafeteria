package com.maidahealth.cafeteria.repositories;

import com.maidahealth.cafeteria.models.LoginModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LoginRepository extends JpaRepository<LoginModel, UUID> {
    Optional<LoginModel> findByEmail(String email);

}
