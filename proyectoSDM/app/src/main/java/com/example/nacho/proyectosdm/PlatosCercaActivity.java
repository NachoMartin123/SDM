package com.example.nacho.proyectosdm;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class PlatosCercaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // para que no se gire
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_platoscerca);
        ;
    }

    //metodos menu
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        //Inflate the menu
        getMenuInflater().inflate(R.menu.barramenu_platoscerca, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.subirplato) {
            final Intent intento = new Intent(PlatosCercaActivity.this, SubirPlatoActivity.class);
            startActivity(intento);
            return true;
        }
        else if(id == R.id.mensajes){
            final Intent intento = new Intent(PlatosCercaActivity.this, MensajesActivity.class);
            startActivity(intento);
            return true;
        }
        else if(id == R.id.misReservas){
            final Intent intento = new Intent(PlatosCercaActivity.this, MisReservasActivity.class);
            startActivity(intento);
            return true;
        }
        else if(id == R.id.misPlatos){
            final Intent intento = new Intent(PlatosCercaActivity.this, MisPlatosActivity.class);
            startActivity(intento);
            return true;
        }
        else if(id == R.id.configuracion){
            final Intent intento = new Intent(PlatosCercaActivity.this, ConfiguracionActivity.class);
            startActivity(intento);
            return true;
        }
        else if(id == R.id.ayuda){ //mejos un toast?
            //final Intent intento = new Intent(PlatosCercaActivity.this, activityNuevaVal.class);
            //startActivity(intento);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
