package com.casadonagloria.restaurante.model;

public class Plato {
    private int idPlato;
    private String nombre;
    private String descripcion;
    private double precio;
    private boolean estaActivo;

    public Plato() {}

    public Plato(int idPlato, String nombre, String descripcion, double precio, boolean estaActivo) {
        this.idPlato = idPlato;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.estaActivo = estaActivo;
    }

    public Plato(String nombre, String descripcion, double precio, boolean estaActivo) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.estaActivo = estaActivo;
    }

    public int getIdPlato() { return idPlato; }
    public void setIdPlato(int idPlato) { this.idPlato = idPlato; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
    public boolean isEstaActivo() { return estaActivo; }
    public void setEstaActivo(boolean estaActivo) { this.estaActivo = estaActivo; }
}