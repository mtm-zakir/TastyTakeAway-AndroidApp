package com.ousl.tastytakeaway;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.ousl.tastytakeaway.adapters.FoodListAdapter;
import com.ousl.tastytakeaway.model.FoodModel;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;

public class DashboardActivity extends AppCompatActivity implements FoodListAdapter.FoodListClickListener {

    String currentUserDetails;
    ImageButton notification, profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getSupportActionBar().hide();

        //Receive Pushed Database Data
        currentUserDetails=getIntent().getStringExtra("current_user_data");

        //Parse JSON Data
        List<FoodModel> foodModelList =  getRestaurantData();
        initRecyclerView(foodModelList);

        //Function Notification Button
        notification =  findViewById(R.id.notificationButton);
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNotifications();
            }
        });

        //Function Profile Button
        profile =  findViewById(R.id.profileButton);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProfile();
            }
        });
    }

    private void initRecyclerView(List<FoodModel> foodModelList) {
        RecyclerView recyclerView =  findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FoodListAdapter adapter = new FoodListAdapter(foodModelList, this);
        recyclerView.setAdapter(adapter);
    }

    private List<FoodModel> getRestaurantData() {
        InputStream is = getResources().openRawResource(R.raw.food);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try{
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while(( n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0,n);
            }
        }catch (Exception e) {

        }

        String jsonStr = writer.toString();
        Gson gson = new Gson();
        FoodModel[] foodModels =  gson.fromJson(jsonStr, FoodModel[].class);
        List<FoodModel> restList = Arrays.asList(foodModels);

        return  restList;

    }

    //Function Start Restaurant Menu Activity
    @Override
    public void onItemClick(FoodModel foodModel) {
        Intent intent = new Intent(DashboardActivity.this, FoodMenuActivity.class);
        intent.putExtra("RestaurantModel", foodModel); //Restaurant Model
        intent.putExtra("current_user_data",currentUserDetails); //User Details Push
        startActivity(intent);

    }


    //Function Start Notification Activity
    private void openNotifications(){
        Intent intent = new Intent(this, NotificationsActivity.class);
        startActivity(intent);
    }

    //Function Start Profile Activity
    private void openProfile(){
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra("current_user_data",currentUserDetails); //User Details Push
        startActivity(intent);
    }

    //Function Back Press Exit Dialog
    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DashboardActivity.this);
        alertDialog.setTitle("Confirm Logout");
        alertDialog.setMessage(" \"Are you sure you want to Logout?\"");
        alertDialog.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(DashboardActivity.this, WelcomeActivity.class);
                startActivity(intent);
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