package com.example.nacho.proyectosdm.modelo;

import android.content.ContentValues;

import com.example.nacho.proyectosdm.persistence.esquemas.Esquemas;

/**
 * Created by Laura Mambo on 18/10/2017.
 */

// Objeto abogado == Usuario
public class Usuario {

     private Long id;
    private String nombre;
    private String contraseña;

    public Usuario(String nombre, String contraseña){



        this.nombre = nombre;
        this.contraseña = contraseña;

    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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


    // empaquetar objetos

 //   public ContentValues toContentValues(){

   //     ContentValues values = new ContentValues();
     //   values.put(Esquemas.LawyerEntry.NOMBRE, nombre);
       // values.put(Esquemas.LawyerEntry.TELEFONO, contraseña);

        //return  values;
    //}
}
