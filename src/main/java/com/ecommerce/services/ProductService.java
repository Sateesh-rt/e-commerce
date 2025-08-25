package com.ecommerce.services;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.dto.ProductDTO;
import com.ecommerce.model.Product;

public interface ProductService {
	public Product saveProduct(MultipartFile image, ProductDTO dto) throws IOException;

	public List<Product> getAllProducts();

	public void deleteProduct(Long id);

	public Product updateProduct(Long id, ProductDTO dto, MultipartFile image) throws IOException;

	 public Product getProductById(Long id);

}


