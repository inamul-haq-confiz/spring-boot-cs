package com.test.demo.model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "products")
@Getter
@Setter
@NoArgsConstructor
public class Product {
	public Product(String id, String description, String title, String image, String price) {
		this.id = id;
		this.description = description;
		this.title = title;
		this.image = image;
		this.price = price;
	}
	@Id
	private String id;
	private String description;
	private String title;
	private String image;
	private String price;
	
}

