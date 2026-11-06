package com.casadonagloria.restaurante.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "costo_envio")
public class CostoEnvio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_costo_envio")
    private int idCostoEnvio;

    @Column(name = "distancia_destino", nullable = false)
    private String distanciaDestino;

    @Column(nullable = false)
    private BigDecimal costo;

    @Column(name = "tiempo_estimado")
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
