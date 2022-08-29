package com.org.moneykeep.Activity.DetailsView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.org.moneykeep.Dialog.DeleteDialog;
import com.org.moneykeep.Dialog.UpdateDialog;
import com.org.moneykeep.R;
import com.org.moneykeep.Until.UpdateList;
import com.org.moneykeep.retrofitBean.PayEventBean;

import java.util.HashMap;
import java.util.Map;

public class DetailsActivity extends AppCompatActivity implements DetailsInterface.IView {
    private int id;
    private TextView detail_type, detail_cost, detail_time, detail_location, detail_remark;
    private LinearLayout detail_delete, detail_update;
    private ImageView detail_imageView;
    private final Map<String, Integer> img = new HashMap<>();
    private DetailsInterface.IPresenter iPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
        this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        localLayoutParams.flags = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags;

        iPresenter = new DetailsPresentersImplements(this, this);

        {
            img.put("餐饮", R.drawable.restaurant_64);
            img.put("交通", R.drawable.traffic_64);
            img.put("服饰", R.drawable.clothes_64);
            img.put("购物", R.drawable.shopping_64);
            img.put("服务", R.drawable.service_64);
            img.put("教育", R.drawable.teach_64);
            img.put("娱乐", R.drawable.entertainment_64);
            img.put("运动", R.drawable.motion_64);
            img.put("生活缴费", R.drawable.living_payment_64);
            img.put("旅行", R.drawable.travel_64);
            img.put("宠物", R.drawable.pets_64);
            img.put("医疗", R.drawable.medical_64);
            img.put("保险", R.drawable.insurance_64);
            img.put("公益", R.drawable.welfare_64);
            img.put("发红包", R.drawable.envelopes_64);
            img.put("转账", R.drawable.transfer_accounts_64);
            img.put("亲属卡", R.drawable.kinship_card_64);
            img.put("做人情", R.drawable.human_64);
            img.put("其它支出", R.drawable.others_64);
            img.put("生意", R.drawable.business_64);
            img.put("工资", R.drawable.wages_64);
            img.put("奖金", R.drawable.bonus_64);
            img.put("收红包", R.drawable.envelopes_64);
            img.put("收转账", R.drawable.transfer_accounts_64);
            img.put("其它收入", R.drawable.others_64);
            img.put("建设银行", R.drawable.construction_bank_64);
            img.put("农业银行", R.drawable.agricultural_bank_64);
        }

        Bundle bundle = this.getIntent().getExtras();
        id = bundle.getInt("Id", -1);

        findId();
        //setListen();

        if (id != -1) {
            getDetailsMessage();
        } else {
            Toast.makeText(this, "Id获取失败", Toast.LENGTH_LONG).show();
            finish();
        }

    }

    private void getDetailsMessage() {
        iPresenter.getDetailsMessage(id);
    }

    private void setListen() {
        Onclick onclick = new Onclick();
        detail_delete.setOnClickListener(onclick);
        detail_update.setOnClickListener(onclick);

    }

    @Override
    public void getDetailsMessageSuccessful(PayEventBean body) {
        detail_type.setText(body.getCategory());
        detail_cost.setText(String.valueOf(body.getCost()));
        detail_time.setText(body.getPayTime());
        detail_location.setText(body.getLocation());
        detail_remark.setText(body.getRemark());
        detail_imageView.setImageResource(img.getOrDefault(body.getCategory(),
                R.drawable.others_64));
        setListen();
    }

    @Override
    public void getDetailsMessageUnSuccessful(String s) {
        Toast.makeText(getApplicationContext(), "查询失败...", Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void UpdateMessageSuccessful(String s) {
        String category = updateList.getType();
        String cost = updateList.getCost();
        String remark = updateList.getRemark();
        detail_type.setText(category);
        detail_cost.setText(cost);
        detail_time.setText(updateList.getDate());
        detail_remark.setText(remark);
        detail_imageView.setImageResource(img.getOrDefault(category, R.drawable.others_64));
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }

    @Override
    public void UpdateMessageUnSuccessful(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }

    @Override
    public void deleteMessageSuccessful(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void deleteMessageUnSuccessful(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }

    private class Onclick implements View.OnClickListener {

        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.detail_delete:
                    deleteMessage();
                    break;
                case R.id.detail_update:
                    UpdateMessage();
                    break;
            }
        }

    }

    private UpdateDialog dialog;
    private UpdateList updateList;

    private void UpdateMessage() {
        updateList = new UpdateList();
        updateList.setType(detail_type.getText().toString());
        updateList.setCost(detail_cost.getText().toString());
        updateList.setDate(detail_time.getText().toString());
        updateList.setRemark(detail_remark.getText().toString());
        updateList.setId(id);
        Log.i("newUpdateListID===>", String.valueOf(id));
        dialog = new UpdateDialog(DetailsActivity.this);
        dialog.setList(updateList);
        dialog.setOnconfirmOnclickListener(new UpdateDialog.IOconfirmListener() {
            @Override
            public void oncofirm(UpdateDialog dialog, UpdateList newUpdateList) {
                iPresenter.UpdateMessage(newUpdateList);
                updateList = newUpdateList;
                /*if(newUpdateList != null){
                    //Toast.makeText(getApplicationContext(), newUpdateList.getRemark(), Toast.LENGTH_LONG).show();
                    String[] dates = newUpdateList.getDate().split(" ");
                    String[] date = dates[0].split("-");
                    String year = date[0];
                    String month = date[1];
                    String day = date[2];
                    String time = dates[dates.length - 1];
                    String cost = newUpdateList.getCost();
                    String category = newUpdateList.getType();
                    String remark = newUpdateList.getRemark();

                    AllPay allPay = new AllPay();
                    allPay.setYear(year);
                    allPay.setMonth(month);
                    allPay.setTime(time);
                    allPay.setDate(dates[0]);
                    allPay.setPayTime(newUpdateList.getDate());
                    allPay.setCategory(category);
                    allPay.setRemark(remark);
                    allPay.setCost(Double.valueOf(cost));

                    allPay.update(id, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e == null){
                                detail_type.setText(category);
                                detail_cost.setText(cost);
                                detail_time.setText(newUpdateList.getDate());
                                detail_remark.setText(remark);
                                detail_imageView.setImageResource(img.getOrDefault(category,
                                        R.drawable.others_64));
                                SharedPreferences keep = null;
                                if (keep == null) {
                                    keep = getSharedPreferences("DeleteOrUpdate", Context.MODE_PRIVATE);
                                }
                                SharedPreferences.Editor user_editor = keep.edit();
                                user_editor.putBoolean("isdelete", true);
                                user_editor.commit();
                                Toast.makeText(getApplicationContext(), "更新成功...", Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(getApplicationContext(), "更新失败：" + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }*/
            }
        });
        dialog.show();
    }


    private void deleteMessage() {

        DeleteDialog dialog = new DeleteDialog(DetailsActivity.this);
        dialog.setiOconfirmListener(dialog1 -> {
            iPresenter.deleteMessage(id);
            //AllPay allPay = new AllPay();
            /*allPay.delete(id, new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        SharedPreferences keep = null;
                        if (keep == null) {
                            keep = getSharedPreferences("DeleteOrUpdate", Context.MODE_PRIVATE);
                        }
                        SharedPreferences.Editor user_editor = keep.edit();
                        user_editor.putBoolean("isdelete", true);
                        user_editor.commit();
                        Toast.makeText(getApplicationContext(), "删除成功...", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "删除失败：" + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });*/
        }).show();

    }

    private void findId() {

        detail_type = findViewById(R.id.detail_type);
        detail_cost = findViewById(R.id.detail_cost);
        detail_time = findViewById(R.id.detail_time);
        detail_location = findViewById(R.id.detail_location);
        detail_remark = findViewById(R.id.detail_remark);
        detail_delete = findViewById(R.id.detail_delete);
        detail_update = findViewById(R.id.detail_update);
        detail_imageView = findViewById(R.id.detail_imageView);

    }


}