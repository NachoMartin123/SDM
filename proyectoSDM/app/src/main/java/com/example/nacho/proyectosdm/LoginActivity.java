package com.example.nacho.proyectosdm;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nacho.proyectosdm.persistence.esquemas.Esquemas;
import com.example.nacho.proyectosdm.persistence.utils.DdbbDataSource;
import com.example.nacho.proyectosdm.persistence.utils.MyDBHelper;


public class LoginActivity extends AppCompatActivity {

    /**
     * ESTA ACTIVITY FUNCIONA PERFECTAMENTE NO TOQUES NADA, SI NECESITAS MODIFICACIONES HAZMELAS SABER.
     */

    MyDBHelper conn;
    EditText usuario,contraseña,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DdbbDataSource datos = new DdbbDataSource(getApplicationContext());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //verificar Usuario
        usuario =(EditText) findViewById(R.id.usuario);
        contraseña = (EditText) findViewById(R.id.contraseña);
        email =(EditText) findViewById(R.id.email);

    }


    // registra los usuarios en la base de datos
    public void registrarse(View view) {
        try {
            escribirMyDB();

        } catch(Exception e) {
            Toast.makeText(getApplicationContext(),"No se pudo registrar" , Toast.LENGTH_SHORT).show();
            limpiar();
        }
    }

    public void iniciarSesion(View view){

        try {
            // consultamos la base de datos
            consultarMyBD();
            //Cambiamos de activity
            Intent miIntent = null;
            switch (view.getId()){
                case R.id.btnUsuarioRegistrado:
                    miIntent=new Intent(LoginActivity.this,SubirPlatoActivity.class);
                    break;}
            if (miIntent!=null){
                startActivity(miIntent);
            }


        }catch (Exception e){

            limpiar();
            Toast.makeText(getApplicationContext(),"El usuario no existe",Toast.LENGTH_LONG).show();
        }
   }
    public void escribirMyDB() {

        if (usuario.getText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(), "Usuario no valido", Toast.LENGTH_SHORT).show();
            limpiar();
        }else if (contraseña.getText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(), "Contraseña no valido", Toast.LENGTH_SHORT).show();
            limpiar(); }
        else if (email.getText().toString().length() == 0 ) {
            Toast.makeText(getApplicationContext(), "Email no valido", Toast.LENGTH_SHORT).show();
            limpiar();
        }else {


            // nos conecctamo  y escribimos
            SQLiteDatabase database = conn.getWritableDatabase();

            ContentValues values = new ContentValues();

            values.put(Esquemas.NOMBRE, usuario.getText().toString());
            values.put(Esquemas.CONTRASEÑA, contraseña.getText().toString());
            values.put(Esquemas.ID, email.getText().toString());


            Long idResultante = database.insert(Esquemas.TABLA_USUARIO, Esquemas.ID, values);
            //Toast.makeText(getApplicationContext(), "ID" + idResultante, Toast.LENGTH_SHORT).show();

            if (idResultante != -1) {
                Toast.makeText(getApplicationContext(), "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
                Intent miIntent = null;
                miIntent = new Intent(LoginActivity.this, PlatosCercaActivity.class);
                    if (miIntent != null) {
                        startActivity(miIntent); }

                limpiar();

                }
            else {
                    Toast.makeText(getApplicationContext(), "Usuario  ya esta registrado ", Toast.LENGTH_SHORT).show();
                    limpiar();
                }

                database.close();

                //insert into usuario (id,nombre,telefono) values (123,'Cristian','85665223')

                /**   String insert="INSERT INTO "+Esquemas.TABLA_USUARIO
                 +" ( " +Esquemas.ID+","+Esquemas.NOMBRE+","+Esquemas.CONTRASEÑA+")" +
                 " VALUES ("+ email.getText().toString()+", '"+usuario.getText().toString()+"','"
                 +contraseña.getText().toString()+"')";

                 database.execSQL(insert);
                 database.close();*/

            } }


        // consultamos la base de datos con el usuario.

    public void consultarMyBD() {

        MyDBHelper conn = new MyDBHelper(this,"chefya.db",null,1);
        SQLiteDatabase database =  conn.getReadableDatabase();
        String[] parametros = {usuario.getText().toString()};
        String[] campos = {Esquemas.NOMBRE, Esquemas.CONTRASEÑA, Esquemas.ID};

        // recorrer la base de datos                        Lo consultamos por el nombre de usuario

           Cursor cursor = database.query(Esquemas.TABLA_USUARIO, campos, Esquemas.NOMBRE + "=?", parametros, null, null, null);
           cursor.moveToFirst();
           usuario.setText(cursor.getString(0));
           contraseña.setText(cursor.getString(1));
           email.setText(cursor.getString(2));
           cursor.close();

           Toast.makeText(getApplicationContext(), "Bienvenido: " + usuario.getText() + " , ¿que te apetece comer hoy?", Toast.LENGTH_LONG).show();

    }


    // limpia los campos si el usuario no esta registrado.
    private void limpiar() {
        usuario.setText("");
        contraseña.setText("");
        email.setText("");
    }
}



