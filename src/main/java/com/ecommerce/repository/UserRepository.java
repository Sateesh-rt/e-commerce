package com.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{
// @Query(value = "SELECT * FROM user WHERE name = ?1 AND password = ?2 AND role = ?3", nativeQuery = true)
    User findByNameAndPasswordAndRole(String name, String password, String role);
}
