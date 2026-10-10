
package com.casadonagloria.restaurante.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Pedido {

    private int idPedido;
    private int idCliente;
    private LocalDateTime fechaHora;
    private String estado;
    private BigDecimal valorTotal;

    public Pedido() {
    }

    public Pedido(int idPedido,
                  int idCliente,
                  LocalDateTime fechaHora,
                  String estado,
                  BigDecimal valorTotal) {

        this.idPedido = idPedido;
        this.idCliente = idCliente;
        this.fechaHora = fechaHora;
        this.estado = estado;
        this.valorTotal = valorTotal;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }
}
