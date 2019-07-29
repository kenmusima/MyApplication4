package com.example.ken.myapplication.Controllers.Main.Contact;



import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.ken.myapplication.Domain.Film;
import com.example.ken.myapplication.R;
import com.example.ken.myapplication.Util.FeedbackValidator;

public class FeedbackActivity extends AppCompatActivity implements View.OnClickListener, DialogInterface.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback1);

        Film film = (Film)getIntent().getSerializableExtra("FILM_OBJECT");
        String title = getString(R.string.activity_feedback) + " " + (film == null ? getString(R.string.cinema) : film.getName());

        TextView textViewContact = findViewById(R.id.textViewFeedback);
        textViewContact.setText(title);

        Button button = findViewById(R.id.buttonSendFeedback);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        //Views ophalen
        RatingBar ratingBarCinema = findViewById(R.id.ratingBarCinema);
        EditText editTextFirstName = findViewById(R.id.editText2);
        EditText editTextLastName = findViewById(R.id.editText3);
        EditText editTextEmailadres = findViewById(R.id.editText8);
        EditText editTextMessage = findViewById(R.id.editText9);

        float rating = ratingBarCinema.getRating();
        String firstName = editTextFirstName.getText().toString();
        String lastName = editTextLastName.getText().toString();
        String emailadres = editTextEmailadres.getText().toString();
        String message = editTextMessage.getText().toString();

        FeedbackValidator validator = new FeedbackValidator(this);
        if (validator.validate(rating, firstName, lastName, emailadres, message)) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getString(R.string.feedback_message_title));
            builder.setMessage(getString(R.string.feedback_message_content));
            builder.setPositiveButton(R.string.ok, this);
            AlertDialog dialog = builder.create();

            dialog.show();
        }
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

        finish();
    }
}
