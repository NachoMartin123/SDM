package com.example.nacho.proyectosdm.modelo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by Laura Mambo on 18/10/2017.
 */

// Objeto abogado == Usuario
public class Usuario implements Serializable {

    private String email;
    private String nombre;
    private String contraseña;
    private String ciudad;
    private Timestamp fecha_alta;
    private boolean activo;

    public Usuario(String nombre, String contraseña, String email){
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.email = email;

    }

    /**
     * necesario para manejar objetos con todos los datos
     * @param email
     * @param nombre
     * @param contraseña
     * @param ciudad
     * @param fecha_alta
     */
    public Usuario(String email, String nombre, String contraseña, String ciudad, Timestamp fecha_alta, boolean activo) {
        this.email = email;
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.ciudad = ciudad;
        this.fecha_alta = fecha_alta;
        this.activo = activo;
    }

    public Usuario(){}

    public String getId() {
        return email;
    }

    public void setId(String correo) {
        this.email = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
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
        this.email = email;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }


    // empaquetar objetos

 //   public ContentValues toContentValues(){

   //     ContentValues values = new ContentValues();
     //   values.put(Esquemas.LawyerEntry.NOMBRE, nombre);
       // values.put(Esquemas.LawyerEntry.TELEFONO, contraseña);

        //return  values;
    //}
}
