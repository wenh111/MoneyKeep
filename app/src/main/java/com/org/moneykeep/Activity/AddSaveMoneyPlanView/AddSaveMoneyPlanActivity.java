package com.org.moneykeep.Activity.AddSaveMoneyPlanView;

import static android.view.View.VISIBLE;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.org.moneykeep.R;
import com.org.moneykeep.Until.InputFilterMinMax;

import java.util.Calendar;

public class AddSaveMoneyPlanActivity extends AppCompatActivity implements AddSaveMoneyInterface.IView {
    private ImageButton but_finish, but_summit;
    private EditText ed_time, ed_target_money, ed_title, ed_custom_number;
    private RadioButton radio, radio_12month, radio_30day, radio_4week, radio_52week, radio_365day, onCreateRadio, radio_custom, radioGroup_radio;
    private RadioGroup radioGroup, radioGroup_custom;
    private LinearLayout ly_custom;
    private AddSaveMoneyInterface.IPresenter iPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_save_money_plan);

        WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
        this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        localLayoutParams.flags = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags;

        iPresenter = new AddSaveMoneyPresenterImplements(this);

        FindId();

        SetListen();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.radio_custom) {
                    ly_custom.setVisibility(VISIBLE);
                } else {
                    ly_custom.setVisibility(View.GONE);
                }
            }
        });
        Bundle bundle = getIntent().getExtras();
        int RadioButtonId = bundle.getInt("RadioButtonId", R.id.radio_12month);
        onCreateRadio = findViewById(RadioButtonId);
        onCreateRadio.setChecked(true);
        ed_time.setInputType(InputType.TYPE_NULL);
        but_finish.getBackground().setAlpha(0);
        but_summit.getBackground().setAlpha(0);

    }

    @Override
    public void addSaveMoneySuccessful(String successfulMessage) {
        SharedPreferences keep = getSharedPreferences("DeleteOrUpdate", Context.MODE_PRIVATE);
        SharedPreferences.Editor user_editor = keep.edit();
        user_editor.putBoolean("start", true);
        user_editor.apply();
        but_summit.setEnabled(true);
        Toast.makeText(AddSaveMoneyPlanActivity.this, successfulMessage, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void addSaveMoneyUnSuccessful(int id, String message) {
        if (id != -1) {
            EditText editText = findViewById(id);
            editText.requestFocus();
        }
        but_summit.setEnabled(true);
        Toast.makeText(AddSaveMoneyPlanActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    private class Onclick implements View.OnClickListener {

        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.ed_time:
                    getTime();
                    break;
                case R.id.but_finish:
                    finish();
                    break;
                case R.id.but_summit:
                    summitData();
                    break;

                default:
                    throw new IllegalStateException("Unexpected value: " + view.getId());
            }
        }
    }

    private void summitData() {
        but_summit.setEnabled(false);
        SharedPreferences userdata;
        userdata = null;
        if (userdata == null) {
            userdata = getSharedPreferences("user", MODE_PRIVATE);
        }
        String user_account = userdata.getString("user_email", "");
        String date = ed_time.getText().toString();
        String title = ed_title.getText().toString();
        String targetMoney = ed_target_money.getText().toString();
        radio = findViewById(radioGroup.getCheckedRadioButtonId());
        String type = radio.getText().toString();
        String number = ed_custom_number.getText().toString();
        String custom_text = null;
        if (radio_custom.isChecked()) {
            radioGroup_radio = findViewById(radioGroup_custom.getCheckedRadioButtonId());
            custom_text = radioGroup_radio.getText().toString();
        }
        iPresenter.addSaveMoney(user_account, number, type,title,date, targetMoney, custom_text);


    }

    private void SetListen() {
        Onclick onclick = new Onclick();
        but_finish.setOnClickListener(onclick);
        but_summit.setOnClickListener(onclick);
        ed_time.setOnClickListener(onclick);
        ed_time.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    getTime();
                }
            }
        });
        ed_target_money.setFilters(new InputFilter[]{new InputFilterMinMax(0, 10000000)});
    }

    private void getTime() {
        Calendar c = Calendar.getInstance();
        new DatePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                int month = i1 + 1;
                String s_month = String.valueOf(month);
                String day = String.valueOf(i2);

                if (month < 10) {
                    s_month = "0" + s_month;
                }
                if (i2 < 10) {
                    day = "0" + day;
                }
                String select_time = String.format("%s-%s-%s", i, s_month, day);
                ed_time.setText(select_time);
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void FindId() {
        ed_custom_number = findViewById(R.id.ed_custom_number);
        but_finish = findViewById(R.id.but_finish);
        but_summit = findViewById(R.id.but_summit);
        ed_time = findViewById(R.id.ed_time);
        ed_target_money = findViewById(R.id.ed_target_money);
        ed_title = findViewById(R.id.ed_title);
        radioGroup = findViewById(R.id.radioGroup);
        radio_12month = findViewById(R.id.radio_12month);
        radio_30day = findViewById(R.id.radio_30day);
        radio_4week = findViewById(R.id.radio_4week);
        radio_52week = findViewById(R.id.radio_52week);
        radio_365day = findViewById(R.id.radio_365day);
        radio_custom = findViewById(R.id.radio_custom);
        radioGroup_custom = findViewById(R.id.radioGroup_custom);
        ly_custom = findViewById(R.id.ly_custom);
    }
}