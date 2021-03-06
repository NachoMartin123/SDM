package com.example.nacho.proyectosdm.persistence.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.nacho.proyectosdm.modelo.Categoria;
import com.example.nacho.proyectosdm.modelo.Chat;
import com.example.nacho.proyectosdm.modelo.Comida;
import com.example.nacho.proyectosdm.modelo.Mensaje;
import com.example.nacho.proyectosdm.modelo.Reserva;
import com.example.nacho.proyectosdm.modelo.Usuario;
import com.example.nacho.proyectosdm.persistence.esquemas.Esquemas;

import java.io.ByteArrayOutputStream;
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
        dbHelper = new MyDBHelper(context, this);

        //reiniciarBBDD(context);

    }

    public void reiniciarBBDD(SQLiteDatabase db, Context context){
        //int tam1 = sizeTable(Esquemas.TABLA_USUARIO);
        //deleteAllReservas();
        //deleteAllComidas();
        //deleteAllUsuarios();

        Esquemas e = new Esquemas(context);
        List<Usuario> usuariosIniciales = e.listaInicialUsuarios();
        List<Comida> comidasIniciales = e.listaInicialComidas();
        for (Usuario user: usuariosIniciales ) {
            insertUsuario(user, db);
        }
//        int tam1a = sizeTable(Esquemas.TABLA_USUARIO);
//        int tam3 = sizeTable(Esquemas.TABLA_COMIDA);


         for (Comida comida: comidasIniciales ) {
            insertComida(comida, db);
        }
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
     * metodo para guardar imageViews
     * @param miImagen, byte[] para poder guardar
     * @return
     */
    private static byte[] getImageAsByteArray(Bitmap miImagen) {
        if(miImagen==null)
            return null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        miImagen.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }


    /**
     *
     * @param u
     * @return numero de elementos insertados
     */
    public long insertUsuario(Usuario u){
        open();
        ContentValues values = new ContentValues();
        values.put("EMAIL", u.getEmail());
        values.put("NOMBRE", u.getNombre());
        values.put("CIUDAD", u.getCiudad());
        values.put("FECHA_ALTA", String.valueOf(u.getFecha_alta()));
        values.put("PASSWORD", u.getPassword());
        values.put("ACTIVO", u.isActivo());
        values.put("TELEFONO", u.getTelefono());
        byte[] data = getImageAsByteArray(u.getImagen());
        values.put("IMAGEN", data);
        // Insertamos la valoracion
        long insertId = database.insert(Esquemas.TABLA_USUARIO, null, values);
        close();
        return insertId;
    }

    /**
     *
     * @param u
     * @return numero de elementos insertados
     */
    public long insertUsuario(Usuario u, SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put("EMAIL", u.getEmail());
        values.put("NOMBRE", u.getNombre());
        values.put("CIUDAD", u.getCiudad());
        values.put("FECHA_ALTA", String.valueOf(u.getFecha_alta()));
        values.put("PASSWORD", u.getPassword());
        values.put("ACTIVO", u.isActivo());
        values.put("TELEFONO", u.getTelefono());
        byte[] data = getImageAsByteArray(u.getImagen());
        values.put("IMAGEN", data);
        // Insertamos la valoracion
        long insertId = db.insert(Esquemas.TABLA_USUARIO, null, values);
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
        List<Usuario> usuarios = new ArrayList<>();
         open();
        //hacemos una query porque queremos devolver un cursor
         String[] allColumns = {   "EMAIL","NOMBRE".toUpperCase(), "CIUDAD", "FECHA_ALTA","PASSWORD","ACTIVO","TELEFONO", "IMAGEN" };

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
                 byte[] imgByte = cursor.getBlob(cursor.getColumnIndex("IMAGEN"));
                 if(imgByte!=null)
                     user.setImagen(BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length));
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
        Cursor cursor = database.query(nombreTabla, null,
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
                byte[] imgByte = cursor.getBlob(cursor.getColumnIndex("IMAGEN"));
                if(imgByte!=null)
                    user.setImagen(BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length));
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

    private void deleteAllComidas(){
        open();
        String selectQuery =  "DELETE FROM COMIDAS";
        database.execSQL(selectQuery);
        close();
    }


    public boolean  insertComida(Comida comida){
        try {
            open();
            ContentValues cv = new ContentValues();
            cv.put("EMAIL_USUARIO", comida.getEmail_usuario());
            cv.put("TITULO", comida.getTitulo());
            cv.put("RACIONES", comida.getRaciones());
            cv.put("PRECIO", comida.getPrecio());
            cv.put("DESCRIPCION", comida.getDescripcion());
            cv.put("SALADO", comida.isSalado());
            cv.put("DULCE", comida.isDulce());
            cv.put("VEGETARIANO", comida.isVegetariano());
            cv.put("CELIACO", comida.isCeliaco());
            cv.put("CATEGORIA", comida.getCategoria().toString());
            byte[] data = getImageAsByteArray(comida.getImagen());
            cv.put("IMAGEN", data);
            cv.put("LATITUD", comida.getLatitud());
            cv.put("LONGITUD", comida.getLongitud());

            database.insert(Esquemas.TABLA_COMIDA, null, cv);
            //database.insert(Esquemas.TABLA_COMIDA, null, comida.toContentValues()

        }catch (Exception e) {
            return false;
        }
        close();
        return true;
    }

    public void insertComida(Comida comida, SQLiteDatabase db){

        ContentValues cv = new ContentValues();
        cv.put("EMAIL_USUARIO", comida.getEmail_usuario());
        cv.put("TITULO", comida.getTitulo());
        cv.put("RACIONES", comida.getRaciones());
        cv.put("PRECIO", comida.getPrecio());
        cv.put("DESCRIPCION", comida.getDescripcion());
        cv.put("SALADO", comida.isSalado());
        cv.put("DULCE", comida.isDulce());
        cv.put("VEGETARIANO", comida.isVegetariano());
        cv.put("CELIACO", comida.isCeliaco());
        cv.put("CATEGORIA", comida.getCategoria().toString());
        byte[] data = getImageAsByteArray(comida.getImagen());
        cv.put("IMAGEN", data);
        cv.put("LATITUD", comida.getLatitud());
        cv.put("LONGITUD", comida.getLongitud());

        db.insert(Esquemas.TABLA_COMIDA, null, cv);
    }

    /**
     * @param id
     * @return Comida cuyo id es el especificado, null en caso de que no exista
     */
    public Comida getComidaById(int id){

        try {
            open();
            String selectQuery = "SELECT * FROM COMIDAS WHERE ID="+id;
            Cursor cursor = database.rawQuery(selectQuery, null);
            // looping through all rows and adding to list
            Comida comida=null;
            if (cursor.moveToFirst()) {//forma de recorrer la tabla, equivalente a resultset
                comida = new Comida();
                comida.setId(cursor.getInt(cursor.getColumnIndex("ID")));
                comida.setEmail_usuario(cursor.getString(cursor.getColumnIndex("EMAIL_USUARIO")));
                comida.setTitulo(cursor.getString(cursor.getColumnIndex("TITULO")));
                comida.setRaciones(cursor.getInt(cursor.getColumnIndex("RACIONES")));
                comida.setPrecio(cursor.getDouble(cursor.getColumnIndex("PRECIO")));
                comida.setDescripcion(cursor.getString(cursor.getColumnIndex("DESCRIPCION")));
                comida.setSalado(cursor.getInt(cursor.getColumnIndex("SALADO"))>0);
                comida.setDulce(cursor.getInt(cursor.getColumnIndex("DULCE"))>0);
                comida.setVegetariano(cursor.getInt(cursor.getColumnIndex("VEGETARIANO"))>0);
                comida.setCeliaco(cursor.getInt(cursor.getColumnIndex("CELIACO"))>0);
                comida.setCategoria(Categoria.valueOf(cursor.getString(cursor.getColumnIndex("CATEGORIA"))));
                //esta bien, pero el cursor no soporta la imagen
                byte[] imgByte = cursor.getBlob(cursor.getColumnIndex("IMAGEN"));
                if(imgByte!=null)
                    comida.setImagen(BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length));
                comida.setLatitud(cursor.getDouble(cursor.getColumnIndex("LATITUD")));
                comida.setLongitud(cursor.getDouble(cursor.getColumnIndex("LONGITUD")));
            }
            cursor.close();
            close();
            return comida;
        }catch(Exception e){
            Log.e(null, "error en getComidaById");
            return null;
        }
    }


    private void setImagenYtituloYPrecio(Reserva reserva){

        try {
            open();
            String selectQuery = "SELECT TITULO, PRECIO, IMAGEN FROM COMIDAS WHERE ID="+reserva.getId_comida();
            Cursor cursor = database.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                reserva.setTitulo(cursor.getString(cursor.getColumnIndex("TITULO")));
                reserva.setPrecio(cursor.getDouble(cursor.getColumnIndex("PRECIO")));
                byte[] imgByte = cursor.getBlob(cursor.getColumnIndex("IMAGEN"));
                if(imgByte!=null)
                    reserva.setImagen(BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length));
            }
            cursor.close();
            close();
        }catch(Exception e){
            Log.e(null, "error en getComidaById");
        }
    }


    /**
     *
     * @param email
     * @return lista con las comidas que ha PUBLICADO un usuario cuyo email es el especificado
     */
    public List<Comida> getComidasUsuario(String email){
        try {
            open();
            //String selectQuery = "SELECT * FROM COMIDAS WHERE EMAIL_USUARIO="+email;
            //Cursor cursor = database.rawQuery(selectQuery, null);
            Cursor cursor = database.query(
                    Esquemas.TABLA_COMIDA,
                    new String []{
                            "ID", "EMAIL_USUARIO", "TITULO","RACIONES","PRECIO","DESCRIPCION","SALADO","DULCE","VEGETARIANO","CELIACO","CATEGORIA","IMAGEN", "LATITUD", "LONGITUD"
                    },
                    "EMAIL_USUARIO = ?",
                    new String [] {
                            email
                    }, null, null, null);

            // looping through all rows and adding to list
            List<Comida> miscomidas = new ArrayList<Comida>();
            if (cursor.moveToFirst()) {//forma de recorrer la tabla, equivalente a resultset
                do {
                    Comida comida = new Comida();
                    comida.setId(cursor.getInt(cursor.getColumnIndex("ID")));
                    comida.setEmail_usuario(cursor.getString(cursor.getColumnIndex("EMAIL_USUARIO")));
                    comida.setTitulo(cursor.getString(cursor.getColumnIndex("TITULO")));
                    comida.setRaciones(cursor.getInt(cursor.getColumnIndex("RACIONES")));
                    comida.setPrecio(cursor.getDouble(cursor.getColumnIndex("PRECIO")));
                    comida.setDescripcion(cursor.getString(cursor.getColumnIndex("DESCRIPCION")));
                    comida.setSalado(cursor.getInt(cursor.getColumnIndex("SALADO"))>0);
                    comida.setDulce(cursor.getInt(cursor.getColumnIndex("DULCE"))>0);
                    comida.setVegetariano(cursor.getInt(cursor.getColumnIndex("VEGETARIANO"))>0);
                    comida.setCeliaco(cursor.getInt(cursor.getColumnIndex("CELIACO"))>0);
                    comida.setCategoria(Categoria.valueOf(cursor.getString(cursor.getColumnIndex("CATEGORIA"))));
                    //esta bien, pero el cursor no soporta la imagen
                    byte[] imgByte = cursor.getBlob(cursor.getColumnIndex("IMAGEN"));
                    if(imgByte!=null)
                        comida.setImagen(BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length));
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

    public List<Comida> getComidasNoUsuario(String email){
        try {
            open();
            Cursor cursor = database.query(
                    Esquemas.TABLA_COMIDA,
                    new String []{
                            "ID"//, "EMAIL_USUARIO", "TITULO","RACIONES","PRECIO","DESCRIPCION","SALADO","DULCE","VEGETARIANO","CELIACO","CATEGORIA","IMAGEN", "LATITUD", "LONGITUD"
                    },
                    "EMAIL_USUARIO != ?",
                    new String [] {
                            email
                    }, null, null, null);
            // looping through all rows and adding to list
            List<Integer> idsComidas = new ArrayList<Integer>();
            if (cursor.moveToFirst()) {//forma de recorrer la tabla, equivalente a resultset
                do {
                    idsComidas.add(cursor.getInt(cursor.getColumnIndex("ID")));
                } while (cursor.moveToNext());
            }
            cursor.close();
            close();
            List<Comida> misComidas = new ArrayList<Comida>();
            for(int i=0;i<idsComidas.size();i++)
                misComidas.add(getComidaById(idsComidas.get(i)));
            return misComidas;
        }catch (Exception e) {
            Log.e(null, "error en getNoComidasUsuario()", e);
            return null;
        }
    }


    public List<Comida> getAllComidas(){

        try {
            open();
            String selectQuery = "SELECT * FROM COMIDAS";
            Cursor cursor = database.rawQuery(selectQuery, null);
            // looping through all rows and adding to list
            List<Comida> comidas = new ArrayList<Comida>();
            if (cursor.moveToFirst()) {//forma de recorrer la tabla, equivalente a resultset
                Comida comida = new Comida();
                //comida.setId(cursor.getInt(cursor.getColumnIndex("ID")));
                comida.setEmail_usuario(cursor.getString(cursor.getColumnIndex("EMAIL_USUARIO")));
                comida.setTitulo(cursor.getString(cursor.getColumnIndex("TITULO")));
                comida.setRaciones(cursor.getInt(cursor.getColumnIndex("RACIONES")));
                comida.setPrecio(cursor.getDouble(cursor.getColumnIndex("PRECIO")));
                comida.setDescripcion(cursor.getString(cursor.getColumnIndex("DESCRIPCION")));
                comida.setSalado(cursor.getInt(cursor.getColumnIndex("SALADO"))>0);
                comida.setDulce(cursor.getInt(cursor.getColumnIndex("DULCE"))>0);
                comida.setVegetariano(cursor.getInt(cursor.getColumnIndex("VEGETARIANO"))>0);
                comida.setCeliaco(cursor.getInt(cursor.getColumnIndex("CELIACO"))>0);
                comida.setCategoria(Categoria.valueOf(cursor.getString(cursor.getColumnIndex("CATEGORIA"))));
                comida.setLatitud(cursor.getDouble(cursor.getColumnIndex("LATITUD")));
                comida.setLongitud(cursor.getDouble(cursor.getColumnIndex("LONGITUD")));
                //esta bien, pero el cursor no soporta la imagen
                byte[] imgByte = cursor.getBlob(cursor.getColumnIndex("IMAGEN"));
                if(imgByte!=null)
                    comida.setImagen(BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length));
                comidas.add(comida);
            }
            cursor.close();
            close();
            return comidas;
        }catch(Exception e){
            Log.e(null, "error en getComidaById");
            return null;
        }
    }

    private boolean updateRacionesComida(int idComida, int cantidad){
        try {
            Comida c = getComidaById(idComida);
            c.setRaciones(c.getRaciones()-cantidad);
            open();
            database.update(Esquemas.TABLA_COMIDA, c.toContentValues(), "ID = ?", new String[] {
                    String.valueOf(idComida)
            });

        }catch (Exception e) {
            return false;
        }finally {
            close();
        }
        return true;
    }

    //-----------------metodos reservas---------------------


    private void deleteAllReservas(){
        open();
        String selectQuery =  "DELETE FROM "+Esquemas.TABLA_RESERVAS;
        database.execSQL(selectQuery);
        close();
    }


    public boolean insertReserva(int idComida, String emailComprador, Timestamp fechaVenta, int cantidad){
        try {
            open();
            ContentValues cv = new ContentValues();
            cv.put("ID_COMIDA", idComida);
            cv.put("EMAIL_COMPRADOR", emailComprador);
            cv.put("FECHA_VENTA", String.valueOf(fechaVenta));
            cv.put("CANTIDAD", cantidad);
            database.insert(Esquemas.TABLA_RESERVAS, null, cv);

        }catch (Exception e) {
            return false;
        }finally {
            close();
        }
        return updateRacionesComida(idComida, cantidad);
    }

    public List<Reserva> getReservasUsuario(String email){
        try {
            open();
            Cursor cursor = database.query(
                    Esquemas.TABLA_RESERVAS,
                    new String []{
                            "ID_COMIDA","EMAIL_COMPRADOR","FECHA_VENTA","CANTIDAD"
                    },
                    "EMAIL_COMPRADOR = ?",
                    new String [] {
                            email
                    }, null, null, null);

            List<Reserva> reservas = new ArrayList<>();
            if (cursor.moveToFirst()) {
                do {
                    Reserva r = new Reserva();
                    r.setId_comida(cursor.getInt(cursor.getColumnIndex("ID_COMIDA")));
                    r.setEmail_usuario(cursor.getString(cursor.getColumnIndex("EMAIL_COMPRADOR")));
                    r.setCantidad(cursor.getInt(cursor.getColumnIndex("CANTIDAD")));
                    r.setFecha_venta( Timestamp.valueOf(cursor.getString(cursor.getColumnIndex("FECHA_VENTA"))));
                    reservas.add(r);
                } while (cursor.moveToNext());
            }
            cursor.close();
            close();
            for(int i=0;i<reservas.size();i++)
                setImagenYtituloYPrecio(reservas.get(i));
            return reservas;
        }catch (Exception e) {
            Log.e(null, "error en getReservasUsuario()", e);
            return null;
        }
    }







    //-----------------metodos chat---------------------

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


    public void updateUsuario(Usuario usuario) {
        open();
        ContentValues values = new ContentValues();
        values.put("NOMBRE", usuario.getNombre());
        values.put("TELEFONO", usuario.getTelefono());
        values.put("PASSWORD", usuario.getPassword());
        database.update(Esquemas.TABLA_USUARIO, values,
                "EMAIL = ?",
                new String [] {
                    usuario.getEmail()
                });
        close();
    }
}
