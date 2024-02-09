package com.ousl.tastytakeaway;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ousl.tastytakeaway.model.UserModel;

import java.util.ArrayList;

public class EditProfileActivity extends AppCompatActivity {

    String currentUserDetails;
    Button updateDetails;
    EditText userName,userPhone;
    TextView userEmail;
    DatabaseHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        getSupportActionBar().hide();

        //Parse Database Data
        userEmail = findViewById(R.id.userEmailText);
        userName = findViewById(R.id.userNameEditText);
        userPhone = findViewById(R.id.userPhoneEditText);

        //Receive Pushed Database Data
        currentUserDetails=getIntent().getStringExtra("current_user_data");
        DB = new DatabaseHelper(this);

        //Function Update Details Button
        updateDetails = findViewById(R.id.updateButton);
        updateDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usedEmail = userEmail.getText().toString();
                String usedName = userName.getText().toString();
                String usedPhoneNumber = userPhone.getText().toString();

                boolean updateNewDetails = DB.updateProfileDetails(usedEmail, usedName, usedPhoneNumber);
                if(updateNewDetails==true){
                    Toast.makeText(EditProfileActivity.this, "Update Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
                    intent.putExtra("current_user_data",currentUserDetails);  //User Details Push
                    startActivity(intent);
                }else {
                    Toast.makeText(EditProfileActivity.this, "Update Failed", Toast.LENGTH_LONG).show();
                }
            }
        });

        getUserDetails();
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
}