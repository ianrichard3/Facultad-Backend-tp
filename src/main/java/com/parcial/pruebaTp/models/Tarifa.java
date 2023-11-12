package com.parcial.pruebaTp.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Table(name = "tarifas") // Nombre de la tabla en la base de datos
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Tarifa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "tipo_tarifa")
    private long tipoTarifa;

    @Column(name = "definicion")
    private String definicion;

    @Column(name = "dia_semana")
    private long diaSemana;

    @Column(name = "dia_mes")
    private long diaMes;

    @Column(name = "mes")
    private long mes;

    @Column(name = "anio")
    private long anio;

    @Column(name = "monto_fijo_alquiler")
    private double montoFijoAlquiler;

    @Column(name = "monto_minuto_fraccion")
    private double montoMinutoFraccion;

    @Column(name = "monto_km")
    private double montoKm;

    @Column(name = "monto_hora")
    private double montoHora;

}
