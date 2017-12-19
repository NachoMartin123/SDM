package com.example.nacho.proyectosdm;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nacho.proyectosdm.modelo.Usuario;
import com.example.nacho.proyectosdm.persistence.esquemas.Esquemas;
import com.example.nacho.proyectosdm.persistence.utils.DdbbDataSource;
import com.example.nacho.proyectosdm.persistence.utils.MyDBHelper;

import org.w3c.dom.Text;


public class LoginActivity extends AppCompatActivity {


    //EditText mUsuario;
    String emailUsuario;
    EditText mContraseña;
    //EditText email;
    DdbbDataSource datos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // para que no se gire
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        datos = new DdbbDataSource(getApplicationContext());

        //verificar Usuario
        emailUsuario = ((EditText)findViewById(R.id.contraseña)).getText().toString();
    }

    public void registrarse(View view) {
        Intent miIntent = null;
        miIntent =new Intent(LoginActivity.this,RegistroActivity.class);
        if (miIntent != null) {
        startActivity(miIntent); } }

    public void iniciarSesion(View view){

        try {
            // consultamos la base de datos
            Usuario usuario = datos.getUserByEmail(emailUsuario);
            if(usuario==null)
                ;//toast usuario no completado
            //Cambiamos de activity
            Intent miIntent = null;
            switch (view.getId()){
                case R.id.btnUsuarioRegistrado:
                    miIntent=new Intent(LoginActivity.this,PlatosCercaActivity.class);
                    break;}
            if (miIntent!=null){
                startActivity(miIntent);
            }


        }catch (Exception e){

            limpiar();
            Toast.makeText(getApplicationContext(),"El usuario no existe",Toast.LENGTH_LONG).show();
        }
   }


        // consultamos la base de datos con el usuario.

    /*public void consultarMyBD() {

        MyDBHelper conn = new MyDBHelper(this,"chefya.db",null,1);
        SQLiteDatabase database =  conn.getReadableDatabase();
        String[] parametros = {mUsuario.getText().toString()};
        String[] campos = {Esquemas.NOMBRE, Esquemas.CONTRASEÑA};

        // recorrer la base de datos                        Lo consultamos por el nombre de usuario

           Cursor cursor = database.query(Esquemas.TABLA_USUARIO, campos, Esquemas.NOMBRE + "=?", parametros, null, null, null);
           cursor.moveToFirst();
           mUsuario.setText(cursor.getString(0));
           mContraseña.setText(cursor.getString(1));
           cursor.close();

           Toast.makeText(getApplicationContext(), "Bienvenido: " + mUsuario.getText() + " , ¿que te apetece comer hoy?", Toast.LENGTH_LONG).show();


    }*/


    // limpia los campos si el usuario no esta registrado.
    private void limpiar() {
        mUsuario.setText("");
        mContraseña.setText("");
    }
}



