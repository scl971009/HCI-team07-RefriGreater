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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.text.ParseException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

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

        Button seefoodBtn = (Button) findViewById(R.id.btnfridge);
        seefoodBtn.setOnClickListener(seefoodbtnlistener);

        Foodlist list = (Foodlist) getApplicationContext();
        list.add("家", "玉米", 73, "預設", 5, false, "", 2019, 6, 27);
        list.add("家", "起司", 200, "預設", 1000, true, "", 2019, 11, 1);
        list.add("家", "頂級蒲燒鰻", 1, "預設", 0, true, "", 2019, 4, 15);
        list.addcategory("預設", 1, true);
        list.addcategory("更改預設", -1, true);
        list.addcategory("新增類別", -1, true);
        list.addcategory("刪除類別", -1, true);
        list.addMap("全選", View.generateViewId());
        list.addMap("預設", View.generateViewId());
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // Show home screen when pressing “back” button,
            //  so that this app won’t be closed accidentally
            setContentView(R.layout.activity_toolbar);
            BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
            navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
            Button seefoodBtn = (Button) findViewById(R.id.btnfridge);
            seefoodBtn.setOnClickListener(seefoodbtnlistener);
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

    public void generateCheckboxList(){
        Foodlist list = (Foodlist) getApplicationContext();

        if (list.categorylist.size() == 3) {
            return;
        }

        CheckBox checkBoxAll = new CheckBox(this);
        setAll(checkBoxAll, "全選");

        Collections.sort(list.categorylist, new Sortbyorder());
        for (int index = 0; index < list.categorylist.size(); index++) {
            CheckBox checkBox = new CheckBox(this);
            if(list.categorylist.get(index).order != -1) {
                setCheckBoxAttribute(checkBox, list.categorylist.get(index), index);
            }
        }
    }

    public void setAll(final CheckBox checkBox, String name){
        Foodlist btnContentList = (Foodlist) getApplicationContext();
        for(int i = 0; i < btnContentList.Map.size(); i++){
            if(btnContentList.Map.get(i).category.matches(name))
                checkBox.setId(btnContentList.Map.get(i).id);
        }
        boolean flag = true;
        for(int i = 0; i < btnContentList.categorylist.size(); i++){
            if(!btnContentList.categorylist.get(i).onoff){
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
                Foodlist list = (Foodlist) getApplicationContext();
                boolean flag = true;
                for(int i = 1; i < list.Map.size(); i++){
                    CheckBox checkBox1 = (CheckBox) findViewById(list.Map.get(i).id);
                    if(!checkBox1.isChecked()){
                        flag = false;
                        break;
                    }
                }
                if(isChecked){
                    for(int i = 0; i < list.Map.size(); i++){
                        CheckBox checkBox1 = (CheckBox) findViewById(list.Map.get(i).id);
                        checkBox1.setChecked(true);
                    }
                }
                else if(flag){
                    for(int i = 0; i < list.Map.size(); i++){
                        CheckBox checkBox1 = (CheckBox) findViewById(list.Map.get(i).id);
                        checkBox1.setChecked(false);
                    }
                }
                LinearLayout ll = (LinearLayout) findViewById(R.id.mylinear);
                ll.removeAllViews();
                generateBtnList();
            }
        });

        LinearLayout linear = (LinearLayout) findViewById(R.id.filter);
        linear.addView(checkBox);
    }

    public void setCheckBoxAttribute(final CheckBox checkBox, final Foodlist.Category category, int index){
        Foodlist list = (Foodlist) getApplicationContext();
        for(int i = 0; i < list.Map.size(); i++){
            if(list.Map.get(i).category.matches(category.category))
                checkBox.setId(list.Map.get(i).id);
        }
        checkBox.setTag(index + "category");
        for(int i = 0; i < list.categorylist.size(); i++){
            if(list.categorylist.get(i).category.matches(category.category)){
                if(list.categorylist.get(i).onoff){
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
                Foodlist list = (Foodlist) getApplicationContext();
                if(isChecked){
                    boolean flag = true;
                    for(int i = 1; i < list.Map.size(); i++){
                        CheckBox checkBox1 = (CheckBox) findViewById(list.Map.get(i).id);
                        if(!checkBox1.isChecked()){
                            flag = false;
                            break;
                        }
                    }
                    if(flag){
                        CheckBox checkBox1 = (CheckBox) findViewById(list.Map.get(0).id);
                        checkBox1.setChecked(true);
                    }
                    for(int i = 0; i < list.categorylist.size(); i++){
                        if(list.categorylist.get(i).category.matches(buttonView.getText().toString()))
                            list.categorylist.get(i).onoff = true;
                    }
                }
                else{
                    CheckBox checkBox1 = (CheckBox) findViewById(list.Map.get(0).id);
                    if(checkBox1.isChecked()){
                        checkBox1.setChecked(false);
                    }
                    for(int i = 0; i < list.categorylist.size(); i++){
                        if(list.categorylist.get(i).category.matches(buttonView.getText().toString()))
                            list.categorylist.get(i).onoff = false;
                    }
                }
                LinearLayout ll = (LinearLayout) findViewById(R.id.mylinear);
                ll.removeAllViews();
                generateBtnList();
            }
        });

        LinearLayout linear = (LinearLayout) findViewById(R.id.filter);
        linear.addView(checkBox);
    }

    public void onTargetClick(View view) {
        TextView text_id = (TextView) findViewById(R.id.textid);
        ToggleButton togglebutton = (ToggleButton) findViewById(R.id.toggle_button);
        //ToggleButton toggleButton = (ToggleButton) view;
        if (togglebutton.isChecked()) {
            text_id.setVisibility(View.VISIBLE);
        } else {
            text_id.setVisibility(View.GONE);
        }
    }

    private Button.OnClickListener seefoodbtnlistener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            setContentView(R.layout.activity_see_food);
            BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
            navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
            Button backBtn = (Button) findViewById(R.id.btnback);
            backBtn.setOnClickListener(backwardbtnlistener);
            Button plusBtn = (Button) findViewById(R.id.btnplus);
            plusBtn.setOnClickListener(plusfoodbtnlistener);
            Spinner spinner = (Spinner) findViewById(R.id.spinner);
            setCenterSpinner(spinner);
            Foodlist list = (Foodlist) getApplicationContext();
            for(int i = 0; i < list.categorylist.size(); i++)
                list.categorylist.get(i).onoff = true;
            generateBtnList();
            generateCheckboxList();
            plusBtn.bringToFront();
        }
    };

    class Sortbyexpire implements Comparator<Foodlist.Food> {
        // Used for sorting in ascending order of
        // roll number
        public int compare(Foodlist.Food a, Foodlist.Food b) {
            return a.expire - b.expire;
        }
    }

    private void regenerateBtnList(int special) {
        Foodlist btnContentList = (Foodlist) getApplicationContext();

        if (null == btnContentList.foodlist) {
            return;
        }

        Collections.sort(btnContentList.foodlist, new Sortbyexpire());
        int count = 0;
        for (int index = 0; index < btnContentList.foodlist.size(); index++) {
            for(int i = 0; i < btnContentList.categorylist.size(); i++){
                if(btnContentList.categorylist.get(i).category.matches(btnContentList.foodlist.get(index).category)){
                    if(btnContentList.categorylist.get(i).onoff){
                        Button codeBtn = new Button(this);
                        resetBtnAttribute(codeBtn, btnContentList.foodlist.get(index), count, index, special);
                        count++;
                    }
                    break;
                }
            }
        }
    }

    private void generateBtnList() {
        Foodlist btnContentList = (Foodlist) getApplicationContext();

        if (null == btnContentList.foodlist) {
            return;
        }

        Collections.sort(btnContentList.foodlist, new Sortbyexpire());
        int count = 0;
        for (int index = 0; index < btnContentList.foodlist.size(); index++) {
            for(int i = 0; i < btnContentList.categorylist.size(); i++){
                if(btnContentList.categorylist.get(i).category.matches(btnContentList.foodlist.get(index).category)){
                    if(btnContentList.categorylist.get(i).onoff){
                        Button codeBtn = new Button(this);
                        setBtnAttribute(codeBtn, btnContentList.foodlist.get(index), count, index);
                        count++;
                    }
                    break;
                }
            }
        }
    }

    private void resetBtnAttribute(Button codeBtn, Foodlist.Food btnContentList, int index, int hash, int special) {
        if(hash != special) {
            codeBtn.setId(View.generateViewId());
            if (btnContentList.expire < 3) {
                codeBtn.setBackground(getResources().getDrawable(R.drawable.backred, null));
            } else if (btnContentList.expire > 5) {
                codeBtn.setBackground(getResources().getDrawable(R.drawable.backgreen, null));
            } else {
                codeBtn.setBackground(getResources().getDrawable(R.drawable.backyellow, null));
            }
            String text = btnContentList.name;
            for (int i = 0; i < 5 - btnContentList.name.length(); i++) {
                text += "　";
            }
            //text += String.format("%4d\t%6d\t%s", btnContentList.quantity, btnContentList.expire, "天");
            if (btnContentList.quantity > 999) {
                text += btnContentList.quantity;
            } else if (btnContentList.quantity > 99) {
                text += " ";
                text += btnContentList.quantity;
            } else if (btnContentList.quantity > 9) {
                text += "  ";
                text += btnContentList.quantity;
            } else if (btnContentList.quantity > -1) {
                text += "   ";
                text += btnContentList.quantity;
            } else if (btnContentList.quantity > -10) {
                text += "  ";
                text += btnContentList.quantity;
            } else if (btnContentList.quantity > -100) {
                text += " ";
                text += btnContentList.quantity;
            } else {
                text += btnContentList.quantity;
            }
            //text+="　";
            if (btnContentList.expire > 9999) {
                text += btnContentList.expire;
            } else if (btnContentList.expire > 999) {
                text += " ";
                text += btnContentList.expire;
            } else if (btnContentList.expire > 99) {
                text += "  ";
                text += btnContentList.expire;
            } else if (btnContentList.expire > 9) {
                text += "   ";
                text += btnContentList.expire;
            } else if (btnContentList.expire > -1) {
                text += "    ";
                text += btnContentList.expire;
            } else if (btnContentList.expire > -10) {
                text += "   ";
                text += btnContentList.expire;
            } else if (btnContentList.expire > -100) {
                text += "  ";
                text += btnContentList.expire;
            } else if (btnContentList.expire > -1000) {
                text += " ";
                text += btnContentList.expire;
            } else {
                text += btnContentList.expire;
            }
            text += "天";
            codeBtn.setText(text);
            Typeface font = Typeface.createFromAsset(getAssets(), "fonts/RobotoMono-Regular.ttf");
            codeBtn.setTypeface(font);
            codeBtn.setTag(hash);
            codeBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 21);
            codeBtn.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
            if (!btnContentList.pri0pub1) {
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
                    int[] location = new int[2];
                    Foodlist list = (Foodlist) getApplicationContext();
                    if (v instanceof Button) {
                        if (((Button) v).getTag() instanceof Integer) {
                            index = Integer.parseInt(((Button) v).getTag().toString());
                        }
                        setContentView(R.layout.activity_see_food);
                        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
                        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
                        Button backBtn = (Button) findViewById(R.id.btnback);
                        backBtn.setOnClickListener(backwardbtnlistener);
                        Button plusBtn = (Button) findViewById(R.id.btnplus);
                        plusBtn.setOnClickListener(plusfoodbtnlistener);
                        Spinner spinner = (Spinner) findViewById(R.id.spinner);
                        setCenterSpinner(spinner);
                        regenerateBtnList(index);
                        generateCheckboxList();
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
            if (btnContentList.expire < 3) {
                codeBtn.setBackground(getResources().getDrawable(R.drawable.longpress1, null));
                recipe.setBackground(getResources().getDrawable(R.drawable.longpress2, null));
                edit.setBackground(getResources().getDrawable(R.drawable.longpress3, null));
                delete.setBackground(getResources().getDrawable(R.drawable.longpress4, null));
            } else if (btnContentList.expire > 5) {
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
            String text = btnContentList.name;
            codeBtn.setText(text);
            Typeface font = Typeface.createFromAsset(getAssets(), "fonts/RobotoMono-Regular.ttf");
            codeBtn.setTypeface(font);
            codeBtn.setTag(hash);
            codeBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 21);
            codeBtn.setGravity(Gravity.CENTER_VERTICAL);
            if (!btnContentList.pri0pub1) {
                codeBtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.privateicon, 0, 0, 0);
                codeBtn.setPadding(36, 0, 0, 0);
                codeBtn.setCompoundDrawablePadding(27);
            } else {
                codeBtn.setPadding(159, 0, 0, 0);
            }

            codeBtn.setOnLongClickListener(new OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Foodlist list = (Foodlist) getApplicationContext();
                    setContentView(R.layout.activity_see_food);
                    BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
                    navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
                    Button backBtn = (Button) findViewById(R.id.btnback);
                    backBtn.setOnClickListener(backwardbtnlistener);
                    Button plusBtn = (Button) findViewById(R.id.btnplus);
                    plusBtn.setOnClickListener(plusfoodbtnlistener);
                    Spinner spinner = (Spinner) findViewById(R.id.spinner);
                    setCenterSpinner(spinner);
                    generateBtnList();
                    generateCheckboxList();
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
                            Foodlist list = (Foodlist)getApplicationContext();
                            if(viewn instanceof Button) {
                                if (((Button) viewn).getTag() instanceof Integer) {
                                    index = Integer.parseInt(((Button) viewn).getTag().toString());
                                }
                            }
                            list.delete(index);
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
                            generateBtnList();
                            generateCheckboxList();
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
                    final Foodlist list = (Foodlist)getApplicationContext();
                    Integer index = 0;
                    if(viewn instanceof Button) {
                        if (((Button) viewn).getTag() instanceof Integer) {
                            index = Integer.parseInt(((Button) viewn).getTag().toString());
                        }
                    }
                    AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                    final View view = getLayoutInflater().inflate(R.layout.editfood, null);

                    // set the custom dialog components - text, image and button
                    final Spinner spinner = (Spinner) view.findViewById(R.id.spinnerfridge);
                    //addsetCenterSpinner(spinner);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, R.layout.spinnerforadd, getResources().getStringArray(R.array.languages));
                    adapter.setDropDownViewResource(R.layout.spinner_down);
                    spinner.setAdapter(adapter);
                    EditText utxtValue = (EditText) view.findViewById(R.id.username);
                    utxtValue.setText(list.foodlist.get(index).name);
                    EditText txtValue = (EditText) view.findViewById(R.id.quantity);
                    txtValue.setText(String.format("%d", list.foodlist.get(index).quantity));
                    TextView date = (TextView) view.findViewById(R.id.date);
                    String today = String.format("%d/%d/%d", list.foodlist.get(index).year, list.foodlist.get(index).month+1, list.foodlist.get(index).day);
                    date.setText(today);
                    Spinner spinner2 = (Spinner) view.findViewById(R.id.spinnercategory);
                    addsetCenterSpinner3(spinner2, index);
                    ToggleButton togglebutton = (ToggleButton) view.findViewById(R.id.toggle_button);
                    togglebutton.setChecked(list.foodlist.get(index).pri0pub1);

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
                            text.setText(String.format("%d", num));
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
                                    txt.setText(String.format("%d/%d/%d",year,  month+1, dayOfMonth));
                                }
                            }, list.foodlist.get(indexinside).year, list.foodlist.get(indexinside).month, list.foodlist.get(indexinside).day);
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
                            if(editText.getText().toString().matches("")){
                                Toast toast = Toast.makeText(MainActivity.this, "請輸入品項", Toast.LENGTH_LONG);
                                toast.show();
                            }
                            else if(editText.getText().toString().length()>5){
                                Toast toast = Toast.makeText(MainActivity.this, "品項名稱太長了", Toast.LENGTH_LONG);
                                toast.show();
                            }
                            else{
                                Foodlist list = (Foodlist)getApplicationContext();
                                Spinner spinnerf = (Spinner) view.findViewById(R.id.spinnerfridge);
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
                                String now = String.format("%d/%d/%d", ny, (nm+1), nd);
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
                                list.change(indexinside, spinnerf.getSelectedItem().toString(), editText.getText().toString(), Integer.valueOf(quan.getText().toString()),
                                        spinnerc.getSelectedItem().toString(), (int)days, toggle.isChecked(), ps.getText().toString(), yy, mm-1, dd);
                                log.dismiss();
                                setContentView(R.layout.activity_see_food);
                                BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
                                navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
                                Button backBtn = (Button) findViewById(R.id.btnback);
                                backBtn.setOnClickListener(backwardbtnlistener);
                                Button plusBtn = (Button) findViewById(R.id.btnplus);
                                plusBtn.setOnClickListener(plusfoodbtnlistener);
                                Spinner spinner = (Spinner) findViewById(R.id.spinner);
                                setCenterSpinner(spinner);
                                for(int i = 0; i < list.categorylist.size(); i++)
                                    list.categorylist.get(i).onoff = true;
                                generateBtnList();
                                generateCheckboxList();
                                plusBtn.bringToFront();
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
            if(btnContentList.expire<3) {
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
            else if(btnContentList.expire>5){
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

    private void setBtnAttribute(Button codeBtn, Foodlist.Food btnContentList, int index, int hash) {
        codeBtn.setId(View.generateViewId());
        if (btnContentList.expire < 3) {
            codeBtn.setBackground(getResources().getDrawable(R.drawable.backred, null));
        } else if (btnContentList.expire > 5) {
            codeBtn.setBackground(getResources().getDrawable(R.drawable.backgreen, null));
        } else {
            codeBtn.setBackground(getResources().getDrawable(R.drawable.backyellow, null));
        }
        String text = btnContentList.name;
        for (int i = 0; i < 5 - btnContentList.name.length(); i++) {
            text += "　";
        }
        //text += String.format("%4d\t%6d\t%s", btnContentList.quantity, btnContentList.expire, "天");
        if (btnContentList.quantity > 999) {
            text += btnContentList.quantity;
        } else if (btnContentList.quantity > 99) {
            text += " ";
            text += btnContentList.quantity;
        } else if (btnContentList.quantity > 9) {
            text += "  ";
            text += btnContentList.quantity;
        } else if (btnContentList.quantity > -1) {
            text += "   ";
            text += btnContentList.quantity;
        } else if (btnContentList.quantity > -10) {
            text += "  ";
            text += btnContentList.quantity;
        } else if (btnContentList.quantity > -100) {
            text += " ";
            text += btnContentList.quantity;
        } else {
            text += btnContentList.quantity;
        }
        //text+="　";
        if (btnContentList.expire > 9999) {
            text += btnContentList.expire;
        } else if (btnContentList.expire > 999) {
            text += " ";
            text += btnContentList.expire;
        } else if (btnContentList.expire > 99) {
            text += "  ";
            text += btnContentList.expire;
        } else if (btnContentList.expire > 9) {
            text += "   ";
            text += btnContentList.expire;
        } else if (btnContentList.expire > -1) {
            text += "    ";
            text += btnContentList.expire;
        } else if (btnContentList.expire > -10) {
            text += "   ";
            text += btnContentList.expire;
        } else if (btnContentList.expire > -100) {
            text += "  ";
            text += btnContentList.expire;
        } else if (btnContentList.expire > -1000) {
            text += " ";
            text += btnContentList.expire;
        } else {
            text += btnContentList.expire;
        }
        text += "天";
        codeBtn.setText(text);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/RobotoMono-Regular.ttf");
        codeBtn.setTypeface(font);
        codeBtn.setTag(hash);
        codeBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 21);
        codeBtn.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
        if (!btnContentList.pri0pub1) {
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
                Foodlist list = (Foodlist)getApplicationContext();
                if(v instanceof Button) {
                    if (((Button) v).getTag() instanceof Integer) {
                        index = Integer.parseInt(((Button) v).getTag().toString());
                    }
                    setContentView(R.layout.activity_see_food);
                    BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
                    navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
                    Button backBtn = (Button) findViewById(R.id.btnback);
                    backBtn.setOnClickListener(backwardbtnlistener);
                    Button plusBtn = (Button) findViewById(R.id.btnplus);
                    plusBtn.setOnClickListener(plusfoodbtnlistener);
                    Spinner spinner = (Spinner) findViewById(R.id.spinner);
                    setCenterSpinner(spinner);
                    regenerateBtnList(index);
                    generateCheckboxList();
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

    private Button.OnClickListener backwardbtnlistener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            setContentView(R.layout.activity_toolbar);
            BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
            navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
            Button seefoodBtn = (Button) findViewById(R.id.btnfridge);
            seefoodBtn.setOnClickListener(seefoodbtnlistener);
        }
    };

    private Button.OnClickListener plusfoodbtnlistener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            // custom dialog
            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
            final View view = getLayoutInflater().inflate(R.layout.plus_food, null);

            // set the custom dialog components - text, image and button
            final Spinner spinner = (Spinner) view.findViewById(R.id.spinnerfridge);
            //addsetCenterSpinner(spinner);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, R.layout.spinnerforadd, getResources().getStringArray(R.array.languages));
            adapter.setDropDownViewResource(R.layout.spinner_down);
            spinner.setAdapter(adapter);
            final int quant = 0;
            EditText txtValue = (EditText) view.findViewById(R.id.quantity);
            txtValue.setText(String.format("%d", quant));
            TextView date = (TextView) view.findViewById(R.id.date);
            Calendar mCal = Calendar.getInstance();
            String dateformat = "yyyy/M/dd";
            SimpleDateFormat df = new SimpleDateFormat(dateformat);
            String today = df.format(mCal.getTime());
            date.setText(today);
            Spinner spinner2 = (Spinner) view.findViewById(R.id.spinnercategory);
            addsetCenterSpinner2(spinner2);
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
                    text.setText(String.format("%d", num));
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
                            txt.setText(String.format("%d/%d/%d",year,  month+1, dayOfMonth));
                        }
                    }, year, month, day);
                    dpd.show();
                }
            });

            Button cancelbtn = (Button) view.findViewById((R.id.btncancel));
            Button confirmbtn = (Button) view.findViewById(R.id.btnconfirm);

            dialog.setView(view);
            final AlertDialog log = dialog.create();

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
                                Foodlist list = (Foodlist)getApplicationContext();
                                if(list.existedcategory(entered.getText().toString()) == true){
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
                                    list.addMap(entered.getText().toString(), View.generateViewId());
                                    list.addcategory(entered.getText().toString(), 0, true);
                                    loginside.dismiss();
                                    log.dismiss();
                                    setContentView(R.layout.activity_see_food);
                                    BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
                                    navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
                                    Button backBtn = (Button) findViewById(R.id.btnback);
                                    backBtn.setOnClickListener(backwardbtnlistener);
                                    Button plusBtn = (Button) findViewById(R.id.btnplus);
                                    plusBtn.setOnClickListener(plusfoodbtnlistener);
                                    Spinner spinner = (Spinner) findViewById(R.id.spinner);
                                    setCenterSpinner(spinner);
                                    for(int i = 0; i < list.categorylist.size(); i++)
                                        list.categorylist.get(i).onoff = true;
                                    generateBtnList();
                                    generateCheckboxList();
                                    plusBtn.bringToFront();
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
                        addsetCenterSpinner(spinner2);

                        cancelbtn.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                loginside.dismiss();
                            }
                        });

                        confirmbtn.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Foodlist list = (Foodlist) getApplicationContext();
                                Spinner entered = (Spinner)view.findViewById(R.id.spinnerdelete);
                                if(list.categorylist.size() < 5){
                                    Toast toast = Toast.makeText(MainActivity.this, "請選擇類別", Toast.LENGTH_LONG);
                                    toast.show();
                                }
                                else{
                                    list.deleteMap(entered.getSelectedItem().toString());
                                    list.changecategory(entered.getSelectedItem().toString());
                                    list.deletecategory(entered.getSelectedItem().toString());
                                    loginside.dismiss();
                                    log.dismiss();
                                    setContentView(R.layout.activity_see_food);
                                    BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
                                    navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
                                    Button backBtn = (Button) findViewById(R.id.btnback);
                                    backBtn.setOnClickListener(backwardbtnlistener);
                                    Button plusBtn = (Button) findViewById(R.id.btnplus);
                                    plusBtn.setOnClickListener(plusfoodbtnlistener);
                                    Spinner spinner = (Spinner) findViewById(R.id.spinner);
                                    setCenterSpinner(spinner);
                                    for(int i = 0; i < list.categorylist.size(); i++)
                                        list.categorylist.get(i).onoff = true;
                                    generateBtnList();
                                    generateCheckboxList();
                                    plusBtn.bringToFront();
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
                        addsetCenterSpinner1(spinner2);

                        cancelbtn.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                loginside.dismiss();
                            }
                        });

                        confirmbtn.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Foodlist list = (Foodlist) getApplicationContext();
                                Spinner entered = (Spinner)view.findViewById(R.id.spinnerdefault);
                                list.changedefaultcategory(entered.getSelectedItem().toString());
                                loginside.dismiss();
                                log.dismiss();
                                setContentView(R.layout.activity_see_food);
                                BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
                                navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
                                Button backBtn = (Button) findViewById(R.id.btnback);
                                backBtn.setOnClickListener(backwardbtnlistener);
                                Button plusBtn = (Button) findViewById(R.id.btnplus);
                                plusBtn.setOnClickListener(plusfoodbtnlistener);
                                Spinner spinner = (Spinner) findViewById(R.id.spinner);
                                setCenterSpinner(spinner);
                                for(int i = 0; i < list.categorylist.size(); i++)
                                    list.categorylist.get(i).onoff = true;
                                generateBtnList();
                                generateCheckboxList();
                                plusBtn.bringToFront();
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
                        Foodlist list = (Foodlist)getApplicationContext();
                        Spinner spinnerf = (Spinner) view.findViewById(R.id.spinnerfridge);
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
                        String now = String.format("%d/%d/%d", ny, (nm+1), nd);
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
                        list.add(spinnerf.getSelectedItem().toString(), editText.getText().toString(), Integer.valueOf(quan.getText().toString()),
                                spinnerc.getSelectedItem().toString(), (int)days, toggle.isChecked(), ps.getText().toString(), yy, mm-1, dd);
                        log.dismiss();
                        setContentView(R.layout.activity_see_food);
                        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
                        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
                        Button backBtn = (Button) findViewById(R.id.btnback);
                        backBtn.setOnClickListener(backwardbtnlistener);
                        Button plusBtn = (Button) findViewById(R.id.btnplus);
                        plusBtn.setOnClickListener(plusfoodbtnlistener);
                        Spinner spinner = (Spinner) findViewById(R.id.spinner);
                        setCenterSpinner(spinner);
                        for(int i = 0; i < list.categorylist.size(); i++)
                            list.categorylist.get(i).onoff = true;
                        generateBtnList();
                        generateCheckboxList();
                        plusBtn.bringToFront();
                    }
                }
            });

            log.show();
        }
    };

    class Sortbyorder implements Comparator<Foodlist.Category> {
        // Used for sorting in ascending order of
        // roll number
        public int compare(Foodlist.Category a, Foodlist.Category b) {
            return b.order - a.order;
        }
    }

    private void addsetCenterSpinner3(Spinner spinner1, int index){
        Foodlist list = (Foodlist)getApplicationContext();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                MainActivity.this,R.layout.spinnerforadd);
        adapter.setDropDownViewResource(R.layout.spinner_down);
        //Sort
        Collections.sort(list.categorylist, new Sortbyorder());
        int selected = 0;
        for(int i = 0; i < list.categorylist.size(); i++){
            if(list.categorylist.get(i).category.matches(list.foodlist.get(index).category)){
                adapter.add(list.categorylist.get(i).category);
                selected = i;
                break;
            }
        }
        for(int i = 0; i < list.categorylist.size(); i++) {
            if(i != selected && list.categorylist.get(i).order!=-1) {
                adapter.add(list.categorylist.get(i).category);
            }
        }
        spinner1.setAdapter(adapter);
    }

    private void addsetCenterSpinner2(Spinner spinner1){
        Foodlist list = (Foodlist)getApplicationContext();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                MainActivity.this,R.layout.spinnerforadd);
        adapter.setDropDownViewResource(R.layout.spinner_down);
        //Sort
        Collections.sort(list.categorylist, new Sortbyorder());
        for(int i = 0; i < list.categorylist.size(); i++) {
            adapter.add(list.categorylist.get(i).category);
        }
        spinner1.setAdapter(adapter);
    }

    private void addsetCenterSpinner1(Spinner spinner1){
        Foodlist list = (Foodlist)getApplicationContext();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                MainActivity.this,R.layout.spinnerforadd);
        adapter.setDropDownViewResource(R.layout.spinner_down);
        //Sort
        Collections.sort(list.categorylist, new Sortbyorder());
        for(int i = 0; i < list.categorylist.size(); i++) {
            if(list.categorylist.get(i).order != -1) {
                adapter.add(list.categorylist.get(i).category);
            }
        }
        spinner1.setAdapter(adapter);
    }

    private void addsetCenterSpinner(Spinner spinner1){
        Foodlist list = (Foodlist)getApplicationContext();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                MainActivity.this,R.layout.spinnerforadd);
        adapter.setDropDownViewResource(R.layout.spinner_down);
        //Sort
        Collections.sort(list.categorylist, new Sortbyorder());
        for(int i = 0; i < list.categorylist.size(); i++) {
            if(list.categorylist.get(i).order == 0) {
                adapter.add(list.categorylist.get(i).category);
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