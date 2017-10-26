package com.example.nacho.proyectosdm.persistence.esquemas;

import android.provider.BaseColumns;

/**
 * Created by Laura Mambo on 18/10/2017.
 */

//Variables estaticas
public class Esquemas {

    public static final String TABLA_USUARIO = "usuario";

    public static final String ID = "id";
    public static final String NOMBRE = "nombre";
    public static final String CONTRASEÑA = "contraseña";

    public static final String CREAR_TABLA_USUARIO = "CREATE TABLE" + TABLA_USUARIO + " ( " + ID + " INTEGER, " + NOMBRE + " TEXT," + CONTRASEÑA + " TEXT, )";

}








