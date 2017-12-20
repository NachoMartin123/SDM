package com.example.nacho.proyectosdm.modelo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.sql.Blob;

/**
 * Created by Nacho on 15/11/2017.
 */

public class Comida {

    private Long id;
    private String email_usuario;
    private String nombre;
    private int raciones;
    private double precio;
    private String descripcion;
    private boolean salado;
    private boolean dulce;
    private boolean vegetariano;
    private boolean celiaco;
    private Categoria categoria;
    private ImageView imagen;

    public Comida(Long id, String email_usuario, String nombre, int raciones, double precio, String descripcion, boolean salado, boolean dulce, boolean vegetariano, boolean celiaco, String categoria, ImageView imagen) {
        this.id = id;
        this.email_usuario = email_usuario;
        this.nombre = nombre;
        this.raciones = raciones;
        this.precio = precio;
        this.descripcion = descripcion;
        this.salado = salado;
        this.dulce = dulce;
        this.vegetariano = vegetariano;
        this.celiaco = celiaco;
        setCategoria(categoria);
        this.imagen=imagen;
    }

    public Comida(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail_usuario() {
        return email_usuario;
    }

    public void setEmail_usuario(String email_usuario) {
        this.email_usuario = email_usuario;
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

    public void setCategoria(String categoria) {
        this.categoria = Categoria.valueOf(categoria);
    }

    public ImageView getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        Bitmap bm = BitmapFactory.decodeByteArray(imagen, 0 ,imagen.length);
        this.imagen.setImageBitmap(bm);
    }
}
