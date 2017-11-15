package com.example.nacho.proyectosdm.modelo;

/**
 * Created by Nacho on 15/11/2017.
 */


public class Valoration {

    public String comment;
    public int rating;

    public Valoration(){
        this.comment = "";
        this.rating = 0;
    }

    public Valoration(int rating, String comment){
        this.comment = comment;
        this.rating = rating;
    }


    public String getComment(){
        return comment;
    }

    public int getRating(){
        return rating;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

}
