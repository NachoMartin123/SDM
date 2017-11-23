package com.example.nacho.proyectosdm.persistence.esquemas;

import android.content.ContentValues;
import android.provider.BaseColumns;

/**
 * Created by Laura Mambo on 18/10/2017.
 */

//Variables estaticas
public class Esquemas {

    // Nombre de la tabla valoratios y sus columnas
    public static final String TABLA_USUARIO = "USUARIOS";
    public static final String ID = "EMAIL";
    public static final String NOMBRE = "NOMBRE";
    public static final String CONTRASEÑA = "PASSWORD";

    public static final String TABLA_COMIDA = "COMIDAS";
    public static final String TABLA_MENSAJES = "MENSAJES";
    public static final String TABLA_VENDIDOS = "VENDIDOS";
    public static final String TABLA_CHATS = "CHATS";

    //Scrip para crear la base datos

  //  public static final String CREAR_TABLA_USUARIO = "CREATE TABLE" + TABLA_USUARIO + " ( " + ID + " INTEGER, " + NOMBRE + " TEXT," + NOMBRE + " TEXT," + CONTRASEÑA + " TEXT )";

    //## POR EL MOMENTO VAMOS A USAR ESTA HASTA QUE HAYA MAS PARAMETROS.

    /*public static final String CREAR_TABLA_USUARIO =
            "CREATE TABLE " + TABLA_USUARIO + " ( " +
                    ID + " TEXT NOT NULL, " +
                    NOMBRE + " TEXT NOT NULL, " +
                    CONTRASEÑA + " TEXT  NOT NULL, " +
                    "CONSTRAINT nombre_unique UNIQUE (" + NOMBRE + ")," +
                    "CHECK ( length(" + NOMBRE + ") > 0), " +
                    "CHECK ( length(" + CONTRASEÑA + ") > 0)"+
                    ");";*/

    //ESTA ES LA DEFINITIVA
    public static final String CREAR_TABLA_USUARIO ="CREATE TABLE " + TABLA_USUARIO + " ( " +
            "EMAIL TEXT NOT NULL UNIQUE PRIMARY KEY, " +
            "NOMBRE TEXT, " +
            "CIUDAD TEXT, "+
            "FECHA_ALTA TEXT,"+
            "PASSWORD TEXT, " +
            "ACTIVO BOOLEAN, "+
            "TELEFONO INT, "+
            "UNIQUE(EMAIL) , " +
            //"CHECK ( length(" + NOMBRE + ") > 0), " +
            //"CHECK ( length(PASSWORD) > 0)"+
            ");";


     public static final String CREAR_TABLA_COMIDA = "CREATE TABLE "+TABLA_COMIDA+" (" +
            "ID BIGINT NOT NULL PRIMARY KEY," +
            "EMAIL_USUARIO TEXT," +
            "NOMBRE TEXT NOT NULL," +
            "RACIONES INTEGER," +
            "PRECIO DOUBLE," +
            "DESCRIPCION TEXT," +
            "SALADO BOOLEAN," +
            "DULCE BOOLEAN," +
            "VEGETARIANO BOOLEAN," +
            "CELIACO BOOLEAN," +
            "CATEGORIA TEXT, )";

    public static final String CREAR_TABLA_VENDIDOS = "CREATE TABLE "+TABLA_VENDIDOS+" (" +
            "ID_COMIDA BIGINT NOT NULL PRIMARY KEY, " +
            "EMAIL_COMPRADOR TEXT, " +
            "FECHA_VENTA FECHA TIMESTAMP, " +
            "VALORACION INTEGER, " +
            "FOREIGN KEY(ID_COMIDA) REFERENCES COMIDAS(ID), " +
            "FOREIGN KEY(EMAIL_COMPRADOR) REFERENCES USUARIOS(EMAIL))";

    public static final String CREAR_TABLA_MENSAJES = "CREATE TABLE "+TABLA_MENSAJES+" (" +
            "ID_CHAT BIGINT NOT NULL PRIMARY KEY, " +
            "EMAIL_EMISOR TEXT, " +
            "MENSAJE TEXT NOT NULL, " +
            "FECHA TIMESTAMP, " +
            "FOREIGN KEY(ID_CHAT) REFERENCES CHATS(ID), " +
            "FOREIGN KEY(EMAIL_EMISOR) REFERENCES USUARIOS(EMAIL))";

    public static final String CREAR_TABLA_CHATS = "CREATE TABLE "+TABLA_CHATS+" (" +
            "ID BIGINT NOT NULL PRIMARY KEY, " +
            "EMAIL_USER_1 TEXT, " +
            "EMAIL_USER_2 TEXT, " +
            "FOREIGN KEY(EMAIL_USER_1) REFERENCES USUARIOS(EMAIL), " +
            "FOREIGN KEY(EMAIL_USER_2) REFERENCES USUARIOS(EMAIL))";





    public static final String SCRIPT_USUARIOS=
            "INSERT INTO USUARIOS (EMAIL,NOMBRE,CIUDAD,FECHA_ALTA, PASSWORD, ACTIVO, TELEFONO) VALUES ('jon@gmail.com','Jon','Gijon','2017-10-16 14:00:00.000', 'password', true, 638111111)";

   //         "INSERT INTO USUARIOS (EMAIL,NOMBRE,CIUDAD,FECHA_ALTA, PASSWORD, ACTIVO, TELEFONO) VALUES('sansa@gmail.com','Sansa','Oviedo','2017-10-16 14:00:00.000', 'password', true, 638222222);\n"+
    //        "INSERT INTO USUARIOS (EMAIL,NOMBRE,CIUDAD,FECHA_ALTA, PASSWORD, ACTIVO, TELEFONO) VALUES('bran@gmail.com','Bran','Gijon','2017-10-16 14:00:00.000', 'password', true, 638333333);\n" +
     //       "INSERT INTO USUARIOS (EMAIL,NOMBRE,CIUDAD,FECHA_ALTA, PASSWORD, ACTIVO, TELEFONO) VALUES('aria@gmail.com','Aria','Oviedo','2017-10-16 14:00:00.000', 'password', true, 638444444);";


    public static final String SCRIPT_COMIDAS =
            "INSERT INTO COMIDAS VALUES(1, 'sansa@gmail.com', 'Macarrones carbonara', 5, 5.95, 'macarrones como los que hacia mi abuela', true, false, false,true,'comida');\n"+
            "INSERT INTO COMIDAS VALUES(2, 'jon@gmail.com', 'Pizza margarita', 4, 3.15, 'pizza traicional con queso y tomate', true, false, true,false,'comida');\n"+
            "INSERT INTO COMIDAS VALUES(3, 'aria@gmail.com', 'Paella', 4, 5.95, 'pizza traicional con queso y tomate', true, false, false,false,'comida');\n"+
            "INSERT INTO COMIDAS VALUES(4, 'bran@gmail.com', 'Pastel', 8, 1.95, 'Pastel de chocolate rico rico', false, true, true,false,'Merienda');\n"+
            "INSERT INTO COMIDAS VALUES(5, 'jon@gmail.com', 'Macedonia', 3, 1.00, 'Manzana, naranja, plátano y pera', false, true, true,true,'Merienda');\n"+
            "INSERT INTO COMIDAS VALUES(6, 'sansa@gmail.com', 'Lentejas', 8, 2.15, 'comida de viejas', true, false, true,false,'comida');\n"+
            "INSERT INTO COMIDAS VALUES(7, 'bran@gmail.com', 'Rosquillas', 4, 1.20, 'muy redondas', false, true, false,false,'Desayuno');\n"+
            "INSERT INTO COMIDAS VALUES(8, 'aria@gmail.com', 'Tortilla de patata', 6, , 'producto spanish', true, false, false,false,'Merienda');";
    public static final String SCRIPT_RESTO=
            "INSERT INTO MENSAJES VALUES(1, 'sansa@gmail.com', 'La foto de esos macarrones de cuando es?', '2017-11-15 17:00:00.000000');\n"+
            "INSERT INTO MENSAJES VALUES(1, 'jon@gmail.com', 'De hoy por la mismo', '2017-11-14 18:00:00.000000');\n"+
            "INSERT INTO MENSAJES VALUES(1, 'sansa@gmail.com', 'No te creo', '2017-11-15 19:00:00.000000');\n"+
            "INSERT INTO CHATS VALUES(1, 'jon@gmail.com', 'sansa@gmail.com');";;



}








