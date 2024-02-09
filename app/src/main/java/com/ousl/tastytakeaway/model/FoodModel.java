package com.ousl.tastytakeaway.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class FoodModel implements Parcelable {

    private String name;
    private String delivery;
    private String items;
    private String image;
    private float delivery_charge;
    private List<Menu> menus;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getDelivery_charge() {
        return delivery_charge;
    }

    public void setDelivery_charge(float delivery_charge) {
        this.delivery_charge = delivery_charge;
    }


    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

    protected FoodModel(Parcel in) {
        name = in.readString();
        delivery = in.readString();
        items = in.readString();
        image = in.readString();
        delivery_charge = in.readFloat();
        menus = in.createTypedArrayList(Menu.CREATOR);
    }

    public static final Creator<FoodModel> CREATOR = new Creator<FoodModel>() {
        @Override
        public FoodModel createFromParcel(Parcel in) {
            return new FoodModel(in);
        }

        @Override
        public FoodModel[] newArray(int size) {
            return new FoodModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(delivery);
        dest.writeString(items);
        dest.writeString(image);
        dest.writeFloat(delivery_charge);
        dest.writeTypedList(menus);
    }
}
