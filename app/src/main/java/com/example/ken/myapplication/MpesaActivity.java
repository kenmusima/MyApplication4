package com.example.ken.myapplication;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TimeUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bdhobare.mpesa.Mpesa;
import com.bdhobare.mpesa.interfaces.AuthListener;
import com.bdhobare.mpesa.interfaces.MpesaListener;
import com.bdhobare.mpesa.models.STKPush;
import com.bdhobare.mpesa.utils.Pair;
import com.example.ken.myapplication.Database.TicketDatabase;
import com.example.ken.myapplication.Domain.Film;
import com.example.ken.myapplication.Domain.Seat;
import com.example.ken.myapplication.Domain.Ticket;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static org.apache.http.protocol.HttpDateGenerator.GMT;

public class MpesaActivity extends AppCompatActivity implements AuthListener, MpesaListener, View.OnClickListener, DialogInterface.OnClickListener{
    //TODO: Replace these values from
    public static final String BUSINESS_SHORT_CODE = "174379";
    public static final String PASSKEY = "bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919";
    public static final String CONSUMER_KEY = "MkpSKAuz4xm5PfzrdX0QdvAFoU7TMdoB";
    public static final String CONSUMER_SECRET = "zp3N6gxV9BzPgKNG";
    public static final String CALLBACK_URL = "YOUR_CALLBACK_URL";


    public static final String  NOTIFICATION = "PushNotification";
    public static final String SHARED_PREFERENCES = "com.bdhobare.mpesa_android_sdk";

    Date currentTime = Calendar.getInstance().getTime();

    Calendar c = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String strDate = sdf.format(c.getTime());

    FirebaseAuth auth;
    Button pay;
    ProgressDialog dialog;
    EditText phone;
    EditText amount;
    TextView counter;
    private Seat seat;
    private Film film;
    private String price;
    private TicketDatabase database;

    private BroadcastReceiver mRegistrationBroadcastReceiver;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mpesa);


        pay = findViewById(R.id.pay);
        phone = findViewById(R.id.phone);
        amount = findViewById(R.id.amount);
        counter = findViewById(R.id.counter_pay);

        Intent intent = getIntent();
        seat = (Seat) intent.getSerializableExtra("SEAT_OBJECT");
        film = (Film) intent.getSerializableExtra("FILM_OBJECT");
        price = (String) intent.getSerializableExtra("totalPrice");


        counter.setOnClickListener(this);

        //Set price
        price.toString();
        amount.setText(price);


        Mpesa.with(this, CONSUMER_KEY, CONSUMER_SECRET);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Processing");
        dialog.setIndeterminate(true);

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String p = phone.getText().toString();
                int a = Integer.valueOf(amount.getText().toString());
                if (p.isEmpty()){
                    phone.setError("Enter phone.");
                    return;
                }
                pay(p, a);
            }
        });

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().
                        equals(NOTIFICATION)) {
                    String title = intent.getStringExtra("title");
                    String message = intent.getStringExtra("message");
                    int code = intent.getIntExtra("code", 0);
                    showDialog(title, message, code);
                }
            }
        };
    }

    private void showDialog(String title, String message, int code) {
        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .title(title)
                .titleGravity(GravityEnum.CENTER)
                .customView(R.layout.success_dialog, true)
                .positiveText("OK")
                .cancelable(false)
                .widgetColorRes(R.color.colorPrimary)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        super.onPositive(dialog);
                        dialog.dismiss();
                        finish();
                    }
                })
                .build();
        View view=dialog.getCustomView();
        TextView messageText = view.findViewById(R.id.message);
        ImageView imageView = view.findViewById(R.id.success);
        if (code != 0){
            imageView.setVisibility(View.GONE);
        }
        messageText.setText(message);
        dialog.show();
    }

    private void pay(String phone, int amount) {
        dialog.show();
        STKPush.Builder builder = new STKPush.Builder(BUSINESS_SHORT_CODE, PASSKEY, amount,BUSINESS_SHORT_CODE, phone);

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        String token = sharedPreferences.getString("InstanceID", "");
        builder.setFirebaseRegID(token);
        STKPush push = builder.build();
        Mpesa.getInstance().pay(this, push);
    }

    @Override
    public void onAuthError(Pair<Integer, String> result) {
        Log.e("Error", result.message);
    }

    @Override
    public void onAuthSuccess() {
        //TODO make payment
        pay.setEnabled(true);
    }

    @Override
    public void onMpesaError(Pair<Integer, String> result) {
        dialog.hide();
        Toast.makeText(this, result.message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMpesaSuccess(String MerchantRequestID, String CheckoutRequestID, String CustomerMessage) {
        dialog.hide();
        Toast.makeText(this, CustomerMessage, Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onResume() {
        super.onResume();

        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(NOTIFICATION));

    }
    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.counter_pay:

                database = new TicketDatabase(this);



                //int rowNumber = seat.getRowNumber();
                int beginSeatNumber = seat.getBeginSeatNumber();
                int endSeatNumber = seat.getEndsSeatNumber();

                String filmName = film.getName();
                String runtime = strDate.toString();

                String posterURL = film.getPosterUrl();

                Ticket ticket = new Ticket(beginSeatNumber, endSeatNumber, filmName, runtime, posterURL);

                database.buyTicket(film,ticket);


                //Add final message
                //Successful
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("The booking was successful!!");
                builder.setPositiveButton(getApplicationContext().getString(R.string.ok), this);
                AlertDialog dialog = builder.create();
                dialog.show();

                startActivity(new Intent(this,TheatreActivity.class));
                break;
        }
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        Intent intent = new Intent(getApplicationContext(), TheatreActivity.class);
        intent.setFlags(FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int sel = item.getItemId();

        if(sel == R.id.next){
            Intent intent = new Intent(MpesaActivity.this, TheatreActivity.class);
            startActivity(intent);
        }
        else if (sel == R.id.nav_logout) {
            Intent iut = new Intent(MpesaActivity.this,LogInActivity.class);
            startActivity(iut);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.next, menu);

        return super.onCreateOptionsMenu(menu);
    }

}



