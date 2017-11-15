package com.example.nacho.proyectosdm.persistence.esquemas;

import android.provider.BaseColumns;

/**
 * Created by Laura Mambo on 18/10/2017.
 */

//Variables estaticas
public class Esquemas {

    // Nombre de la tabla valoratios y sus columnas
    public static final String TABLA_USUARIO = "usuario";

    public static final String ID = "id";
    public static final String NOMBRE = "nombre";
    public static final String CONTRASEÑA = "contraseña";

    //Scrip para crear la base datos

    // public static final String CREAR_TABLA_USUARIO = "CREATE TABLE " + TABLA_USUARIO + " ( " + ID + " int NOT NULL AUTO_INCREMENT, " + NOMBRE + " VARCHAR(10)," + CONTRASEÑA + " VARCHAR(10), PRIMARY KEY (" + ID + ") );";

    public static final String CREAR_TABLA_USUARIO ="CREATE TABLE " + TABLA_USUARIO + " ( " +
            ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
            NOMBRE + " TEXT NOT NULL, " +
            CONTRASEÑA + " TEXT  NOT NULL, " +
            "CONSTRAINT nombre_unique UNIQUE (" + NOMBRE + ")," +
            "CHECK ( length(" + NOMBRE + ") > 0), " +
            "CHECK ( length(" + CONTRASEÑA + ") > 0)"+
            ");";
}








