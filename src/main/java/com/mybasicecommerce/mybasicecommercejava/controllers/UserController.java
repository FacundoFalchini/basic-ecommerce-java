package com.mybasicecommerce.mybasicecommercejava.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mybasicecommerce.mybasicecommercejava.error.HttpError;
import com.mybasicecommerce.mybasicecommercejava.httpEnpointsResponses.user.signUpUserError;
import com.mybasicecommerce.mybasicecommercejava.httpEnpointsResponses.user.signUpUserSucces;
import com.mybasicecommerce.mybasicecommercejava.models.Cart;
import com.mybasicecommerce.mybasicecommercejava.models.User;
import com.mybasicecommerce.mybasicecommercejava.repositories.CartRepository;
import com.mybasicecommerce.mybasicecommercejava.repositories.UserRepository;
import com.mybasicecommerce.mybasicecommercejava.tokenProvider.JwtTokenProvider;

import jakarta.servlet.http.HttpServletRequest;

import org.mindrot.jbcrypt.BCrypt;


@RestController 
@RequestMapping("/users") //indicamos la ruta base para todos los endpoints
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository; 

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    
    @PostMapping
    public ResponseEntity<Object> signUpUser(@RequestBody User user){
        try {

            if(userRepository.existsByName(user.getName())){
                throw new HttpError("Name is not available", 400);
            }

            if (userRepository.existsByEmail(user.getEmail())) {
                throw new HttpError("Email is not available", 400);
            }
        
            int saltRounds = 10; 
            String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(saltRounds));

            // Crear un nuevo usuario, user va por defecto si no lo agrego como set, y country si no lo agrego siquiera va a la base de datos. 
            User newUser = new User();
            newUser.setName(user.getName());
            newUser.setEmail(user.getEmail());
            newUser.setPassword(hashedPassword);

            userRepository.save(newUser);
      
            // Crear un nuevo carrito asociado al usuario
            Cart newCart = Cart.builder().user(newUser).build(); 
            System.out.println(newCart);
            cartRepository.save(newCart);
      
            //Creo el token
            String token = jwtTokenProvider.createToken(newUser.getId());

            //Creo la respuesta de exito 
            signUpUserSucces response = new signUpUserSucces(token, 3600000, newUser, "The sign up was succesfull");

            //Retorno 
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        //Hay que tener en cuenta las 2 posibles exepciones, las mias y las default 
        } catch (HttpError e) {
            signUpUserError response = new signUpUserError(e.getMessage());
            return ResponseEntity.status(e.getStatusCode()).body(response);
        } catch (Exception e) {
            signUpUserError response = new signUpUserError(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    @PostMapping("/login")
    public ResponseEntity<Object> logInUser(@RequestBody User user){

   
        try{
        User existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser == null) {
            throw new HttpError("We couldn't find an account with that email address", 400);
        }

        boolean isMatch = BCrypt.checkpw(user.getPassword(), existingUser.getPassword());
    

        if (isMatch == false) {
            throw new HttpError("Password is incorrect", 400);
        }


        //Creo el token
        String token = jwtTokenProvider.createToken(existingUser.getId());

        //Creo la respuesta de exito 
        signUpUserSucces response = new signUpUserSucces(token, 3600000, existingUser, "The login was succesfull");

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }catch (HttpError e) {
        signUpUserError response = new signUpUserError(e.getMessage());
        return ResponseEntity.status(e.getStatusCode()).body(response);
    } catch (Exception e) {
        signUpUserError response = new signUpUserError(e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    }

    @GetMapping("/me")
    public ResponseEntity<Object> getProfile(HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        // Aquí puedes utilizar el objeto User obtenido del filtro Auth
        return ResponseEntity.ok(user);
    }


    @GetMapping
    public List<User> getAllUsers() {
       // return new ResponseEntity<>(savedCountry, HttpStatus.CREATED);
       return userRepository.findAll();
    }
    
}
