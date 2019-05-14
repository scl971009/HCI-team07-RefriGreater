package com.example.refrigreater;

import android.app.Application;

import java.util.ArrayList;

public class Foodlist extends Application {

    public ArrayList<Food> foodlist = new ArrayList<>();

    public ArrayList<Category> categorylist = new ArrayList<>();

    public ArrayList<IdMapCategory> Map = new ArrayList<>();

    public void addcategory(String category, int order, boolean onoff){
        categorylist.add(new Category(category, order, onoff));
    }

    public void add(String fridge, String name, int quantity, String category, int expire, boolean pri0pub1, String ps){
        foodlist.add(new Food(fridge, name, quantity, category, expire, pri0pub1, ps));
    }

    public void delete(int i){
        foodlist.remove(foodlist.get(i));
    }

    public void deletecategory(String category){
        for(int i = 0; i < categorylist.size(); i++){
            if(categorylist.get(i).category.matches(category)){
                categorylist.remove(categorylist.get(i));
                break;
            }
        }
    }

    public void changedefaultcategory(String category){
        for(int i = 0; i < categorylist.size(); i++){
            if(categorylist.get(i).order == -1)
                continue;
            if(categorylist.get(i).category.matches(category)){
                categorylist.get(i).order = 1;
            }
            else{
                categorylist.get(i).order = 0;
            }
        }
    }

    public void changecategory(String category){
        String defaultcategory = "";
        for(int i = 0; i < categorylist.size(); i++){
            if(categorylist.get(i).order == 1){
                defaultcategory = categorylist.get(i).category;
                break;
            }
        }
        for(int i = 0; i < foodlist.size(); i++){
            if(foodlist.get(i).category.matches(category)){
                foodlist.get(i).category = defaultcategory;
            }
        }
    }

    public boolean existedcategory(String category){
        for(int i = 0; i < categorylist.size(); i++){
            if(categorylist.get(i).category.matches(category)){
                return true;
            }
        }
        return false;
    }

    public void addMap(String category, int id){
        Map.add(new IdMapCategory(category, id));
    }

    public void deleteMap(String category){
        for(int i = 0; i < Map.size(); i++){
            if(Map.get(i).category.matches(category)){
                Map.remove(Map.get(i));
                break;
            }
        }
    }

    public class IdMapCategory{
        public int id;
        public String category;
        public IdMapCategory(String category, int id){
            this.category = category;
            this.id = id;
        }
    }

    public class Category{
        public String category;
        public int order;
        public boolean onoff;
        public Category(String category, int order, boolean onoff){
            this.category = category;
            this.order = order;
            this.onoff = onoff;
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
