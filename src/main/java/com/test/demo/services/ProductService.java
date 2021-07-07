package com.test.demo.services;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.test.demo.dtos.ProductDto;
import com.test.demo.model.Product;
import com.test.demo.repository.ProductRepository;

@Service
public class ProductService{

	@Autowired
	private ProductRepository repository;
	@Autowired
	private ModelMapper mapper;
	public List<ProductDto> getAllProducts() {
		
		var products = repository.findAll();
		return products.stream().map(this::mapToDto).collect(Collectors.toList());		
	}

	public ProductDto getProduct(String id) {
		var product = repository.findById(id);
		return mapToDto(product.get());
	}

	
	public ProductDto addProduct(ProductDto product) {		
		var productToInsert = mapToModel(product);
		var newProduct = repository.save(productToInsert);		
		return mapToDto(newProduct);
	}

	
	public ProductDto updateProduct(ProductDto product) {
		var productToUpdate = mapToModel(product);
		var newProduct = repository.save(productToUpdate);	
		return mapToDto(newProduct);	
		}

	
	public boolean deleteProduct(String id) {
		repository.deleteById(id);
		return true;
	}
	
	private ProductDto mapToDto(Product product)
	{
		return mapper.map(product, ProductDto.class);
	}
	private Product mapToModel(ProductDto product)
	{
		return mapper.map(product, Product.class);
	}

}
