package com.mybasicecommerce.mybasicecommercejava.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mybasicecommerce.mybasicecommercejava.models.Country;
import com.mybasicecommerce.mybasicecommercejava.repositories.CountryRepository;

@RestController
@RequestMapping("/countries")
public class CountryController {

    //Crea un objeto y lo mapea
    @Autowired
    private CountryRepository countryRepository;
    

    @PostMapping
    public ResponseEntity<Country> createCountry(@RequestBody Country country) {
     // Guardar el país en la base de datos usando el repositorio
    Country savedCountry = countryRepository.save(country);
    // Devolver el país creado y el código de estado 201 (Created)
        return new ResponseEntity<>(savedCountry, HttpStatus.CREATED);
    }
}
