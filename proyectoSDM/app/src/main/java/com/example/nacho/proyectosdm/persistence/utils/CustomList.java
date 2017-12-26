package com.example.nacho.proyectosdm.persistence.utils;

import android.app.Activity;
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
    private final Integer[] imageId;
    private final Boolean[] salado;
    private final Boolean[] dulce;
    private final Boolean[] celiaco;
    private final Boolean[] vegetariano;

    public CustomList(Activity context, String[] titulos, Integer[] raciones, Double[] precios, Boolean[] salado,
                      Boolean[] dulce, Boolean[] celiaco, Boolean[] vegetariano, Integer[] imageId) {
        super(context, R.layout.list_single, titulos);
        this.context = context;
        this.titulos = titulos;
        this.imageId = imageId;
        this.precios = precios;
        this.raciones = raciones;
        this.salado = salado;
        this.dulce = dulce;
        this.celiaco = celiaco;
        this.vegetariano = vegetariano;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_single, null, true);
        TextView txtTitulo = (TextView) rowView.findViewById(R.id.titulo);
        TextView txtRaciones= (TextView) rowView.findViewById(R.id.raciones);
        TextView txtPrecios= (TextView) rowView.findViewById(R.id.precio);
        TextView txtSabores= (TextView) rowView.findViewById(R.id.sabores);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);

        txtTitulo.setText(titulos[position]);
        txtRaciones.setText("raciones restantes: "+raciones[position]);
        txtPrecios.setText("Precio: "+precios[position]);
        String s = "";
        if(salado[position]) s += "  salado";
        if(dulce[position]) s +="  dulce";
        if(celiaco[position]) s +="  celiaco";;
        if(vegetariano[position]) s +="  vegetariano";;
        txtSabores.setText("Clasificación: \n"+s);


        //imageView.setImageResource(imageId[position]);
        return rowView;
    }


}
