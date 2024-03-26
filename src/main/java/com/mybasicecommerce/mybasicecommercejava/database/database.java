package com.mybasicecommerce.mybasicecommercejava.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
//import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.context.annotation.Configuration;

import com.mongodb.MongoException;
//import com.mongodb.client.MongoIterable;

@Configuration
public class database {
    
    @Autowired
    private MongoDatabaseFactory mongoDbFactory;

    //@Autowired
    //private MongoTemplate mongoTemplate;

    @EventListener(ApplicationReadyEvent.class)
    public void checkDbConnection() {
        try {
            //mongoDbFactory.getDb();
            mongoDbFactory.getMongoDatabase();
            System.out.println("Conexión a la base de datos establecida correctamente.");
            // Otra lógica que deseas realizar después de la conexión exitosa.

            //MongoIterable<String> collectionNames = mongoDbFactory.getMongoDatabase().listCollectionNames();
            ///System.out.println("Colecciones en la base de datos:");
            //for (String collectionName : collectionNames) {
            //System.out.println(collectionName);
            //}

        } catch (MongoException ex) {
            System.out.println("Error al conectar a la base de datos: " + ex.getMessage());
            // Otra lógica para manejar el error de conexión.
        }
    }

}
