package com.mybasicecommerce.mybasicecommercejava.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.mybasicecommerce.mybasicecommercejava.models.User;

//El repositorio se hace cargo de todas las CRUD operations
@Repository
public interface UserRepository extends MongoRepository<User, String> {
    boolean existsByName(String name);
    boolean existsByEmail(String name);
    User findByEmail(String email);

    
} 
    