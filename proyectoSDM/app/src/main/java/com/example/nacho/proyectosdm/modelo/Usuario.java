package com.example.nacho.proyectosdm.modelo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by Laura Mambo on 18/10/2017.
 */

// Objeto abogado == Usuario
public class Usuario implements Serializable{
    private String email;
    private String nombre;
    private String password;
    private String ciudad;
    private Timestamp fecha_alta;
    private boolean activo;
    private int telefono;
    private ImageView imagen;


    public Usuario(String nombre, String password, String email){
        this.nombre = nombre;
        this.password = password;
        this.email = email;

    }


    /**
     * necesario para manejar objetos con todos los datos
     * @param email
     * @param nombre
     * @param password
     * @param ciudad
     * @param fecha_alta
     * @param telefono
     */
    public Usuario(String email, String nombre, String password, String ciudad, Timestamp fecha_alta, boolean activo, int telefono, ImageView imagen) {
        this.email = email;
        this.nombre = nombre;
        this.password = password;
        this.ciudad = ciudad;
        this.fecha_alta = fecha_alta;
        this.activo = activo;
        this.telefono = telefono;
        this.imagen=imagen;
    }

    public Usuario(){}


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String contraseña) {
        this.password = contraseña;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public Timestamp getFecha_alta() {
        return fecha_alta;
    }

    public void setFecha_alta(Timestamp fecha_alta) {
        this.fecha_alta = fecha_alta;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        //if(!email.contains("@"))
        //    throw new IllegalArgumentException("el email debe contener el caracter '@'");
        this.email = email;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        if (telefono < 100000000 || telefono > 999999999)
            throw new IllegalArgumentException("el numero de telefono debe tener 9 digitos");
        this.telefono = telefono;
    }

    public ImageView getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {;
        this.imagen.setImageBitmap(imagen);
    }
}
