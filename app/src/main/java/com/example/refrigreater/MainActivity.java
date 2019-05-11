package com.example.refrigreater;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.design.widget.BottomNavigationView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends Activity {

    private ConstraintLayout constraintLayout;
    private ConstraintSet applyConstraintSet = new ConstraintSet();

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

        Foodlist list = (Foodlist)getApplicationContext();
        list.add("家", "玉米", 73, "預設", 5, false, "");
        list.add("家", "起司", 200, "預設", 1000, true, "");
        list.add("家", "頂級蒲燒鰻", 1, "預設", 0, true, "");
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            // Show home screen when pressing “back” button,
            //  so that this app won’t be closed accidentally
            setContentView(R.layout.activity_toolbar);
            BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
            navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
            Button seefoodBtn = (Button)findViewById(R.id.btnfridge);
            seefoodBtn.setOnClickListener(seefoodbtnlistener);
            return true;
        }

        return super.onKeyDown(keyCode, event);
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
            Button backBtn = (Button)findViewById(R.id.btnback);
            backBtn.setOnClickListener(backwardbtnlistener);
            Spinner spinner = (Spinner) findViewById(R.id.spinner);
            setCenterSpinner(spinner);
            generateBtnList();
        }
    };

    class Sortbyexpire implements Comparator<Foodlist.Food>
    {
        // Used for sorting in ascending order of
        // roll number
        public int compare(Foodlist.Food a, Foodlist.Food b)
        {
            return a.expire - b.expire;
        }
    }

    private void generateBtnList(){
        Foodlist btnContentList = (Foodlist)getApplicationContext();

        if( null == btnContentList ){
            return;
        }

        Collections.sort(btnContentList.foodlist, new Sortbyexpire());
        int count = 0;
        for(int index = 0; index < btnContentList.foodlist.size(); index++){
            Button codeBtn = new Button( this );
            setBtnAttribute( codeBtn,  btnContentList.foodlist.get(index), count );
            count++;
        }
    }

    private void setBtnAttribute( Button codeBtn, Foodlist.Food btnContentList, int index ){
        codeBtn.setId(View.generateViewId());
        if(btnContentList.expire<3){
            codeBtn.setBackground(getResources().getDrawable(R.drawable.backred, null));
        }
        else if(btnContentList.expire>5){
            codeBtn.setBackground(getResources().getDrawable(R.drawable.backgreen, null));
        }
        else {
            codeBtn.setBackground(getResources().getDrawable(R.drawable.backyellow, null));
        }
        String text = btnContentList.name;
        for(int i = 0; i < 5 - btnContentList.name.length(); i++){
            text += "　";
        }
        //text += String.format("%4d\t%6d\t%s", btnContentList.quantity, btnContentList.expire, "天");
        if(btnContentList.quantity>999) {
            text += btnContentList.quantity;
        }
        else if(btnContentList.quantity>99){
            text += " ";
            text += btnContentList.quantity;
        }
        else if(btnContentList.quantity>9){
            text += "  ";
            text += btnContentList.quantity;
        }
        else if(btnContentList.quantity>-1){
            text += "   ";
            text += btnContentList.quantity;
        }
        else if(btnContentList.quantity>-10){
            text += "  ";
            text += btnContentList.quantity;
        }
        else if(btnContentList.quantity>-100){
            text += " ";
            text += btnContentList.quantity;
        }
        else{
            text += btnContentList.quantity;
        }
        //text+="　";
        if(btnContentList.expire>9999) {
            text += btnContentList.expire;
        }
        else if(btnContentList.expire>999){
            text += " ";
            text += btnContentList.expire;
        }
        else if(btnContentList.expire>99){
            text += "  ";
            text += btnContentList.expire;
        }
        else if(btnContentList.expire>9){
            text += "   ";
            text += btnContentList.expire;
        }
        else if(btnContentList.expire > -1){
            text += "    ";
            text += btnContentList.expire;
        }
        else if(btnContentList.expire > -10){
            text += "   ";
            text += btnContentList.expire;
        }
        else if(btnContentList.expire>-100){
            text += "  ";
            text += btnContentList.expire;
        }
        else if(btnContentList.expire>-1000){
            text += " ";
            text += btnContentList.expire;
        }
        else{
            text += btnContentList.expire;
        }
        text += "天";
        codeBtn.setText(text);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/RobotoMono-Regular.ttf");
        codeBtn.setTypeface(font);
        codeBtn.setTag(btnContentList.name);
        codeBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 21);
        codeBtn.setGravity(Gravity.RIGHT|Gravity.CENTER_VERTICAL);
        codeBtn.setPadding(36, 0, 75, 0);
        if(!btnContentList.pri0pub1){
            codeBtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.privateicon, 0, 0, 0);
        }

        codeBtn.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                // btn click process
                setContentView(R.layout.activity_toolbar);
                BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
                navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
                Button seefoodBtn = (Button)findViewById(R.id.btnfridge);
                seefoodBtn.setOnClickListener(seefoodbtnlistener);
            }
        });

        //RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams( RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT );
        //rlp.addRule( RelativeLayout.ALIGN_PARENT_LEFT );
        constraintLayout = (android.support.constraint.ConstraintLayout) findViewById(R.id.container);
        constraintLayout.addView( codeBtn );
        applyConstraintSet.clone(constraintLayout);
        applyConstraintSet.connect(codeBtn.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP, 528+132*index);
        applyConstraintSet.connect(codeBtn.getId(), ConstraintSet.LEFT, constraintLayout.getId(), ConstraintSet.LEFT, 60);
        applyConstraintSet.constrainHeight(codeBtn.getId(), 132);
        applyConstraintSet.constrainWidth(codeBtn.getId(), ConstraintSet.WRAP_CONTENT);
        applyConstraintSet.applyTo(constraintLayout);
        //codeBtn.setLayoutParams( rlp );
    }

    private Button.OnClickListener backwardbtnlistener = new Button.OnClickListener(){
        @Override
        public void onClick(View v) {
            setContentView(R.layout.activity_toolbar);
            BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
            navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
            Button seefoodBtn = (Button)findViewById(R.id.btnfridge);
            seefoodBtn.setOnClickListener(seefoodbtnlistener);
        }
    };

    private void setCenterSpinner(Spinner spinner1){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.languages,R.layout.spinner_center_item);
        adapter.setDropDownViewResource(R.layout.spinner_down);
        spinner1.setAdapter(adapter);
        //spinner1.setOnItemSelectedListener(new MyOnItemSelectedListener());
    }

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