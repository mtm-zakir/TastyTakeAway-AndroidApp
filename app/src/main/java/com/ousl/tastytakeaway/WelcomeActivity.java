package com.ousl.tastytakeaway;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WelcomeActivity extends AppCompatActivity {
    private Button startButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        getSupportActionBar().hide();

        //Function Get Started Button
        startButton = findViewById(R.id.started_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override  public void onClick(View view) {openLogin(); }});
    }

    //Function Start Login Activity
    public void openLogin(){
        Intent intent=new Intent(this, LoginActivity.class);
        startActivity(intent);}

}