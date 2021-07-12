package com.test.demo.services;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.test.demo.constants.Messages;
import com.test.demo.dtos.ProductDto;
import com.test.demo.model.Product;
import com.test.demo.repository.ProductRepository;

@Service
public class ProductService extends BaseService{

	@Autowired
	private ProductRepository repository;
	public List<ProductDto> getAllProducts() {
		
		var products = repository.findAll();
		return products.stream().map(x -> mapTo(x, ProductDto.class)).collect(Collectors.toList());		
	}

	public ProductDto getProduct(String id){
		var product = repository.findById(id);
		if(product == null) { throw new RuntimeException(Messages.PRODUCT_NOT_FOUND);}
		return mapTo(product.get(), ProductDto.class);
	}

	
	public ProductDto addProduct(ProductDto product) {		
		var productToInsert = mapTo(product, Product.class);
		var newProduct = repository.save(productToInsert);		
		return mapTo(newProduct, ProductDto.class);	
	}

	
	public ProductDto updateProduct(ProductDto product) {		
		var productToUpdate = mapTo(product, Product.class);
		var newProduct = repository.save(productToUpdate);	
		return mapTo(newProduct, ProductDto.class);	
		}

	
	public boolean deleteProduct(String id) {
		if(repository.findById(id) == null)
		{throw new RuntimeException(Messages.PRODUCT_NOT_FOUND);}
		repository.deleteById(id);
		return true;
	}
}
