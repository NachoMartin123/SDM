package com.example.nacho.proyectosdm;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.nacho.proyectosdm.modelo.Comida;
import com.example.nacho.proyectosdm.persistence.utils.DdbbDataSource;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class PlatosCercaActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener,
        SeleccionExtrasDialog.OnSeleccionExtrasRealizada,
        OnMapReadyCallback {

    private ActionBarDrawerToggle mDrawerToggle;

    private boolean[] extraSeleccionados = {true, true, true, true};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // para que no se gire
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_platoscerca);

        DrawerLayout drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);

        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                R.string.drawer_open,
                R.string.drawer_close
        );

        drawerLayout.addDrawerListener(mDrawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        navigationView.setNavigationItemSelectedListener(this);

        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager()
                .findFragmentById(R.id.mapa);
        mapFragment.getMapAsync(this);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

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

        return false;
    }

    //metodos menu
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        //Inflate the menu
        getMenuInflater().inflate(R.menu.barramenu_platoscerca, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        else if (item.getItemId() == R.id.itemExtras) {
            DialogFragment dialogo = SeleccionExtrasDialog.crear(extraSeleccionados);
            dialogo.show(getSupportFragmentManager(), "dialogoExtras");
        }
        else {
            item.setChecked(!item.isChecked());
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSeleccionExtraRealizada(boolean[] seleccion) {
        this.extraSeleccionados = seleccion;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        DdbbDataSource bd = new DdbbDataSource(this);

        List<Comida> comidas = bd.getComidasUsuario("jon@gmail.com");

        LatLngBounds.Builder creadorRango = LatLngBounds.builder();
        for (Comida comida: comidas) {
            LatLng posicion = comida.crearPosicion();
            googleMap.addMarker(new MarkerOptions()
                    .position(posicion)
                    .title(comida.getDescripcion()));
            creadorRango.include(posicion);
        }

        googleMap.moveCamera(CameraUpdateFactory
            .newLatLngBounds(creadorRango.build(), 100));
    }
}
