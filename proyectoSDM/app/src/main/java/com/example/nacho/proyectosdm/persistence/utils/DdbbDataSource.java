package com.example.nacho.proyectosdm.persistence.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.nacho.proyectosdm.modelo.Usuario;
import com.example.nacho.proyectosdm.persistence.esquemas.Esquemas;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nacho on 23/11/2017.
 */

public class DdbbDataSource {
    /**
     * Referencia para manejar la base de datos. Este objeto lo obtenemos a partir de MyDBHelper
     * y nos proporciona metodos para hacer operaciones
     * CRUD (create, read, update and delete)
     */
    private SQLiteDatabase database;
    /**
     * Referencia al helper que se encarga de crear y actualizar la base de datos.
     */
    private MyDBHelper dbHelper;
    /**
     * Columnas de la tabla
     */
   // private final String[] allColumns = { MyDBHelper.COLUMN_ID, MyDBHelper.COLUMN_ASIGNATURA,
    //        MyDBHelper.COLUMN_COMMENT, MyDBHelper.COLUMN_RATING };
    /**
     * Constructor.
     *
     * @param context
     */
    public DdbbDataSource(Context context) {
        //el último parámetro es la versión
        dbHelper = new MyDBHelper(context, null, null, 1);
        deleteAll();
        List<Usuario> users = getAllUsuarios();
        insertUsuario(Esquemas.TABLA_USUARIO, "jon@gmail.com","Jon","Gijon","2017-10-16 14:00:00.000", "password", true, 638111111);
        insertUsuario(Esquemas.TABLA_USUARIO,"sansa@gmail.com","Sansa","Oviedo","2017-10-16 14:00:00.000", "password", true, 638222222);
        List<Usuario> users2  = getAllUsuarios();

    }

    /**
     * Abre una conexion para escritura con la base de datos.
     * Esto lo hace a traves del helper con la llamada a getWritableDatabase. Si la base de
     * datos no esta creada, el helper se encargara de llamar a onCreate
     *
     * @throws SQLException
     */
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    /**
     * Cierra la conexion con la base de datos
     */
    public void close() {
        dbHelper.close();
    }


    /**
     *
     * @param nombreTabla
     * @param email
     * @param nombre
     * @param ciudad
     * @param fecha_alta
     * @param password
     * @param activo
     * @param telefono
     * @return numero de elementos insertados
     */
    public long insertUsuario(String nombreTabla, String email, String nombre, String ciudad, String fecha_alta, String password, boolean activo, int telefono){
        open();
        ContentValues values = new ContentValues();
        values.put("EMAIL", email);
        values.put("NOMBRE", nombre);
        values.put("CIUDAD", ciudad);
        values.put("FECHA_ALTA", fecha_alta);
        values.put("PASSWORD", password);
        values.put("ACTIVO", activo);
        values.put("TELEFONO", telefono);
        // Insertamos la valoracion
        long insertId = database.insert(nombreTabla, null, values);
        close();
        return insertId;
    }




    public void deleteAll(){
        open();
        String selectQuery =  "DELETE FROM USUARIOS";
        database.execSQL(selectQuery);
        close();
    }


     public List<Usuario> getAllUsuarios() {
        // Lista que almacenara el resultado
        List<Usuario> usuarios = new ArrayList<Usuario>();
         open();
        //hacemos una query porque queremos devolver un cursor
         String[] allColumns = {   "EMAIL","NOMBRE".toUpperCase(), "CIUDAD", "FECHA_ALTA","PASSWORD","ACTIVO","TELEFONO" };


         Cursor cursor = database.query(Esquemas.TABLA_USUARIO, allColumns,
                null, null, null, null, null);
         //query(Esquemas.TABLA_USUARIO, allColumns, null, allColumns, null, null, null);
         //String selectQuery =  "SELECT * FROM USUARIOS ";
         //Cursor cursor = database.rawQuery(selectQuery, null);

         if(cursor.moveToFirst()) {
             while (!cursor.isAfterLast()) {
                 Usuario user = new Usuario();
                 user.setEmail(cursor.getString(cursor.getColumnIndex("EMAIL")));
                 user.setNombre(cursor.getString(cursor.getColumnIndex("NOMBRE")));
                 user.setContraseña(cursor.getString(cursor.getColumnIndex("CONTRASEÑA")));
                 user.setCiudad(cursor.getString(cursor.getColumnIndex("CIUDAD")));
                 user.setFecha_alta(Timestamp.valueOf(cursor.getString(cursor.getColumnIndex("FECHA_ALTA"))));
                 user.setActivo(cursor.getInt(cursor.getColumnIndex("ACTIVO")) > 0);
                 user.setTelefono(cursor.getInt(cursor.getColumnIndex("TELEFONO")));
                 usuarios.add(user);
                 cursor.moveToNext();
             }
         }
        cursor.close();
        // Una vez obtenidos todos los datos y cerrado el cursor, devolvemos la
        // lista.
        return usuarios;
    }

}
