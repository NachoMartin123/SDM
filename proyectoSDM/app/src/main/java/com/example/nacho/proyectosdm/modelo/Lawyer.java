package com.example.nacho.proyectosdm.modelo;

import android.content.ContentValues;

import com.example.nacho.proyectosdm.persistence.esquemas.LawyerContract;

import java.util.UUID;

/**
 * Created by Laura Mambo on 18/10/2017.
 */

// Objeto abogado == Usuario
public class Lawyer {

    Long id;
    String nombre;
    String numero;

    public Lawyer(String nombre, String numero){



        this.nombre = nombre;
        this.numero= numero;

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

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }


    // empaquetar objetos

    public ContentValues toContentValues(){

        ContentValues values = new ContentValues();
        values.put(LawyerContract.LawyerEntry.NOMBRE, nombre);
        values.put(LawyerContract.LawyerEntry.TELEFONO, numero);

        return  values;
    }
}
