package com.example.nacho.proyectosdm.modelo;

/**
 * Created by Nacho on 15/11/2017.
 */

public class Comida {

    private Long id;
    private String correo_usuario;
    private String nombre;
    private int raciones;
    private double precio;
    private String descripcion;
    private boolean salado;
    private boolean dulce;
    private boolean vegetariano;
    private boolean celiaco;
    private Categoria categoria;

    public Comida(Long id, String correo_usuario, String nombre, int raciones, double precio, String descripcion, boolean salado, boolean dulce, boolean vegetariano, boolean celiaco, Categoria categoria) {
        this.id = id;
        this.correo_usuario = correo_usuario;
        this.nombre = nombre;
        this.raciones = raciones;
        this.precio = precio;
        this.descripcion = descripcion;
        this.salado = salado;
        this.dulce = dulce;
        this.vegetariano = vegetariano;
        this.celiaco = celiaco;
        this.categoria = categoria;
    }

    public Comida(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCorreo_usuario() {
        return correo_usuario;
    }

    public void setCorreo_usuario(String correo_usuario) {
        this.correo_usuario = correo_usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getRaciones() {
        return raciones;
    }

    public void setRaciones(int raciones) {
        this.raciones = raciones;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isSalado() {
        return salado;
    }

    public void setSalado(boolean salado) {
        this.salado = salado;
    }

    public boolean isDulce() {
        return dulce;
    }

    public void setDulce(boolean dulce) {
        this.dulce = dulce;
    }

    public boolean isVegetariano() {
        return vegetariano;
    }

    public void setVegetariano(boolean vegetariano) {
        this.vegetariano = vegetariano;
    }

    public boolean isCeliaco() {
        return celiaco;
    }

    public void setCeliaco(boolean celiaco) {
        this.celiaco = celiaco;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}