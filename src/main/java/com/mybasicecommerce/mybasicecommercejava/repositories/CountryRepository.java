package com.mybasicecommerce.mybasicecommercejava.repositories;

//import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.mybasicecommerce.mybasicecommercejava.models.Country;

//El repositorio se hace cargo de todas las CRUD operations
@Repository
public interface CountryRepository extends MongoRepository<Country, String> {

    
} 
    
