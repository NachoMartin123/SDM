package com.example.nacho.proyectosdm.modelo;

import android.content.ContentValues;
import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;
import java.sql.Timestamp;

/**
 * Created by Nacho on 29/12/2017.
 */

public class Reserva {

    private String titulo;
    private int id_comida;
    private String email_usuario;
    private Timestamp fecha_venta;
    private Bitmap imagen;
    private int cantidad;
    private int valoracion;
    private double precio;

    public Reserva(){
        this.titulo="";
        this.email_usuario="";
        this.fecha_venta=null;
        this.imagen=null;
        this.cantidad=-1;
        this.valoracion=0;
    }

    public Reserva(String titulo, int id_comida, String email_usuario, Timestamp fecha_venta, Bitmap imagen, int valoracion, double precio) {
        this.id_comida = id_comida;
        this.email_usuario = email_usuario;
        this.fecha_venta = fecha_venta;
        this.imagen = imagen;
        this.valoracion = valoracion;
        this.precio = precio;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getId_comida() {
        return id_comida;
    }

    public void setId_comida(int id_comida) {
        this.id_comida = id_comida;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getEmail_usuario() {
        return email_usuario;
    }

    public void setEmail_usuario(String email_usuario) {
        this.email_usuario = email_usuario;
    }

    public Timestamp getFecha_venta() {
        return fecha_venta;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setFecha_venta(Timestamp fecha_venta) {
        this.fecha_venta = fecha_venta;
    }

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }

    public int getValoracion() {
        return valoracion;
    }

    public void setValoracion(int valoracion) {
        this.valoracion = valoracion;
    }

    public ContentValues toContentValues() {
        ContentValues cv = new ContentValues();
        cv.put("ID_COMIDA",getId_comida());
        cv.put("EMAIL_USUARIO", getEmail_usuario());
        cv.put("FECHA_VENTA", getFecha_venta().toString());
        cv.put("CANTIDAD", getCantidad());
        byte[] data = getImageAsByteArray(getImagen());
        cv.put("IMAGEN", data);
        return cv;
    }

    private static byte[] getImageAsByteArray(Bitmap miImagen) {
        if (miImagen == null)
            return null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        miImagen.compress(Bitmap.CompressFormat.JPEG, 0, outputStream);
        return outputStream.toByteArray();
    }
}
