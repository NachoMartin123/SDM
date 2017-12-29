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

import java.sql.Timestamp;

/**
 * Created by Nacho on 29/12/2017.
 */

public class CustomListReservas extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] titulos;
    private final Integer[] cantidades;
    private final Double[] precios;
    private final Timestamp[] fechas_ventas;
    private final Bitmap[] imagenes;
    

    public CustomListReservas(Activity context, String[] titulos, Integer[] cantidades, Double[] precios, Timestamp[] fechas_ventas, Bitmap[] imagenes) {
        super(context, R.layout.list_single, titulos);
        this.context = context;
        this.titulos = titulos;
        this.cantidades = cantidades;
        this.precios = precios;
        this.fechas_ventas = fechas_ventas;
        this.imagenes = imagenes;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_single, null, true);
        //reutilizo campos de list_single
        TextView txtTitulo = (TextView) rowView.findViewById(R.id.singleTitulo);
        TextView txtCantidades= (TextView) rowView.findViewById(R.id.singleRaciones);
        TextView txtPrecios= (TextView) rowView.findViewById(R.id.singlePrecio);
        TextView txtFecha= (TextView) rowView.findViewById(R.id.singleCiudad);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.imgSingle);

        txtTitulo.setText(titulos[position]);
        txtCantidades.setText("Raciones compradas: "+cantidades[position]);
        txtPrecios.setText("Precio/ud: "+precios[position]+"€");
        txtFecha.setText("Fecha compra: "+fechas_ventas[position].toString().substring(0, fechas_ventas[position].toString().length()-7));

        if(imagenes[position]!=null)
            imageView.setImageBitmap(Bitmap.createScaledBitmap(imagenes[position], 550, 370, false));

        return rowView;
    }


}
