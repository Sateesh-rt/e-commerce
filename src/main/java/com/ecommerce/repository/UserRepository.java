package com.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecommerce.dto.UserProjection;
import com.ecommerce.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
   

	@Query(value = "SELECT id, name, password, role FROM users WHERE name = :name AND password = :password AND role = :role", nativeQuery = true)
	UserProjection getUserDetailsByRole(@Param("role") String role, @Param("name") String name,
			@Param("password") String password);
}
