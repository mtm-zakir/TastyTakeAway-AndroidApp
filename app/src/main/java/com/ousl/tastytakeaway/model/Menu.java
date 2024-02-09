package com.ousl.tastytakeaway.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Menu implements Parcelable {
    private String name;
    private String rating;
    private float price;
    private int totalInCart;
    private String url;

    public int getTotalInCart() {
        return totalInCart;
    }

    public void setTotalInCart(int totalInCart) {
        this.totalInCart = totalInCart;
    }

    protected Menu(Parcel in) {
        name = in.readString();
        rating = in.readString();
        price = in.readFloat();
        url = in.readString();
        totalInCart = in.readInt();
    }

    public static final Creator<Menu> CREATOR = new Creator<Menu>() {
        @Override
        public Menu createFromParcel(Parcel in) {
            return new Menu(in);
        }

        @Override
        public Menu[] newArray(int size) {
            return new Menu[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(rating);
        dest.writeFloat(price);
        dest.writeString(url);
        dest.writeInt(totalInCart);
    }
}
