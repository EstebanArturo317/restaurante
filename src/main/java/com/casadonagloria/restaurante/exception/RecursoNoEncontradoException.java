
package com.casadonagloria.restaurante.exception;

public class RecursoNoEncontradoException extends RuntimeException {

    // Excepcion para registros que no existen en la base de datos
    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
