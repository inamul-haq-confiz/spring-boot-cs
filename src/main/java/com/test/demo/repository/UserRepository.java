package com.test.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.test.demo.model.Product;
import com.test.demo.model.User;

public interface UserRepository extends MongoRepository<User, String> {
    User findByUsername(String username);	
}

