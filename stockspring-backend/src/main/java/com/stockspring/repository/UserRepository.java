package com.stockspring.repository;

import com.stockspring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@EnableJpaRepositories
@Repository

//Get all CRUD functions
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
