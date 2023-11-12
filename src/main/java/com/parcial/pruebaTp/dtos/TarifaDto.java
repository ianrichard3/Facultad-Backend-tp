package com.parcial.pruebaTp.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TarifaDto {
    private long id;
    private long tipoTarifa;
    private String definicion;
    private long diaSemana;
    private long diaMes;
    private long mes;
    private long anio;
    private double montoFijoAlquiler;
    private double montoMinutoFraccion;
    private double montoKm;
    private double montoHora;
}
