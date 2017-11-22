package com.example.nacho.proyectosdm.modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nacho on 22/11/2017.
 */

public class Chat {
    private Long id;
    private String email_user_1;
    private String email_user_2;
    private List<Mensaje> mensajes;


    public Chat(Long id, String email_user_1, String email_user_2) {
        this.id = id;
        this.email_user_1 = email_user_1;
        this.email_user_2 = email_user_2;
        mensajes = new ArrayList<Mensaje>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail_user_1() {
        return email_user_1;
    }

    public void setEmail_user_1(String email_user_1) {
        this.email_user_1 = email_user_1;
    }

    public String getEmail_user_2() {
        return email_user_2;
    }

    public void setEmail_user_2(String email_user_2) {
        this.email_user_2 = email_user_2;
    }

    public List<Mensaje> getMensajes() {
        return mensajes;
    }

    public void setMensajes(List<Mensaje> mensajes) {
        this.mensajes = mensajes;
    }

    public void addMensaje(Mensaje mensaje){
        mensajes.add(mensaje);
    }
}
