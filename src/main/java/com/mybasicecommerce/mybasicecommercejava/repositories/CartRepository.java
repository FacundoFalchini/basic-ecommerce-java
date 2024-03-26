package com.mybasicecommerce.mybasicecommercejava.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mybasicecommerce.mybasicecommercejava.models.Cart;


@Repository
public interface CartRepository extends MongoRepository<Cart,String> {

    
} 
