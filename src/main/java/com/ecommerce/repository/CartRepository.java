package com.ecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.model.Cart;
import com.ecommerce.model.User;

public interface CartRepository extends JpaRepository<Cart, Long> {
	List<Cart> findByUserId(Long userId);
	Optional<Cart> findByUserIdAndProductName(Long userId, String productName);
	List<Cart> findByUser(User user);

}
