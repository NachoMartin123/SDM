package com.example.lauramambo.libro2;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * Created by Laura Mambo on 10/10/2017.
 */

public class Libro22 implements Parcelable {

    public String titulo;
    public String autor;
    public String fecha;
    public Parcelable parcel1;

  private Libro22(String titulo, String autor, String fecha){

        this.titulo = titulo;
        this.autor = autor;
        this.fecha = fecha;

        Log.i("Titutlo1", this.titulo);
        Log.i("Autor1", this.autor);
    }

    protected Libro22(Parcel in) {

        titulo = in.readString();
        autor = in.readString();
        fecha = in.readString();
    }

    public Libro22() {

    }

    public String getAutor() {
        return autor;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }


    public static final Creator<Libro22> CREATOR = new Creator<Libro22>() {
        @Override
        public Libro22 createFromParcel(Parcel in) {
            return new Libro22(in);
        }

        @Override
        public Libro22[] newArray(int size) {
            return new Libro22[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parce1, int i) {

        parce1.writeString(titulo);
        parce1.writeString(autor);
        parce1.writeString(fecha);


    }
}
