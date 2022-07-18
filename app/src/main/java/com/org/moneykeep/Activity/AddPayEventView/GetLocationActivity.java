package com.org.moneykeep.Activity.AddPayEventView;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.addresspicker.huichao.addresspickerlibrary.CityPickerDialog;
import com.addresspicker.huichao.addresspickerlibrary.InitAreaTask;
import com.addresspicker.huichao.addresspickerlibrary.Util;
import com.addresspicker.huichao.addresspickerlibrary.address.City;
import com.addresspicker.huichao.addresspickerlibrary.address.County;
import com.addresspicker.huichao.addresspickerlibrary.address.Province;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.org.moneykeep.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GetLocationActivity extends AppCompatActivity implements OnGetSuggestionResultListener,InitAreaTask.onLoadingAddressListener {

    private Button my_location;
    private ImageButton but_finish;
    private AutoCompleteTextView auto_ed_text;
    private TextView tx_location;
    private ListView result;
    private SuggestionSearch mSuggestionSearch;
    private SimpleAdapter simpleAdapter;
    private String city, street,province,district;
    private ArrayList<Province> provinces;
    private Dialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_location);

        WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
        this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        localLayoutParams.flags = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags;

        SDKInitializer.initialize(getApplicationContext());

        Bundle bundle = this.getIntent().getExtras();
        city = bundle.getString("city", "");
        province = bundle.getString("province","");
        district = bundle.getString("district","");
        street = bundle.getString("street", "");


        if (provinces == null) {
            provinces = new ArrayList<>();
        }
        if (provinces.size() > 0) {
            Toast.makeText(GetLocationActivity.this,"初始化完成".toString(),Toast.LENGTH_SHORT).show();
        } else {
            //没有地址数据，就去初始化
            InitAreaTask initAreaTask = new InitAreaTask(GetLocationActivity.this, provinces);
            initAreaTask.execute(0);
            initAreaTask.setOnLoadingAddressListener(this);
        }

        FindId();

        SetListen();

        but_finish.getBackground().setAlpha(0);
        tx_location.setText(city);

        mSuggestionSearch = SuggestionSearch.newInstance();
        mSuggestionSearch.setOnGetSuggestionResultListener(this);

        auto_ed_text.setThreshold(1);
        auto_ed_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mSuggestionSearch.requestSuggestion((new SuggestionSearchOption())
                        .keyword(s.toString()) // 关键字
                        .city(tx_location.getText().toString())// 城市
                        .citylimit(true));
            }
        });

    }

    @Override
    public void onGetSuggestionResult(SuggestionResult suggestionResult) {
        if (suggestionResult == null || suggestionResult.getAllSuggestions() == null) {
            return;
        }
        List<HashMap<String, String>> suggest = new ArrayList<>();
        for (SuggestionResult.SuggestionInfo info : suggestionResult.getAllSuggestions()) {
            if (info.getKey() != null && info.getDistrict() != null && info.getCity() != null) {
                HashMap<String, String> map = new HashMap<>();
                map.put("key", info.getKey());
                map.put("city", info.getCity());
                map.put("dis", info.getDistrict());
                suggest.add(map);
            }
        }

        simpleAdapter = new SimpleAdapter(getApplicationContext(),
                suggest,
                R.layout.listview_item_location,
                new String[]{"key", "city", "dis"},
                new int[]{R.id.sug_key, R.id.sug_city, R.id.sug_dis});

        result.setAdapter(simpleAdapter);
        result.setOnItemClickListener(new ItemListener());
        simpleAdapter.notifyDataSetChanged();
    }

    private void showAddressDialog() {//初始化地址数据后，显示地址选择框
        new CityPickerDialog(GetLocationActivity.this, provinces, null, null, null,
                new CityPickerDialog.onCityPickedListener() {

                    @Override
                    public void onPicked(Province selectProvince,
                                         City selectCity, County selectCounty) {

                        StringBuilder address = new StringBuilder();


                        if(selectCounty == null){
                            address.append(selectProvince != null ? selectProvince.getAreaName() : "");
                        }else{
                            address.append(selectCity != null ? selectCity.getAreaName() : "");
                        }

                        tx_location.setText(address.toString());
                        Toast.makeText(GetLocationActivity.this,address.toString(),Toast.LENGTH_SHORT).show();
                    }
                }).show();
    }

    @Override
    public void onLoadFinished() {
        progressDialog.dismiss();
        Toast.makeText(GetLocationActivity.this,"加载完成",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoading() {
        progressDialog = Util.createLoadingDialog(GetLocationActivity.this, "请稍等...", true, 0);
        progressDialog.show();
    }

    class ItemListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            HashMap<String, String> map = (HashMap<String, String>) simpleAdapter.getItem(position);
            String city_return = map.get("city");
            String key_return = map.get("key");
            String dis_return = map.get("dis");
            Intent intent = new Intent();
            ComponentName componentName = new ComponentName(GetLocationActivity.this, AddPayEventActivity.class);
            intent.setComponent(componentName);
            Bundle bundle = new Bundle();
            bundle.putString("location_return", city_return + " " + dis_return + " " + key_return);
            intent.putExtras(bundle);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    private void SetListen() {
        Onclick onclick = new Onclick();
        but_finish.setOnClickListener(onclick);
        my_location.setOnClickListener(onclick);
        tx_location.setOnClickListener(onclick);
    }


    private class Onclick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.but_finish:
                    finish();
                    break;
                case R.id.my_location:
                    Intent intent = new Intent();
                    ComponentName componentName = new ComponentName(GetLocationActivity.this, AddPayEventActivity.class);
                    intent.setComponent(componentName);
                    Bundle bundle = new Bundle();
                    bundle.putString("my_city_return", city);
                    intent.putExtras(bundle);
                    setResult(RESULT_OK, intent);
                    finish();
                    break;
                case R.id.tx_location:
                    showAddressDialog();
                    break;
            }
        }
    }

    private void FindId() {
        but_finish = findViewById(R.id.but_finish);
        auto_ed_text = findViewById(R.id.auto_ed_text);
        tx_location = findViewById(R.id.tx_location);
        result = findViewById(R.id.result);
        my_location = findViewById(R.id.my_location);
    }
}