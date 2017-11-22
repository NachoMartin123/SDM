package com.example.nacho.proyectosdm.modelo;

import java.sql.Timestamp;

/**
 * Created by Nacho on 15/11/2017.
 */

public class Mensaje implements Comparable<Mensaje>{

    private Long id_chat;
    private String email_emisor;
    private String email_receptor;
    private String mensaje;
    private Timestamp fecha;

    public Mensaje(Long id_chat, String email_emisor, String mensaje, Timestamp fecha) {
        this.id_chat = id_chat;
        this.email_emisor = email_emisor;
        this.mensaje = mensaje;
        this.fecha = fecha;
    }

    public Long getId_chat() {
        return id_chat;
    }

    public void setId_chat(Long id_chat) {
        this.id_chat = id_chat;
    }

    public String getEmail_emisor() {
        return email_emisor;
    }

    public void setEmail_emisor(String email_emisor) {
        this.email_emisor = email_emisor;
    }

    public String getEmail_receptor() {
        return email_receptor;
    }

    public void setEmail_receptor(String email_receptor) {
        this.email_receptor = email_receptor;
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

    public int compareTo(Mensaje m2){
        return getFecha().compareTo(m2.getFecha());
    }
}
