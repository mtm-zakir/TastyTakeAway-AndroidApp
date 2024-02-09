package com.ousl.tastytakeaway;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ousl.tastytakeaway.model.UserModel;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {
    Button logoutButton;
    TextView editProfile;
    TextView userName,userEmail,userPhone;
    String currentUserDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().hide();

        //Parse Database Data
        userName = findViewById(R.id.userNameText);
        userEmail = findViewById(R.id.userEmailText);
        userPhone = findViewById(R.id.userPhoneText);

        //Receive Pushed Database Data
        currentUserDetails=getIntent().getStringExtra("current_user_data");

        editProfile = findViewById(R.id.editProfileButton);
        logoutButton = findViewById(R.id.logoutButton);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLogout();
            }
        });

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEditProfile();
            }
        });

        getUserDetails();

    }
    //Function Start Login Activity
    public void openLogout() {
        Toast.makeText(ProfileActivity.this, "Logout Successful", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    //Function Start Edit Profile Activity
    public void openEditProfile() {
        Intent intent = new Intent(this, EditProfileActivity.class);
        intent.putExtra("current_user_data",currentUserDetails); //User Details Push
        startActivity(intent);
    }


    //Function Get Current User Details
    public void getUserDetails(){
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        ArrayList<UserModel> aList = databaseHelper.getCurrentUserDetails(currentUserDetails);
        UserModel userModel = aList.get(0);
        userName.setText(userModel.getUsed_name());
        userEmail.setText(userModel.getUsed_email());
        userPhone.setText(userModel.getUsed_phone());
    }

    //Function Back Press
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, DashboardActivity.class);
        intent.putExtra("current_user_data",currentUserDetails);  //UserDetails Push
        startActivity(intent);
    }
}