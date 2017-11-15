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

public class MainActivity extends AppCompatActivity {

<<<<<<< HEAD
    final Intennt miIntent = new Intent(activity1, activity2);
    startActivity(myIntent, );

=======
    EditText usuario,contraseña;
>>>>>>> f1a10b6b1c0e4e2560b1aed5846492438e7e5f4f

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity);

    // recoger los datos de los componentes

        usuario =(EditText) findViewById(R.id.usuario);
        contraseña = (EditText) findViewById(R.id.contraseña);



    }

    public void onClick(View view){

        //llamamos a la otra activity
        Intent miIntent = null;
        switch (view.getId()){
            case R.id.btnUsuarioRegistrado:
                miIntent = new Intent(MainActivity.this,PlatosCercaDeTi.class);
                break; }
        if(miIntent!=null){
            startActivity(miIntent);}

        usuarioRegistrado();
    }

    // registra los usuarios en la base de datos
    public void usuarioRegistrado(){


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
