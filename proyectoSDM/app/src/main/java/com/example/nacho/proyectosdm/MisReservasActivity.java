package com.example.nacho.proyectosdm;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.nacho.proyectosdm.modelo.Comida;
import com.example.nacho.proyectosdm.modelo.Reserva;
import com.example.nacho.proyectosdm.modelo.Usuario;
import com.example.nacho.proyectosdm.persistence.utils.DdbbDataSource;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Locale;

public class MisReservasActivity extends AppCompatActivity {


    DdbbDataSource datos;//acceso a datos bbdd
    private Usuario usuarioActual=null;
    private List<Reserva> comidasReservadas;

    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // para que no se gire
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_reservas);

        datos = new DdbbDataSource(getApplicationContext());

        String emailUsuarioActual = getIntent().getExtras().getString("emailUsuario");
        usuarioActual = datos.getUserByEmail(emailUsuarioActual);
        comidasReservadas = datos.getReservasUsuario(emailUsuarioActual);

        inicializarListaMisReservas(comidasReservadas);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    private void inicializarListaMisReservas(List<Reserva> reservas) {
        if (reservas.size() > 0) {
            String[] titulos = new String[reservas.size()];
            Integer[] cantidades = new Integer[reservas.size()];
            Double[] precios = new Double[reservas.size()];
            Timestamp[] fechas = new Timestamp[reservas.size()];
            Bitmap[] imagenes = new Bitmap[reservas.size()];

            for (int i = 0; i < reservas.size(); i++) {
                titulos[i] = reservas.get(i).getTitulo();
                cantidades[i] = reservas.get(i).getCantidad();
                precios[i] = reservas.get(i).getPrecio();
                fechas[i] = reservas.get(i).getFecha_venta();
                imagenes[i] = reservas.get(i).getImagen();
            }

            CustomListReservas adapter = new CustomListReservas(MisReservasActivity.this, titulos, cantidades, precios, fechas, imagenes);
            list = (ListView) findViewById(R.id.list);
            list.setAdapter(adapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    Toast.makeText(MisReservasActivity.this, "You Clicked at " + position, Toast.LENGTH_SHORT).show();
                    //

                }
            });
        }else{
            list = (ListView) findViewById(R.id.list);
            list.setAdapter(null);
        }
    }


}
