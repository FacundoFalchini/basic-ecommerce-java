package com.mybasicecommerce.mybasicecommercejava.middleware.protectedRoutes;


import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import com.mybasicecommerce.mybasicecommercejava.middleware.Auth;


 
@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<Auth> authFilterRegistration() {
        FilterRegistrationBean<Auth> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new Auth());
        registrationBean.addUrlPatterns("/users/me"); // Aplica el filtro solo a esta ruta
        return registrationBean;
    }
   
}
 








//Los filtros son lo PRIMERO que se ejecutan0