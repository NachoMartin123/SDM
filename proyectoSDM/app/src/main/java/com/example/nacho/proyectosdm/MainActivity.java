package com.example.nacho.proyectosdm;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nacho.proyectosdm.persistence.esquemas.Esquemas;
import com.example.nacho.proyectosdm.persistence.utils.MyDBHelper;

public class MainActivity extends AppCompatActivity {

    EditText usuario,contraseña;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity);

    // recoger los datos de los componentes

        usuario =(EditText) findViewById(R.id.usuario);
        contraseña = (EditText) findViewById(R.id.contraseña);



    }



    // registra los usuarios en la base de datos "OnClick();
    //public void usuarioRegistrado(){

        //llamamos a la  base de datos

      //  MyDBHelper newBD = new MyDBHelper(this,"bd_usuarios",null, 1);

        //SQLiteDatabase db = newBD.getWritableDatabase();

       // ContentValues values = new ContentValues();

       // values.put(Esquemas.ID, usuario.getText().toString());
        //values.put(Esquemas.NOMBRE, usuario.getText().toString());
       // values.put(Esquemas.CONTRASEÑA, contraseña.getText().toString());

    //    Long idResultante = db.insert(Esquemas.TABLA_USUARIO,Esquemas.ID,values);

        // ventana emergente confirmando lo que se hace bien
    //    Toast.makeText(getApplicationContext(),"id Registro: " + idResultante,Toast.LENGTH_SHORT).show();

    //    db.close();


    public void usuarioRegistrado() {
    //insert into usuario (id,nombre,telefono) values (123,'Cristian','85665223')
        MyDBHelper newBD = new MyDBHelper(this,"bd_usuarios",null, 1);

        SQLiteDatabase db = newBD.getWritableDatabase();

        String insert = "INSERT INTO " + Esquemas.TABLA_USUARIO
                + " ( " + Esquemas.ID + "," + Esquemas.NOMBRE + "," + Esquemas.CONTRASEÑA + ")" +
                " VALUES (" + "1" + ", '" + usuario.getText().toString() + "','"
                + contraseña.getText().toString() + "')";

        db.execSQL(insert);


        db.close();
        //almacena los usuarios que se registran

    }

}
