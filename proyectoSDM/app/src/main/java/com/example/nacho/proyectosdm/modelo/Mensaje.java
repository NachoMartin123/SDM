package com.example.nacho.proyectosdm.modelo;

import java.sql.Timestamp;

/**
 * Created by Nacho on 15/11/2017.
 */

public class Mensaje {

    private String correo_emisor;
    private String correo_receptor;
    private String mensaje;
    private Timestamp fecha;

    public Mensaje(String correo_emisor, String correo_receptor, String mensaje, Timestamp fecha) {
        this.correo_emisor = correo_emisor;
        this.correo_receptor = correo_receptor;
        this.mensaje = mensaje;
        this.fecha = fecha;
    }

    public String getCorreo_emisor() {
        return correo_emisor;
    }

    public void setCorreo_emisor(String id_emisor) {
        this.correo_emisor = correo_emisor;
    }

    public String getICorreo_receptor() {
        return correo_receptor;
    }

    public void setCorreo_receptor(String correo_receptor) {
        this.correo_receptor = correo_receptor;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }
}
