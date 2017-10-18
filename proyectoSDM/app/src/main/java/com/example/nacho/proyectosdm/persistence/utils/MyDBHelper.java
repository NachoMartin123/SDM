package com.example.nacho.proyectosdm.persistence.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.nacho.proyectosdm.modelo.Lawyer;
import com.example.nacho.proyectosdm.persistence.esquemas.LawyerContract;

/**
 * Created by Laura Mambo on 18/10/2017.
 */

// Crea la base de datos

public class MyDBHelper extends SQLiteOpenHelper {

    public static final String DBNAME ="Lawyer.db";
    public static final int DBVERSION = 1;

    public static final String BDCREATE = "CREATE TABLE " + LawyerContract.LawyerEntry.TABLE_NAME + " ("
            + LawyerContract.LawyerEntry.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + LawyerContract.LawyerEntry.NOMBRE + " TEXT NOT NULL,"
            + LawyerContract.LawyerEntry.TELEFONO + " TEXT NOT NULL)";


    public MyDBHelper(Context context){

        super(context, DBNAME, null, DBVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creamos la base de datos
        db.execSQL(BDCREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertLawyer(Lawyer lawyer){

        SQLiteDatabase db = getWritableDatabase();
        db.insert(LawyerContract.LawyerEntry.TABLE_NAME,null,lawyer.toContentValues());
    }


}
