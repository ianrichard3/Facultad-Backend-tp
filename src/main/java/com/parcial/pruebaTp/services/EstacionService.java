package com.parcial.pruebaTp.services;

import com.parcial.pruebaTp.dtos.EstacionDto;
import com.parcial.pruebaTp.models.Estacion;
import com.parcial.pruebaTp.repositories.EstacionRepository;
import com.parcial.pruebaTp.support.Point2D;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class EstacionService {

    @Autowired
    private EstacionRepository estacionRepository;

    public List<EstacionDto> findAll() {
        List<Estacion> estacionList = estacionRepository.findAll();
        return estacionList.stream().map(this::convertToDto).toList();
    }

    public EstacionDto findById(Long id) {
        Optional<Estacion> optionalEstacion = estacionRepository.findById(id);
        return optionalEstacion.map(this::convertToDto).orElse(null);
    }


    public EstacionDto save(EstacionDto estacionDto) {
        Estacion estacion = convertToEntity(estacionDto);
        estacionRepository.save(estacion);
        return this.convertToDto(estacion);
    }


    public EstacionDto update(Long id, EstacionDto estacionDto) {

        Optional<Estacion> optionalEstacion = estacionRepository.findById(id);

        if (optionalEstacion.isPresent()) {
            Estacion estacion = convertToEntity(estacionDto);
            estacion.setId(id);
            Estacion updatedEstacion = estacionRepository.save(estacion);
            return this.convertToDto(updatedEstacion);
        } else {
            return null;
        }
    }


    public EstacionDto deleteById(Long id) {

        Optional<Estacion> optionalEstacion = estacionRepository.findById(id);
        if (optionalEstacion.isEmpty()) {
            throw new NoSuchElementException("No se encontro la orden");
        }
        estacionRepository.deleteById(id);
        return this.convertToDto(optionalEstacion.get());
    }




    // Get estacion mas cercana a un punto
    public EstacionDto getNearestStationFromALocation(double latitud, double longitud) {


        Point2D userLocation = new Point2D(latitud, longitud); 


        List<Estacion> allStations = estacionRepository.findAll();
        int nearestIndex = 0;
        double currentNearest = 0.0;
        
        for (int i = 0 ; i < allStations.size() ; i++) {
            Estacion station = allStations.get(i);
            Point2D stationLocation = new Point2D(station.getLatitud(), station.getLongitud());
            double curDistance = Point2D.distanceBetweenPoints(stationLocation, userLocation);

            // if (i == 0) {
            //     currentNearest = curDistance;
            // }

            if ( curDistance < currentNearest || i == 0) {
                currentNearest = curDistance;
                nearestIndex = i;
            }
        }

        Estacion nearestStation = allStations.get(nearestIndex);
        System.out.println(convertToDto(nearestStation));
        return convertToDto(nearestStation); 

    }








    // Mapper Entity --> Dto
    private EstacionDto convertToDto(Estacion estacion) {

        EstacionDto estacionDto = new EstacionDto();

        estacionDto.setId(estacion.getId());
        estacionDto.setNombre(estacion.getNombre());
        estacionDto.setFechaHoraCreacion(estacion.getFechaHoraCreacion());
        estacionDto.setLatitud(estacion.getLatitud());
        estacionDto.setLongitud(estacion.getLongitud());
        return estacionDto;

    }


    // Mapper Dto --> Entity
    private Estacion convertToEntity(EstacionDto estacionDto) {

        Estacion estacion = new Estacion();

        estacion.setId(estacionDto.getId());
        estacion.setNombre(estacionDto.getNombre());
        estacion.setFechaHoraCreacion(estacionDto.getFechaHoraCreacion());
        estacion.setLatitud(estacionDto.getLatitud());
        estacion.setLongitud(estacionDto.getLongitud());
        return estacion;
    }
    
    
    
    
    


}
