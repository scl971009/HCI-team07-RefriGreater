package com.example.refrigreater;

import android.app.Application;

import java.util.ArrayList;

public class Foodlist extends Application {

    public ArrayList<Food> foodlist = new ArrayList<>();

    public ArrayList<Category> categorylist = new ArrayList<>();

    public void addcategory(String category, int order){
        categorylist.add(new Category(category, order));
    }

    public void add(String fridge, String name, int quantity, String category, int expire, boolean pri0pub1, String ps){
        foodlist.add(new Food(fridge, name, quantity, category, expire, pri0pub1, ps));
    }

    public class Category{
        public String category;
        public int order;
        public Category(String category, int order){
            this.category = category;
            this.order = order;
        }
    }

    public class Food {
        public String name;
        public String category;
        public int quantity;
        public int expire;
        public boolean pri0pub1;
        public String fridge;
        public String ps;

        public Food(String fridge, String name, int quantity, String category, int expire, boolean pri0pub1, String ps) {
            this.fridge = fridge;
            this.name = name;
            this.quantity = quantity;
            this.category = category;
            this.expire = expire;
            this.pri0pub1 = pri0pub1;
            this.ps = ps;
        }
    }
}
