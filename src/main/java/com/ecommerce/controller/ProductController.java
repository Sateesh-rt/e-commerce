package com.ecommerce.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.dto.ProductDTO;
import com.ecommerce.dto.ProductResponse;
import com.ecommerce.model.Product;
import com.ecommerce.services.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/products")
public class ProductController {
	@Autowired
	private ProductService service;
	@Autowired
	private ObjectMapper objectMapper;

	@PostMapping("/addProducts")

	public ResponseEntity<?> createProduct(@RequestPart("product") ProductDTO productDto,
			@RequestPart("image") MultipartFile image) throws IOException {

		return ResponseEntity.ok(service.saveProduct(image, productDto));

	}

	@GetMapping("/getProducts")
	public List<Map<String, Object>> getAllProducts() {
		List<Product> products = service.getAllProducts();

		return products.stream().map(p -> {
			try {
				// ✅ Convert Product object to Map automatically
				Map<String, Object> map = objectMapper.convertValue(p, Map.class);

				// 📷 Add Base64 image field
				try {
					byte[] imageBytes = Files.readAllBytes(Paths.get(p.getImagePath()));
					String base64Image = Base64.getEncoder().encodeToString(imageBytes);
					map.put("imageUrl", "data:image/jpeg;base64," + base64Image);
				} catch (IOException e) {
					map.put("imageUrl", null);
					System.err.println("Image not found for product ID " + p.getId() + ": " + e.getMessage());
				}

				return map;
			} catch (Exception e) {
				throw new RuntimeException("Error converting product to map", e);
			}
		}).collect(Collectors.toList());
	}



	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
		service.deleteProduct(id);
		return ResponseEntity.ok().build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestPart("product") ProductDTO dto,
			@RequestPart(value = "image", required = false) MultipartFile image) throws IOException {

		return ResponseEntity.ok(service.updateProduct(id, dto, image));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) throws Exception {
		Product product = service.getProductById(id);

		ProductResponse response = objectMapper.convertValue(product, ProductResponse.class);
//		response.setId(product.getId());
//		response.setName(product.getName());
//		response.setDescription(product.getDescription());
//		response.setPrice(product.getPrice());
//		response.setCategory(product.getCategory());

		String fileName = new File(product.getImagePath()).getName(); // Extract file name
		String imageUrl = "http://localhost:9090/uploads/" + fileName;
		response.setImageUrl(imageUrl);

		return ResponseEntity.ok(response);
	}
}
