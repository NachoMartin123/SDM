package com.example.nacho.proyectosdm;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nacho.proyectosdm.modelo.Usuario;
import com.example.nacho.proyectosdm.persistence.utils.DdbbDataSource;


public class LoginActivity extends AppCompatActivity {

    DdbbDataSource datos;//este atributo accede a la bbdd


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // para que no se gire
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        datos = new DdbbDataSource(getApplicationContext());
    }

    public void registrarse(View view) {
        Intent miIntent = null;
        miIntent =new Intent(LoginActivity.this,RegistroActivity.class);
        if (miIntent != null) {
            startActivity(miIntent); } }


    public void iniciarSesion(View view){

        String emailUsuario = ((EditText)findViewById(R.id.usuario)).getText().toString();
        String contrasenaUsuario = ((EditText)findViewById(R.id.contrasena)).getText().toString();

        Usuario usuario = datos.getUserByEmail(emailUsuario);
        if(emailUsuario.isEmpty() || contrasenaUsuario.isEmpty()){
            Toast.makeText(getApplicationContext(), "Rellene todos los campos", Toast.LENGTH_LONG).show();
        }
        else if(usuario==null) {
            Toast.makeText(getApplicationContext(), "El usuario no existe", Toast.LENGTH_LONG).show();
            limpiar();
        }

        else if(usuario.getEmail().equals(emailUsuario) && usuario.getPassword().equals(contrasenaUsuario)){
            Intent miIntent = null;
            switch (view.getId()){
                case R.id.btnIniciarSesion:
                    miIntent=new Intent(LoginActivity.this,PlatosCercaActivity.class);
                    break;}
            if (miIntent!=null){
                miIntent.putExtra("emailUsuario", usuario.getEmail());
                startActivity(miIntent);
            }
        }
        else {
            Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrectos", Toast.LENGTH_LONG).show();
            limpiar();
        }
   }

    // limpia los campos si el usuario no esta registrado.
    private void limpiar() {
        //((EditText)findViewById(R.id.usuario)).setText("");//volver a meter el usuario es un rollo
        ((EditText)findViewById(R.id.contrasena)).setText("");
    }

}



