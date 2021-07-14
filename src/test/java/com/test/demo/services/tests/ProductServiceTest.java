package com.test.demo.services.tests;

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
import com.test.demo.dtos.ProductDto;
import com.test.demo.exceptions.ItemNotFoundException;
import com.test.demo.model.Product;
import com.test.demo.repository.ProductRepository;
import com.test.demo.services.ProductService;

@SpringBootTest
class ProductServiceTest {
   
    @Autowired
    private ProductService service;
    
    @MockBean
    private ProductRepository repository;
    
   
    
    @Test
    @DisplayName("Test getAll Success")
    void testGetAll() {
    	var products = new ArrayList<Product>();
    	products.add(new Product("1", "title", "desc","image" ,"20"));
    	products.add(new Product("2", "title", "desc","image" ,"20"));
    	products.add(new Product("3", "title", "desc","image" ,"20"));
    	when(repository.findAll()).thenReturn(products);

        // Execute the service call
        var response = service.getAllProducts();

        // Assert the response
        Assertions.assertTrue(response.size() > 0, "No product found");
    }
    
    @Test
    @DisplayName("Test getById Success")
    void testGetById() {
    	var product =  Optional.of(new Product("1", "title", "desc","image" ,"20"));
    	when(repository.findById("1")).thenReturn(product);

        // Execute the service call
        var response = service.getProduct("1");

        // Assert the response
        Assertions.assertNotNull(response, "No product found");
    }
    
    @Test
    @DisplayName("Test getById Invalid")
    void testGetByIdInvalid() {
    	when(repository.findById("1")).thenReturn(Optional.empty());

        // Execute the service call
        var exception = assertThrows(ItemNotFoundException.class, () -> service.getProduct("1"));
        // Assert the response
        Assertions.assertEquals(Messages.PRODUCT_NOT_FOUND, exception.getMessage());
    }
    
  
    @Test
    @DisplayName("Test addProduct Success")
    void testAddProduct() {
    	
    	var productDto =  new ProductDto("0", "title", "desc","image" ,"20");
    	var savedProduct =  new Product("1", productDto.getTitle(), productDto.getDescription(), productDto.getImage() ,productDto.getPrice());

    	when(repository.save(any())).thenReturn(savedProduct);

        // Execute the service call
        var response = service.addProduct(productDto);

        // Assert the response
        Assertions.assertNotNull(response, "Unable to insert Product");
        Assertions.assertEquals("1", response.getId());

    }
    @Test
    @DisplayName("Test updateProduct Success")
    void testUpdateProduct() {
    	
    	var productDto =  new ProductDto("1", "title", "desc","image" ,"20");
    	var savedProduct =  new Product("1", productDto.getTitle(), productDto.getDescription(), productDto.getImage() ,productDto.getPrice());

    	when(repository.save(any())).thenReturn(savedProduct);

        // Execute the service call
        var response = service.updateProduct(productDto);

        // Assert the response
        Assertions.assertNotNull(response, "Unable to update Product");
        Assertions.assertEquals("1", response.getId());
    }
    
    @Test
    @DisplayName("Test deleteById Success")
    void testDeleteById() {   
        // Execute the service call
    	when(repository.existsById("1")).thenReturn(true);
        var response = service.deleteProduct("1");
        // Assert the response
        Assertions.assertTrue(response, "No product found");
    }
    
    @Test
    @DisplayName("Test deleteById Invalid")
    void testDeleteByIdInvalid() {   
    	when(repository.existsById("1")).thenReturn(false);
        // Execute the service call
        var exception = assertThrows(ItemNotFoundException.class, () -> service.deleteProduct("1"));
        // Assert the response
        Assertions.assertEquals(Messages.PRODUCT_NOT_FOUND, exception.getMessage());
    }
}