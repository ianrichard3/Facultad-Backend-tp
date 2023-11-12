package com.parcial.pruebaTp.services;

import com.parcial.pruebaTp.dtos.AlquilerDto;
import com.parcial.pruebaTp.models.Alquiler;
import com.parcial.pruebaTp.repositories.AlquilerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AlquilerService {

    @Autowired
    private AlquilerRepository alquilerRepository;

    public List<AlquilerDto> findAll() {
        List<Alquiler> alquilerList = alquilerRepository.findAll();
        return alquilerList.stream().map(this::convertToDto).toList();
    }

    public AlquilerDto findById(Long id) {
        Optional<Alquiler> optionalAlquiler = alquilerRepository.findById(id);
        return optionalAlquiler.map(this::convertToDto).orElse(null);
    }


    public AlquilerDto save(AlquilerDto alquilerDto) {
        Alquiler alquiler = convertToEntity(alquilerDto);
        alquilerRepository.save(alquiler);
        return this.convertToDto(alquiler);
    }


    public AlquilerDto update(Long id, AlquilerDto alquilerDto) {

        Optional<Alquiler> optionalAlquiler = alquilerRepository.findById(id);

        if (optionalAlquiler.isPresent()) {
            Alquiler alquiler = convertToEntity(alquilerDto);
            alquiler.setId(id);
            Alquiler updatedAlquiler = alquilerRepository.save(alquiler);
            return this.convertToDto(updatedAlquiler);
        } else {
            return null;
        }
    }


    public AlquilerDto deleteById(Long id) {

        Optional<Alquiler> optionalAlquiler = alquilerRepository.findById(id);
        if (optionalAlquiler.isEmpty()) {
            throw new NoSuchElementException("No se encontro la orden");
        }
        alquilerRepository.deleteById(id);
        return this.convertToDto(optionalAlquiler.get());
    }






    

    // Servicios extra

    @Autowired
    EstacionService estacionService;

    @Autowired
    IdAlquilerSingleton idAlquilerSingleton;
    
    // Inicializacion de un alquiler
    public AlquilerDto iniciarAlquiler(Long id) {

        // Obtener fecha y hora actual
        LocalDateTime fechaHoraActual = LocalDateTime.now();

        // Crear el alquiler
        Alquiler createdAlquiler = new Alquiler();

        // Setear estacion de retiro (pasado el id por parametro)
        createdAlquiler.setEstacionRetiro(estacionService.findEntityById(id));

        // acceder al singleton para obtener el id
        Long ultimoId = idAlquilerSingleton.getIdAlquiler();
        idAlquilerSingleton.actualizarIdAlquiler();

        createdAlquiler.setId(ultimoId);
        // Setear estado Default 1 (inciado)
        createdAlquiler.setEstado(1);

        // Setear fecha retiro
        createdAlquiler.setFechaHoraRetiro(fechaHoraActual);


        // Log
        System.out.println("Alquiler inicializado - id: " + ultimoId);

        return convertToDto(createdAlquiler);
    }












    // Mapper Entity --> Dto
    private AlquilerDto convertToDto(Alquiler alquiler) {

        AlquilerDto alquilerDto = new AlquilerDto();

        alquilerDto.setId(alquiler.getId());
        alquilerDto.setIdCliente(alquiler.getIdCliente());
        alquilerDto.setEstado(alquiler.getEstado());
        alquilerDto.setEstacionRetiro(alquiler.getEstacionRetiro().getId());
        alquilerDto.setEstacionDevolucion(alquiler.getEstacionDevolucion().getId());
        alquilerDto.setFechaHoraDevolucion(alquiler.getFechaHoraDevolucion());
        alquilerDto.setFechaHoraRetiro(alquiler.getFechaHoraRetiro());
        alquilerDto.setMonto(alquiler.getMonto());
        alquilerDto.setIdTarifa(alquiler.getTarifa().getId());
        return alquilerDto;

    }


    // Mapper Dto --> Entity
    private Alquiler convertToEntity(AlquilerDto alquilerDto) {

        Alquiler alquiler = new Alquiler();

        alquiler.setId(alquilerDto.getId());
        alquiler.setIdCliente(alquilerDto.getIdCliente());
        alquiler.setEstado(alquilerDto.getEstado());
        alquiler.setFechaHoraDevolucion(alquilerDto.getFechaHoraDevolucion());
        alquiler.setFechaHoraRetiro(alquilerDto.getFechaHoraRetiro());
        alquiler.setMonto(alquilerDto.getMonto());
        return alquiler;

    }

}
