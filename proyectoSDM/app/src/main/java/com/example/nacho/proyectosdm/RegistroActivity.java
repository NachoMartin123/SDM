package com.example.nacho.proyectosdm;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nacho.proyectosdm.persistence.esquemas.Esquemas;
import com.example.nacho.proyectosdm.persistence.utils.DdbbDataSource;
import com.example.nacho.proyectosdm.persistence.utils.MyDBHelper;

public class RegistroActivity extends AppCompatActivity {

    EditText usuario;
    EditText contraseña;
    EditText email;
    EditText ciudad;
    EditText telefono;
    EditText Recontraseña;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        //verificar Usuario
        usuario =(EditText) findViewById(R.id.usuario);
        contraseña = (EditText) findViewById(R.id.contraseña);
        Recontraseña = (EditText) findViewById(R.id.Recontraseña);
        email =(EditText) findViewById(R.id.email);
        ciudad =(EditText) findViewById(R.id.ciudad);
        telefono =(EditText) findViewById(R.id.telefono);

    }

    public void registrarse(View view) {
        try {
            escribirMyDB();

        } catch(Exception e) {
            Toast.makeText(getApplicationContext(),"No se pudo registrar" , Toast.LENGTH_SHORT).show();

        }
    }

    public void escribirMyDB() {

        if (usuario.getText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(), "Usuario no valido", Toast.LENGTH_SHORT).show();
             }

        else if (contraseña.getText().toString().length() == 0 || Recontraseña.getText().toString().length() == 0 || !contraseña.getText().toString().equals(Recontraseña.getText().toString()) ) {
            Toast.makeText(getApplicationContext(), "Contraseña no valido", Toast.LENGTH_SHORT).show();

        }

        else if (email.getText().toString().length() == 0 ) {
            Toast.makeText(getApplicationContext(), "Email no valido", Toast.LENGTH_SHORT).show();

        }
        else if (telefono.getText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(), "Telefono no valido", Toast.LENGTH_SHORT).show();
            }

        else  if (ciudad.getText().toString().length() == 0 ) {
            Toast.makeText(getApplicationContext(), "Ciudad no valido", Toast.LENGTH_SHORT).show();}

        else {

            MyDBHelper conn = new MyDBHelper(this, new DdbbDataSource(this));
            SQLiteDatabase database = conn.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(Esquemas.NOMBRE, usuario.getText().toString());
            values.put(Esquemas.CONTRASEÑA, contraseña.getText().toString());
            values.put(Esquemas.ID, email.getText().toString());
            values.put(Esquemas.CIUDAD, ciudad.getText().toString());
            values.put(Esquemas.TELEFONO, telefono.getText().toString());


            Long idResultante = database.insert(Esquemas.TABLA_USUARIO, Esquemas.ID, values);
            //  Toast.makeText(getApplicationContext(), "ID" + idResultante, Toast.LENGTH_SHORT).show();

            if (idResultante != -1) {
                Toast.makeText(getApplicationContext(), "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
                Intent miIntent = null;

                miIntent = new Intent(RegistroActivity.this, PlatosCercaActivity.class);
                if (miIntent != null) {
                    startActivity(miIntent);
                }
            }
            else {
                Toast.makeText(getApplicationContext(), " Ese nombre de usuario  ya esta registrado ", Toast.LENGTH_SHORT).show();}


            database.close();

        }


} }
