package com.parcial.pruebaTp.services;

import org.springframework.stereotype.Service;

@Service
public class IdAlquilerSingleton {
    private long idAlquiler;
    private static IdAlquilerSingleton instancia;

    IdAlquilerSingleton () {
        idAlquiler = 1;
    }

    public long getIdAlquiler() {
        return idAlquiler;
    }

    public void actualizarIdAlquiler() {
        idAlquiler += 1;
    }

    public IdAlquilerSingleton getInstancia(){
        if (instancia == null) {
            instancia = new IdAlquilerSingleton();
        }
        return instancia;
    }
}
