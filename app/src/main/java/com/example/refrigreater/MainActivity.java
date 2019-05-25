package com.example.refrigreater;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.graphics.Typeface;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
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
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.text.ParseException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import static java.lang.String.format;

public class MainActivity extends Activity {

    private ConstraintLayout constraintLayout;
    private ConstraintSet applyConstraintSet = new ConstraintSet();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fridgelist list = (Fridgelist) getApplicationContext();
        list.add_fridge("家", "1234567", "宇智波佐助");
        list.add_id(list.fridgelist.get(0).id, View.generateViewId());
        list.fridgelist.get(0).add( "玉米", 73, "預設", 5, false, "", 2019, 6, 27);
        list.fridgelist.get(0).add("起司", 200, "預設", 1000, true, "", 2019, 11, 1);
        list.fridgelist.get(0).add( "頂級蒲燒鰻", 1, "預設", 0, true, "", 2019, 4, 15);
        list.fridgelist.get(0).addcategory("預設", 1, true);
        list.fridgelist.get(0).addcategory("更改預設", -1, true);
        list.fridgelist.get(0).addcategory("新增類別", -1, true);
        list.fridgelist.get(0).addcategory("刪除類別", -1, true);
        list.fridgelist.get(0).addMap("全選", View.generateViewId());
        list.fridgelist.get(0).addMap("預設", View.generateViewId());
        call_main();
    }

    public void call_main(){
        Fridgelist list = (Fridgelist) getApplicationContext();
        setContentView(R.layout.activity_toolbar);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        switch(list.fridgelist.size()){
            case 0:
                break;
            case 1:
                generateBtn1();
                generateTxt1();
                break;
            case 2:
                generateBtn2();
                generateTxt2();
        }
    }

    private void generateBtn1(){
        Fridgelist list = (Fridgelist)getApplicationContext();
        Button codeBtn = new Button(this);
        codeBtn.setId(View.generateViewId());
        codeBtn.setText(list.fridgelist.get(0).name);
        codeBtn.setBackground(getResources().getDrawable(R.drawable.fridgebotton, null));
        codeBtn.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL);
        codeBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 40);
        codeBtn.setPadding(0, 14, 0, 0);

        codeBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button)v;
                String buttonText = b.getText().toString();
                call_seefood(buttonText);
            }
        });

        constraintLayout = (android.support.constraint.ConstraintLayout) findViewById(R.id.container);
        constraintLayout.addView(codeBtn);
        applyConstraintSet.clone(constraintLayout);
        applyConstraintSet.connect(codeBtn.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP, 750);
        applyConstraintSet.connect(codeBtn.getId(), ConstraintSet.LEFT, constraintLayout.getId(), ConstraintSet.LEFT, 129);
        applyConstraintSet.constrainHeight(codeBtn.getId(), 192);
        applyConstraintSet.constrainWidth(codeBtn.getId(), 825);
        applyConstraintSet.applyTo(constraintLayout);
    }

    public void onTargetClick(View view) {
        Fridgelist list = (Fridgelist)getApplicationContext();
        switch(list.fridgelist.size()){
            case 0:
                break;
            case 1:
                TextView text_id = (TextView) findViewById(list.fridgeid.get(0).id_num);
                ToggleButton togglebutton = (ToggleButton) findViewById(R.id.toggle_button);
                //ToggleButton toggleButton = (ToggleButton) view;
                if (togglebutton.isChecked()) {
                    text_id.setVisibility(View.VISIBLE);
                } else {
                    text_id.setVisibility(View.GONE);
                }
                break;
            case 2:
                TextView text_id_1 = (TextView)findViewById(list.fridgeid.get(0).id_num);
                TextView text_id_2 = (TextView)findViewById(list.fridgeid.get(1).id_num);
                ToggleButton toggle2 = (ToggleButton)findViewById(R.id.toggle_button);
                if(toggle2.isChecked()){
                    text_id_1.setVisibility(View.VISIBLE);
                    text_id_2.setVisibility(View.VISIBLE);
                }
                else{
                    text_id_1.setVisibility(View.GONE);
                    text_id_2.setVisibility(View.GONE);
                }
        }
    }

    private void generateTxt1(){
        //toggle 要找得到?
        Fridgelist list = (Fridgelist)getApplicationContext();
        TextView id = new TextView(MainActivity.this);
        id.setId(list.fridgeid.get(0).id_num);
        id.setText("ID: "+list.fridgelist.get(0).id);
        id.setGravity(Gravity.CENTER);
        id.setVisibility(View.GONE);
        id.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);

        constraintLayout = (android.support.constraint.ConstraintLayout) findViewById(R.id.container);
        constraintLayout.addView(id);
        applyConstraintSet.clone(constraintLayout);
        applyConstraintSet.connect(id.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP, Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 313, getResources().getDisplayMetrics())));
        applyConstraintSet.connect(id.getId(), ConstraintSet.LEFT, constraintLayout.getId(), ConstraintSet.LEFT, Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 123, getResources().getDisplayMetrics())));
        applyConstraintSet.constrainHeight(id.getId(), ConstraintSet.WRAP_CONTENT);
        applyConstraintSet.constrainWidth(id.getId(), ConstraintSet.WRAP_CONTENT);
        applyConstraintSet.applyTo(constraintLayout);
    }

    private void generateBtn2(){
        Fridgelist list = (Fridgelist)getApplicationContext();
        for(int i = 0; i < 2; i++){
            Button codeBtn = new Button(this);
            codeBtn.setId(View.generateViewId());
            codeBtn.setText(list.fridgelist.get(i).name);
            codeBtn.setBackground(getResources().getDrawable(R.drawable.fridgebotton, null));
            codeBtn.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL);
            codeBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 40);
            codeBtn.setPadding(0, 14, 0, 0);

            codeBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button b = (Button)v;
                    String buttonText = b.getText().toString();
                    call_seefood(buttonText);
                }
            });

            constraintLayout = (android.support.constraint.ConstraintLayout) findViewById(R.id.container);
            constraintLayout.addView(codeBtn);
            applyConstraintSet.clone(constraintLayout);
            applyConstraintSet.connect(codeBtn.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP, 570+i*333);
            applyConstraintSet.connect(codeBtn.getId(), ConstraintSet.LEFT, constraintLayout.getId(), ConstraintSet.LEFT, 129);
            applyConstraintSet.constrainHeight(codeBtn.getId(), 192);
            applyConstraintSet.constrainWidth(codeBtn.getId(), 825);
            applyConstraintSet.applyTo(constraintLayout);
        }
    }

    private void generateTxt2(){
        Fridgelist list = (Fridgelist)getApplicationContext();
        for(int i = 0; i < 2; i++){
            TextView id = new TextView(MainActivity.this);
            id.setId(list.fridgeid.get(i).id_num);
            id.setText("ID: "+list.fridgelist.get(i).id);
            id.setGravity(Gravity.CENTER);
            id.setVisibility(View.GONE);
            id.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);

            constraintLayout = (android.support.constraint.ConstraintLayout) findViewById(R.id.container);
            constraintLayout.addView(id);
            applyConstraintSet.clone(constraintLayout);
            applyConstraintSet.connect(id.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP, 762+i*333);
            applyConstraintSet.connect(id.getId(), ConstraintSet.LEFT, constraintLayout.getId(), ConstraintSet.LEFT, Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 123, getResources().getDisplayMetrics())));
            applyConstraintSet.constrainHeight(id.getId(), ConstraintSet.WRAP_CONTENT);
            applyConstraintSet.constrainWidth(id.getId(), ConstraintSet.WRAP_CONTENT);
            applyConstraintSet.applyTo(constraintLayout);
        }
    }

    public void call_seefood(String fridge){
        Fridgelist list = (Fridgelist) getApplicationContext();
        setContentView(R.layout.activity_see_food);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Button backBtn = (Button) findViewById(R.id.btnback);
        backBtn.setOnClickListener(backwardbtnlistener);
        Button plusBtn = (Button) findViewById(R.id.btnplus);
        plusBtn.setOnClickListener(plusfoodbtnlistener);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        setCenterSpinner(spinner);
        addsetCenterSpinnerleftup(spinner, fridge);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(MainActivity.this, "您選擇"+parent.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
                LinearLayout ll = (LinearLayout) findViewById(R.id.mylinear);
                ll.removeAllViews();
                generateBtnList(parent.getSelectedItem().toString());
                LinearLayout linear = (LinearLayout) findViewById(R.id.filter);
                linear.removeAllViews();
                generateCheckboxList(parent.getSelectedItem().toString());
                //call_seefood(parent.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Integer index = 0;
        for(int i = 0; i < list.fridgelist.size(); i++){
            if(list.fridgelist.get(i).name.matches(fridge)) {
                index = i;
                break;
            }
        }
        for(int i = 0; i < list.fridgelist.get(index).categorylist.size(); i++)
            list.fridgelist.get(index).categorylist.get(i).onoff = true;
        generateBtnList(spinner.getSelectedItem().toString());
        generateCheckboxList(spinner.getSelectedItem().toString());
        plusBtn.bringToFront();
    }

    private void addsetCenterSpinnerleftup(Spinner spinner, String fridge){
        Fridgelist list = (Fridgelist)getApplicationContext();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                MainActivity.this,R.layout.spinnerleftup);
        adapter.setDropDownViewResource(R.layout.spinner_down);
        Integer index = 0;
        for(int i = 0; i < list.fridgelist.size(); i++){
            if(list.fridgelist.get(i).name.matches(fridge)){
                index = i;
                break;
            }
        }
        //adapter.add(String.format("%b", list.fridgelist.get(0).name.matches(fridge)));
        adapter.add(list.fridgelist.get(index).name);
        for(int i = 0; i < list.fridgelist.size(); i++){
            if(!list.fridgelist.get(i).name.matches(fridge)){
                adapter.add(list.fridgelist.get(i).name);
            }
        }
        spinner.setAdapter(adapter);
    }

    private void generateBtnList(String fridge) {
        Fridgelist list = (Fridgelist) getApplicationContext();
        Integer temp = 0;
        for(int i = 0; i < list.fridgelist.size(); i++){
            if(list.fridgelist.get(i).name.matches(fridge)){
                temp = i;
                break;
            }
        }

        if (null == list.fridgelist.get(temp).foodlist) {
            return;
        }

        Collections.sort(list.fridgelist.get(temp).foodlist, new Sortbyexpire());
        int count = 0;
        for (int index = 0; index < list.fridgelist.get(temp).foodlist.size(); index++) {
            for(int i = 0; i < list.fridgelist.get(temp).categorylist.size(); i++){
                if(list.fridgelist.get(temp).categorylist.get(i).category.matches(list.fridgelist.get(temp).foodlist.get(index).category)){
                    if(list.fridgelist.get(temp).categorylist.get(i).onoff){
                        Button codeBtn = new Button(this);
                        setBtnAttribute(codeBtn, list.fridgelist.get(temp).foodlist.get(index), count, index, fridge);
                        count++;
                    }
                    break;
                }
            }
        }
    }

    class Sortbyexpire implements Comparator<Fridgelist.Fridge.Food> {
        // Used for sorting in ascending order of
        // roll number
        public int compare(Fridgelist.Fridge.Food a, Fridgelist.Fridge.Food b) {
            return a.expire - b.expire;
        }
    }

    private void setBtnAttribute(Button codeBtn, Fridgelist.Fridge.Food list, int index, int hash, final String fridge) {
        codeBtn.setId(View.generateViewId());
        if (list.expire < 3) {
            codeBtn.setBackground(getResources().getDrawable(R.drawable.backred, null));
        } else if (list.expire > 5) {
            codeBtn.setBackground(getResources().getDrawable(R.drawable.backgreen, null));
        } else {
            codeBtn.setBackground(getResources().getDrawable(R.drawable.backyellow, null));
        }
        String text = list.name;
        for (int i = 0; i < 5 - list.name.length(); i++) {
            text += "　";
        }
        //text += String.format("%4d\t%6d\t%s", btnContentList.quantity, btnContentList.expire, "天");
        if (list.quantity > 999) {
            text += list.quantity;
        } else if (list.quantity > 99) {
            text += " ";
            text += list.quantity;
        } else if (list.quantity > 9) {
            text += "  ";
            text += list.quantity;
        } else if (list.quantity > -1) {
            text += "   ";
            text += list.quantity;
        } else if (list.quantity > -10) {
            text += "  ";
            text += list.quantity;
        } else if (list.quantity > -100) {
            text += " ";
            text += list.quantity;
        } else {
            text += list.quantity;
        }
        //text+="　";
        if (list.expire > 9999) {
            text += list.expire;
        } else if (list.expire > 999) {
            text += " ";
            text += list.expire;
        } else if (list.expire > 99) {
            text += "  ";
            text += list.expire;
        } else if (list.expire > 9) {
            text += "   ";
            text += list.expire;
        } else if (list.expire > -1) {
            text += "    ";
            text += list.expire;
        } else if (list.expire > -10) {
            text += "   ";
            text += list.expire;
        } else if (list.expire > -100) {
            text += "  ";
            text += list.expire;
        } else if (list.expire > -1000) {
            text += " ";
            text += list.expire;
        } else {
            text += list.expire;
        }
        text += "天";
        codeBtn.setText(text);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/RobotoMono-Regular.ttf");
        codeBtn.setTypeface(font);
        codeBtn.setTag(hash);
        codeBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 21);
        codeBtn.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
        if (!list.pri0pub1) {
            codeBtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.privateicon, 0, 0, 0);
            codeBtn.setPadding(36, 0, 0, 0);
            codeBtn.setCompoundDrawablePadding(27);
        }
        else{
            codeBtn.setPadding(159, 0, 0, 0);
        }

        codeBtn.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Integer index = 0;
                if(v instanceof Button) {
                    if (((Button) v).getTag() instanceof Integer) {
                        index = Integer.parseInt(((Button) v).getTag().toString());
                    }
                    final Integer temp = index;
                    setContentView(R.layout.activity_see_food);
                    BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
                    navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
                    Button backBtn = (Button) findViewById(R.id.btnback);
                    backBtn.setOnClickListener(backwardbtnlistener);
                    Button plusBtn = (Button) findViewById(R.id.btnplus);
                    plusBtn.setOnClickListener(plusfoodbtnlistener);
                    Spinner spinner = (Spinner) findViewById(R.id.spinner);
                    setCenterSpinner(spinner);
                    addsetCenterSpinnerleftup(spinner, fridge);
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            //Toast.makeText(MainActivity.this, "您選擇"+parent.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
                            LinearLayout ll = (LinearLayout) findViewById(R.id.mylinear);
                            ll.removeAllViews();
                            regenerateBtnList(temp, parent.getSelectedItem().toString());
                            LinearLayout linear = (LinearLayout) findViewById(R.id.filter);
                            linear.removeAllViews();
                            generateCheckboxList(parent.getSelectedItem().toString());
                            //call_seefood(parent.getSelectedItem().toString());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    regenerateBtnList(index, fridge);
                    generateCheckboxList(fridge);
                    plusBtn.bringToFront();
                }
                return true;
            }
        });
        //constraintLayout = (android.support.constraint.ConstraintLayout) findViewById(R.id.container);
        LinearLayout linear = (LinearLayout) findViewById(R.id.mylinear);
        LinearLayout contain = new LinearLayout(MainActivity.this);
        contain.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        contain.setLayoutParams(params);
        contain.addView(codeBtn);
        LinearLayout.LayoutParams pac = new LinearLayout.LayoutParams(960, 132);
        pac.setMargins(0, 0, 0, 0);
        codeBtn.setLayoutParams(pac);
        linear.addView(contain);
        /*constraintLayout.addView(codeBtn);
        applyConstraintSet.clone(constraintLayout);
        applyConstraintSet.connect(codeBtn.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP, 528 + 132 * index);
        applyConstraintSet.connect(codeBtn.getId(), ConstraintSet.LEFT, constraintLayout.getId(), ConstraintSet.LEFT, 60);
        applyConstraintSet.constrainHeight(codeBtn.getId(), 132);
        applyConstraintSet.constrainWidth(codeBtn.getId(), ConstraintSet.WRAP_CONTENT);
        applyConstraintSet.applyTo(constraintLayout);*/
    }

    private void regenerateBtnList(int special, String fridge) {
        Fridgelist list = (Fridgelist) getApplicationContext();

        Integer temp = 0;
        for(int i = 0; i < list.fridgelist.size(); i++){
            if(list.fridgelist.get(i).name.matches(fridge)){
                temp = i;
                break;
            }
        }

        if (null == list.fridgelist.get(temp).foodlist) {
            return;
        }

        Collections.sort(list.fridgelist.get(temp).foodlist, new Sortbyexpire());
        int count = 0;
        for (int index = 0; index < list.fridgelist.get(temp).foodlist.size(); index++) {
            for(int i = 0; i < list.fridgelist.get(temp).categorylist.size(); i++){
                if(list.fridgelist.get(temp).categorylist.get(i).category.matches(list.fridgelist.get(temp).foodlist.get(index).category)){
                    if(list.fridgelist.get(temp).categorylist.get(i).onoff){
                        Button codeBtn = new Button(this);
                        resetBtnAttribute(codeBtn, list.fridgelist.get(temp).foodlist.get(index), count, index, special, fridge);
                        count++;
                    }
                    break;
                }
            }
        }
    }

    private void resetBtnAttribute(Button codeBtn, Fridgelist.Fridge.Food list, int index, int hash, int special, final String fridge) {
        if(hash != special) {
            codeBtn.setId(View.generateViewId());
            if (list.expire < 3) {
                codeBtn.setBackground(getResources().getDrawable(R.drawable.backred, null));
            } else if (list.expire > 5) {
                codeBtn.setBackground(getResources().getDrawable(R.drawable.backgreen, null));
            } else {
                codeBtn.setBackground(getResources().getDrawable(R.drawable.backyellow, null));
            }
            String text = list.name;
            for (int i = 0; i < 5 - list.name.length(); i++) {
                text += "　";
            }
            //text += String.format("%4d\t%6d\t%s", btnContentList.quantity, btnContentList.expire, "天");
            if (list.quantity > 999) {
                text += list.quantity;
            } else if (list.quantity > 99) {
                text += " ";
                text += list.quantity;
            } else if (list.quantity > 9) {
                text += "  ";
                text += list.quantity;
            } else if (list.quantity > -1) {
                text += "   ";
                text += list.quantity;
            } else if (list.quantity > -10) {
                text += "  ";
                text += list.quantity;
            } else if (list.quantity > -100) {
                text += " ";
                text += list.quantity;
            } else {
                text += list.quantity;
            }
            //text+="　";
            if (list.expire > 9999) {
                text += list.expire;
            } else if (list.expire > 999) {
                text += " ";
                text += list.expire;
            } else if (list.expire > 99) {
                text += "  ";
                text += list.expire;
            } else if (list.expire > 9) {
                text += "   ";
                text += list.expire;
            } else if (list.expire > -1) {
                text += "    ";
                text += list.expire;
            } else if (list.expire > -10) {
                text += "   ";
                text += list.expire;
            } else if (list.expire > -100) {
                text += "  ";
                text += list.expire;
            } else if (list.expire > -1000) {
                text += " ";
                text += list.expire;
            } else {
                text += list.expire;
            }
            text += "天";
            codeBtn.setText(text);
            Typeface font = Typeface.createFromAsset(getAssets(), "fonts/RobotoMono-Regular.ttf");
            codeBtn.setTypeface(font);
            codeBtn.setTag(hash);
            codeBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 21);
            codeBtn.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
            if (!list.pri0pub1) {
                codeBtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.privateicon, 0, 0, 0);
                codeBtn.setPadding(36, 0, 0, 0);
                codeBtn.setCompoundDrawablePadding(27);
            } else {
                codeBtn.setPadding(159, 0, 0, 0);
            }

            codeBtn.setOnLongClickListener(new OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Integer index = 0;
                    if (v instanceof Button) {
                        if (((Button) v).getTag() instanceof Integer) {
                            index = Integer.parseInt(((Button) v).getTag().toString());
                        }
                        final Integer temp = index;
                        setContentView(R.layout.activity_see_food);
                        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
                        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
                        Button backBtn = (Button) findViewById(R.id.btnback);
                        backBtn.setOnClickListener(backwardbtnlistener);
                        Button plusBtn = (Button) findViewById(R.id.btnplus);
                        plusBtn.setOnClickListener(plusfoodbtnlistener);
                        Spinner spinner = (Spinner) findViewById(R.id.spinner);
                        setCenterSpinner(spinner);
                        addsetCenterSpinnerleftup(spinner, fridge);
                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                //Toast.makeText(MainActivity.this, "您選擇"+parent.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
                                LinearLayout ll = (LinearLayout) findViewById(R.id.mylinear);
                                ll.removeAllViews();
                                regenerateBtnList(temp, parent.getSelectedItem().toString());
                                LinearLayout linear = (LinearLayout) findViewById(R.id.filter);
                                linear.removeAllViews();
                                generateCheckboxList(parent.getSelectedItem().toString());
                                //call_seefood(parent.getSelectedItem().toString());
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        regenerateBtnList(index, fridge);
                        generateCheckboxList(fridge);
                        plusBtn.bringToFront();
                    }
                    return true;
                }
            });
            //constraintLayout = (android.support.constraint.ConstraintLayout) findViewById(R.id.container);
            LinearLayout linear = (LinearLayout) findViewById(R.id.mylinear);
            LinearLayout contain = new LinearLayout(MainActivity.this);
            contain.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            contain.setLayoutParams(params);
            contain.addView(codeBtn);
            LinearLayout.LayoutParams pac = new LinearLayout.LayoutParams(960, 132);
            pac.setMargins(0, 0, 0, 0);
            codeBtn.setLayoutParams(pac);
            linear.addView(contain);
        }
        else{
            codeBtn.setId(View.generateViewId());
            final Button delete = new Button(MainActivity.this);
            Button recipe = new Button(MainActivity.this);
            Button edit = new Button(MainActivity.this);
            delete.setId(View.generateViewId());
            recipe.setId(View.generateViewId());
            edit.setId(View.generateViewId());
            if (list.expire < 3) {
                codeBtn.setBackground(getResources().getDrawable(R.drawable.longpress1, null));
                recipe.setBackground(getResources().getDrawable(R.drawable.longpress2, null));
                edit.setBackground(getResources().getDrawable(R.drawable.longpress3, null));
                delete.setBackground(getResources().getDrawable(R.drawable.longpress4, null));
            } else if (list.expire > 5) {
                codeBtn.setBackground(getResources().getDrawable(R.drawable.longpress9, null));
                recipe.setBackground(getResources().getDrawable(R.drawable.longpress10, null));
                edit.setBackground(getResources().getDrawable(R.drawable.longpress11, null));
                delete.setBackground(getResources().getDrawable(R.drawable.longpress12, null));
            } else {
                codeBtn.setBackground(getResources().getDrawable(R.drawable.longpress5, null));
                recipe.setBackground(getResources().getDrawable(R.drawable.longpress6, null));
                edit.setBackground(getResources().getDrawable(R.drawable.longpress7, null));
                delete.setBackground(getResources().getDrawable(R.drawable.longpress8, null));
            }
            String text = list.name;
            codeBtn.setText(text);
            Typeface font = Typeface.createFromAsset(getAssets(), "fonts/RobotoMono-Regular.ttf");
            codeBtn.setTypeface(font);
            codeBtn.setTag(hash);
            codeBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 21);
            codeBtn.setGravity(Gravity.CENTER_VERTICAL);
            if (!list.pri0pub1) {
                codeBtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.privateicon, 0, 0, 0);
                codeBtn.setPadding(36, 0, 0, 0);
                codeBtn.setCompoundDrawablePadding(27);
            } else {
                codeBtn.setPadding(159, 0, 0, 0);
            }

            codeBtn.setOnLongClickListener(new OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    setContentView(R.layout.activity_see_food);
                    BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
                    navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
                    Button backBtn = (Button) findViewById(R.id.btnback);
                    backBtn.setOnClickListener(backwardbtnlistener);
                    Button plusBtn = (Button) findViewById(R.id.btnplus);
                    plusBtn.setOnClickListener(plusfoodbtnlistener);
                    Spinner spinner = (Spinner) findViewById(R.id.spinner);
                    setCenterSpinner(spinner);
                    addsetCenterSpinnerleftup(spinner, fridge);
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            //Toast.makeText(MainActivity.this, "您選擇"+parent.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
                            LinearLayout ll = (LinearLayout) findViewById(R.id.mylinear);
                            ll.removeAllViews();
                            generateBtnList(parent.getSelectedItem().toString());
                            LinearLayout linear = (LinearLayout) findViewById(R.id.filter);
                            linear.removeAllViews();
                            generateCheckboxList(parent.getSelectedItem().toString());
                            //call_seefood(parent.getSelectedItem().toString());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    generateBtnList(fridge);
                    generateCheckboxList(fridge);
                    plusBtn.bringToFront();
                    return true;
                }
            });
            delete.setTag(hash);
            delete.setGravity(Gravity.CENTER_VERTICAL);
            delete.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(final View viewn) {
                    //setContentView(R.layout.deletefood);
                    AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                    final View view = getLayoutInflater().inflate(R.layout.deletefood, null);
                    dialog.setView(view);
                    final AlertDialog loginside = dialog.create();

                    Button cancelbtn = (Button) view.findViewById((R.id.btncancel));
                    Button confirmbtn = (Button) view.findViewById(R.id.btndelete);

                    cancelbtn.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            loginside.dismiss();
                        }
                    });

                    confirmbtn.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Integer index = 0;
                            Fridgelist list = (Fridgelist)getApplicationContext();
                            Integer temp = 0;
                            for(int i = 0; i < list.fridgelist.size(); i++){
                                if(list.fridgelist.get(i).name.matches(fridge)){
                                    temp = i;
                                    break;
                                }
                            }
                            if(viewn instanceof Button) {
                                if (((Button) viewn).getTag() instanceof Integer) {
                                    index = Integer.parseInt(((Button) viewn).getTag().toString());
                                }
                            }
                            list.fridgelist.get(temp).delete(index);
                            loginside.dismiss();
                            setContentView(R.layout.activity_see_food);
                            BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
                            navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
                            Button backBtn = (Button) findViewById(R.id.btnback);
                            backBtn.setOnClickListener(backwardbtnlistener);
                            Button plusBtn = (Button) findViewById(R.id.btnplus);
                            plusBtn.setOnClickListener(plusfoodbtnlistener);
                            Spinner spinner = (Spinner) findViewById(R.id.spinner);
                            setCenterSpinner(spinner);
                            generateBtnList(fridge);
                            generateCheckboxList(fridge);
                            plusBtn.bringToFront();
                        }
                    });
                    loginside.show();
                    WindowManager.LayoutParams params = loginside.getWindow().getAttributes();
                    params.width = 690;
                    params.height = 495;
                    loginside.getWindow().setAttributes(params);
                }
            });
            recipe.setGravity(Gravity.CENTER_VERTICAL);
            edit.setGravity(Gravity.CENTER_VERTICAL);

            edit.setTag(hash);

            edit.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View viewn) {
                    final Fridgelist list = (Fridgelist)getApplicationContext();
                    Integer temp = 0;
                    for(int i = 0; i < list.fridgelist.size(); i++){
                        if(list.fridgelist.get(i).name.matches(fridge)){
                            temp = i;
                            break;
                        }
                    }
                    final Integer inside = temp;
                    Integer index = 0;
                    if(viewn instanceof Button) {
                        if (((Button) viewn).getTag() instanceof Integer) {
                            index = Integer.parseInt(((Button) viewn).getTag().toString());
                        }
                    }
                    final Integer tempindex = index;
                    Spinner spinner_out = (Spinner) findViewById(R.id.spinner);
                    String fridgeout = spinner_out.getSelectedItem().toString();
                    AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                    final View view = getLayoutInflater().inflate(R.layout.editfood, null);

                    // set the custom dialog components - text, image and button
                    final Spinner spinner = (Spinner) view.findViewById(R.id.spinnerfridge);
                    setCenterSpinner(spinner);
                    addsetCenterSpinnerleftup(spinner, fridgeout);
                    final Spinner spinner2 = (Spinner) view.findViewById(R.id.spinnercategory);
                    addsetCenterSpinner3(spinner2, index, fridge);

                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if(!parent.getSelectedItem().toString().matches(fridge)){
                                removeSpinner(spinner2);
                                addsetCenterSpinner1(spinner2,  parent.getSelectedItem().toString());
                            }
                            else{
                                removeSpinner(spinner2);
                                addsetCenterSpinner3(spinner2, tempindex, fridge);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                    EditText utxtValue = (EditText) view.findViewById(R.id.username);
                    utxtValue.setText(list.fridgelist.get(temp).foodlist.get(index).name);
                    EditText psText = (EditText) view.findViewById(R.id.ps);
                    psText.setText(list.fridgelist.get(temp).foodlist.get(index).ps);
                    EditText txtValue = (EditText) view.findViewById(R.id.quantity);
                    txtValue.setText(format("%d", list.fridgelist.get(temp).foodlist.get(index).quantity));
                    TextView date = (TextView) view.findViewById(R.id.date);
                    String today = format("%d/%d/%d", list.fridgelist.get(temp).foodlist.get(index).year, list.fridgelist.get(temp).foodlist.get(index).month+1, list.fridgelist.get(temp).foodlist.get(index).day);
                    date.setText(today);
                    ToggleButton togglebutton = (ToggleButton) view.findViewById(R.id.toggle_button);
                    togglebutton.setChecked(list.fridgelist.get(temp).foodlist.get(index).pri0pub1);

                    Button dialogButton = (Button) view.findViewById(R.id.btnminus);
                    // if button is clicked, close the custom dialog
                    dialogButton.setOnClickListener(new OnClickListener() {
                        public void onClick(View v) {
                            EditText text = (EditText) view.findViewById(R.id.quantity);
                            int num = Integer.valueOf((String) text.getText().toString());
                            num--;
                            text.setText(Integer.toString(num));
                        }
                    });
                    Button plusbtn = (Button) view.findViewById((R.id.btnplus));
                    plusbtn.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            EditText text = (EditText) view.findViewById(R.id.quantity);
                            int num = Integer.valueOf((String) text.getText().toString());
                            num++;
                            text.setText(format("%d", num));
                        }
                    });
                    final int indexinside = index;
                    //calendar
                    Button canlendarbtn = (Button)view.findViewById(R.id.btncalendar);
                    canlendarbtn.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DatePickerDialog dpd = new DatePickerDialog(MainActivity.this, R.style.AppTheme_DialogTheme, new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker vi, int year, int month, int dayOfMonth) {
                                    TextView txt = (TextView)view.findViewById(R.id.date);
                                    txt.setText(format("%d/%d/%d",year,  month+1, dayOfMonth));
                                }
                            }, list.fridgelist.get(inside).foodlist.get(indexinside).year, list.fridgelist.get(inside).foodlist.get(indexinside).month, list.fridgelist.get(inside).foodlist.get(indexinside).day);
                            dpd.show();
                        }
                    });

                    Button cancelbtn = (Button) view.findViewById((R.id.btncancel));
                    Button confirmbtn = (Button) view.findViewById(R.id.btnconfirm);

                    dialog.setView(view);
                    final AlertDialog log = dialog.create();

                    cancelbtn.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            log.dismiss();
                        }
                    });

                    confirmbtn.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            EditText editText = (EditText) view.findViewById(R.id.username);
                            Spinner spinnerc = (Spinner) view.findViewById(R.id.spinnercategory);
                            if (editText.getText().toString().matches("")) {
                                Toast toast = Toast.makeText(MainActivity.this, "請輸入品項", Toast.LENGTH_LONG);
                                toast.show();
                            } else if (editText.getText().toString().length() > 5) {
                                Toast toast = Toast.makeText(MainActivity.this, "品項名稱太長了", Toast.LENGTH_LONG);
                                toast.show();
                            } else {
                                Spinner spinnerf = (Spinner) view.findViewById(R.id.spinnerfridge);
                                Fridgelist list = (Fridgelist) getApplicationContext();
                                EditText quan = (EditText) view.findViewById(R.id.quantity);
                                ToggleButton toggle = (ToggleButton) view.findViewById(R.id.toggle_button);
                                EditText ps = (EditText) view.findViewById(R.id.ps);
                                TextView date = (TextView) view.findViewById(R.id.date);
                                Integer temp = 0;
                                for (int i = 0; i < list.fridgelist.size(); i++) {
                                    if (list.fridgelist.get(i).name.matches(fridge)) {
                                        temp = i;
                                        break;
                                    }
                                }
                                String line = (String) date.getText();
                                String[] split_line = line.split("/");
                                int yy = Integer.valueOf(split_line[0]);
                                int mm = Integer.valueOf(split_line[1]);
                                int dd = Integer.valueOf(split_line[2]);
                                DateFormat df = new SimpleDateFormat("yyyy/M/dd");
                                Calendar c = Calendar.getInstance();
                                int ny = c.get(Calendar.YEAR);
                                int nm = c.get(Calendar.MONTH);
                                int nd = c.get(Calendar.DAY_OF_MONTH);
                                String now = format("%d/%d/%d", ny, (nm + 1), nd);
                                long days = 0;
                                try {
                                    Date d1 = df.parse(line);
                                    Date d2 = df.parse(now);
                                    long diff = d1.getTime() - d2.getTime();
                                    days = diff / (1000 * 60 * 60 * 24);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                if (spinnerf.getSelectedItem().toString().matches(fridge)) {
                                    list.fridgelist.get(temp).change(indexinside, editText.getText().toString(), Integer.valueOf(quan.getText().toString()),
                                            spinnerc.getSelectedItem().toString(), (int) days, toggle.isChecked(), ps.getText().toString(), yy, mm - 1, dd);
                                    log.dismiss();
                                    call_seefood(fridge);
                                }
                                else{
                                    list.fridgelist.get(temp).delete(indexinside);
                                    for (int i = 0; i < list.fridgelist.size(); i++) {
                                        if (list.fridgelist.get(i).name.matches(spinnerf.getSelectedItem().toString())) {
                                            temp = i;
                                            break;
                                        }
                                    }
                                    list.fridgelist.get(temp).add(editText.getText().toString(), Integer.valueOf(quan.getText().toString()),
                                            spinnerc.getSelectedItem().toString(), (int) days, toggle.isChecked(), ps.getText().toString(), yy, mm - 1, dd);
                                    log.dismiss();
                                    call_seefood(spinnerf.getSelectedItem().toString());
                                }
                            }
                        }
                    });

                    log.show();
                }
            });

            LinearLayout linear = (LinearLayout) findViewById(R.id.mylinear);
            LinearLayout contain = new LinearLayout(MainActivity.this);
            contain.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 0, 0, 0);
            contain.setBaselineAligned(false);
            contain.setLayoutParams(params);
            contain.addView(codeBtn);
            contain.addView(recipe);
            contain.addView(edit);
            contain.addView(delete);
            if(list.expire<3) {
                LinearLayout.LayoutParams pac = new LinearLayout.LayoutParams(562, 132);
                pac.setMargins(0, 0, 0, 0);
                codeBtn.setLayoutParams(pac);
                LinearLayout.LayoutParams pad = new LinearLayout.LayoutParams(163, 132);
                pad.setMargins(0, 0, 0, 0);
                delete.setLayoutParams(pad);
                LinearLayout.LayoutParams par = new LinearLayout.LayoutParams(115, 132);
                par.setMargins(0, 0, 0, 0);
                recipe.setLayoutParams(par);
                LinearLayout.LayoutParams pae = new LinearLayout.LayoutParams(120, 132);
                pae.setMargins(0, 0, 0, 0);
                edit.setLayoutParams(pae);
            }
            else if(list.expire>5){
                LinearLayout.LayoutParams pac = new LinearLayout.LayoutParams(558, 132);
                pac.setMargins(0, 0, 0, 0);
                codeBtn.setLayoutParams(pac);
                LinearLayout.LayoutParams pad = new LinearLayout.LayoutParams(164, 132);
                pad.setMargins(0, 0, 0, 0);
                delete.setLayoutParams(pad);
                LinearLayout.LayoutParams par = new LinearLayout.LayoutParams(120, 132);
                par.setMargins(0, 0, 0, 0);
                recipe.setLayoutParams(par);
                LinearLayout.LayoutParams pae = new LinearLayout.LayoutParams(118, 132);
                pae.setMargins(0, 0, 0, 0);
                edit.setLayoutParams(pae);
            }
            else{
                LinearLayout.LayoutParams pac = new LinearLayout.LayoutParams(563, 132);
                pac.setMargins(0, 0, 0, 0);
                codeBtn.setLayoutParams(pac);
                LinearLayout.LayoutParams pad = new LinearLayout.LayoutParams(165, 132);
                pad.setMargins(0, 0, 0, 0);
                delete.setLayoutParams(pad);
                LinearLayout.LayoutParams par = new LinearLayout.LayoutParams(115, 132);
                par.setMargins(0, 0, 0, 0);
                recipe.setLayoutParams(par);
                LinearLayout.LayoutParams pae = new LinearLayout.LayoutParams(117, 132);
                pae.setMargins(0, 0, 0, 0);
                edit.setLayoutParams(pae);
            }
            //delete.bringToFront();
            linear.addView(contain);
        }
        /*constraintLayout.addView(codeBtn);
        applyConstraintSet.clone(constraintLayout);
        applyConstraintSet.connect(codeBtn.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP, 528 + 132 * index);
        applyConstraintSet.connect(codeBtn.getId(), ConstraintSet.LEFT, constraintLayout.getId(), ConstraintSet.LEFT, 60);
        applyConstraintSet.constrainHeight(codeBtn.getId(), 132);
        applyConstraintSet.constrainWidth(codeBtn.getId(), ConstraintSet.WRAP_CONTENT);
        applyConstraintSet.applyTo(constraintLayout);*/
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // Show home screen when pressing “back” button,
            //  so that this app won’t be closed accidentally
            call_main();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    public void switchfilter(View view){
        LinearLayout linear = (LinearLayout) findViewById(R.id.filter);
        if(linear.getVisibility() == View.GONE)
            linear.setVisibility(View.VISIBLE);
        else
            linear.setVisibility(View.GONE);
    }

    public void generateCheckboxList(String fridge){
        Fridgelist list = (Fridgelist) getApplicationContext();

        Integer indexx = 0;
        for(int i = 0; i < list.fridgelist.size(); i++){
            if(list.fridgelist.get(i).name.matches(fridge)){
                indexx = i;
                break;
            }
        }

        if (list.fridgelist.get(indexx).categorylist.size() == 3) {
            return;
        }

        CheckBox checkBoxAll = new CheckBox(this);
        setAll(checkBoxAll, "全選", fridge);

        Collections.sort(list.fridgelist.get(indexx).categorylist, new Sortbyorder());
        for (int index = 0; index < list.fridgelist.get(indexx).categorylist.size(); index++) {
            CheckBox checkBox = new CheckBox(this);
            if(list.fridgelist.get(indexx).categorylist.get(index).order != -1) {
                setCheckBoxAttribute(checkBox, list.fridgelist.get(indexx).categorylist.get(index), index, fridge);
            }
        }
    }

    public void setAll(final CheckBox checkBox, String name, final String fridge){
        final Fridgelist list = (Fridgelist) getApplicationContext();

        Integer index = 0;
        for(int i = 0; i < list.fridgelist.size(); i++){
            if(list.fridgelist.get(i).name.matches(fridge)){
                index = i;
                break;
            }
        }

        final Integer temp = index;

        for(int i = 0; i < list.fridgelist.get(index).Map.size(); i++){
            if(list.fridgelist.get(index).Map.get(i).category.matches(name))
                checkBox.setId(list.fridgelist.get(index).Map.get(i).id);
        }
        boolean flag = true;
        for(int i = 0; i < list.fridgelist.get(index).categorylist.size(); i++){
            if(!list.fridgelist.get(index).categorylist.get(i).onoff){
                flag = false;
                break;
            }
        }
        if(flag){
            checkBox.setChecked(true);
        }
        else{
            checkBox.setChecked(false);
        }
        checkBox.setGravity(Gravity.END|Gravity.CENTER_VERTICAL);
        checkBox.setText(name);
        checkBox.setPadding(45, 28, 45, 28);
        checkBox.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        checkBox.setButtonDrawable(R.drawable.check_box_style);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                boolean flag = true;
                for(int i = 1; i < list.fridgelist.get(temp).Map.size(); i++){
                    CheckBox checkBox1 = (CheckBox) findViewById(list.fridgelist.get(temp).Map.get(i).id);
                    if(!checkBox1.isChecked()){
                        flag = false;
                        break;
                    }
                }
                if(isChecked){
                    for(int i = 0; i < list.fridgelist.get(temp).Map.size(); i++){
                        CheckBox checkBox1 = (CheckBox) findViewById(list.fridgelist.get(temp).Map.get(i).id);
                        checkBox1.setChecked(true);
                    }
                }
                else if(flag){
                    for(int i = 0; i < list.fridgelist.get(temp).Map.size(); i++){
                        CheckBox checkBox1 = (CheckBox) findViewById(list.fridgelist.get(temp).Map.get(i).id);
                        checkBox1.setChecked(false);
                    }
                }
                LinearLayout ll = (LinearLayout) findViewById(R.id.mylinear);
                ll.removeAllViews();
                generateBtnList(fridge);
            }
        });

        LinearLayout linear = (LinearLayout) findViewById(R.id.filter);
        linear.addView(checkBox);
    }

    public void setCheckBoxAttribute(final CheckBox checkBox, final Fridgelist.Fridge.Category category, final int index, final String fridge){
        final Fridgelist list = (Fridgelist) getApplicationContext();
        Integer temp = 0;
        for(int i = 0; i < list.fridgelist.size(); i++){
            if(list.fridgelist.get(i).name.matches(fridge)){
                temp = i;
                break;
            }
        }

        final Integer inside = temp;

        for(int i = 0; i < list.fridgelist.get(temp).Map.size(); i++){
            if(list.fridgelist.get(temp).Map.get(i).category.matches(category.category))
                checkBox.setId(list.fridgelist.get(temp).Map.get(i).id);
        }
        checkBox.setTag(index + "category");
        for(int i = 0; i < list.fridgelist.get(temp).categorylist.size(); i++){
            if(list.fridgelist.get(temp).categorylist.get(i).category.matches(category.category)){
                if(list.fridgelist.get(temp).categorylist.get(i).onoff){
                    checkBox.setChecked(true);
                }
                else{
                    checkBox.setChecked(false);
                }
            }
        }
        checkBox.setGravity(Gravity.END|Gravity.CENTER_VERTICAL);
        checkBox.setText(category.category);
        checkBox.setPadding(45, 28, 45, 28);
        checkBox.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        checkBox.setButtonDrawable(R.drawable.check_box_style);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               if(isChecked){
                    boolean flag = true;
                    for(int i = 1; i < list.fridgelist.get(inside).Map.size(); i++){
                        CheckBox checkBox1 = (CheckBox) findViewById(list.fridgelist.get(inside).Map.get(i).id);
                        if(!checkBox1.isChecked()){
                            flag = false;
                            break;
                        }
                    }
                    if(flag){
                        CheckBox checkBox1 = (CheckBox) findViewById(list.fridgelist.get(inside).Map.get(0).id);
                        checkBox1.setChecked(true);
                    }
                    for(int i = 0; i < list.fridgelist.get(inside).categorylist.size(); i++){
                        if(list.fridgelist.get(inside).categorylist.get(i).category.matches(buttonView.getText().toString()))
                            list.fridgelist.get(inside).categorylist.get(i).onoff = true;
                    }
                }
                else{
                    CheckBox checkBox1 = (CheckBox) findViewById(list.fridgelist.get(inside).Map.get(0).id);
                    if(checkBox1.isChecked()){
                        checkBox1.setChecked(false);
                    }
                    for(int i = 0; i < list.fridgelist.get(inside).categorylist.size(); i++){
                        if(list.fridgelist.get(inside).categorylist.get(i).category.matches(buttonView.getText().toString()))
                            list.fridgelist.get(inside).categorylist.get(i).onoff = false;
                    }
                }
                LinearLayout ll = (LinearLayout) findViewById(R.id.mylinear);
                ll.removeAllViews();
                Spinner spinner = (Spinner)findViewById(R.id.spinner);
                generateBtnList(spinner.getSelectedItem().toString());
            }
        });

        LinearLayout linear = (LinearLayout) findViewById(R.id.filter);
        linear.addView(checkBox);
    }

    public void add_fridge(View view){
        final Fridgelist list = (Fridgelist)getApplicationContext();
        if(list.fridgelist.size() == 2){
            Toast toast = Toast.makeText(MainActivity.this, "最多只能擁有兩個冰箱", Toast.LENGTH_LONG);
            toast.show();
        }
        else {
            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
            final View v = getLayoutInflater().inflate(R.layout.new_fridge, null);

            Button cancel = (Button) v.findViewById(R.id.cancel);
            Button confirm = (Button) v.findViewById(R.id.confirm);
            Button question = (Button) v.findViewById(R.id.question);

            dialog.setView(v);
            final AlertDialog log = dialog.create();

            question.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    ImageView setsumei = (ImageView)v.findViewById(R.id.setsumei);
                    if(setsumei.getVisibility()==View.GONE){
                        setsumei.setVisibility(View.VISIBLE);
                        setsumei.bringToFront();
                    }
                    else{
                        setsumei.setVisibility(View.GONE);
                    }
                }
            });

            cancel.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    log.dismiss();
                }
            });

            confirm.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    EditText fridge = (EditText) v.findViewById(R.id.fridge_name);
                    String fridge_name = fridge.getText().toString();
                    EditText name = (EditText) v.findViewById(R.id.user_name);
                    String user_name = name.getText().toString();
                    if(fridge_name.length()>3){
                        Toast toast = Toast.makeText(MainActivity.this, "冰箱名稱最多三個字", Toast.LENGTH_LONG);
                        toast.show();
                    }
                    else if(fridge_name.matches("")){
                        Toast toast = Toast.makeText(MainActivity.this, "請輸入冰箱名稱", Toast.LENGTH_LONG);
                        toast.show();
                    }
                    else if(user_name.matches("")){
                        Toast toast = Toast.makeText(MainActivity.this, "請輸入您的暱稱", Toast.LENGTH_LONG);
                        toast.show();
                    }
                    else {
                        String id = "3456789";
                        list.add_fridge(fridge_name, id, user_name);
                        list.add_id(list.fridgelist.get(list.fridgelist.size() - 1).id, View.generateViewId());
                        list.fridgelist.get(list.fridgelist.size() - 1).addcategory("預設", 1, true);
                        list.fridgelist.get(list.fridgelist.size() - 1).addcategory("更改預設", -1, true);
                        list.fridgelist.get(list.fridgelist.size() - 1).addcategory("新增類別", -1, true);
                        list.fridgelist.get(list.fridgelist.size() - 1).addcategory("刪除類別", -1, true);
                        list.fridgelist.get(list.fridgelist.size() - 1).addMap("全選", View.generateViewId());
                        list.fridgelist.get(list.fridgelist.size() - 1).addMap("預設", View.generateViewId());
                        log.dismiss();
                        call_main();
                    }
                }
            });

            log.show();
            WindowManager.LayoutParams params = log.getWindow().getAttributes();
            params.height = 850;
            params.width = 850;
            log.getWindow().setAttributes(params);
        }
    }

    private Button.OnClickListener backwardbtnlistener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            call_main();
        }
    };

    private Button.OnClickListener plusfoodbtnlistener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            // custom dialog
            Spinner spinner_out = (Spinner) findViewById(R.id.spinner);
            String fridge = spinner_out.getSelectedItem().toString();
            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
            final View view = getLayoutInflater().inflate(R.layout.plus_food, null);

            // set the custom dialog components - text, image and button
            final Spinner spinner = (Spinner) view.findViewById(R.id.spinnerfridge);
            setCenterSpinner(spinner);
            addsetCenterSpinnerleftup(spinner, fridge);
            final int quant = 0;
            EditText txtValue = (EditText) view.findViewById(R.id.quantity);
            txtValue.setText(format("%d", quant));
            TextView date = (TextView) view.findViewById(R.id.date);
            Calendar mCal = Calendar.getInstance();
            String dateformat = "yyyy/M/dd";
            SimpleDateFormat df = new SimpleDateFormat(dateformat);
            String today = df.format(mCal.getTime());
            date.setText(today);
            final Spinner spinner2 = (Spinner) view.findViewById(R.id.spinnercategory);
            addsetCenterSpinner2(spinner2, spinner.getSelectedItem().toString());
            ToggleButton togglebutton = (ToggleButton) view.findViewById(R.id.toggle_button);
            togglebutton.setChecked(false);

            Button dialogButton = (Button) view.findViewById(R.id.btnminus);
            // if button is clicked, close the custom dialog
            dialogButton.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    EditText text = (EditText) view.findViewById(R.id.quantity);
                    int num = Integer.valueOf((String) text.getText().toString());
                    num--;
                    text.setText(Integer.toString(num));
                }
            });
            Button plusbtn = (Button) view.findViewById((R.id.btnplus));
            plusbtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText text = (EditText) view.findViewById(R.id.quantity);
                    int num = Integer.valueOf((String) text.getText().toString());
                    num++;
                    text.setText(format("%d", num));
                }
            });

            //calendar
            Button canlendarbtn = (Button)view.findViewById(R.id.btncalendar);
            canlendarbtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Calendar c = Calendar.getInstance();
                    int day = c.get(Calendar.DAY_OF_MONTH);
                    int month = c.get(Calendar.MONTH);
                    int year = c.get(Calendar.YEAR);
                    DatePickerDialog dpd = new DatePickerDialog(MainActivity.this, R.style.AppTheme_DialogTheme, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker vi, int year, int month, int dayOfMonth) {
                            TextView txt = (TextView)view.findViewById(R.id.date);
                            txt.setText(format("%d/%d/%d",year,  month+1, dayOfMonth));
                        }
                    }, year, month, day);
                    dpd.show();
                }
            });

            Button cancelbtn = (Button) view.findViewById((R.id.btncancel));
            Button confirmbtn = (Button) view.findViewById(R.id.btnconfirm);

            dialog.setView(view);
            final AlertDialog log = dialog.create();

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    removeSpinner(spinner2);
                    addsetCenterSpinner2(spinner2, parent.getSelectedItem().toString());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            spinner2.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
                public void onItemSelected(AdapterView parent, View v, int position, long id) {
                    if(parent.getSelectedItem().toString().matches("新增類別")){
                        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                        final View view = getLayoutInflater().inflate(R.layout.addcategory, null);
                        dialog.setView(view);
                        final AlertDialog loginside = dialog.create();

                        Button cancelbtn = (Button) view.findViewById((R.id.btncancel));
                        Button confirmbtn = (Button) view.findViewById(R.id.btnnew);

                        cancelbtn.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                loginside.dismiss();
                            }
                        });

                        confirmbtn.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                EditText entered = (EditText)view.findViewById(R.id.newcatogary);
                                Fridgelist list = (Fridgelist)getApplicationContext();
                                Integer temp = 0;
                                for(int i = 0; i < list.fridgelist.size(); i++){
                                    if(list.fridgelist.get(i).name.matches(spinner.getSelectedItem().toString())){
                                        temp = i;
                                        break;
                                    }
                                }
                                if(list.fridgelist.get(temp).existedcategory(entered.getText().toString()) == true){
                                    Toast toast = Toast.makeText(MainActivity.this, "此類別已存在", Toast.LENGTH_LONG);
                                    toast.show();
                                }
                                else if(entered.getText().toString().matches("")){
                                    Toast toast = Toast.makeText(MainActivity.this, "請輸入類別", Toast.LENGTH_LONG);
                                    toast.show();
                                }
                                else if(entered.getText().toString().matches("全選")){
                                    Toast toast = Toast.makeText(MainActivity.this, "類別不能叫做全選", Toast.LENGTH_LONG);
                                    toast.show();
                                }
                                else if(entered.getText().toString().matches("更改預設")){
                                    Toast toast = Toast.makeText(MainActivity.this, "類別不能叫做更改預設", Toast.LENGTH_LONG);
                                    toast.show();
                                }
                                else if(entered.getText().toString().matches("刪除類別")){
                                    Toast toast = Toast.makeText(MainActivity.this, "類別不能叫做刪除類別", Toast.LENGTH_LONG);
                                    toast.show();
                                }
                                else if(entered.getText().toString().matches("新增類別")){
                                    Toast toast = Toast.makeText(MainActivity.this, "類別不能叫做新增類別", Toast.LENGTH_LONG);
                                    toast.show();
                                }
                                else{
                                    list.fridgelist.get(temp).addMap(entered.getText().toString(), View.generateViewId());
                                    list.fridgelist.get(temp).addcategory(entered.getText().toString(), 0, true);
                                    loginside.dismiss();
                                    log.dismiss();
                                    call_seefood(spinner.getSelectedItem().toString());
                                }
                            }
                        });

                        loginside.show();
                        WindowManager.LayoutParams params = loginside.getWindow().getAttributes();
                        params.width = 690;
                        params.height = 495;
                        loginside.getWindow().setAttributes(params);
                    }
                    else if(parent.getSelectedItem().toString().matches("刪除類別")){
                        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                        final View view = getLayoutInflater().inflate(R.layout.deletecategory, null);
                        dialog.setView(view);
                        final AlertDialog loginside = dialog.create();

                        Button cancelbtn = (Button) view.findViewById((R.id.btncancel));
                        Button confirmbtn = (Button) view.findViewById(R.id.btndelete);

                        Spinner spinner2 = (Spinner) view.findViewById(R.id.spinnerdelete);
                        addsetCenterSpinner(spinner2, spinner.getSelectedItem().toString());

                        cancelbtn.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                loginside.dismiss();
                            }
                        });

                        confirmbtn.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Fridgelist list = (Fridgelist) getApplicationContext();
                                Integer temp = 0;
                                for(int i = 0; i < list.fridgelist.size(); i++){
                                    if(list.fridgelist.get(i).name.matches(spinner.getSelectedItem().toString())){
                                        temp = i;
                                        break;
                                    }
                                }
                                Spinner entered = (Spinner)view.findViewById(R.id.spinnerdelete);
                                if(list.fridgelist.get(temp).categorylist.size() < 5){
                                    Toast toast = Toast.makeText(MainActivity.this, "請選擇類別", Toast.LENGTH_LONG);
                                    toast.show();
                                }
                                else{
                                    list.fridgelist.get(temp).deleteMap(entered.getSelectedItem().toString());
                                    list.fridgelist.get(temp).changecategory(entered.getSelectedItem().toString());
                                    list.fridgelist.get(temp).deletecategory(entered.getSelectedItem().toString());
                                    loginside.dismiss();
                                    log.dismiss();
                                    call_seefood(spinner.getSelectedItem().toString());
                                }
                            }
                        });

                        loginside.show();
                        WindowManager.LayoutParams params = loginside.getWindow().getAttributes();
                        params.width = 900;
                        params.height = 690;
                        loginside.getWindow().setAttributes(params);
                    }
                    else if(parent.getSelectedItem().toString().matches("更改預設")){
                        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                        final View view = getLayoutInflater().inflate(R.layout.changedefault, null);
                        dialog.setView(view);
                        final AlertDialog loginside = dialog.create();

                        Button cancelbtn = (Button) view.findViewById((R.id.btncancel));
                        Button confirmbtn = (Button) view.findViewById(R.id.btnconfirm);

                        Spinner spinner2 = (Spinner) view.findViewById(R.id.spinnerdefault);
                        addsetCenterSpinner1(spinner2, spinner.getSelectedItem().toString());

                        cancelbtn.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                loginside.dismiss();
                            }
                        });

                        confirmbtn.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Fridgelist list = (Fridgelist) getApplicationContext();
                                Integer temp = 0;
                                for(int i = 0; i < list.fridgelist.size(); i++){
                                    if(list.fridgelist.get(i).name.matches(spinner.getSelectedItem().toString())){
                                        temp = i;
                                        break;
                                    }
                                }
                                Spinner entered = (Spinner)view.findViewById(R.id.spinnerdefault);
                                list.fridgelist.get(temp).changedefaultcategory(entered.getSelectedItem().toString());
                                loginside.dismiss();
                                log.dismiss();
                                call_seefood(spinner.getSelectedItem().toString());
                            }
                        });

                        loginside.show();
                        WindowManager.LayoutParams params = loginside.getWindow().getAttributes();
                        params.width = 690;
                        params.height = 495;
                        loginside.getWindow().setAttributes(params);
                    }
                }
                public void onNothingSelected(AdapterView parent) {
                }
            });

            cancelbtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    log.dismiss();
                }
            });

            confirmbtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText editText = (EditText) view.findViewById(R.id.username);
                    Spinner spinnerc = (Spinner) view.findViewById(R.id.spinnercategory);
                    if(editText.getText().toString().matches("")){
                        Toast toast = Toast.makeText(MainActivity.this, "請輸入品項", Toast.LENGTH_LONG);
                        toast.show();
                    }
                    else if(editText.getText().toString().length()>5){
                        Toast toast = Toast.makeText(MainActivity.this, "品項名稱太長了", Toast.LENGTH_LONG);
                        toast.show();
                    }
                    else if(spinnerc.getSelectedItem().toString().matches("新增類別")||spinnerc.getSelectedItem().toString().matches("刪除類別")||spinnerc.getSelectedItem().toString().matches("更改預設")){
                        Toast toast = Toast.makeText(MainActivity.this, "請選擇類別", Toast.LENGTH_LONG);
                        toast.show();
                    }
                    else{
                        Fridgelist list = (Fridgelist)getApplicationContext();
                        Spinner spinnerf = (Spinner) view.findViewById(R.id.spinnerfridge);
                        Integer temp = 0;
                        for(int i = 0; i < list.fridgelist.size(); i++){
                            if(list.fridgelist.get(i).name.matches(spinnerf.getSelectedItem().toString())){
                                temp = i;
                                break;
                            }
                        }
                        EditText quan = (EditText)view.findViewById(R.id.quantity);
                        ToggleButton toggle = (ToggleButton) view.findViewById(R.id.toggle_button);
                        EditText ps = (EditText)view.findViewById(R.id.ps);
                        TextView date = (TextView)view.findViewById(R.id.date);
                        String line = (String) date.getText();
                        String[] split_line = line.split("/");
                        int yy = Integer.valueOf(split_line[0]);
                        int mm = Integer.valueOf(split_line[1]);
                        int dd =Integer.valueOf(split_line[2]);
                        DateFormat df = new SimpleDateFormat("yyyy/M/dd");
                        Calendar c = Calendar.getInstance();
                        int ny = c.get(Calendar.YEAR);
                        int nm = c.get(Calendar.MONTH);
                        int nd = c.get(Calendar.DAY_OF_MONTH);
                        String now = format("%d/%d/%d", ny, (nm+1), nd);
                        long days = 0;
                        try {
                            Date d1 = df.parse(line);
                            Date d2 = df.parse(now);
                            long diff = d1.getTime() - d2.getTime();
                            days = diff / (1000 * 60 * 60 * 24);
                        }
                        catch(ParseException e){
                            e.printStackTrace();
                        }
                        list.fridgelist.get(temp).add(editText.getText().toString(), Integer.valueOf(quan.getText().toString()),
                                spinnerc.getSelectedItem().toString(), (int)days, toggle.isChecked(), ps.getText().toString(), yy, mm-1, dd);
                        log.dismiss();
                        call_seefood(spinnerf.getSelectedItem().toString());
                    }
                }
            });

            log.show();
        }
    };

    class Sortbyorder implements Comparator<Fridgelist.Fridge.Category> {
        // Used for sorting in ascending order of
        // roll number
        public int compare(Fridgelist.Fridge.Category a, Fridgelist.Fridge.Category b) {
            return b.order - a.order;
        }
    }

    private void addsetCenterSpinner3(Spinner spinner1, int index, String fridge){
        Fridgelist list = (Fridgelist)getApplicationContext();
        Integer temp = 0;
        for(int i = 0; i < list.fridgelist.size(); i++){
            if(list.fridgelist.get(i).name.matches(fridge)){
                temp = i;
                break;
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                MainActivity.this,R.layout.spinnerforadd);
        adapter.setDropDownViewResource(R.layout.spinner_down);
        //Sort
        Collections.sort(list.fridgelist.get(temp).categorylist, new Sortbyorder());
        int selected = 0;
        for(int i = 0; i < list.fridgelist.get(temp).categorylist.size(); i++){
            if(list.fridgelist.get(temp).categorylist.get(i).category.matches(list.fridgelist.get(temp).foodlist.get(index).category)){
                adapter.add(list.fridgelist.get(temp).categorylist.get(i).category);
                selected = i;
                break;
            }
        }
        for(int i = 0; i < list.fridgelist.get(temp).categorylist.size(); i++) {
            if(i != selected && list.fridgelist.get(temp).categorylist.get(i).order!=-1) {
                adapter.add(list.fridgelist.get(temp).categorylist.get(i).category);
            }
        }
        spinner1.setAdapter(adapter);
    }

    private void addsetCenterSpinner2(Spinner spinner1, String fridge){
        Fridgelist list = (Fridgelist)getApplicationContext();
        Integer temp = 0;
        for(int i = 0; i < list.fridgelist.size(); i++){
            if(list.fridgelist.get(i).name.matches(fridge)){
                temp = i;
                break;
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                MainActivity.this,R.layout.spinnerforadd);
        adapter.setDropDownViewResource(R.layout.spinner_down);
        //Sort
        Collections.sort(list.fridgelist.get(temp).categorylist, new Sortbyorder());
        for(int i = 0; i < list.fridgelist.get(temp).categorylist.size(); i++) {
            adapter.add(list.fridgelist.get(temp).categorylist.get(i).category);
        }
        spinner1.setAdapter(adapter);
    }

    private void removeSpinner(Spinner spinner){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                MainActivity.this,R.layout.spinnerforadd);
        adapter.setDropDownViewResource(R.layout.spinner_down);
        spinner.setAdapter(adapter);
    }

    private void addsetCenterSpinner1(Spinner spinner1, String fridge){
        Fridgelist list = (Fridgelist)getApplicationContext();
        Integer temp = 0;
        for(int i = 0; i < list.fridgelist.size(); i++){
            if(list.fridgelist.get(i).name.matches(fridge)){
                temp = i;
                break;
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                MainActivity.this,R.layout.spinnerforadd);
        adapter.setDropDownViewResource(R.layout.spinner_down);
        //Sort
        Collections.sort(list.fridgelist.get(temp).categorylist, new Sortbyorder());
        for(int i = 0; i < list.fridgelist.get(temp).categorylist.size(); i++) {
            if(list.fridgelist.get(temp).categorylist.get(i).order != -1) {
                adapter.add(list.fridgelist.get(temp).categorylist.get(i).category);
            }
        }
        spinner1.setAdapter(adapter);
    }

    private void addsetCenterSpinner(Spinner spinner1, String fridge){
        Fridgelist list = (Fridgelist)getApplicationContext();
        Integer temp = 0;
        for(int i = 0; i < list.fridgelist.size(); i++){
            if(list.fridgelist.get(i).name.matches(fridge)){
                temp = i;
                break;
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                MainActivity.this,R.layout.spinnerforadd);
        adapter.setDropDownViewResource(R.layout.spinner_down);
        //Sort
        Collections.sort(list.fridgelist.get(temp).categorylist, new Sortbyorder());
        for(int i = 0; i < list.fridgelist.get(temp).categorylist.size(); i++) {
            if(list.fridgelist.get(temp).categorylist.get(i).order == 0) {
                adapter.add(list.fridgelist.get(temp).categorylist.get(i).category);
            }
        }
        spinner1.setAdapter(adapter);
    }

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
                    call_main();
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