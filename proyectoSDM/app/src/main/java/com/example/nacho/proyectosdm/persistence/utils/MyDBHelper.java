package com.example.nacho.proyectosdm.persistence.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.nacho.proyectosdm.modelo.Categoria;
import com.example.nacho.proyectosdm.modelo.Comida;
import com.example.nacho.proyectosdm.modelo.Usuario;
import com.example.nacho.proyectosdm.persistence.esquemas.Esquemas;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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

       // super(context, name, factory, version);
    }

    // genera las tablas
    @Override
    public void onCreate(SQLiteDatabase db) {
        // creamos la base de datos
        db.execSQL(Esquemas.CREAR_TABLA_USUARIO);    // HASTA AQUI FUNCIONA, TEN CUIDADO CON LO QUE AÑADES PORQUE SINO LO HACES BIEN NO FUNCIONA NADA.
        db.execSQL(Esquemas.CREAR_TABLA_COMIDA);
        db.execSQL(Esquemas.CREAR_TABLA_VENDIDOS);
        db.execSQL(Esquemas.CREAR_TABLA_MENSAJES);
        db.execSQL(Esquemas.SCRIPT_CREACION);
    }

// cada vez que iniciamos la app revisa si existe los datos buscados en la base de datos
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // si existe actualizala
        db.execSQL("DROP TABLE IF EXISTS " + Esquemas.TABLA_USUARIO);
        onCreate(db);

    }

    ////////////////////////////////////////////////***********************///////////////////////////////////////////////////////////////////
    //metodos usuario
    public boolean insertUsuario(Usuario usuario) {
        //Open connection to write data
        SQLiteDatabase db = this.getWritableDatabase();
        // Inserting Row
        try {
            db.execSQL("INSERT INTO USUARIOS VALUES('" + usuario.getEmail() + "','"
                    + usuario.getNombre() + "','"
                    + usuario.getCiudad() + "'','"
                    + usuario.getFecha_alta() + "','"
                    + usuario.getContraseña() + "',"
                    + usuario.isActivo() + ")");
        }catch (Exception e) {
            return false;
        }
        db.close();
        return true;
    }

    public boolean deleteUsuario(String correo) {
        //Open connection to write data
        SQLiteDatabase db = this.getWritableDatabase();
        // Inserting Row
        try {
            db.execSQL("DELETE FROM USUARIOS WHERE CORREO='"+correo+"'");
        }catch (Exception e) {
            return false;
        }
        db.close();
        return true;
    }

    public List<Usuario> getAllUsuarios() {
        //Open connection to read only
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery =  "SELECT * FROM USUARIOS ";

        //Student student = new Student();
        List<Usuario> userList = new ArrayList<Usuario>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {//forma de recorrer la tabla, equivalente a resultset
            do {
                Usuario user = new Usuario();
                user.setEmail(cursor.getString(cursor.getColumnIndex("CORREO")));
                user.setNombre(cursor.getString(cursor.getColumnIndex("NOMBRE")));
                user.setContraseña(cursor.getString(cursor.getColumnIndex("CONTRASEÑA")));
                user.setCiudad(cursor.getString(cursor.getColumnIndex("CIUDAD")));
                user.setFecha_alta(Timestamp.valueOf(cursor.getString(cursor.getColumnIndex("FECHA_ALTA"))));
                user.setActivo(cursor.getInt(cursor.getColumnIndex("ACTIVO"))>0);
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return userList;

    }

    public Usuario getUserByCorreo(String correo){
        //Open connection to write data
        SQLiteDatabase db = this.getWritableDatabase();
        // Inserting Row
        try {
            String selectQuery = "SELECT * FROM USUARIOS WHERE CORREO="+correo;
            Cursor cursor = db.rawQuery(selectQuery, null);
            // looping through all rows and adding to list
            Usuario user=null;
            if (cursor.moveToFirst()) {//forma de recorrer la tabla, equivalente a resultset
                user = new Usuario();
                user.setEmail(cursor.getString(cursor.getColumnIndex("CORREO")));
                user.setNombre(cursor.getString(cursor.getColumnIndex("NOMBRE")));
                user.setContraseña(cursor.getString(cursor.getColumnIndex("CONTRASEÑA")));
                user.setCiudad(cursor.getString(cursor.getColumnIndex("CIUDAD")));
                user.setFecha_alta(Timestamp.valueOf(cursor.getString(cursor.getColumnIndex("FECHA_ALTA"))));
                user.setActivo(cursor.getInt(cursor.getColumnIndex("ACTIVO"))>0);
            }
            cursor.close();
            db.close();
            return user;
        }catch (Exception e) {
            return null;
        }
    }


    //metodos comida
    //---------------------------------------------------------------------------------------------------------------

    public Comida getComidaById(Long id){
        //Open connection to write data
        SQLiteDatabase db = this.getWritableDatabase();
        // Inserting Row
        try {
            String selectQuery = "SELECT * FROM COMIDAS WHERE ID="+id;
            Cursor cursor = db.rawQuery(selectQuery, null);
            // looping through all rows and adding to list
            Comida comida=null;
            if (cursor.moveToFirst()) {//forma de recorrer la tabla, equivalente a resultset
                comida = new Comida();
                comida.setId(cursor.getLong(cursor.getColumnIndex("ID")));
                comida.setCorreo_usuario(cursor.getString(cursor.getColumnIndex("CORREO_USUARIO")));
                comida.setNombre(cursor.getString(cursor.getColumnIndex("NOMBRE")));
                comida.setRaciones(cursor.getInt(cursor.getColumnIndex("RACIONES")));
                comida.setPrecio(cursor.getDouble(cursor.getColumnIndex("PRECIO")));
                comida.setDescripcion(cursor.getString(cursor.getColumnIndex("DESCRIPCION")));
                comida.setSalado(cursor.getInt(cursor.getColumnIndex("SALADO"))>0);
                comida.setDulce(cursor.getInt(cursor.getColumnIndex("DULCE"))>0);
                comida.setVegetariano(cursor.getInt(cursor.getColumnIndex("VEGETARIANO"))>0);
                comida.setCeliaco(cursor.getInt(cursor.getColumnIndex("CELIACO"))>0);
                comida.setCategoria(Categoria.valueOf(cursor.getString(cursor.getColumnIndex("CATEGORIA"))));
            }
            cursor.close();
            db.close();
            return comida;
        }catch (Exception e) {
            return null;
        }
    }

    public boolean insertComida(Comida comida){
        //Open connection to write data
        SQLiteDatabase db = this.getWritableDatabase();
        // Inserting Row
        try {
            db.execSQL("INSERT INTO USUARIOS VALUES(" + comida.getId() + ",'"
                    + comida.getCorreo_usuario() + "','"
                    + comida.getNombre() + "'',"
                    + comida.getRaciones() + ","
                    + comida.getPrecio() + ",'"
                    + comida.getDescripcion() + "', "
                    + comida.isSalado()+","
                    + comida.isDulce()+ ","
                    + comida.isVegetariano()+","
                    + comida.isCeliaco()+",'"
                    + comida.getCategoria()+"')");
        }catch (Exception e) {
            return false;
        }
        db.close();
        return true;
    }

    /**
     *
     * @param correo
     * @return lista con las comidas que ha PUBLICADO un usuario
     */
    public List<Comida> getComidasUsuario(String correo){
        //Open connection to write data
        SQLiteDatabase db = this.getWritableDatabase();
        // Inserting Row
        try {
            String selectQuery = "SELECT * FROM COMIDAS WHERE CORREO_USUARIO = '"+correo+"'";
            Cursor cursor = db.rawQuery(selectQuery, null);
            // looping through all rows and adding to list
            List<Comida> miscomidas = new ArrayList<Comida>();
            if (cursor.moveToFirst()) {//forma de recorrer la tabla, equivalente a resultset
                do {
                    Comida comida = new Comida();
                    comida.setId(cursor.getLong(cursor.getColumnIndex("ID")));
                    comida.setCorreo_usuario(cursor.getString(cursor.getColumnIndex("CORREO_USUARIO")));
                    comida.setNombre(cursor.getString(cursor.getColumnIndex("NOMBRE")));
                    comida.setRaciones(cursor.getInt(cursor.getColumnIndex("RACIONES")));
                    comida.setPrecio(cursor.getDouble(cursor.getColumnIndex("PRECIO")));
                    comida.setDescripcion(cursor.getString(cursor.getColumnIndex("DESCRIPCION")));
                    comida.setSalado(cursor.getInt(cursor.getColumnIndex("SALADO"))>0);
                    comida.setDulce(cursor.getInt(cursor.getColumnIndex("DULCE"))>0);
                    comida.setVegetariano(cursor.getInt(cursor.getColumnIndex("VEGETARIANO"))>0);
                    comida.setCeliaco(cursor.getInt(cursor.getColumnIndex("CELIACO"))>0);
                    comida.setCategoria(Categoria.valueOf(cursor.getString(cursor.getColumnIndex("CATEGORIA"))));
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
            return miscomidas;
        }catch (Exception e) {
            return null;
        }
    }



}
