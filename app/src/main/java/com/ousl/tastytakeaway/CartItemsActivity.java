package com.ousl.tastytakeaway;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.ousl.tastytakeaway.adapters.CartListAdapter;
import com.ousl.tastytakeaway.model.Menu;
import com.ousl.tastytakeaway.model.FoodModel;

public class CartItemsActivity extends AppCompatActivity {
    RecyclerView cartItemsRecyclerView;
    TextView SubtotalAmount, DeliveryChargeAmount, DeliveryCharge, TotalAmount, checkoutBtn;
    CartListAdapter cartListAdapter;
    String currentUserDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_items);
        getSupportActionBar().hide();

        //Receive Pushed Database Data
        currentUserDetails=getIntent().getStringExtra("current_user_data");

        //Parse JSON Data
        FoodModel foodModel = getIntent().getParcelableExtra("RestaurantModel");

        SubtotalAmount = findViewById(R.id.tvSubtotalAmount);
        DeliveryChargeAmount = findViewById(R.id.tvDeliveryChargeAmount);
        DeliveryCharge = findViewById(R.id.tvDeliveryCharge);
        TotalAmount = findViewById(R.id.tvTotalAmount);

        cartItemsRecyclerView = findViewById(R.id.cartItemsRecyclerView);

        //Function Place Order Button
        checkoutBtn = findViewById(R.id.btnCheckout);
        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPlaceOrderButtonClick(foodModel);
            }
        });

        initRecyclerView(foodModel);
        calculateTotalAmount(foodModel);
    }

    //Function Calculate Amount
    private void calculateTotalAmount(FoodModel foodModel) {
        float subTotalAmount = 0f;

        for(Menu m : foodModel.getMenus()) {
            subTotalAmount += m.getPrice() * m.getTotalInCart();
        }

        SubtotalAmount.setText("LKR. "+String.format("%.2f", subTotalAmount));
        DeliveryChargeAmount.setText("LKR. "+String.format("%.2f", foodModel.getDelivery_charge()));
        subTotalAmount += foodModel.getDelivery_charge();
        TotalAmount.setText("LKR. "+String.format("%.2f", subTotalAmount));
    }

    //Function Place Order Click
    private void onPlaceOrderButtonClick(FoodModel foodModel) {
        //start Success Activity..
        Intent intent = new Intent(CartItemsActivity.this, DeliveryDetailsActivity.class);
        intent.putExtra("RestaurantModel", foodModel);
        intent.putExtra("current_user_data",currentUserDetails); //User Details Push
        startActivityForResult(intent, 1000);
    }

    private void initRecyclerView(FoodModel foodModel) {
        cartItemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartListAdapter = new CartListAdapter(foodModel.getMenus());
        cartItemsRecyclerView.setAdapter(cartListAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 1000) {
            setResult(Activity.RESULT_OK);
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home :
                finish();
            default:
                //do nothing
        }
        return super.onOptionsItemSelected(item);
    }

    //Function Back Press
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
}