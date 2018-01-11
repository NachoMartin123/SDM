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
import com.example.nacho.proyectosdm.modelo.Usuario;
import com.example.nacho.proyectosdm.persistence.utils.DdbbDataSource;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class MisPlatosActivity extends AppCompatActivity {


    DdbbDataSource datos;//acceso a datos bbdd
    private Usuario usuarioActual=null;
    private List<Comida> comidasUsuario;

    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // para que no se gire
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_platos);

        datos = new DdbbDataSource(getApplicationContext());

        String emailUsuarioActual = getIntent().getExtras().getString("emailUsuario");
        usuarioActual = datos.getUserByEmail(emailUsuarioActual);
        comidasUsuario = datos.getComidasUsuario(emailUsuarioActual);

        inicializarListaComidas(comidasUsuario);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    private void inicializarListaComidas(List<Comida> comidas) {
        if (comidas.size() > 0) {
            String[] titulos = new String[comidas.size()];
            Integer[] imageId = new Integer[comidas.size()];
            Integer[] raciones = new Integer[comidas.size()];
            Double[] precios = new Double[comidas.size()];
            String[] ciudades = new String[comidas.size()];
            Bitmap[] imagenes = new Bitmap[comidas.size()];

            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            for (int i = 0; i < comidas.size(); i++) {
                List<Address> addresses = null;
                try {
                    addresses = geocoder.getFromLocation(comidas.get(i).getLatitud(), comidas.get(i).getLongitud(), 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (addresses.isEmpty()) {
                    ciudades[i] = "Desconocida";
                }
                else {
                    String cityName = addresses.get(0).getAddressLine(0);
                    String stateName = addresses.get(0).getAddressLine(1);
                    String countryName = addresses.get(0).getAddressLine(2);
                    ciudades[i] = cityName+", "+stateName+", "+countryName;
                }

                titulos[i] = comidas.get(i).getTitulo();
                imageId[i] = i;//comidas.get(i).getId()
                raciones[i] = comidas.get(i).getRaciones();
                precios[i] = comidas.get(i).getPrecio();
                imagenes[i] = comidas.get(i).getImagen();
            }

            CustomList adapter = new CustomList(MisPlatosActivity.this, titulos, raciones, precios, ciudades, imagenes);
            list = (ListView) findViewById(R.id.list);
            list.setAdapter(adapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    Toast.makeText(MisPlatosActivity.this, "You Clicked at " + position, Toast.LENGTH_SHORT).show();

                    if (position == 1) {
                        //code specific to first list item
                        Log.i(null, "entro en listener");
                    }

                }
            });
        }else{
            list = (ListView) findViewById(R.id.list);
            list.setAdapter(null);
        }
    }



}
