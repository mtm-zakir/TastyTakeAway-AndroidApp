package com.ousl.tastytakeaway;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.ousl.tastytakeaway.adapters.MenuListAdapter;
import com.ousl.tastytakeaway.model.Menu;
import com.ousl.tastytakeaway.model.FoodModel;

import java.util.ArrayList;
import java.util.List;

public class FoodMenuActivity extends AppCompatActivity implements MenuListAdapter.MenuListClickListener {
    List<Menu> menuList = null;
    MenuListAdapter menuListAdapter;
    List<Menu> itemsInCartList;
    int totalItemInCart = 0;
    TextView buttonCheckout;
    String currentUserDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_menu);
//      getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.purple_500)));

        //Receive Pushed Database Data
        currentUserDetails=getIntent().getStringExtra("current_user_data");

        //Parse JSON Data
        FoodModel foodModel = getIntent().getParcelableExtra("RestaurantModel");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(foodModel.getName() + " Menu");
        actionBar.setSubtitle("Delivery "+ foodModel.getDelivery());
        actionBar.setDisplayHomeAsUpEnabled(true);


        menuList = foodModel.getMenus();
        initRecyclerView();


        //Function Checkout Button
        buttonCheckout = findViewById(R.id.buttonCheckout);
        buttonCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foodModel.setMenus(itemsInCartList);
                Intent intent = new Intent(FoodMenuActivity.this, CartItemsActivity.class);
                intent.putExtra("RestaurantModel", foodModel);
                intent.putExtra("current_user_data",currentUserDetails);  //UserDetails Push
                startActivityForResult(intent, 1000);
            }
        });
    }

    private void initRecyclerView() {
        RecyclerView recyclerView =  findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        menuListAdapter = new MenuListAdapter(menuList, this);
        recyclerView.setAdapter(menuListAdapter);
    }


    //Function for + Button
    @Override
    public void onAddToCartClick(Menu menu) {
        if(itemsInCartList == null) {
            itemsInCartList = new ArrayList<>();
        }
        itemsInCartList.add(menu);
        totalItemInCart = 0;

        for(Menu m : itemsInCartList) {
            totalItemInCart = totalItemInCart + m.getTotalInCart();
        }
        buttonCheckout.setText("Checkout (" +totalItemInCart +") items");
    }

    //Function for Button Update
    @Override
    public void onUpdateCartClick(Menu menu) {
        if(itemsInCartList.contains(menu)) {
            int index = itemsInCartList.indexOf(menu);
            itemsInCartList.remove(index);
            itemsInCartList.add(index, menu);

            totalItemInCart = 0;

            for(Menu m : itemsInCartList) {
                totalItemInCart = totalItemInCart + m.getTotalInCart();
            }
            buttonCheckout.setText("Checkout (" +totalItemInCart +") items");
        }
    }

    //Function for - Button
    @Override
    public void onRemoveFromCartClick(Menu menu) {
        if(itemsInCartList.contains(menu)) {
            itemsInCartList.remove(menu);
            totalItemInCart = 0;

            for(Menu m : itemsInCartList) {
                totalItemInCart = totalItemInCart + m.getTotalInCart();
            }
            buttonCheckout.setText("Checkout (" +totalItemInCart +") items");
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home :
                finish();
            default:
                //Do Nothing
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1000 && resultCode == Activity.RESULT_OK) {
            finish();
        }
    }
}