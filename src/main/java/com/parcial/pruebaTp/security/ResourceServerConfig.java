 package com.parcial.pruebaTp.security;


 import org.springframework.beans.factory.annotation.Value;
// import org.springframework.cloud.gateway.route.RouteLocator;
// import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
 import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
 import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
 import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
 import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.server.SecurityWebFilterChain;

 @Configuration
 @EnableWebSecurity
 @EnableMethodSecurity
 public class ResourceServerConfig {



    // @Configuration
    // @EnableWebFluxSecurity
    // public class GWConfig {

        // @Bean
        // public RouteLocator configurarRutas(RouteLocatorBuilder builder,
        //                                     @Value("${gw-bicicletas.url-microservicio-alquileres}") String uriAlquileres,
        //                                     @Value("${gw-bicicletas.url-microservicio-estaciones}") String uriEstaciones) {
        //     return builder.routes()
        //             .route(p -> p.path("/api/alquileres/**").uri(uriAlquileres))
        //             .route(p -> p.path("/api/estaciones/**").uri(uriEstaciones))
        //             .build();
        // }


     @Bean
     public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
         http.authorizeHttpRequests(authorize ->
                 authorize
                    .requestMatchers("/publico/**")
                         .permitAll()

                 // Esta ruta puede ser accedida únicamente por usuarios autenticados con el rol de administrador
                 .requestMatchers("/protegido-administradores/**")
                 .hasRole("ADMINISTRADOR")
                 
                 .requestMatchers(HttpMethod.PUT, "/protegido-administradores/**")
                 .hasRole("ADMINISTRADOR")

                 .requestMatchers(HttpMethod.DELETE, "/protegido-administradores/**")
                 .hasRole("ADMINISTRADOR")

                 .requestMatchers(HttpMethod.POST, "/protegido-administradores/**")
                 .hasRole("ADMINISTRADOR")







                 // esta ruta puede ser accedida únicamente por usuario autenticados
                 // con el rol de usuario o administrador

                 // Metodo get
                 .requestMatchers("/protegido-usuarios/**")
                 .hasAnyRole("CLIENTE", "ADMINISTRADOR")

                 // Habilitamos metodo POST
                 .requestMatchers(HttpMethod.POST, "/protegido-usuarios/**")
                 .hasAnyRole("CLIENTE", "ADMINISTRADOR")


                 .requestMatchers(HttpMethod.PUT, "/protegido-usuarios/**")
                 .hasAnyRole("CLIENTE", "ADMINISTRADOR")

                 .requestMatchers(HttpMethod.DELETE, "/protegido-usuarios/**")
                 .hasAnyRole("CLIENTE", "ADMINISTRADOR")


         ).oauth2ResourceServer
                 (oauth2 -> oauth2.jwt(Customizer.withDefaults()));

         return http.build();
     }

     @Bean
     public JwtAuthenticationConverter jwtAuthenticationConverter() {
         var jwtAuthenticationConverter = new JwtAuthenticationConverter();
         var grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();


         // nombre del claim a analizar
         grantedAuthoritiesConverter.setAuthoritiesClaimName("authorities");
         // Prefijo en conversion por convencion de SPRING
         grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");

         // asociar conversor al token
         jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);

         return jwtAuthenticationConverter;
     }

 }
