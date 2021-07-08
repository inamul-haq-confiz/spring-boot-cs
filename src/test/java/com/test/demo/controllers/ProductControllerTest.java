package com.test.demo.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

import com.test.demo.constants.Messages;
import com.test.demo.controller.ProductsController;
import com.test.demo.dtos.ProductDto;
import com.test.demo.model.Product;
import com.test.demo.repository.ProductRepository;
import com.test.demo.services.ProductService;

@SpringBootTest
public class ProductControllerTest {
   
	@MockBean
    private ProductService service;
    
    @Autowired
    private ProductsController controller;
    
   
    
    @Test
    @DisplayName("Test getAll Success")
    void testGetAll() {
    	var products = new ArrayList<ProductDto>();
    	products.add(new ProductDto("1", "title", "desc","image" ,"20"));
    	products.add(new ProductDto("2", "title", "desc","image" ,"20"));
    	products.add(new ProductDto("3", "title", "desc","image" ,"20"));
    	when(service.getAllProducts()).thenReturn(products);

        // Execute the service call
        var response = controller.get();

        // Assert the response
        Assertions.assertTrue(response.size() > 0, "No product found");
    }
    
    @Test
    @DisplayName("Test getById Success")
    void testGetById() {
    	var product =  new ProductDto("1", "title", "desc","image" ,"20");
    	when(service.getProduct("1")).thenReturn(product);

        // Execute the service call
        var response = controller.get("1");

        // Assert the response
        Assertions.assertNotNull(response, "No product found");
    }
    
  
    @Test
    @DisplayName("Test addProduct Success")
    void testAddProduct() {
    	
    	var productDto =  new ProductDto("0", "title", "desc","image" ,"20");
    	var savedProduct =  new ProductDto("1", productDto.getTitle(), productDto.getDescription(), productDto.getImage() ,productDto.getPrice());

    	when(service.addProduct(any())).thenReturn(savedProduct);

        // Execute the service call
        var response = controller.post(productDto);

        // Assert the response
        Assertions.assertNotNull(response, "Unable to insert Product");
        Assertions.assertEquals(response.getId(), "1");

    }
    @Test
    @DisplayName("Test updateProduct Success")
    void testUpdateProduct() {
    	
    	var productDto =  new ProductDto("1", "title", "desc","image" ,"20");
    	var savedProduct =  new ProductDto("1", productDto.getTitle(), productDto.getDescription(), productDto.getImage() ,productDto.getPrice());

    	when(service.updateProduct(any())).thenReturn(savedProduct);

        // Execute the service call
        var response = controller.put(productDto.getId(), productDto);

        // Assert the response
        Assertions.assertNotNull(response, "Unable to update Product");
        Assertions.assertEquals(response.getId(), "1");
    }
    
    @Test
    @DisplayName("Test deleteById Success")
    void testDeleteById() {   
        // Execute the service call
    	when(service.deleteProduct("1")).thenReturn(true);
        var response = controller.delete("1");
        // Assert the response
        Assertions.assertTrue(response, "No product found");
    }   
    
}