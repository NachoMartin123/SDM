package com.example.nacho.proyectosdm.modelo;

import android.graphics.Bitmap;

import com.google.android.gms.maps.model.LatLng;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import android.content.ContentValues;

import com.google.android.gms.maps.model.LatLng;

import java.io.ByteArrayOutputStream;

/**
 * Created by Nacho on 15/11/2017.
 */

public class Comida {

    private int id;
    private String email_usuario;
    private String titulo;
    private int raciones;
    private double precio;
    private String descripcion;
    private boolean salado;
    private boolean dulce;
    private boolean vegetariano;
    private boolean celiaco;
    private Categoria categoria;
    private Bitmap imagen;
    private double latitud;
    private double longitud;

    public Comida() {
        this.email_usuario = "";
        this.titulo = "";
        this.raciones = 0;
        this.precio = 0.0;
        this.descripcion = "";
        this.salado = false;
        this.dulce = false;
        this.vegetariano = false;
        this.celiaco = false;
        this.imagen = null;
        this.categoria = null;
        this.latitud = 0;
        this.longitud = 0;

    }

    public Comida(String email_usuario, String titulo, int raciones, double precio, String descripcion, boolean salado, boolean dulce, boolean vegetariano, boolean celiaco, Categoria categoria, Bitmap imagen, double latitud, double longitud) {

        this.email_usuario = email_usuario;
        this.titulo = titulo;
        this.raciones = raciones;
        this.precio = precio;
        this.descripcion = descripcion;
        this.salado = salado;
        this.dulce = dulce;
        this.vegetariano = vegetariano;
        this.celiaco = celiaco;
        this.imagen = imagen;
        this.categoria = categoria;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail_usuario() {
        return email_usuario;
    }

    public void setEmail_usuario(String email_usuario) {
        this.email_usuario = email_usuario;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
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

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public ContentValues toContentValues() {
        ContentValues cv = new ContentValues();
        cv.put("EMAIL_USUARIO", getEmail_usuario());
        cv.put("TITULO", getTitulo());
        cv.put("RACIONES", getRaciones());
        cv.put("PRECIO", getPrecio());
        cv.put("DESCRIPCION", getDescripcion());
        cv.put("SALADO", isSalado());
        cv.put("DULCE", isDulce());
        cv.put("VEGETARIANO", isVegetariano());
        cv.put("CELIACO", isCeliaco());
        cv.put("CATEGORIA", getCategoria().toString());
        byte[] data = getImageAsByteArray(getImagen());
        cv.put("IMAGEN", data);
        cv.put("LATITUD", getLatitud());
        cv.put("LONGITUD", getLongitud());
        return cv;
    }

    private static byte[] getImageAsByteArray(Bitmap miImagen) {
        if (miImagen == null)
            return null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        miImagen.compress(Bitmap.CompressFormat.JPEG, 0, outputStream);
        return outputStream.toByteArray();
    }

    public LatLng crearPosicion() {
        return new LatLng(latitud, longitud);
    }

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }

    public boolean cumpleCaracteristicas(boolean salado, boolean dulce, boolean vegetariano, boolean celiaco) {
        if (salado && !this.salado) {
            return false;
        }
        if (dulce && !this.dulce) {
            return false;
        }
        if (vegetariano && !this.vegetariano) {
            return false;
        }
        if (celiaco && !this.celiaco) {
            return false;
        }
        return true;
    }
}

