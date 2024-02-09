package com.ousl.tastytakeaway;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ousl.tastytakeaway.model.UserModel;

import java.util.ArrayList;


public class DeliveryDetailsActivity extends AppCompatActivity {
    TextView placeOrder,userId;
    EditText address;
    RadioGroup radioGroup;
    RadioButton radioButton;
    String currentUserDetails;
    DatabaseHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_details);
        getSupportActionBar().hide();

        radioGroup=findViewById(R.id.radioGroup);

        //Parse Database Data
        userId = findViewById(R.id.userIdText);
        address = findViewById(R.id.addressEditText);

        //Receive Pushed Database Data
        currentUserDetails=getIntent().getStringExtra("current_user_data");
        DB = new DatabaseHelper(this);

        //Function Place Order
        placeOrder =findViewById(R.id.placeOrderButton);
        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usedId = userId.getText().toString();
                String usedAddress = address.getText().toString();
                String orderMethod = radioButton.getText().toString();
                    Boolean addDeliveryDetail = DB.addDeliveryDetails(usedId, usedAddress, orderMethod);
                    if (addDeliveryDetail) {
                        Toast.makeText(DeliveryDetailsActivity.this, "Order Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(DeliveryDetailsActivity.this, OrderDoneActivity.class);
                        intent.putExtra("current_user_data", currentUserDetails);
                        startActivity(intent);
                    } else {
                        Toast.makeText(DeliveryDetailsActivity.this, "Order Failed", Toast.LENGTH_LONG).show();
                    }
                }
        });

        getUserDetails();

        Fragment fragment=new MapFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.mapLayout,fragment).commit();


    }

    //Function Payment Toast
    public void payment(View v) {
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
    }

    //Function Get Current User Details
    public void getUserDetails(){
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        ArrayList<UserModel> aList = databaseHelper.getCurrentUserDetails(currentUserDetails);
        UserModel userModel = aList.get(0);
        userId.setText(userModel.getUsed_id());
    }
}