package com.example.nacho.proyectosdm;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nacho.proyectosdm.R;

/**
 * Created by Nacho on 23/12/2017.
 */
public class CustomList extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] titulos;
    private final Integer[] raciones;
    private final Double[] precios;
    private final Bitmap[] imagenes;
    private final String[] ciudades;

    public CustomList(Activity context, String[] titulos, Integer[] raciones, Double[] precios, String[] ciudades, Bitmap[] imagenes) {
        super(context, R.layout.list_single, titulos);
        this.context = context;
        this.titulos = titulos;
        this.imagenes = imagenes;
        this.precios = precios;
        this.raciones = raciones;
        this.ciudades = ciudades;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_single, null, true);
        TextView txtTitulo = (TextView) rowView.findViewById(R.id.singleTitulo);
        TextView txtRaciones= (TextView) rowView.findViewById(R.id.singleRaciones);
        TextView txtPrecios= (TextView) rowView.findViewById(R.id.singlePrecio);
        TextView txtCiudades= (TextView) rowView.findViewById(R.id.singleCiudad);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.imgSingle);

        txtTitulo.setText(titulos[position]);
        txtRaciones.setText("raciones restantes: "+raciones[position]);
        txtPrecios.setText("Precio: "+precios[position]);
        txtCiudades.setText("Ciudad: "+ciudades[position]);

        if(imagenes[position]!=null)
            imageView.setImageBitmap(Bitmap.createScaledBitmap(imagenes[position], 550, 370, false));

        return rowView;
    }


}
