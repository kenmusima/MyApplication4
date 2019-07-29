package com.example.ken.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import android.view.inputmethod.InputMethodManager;



public class LogInActivity extends AppCompatActivity implements View.OnClickListener {

    //defining views
    String username,password;
    private Button buttonSignIn;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignup;
    CheckBox credentials;
    private SharedPreferences loginPreferences;
    SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;

    //firebase auth object
    private FirebaseAuth mAuth;

    //progress dialog
    private ProgressDialog progressDialog;

    private boolean checkLoggedIn() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        return firebaseUser != null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //getting firebase auth object
        mAuth = FirebaseAuth.getInstance();

        //if the objects get current user method is not null
        //means user is already logged in

        //initializing views
        editTextEmail = findViewById(R.id.user);
        editTextPassword = findViewById(R.id.pass);
        buttonSignIn = findViewById(R.id.button89);

//        saveLogin = loginPreferences.getBoolean("saveLogin", false);
//
//        if (saveLogin == true) {
//            editTextEmail.setText(loginPreferences.getString("username", ""));
//            editTextPassword.setText(loginPreferences.getString("password", ""));
//            credentials.setChecked(true);
//            loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
//            loginPrefsEditor = loginPreferences.edit();
//
            progressDialog = new ProgressDialog(this);

            //attaching click listener
            buttonSignIn.setOnClickListener(this);
//        }
    }
    //method for user login
    private void userLogin(){
        String email = editTextEmail.getText().toString().trim();
        String password  = editTextPassword.getText().toString().trim();


        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter an email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter a password",Toast.LENGTH_LONG).show();
            return;
        }

        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Authenticating User Please Wait...");
        progressDialog.show();

        //logging in the user
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        //if the task is successful
                        if(task.isSuccessful()){
                            //start the profile activity
                            finish();
                            Intent intent = new Intent(LogInActivity.this,TheatreActivity.class);
                            startActivity(intent);
                        }
                    }
                })
           .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(LogInActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onClick(View view) {



//        if (view == buttonSignIn) {
//            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//            imm.hideSoftInputFromWindow(editTextEmail.getWindowToken(), 0);
//
//            username = editTextEmail.getText().toString();
//            password = editTextPassword.getText().toString();
//
//            if (credentials.isChecked()) {
//                loginPrefsEditor.putBoolean("saveLogin", true);
//                loginPrefsEditor.putString("username", username);
//                loginPrefsEditor.putString("password", password);
//                loginPrefsEditor.commit();
//            } else {
//                loginPrefsEditor.clear();
//                loginPrefsEditor.commit();
//            }
        if (view == buttonSignIn) {
            userLogin();

        }


    }


}


//}


