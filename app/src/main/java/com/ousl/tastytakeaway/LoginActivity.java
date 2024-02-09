package com.ousl.tastytakeaway;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    Button loginButton;
    EditText email, password;
    TextView createAcc;
    DatabaseHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        createAcc = findViewById(R.id.createAccText);
        loginButton = findViewById(R.id.loginButton);
        email = (EditText) findViewById(R.id.emailEditText);
        password = (EditText) findViewById(R.id.passwordEditText);

        DB = new DatabaseHelper(this);

        createAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSignup();
            }
        });

        //Function Login Button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usedEmail = email.getText().toString();
                String usedPassword = password.getText().toString();

                if (usedEmail.equals("") || usedPassword.equals(""))
                    Toast.makeText(LoginActivity.this, "Enter All Fields", Toast.LENGTH_SHORT).show();
                else {
                    Boolean checkEmailPasswords = DB.checkEmailPassword(usedEmail, usedPassword);
                    if (checkEmailPasswords == true) {
                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                        intent.putExtra("current_user_data",usedEmail);  //User Details Push
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    //Function Start Register Activity
    public void openSignup() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    //Function Back Press Exit Dialog
    @Override
    public void onBackPressed() {
        androidx.appcompat.app.AlertDialog.Builder alertDialog = new AlertDialog.Builder(LoginActivity.this);
        alertDialog.setTitle("Confirm Exit");
        alertDialog.setMessage(" \"Are you sure you want to Exit?\"");
        alertDialog.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
            }
        });

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }
}