package com.example.refrigreater;

import android.app.Application;

import java.util.ArrayList;

public class Fridgelist extends Application {

    boolean shake = true;

    public void change_shake(){
        if(shake){
            shake = false;
        }
        else{
            shake = true;
        }
    }

    public ArrayList<Fridge> fridgelist = new ArrayList<>();
    public ArrayList<HashforFridge> fridgeid = new ArrayList<>();

    public void add_id(String id, int id_num){
        fridgeid.add(new HashforFridge(id, id_num));
    }

    public class HashforFridge{
        int id_num;
        String id;
        public HashforFridge(String id, int id_num){
            this.id_num = id_num;
            this.id = id;
        }
    }

    public void add_fridge(String name, String id, String user) {
        fridgelist.add(new Fridge(name, id, user));
    }

    public void delete_fridge(int index){
        fridgelist.remove(fridgelist.get(index));
    }

    public class Fridge {
        public ArrayList<Food> foodlist = new ArrayList<>();
        public ArrayList<Category> categorylist = new ArrayList<>();
        public ArrayList<IdMapCategory> Map = new ArrayList<>();
        public String name;
        public String user;
        public String id;

        public Fridge(String name, String id, String user) {
            this.name = name;
            this.id = id;
            this.user = user;
        }

        public void add(String name, String category, int expire, boolean pri0pub1, String ps, int year, int month, int day, boolean nother) {
            foodlist.add(new Food(name, category, expire, pri0pub1, ps, year, month, day, nother));
        }

        public void delete(int i) {
            foodlist.remove(foodlist.get(i));
        }

        public void change(int index, String name, String category, int expire, boolean pri0pub1, String ps, int year, int month, int day, boolean nother) {
            foodlist.get(index).name = name;
            foodlist.get(index).category = category;
            foodlist.get(index).expire = expire;
            foodlist.get(index).pri0pub1 = pri0pub1;
            foodlist.get(index).ps = ps;
            foodlist.get(index).year = year;
            foodlist.get(index).month = month;
            foodlist.get(index).day = day;
            foodlist.get(index).nother = nother;
        }

        public class Food {
            public String name;
            public String category;
            public int expire;
            public boolean pri0pub1;
            public String ps;
            public int year;
            public int month;
            public int day;
            public boolean nother;

            public Food(String name, String category, int expire, boolean pri0pub1, String ps, int year, int month, int day, boolean nother) {
                this.name = name;
                this.category = category;
                this.expire = expire;
                this.pri0pub1 = pri0pub1;
                this.ps = ps;
                this.year = year;
                this.month = month;
                this.day = day;
                this.nother = nother;
            }
        }

        public void addcategory(String category, int order, boolean onoff) {
            categorylist.add(new Category(category, order, onoff));
        }

        public void deletecategory(String category) {
            for (int i = 0; i < categorylist.size(); i++) {
                if (categorylist.get(i).category.matches(category)) {
                    categorylist.remove(categorylist.get(i));
                    break;
                }
            }
        }

        public void changedefaultcategory(String category) {
            for (int i = 0; i < categorylist.size(); i++) {
                if (categorylist.get(i).order == -1)
                    continue;
                if (categorylist.get(i).category.matches(category)) {
                    categorylist.get(i).order = 1;
                } else {
                    categorylist.get(i).order = 0;
                }
            }
        }

        public void changecategory(String category) {
            String defaultcategory = "";
            for (int i = 0; i < categorylist.size(); i++) {
                if (categorylist.get(i).order == 1) {
                    defaultcategory = categorylist.get(i).category;
                    break;
                }
            }
            for (int i = 0; i < foodlist.size(); i++) {
                if (foodlist.get(i).category.matches(category)) {
                    foodlist.get(i).category = defaultcategory;
                }
            }
        }

        public boolean existedcategory(String category) {
            for (int i = 0; i < categorylist.size(); i++) {
                if (categorylist.get(i).category.matches(category)) {
                    return true;
                }
            }
            return false;
        }

        public class Category {
            public String category;
            public int order;
            public boolean onoff;

            public Category(String category, int order, boolean onoff) {
                this.category = category;
                this.order = order;
                this.onoff = onoff;
            }
        }

        public void addMap(String category, int id) {
            Map.add(new IdMapCategory(category, id));
        }

        public void deleteMap(String category) {
            for (int i = 0; i < Map.size(); i++) {
                if (Map.get(i).category.matches(category)) {
                    Map.remove(Map.get(i));
                    break;
                }
            }
        }

        public class IdMapCategory {
            public int id;
            public String category;

            public IdMapCategory(String category, int id) {
                this.category = category;
                this.id = id;
            }
        }
    }
}


