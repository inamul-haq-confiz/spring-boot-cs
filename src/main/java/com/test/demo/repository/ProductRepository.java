package com.test.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.test.demo.model.Product;

public interface ProductRepository extends MongoRepository<Product, String> {}

