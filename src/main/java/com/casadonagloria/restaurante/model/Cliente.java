
package com.casadonagloria.restaurante.model;

public class Cliente {

    private int idCliente;
    private String nombre;
    private String telefono;
    private String direccion;
    private String barrio;
    private String descripcionDir;

    public Cliente() {
    }

    public Cliente(int idCliente, String nombre,
                   String telefono, String direccion,
                   String barrio, String descripcionDir) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
        this.barrio = barrio;
        this.descripcionDir = descripcionDir;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public String getDescripcionDir() {
        return descripcionDir;
    }

    public void setDescripcionDir(String descripcionDir) {
        this.descripcionDir = descripcionDir;
    }
}
