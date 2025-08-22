package com.ecommerce.services;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.dto.ProductDTO;
import com.ecommerce.model.Product;

public interface ProductService {
	Product saveProduct(MultipartFile image, ProductDTO dto) throws IOException;

	List<Product> getAllProducts();

	void deleteProduct(Long id);

	Product updateProduct(Long id, ProductDTO dto, MultipartFile image) throws IOException;

	Product getProductById(Long id);

}


