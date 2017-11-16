package com.example.nacho.proyectosdm;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nacho.proyectosdm.persistence.esquemas.Esquemas;
import com.example.nacho.proyectosdm.persistence.utils.MyDBHelper;


public class LoginActivity extends AppCompatActivity {


    private SQLiteDatabase database;
    EditText usuario,contraseña;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    public void verificarUsuario(View view){
        // recoger los datos de los componentes
        usuario =(EditText) findViewById(R.id.usuario);
        contraseña = (EditText) findViewById(R.id.contraseña);
    }

    // registra los usuarios en la base de datos
    public void registrarse(View view) {


        try {
            escribirMyDB();

            Intent miIntent = null;
            switch (view.getId()){
                case R.id.btnRegistrarse:
                    miIntent=new Intent(LoginActivity.this,PlatosCercaActivity.class);
                    break;}

        } catch(Exception e) {
            Toast.makeText(getApplicationContext(),"No se pudo registrar", Toast.LENGTH_SHORT).show();
        }
    }

    public void usuarioRegistrado(View view){

        try {
            // consultamos la base de datos
            consultarMyBD();
        //Cambiamos de activity
        Intent miIntent=null;
        switch (view.getId()){
            case R.id.btnUsuarioRegistrado:
                miIntent=new Intent(LoginActivity.this,PlatosCercaActivity.class);
                break;}
        if (miIntent!=null){
            startActivity(miIntent);
        } } catch (Exception e){

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
            limpiar();
        }else{
            // creamos la base de datos
            MyDBHelper myBD = new MyDBHelper(getApplicationContext(), "chefya.db", null, 1);

            database = myBD.getWritableDatabase();

            ContentValues values = new ContentValues();

            values.put(Esquemas.NOMBRE, usuario.getText().toString());
            values.put(Esquemas.CONTRASEÑA, contraseña.getText().toString());
            Long idResultante = database.insert(Esquemas.TABLA_USUARIO, null, values);

            if (idResultante != -1) {
                Toast.makeText(getApplicationContext(), "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Usuario  ya esta registrado ", Toast.LENGTH_SHORT).show();
            }
            database.close();

        }
    }

        // consultamos la base de datos con el usuario.

    public void consultarMyBD() {

        MyDBHelper myBD = new MyDBHelper(getApplicationContext(), "chefya.db", null, 1);
        SQLiteDatabase db = myBD.getReadableDatabase();
        String[] parametros = {usuario.getText().toString()};
        String[] campos = {Esquemas.NOMBRE, Esquemas.CONTRASEÑA};

        // recorrer la base de datos                        Lo consultamos por el nombre de usuario
        Cursor cursor = db.query(Esquemas.TABLA_USUARIO, campos, Esquemas.NOMBRE + "=?", parametros, null, null, null);
        cursor.moveToFirst();
        usuario.setText(cursor.getString(0));
        contraseña.setText(cursor.getString(1));
        cursor.close();

        Toast.makeText(getApplicationContext(), "Bienvenido: " + usuario.getText() + " , ¿que te apetece comer hoy?", Toast.LENGTH_LONG).show();

    }


    // limpia los campos si el usuario no esta registrado.
    private void limpiar() {
        usuario.setText("");
        contraseña.setText("");
    }
}



