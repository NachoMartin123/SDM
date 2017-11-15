package com.example.nacho.proyectosdm.persistence.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.nacho.proyectosdm.modelo.Usuario;
import com.example.nacho.proyectosdm.persistence.esquemas.Esquemas;

/**
 * Created by Laura Mambo on 18/10/2017.
 */

// Crea la base de datos

public class MyDBHelper extends SQLiteOpenHelper {

    //Nombre y version de la base de datos

    private static final String DATABASE_NAME = "usuario.db";
    private static final int DATABASE_VERSION = 1;


    public MyDBHelper(Context context, String name,SQLiteDatabase.CursorFactory factory, int version){

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // genera las tablas
    @Override
    public void onCreate(SQLiteDatabase db) {

        // creamos la base de datos
        db.execSQL(Esquemas.CREAR_TABLA_USUARIO);
    }
// cada vez que iniciamos la app revisa si existe los datos buscados en la base de datos
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// si existe actualizala

        db.execSQL("DROP TABLE IF EXISTS usuarios" + Esquemas.TABLA_USUARIO);
        onCreate(db);

    }



}
