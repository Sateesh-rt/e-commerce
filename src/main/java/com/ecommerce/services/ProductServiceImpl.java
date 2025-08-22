package com.ecommerce.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.dto.ProductDTO;
import com.ecommerce.model.Product;
import com.ecommerce.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository repo;
	
	@Autowired
	private ObjectMapper objectMapper;

	
	
	@Override
	public Product saveProduct(MultipartFile image, ProductDTO dto) throws IOException {
	    String folderPath = System.getProperty("user.dir") + "/uploads";// String path
	    Path uploadDir = Paths.get(folderPath);// converted to String path to pathObject
	    Files.createDirectories(uploadDir);// create directory folder search

	    String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();// uniqueno and filename
	    Path filePath = uploadDir.resolve(fileName);// appends filename to folderPath
	    Files.write(filePath, image.getBytes());// read into byteArray and save it

	    // ✅ Use ObjectMapper to convert DTO to Entity
	    Product p = objectMapper.convertValue(dto,Product.class);

	    // ✅ Set the image path manually (since it's not part of DTO)
	    p.setImagePath(filePath.toString());

	    return repo.save(p);
	}




	@Override
	public List<Product> getAllProducts() {
		List<Product> all = repo.findAll();
		return all;
	}

	@Autowired
	private ProductRepository productRepository;

	public void deleteProduct(Long id) {
		Optional<Product> productOpt = productRepository.findById(id);

		if (productOpt.isPresent()) {
			Product product = productOpt.get();

			// ✅ Delete image file from disk
			String imagePath = product.getImagePath();
			// This must be full path
			File imageFile = new File(imagePath);

			if (imageFile.exists()) {
				imageFile.delete(); // deletes from uploads folder
			}

			// ✅ Delete the product from database
			productRepository.deleteById(id);
		} else {
			throw new RuntimeException("Product not found with ID: " + id);
		}
	}

//object mapper ,native querry 

	@Override
	public Product updateProduct(Long id, ProductDTO dto, MultipartFile image) throws IOException {
		Product p = repo.findById(id).orElseThrow();

		// Update text fields
		p.setName(dto.getName());
		p.setDescription(dto.getDescription());
		p.setPrice(dto.getPrice());
		p.setCategory(dto.getCategory());

		// If new image is provided
		if (image != null && !image.isEmpty()) {
			// Delete the old image if it exists
			if (p.getImagePath() != null) {
				File oldImage = new File(p.getImagePath());
				try {
					if (oldImage.exists()) {
						oldImage.delete(); // Delete the old file
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			// Save the new image
			String folderPath = System.getProperty("user.dir") + "/uploads";
			String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
			Path uploadDir = Paths.get(folderPath);
			Files.createDirectories(uploadDir);
			Path filePath = uploadDir.resolve(fileName);
			Files.write(filePath, image.getBytes());

			// Update new image path in DB
			p.setImagePath(filePath.toString());
		}

		return repo.save(p);
	}

	@Override
	public Product getProductById(Long id) {
		Product product = repo.findById(id).orElse(null);

		return product;

	}

}
