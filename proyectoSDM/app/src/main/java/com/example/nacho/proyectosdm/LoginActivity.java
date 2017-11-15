package com.example.nacho.proyectosdm;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nacho.proyectosdm.persistence.esquemas.Esquemas;
import com.example.nacho.proyectosdm.persistence.utils.MyDBHelper;

public class LoginActivity extends AppCompatActivity {

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

        switch (view.getId()){
            case R.id.btnUsuarioRegistrado:
                miIntent = new Intent(LoginActivity.this,PlatosCercaDeTi.class);
                break; }
        if(miIntent!=null){
            startActivity(miIntent);}

        registrarUsuario();


        //llamamos a la otra activity
        final Intent miIntent = new Intent(LoginActivity.this, PlatosCercaDeTi.class);
        startActivity(miIntent);
    }

    // registra los usuarios en la base de datos
    public void registrarUsuario(){


        MyDBHelper myBD = new MyDBHelper(this,"bd_usuaarios",null, 1);

        SQLiteDatabase db = myBD.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Esquemas.NOMBRE, usuario.getText().toString());
        values.put(Esquemas.CONTRASEÑA, contraseña.getText().toString());

        Long idResultante = db.insert(Esquemas.TABLA_USUARIO,Esquemas.NOMBRE,values);

        // ventana emergente confirmando lo que se hace bien
        Toast.makeText(getApplicationContext(),"id Registro: " + idResultante,Toast.LENGTH_SHORT); // aqui falta un show peor nose porque no me lo genera

        db.close();

    }

    //almacena los usuarios que se registran



}
