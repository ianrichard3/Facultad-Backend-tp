package com.parcial.pruebaTp;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

import com.parcial.pruebaTp.externalServices.CurrencyConversionService;
import com.parcial.pruebaTp.support.Point2D;

@SpringBootApplication
public class PruebaTpApplication {

	public static void main(String[] args) {
		SpringApplication.run(PruebaTpApplication.class, args);



		// CurrecyConversionService currecyConversionService = new CurrecyConversionService();
		// currecyConversionService.restTemplate = new RestTemplate();
		// String cambio = currecyConversionService.convertCurrency(10000,"PEN");
		// System.out.println(cambio);





		// // Obtenemos la fecha de finalizacion
        // LocalDateTime fechaFinalizacion = LocalDateTime.now();

		// LocalDateTime fechaInicio = LocalDateTime.of(2023,11, 11, 15, 30);

        // int duracionAlquiler = (int) fechaInicio.until(fechaFinalizacion, ChronoUnit.MINUTES);

		
		// // Obtenemos las horas y minutos
        // int horasEnteras = (int) duracionAlquiler/60;

		// int minutosRestantes = duracionAlquiler - horasEnteras*60;

		// if (minutosRestantes > 30) {
		// 	horasEnteras += 1;
		// 	minutosRestantes = 0;
		// }

		// System.out.println(fechaInicio.getDayOfWeek().getValue());

		// Obtener la Distancia Recorrida



		


		// System.out.println(duracionAlquiler);
		// System.out.println(horasEnteras);
		// System.out.println(minutosRestantes);

	}

}


// http://localhost:8080/swagger-ui/index.html
