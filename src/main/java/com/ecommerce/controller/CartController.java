package com.ecommerce.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.dto.CartDto;
import com.ecommerce.model.Cart;
import com.ecommerce.model.User;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.services.CartService;
import com.fasterxml.jackson.databind.ObjectMapper;;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "http://localhost:4200") // allow Angular frontend
public class CartController {

    @Autowired
    private CartService cartService;
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ObjectMapper objectMapper;

  
    @PostMapping("/add/{userId}")
    public Cart addToCart( @PathVariable Long userId, @RequestBody CartDto dto ) {
        return cartService.addToCart(userId, dto);
    }

   
    @GetMapping("/{userId}")
    public List<Map<String, Object>> getCart(@PathVariable Long userId) {
       List<Cart> data= cartService.getCartByUser(userId);
       
       return data.stream().map(c -> {
			try {
				// ✅ Convert Product object to Map automatically
				Map<String, Object> map = objectMapper.convertValue(c, Map.class);

				// 📷 Add Base64 image field
				try {
					byte[] imageBytes = Files.readAllBytes(Paths.get(c.getImagePath()));
					String base64Image = Base64.getEncoder().encodeToString(imageBytes);
					map.put("imageUrl", "data:image/jpeg;base64," + base64Image);
				} catch (IOException e) {
					map.put("imageUrl", null);
					System.err.println("Image not found for product ID " + c.getId() + ": " + e.getMessage());
				}

				return map;
			} catch (Exception e) {
				throw new RuntimeException("Error converting product to map", e);
			}
		}).collect(Collectors.toList());
    }

   
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, String>> removeItem(@PathVariable Long id) {
        cartService.removeFromCart(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Item removed successfully");
        return ResponseEntity.ok(response);
    }

    
    
    @GetMapping("/{userId}/items")
    public List<Cart> getCartItems(@PathVariable Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return cartRepository.findByUser(user);
    }
  
    
    
}


