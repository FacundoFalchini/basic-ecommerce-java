package com.mybasicecommerce.mybasicecommercejava.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mybasicecommerce.mybasicecommercejava.repositories.CartRepository;

@RestController
@RequestMapping("/carts")
public class CartController {

    @Autowired
    private CartRepository cartRepository; 
    
}
