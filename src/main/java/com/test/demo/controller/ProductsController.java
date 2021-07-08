package com.test.demo.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.test.demo.dtos.ProductDto;
import com.test.demo.dtos.response.ResponseDto;
import com.test.demo.services.ProductService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;


@RestController
@RequestMapping("api/products")
public class ProductsController {
	@Autowired
	private ProductService productService;
	@GetMapping
    @ApiOperation(authorizations = {@Authorization(value = "jwtToken")}, value = "Get all products")
	public List<ProductDto> get()
	{		
		return productService.getAllProducts();
	}
	@GetMapping("/{id}")
    @ApiOperation(authorizations = {@Authorization(value = "jwtToken")}, value = "Get product by Id")
	public ProductDto get(@PathVariable String id)
	{
		return productService.getProduct(id);		
	}
	@PostMapping
    @ApiOperation(authorizations = {@Authorization(value = "jwtToken")}, value = "Save product")
	public ProductDto post(@RequestBody ProductDto product)
	{
		return productService.addProduct(product);
	}
	@PutMapping("/{id}")
    @ApiOperation(authorizations = {@Authorization(value = "jwtToken")}, value = "Update Product")
	public ProductDto put(@PathVariable String id, @RequestBody ProductDto product)
	{ 
		return productService.updateProduct(product);
	}
    @ApiOperation(authorizations = {@Authorization(value = "jwtToken")}, value = "Delete Product")
	@DeleteMapping("/{id}")
	public boolean delete(@PathVariable String id)
	{
		return productService.deleteProduct(id);
		
	}
	

}
