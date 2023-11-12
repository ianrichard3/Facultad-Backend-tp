package com.parcial.pruebaTp.controllers;

import com.parcial.pruebaTp.dtos.AlquilerDto;
import com.parcial.pruebaTp.dtos.UbicacionDto;
import com.parcial.pruebaTp.services.AlquilerService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.NoSuchElementException;



@RestController
@RequestMapping("/api/alquileres")
public class AlquilerController {

    @Autowired
    private AlquilerService alquilerService;

    @GetMapping
    public ResponseEntity<List<AlquilerDto>> getAll() {
        List<AlquilerDto> alquilerDtos = alquilerService.findAll();
        return ResponseEntity.ok(alquilerDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlquilerDto> getNearest(@PathVariable  Long id)
    {

        try {
            AlquilerDto alqulerDto = alquilerService.findById(id);
            return ResponseEntity.ok(alqulerDto);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    // Aca iria el iniciar el alquiler de una biciletra desde una estacion dada

    @PostMapping
    public ResponseEntity<AlquilerDto> add(@RequestParam Long idEstacion){

        AlquilerDto createdAlquiler = alquilerService.iniciarAlquiler(idEstacion);


        return ResponseEntity.ok(createdAlquiler);
    }






    // DEFAULT POST 


    // @PostMapping
    // public ResponseEntity<AlquilerDto> add(@RequestBody AlquilerDto alquilerDto) {

    //     try {
    //         AlquilerDto addedAlquiler = alquilerService.save(alquilerDto);
    //         return ResponseEntity.ok(addedAlquiler);
    //     } catch (Exception e) {
    //         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    //     }
    // }



    // Aca iria el finalizar un alquiler en curso La devolucion
    // Aca tambien tendria que acceder a la API la vaina, donde seria aca?? o donde
    @PutMapping("/{id}")
    public ResponseEntity<AlquilerDto> update(@PathVariable Long id, @RequestBody AlquilerDto alquilerDto) {
        try {
            AlquilerDto updatedAlquiler = alquilerService.update(id, alquilerDto);
            return ResponseEntity.ok(updatedAlquiler);

        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<AlquilerDto> delete(@PathVariable Long id) {

        try {
            AlquilerDto deletedAlquiler = alquilerService.deleteById(id);
            return ResponseEntity.ok(deletedAlquiler);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(404).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(204).build();
        }
    }


}