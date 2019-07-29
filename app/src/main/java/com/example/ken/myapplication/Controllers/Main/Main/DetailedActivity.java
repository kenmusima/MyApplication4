package com.example.ken.myapplication.Controllers.Main.Main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.ken.myapplication.Controllers.Main.Contact.FeedbackActivity;
import com.example.ken.myapplication.TicketSelection;

import com.example.ken.myapplication.Domain.Film;
import com.example.ken.myapplication.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

public class DetailedActivity extends AppCompatActivity implements View.OnClickListener {

    Film film;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        TextView filmDetailedTitle = findViewById(R.id.filmDetailedTitle);
        ImageView filmDetailedImage = findViewById(R.id.filmDetailedImage);
        Button filmDetailedOrder = findViewById(R.id.filmDetailedOrder);
        Button buttonGiveMovieFeedback = findViewById(R.id.buttonGiveMovieFeedback);
        TextView filmDetailedDescription = findViewById(R.id.filmDetailedDescription);

        film = (Film) getIntent().getSerializableExtra("FILM_OBJECT");
        RequestCreator requestCreator = Picasso.get().load(film.getPosterUrl());

        filmDetailedTitle.setText(film.getName());
        requestCreator.into(filmDetailedImage);
        filmDetailedDescription.setText(film.getDescription());

        filmDetailedOrder.setOnClickListener(this);
        buttonGiveMovieFeedback.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        Intent intent = null;
        switch (v.getId()) {
            case R.id.filmDetailedOrder:
                intent = new Intent(getApplicationContext(), TicketSelection.class);
                break;
            case R.id.buttonGiveMovieFeedback:
                intent = new Intent(getApplicationContext(), FeedbackActivity.class);
                break;
        }

        intent.putExtra("FILM_OBJECT", film);
        startActivity(intent);
    }
}

