package com.example.nacho.proyectosdm.persistence.utils;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.nacho.proyectosdm.persistence.esquemas.Esquemas;



/**
 * Created by Laura Mambo on 18/10/2017.
 */

// Crea la base de datos

public class MyDBHelper extends SQLiteOpenHelper {

    //Nombre y version de la base de datos

    private static final String DATABASE_NAME = "chefYa.db";
    private static final int DATABASE_VERSION = 1;


    public MyDBHelper(Context context, String name,SQLiteDatabase.CursorFactory factory, int version){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //onCreate(this.getWritableDatabase());
    }


    // genera las tablas
    @Override
    public void onCreate(SQLiteDatabase db) {

        // creamos la base de datos
        //db.execSQL("DROP TABLE IF EXISTS "+Esquemas.TABLA_RESERVAS);
        //db.execSQL("DROP TABLE IF EXISTS "+Esquemas.TABLA_USUARIO);
        //db.execSQL("DROP TABLE IF EXISTS "+Esquemas.TABLA_COMIDA);


        //db.execSQL(Esquemas.CREAR_TABLA_RESERVAS);
        db.execSQL(Esquemas.CREAR_TABLA_USUARIO);
        db.execSQL(Esquemas.CREAR_TABLA_COMIDA);
        db.execSQL(Esquemas.CREAR_TABLA_RESERVAS);
        db.execSQL(Esquemas.CREAR_TABLA_MENSAJES);
        db.execSQL(Esquemas.CREAR_TABLA_CHATS);
    }

// cada vez que iniciamos la app revisa si existe los datos buscados en la base de datos
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // si existe actualizala

        db.execSQL("DROP TABLE IF EXISTS " + Esquemas.TABLA_USUARIO);
        db.execSQL("DROP TABLE IF EXISTS " + Esquemas.TABLA_COMIDA);
        db.execSQL("DROP TABLE IF EXISTS " + Esquemas.TABLA_MENSAJES);
        db.execSQL("DROP TABLE IF EXISTS " + Esquemas.TABLA_RESERVAS);
        db.execSQL("DROP TABLE IF EXISTS " + Esquemas.TABLA_CHATS);
        onCreate(db);
    }




}
