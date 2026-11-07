package com.casadonagloria.restaurante.exception;

public class ReglaNegocioException extends RuntimeException {

    // Excepcion para operaciones que incumplen una regla de negocio
    public ReglaNegocioException(String mensaje) {
        super(mensaje);
    }
}
