package com.parcial.pruebaTp.controllers;

import com.parcial.pruebaTp.dtos.EstacionDto;
import com.parcial.pruebaTp.dtos.UbicacionDto;
import com.parcial.pruebaTp.services.EstacionService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;



@RestController
@RequestMapping("/api/estacions")
public class EstacionController {

    @Autowired
    private EstacionService estacionService;

    @GetMapping
    public ResponseEntity<List<EstacionDto>> getAll() {
        List<EstacionDto> estacionDtos = estacionService.findAll();
        return ResponseEntity.ok(estacionDtos);
    }

    
    /*
        Consultar los datos de la estación más cercana a una ubicación provista por el
        cliente.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EstacionDto> getNearest(
        @PathVariable  Long id,  
    @RequestBody UbicacionDto ubicacionDto) {


        double latitud = ubicacionDto.getLatitud();
        double longitud = ubicacionDto.getLongitud();

        EstacionDto estacionMasCercana = estacionService.getNearestStationFromALocation(latitud, longitud);


        return ResponseEntity.ok(estacionMasCercana);

    }


}