package com.hextech.estoque_api.domain.repositories;

import com.hextech.estoque_api.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByIdAndCompanyId(Long id, Long companyId);
}
