package com.casadonagloria.restaurante.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "detalle_pedido")
public class DetallePedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle")
    private int idDetalle;


    @NotNull(message = "El pedido es obligatorio")
    @ManyToOne
    @JoinColumn(name = "id_pedido", nullable = false)
    private Pedido pedido;


    @NotNull(message = "El plato es obligatorio")
    @ManyToOne
    @JoinColumn(name = "id_plato", nullable = false)
    private Plato plato;


    @Positive(message = "La cantidad debe ser mayor que cero")
    @Column(nullable = false)
    private int cantidad;


    @PositiveOrZero(message = "El subtotal no puede ser negativo")
    @Column(nullable = false)
    private int subtotal;


    public DetallePedido() {}
    
    public int getIdDetalle() { return idDetalle; }
    public void setIdDetalle(int idDetalle) { this.idDetalle = idDetalle; }
    public Pedido getPedido() { return pedido; }
    public void setPedido(Pedido pedido) { this.pedido = pedido; }
    public Plato getPlato() { return plato; }
    public void setPlato(Plato plato) { this.plato = plato; }
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public int getSubtotal() { return subtotal; }
    public void setSubtotal(int subtotal) { this.subtotal = subtotal; }
}
