package com.example.nacho.proyectosdm.persistence.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.nacho.proyectosdm.modelo.Categoria;
import com.example.nacho.proyectosdm.modelo.Chat;
import com.example.nacho.proyectosdm.modelo.Comida;
import com.example.nacho.proyectosdm.modelo.Mensaje;
import com.example.nacho.proyectosdm.modelo.Usuario;
import com.example.nacho.proyectosdm.persistence.esquemas.Esquemas;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
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

        db.execSQL(Esquemas.CREAR_TABLA_USUARIO);    // HASTA AQUI FUNCIONA, TEN CUIDADO CON LO QUE AÑADES PORQUE SINO LO HACES BIEN NO FUNCIONA NADA
        /*db.execSQL(Esquemas.CREAR_TABLA_COMIDA);
        db.execSQL(Esquemas.CREAR_TABLA_VENDIDOS);
        db.execSQL(Esquemas.CREAR_TABLA_MENSAJES);
        db.execSQL(Esquemas.CREAR_TABLA_CHATS); */


    }

// cada vez que iniciamos la app revisa si existe los datos buscados en la base de datos
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // si existe actualizala

        db.execSQL("DROP TABLE IF EXISTS " + Esquemas.TABLA_USUARIO);
        db.execSQL("DROP TABLE IF EXISTS " + Esquemas.TABLA_COMIDA);
        db.execSQL("DROP TABLE IF EXISTS " + Esquemas.TABLA_MENSAJES);
        db.execSQL("DROP TABLE IF EXISTS " + Esquemas.TABLA_VENDIDOS);
        db.execSQL("DROP TABLE IF EXISTS " + Esquemas.TABLA_CHATS);
        onCreate(db);
    }



    public int sizeTable(String nombreTabla) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean isExist = false;
        Cursor cursor = db.rawQuery("select * from " + nombreTabla,null);
        if (cursor != null) {
           return cursor.getCount();
        }
        return -1;
    }
    /////////////////////////////***********************////////////////////////////////////////
    //metodos usuario



    public boolean deleteUsuario(String email) {
        //Open connection to write data
        SQLiteDatabase db = this.getWritableDatabase();
        // Inserting Row
        try {
            db.execSQL("DELETE FROM USUARIOS WHERE EMAIL='"+email+"'");
        }catch (Exception e) {
            return false;
        }
        db.close();
        return true;
    }

    /**
     * @return lista con todos los usuarios de la bbdd con sus repectivos datos
     */
    public List<Usuario> getAllUsuarios() {
        //Open connection to read only
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery =  "SELECT * FROM USUARIOS ";

        try {
            List<Usuario> userList = new ArrayList<Usuario>();

            Cursor cursor = db.rawQuery(selectQuery, null);
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {//forma de recorrer la tabla, equivalente a resultset
                do {
                   Usuario user = new Usuario();
                    user.setEmail(cursor.getString(cursor.getColumnIndex("EMAIL")));
                    user.setNombre(cursor.getString(cursor.getColumnIndex("NOMBRE")));
                    user.setContraseña(cursor.getString(cursor.getColumnIndex("CONTRASEÑA")));
                    user.setCiudad(cursor.getString(cursor.getColumnIndex("CIUDAD")));
                    user.setFecha_alta(Timestamp.valueOf(cursor.getString(cursor.getColumnIndex("FECHA_ALTA"))));
                    user.setActivo(cursor.getInt(cursor.getColumnIndex("ACTIVO")) > 0);
                    user.setTelefono(cursor.getInt(cursor.getColumnIndex("TELEFONO")));
                    userList.add(user);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
            return userList;
        }catch(Exception e){
            Log.e(null, "error en getAllUsuarios()");
            return null;
        }

    }

    /**
     * @param email
     * @return Usuario del email especificado, null en caso de que no exista
     */
    public Usuario getUserByEmail(String email){
        //Open connection to write data
        SQLiteDatabase db = this.getWritableDatabase();
        // Inserting Row
        try {
            String selectQuery = "SELECT * FROM USUARIOS WHERE email='"+email+"';";
            Cursor cursor = db.rawQuery(selectQuery, null);
            // looping through all rows and adding to list
            Usuario user=null;
            if (cursor.moveToFirst()) {//forma de recorrer la tabla, equivalente a resultset
                user = new Usuario();
                user.setEmail(cursor.getString(cursor.getColumnIndex("EMAIL")));
                user.setNombre(cursor.getString(cursor.getColumnIndex("NOMBRE")));
                user.setContraseña(cursor.getString(cursor.getColumnIndex("CONTRASEÑA")));
                user.setCiudad(cursor.getString(cursor.getColumnIndex("CIUDAD")));
                user.setFecha_alta(Timestamp.valueOf(cursor.getString(cursor.getColumnIndex("FECHA_ALTA"))));
                user.setActivo(cursor.getInt(cursor.getColumnIndex("ACTIVO"))>0);
                user.setTelefono(cursor.getInt(cursor.getColumnIndex("TELEFONO")));
            }
            cursor.close();
            db.close();
            return user;
        }catch(Exception ex){
            Log.e(null, "error en getUserByEmail");
            return null;
        }
    }


    //metodos comida
    //---------------------------------------------------------------------------------------------------------------

    /**
     * @param id
     * @return Comida cuyo id es el especificado, null en caso de que no exista
     */
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
                comida.setEmail_usuario(cursor.getString(cursor.getColumnIndex("EMAIL_USUARIO")));
                comida.setNombre(cursor.getString(cursor.getColumnIndex("NOMBRE")));
                comida.setRaciones(cursor.getInt(cursor.getColumnIndex("RACIONES")));
                comida.setPrecio(cursor.getDouble(cursor.getColumnIndex("PRECIO")));
                comida.setDescripcion(cursor.getString(cursor.getColumnIndex("DESCRIPCION")));
                comida.setSalado(cursor.getInt(cursor.getColumnIndex("SALADO"))>0);
                comida.setDulce(cursor.getInt(cursor.getColumnIndex("DULCE"))>0);
                comida.setVegetariano(cursor.getInt(cursor.getColumnIndex("VEGETARIANO"))>0);
                comida.setCeliaco(cursor.getInt(cursor.getColumnIndex("CELIACO"))>0);
                comida.setCategoria(cursor.getString(cursor.getColumnIndex("CATEGORIA")));
            }
            cursor.close();
            db.close();
            return comida;
        }catch(Exception e){
            Log.e(null, "error en getComidaById");
            return null;
        }
    }

    public boolean insertComida(Comida comida){
        //Open connection to write data
        SQLiteDatabase db = this.getWritableDatabase();
        // Inserting Row
        try {
            db.execSQL("INSERT INTO USUARIOS VALUES(" + comida.getId() + ",'"
                    + comida.getEmail_usuario() + "','"
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
     * @param email
     * @return lista con las comidas que ha PUBLICADO un usuario cuyo email es el especificado
     */
    public List<Comida> getComidasUsuario(String email){
        //Open connection to write data
        SQLiteDatabase db = this.getWritableDatabase();
        // Inserting Row
        try {
            String selectQuery = "SELECT * FROM COMIDAS WHERE EMAIL_USUARIO = '"+email+"'";
            Cursor cursor = db.rawQuery(selectQuery, null);
            // looping through all rows and adding to list
            List<Comida> miscomidas = new ArrayList<Comida>();
            if (cursor.moveToFirst()) {//forma de recorrer la tabla, equivalente a resultset
                do {
                    Comida comida = new Comida();
                    comida.setId(cursor.getLong(cursor.getColumnIndex("ID")));
                    comida.setEmail_usuario(cursor.getString(cursor.getColumnIndex("EMAIL_USUARIO")));
                    comida.setNombre(cursor.getString(cursor.getColumnIndex("NOMBRE")));
                    comida.setRaciones(cursor.getInt(cursor.getColumnIndex("RACIONES")));
                    comida.setPrecio(cursor.getDouble(cursor.getColumnIndex("PRECIO")));
                    comida.setDescripcion(cursor.getString(cursor.getColumnIndex("DESCRIPCION")));
                    comida.setSalado(cursor.getInt(cursor.getColumnIndex("SALADO"))>0);
                    comida.setDulce(cursor.getInt(cursor.getColumnIndex("DULCE"))>0);
                    comida.setVegetariano(cursor.getInt(cursor.getColumnIndex("VEGETARIANO"))>0);
                    comida.setCeliaco(cursor.getInt(cursor.getColumnIndex("CELIACO"))>0);
                    comida.setCategoria(cursor.getString(cursor.getColumnIndex("CATEGORIA")));
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
            return miscomidas;
        }catch (Exception e) {
           Log.e(null, "error en getComidasUsuario()");
            return null;
        }
    }


    /**
     * Busca las converesaciones/chats en los que ha participado el usuario
     * ya sea como emisor o receptor
     * @param email, identifica al usuario
     * @return lista con los chats de en los que ha participado un usuario. Los mensajes de cada
     * chat están ordenados por fecha
     */
    public List<Chat> getChatsUsuario(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery =  "SELECT * FROM CHATS " +
                "where email_user_1 ='"+email+"' or email_user_2 ='"+email+"'";

        try {
            List<Chat> mischats = new ArrayList<Chat>();

            Cursor cursor = db.rawQuery(selectQuery, null);
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {//forma de recorrer la tabla, equivalente a resultset
                do {
                    Chat chat = new Chat(
                            cursor.getLong(cursor.getColumnIndex("ID")),
                            cursor.getString(cursor.getColumnIndex("EMAIL_USER_1")),
                            cursor.getString(cursor.getColumnIndex("EMAIL_USER_2"))
                    );
                    mischats.add(chat);
                } while (cursor.moveToNext());
            }
            cursor.close();
            selectQuery = "SELECT * FROM MENSAJES ";

            cursor = db.rawQuery(selectQuery, null);
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {//forma de recorrer la tabla, equivalente a resultset
                do {
                    Mensaje mensaje = new Mensaje(
                            //Long id_chat, String email_emisor, String mensaje, Timestamp fecha
                            cursor.getLong(cursor.getColumnIndex("ID_CHAT")),
                            cursor.getString(cursor.getColumnIndex("EMAIL_EMISOR")),
                            cursor.getString(cursor.getColumnIndex("MENSAJE")),
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex("FECHA")))

                    );
                    for (int i = 0; i < mischats.size(); i++) {
                        if (mensaje.getId_chat() == mischats.get(i).getId())
                            mischats.get(i).addMensaje(mensaje);
                    }
                } while (cursor.moveToNext());
            }

            //ordena todos los mensajes de cada chat segun la fehcha
            for (int i = 0; i < mischats.size(); i++) {
                Collections.sort(mischats.get(i).getMensajes());
            }

            db.close();
            return mischats;
        }catch(Exception e){
            Log.e(null, "error en getChatsUsuario()");
            return null;
        }
    }



}
