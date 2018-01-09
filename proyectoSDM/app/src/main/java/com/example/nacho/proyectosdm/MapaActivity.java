package com.example.nacho.proyectosdm;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.nacho.proyectosdm.modelo.Comida;
import com.example.nacho.proyectosdm.persistence.utils.DdbbDataSource;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapaActivity extends AppCompatActivity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mapa);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager()
                .findFragmentById(R.id.mapa);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        DdbbDataSource bd = new DdbbDataSource(this);

        String emailUsuarioActual = getIntent().getExtras().getString("emailUsuario");
        List<Comida> comidas = bd.getComidasNoUsuario(emailUsuarioActual);

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
