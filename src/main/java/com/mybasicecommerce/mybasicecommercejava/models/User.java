package com.mybasicecommerce.mybasicecommercejava.models;

import org.springframework.data.annotation.Id;

//Hay algunas validaciones que Spring DataMongoDB no puede hacer por su cuenta (ejemplo, que no sea nulo, blank, longitud, y para esto usamos el jakarta validation, mientras que la de asegurar que sea unico en la base de datos si tiene)

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
//import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
//@RequiredArgsConstructor Constructor con argumentos para campos que son de tipo "final" 
@NoArgsConstructor //Constructor sin argumentos, inicializa todos los campos, incluso los final como null 
@AllArgsConstructor //Constructor con argumentos para todos los campos. 
@Document(collection = "users")
public class User {
@Id
private String id; 


    @NotBlank
    private  String name;

    @NotBlank
    @Email
    @Indexed(unique = true)
    private  String email; 

    @NotBlank
    @Size(min = 7, max = 10000)
    private String password;

    @Pattern(regexp = "admin|user|seller")
    private  String role ="user"; 

    @DBRef
    private Country country; //Opcion de referencia, no de inscrustar

}