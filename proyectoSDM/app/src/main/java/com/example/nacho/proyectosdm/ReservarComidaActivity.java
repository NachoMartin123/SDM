package com.example.nacho.proyectosdm;

import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.nacho.proyectosdm.modelo.Comida;
import com.example.nacho.proyectosdm.persistence.utils.DdbbDataSource;

import org.w3c.dom.Text;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ReservarComidaActivity extends AppCompatActivity {

    DdbbDataSource datos;
    Comida comidaActual;
    String emailUsuarioComprador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservar_comida);

        datos = new DdbbDataSource(getApplicationContext());

        int idComidaActual = getIntent().getExtras().getInt("idComidaActual");
        emailUsuarioComprador = getIntent().getExtras().getString("emailUsuarioComprador");
        comidaActual = datos.getComidaById(idComidaActual);

        inicializarDatos();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void inicializarDatos() {
        ((TextView) findViewById(R.id.reservarTitulo)).setText(comidaActual.getTitulo());
        ((TextView) findViewById(R.id.reservarDescripcion)).setText(comidaActual.getDescripcion());
        ((TextView) findViewById(R.id.reservarPrecio)).setText(String.valueOf(comidaActual.getPrecio()));
        ((TextView) findViewById(R.id.reservarRaciones)).setText(String.valueOf(comidaActual.getRaciones()));
        ((TextView) findViewById(R.id.reservarCarac)).setText(obtenerCategorias());
        ((TextView) findViewById(R.id.reservarDireccion)).setText(obtenerDireccion());

        ImageView imgHeader = (ImageView) findViewById(R.id.reservarImageView);
        if (comidaActual.getImagen() != null)
            imgHeader.setImageBitmap(Bitmap.createScaledBitmap(comidaActual.getImagen(), 900, 450, false));
        inicializarNumberPicker();

        Button boton = (Button) findViewById(R.id.reservarButton);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NumberPicker np = (NumberPicker) findViewById(R.id.reservarNumberPicker);
                Calendar calendar = Calendar.getInstance();
                java.util.Date now = calendar.getTime();
                Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
                currentTimestamp.setHours(currentTimestamp.getHours()+1);
                datos.insertReserva(comidaActual.getId(), emailUsuarioComprador, currentTimestamp, np.getValue());
                Intent miIntent = new Intent(ReservarComidaActivity.this,PlatosCercaActivity.class);
                miIntent.putExtra("emailUsuario", emailUsuarioComprador);
                startActivity(miIntent);
            }
        });

    }

    private String obtenerCategorias(){
        String s = "";
        if (comidaActual.isSalado()) s += "  salado";
        if (comidaActual.isDulce()) s += "  dulce";
        if (comidaActual.isVegetariano()) s += "  vegetariano";
        if (comidaActual.isCeliaco()) s += "  celiaco";
        if (s.length() > 0)
            s = s.substring(2);
        return s;
    }

    private String obtenerDireccion(){
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(comidaActual.getLatitud(), comidaActual.getLongitud(), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String cityName="", stateName="", countryName="";
        if (addresses != null && addresses.size()  != 0) {
            cityName = addresses.get(0).getAddressLine(0);
            stateName = addresses.get(0).getAddressLine(1);
            countryName = addresses.get(0).getAddressLine(2);
        }
        return cityName+", "+stateName+", "+countryName;
    }


    private void inicializarNumberPicker(){
        NumberPicker np = (NumberPicker) findViewById(R.id.reservarNumberPicker);
        np.setMinValue(0);
        np.setMaxValue(comidaActual.getRaciones());
        np.setValue(0);
        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                Button boton = (Button) findViewById(R.id.reservarButton);
                if (picker.getValue() == 0) {
                    boton.setEnabled(false);
                } else {
                    boton.setEnabled(true);
                }
                DecimalFormat df = new DecimalFormat("####0.00");
                ((TextView) findViewById(R.id.reservarPrecioFinal)).setText(df.format(picker.getValue() * comidaActual.getPrecio()) + " €");
            }
        });
    }



}
