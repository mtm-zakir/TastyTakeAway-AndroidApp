package com.ousl.tastytakeaway.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ousl.tastytakeaway.R;
import com.ousl.tastytakeaway.model.FoodModel;
import com.bumptech.glide.Glide;

import java.util.List;

public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.MyViewHolder> {

    private List<FoodModel> foodModelList;
    private FoodListClickListener clickListener;

    public FoodListAdapter(List<FoodModel> foodModelList, FoodListClickListener clickListener) {
        this.foodModelList = foodModelList;
        this.clickListener = clickListener;
    }

    public void updateData(List<FoodModel> foodModelList) {
        this.foodModelList = foodModelList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FoodListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row_food_list, parent, false);
        return  new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodListAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.foodName.setText(foodModelList.get(position).getName());
        holder.foodDelivery.setText("Delivery "+ foodModelList.get(position).getDelivery());
        holder.foodItems.setText("" + foodModelList.get(position).getItems());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(foodModelList.get(position));
            }
        });
        Glide.with(holder.thumbImage)
                .load(foodModelList.get(position).getImage())
                .into(holder.thumbImage);

    }

    @Override
    public int getItemCount() {
        return foodModelList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView  foodName;
        TextView  foodDelivery;
        TextView  foodItems;
        ImageView thumbImage;

        public MyViewHolder(View view) {
            super(view);
            foodName = view.findViewById(R.id.foodName);
            foodDelivery = view.findViewById(R.id.foodDelivery);
            foodItems = view.findViewById(R.id.foodItems);
            thumbImage = view.findViewById(R.id.thumbImage);

        }
    }

    public interface FoodListClickListener {
        public void onItemClick(FoodModel foodModel);
    }
}
