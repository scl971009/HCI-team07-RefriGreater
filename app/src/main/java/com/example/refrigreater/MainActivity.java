package com.example.refrigreater;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        ToggleButton togglebutton = (ToggleButton) findViewById(R.id.toggle_button);

        togglebutton.setChecked(false);

        Button seefoodBtn = (Button)findViewById(R.id.btnfridge);
        seefoodBtn.setOnClickListener(seefoodbtnlistener);
    }



    public void onTargetClick(View view){
        TextView text_id = (TextView) findViewById(R.id.textid);
        ToggleButton togglebutton = (ToggleButton) findViewById(R.id.toggle_button);
        ToggleButton toggleButton = (ToggleButton) view;
        if(togglebutton.isChecked()){
            text_id.setVisibility(View.VISIBLE);
        }
        else{
            text_id.setVisibility(View.GONE);
        }
    }

    private Button.OnClickListener seefoodbtnlistener = new Button.OnClickListener(){
        @Override
        public void onClick(View v) {
            setContentView(R.layout.activity_see_food);
            BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
            navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        }
    };

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_fridge:
                    setContentView(R.layout.activity_toolbar);
                    BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
                    navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
                    Button seefoodBtn = (Button)findViewById(R.id.btnfridge);
                    seefoodBtn.setOnClickListener(seefoodbtnlistener);
                    return true;
                case R.id.navigation_recipe:
                    setContentView(R.layout.fridge);
                    BottomNavigationView navigation1 = (BottomNavigationView) findViewById(R.id.navigation);
                    navigation1.setSelectedItemId(R.id.navigation_recipe);
                    navigation1.setOnNavigationItemSelectedListener(this);
                    return true;
                case R.id.navigation_setting:
                    setContentView(R.layout.fridge);
                    BottomNavigationView navigation2 = (BottomNavigationView) findViewById(R.id.navigation);
                    navigation2.setSelectedItemId(R.id.navigation_setting);
                    navigation2.setOnNavigationItemSelectedListener(this);
                    return true;
            }
            return false;
        }
    };
}