package com.parcial.pruebaTp.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parcial.pruebaTp.dtos.AlquilerDto;
import com.parcial.pruebaTp.dtos.TarifaDto;
import com.parcial.pruebaTp.externalServices.CurrencyConversionService;
import com.parcial.pruebaTp.models.Alquiler;
import com.parcial.pruebaTp.models.Estacion;
import com.parcial.pruebaTp.models.Tarifa;
import com.parcial.pruebaTp.repositories.AlquilerRepository;

import ch.qos.logback.core.util.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CurrencyEditor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
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

    public List<AlquilerDto> findAllFiltroEstado(Long estado) {

        if (estado == null) return findAll();

        List<Alquiler> alquilerList = alquilerRepository.findAllByEstado(estado);
        return alquilerList.stream().map(this::convertToDto).toList();
    }

    public AlquilerDto findById(Long id) {
        Optional<Alquiler> optionalAlquiler = alquilerRepository.findById(id);
        return optionalAlquiler.map(this::convertToDto).orElse(null);
    }

    public AlquilerDto findByIdEnDivisa(Long id, String divisa) {

        AlquilerDto encontrado = findById(id);

        if (divisa == null) return encontrado;

        Double nuevoMonto = currecyConversionService.convertCurrency(encontrado.getMonto(), divisa);
        encontrado.setMonto(nuevoMonto);

        return encontrado;

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

    // @Autowired
    // IdAlquilerSingleton idAlquilerSingleton;
    
    // Inicializacion de un alquiler
    public AlquilerDto iniciarAlquiler(Long id) {

        // Obtener fecha y hora actual
        LocalDateTime fechaHoraActual = LocalDateTime.now();

        // Crear el alquiler
        Alquiler createdAlquiler = new Alquiler();

        // Setear estacion de retiro (pasado el id por parametro)
        createdAlquiler.setEstacionRetiro(estacionService.findEntityById(id));

        // acceder al singleton para obtener el id
        IdAlquilerSingleton idAlquilerSingleton = IdAlquilerSingleton.getInstancia(alquilerRepository);
        Long ultimoId = idAlquilerSingleton.getIdAlquiler();
        idAlquilerSingleton.actualizarIdAlquiler();

        createdAlquiler.setId(ultimoId);
        // Setear estado Default 1 (inciado)
        createdAlquiler.setEstado(1);

        // Setear fecha retiro
        createdAlquiler.setFechaHoraRetiro(fechaHoraActual);



        // Guardar Aluiler en la base de datos
        alquilerRepository.save(createdAlquiler);


        // Log
        System.out.println("Alquiler inicializado - id: " + ultimoId);


        
        // ACa se deberia mostrar un dto con los datos que importan, no con caulquier cosa pero no importa
        return new AlquilerDto();
    }




    @Autowired
    TarifaService tarifaService;

    @Autowired
    CurrencyConversionService currecyConversionService;

    // Finalizacion de alquiler
    public AlquilerDto finalizarAlquiler(Long id, Long idEstacionDestino, String divisa) {

        Optional<Alquiler> optionalAlqulerAFinalizar = alquilerRepository.findById(id);
        if (!optionalAlqulerAFinalizar.isPresent())
        {
            return null;
        }

        // Se obtiene el alquiler a finalizar (el cual tiene la estacion de salida seteada)
        Alquiler alquilerAFinalizar = optionalAlqulerAFinalizar.get();

        // Obtener estacion destino
        Estacion estacionDestino = estacionService.findEntityById(idEstacionDestino);

        // Seteamos la estacion destino al alquiler a finalizar
        alquilerAFinalizar.setEstacionDevolucion(estacionDestino);

        

        // Obtenemos la fecha de finalizacion
        LocalDateTime fechaFinalizacion = LocalDateTime.now();

        // Seteamos la fechaHora de devolucion
        alquilerAFinalizar.setFechaHoraDevolucion(fechaFinalizacion);


        // Seteamos el estado como finalizado
        alquilerAFinalizar.setEstado(2L);



        // obtenemos la fecha de retiro
        LocalDateTime fechaRetiro = alquilerAFinalizar.getFechaHoraRetiro();

        // Obtenemos los minutos pasados desde que se retiro la bicicleta
        int duracionAlquiler = (int) fechaRetiro.until(fechaFinalizacion, ChronoUnit.MINUTES);


        // Obtenemos las horas y minutos
        int horasEnteras = (int) duracionAlquiler/60;
        

		int minutosRestantes = duracionAlquiler - horasEnteras*60;

        System.out.println("Tiempo Original: " + horasEnteras + " horas y " + minutosRestantes +" minutos");

		if (minutosRestantes > 30) {
			horasEnteras += 1;
			minutosRestantes = 0;
		}


        System.out.println("Tiempo a cobrar: " + horasEnteras + " horas y " + minutosRestantes +" minutos");

        


        // Obtenemos la distancia recorrida
        double distanciaRecorridaEnMetros = estacionService.distanceBetweenStations(alquilerAFinalizar.getEstacionRetiro(), alquilerAFinalizar.getEstacionDevolucion());
        double distanciaRecorridaEnKM = distanciaRecorridaEnMetros/1000;

        System.out.println("DISTANCIA RECORRIDA: "+ distanciaRecorridaEnKM +" km");




        // Ahora lo que hay que hacer es obtener el importe de cada cosa (fijo, hora, minutos, y kilometros)
        // Se obtendra accediendo a la BD a la tabla de tarifas y comparandolo con la fecha actual



        // Obtenemos el numero de dia del mes, el numero de dia de la semana, y el numero del mes de la fecha de finalizacion
        int diaDelMes = fechaFinalizacion.getDayOfMonth();
        int diaDeSemana = fechaFinalizacion.getDayOfWeek().getValue();
        int nroMes = fechaFinalizacion.getMonthValue();

        // Le pedimos a tarifaService que nos busque una tarifa con los datos de fechaFinalizacion


        
        TarifaDto tarifa = tarifaService.getTarifaPorDiaYMes(diaDelMes, nroMes, diaDeSemana);

        
        System.out.println("HASTA ACA SE LLEGO");


        // Calculamos el monto segun la tarifa
        double montoAlquiler = tarifa.getMontoFijoAlquiler();
        montoAlquiler += tarifa.getMontoKm() * distanciaRecorridaEnKM;
        montoAlquiler += tarifa.getMontoHora() * horasEnteras;
        montoAlquiler += tarifa.getMontoMinutoFraccion() * minutosRestantes;


        // Seteamos el monto para la BD
        alquilerAFinalizar.setMonto(montoAlquiler);



        // Guardamos en la base de datos el alquiler finalizado
        alquilerRepository.save(alquilerAFinalizar);





        // Conversion de moneda

        // Convertimos a dto el alquiler finalizado
        AlquilerDto alquilerDto = convertToDto(alquilerAFinalizar);

        // Si no se pasa divisa, no se convierte nada
        if (divisa != null) {
            // String montoConvertidoString = currecyConversionService.convertCurrency(montoAlquiler, divisa);
            // ObjectMapper objectMapper = new ObjectMapper();
            // try {
            //     HashMap<String, Object> json = objectMapper.readValue(montoConvertidoString, HashMap.class);
            //     Double montoConvertido = (Double) json.get("importe");
            //     alquilerDto.setMonto(montoConvertido);
            // }
            // catch (Exception e) {System.out.println(e.getStackTrace());};

            Double montoConvertido = currecyConversionService.convertCurrency(montoAlquiler, divisa);
            alquilerDto.setMonto(montoConvertido);

            
        }

        return alquilerDto;
    }











    // Mapper Entity --> Dto
    private AlquilerDto convertToDto(Alquiler alquiler) {

        AlquilerDto alquilerDto = new AlquilerDto();

        alquilerDto.setId(alquiler.getId());
        alquilerDto.setIdCliente(alquiler.getIdCliente());
        alquilerDto.setEstado(alquiler.getEstado());
        alquilerDto.setEstacionRetiro(alquiler.getEstacionRetiro().getId());

        if (alquiler.getEstacionDevolucion() == null) alquilerDto.setEstacionDevolucion(0);
        else alquilerDto.setEstacionDevolucion(alquiler.getEstacionDevolucion().getId());

        alquilerDto.setFechaHoraDevolucion(alquiler.getFechaHoraDevolucion());
        alquilerDto.setFechaHoraRetiro(alquiler.getFechaHoraRetiro());
        alquilerDto.setMonto(alquiler.getMonto());

        if (alquiler.getTarifa() == null) alquilerDto.setIdTarifa(0);
        else alquilerDto.setIdTarifa(alquiler.getTarifa().getId());

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
