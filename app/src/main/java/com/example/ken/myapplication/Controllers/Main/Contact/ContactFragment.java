package com.example.ken.myapplication.Controllers.Main.Contact;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.example.ken.myapplication.Domain.Film;
import com.example.ken.myapplication.R;



public class ContactFragment extends Fragment implements View.OnClickListener{


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //View
        View view = inflater.inflate(R.layout.fragment_contact, container, false);

        Button buttonRoute = view.findViewById(R.id.buttonRoute);
        Button buttonFeedback = view.findViewById(R.id.buttonFeedback);

        buttonRoute.setOnClickListener(this);
        buttonFeedback.setOnClickListener(this);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onClick(View v) {

        //Cast
        Button button = (Button)v;
        Intent intent;

        if (button.getText().equals(getString(R.string.route))) {


            intent = new Intent(getContext(), MapsActivity.class);

        } else if (button.getText().equals(getString(R.string.feedback_given))) {

            Film film = null;

            intent = new Intent(getContext(), FeedbackActivity.class);
            intent.putExtra("FILM_OBJECT", film);

        } else return;

        //Start intent
        startActivity(intent);
    }
}
