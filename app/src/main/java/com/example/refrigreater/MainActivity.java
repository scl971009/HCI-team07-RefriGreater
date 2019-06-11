package com.example.refrigreater;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
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
        if (getIntent().hasExtra("fromNotification")) {
            call_recipe_search();
        }
        else {
            Fridgelist list = (Fridgelist) getApplicationContext();
            list.add_fridge("家", "1234567", "宇智波佐助");
            list.add_id(list.fridgelist.get(0).id, View.generateViewId());
            list.fridgelist.get(0).add("玉米", "主食", 5, true, "", 2019, 5, 17, true);
            list.fridgelist.get(0).add("起司", "蛋奶", 209, true, "", 2020, 0, 7, true);
            list.fridgelist.get(0).add("芒果", "水果", 0, true, "", 2019, 5, 12, true);
            list.fridgelist.get(0).add("鮮奶", "飲料", 0, false, "", 2019, 5, 12, true);
            list.fridgelist.get(0).add("高麗菜", "蔬菜", 3, true, "", 2019, 5, 15, true);
            list.fridgelist.get(0).add("橘子", "水果", 4, true, "", 2019, 5, 16, true);
            list.fridgelist.get(0).add("雞胸肉", "魚肉", 7, false, "", 2019, 5, 19, true);
            list.fridgelist.get(0).add("義大利麵", "主食", 14, true, "", 2019, 5, 26, true);
            list.fridgelist.get(0).add("蛋", "蛋奶", 16, true, "", 2019, 5, 28, true);
            list.fridgelist.get(0).add("果凍", "其他", 21, false, "", 2019, 6, 3, true);
            list.fridgelist.get(0).add("鰻魚", "魚肉", 111, true, "", 2019, 9, 1, true);
            list.fridgelist.get(0).add("叉燒肉", "魚肉", 13, true, "", 2019, 5, 25, false);
            list.fridgelist.get(0).add("溏心蛋", "蛋奶", 3, true, "", 2019, 5, 15, false);
            list.fridgelist.get(0).addcategory("主食", 1, true);
            list.fridgelist.get(0).addcategory("水果", 0, true);
            list.fridgelist.get(0).addcategory("飲料", 0, true);
            list.fridgelist.get(0).addcategory("蔬菜", 0, true);
            list.fridgelist.get(0).addcategory("魚肉", 0, true);
            list.fridgelist.get(0).addcategory("蛋奶", 0, true);
            list.fridgelist.get(0).addcategory("其他", 0, true);
            list.fridgelist.get(0).addcategory("更改預設", -1, true);
            list.fridgelist.get(0).addcategory("新增類別", -1, true);
            list.fridgelist.get(0).addcategory("刪除類別", -1, true);
            list.fridgelist.get(0).addMap("全選", View.generateViewId());
            list.fridgelist.get(0).addMap("主食", View.generateViewId());
            list.fridgelist.get(0).addMap("水果", View.generateViewId());
            list.fridgelist.get(0).addMap("飲料", View.generateViewId());
            list.fridgelist.get(0).addMap("蔬菜", View.generateViewId());
            list.fridgelist.get(0).addMap("魚肉", View.generateViewId());
            list.fridgelist.get(0).addMap("蛋奶", View.generateViewId());
            list.fridgelist.get(0).addMap("其他", View.generateViewId());


            call_main();
        }
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

    public void mainsetsumei(View view){
        ImageView setsumei = (ImageView)findViewById(R.id.setsumei);
        ToggleButton togglebutton = (ToggleButton)findViewById(R.id.mainq);
        if(togglebutton.isChecked()){
            setsumei.setVisibility((View.VISIBLE));
        }
        else{
            setsumei.setVisibility(View.GONE);
        }
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

        Button searchbar = (Button)findViewById(R.id.searchbar);
        searchbar.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String id = "id";
                NotificationManager notificationManger;
                notificationManger = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                Intent notificationIntent = new Intent(MainActivity.this, MainActivity.class);
                notificationIntent.putExtra("fromNotification", true);
                PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, notificationIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                Notification notification;
                notification = new Notification.Builder(MainActivity.this)
                        .setSmallIcon(R.drawable.app_iconnn)
                        .setContentTitle("到期通知")
                        .setContentText("芒果快要到期了")
                        .setContentIntent(pendingIntent)
                        .build();
                notification.flags = Notification.FLAG_AUTO_CANCEL;
                notification.priority = Notification.PRIORITY_HIGH;
                notification.defaults |= Notification.DEFAULT_SOUND;
                notificationManger.notify(1, notification);
                return false;
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

    private void addsetCenterSpinner4(Spinner spinner, String fridge){
        Fridgelist list = (Fridgelist)getApplicationContext();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                MainActivity.this,R.layout.spinnerforadd);
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
            if(a.nother == b.nother) {
                return a.expire - b.expire;
            }
            else{
                if(!a.nother){
                    return 1;
                }
                else{
                    return -1;
                }
            }
        }
    }

    public void into_recipe(View view){
        call_recipe_ingredient();
    }

    private void setBtnAttribute(Button codeBtn, Fridgelist.Fridge.Food list, int index, int hash, final String fridge) {
        codeBtn.setId(View.generateViewId());
        if(list.nother) {
            if (list.expire < 3) {
                codeBtn.setBackground(getResources().getDrawable(R.drawable.backred, null));
            } else if (list.expire > 5) {
                codeBtn.setBackground(getResources().getDrawable(R.drawable.backgreen, null));
            } else {
                codeBtn.setBackground(getResources().getDrawable(R.drawable.backyellow, null));
            }
        }
        else{
            codeBtn.setBackground(getResources().getDrawable(R.drawable.backblack, null));
        }
        String text = list.name;
        for (int i = 0; i < 5 - list.name.length(); i++) {
            text += "　";
        }
        //text += String.format("%4d\t%6d\t%s", btnContentList.quantity, btnContentList.expire, "天");
        text += "    ";
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
        if(list.nother) {
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
        }
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

    private void resetBtnAttribute(Button codeBtn, Fridgelist.Fridge.Food list, int index, int hash, final int special, final String fridge) {
        if(hash != special) {
            codeBtn.setId(View.generateViewId());
            if(list.nother) {
                if (list.expire < 3) {
                    codeBtn.setBackground(getResources().getDrawable(R.drawable.backred, null));
                } else if (list.expire > 5) {
                    codeBtn.setBackground(getResources().getDrawable(R.drawable.backgreen, null));
                } else {
                    codeBtn.setBackground(getResources().getDrawable(R.drawable.backyellow, null));
                }
            }
            else{
                codeBtn.setBackground(getResources().getDrawable(R.drawable.backblack, null));
            }
            String text = list.name;
            for (int i = 0; i < 5 - list.name.length(); i++) {
                text += "　";
            }
            //text += String.format("%4d\t%6d\t%s", btnContentList.quantity, btnContentList.expire, "天");
            text += "    ";
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

            if(list.nother) {
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
            }
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
                            call_seefood(fridge);
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

            recipe.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    call_recipe_search();
                }
            });

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
                    addsetCenterSpinner4(spinner, fridgeout);
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
                    TextView date = (TextView) view.findViewById(R.id.date);
                    String today = format("%d/%d/%d", list.fridgelist.get(temp).foodlist.get(index).year, list.fridgelist.get(temp).foodlist.get(index).month+1, list.fridgelist.get(temp).foodlist.get(index).day);
                    date.setText(today);
                    TextView dayleft = (TextView)view.findViewById(R.id.until);
                    dayleft.setText("("+list.fridgelist.get(temp).foodlist.get(index).expire+"天後)");
                    ToggleButton togglebutton = (ToggleButton) view.findViewById(R.id.toggle_button);
                    togglebutton.setChecked(list.fridgelist.get(temp).foodlist.get(index).pri0pub1);

                    // if button is clicked, close the custom dialog

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
                                    String line = (String) txt.getText();
                                    String[] split_line = line.split("/");
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
                                    TextView daysleft = (TextView)view.findViewById(R.id.until);
                                    daysleft.setText("("+days+"天後)");
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
                                    list.fridgelist.get(temp).change(indexinside, editText.getText().toString(),
                                            spinnerc.getSelectedItem().toString(), (int) days, toggle.isChecked(), ps.getText().toString(), yy, mm - 1, dd, true);
                                }
                                else{
                                    list.fridgelist.get(temp).delete(indexinside);
                                    for (int i = 0; i < list.fridgelist.size(); i++) {
                                        if (list.fridgelist.get(i).name.matches(spinnerf.getSelectedItem().toString())) {
                                            temp = i;
                                            break;
                                        }
                                    }
                                    list.fridgelist.get(temp).add(editText.getText().toString(),
                                            spinnerc.getSelectedItem().toString(), (int) days, toggle.isChecked(), ps.getText().toString(), yy, mm - 1, dd, true);
                                }
                                log.dismiss();
                                call_seefood(fridge);
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
            linear.addView(contain);
        }
    }

    public void mem(View view){
        Spinner spinner = findViewById(R.id.spinner);
        final String fridge = spinner.getSelectedItem().toString();
        setContentView(R.layout.fridgemem);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        TextView fridge_name = new TextView(MainActivity.this);
        fridge_name.setId(View.generateViewId());
        fridge_name.setText(fridge);
        fridge_name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        fridge_name.setGravity(Gravity.CENTER);
        constraintLayout = (android.support.constraint.ConstraintLayout) findViewById(R.id.container);
        constraintLayout.addView(fridge_name);
        applyConstraintSet.clone(constraintLayout);
        applyConstraintSet.connect(fridge_name.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP, 291);
        applyConstraintSet.connect(fridge_name.getId(), ConstraintSet.LEFT, constraintLayout.getId(), ConstraintSet.LEFT, 165);
        applyConstraintSet.constrainHeight(fridge_name.getId(), 72);
        applyConstraintSet.constrainWidth(fridge_name.getId(), 270);
        applyConstraintSet.applyTo(constraintLayout);

        TextView id = new TextView(MainActivity.this);
        id.setId(View.generateViewId());
        String id_t = "id";
        String username = "";
        final Fridgelist list = (Fridgelist)getApplication();
        for(int i = 0; i < list.fridgelist.size(); i++){
            if(list.fridgelist.get(i).name.matches(fridge)){
                id_t = list.fridgelist.get(i).id;
                username = list.fridgelist.get(i).user;
                break;
            }
        }
        String whole = "(ID:"+id_t+")";
        id.setText(whole);
        id.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        id.setGravity(Gravity.CENTER);
        constraintLayout = (android.support.constraint.ConstraintLayout) findViewById(R.id.container);
        constraintLayout.addView(id);
        applyConstraintSet.clone(constraintLayout);
        applyConstraintSet.connect(id.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP, 302);
        applyConstraintSet.connect(id.getId(), ConstraintSet.LEFT, constraintLayout.getId(), ConstraintSet.LEFT, 435);
        applyConstraintSet.constrainHeight(id.getId(), 57);
        applyConstraintSet.constrainWidth(id.getId(), 255);
        applyConstraintSet.applyTo(constraintLayout);

        if(id_t.matches("1234567")){
            ImageView homemember = new ImageView(MainActivity.this);
            homemember.setId(View.generateViewId());
            homemember.setBackground(getResources().getDrawable(R.drawable.homemem, null));
            constraintLayout = (android.support.constraint.ConstraintLayout) findViewById(R.id.container);
            constraintLayout.addView(homemember);
            applyConstraintSet.clone(constraintLayout);
            applyConstraintSet.connect(homemember.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP, 535);
            applyConstraintSet.connect(homemember.getId(), ConstraintSet.LEFT, constraintLayout.getId(), ConstraintSet.LEFT, 84);
            applyConstraintSet.constrainHeight(homemember.getId(), 819);
            applyConstraintSet.constrainWidth(homemember.getId(), 885);
            applyConstraintSet.applyTo(constraintLayout);
        }
        else if(id_t.matches("c876387")){
            ImageView sao = new ImageView(MainActivity.this);
            sao.setId(View.generateViewId());
            sao.setBackground(getResources().getDrawable(R.drawable.sao, null));
            constraintLayout = (android.support.constraint.ConstraintLayout) findViewById(R.id.container);
            constraintLayout.addView(sao);
            applyConstraintSet.clone(constraintLayout);
            applyConstraintSet.connect(sao.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP, 535);
            applyConstraintSet.connect(sao.getId(), ConstraintSet.LEFT, constraintLayout.getId(), ConstraintSet.LEFT, 84);
            applyConstraintSet.constrainHeight(sao.getId(), 423);
            applyConstraintSet.constrainWidth(sao.getId(), 885);
            applyConstraintSet.applyTo(constraintLayout);
        }
        else{
            ImageView newf = new ImageView(MainActivity.this);
            newf.setId(View.generateViewId());
            newf.setBackground(getResources().getDrawable(R.drawable.newfridgemem, null));
            constraintLayout = (android.support.constraint.ConstraintLayout) findViewById(R.id.container);
            constraintLayout.addView(newf);
            applyConstraintSet.clone(constraintLayout);
            applyConstraintSet.connect(newf.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP, 535);
            applyConstraintSet.connect(newf.getId(), ConstraintSet.LEFT, constraintLayout.getId(), ConstraintSet.LEFT, 84);
            applyConstraintSet.constrainHeight(newf.getId(), 225);
            applyConstraintSet.constrainWidth(newf.getId(), 885);
            applyConstraintSet.applyTo(constraintLayout);
        }

        TextView user = new TextView(MainActivity.this);
        user.setId(View.generateViewId());
        user.setText(username);
        user.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        user.setGravity(Gravity.START);
        user.setTextColor(getResources().getColor(R.color.mem, null));
        constraintLayout = (android.support.constraint.ConstraintLayout) findViewById(R.id.container);
        constraintLayout.addView(user);
        applyConstraintSet.clone(constraintLayout);
        applyConstraintSet.connect(user.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP, 649);
        applyConstraintSet.connect(user.getId(), ConstraintSet.LEFT, constraintLayout.getId(), ConstraintSet.LEFT, 294);
        applyConstraintSet.constrainHeight(user.getId(), 75);
        applyConstraintSet.constrainWidth(user.getId(), 270);
        applyConstraintSet.applyTo(constraintLayout);

        Button back = (Button)findViewById(R.id.btnback);
        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                call_seefood(fridge);
            }
        });

        Button quit = (Button)findViewById(R.id.btnquit);
        quit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                final View v = getLayoutInflater().inflate(R.layout.confirmquit, null);

                Button cancel = (Button) v.findViewById(R.id.btncancel);
                Button confirm = (Button) v.findViewById(R.id.btnconfirm);
                TextView text = (TextView)v.findViewById(R.id.quitwhat);
                text.setText(fridge+"?");

                dialog.setView(v);
                final AlertDialog log = dialog.create();


                cancel.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        log.dismiss();
                    }
                });

                confirm.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int i = 0;
                        for(i = 0; i < list.fridgelist.size(); i++){
                            if(list.fridgelist.get(i).name.matches(fridge))
                                break;
                        }
                        list.delete_fridge(i);
                        log.dismiss();
                        call_main();
                    }
                });

                log.show();
                WindowManager.LayoutParams params = log.getWindow().getAttributes();
                params.height = 595;
                params.width = 790;
                log.getWindow().setAttributes(params);
            }
        });
    }

    public void call_recipe_search(){
        Fridgelist list = (Fridgelist)getApplicationContext();
        setContentView(R.layout.recipe_search);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Button star = (Button)findViewById(R.id.shakestar);
        if(list.shake){
            star.setBackground(getResources().getDrawable(R.drawable.star, null));
        }
        else{
            star.setBackground(getDrawable(R.drawable.unstar));
        }
        Button shake = (Button)findViewById(R.id.shake);
        shake.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                call_recipe_ingredient();
            }
        });
        Button ice = (Button)findViewById(R.id.ice);
        ice.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                call_recipe_ingredient();
            }
        });
    }

    public void call_recipe_ingredient(){
        final Fridgelist list = (Fridgelist)getApplicationContext();
        setContentView(R.layout.recipe_ingredient);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Button star = (Button)findViewById(R.id.star);
        if(list.shake){
            star.setBackground(getDrawable(R.drawable.bigstar));
        }
        else{
            star.setBackground(getDrawable(R.drawable.bigunstar));
        }
        star.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                list.change_shake();
                if(list.shake){
                    v.setBackground(getDrawable(R.drawable.bigstar));
                }
                else{
                    v.setBackground(getDrawable(R.drawable.bigunstar));
                }
            }
        });

        Button back = (Button)findViewById(R.id.btnback);
        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                call_recipe_search();
            }
        });

        ToggleButton toggle = (ToggleButton)findViewById(R.id.switchiands);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    call_recipe_step();
                }
                else{
                    call_recipe_ingredient();
                }
            }
        });
    }

    public void call_recipe_step(){
        final Fridgelist list = (Fridgelist)getApplicationContext();
        setContentView(R.layout.recipe_step);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Button back = (Button)findViewById(R.id.btnback);
        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                call_recipe_search();
            }
        });

        Button star = (Button)findViewById(R.id.star);
        if(list.shake){
            star.setBackground(getDrawable(R.drawable.bigstar));
        }
        else{
            star.setBackground(getDrawable(R.drawable.bigunstar));
        }
        star.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                list.change_shake();
                if(list.shake){
                    v.setBackground(getDrawable(R.drawable.bigstar));
                }
                else{
                    v.setBackground(getDrawable(R.drawable.bigunstar));
                }
            }
        });

        ToggleButton toggle = (ToggleButton)findViewById(R.id.switchiands);
        toggle.setChecked(true);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    call_recipe_step();
                }
                else{
                    call_recipe_ingredient();
                }
            }
        });
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
        if(linear.getVisibility() == View.GONE) {
            linear.setVisibility(View.VISIBLE);
            linear.bringToFront();
            linear.setTranslationZ(10);
        }
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
        checkBox.bringToFront();
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

    public void join_fridge(View view){
        final Fridgelist list = (Fridgelist)getApplicationContext();
        if(list.fridgelist.size() == 2){
            Toast toast = Toast.makeText(MainActivity.this, "最多只能擁有兩個冰箱", Toast.LENGTH_LONG);
            toast.show();
        }
        else{
            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
            final View v = getLayoutInflater().inflate(R.layout.joinfridge, null);

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
                    EditText code = (EditText) v.findViewById(R.id.shiriaru);
                    String shiriaru = code.getText().toString();
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
                    else if(!shiriaru.matches("c876387")){
                        Toast toast = Toast.makeText(MainActivity.this, "查無此冰箱", Toast.LENGTH_LONG);
                        toast.show();
                    }
                    else {
                        String id = "c876387";
                        list.add_fridge(fridge_name, id, user_name);
                        list.add_id(list.fridgelist.get(list.fridgelist.size() - 1).id, View.generateViewId());
                        list.fridgelist.get(list.fridgelist.size() - 1).addcategory("預設", 1, true);
                        list.fridgelist.get(list.fridgelist.size() - 1).addcategory("更改預設", -1, true);
                        list.fridgelist.get(list.fridgelist.size() - 1).addcategory("新增類別", -1, true);
                        list.fridgelist.get(list.fridgelist.size() - 1).addcategory("刪除類別", -1, true);
                        list.fridgelist.get(list.fridgelist.size() - 1).addMap("全選", View.generateViewId());
                        list.fridgelist.get(list.fridgelist.size() - 1).addMap("預設", View.generateViewId());
                        list.fridgelist.get(list.fridgelist.size() - 1).add("兔肉", "預設", 87, true, "", 2019, 8, 7, true);
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
            addsetCenterSpinner4(spinner, fridge);
            TextView date = (TextView) view.findViewById(R.id.date);
            Calendar mCal = Calendar.getInstance();
            String dateformat = "yyyy/M/dd";
            SimpleDateFormat df = new SimpleDateFormat(dateformat);
            String today = df.format(mCal.getTime());
            date.setText(today);
            TextView dayleft = (TextView)view.findViewById(R.id.until);
            dayleft.setText("(0天後)");
            final Spinner spinner2 = (Spinner) view.findViewById(R.id.spinnercategory);
            addsetCenterSpinner2(spinner2, spinner.getSelectedItem().toString());
            ToggleButton togglebutton = (ToggleButton) view.findViewById(R.id.toggle_button);
            togglebutton.setChecked(false);

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
                            String line = (String) txt.getText();
                            String[] split_line = line.split("/");
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
                            TextView daysleft = (TextView)view.findViewById(R.id.until);
                            daysleft.setText("("+days+"天後)");
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
                        list.fridgelist.get(temp).add(editText.getText().toString(),
                                spinnerc.getSelectedItem().toString(), (int)days, toggle.isChecked(), ps.getText().toString(), yy, mm-1, dd, true);
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
                    setContentView(R.layout.recipe);
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