package com.example.nacho.proyectosdm.persistence.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
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
        //PRUEBAS DE FUNCINAMIENTO DE METODOS
        dbHelper = new MyDBHelper(context, null, null, 1);
        int tam1 = sizeTable(Esquemas.TABLA_USUARIO);
        deleteAllUsuarios();
        int tam2 = sizeTable(Esquemas.TABLA_USUARIO);
        List<Usuario> users = getAllUsuarios();
        insertUsuario(Esquemas.TABLA_USUARIO, "jon@gmail.com","Jon","Gijon","2017-10-16 14:00:00.000", "password", true, "638111111");
        insertUsuario(Esquemas.TABLA_USUARIO,"sansa@gmail.com","Sansa","Oviedo","2017-10-16 14:00:00.000", "password", true, "638222222");
        insertUsuario(Esquemas.TABLA_USUARIO,"niguateresa@gmail.com","nigua","Oviedo","2017-10-16 14:00:00.000", "1234", true, "638222222");
        List<Usuario> users2  = getAllUsuarios();
        int tam3 = sizeTable(Esquemas.TABLA_USUARIO);
        deleteUsuario(Esquemas.TABLA_USUARIO, "sansa@gmail.com");
        int tam4 = sizeTable(Esquemas.TABLA_USUARIO);
        Usuario user1 = getUserByEmail("jon@gmail.com");
        insertUsuario(Esquemas.TABLA_USUARIO,"sansa@gmail.com","Sansa","Oviedo","2017-10-16 14:00:00.000", "password", true, "638222222");

        Comida comida = new Comida();
        comida.setCategoria(Categoria.DESAYUNO);
        comida.setCeliaco(true);
        comida.setNombre("Bizcocho de chocolate");
        comida.setDescripcion("Bizcocho de chocolate");
        comida.setDulce(true);
        comida.setEmail_usuario("jon@gmail.com");
        comida.setRaciones(4);
        comida.setLatitud(43.362119);
        comida.setLongitud(-5.850375);
        insertComida(comida);

        comida = new Comida();
        comida.setCategoria(Categoria.COMIDA);
        comida.setCeliaco(true);
        comida.setNombre("Macarrones");
        comida.setDescripcion("Macarrones");
        comida.setSalado(true);
        comida.setEmail_usuario("jon@gmail.com");
        comida.setRaciones(4);
        comida.setLatitud(43.540915);
        comida.setLongitud(-5.922073);
        insertComida(comida);
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
    public long insertUsuario(String nombreTabla, String email, String nombre, String ciudad, String fecha_alta, String password, boolean activo, String telefono){
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


    public void deleteAllUsuarios(){
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
                 user.setNombre(cursor.getString(1));
                 user.setPassword(cursor.getString(cursor.getColumnIndex("PASSWORD")));
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



    public int sizeTable(String nombreTabla) {
        open();
        String[] allColumns = {   "EMAIL","NOMBRE".toUpperCase(), "CIUDAD", "FECHA_ALTA","PASSWORD","ACTIVO","TELEFONO" };

        Cursor cursor = database.query(Esquemas.TABLA_USUARIO, allColumns,
                null, null, null, null, null);
        if (cursor != null) {
            return cursor.getCount();
        }
        close();
        return -1;
    }


    public boolean deleteUsuario(String nombreTabla, String email){
        open();
        long eliminados = database.delete(Esquemas.TABLA_USUARIO, "EMAIL" + "='" + email+"'", null);
        close();
        return eliminados>0;

    }

    /**
     * @param email
     * @return Usuario del email especificado, null en caso de que no exista
     */
    public Usuario getUserByEmail(String email){
        try {
            open();
            String selectQuery = "SELECT * FROM USUARIOS WHERE email='"+email+"';";
            Cursor cursor = database.rawQuery(selectQuery, null);
            // looping through all rows and adding to list
            Usuario user=null;
            if (cursor.moveToFirst()) {//forma de recorrer la tabla, equivalente a resultset
                user = new Usuario();
                user.setEmail(cursor.getString(cursor.getColumnIndex("EMAIL")));
                user.setNombre(cursor.getString(1));
                user.setPassword(cursor.getString(cursor.getColumnIndex("PASSWORD")));
                user.setCiudad(cursor.getString(cursor.getColumnIndex("CIUDAD")));
                user.setFecha_alta(Timestamp.valueOf(cursor.getString(cursor.getColumnIndex("FECHA_ALTA"))));
                user.setActivo(cursor.getInt(cursor.getColumnIndex("ACTIVO"))>0);
                user.setTelefono(cursor.getInt(cursor.getColumnIndex("TELEFONO")));
            }
            cursor.close();
            close();
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

        try {
            open();
            String selectQuery = "SELECT * FROM COMIDAS WHERE ID="+id;
            Cursor cursor = database.rawQuery(selectQuery, null);
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
                comida.setCategoria(Categoria.valueOf(cursor.getString(cursor.getColumnIndex("CATEGORIA"))));
            }
            cursor.close();
            close();
            return comida;
        }catch(Exception e){
            Log.e(null, "error en getComidaById");
            return null;
        }
    }

    public boolean insertComida(Comida comida){
        try {
            open();
            database.insert(
                    Esquemas.TABLA_COMIDA,
                    null,
                    comida.toContentValues()
            );
        }catch (Exception e) {
            return false;
        }
        close();
        return true;
    }

    /**
     *
     * @param email
     * @return lista con las comidas que ha PUBLICADO un usuario cuyo email es el especificado
     */
    public List<Comida> getComidasUsuario(String email){
        try {
            open();
            Cursor cursor = database.query(
                    Esquemas.TABLA_COMIDA,
                    new String []{
                            "ID", "DESCRIPCION", "LATITUD", "LONGITUD"
                    },
                    "EMAIL_USUARIO = ?",
                    new String [] {
                            email
                    }, null, null, null);
            // looping through all rows and adding to list
            List<Comida> miscomidas = new ArrayList<Comida>();
            if (cursor.moveToFirst()) {//forma de recorrer la tabla, equivalente a resultset
                do {
                    /*
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
                    comida.setCategoria(Categoria.valueOf(cursor.getString(cursor.getColumnIndex("CATEGORIA"))));
                    */
                    Comida comida = new Comida();
                    comida.setId(cursor.getLong(cursor.getColumnIndex("ID")));
                    comida.setDescripcion(cursor.getString(cursor.getColumnIndex("DESCRIPCION")));
                    comida.setLatitud(cursor.getDouble(cursor.getColumnIndex("LATITUD")));
                    comida.setLongitud(cursor.getDouble(cursor.getColumnIndex("LONGITUD")));

                    miscomidas.add(comida);
                } while (cursor.moveToNext());
            }
            cursor.close();
            close();
            return miscomidas;
        }catch (Exception e) {
            Log.e(null, "error en getComidasUsuario()", e);
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

        try {
            open();
            String selectQuery =  "SELECT * FROM CHATS " +
                    "where email_user_1 ='"+email+"' or email_user_2 ='"+email+"'";
            List<Chat> mischats = new ArrayList<Chat>();

            Cursor cursor = database.rawQuery(selectQuery, null);
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

            cursor = database.rawQuery(selectQuery, null);
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

            database.close();
            return mischats;
        }catch(Exception e){
            Log.e(null, "error en getChatsUsuario()");
            return null;
        }
    }


}
