package com.example.ken.myapplication.Seat;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ken.myapplication.Database.TicketDatabase;
import com.example.ken.myapplication.Domain.Film;
import com.example.ken.myapplication.Domain.Seat;
import com.example.ken.myapplication.Domain.Ticket;
import com.example.ken.myapplication.MpesaActivity;
import com.example.ken.myapplication.R;

import java.util.ArrayList;
import java.util.HashMap;

public class BookSeat extends AppCompatActivity implements View.OnClickListener {

    private final String CHAIR_ID_NAME = "selectableChair";
    private Bitmap redChair;
    private Bitmap blueChair;
    private Bitmap greenChair;
    private HashMap<ImageView, Integer> seats;
    private TextView textViewChairSelected;
    private ArrayList<Integer> selectedChairIDs;
    private Film film;
    private TicketDatabase database;
    private int lastSeat;
    private ArrayList<Integer> orderedSeats;

    private int totalChairs;
    private String price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat);

        //Database
        database = new TicketDatabase(this);

        selectedChairIDs = new ArrayList<Integer>();
        redChair = BitmapFactory.decodeResource(getResources(), R.drawable.chair_red);
        blueChair = BitmapFactory.decodeResource(getResources(), R.drawable.chair_blue);
        greenChair = BitmapFactory.decodeResource(getResources(), R.drawable.chair_green);

        textViewChairSelected = findViewById(R.id.textViewChairSelected);

        Button buttonSelectChair = findViewById(R.id.buttonSelectChair);
        buttonSelectChair.setOnClickListener(this);

        //Get intent
        film = (Film) getIntent().getSerializableExtra("TICKET_OBJECT");
        totalChairs = (int) getIntent().getSerializableExtra("totalNumberOfChairs");
        price = (String) getIntent().getSerializableExtra("totalPrice");

        //Get seats
        getSeats();
    }

    @Override
    public void onClick(View v) {

        //Seats have been selected
        Button buttonSelectChair = findViewById(R.id.buttonSelectChair);

        if (totalChairs >= 1) {
            buttonSelectChair.setVisibility(View.VISIBLE);
        }
        int id = v.getId();

        if (id == R.id.buttonSelectChair) {

            int beginSeatNumber = selectedChairIDs.get(0);
            int endSeatNumber = selectedChairIDs.get(selectedChairIDs.size() - 1);

            Seat seat = new Seat(beginSeatNumber, endSeatNumber);

            Intent intent = new Intent(getApplicationContext(), MpesaActivity.class);
            intent.putExtra("SEAT_OBJECT", seat);
            intent.putExtra("FILM_OBJECT", film);
            intent.putExtra("totalPrice", price);

            startActivity(intent);

        } else {

            for (ImageView seat : seats.keySet()) {
                seat.setImageBitmap(greenChair);
            }

            selectedChairIDs.clear();

            int number = seats.get(v);
            for (int a = 0; a < totalChairs; a++)
                selectChair(number + a);

            String chairString = "";
            for (int a = 0; a < selectedChairIDs.size(); a++) {
                chairString += selectedChairIDs.get(a);
                if (a != selectedChairIDs.size() - 1)
                    chairString += ", ";
            }

            textViewChairSelected.setText(getString(R.string.seats) + " " + chairString + " " + getString(R.string.selected));
        }
    }

    private void getSeats() {

        ArrayList<Ticket> orderedTickets = database.getTicketsByFilmTitle(film.getName());
        orderedSeats = new ArrayList<Integer>();
        for (Ticket ticket : orderedTickets) {
            for (int a = ticket.getBeginSeatNumber(); a <= ticket.getEndSeatNumber(); a++) {
                orderedSeats.add(a);
            }
        }

        seats = new HashMap<>();
        int index = 1;
        while (true) {

            if (orderedSeats.contains(index)) {

                selectOrderedChair(index);

            } else {

                int id = getResources().getIdentifier(CHAIR_ID_NAME + index, "id", getPackageName());

                ImageView seat = findViewById(id);

                if (seat == null) break;

                seats.put(seat, index);

                //Add on click
                seat.setOnClickListener(this);
            }

            lastSeat = index;

            index++;
        }
    }

    private void selectChair(int number) {

        if (orderedSeats.contains(number) || selectedChairIDs.contains(number)) {

            selectChair(number + 1);

        } else if (number > lastSeat) {

            selectChair(1);

        } else {

            int id = getResources().getIdentifier(CHAIR_ID_NAME + number, "id", getPackageName());
            ImageView v = findViewById(id);


            v.setImageBitmap(blueChair);
            selectedChairIDs.add(number);
        }
    }

    private void selectOrderedChair(int number) {

        int id = getResources().getIdentifier(CHAIR_ID_NAME + number, "id", getPackageName());
        ImageView v = findViewById(id);

        if (v == null) return;

        v.setImageBitmap(redChair);
    }
}

