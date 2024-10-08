package com.v1.smartbill.repository;

import com.v1.smartbill.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    List<User> findByLastname(String lastName);
    List<User> findByBirthdateGreaterThan(Date birthdate);
}
