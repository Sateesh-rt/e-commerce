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

//@Autowired
//private ProductRepository productRepository;
//
//
//public Product addProduct(Product p) {
//    return productRepository.save(p);
//}
//
//public List<Product> getAll() {
//    return productRepository.findAll();
//}
//
//public Product updateProduct(Long id, ProductDTO dto) throws IOException {
//    Product product = productRepository.findById(id)
//        .orElseThrow(() -> new RuntimeException("Product not found"));
//
//    product.setName(dto.getName());
//    product.setDescription(dto.getDescription());
//    product.setPrice(dto.getPrice());
//    product.setCategory(dto.getCategory());
//
//    if (dto.getImage() != null && !dto.getImage().isEmpty()) {
//        product.setImage(dto.getImage().getBytes());
//    }
//
//    return productRepository.save(product);
//}
//
//
//public void deleteProduct(Long id) {
//     productRepository.deleteById(id);
//}
