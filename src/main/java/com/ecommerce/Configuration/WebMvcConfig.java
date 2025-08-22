package com.ecommerce.Configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {// to map the url to image 

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		String uploadPath = "file:" + System.getProperty("user.dir") + "/uploads/";//this is File System path 
		registry.addResourceHandler("/uploads/**").addResourceLocations(uploadPath);// it 
	}
}
