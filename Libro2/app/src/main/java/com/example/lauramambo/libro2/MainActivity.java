package com.example.lauramambo.libro2;


import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.Date;


public class MainActivity extends AppCompatActivity {

    public static String OBJETO_KEY = "libros";

    public Libro22 libro;
    public Intent mIntent;
    public Bundle b;
    public EditText titulo;
    public EditText autor;
    public Date fecha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mIntent = new Intent();
        b = new Bundle();

    }

    // metodo boton para pasar a otra activity
    public void next(View view){
        mIntent.putExtra("libros",(Parcelable) this.libro);
        //llamamos a la subactividad
        startActivity (mIntent);
    }

    }

