package com.example.nacho.proyectosdm;

import android.content.pm.ActivityInfo;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.nacho.proyectosdm.modelo.Usuario;
import com.example.nacho.proyectosdm.persistence.utils.DdbbDataSource;

public class ConfiguracionActivity extends AppCompatActivity {

    private TextInputEditText textActualizarNombreUsuario;
    private TextInputEditText txtActualizarTelefono;
    private TextInputEditText textContrasena;
    private TextInputEditText textRecontrasena;

    private DdbbDataSource datos;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // para que no se gire
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);

        String emailUsuario = getIntent().getStringExtra("emailUsuario");

        datos = new DdbbDataSource(getApplicationContext());
        usuario = datos.getUserByEmail(emailUsuario);

        textActualizarNombreUsuario = (TextInputEditText)findViewById(R.id.textActualizarNombreUsuario);
        txtActualizarTelefono = (TextInputEditText)findViewById(R.id.txtActualizarTelefono);
        textContrasena = (TextInputEditText)findViewById(R.id.textContrasena);
        textRecontrasena = (TextInputEditText)findViewById(R.id.textRecontrasena);

        textActualizarNombreUsuario.setText(usuario.getNombre());
        txtActualizarTelefono.setText(Integer.toString(usuario.getTelefono()));
        textContrasena.setText(usuario.getPassword());
        textRecontrasena.setText(usuario.getPassword());
    }

    public void actualizar (View view){

        boolean error = false;
        if (textActualizarNombreUsuario.getText().toString().isEmpty()) {
            textActualizarNombreUsuario.setError("El nombre no puede estar vacio");
            error = true;
        }
        else {
            textActualizarNombreUsuario.setError(null);
        }
        if (txtActualizarTelefono.getText().toString().isEmpty()) {
            txtActualizarTelefono.setError("El telefono no puede estar vacio");
            error = true;
        }
        else {
            txtActualizarTelefono.setError(null);
        }
        if (textContrasena.getText().toString().isEmpty()
            || !textContrasena.getText().toString().equals(textRecontrasena.getText().toString())) {
            textContrasena.setError("Contraseña no valida");
            error = true;
        }
        else {
            textContrasena.setError(null);
        }

        if (!error) {
            usuario.setNombre(textActualizarNombreUsuario.getText().toString());
            usuario.setTelefono(Integer.parseInt(txtActualizarTelefono.getText().toString()));
            usuario.setPassword(textContrasena.getText().toString());

            datos.updateUsuario(usuario);

            finish();
        }
    }
}
