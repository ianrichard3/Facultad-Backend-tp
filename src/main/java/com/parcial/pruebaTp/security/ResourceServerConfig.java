// package com.parcial.pruebaTp.security;


// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.Customizer;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
// import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
// import org.springframework.security.web.SecurityFilterChain;

// @Configuration
// @EnableWebSecurity
// public class ResourceServerConfig {

//     @Bean
//     public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//         http.authorizeHttpRequests(authorize ->
//                 authorize
//                    .requestMatchers("/publico/**")
//                         .permitAll()

//                 // Esta ruta puede ser accedida únicamente por usuarios autenticados con el rol de administrador
//                 .requestMatchers("/protegido-administradores/**")
//                 .hasRole("ADMIN")

//                 // esta ruta puede ser accedida únicamente por usuario autenticados
//                 // con el rol de usuario o administrador
//                 .requestMatchers("/protegido-usuarios/**")
//                 .hasAnyRole("USUARIO", "ADMIN")


//         ).oauth2ResourceServer
//                 (oauth2 -> oauth2.jwt(Customizer.withDefaults()));

//         return http.build();
//     }

//     @Bean
//     public JwtAuthenticationConverter jwtAuthenticationConverter() {
//         var jwtAuthenticationConverter = new JwtAuthenticationConverter();
//         var grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();


//         // nombre del claim a analizar
//         grantedAuthoritiesConverter.setAuthoritiesClaimName("authorities");
//         // Prefijo en conversion por convencion de SPRING
//         grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");

//         // asociar conversor al token
//         jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);

//         return jwtAuthenticationConverter;
//     }

// }
