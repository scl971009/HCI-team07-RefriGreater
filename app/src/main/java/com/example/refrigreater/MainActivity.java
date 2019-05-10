package com.example.refrigreater;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar);

        TextView text_id = (TextView) findViewById(R.id.textid);
        ToggleButton togglebutton = (ToggleButton) findViewById(R.id.toggle_button);

        togglebutton.setChecked(false);

        Button seefoodBtn = (Button)findViewById(R.id.btnfridge);
        seefoodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this  , SeeFood.class);
                startActivity(intent);
            }
        });
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
}