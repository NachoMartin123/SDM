package com.example.nacho.proyectosdm.persistence.esquemas;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.provider.BaseColumns;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.VectorEnabledTintResources;

import com.example.nacho.proyectosdm.R;
import com.example.nacho.proyectosdm.modelo.Categoria;
import com.example.nacho.proyectosdm.modelo.Comida;
import com.example.nacho.proyectosdm.modelo.Usuario;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Laura Mambo on 18/10/2017.
 */

//Variables estaticas
public class Esquemas extends AppCompatActivity {

    // Nombre de la tabla valoratios y sus columnas
    public static final String TABLA_USUARIO = "USUARIOS";
    public static final String ID = "EMAIL";
    public static final String NOMBRE = "NOMBRE";
    public static final String CONTRASEÑA = "PASSWORD";
    public static final String CIUDAD = "CIUDAD";
    public static final String TELEFONO = "TELEFONO";

    public static final String TABLA_COMIDA = "COMIDAS";
    public static final String TABLA_MENSAJES = "MENSAJES";
    public static final String TABLA_RESERVAS = "RESERVAS";//"VENDIDOS";
    public static final String TABLA_CHATS = "CHATS";


    public static final String CREAR_TABLA_USUARIO ="CREATE TABLE " + TABLA_USUARIO + " ( " +
            "EMAIL TEXT NOT NULL UNIQUE PRIMARY KEY , " +
            "NOMBRE TEXT, " +
            "CIUDAD TEXT, "+
            "FECHA_ALTA TEXT,"+
            "PASSWORD TEXT, " +
            "ACTIVO BOOLEAN, "+
            "TELEFONO TEXT, "+
            "IMAGEN BLOB," +
            "UNIQUE(EMAIL) , " +
            "CHECK ( length(" + NOMBRE + ") > 0), " +
            "CHECK ( length("+CONTRASEÑA+") > 0)"+
            ");";


     public static final String CREAR_TABLA_COMIDA = "CREATE TABLE "+TABLA_COMIDA+" (" +
            "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "EMAIL_USUARIO TEXT," +
            "TITULO TEXT NOT NULL," +
            "RACIONES INT," +
            "PRECIO DOUBLE," +
            "DESCRIPCION TEXT," +
            "SALADO BOOLEAN," +
            "DULCE BOOLEAN," +
            "IMAGEN BLOB," +
            "VEGETARIANO BOOLEAN," +
            "CELIACO BOOLEAN," +
            "CATEGORIA TEXT,"+
            "LATITUD DOUBLE," +
            "LONGITUD DOUBLE )";

    public static final String CREAR_TABLA_RESERVAS = "CREATE TABLE "+TABLA_RESERVAS+" (" +
            "ID_COMIDA INTEGER NOT NULL, " +
            "EMAIL_COMPRADOR TEXT NOT NULL, " +
            "FECHA_VENTA TIMESTAMP NOT NULL, " +
            "CANTIDAD INTEGER, " +
            "VALORACION INTEGER, " +
            "FOREIGN KEY(ID_COMIDA) REFERENCES COMIDAS(ID), " +
            "FOREIGN KEY(EMAIL_COMPRADOR) REFERENCES USUARIOS(EMAIL), " +
            "PRIMARY KEY(ID_COMIDA, EMAIL_COMPRADOR, FECHA_VENTA))";

    public static final String CREAR_TABLA_MENSAJES = "CREATE TABLE "+TABLA_MENSAJES+" (" +
            "ID_CHAT INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "EMAIL_EMISOR TEXT, " +
            "MENSAJE TEXT NOT NULL, " +
            "FECHA TIMESTAMP, " +
            "FOREIGN KEY(ID_CHAT) REFERENCES CHATS(ID), " +
            "FOREIGN KEY(EMAIL_EMISOR) REFERENCES USUARIOS(EMAIL))";

    public static final String CREAR_TABLA_CHATS = "CREATE TABLE "+TABLA_CHATS+" (" +
            "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "EMAIL_USER_1 TEXT, " +
            "EMAIL_USER_2 TEXT, " +
            "FOREIGN KEY(EMAIL_USER_1) REFERENCES USUARIOS(EMAIL), " +
            "FOREIGN KEY(EMAIL_USER_2) REFERENCES USUARIOS(EMAIL))";


    public Esquemas(Context context){
        this.context = context;
    }

    private Context context;


    public List<Usuario> listaInicialUsuarios(){
        List<Usuario> usuarios = new ArrayList<Usuario>();
        usuarios.add(new Usuario("a","a","a","Gijon",new Timestamp(2017,10,16,14,00,00,000), true, 638111100, null));
        usuarios.add(new Usuario("jon@gmail.com","Jon","a","Gijon",new Timestamp(2017,10,16,14,00,00,000), true, 638111111, BitmapFactory.decodeResource(context.getResources(),R.drawable.jon)));
        usuarios.add(new Usuario("sansa@gmail.com","Sansa", "a","Oviedo",new Timestamp(2017,10,16,14,00,00,000), true, 638222222, BitmapFactory.decodeResource(context.getResources(),R.drawable.sansa)));
        usuarios.add(new Usuario("niguateresa@gmail.com","nigua", "1234","Oviedo",new Timestamp(2017,10,16,14,00,00,000), true, 638222222, null));
        return usuarios;
    }


    public List<Comida> listaInicialComidas(){


        List<Comida> comidas = new ArrayList<Comida>();
        Comida comida = new Comida();
        comida.setCategoria(Categoria.DESAYUNO);
        comida.setCeliaco(true);
        comida.setTitulo("Bizcocho de chocolate");
        comida.setDescripcion("Bizcocho de chocolate");
        comida.setDulce(true);
        comida.setEmail_usuario("jon@gmail.com");
        comida.setRaciones(4);
        comida.setLatitud(43.362119);
        comida.setLongitud(-5.850375);
        comidas.add(comida);

        comida = new Comida();
        comida.setCategoria(Categoria.COMIDA);
        comida.setCeliaco(true);
        comida.setTitulo("Macarrones");
        comida.setDescripcion("Macarrones");
        comida.setSalado(true);
        comida.setEmail_usuario("jon@gmail.com");
        comida.setRaciones(4);
        comida.setLatitud(43.540915);
        comida.setLongitud(-5.922073);
        comidas.add(comida);

        //Long id, String email_usuario, String nombre, int raciones, double precio, String descripcion, boolean salado, boolean dulce, boolean vegetariano, boolean celiaco, Categoria categoria, ImageView imagen, double latitud, double longitud) {
        comidas.add(new Comida("jon@gmail.com", "Pizza margarita", 4, 3.15, "pizza traicional con queso y tomate", true, false, true,false,Categoria.COMIDA,BitmapFactory.decodeResource(context.getResources(), R.drawable.pizza),43.540531, -5.654908));
        comidas.add(new Comida("jon@gmail.com", "Paella", 4, 5.95, "arroz amarillo", true, false, false,false,Categoria.COMIDA,BitmapFactory.decodeResource(context.getResources(), R.drawable.paella), 43.540531, -5.654908));
        comidas.add(new Comida("jon@gmail.com", "Pastel", 8, 1.95, "Pastel de chocolate rico rico", false, true, true,false,Categoria.MERIENDA,BitmapFactory.decodeResource(context.getResources(), R.drawable.pastel), 43.540531, -5.654908));
        comidas.add(new Comida("jon@gmail.com", "Macedonia", 3, 1.00, "Manzana, naranja, plátano y pera", false, true, true,true,Categoria.MERIENDA,BitmapFactory.decodeResource(context.getResources(), R.drawable.macedonia), 43.540531, -5.654908));
        comidas.add(new Comida("sansa@gmail.com", "Lentejas", 8, 2.15, "comida de viejas", true, false, true,false,Categoria.COMIDA,BitmapFactory.decodeResource(context.getResources(), R.drawable.lentejas), 43.354241, -5.844521));
        comidas.add(new Comida("sansa@gmail.com", "Rosquillas", 4, 0.9, "muy redondas", false, true, false,false,Categoria.DESAYUNO,BitmapFactory.decodeResource(context.getResources(), R.drawable.rosquillas), 43.354241, -5.844521));
        comidas.add(new Comida("sansa@gmail.com", "Tortilla de patata", 6, 2.0, "producto made in spain", true, false, false,false,Categoria.MERIENDA,BitmapFactory.decodeResource(context.getResources(), R.drawable.tortilla), 43.354241, -5.844521));
        comidas.add(new Comida("a", "Roscón", 8, 2.0, "para el 6 de enero", false, true, false,true,Categoria.DESAYUNO,BitmapFactory.decodeResource(context.getResources(), R.drawable.roscon), 43.361784, -5.847610));
        comidas.add(new Comida("a", "Torrijas", 9, 1.2, "la gordura máxima", false, true, false,false,Categoria.MERIENDA,BitmapFactory.decodeResource(context.getResources(), R.drawable.torrijas), 43.361784, -5.847610));
        return comidas;
    }

}








