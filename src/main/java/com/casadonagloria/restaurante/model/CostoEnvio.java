
package com.casadonagloria.restaurante.model;

import java.math.BigDecimal;

public class CostoEnvio {

    private int idCostoEnvio;
    private String distanciaDestino;
    private BigDecimal costo;
    private Integer tiempoEstimado;

    public CostoEnvio() {
    }

    public CostoEnvio(int idCostoEnvio,
                      String distanciaDestino,
                      BigDecimal costo,
                      Integer tiempoEstimado) {
        this.idCostoEnvio = idCostoEnvio;
        this.distanciaDestino = distanciaDestino;
        this.costo = costo;
        this.tiempoEstimado = tiempoEstimado;
    }

    public int getIdCostoEnvio() {
        return idCostoEnvio;
    }

    public void setIdCostoEnvio(int idCostoEnvio) {
        this.idCostoEnvio = idCostoEnvio;
    }

    public String getDistanciaDestino() {
        return distanciaDestino;
    }

    public void setDistanciaDestino(String distanciaDestino) {
        this.distanciaDestino = distanciaDestino;
    }

    public BigDecimal getCosto() {
        return costo;
    }

    public void setCosto(BigDecimal costo) {
        this.costo = costo;
    }

    public Integer getTiempoEstimado() {
        return tiempoEstimado;
    }

    public void setTiempoEstimado(Integer tiempoEstimado) {
        this.tiempoEstimado = tiempoEstimado;
    }
}
