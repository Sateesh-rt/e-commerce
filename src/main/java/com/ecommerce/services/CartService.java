package com.ecommerce.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.dto.CartDto;
import com.ecommerce.model.Cart;
import com.ecommerce.model.User;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ObjectMapper objectMapper;

   
    public Cart addToCart(Long userId, CartDto dto) {
    	User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Optional<Cart> existingCartItem = cartRepository.findByUserIdAndProductName(userId, dto.getProductName());

        if (existingCartItem.isPresent()) {
            Cart existing = existingCartItem.get();
            existing.setQuantity(existing.getQuantity() + 1);
            existing.setTotal(existing.getPrice() * existing.getQuantity());
            return cartRepository.save(existing);
        } else {
            Cart cart = objectMapper.convertValue(dto, Cart.class);
            cart.setUser(user);
            cart.setTotal(cart.getPrice() * cart.getQuantity());

            // ✅ directly reuse product imagePath from DTO
            

            return cartRepository.save(cart);
    	    }
    }

    
    public List<Cart> getCartByUser(Long userId) {
      List<Cart>  data=cartRepository.findByUserId(userId);
      return data;
    }

  
    public void removeFromCart(Long cartId) {
        cartRepository.deleteById(cartId);
    }

    
  
}
