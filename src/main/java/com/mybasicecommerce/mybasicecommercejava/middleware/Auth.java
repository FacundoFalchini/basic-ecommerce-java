package com.mybasicecommerce.mybasicecommercejava.middleware;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.mybasicecommerce.mybasicecommercejava.models.User;
import com.mybasicecommerce.mybasicecommercejava.repositories.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



 
//GenericFilterBean es una clase para crear filtros PERSONALIZADOS en Spring 
@Component

public class Auth extends GenericFilterBean {
Auth(final UserRepository userRepository){
    super();
    this.userRepository = userRepository;
}

    private final UserRepository userRepository;
   
    //El metodo doFilter se ejecuta en cada solicitud HTTP entrante. En este metodo, chequeamos que el encabezado de autorizacion empieza con Bearer o no. 
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain ) throws IOException, ServletException {
        
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        final String requestURI = request.getRequestURI();

        // Solo aplicar la lógica de autenticación para la ruta "/users/me"
        if (!"/users/me".equals(requestURI)) {
            filterChain.doFilter(request, response);
            return;
        }



        final String authorizationHeader = request.getHeader("Authorization");

        //Si no tiene el encabezo, o no empieza con Bearer retornamos que no esta autorizado. 
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
       
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("Auth Failed, no token found");
            return;
            }
      
        
        //Si existe, lo obtenemos 
        final String token = authorizationHeader.substring(7);
        System.out.println(token);
    
        try{

            Algorithm algorithm = Algorithm.HMAC256("thisiswhitneyhouston");
            JWTVerifier verifier = JWT.require(algorithm).build();

            //Este metodo da una exepcion si no es valido, de tipo JWTVerificationException
            DecodedJWT jwt = verifier.verify(token);
            System.out.println("Token:");
            System.out.println(jwt);

            String userIdFromToken = jwt.getClaim("userId").asString();
            System.out.println("User Id");
            System.out.println(userIdFromToken);

            System.out.println(userRepository);

            Optional<User> optionalUser = userRepository.findById(userIdFromToken);
            if (optionalUser.isPresent()) {
                System.out.println("entr");
                User user = optionalUser.get();
                request.setAttribute("user", user);
                // Aquí puedes utilizar el objeto User obtenido
            } else {
                // El usuario no fue encontrado en la base de datos
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.getWriter().write("Auth Failed, no user found");
                filterChain.doFilter(request, response);
                return;
            }

        }catch (TokenExpiredException exception) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("Auth Failed, Token expired");
        }catch (JWTVerificationException exception) {
            // Manejar errores de decodificación o verificación del token
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("Auth Failed, Token invalid");
        }
        catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.getWriter().write(e.getMessage());
        }
 
}
}


