package com.ousl.tastytakeaway;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.VideoView;
public class RegisterActivity extends AppCompatActivity {
    Button registerButton;
    EditText userName, email , phoneNumber, password, rePassword ;
    DatabaseHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        userName = (EditText) findViewById(R.id.userNameEditText);
        email = (EditText) findViewById(R.id.emailEditText);
        phoneNumber = (EditText) findViewById(R.id.phoneNumberEditText);
        password = (EditText) findViewById(R.id.passwordEditText);
        rePassword = (EditText) findViewById(R.id.rePasswordEditText);
        DB = new DatabaseHelper(this);

        VideoView videoView = findViewById(R.id.videoView);
        videoView.setVideoPath("android.resource://" + getPackageName()
                + "/" + R.raw.video);
        videoView.start();

        //Function Register Button
        registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usedName = userName.getText().toString();
                String usedEmail = email.getText().toString();
                String usedPhoneNumber = phoneNumber.getText().toString();
                String usedPassword = password.getText().toString();
                String usedRePassword = rePassword.getText().toString();

                //Function Validate Inputs
                boolean checkInput=validateInputs(usedName,usedEmail,usedPhoneNumber,usedPassword);

                if (checkInput == true){
                    if (usedName.equals("") || usedEmail.equals("") || usedPhoneNumber.equals("") || usedPassword.equals("") || usedRePassword.equals(""))
                        Toast.makeText(RegisterActivity.this, "Enter All Fields", Toast.LENGTH_SHORT).show();
                    else {
                        if (usedPassword.equals(usedRePassword)) {
                            Boolean checkEmails = DB.checkEmail(usedEmail);
                            if (checkEmails == false) {
                                Boolean createUser = DB.createNewUser(usedName, usedEmail, usedPhoneNumber, usedPassword);
                                if (createUser == true) {
                                    Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(RegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                                }
                            } else
                                Toast.makeText(RegisterActivity.this, "User Already Exist. Try Different Email", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RegisterActivity.this, "Password Not Matching", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }

    //Function for Validate Inputs
    private Boolean validateInputs(String usedName, String usedEmail, String usedPhoneNumber, String usedPassword){
        if(usedName.length()==0) {
            userName.requestFocus();
            userName.setError("FIELD CANNOT BE EMPTY");
            return false;
        }
        else if (!usedName.matches("[a-zA-Z]+")) {
            userName.requestFocus();
            userName.setError("ENTER ALPHABETIC LETTERS ONLY");
            return false;
        }
        else if(usedEmail.length()==0) {
            email.requestFocus();
            email.setError("FIELD CANNOT BE EMPTY");
            return false;
        }
        else if (!usedEmail.matches("[a-zA-Z0-9]+@[a-z]+\\.+[a-z]+")) {
            email.requestFocus();
            email.setError("INVALID EMAIL");
            return false;
        }
        else if(usedPhoneNumber.length()==0) {
            phoneNumber.requestFocus();
            phoneNumber.setError("FIELD CANNOT BE EMPTY");
            return false;
        }
        else if (!usedPhoneNumber.matches("0[0-9]{9}")) {
            phoneNumber.requestFocus();
            phoneNumber.setError("INVALID NUMBER (07XXXXXXXX)");
            return false;
        }
        else if(usedPassword.length()==0) {
            password.requestFocus();
            password.setError("FIELD CANNOT BE EMPTY");
            return false;
        }
        else if (!usedPassword.matches("^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=_\\-!¡?¿.,;:<>])(?=\\S+$).{6,}$")) {
            password.requestFocus();
            password.setError("USE SPECIAL CHARACTERS AND NUMBERS");
            return false;
        }
        else if(usedPassword.length()<=5) {
            password.requestFocus();
            password.setError("MINIMUM 6 CHARACTERS REQUIRED");
            return false;
        }
        else {
            return true;
        }
    }
}