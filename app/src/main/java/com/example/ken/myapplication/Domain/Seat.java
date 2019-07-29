package com.example.ken.myapplication.Domain;

import java.io.Serializable;


public class Seat implements Serializable {

    private int beginSeatNumber;
    private int endsSeatNumber;

    public Seat(int beginSeatNumber, int endsSeatNumber) {
        this.beginSeatNumber = beginSeatNumber;
        this.endsSeatNumber = endsSeatNumber;
    }

    public int getBeginSeatNumber() {
        return beginSeatNumber;
    }

    public int getEndsSeatNumber() {
        return endsSeatNumber;
    }

}
