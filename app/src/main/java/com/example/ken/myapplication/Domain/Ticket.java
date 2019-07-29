package com.example.ken.myapplication.Domain;


import java.io.Serializable;



public class Ticket implements Serializable {

    //private int rownumber;
    private int beginSeatNumber;
    private int endSeatNumber;
    private String filmTitle;
    private String runTime;
    private String posterURL;


    public Ticket(int beginSeatNumber, int endSeatNumber, String filmTitle, String runTime, String posterURL) {
        this.beginSeatNumber = beginSeatNumber;
        this.endSeatNumber = endSeatNumber;
        this.filmTitle = filmTitle;
        this.runTime = runTime;
        this.posterURL = posterURL;
    }

    public int getBeginSeatNumber() {
        return beginSeatNumber;
    }

    public int getEndSeatNumber() {
        return endSeatNumber;
    }

    public String getFilmTitle() {
        return filmTitle;
    }

    public String getRunTime() {
        return runTime;
    }

    public String getPosterURL() {
        return posterURL;
    }
}
