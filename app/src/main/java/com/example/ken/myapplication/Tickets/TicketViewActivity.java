package com.example.ken.myapplication.Tickets;



import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ken.myapplication.TheatreActivity;
import com.example.ken.myapplication.Domain.Ticket;
import com.example.ken.myapplication.R;
import com.example.ken.myapplication.Util.QRCodeGenerator;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

public class TicketViewActivity extends AppCompatActivity implements View.OnClickListener {

    private Ticket ticket;

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);

        ticket = (Ticket) getIntent().getSerializableExtra("TICKET_OBJECT");

        Button buttonReturnMain = findViewById(R.id.buttonReturnMain);
        TextView textViewTicketSeatRange = findViewById(R.id.textViewTicketSeatRange);
        TextView textViewTicketMovieTitle = findViewById(R.id.textViewTicketMovieTitle);
        TextView textViewTicketMovieTime = findViewById(R.id.textViewTicketMovieTime);
        ImageView imageViewQRCode = findViewById(R.id.imageViewQRCode);
        QRCodeGenerator qrCodeGenerator = new QRCodeGenerator(getColor(R.color.qrCodePrimary), getColor(R.color.qrCodeSecondary));

        buttonReturnMain.setOnClickListener(this);
        Bitmap qrCode = qrCodeGenerator.generate(ticket.getFilmTitle() + ticket.getBeginSeatNumber());
        imageViewQRCode.setImageBitmap(qrCode);

        textViewTicketSeatRange.setText(getString(R.string.chair) + " " + ticket.getBeginSeatNumber() + " - " + ticket.getEndSeatNumber());
        textViewTicketMovieTitle.setText(ticket.getFilmTitle());
        textViewTicketMovieTime.setText(ticket.getRunTime());
    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent(getApplicationContext(), TheatreActivity.class);
        intent.setFlags(FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
