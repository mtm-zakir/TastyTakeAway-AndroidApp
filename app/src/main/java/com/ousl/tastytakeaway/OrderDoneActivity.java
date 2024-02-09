package com.ousl.tastytakeaway;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ousl.tastytakeaway.model.FoodModel;

public class OrderDoneActivity extends AppCompatActivity {

    TextView buttonDone;
    String currentUserDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_done);
        getSupportActionBar().hide();

        //Receive Pushed Database Data
        currentUserDetails=getIntent().getStringExtra("current_user_data");

        //Parse JSON Data
        FoodModel foodModel = getIntent().getParcelableExtra("RestaurantModel");

        //Function Done Button
        buttonDone = findViewById(R.id.buttonDone);
        buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMain();
            }
        });
    }

    //Function Start Main Activity
    public void openMain() {
        Intent intent = new Intent(this, DashboardActivity.class);
        intent.putExtra("current_user_data",currentUserDetails);  //User Details Push
        startActivity(intent);
    }
}