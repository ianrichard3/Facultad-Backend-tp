package com.parcial.pruebaTp.controllers;

import com.parcial.pruebaTp.dtos.EstacionDto;
import com.parcial.pruebaTp.dtos.UbicacionDto;
import com.parcial.pruebaTp.services.EstacionService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;



@RestController
@RequestMapping("/")
public class EstacionController {

    @Autowired
    private EstacionService estacionService;

    // @PreAuthorize("hasRole('USUARIOS')")
    @GetMapping("/protegido-usuarios/estaciones")
    public ResponseEntity<List<EstacionDto>> getAll(Authentication auth) {

        System.out.println(auth.getAuthorities().iterator().next().getAuthority());
        List<EstacionDto> estacionDtos = estacionService.findAll();
        return ResponseEntity.ok(estacionDtos);
    }

    
    /*
        Consultar los datos de la estación más cercana a una ubicación provista por el
        cliente.
     */
    @GetMapping("/protegido-administradores/estaciones/{id}")
    public ResponseEntity<EstacionDto> getNearest(Authentication auth, 
        @PathVariable  Long id,  
    @RequestBody UbicacionDto ubicacionDto) {


        double latitud = ubicacionDto.getLatitud();
        double longitud = ubicacionDto.getLongitud();

        EstacionDto estacionMasCercana = estacionService.getNearestStationFromALocation(latitud, longitud);


        return ResponseEntity.ok(estacionMasCercana);

    }

    @PostMapping("/protegido-administradores/estaciones")
    public ResponseEntity<EstacionDto> add(@RequestBody EstacionDto estacionDto, Authentication auth) {

        try {
            EstacionDto addedEstacion = estacionService.save(estacionDto);
            return ResponseEntity.ok(addedEstacion);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/protegido-administradores/estaciones/{id}")
    public ResponseEntity<EstacionDto> delete(Authentication auth, @PathVariable Long id) {

        try {
            EstacionDto deletedEstacion = estacionService.deleteById(id);
            return ResponseEntity.ok(deletedEstacion);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(404).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(204).build();
        }
    }


}