package com.org.moneykeep.Activity.PunchCardView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.org.moneykeep.Dialog.DeleteDialog;
import com.org.moneykeep.Dialog.PunchCardDialog;
import com.org.moneykeep.R;
import com.org.moneykeep.Until.ChangeDouble;

public class PunchCardActivity extends AppCompatActivity implements PunchCardViewInterface.IView {

    private TextView type, targetMoney, deadlineDate, state, savedMoney, saved_money, over_money, title, punchCardAmount;
    private ImageView imageView;
    private ProgressBar progressBar;
    private LinearLayout delete, punchCard;
    private String objectId, sDeadlineDate, sSpeed, nowState, sTitle, sType, sDate;
    private double sTargetMoney, sSavedMoney, newSavedMoney;
    private int everyDaySaved, amount, remainingTimes;
    private PunchCardViewInterface.IPresenter iPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_punch_card);

        WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
        this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        localLayoutParams.flags = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags;
        iPresenter = new PunchCardViewPresenterImplements(this);


        Bundle bundle = this.getIntent().getExtras();
        objectId = bundle.getString("objectId", "");
        sDeadlineDate = bundle.getString("deadlineDate", "");
        sTargetMoney = bundle.getDouble("targetMoney", 0);
        sSavedMoney = bundle.getDouble("savedMoney", 0);
        sSpeed = bundle.getString("speed", "");
        nowState = bundle.getString("nowState", "");
        sTitle = bundle.getString("title", "");
        sType = bundle.getString("type", "");
        sDate = bundle.getString("date", "");
        everyDaySaved = bundle.getInt("everydaySave", 100);
        amount = bundle.getInt("amount", 10);
        remainingTimes = bundle.getInt("remainingTimes", amount);
        FindId();

        SetListen();

        makeView();
    }


    private void makeView() {
        String text = nowState;
        if (nowState.equals("未开始") || nowState.equals("未完成") || nowState.equals("已完成") || nowState.equals("已打卡")) {
            punchCard.setEnabled(false);
            punchCard.setClickable(false);
            if (nowState.equals("未开始")) {
                sDate = sDate.substring(0, sDate.lastIndexOf("-"));
                text = nowState + "(" + sDate + ")";
            }
        }
        punchCardAmount.setText(remainingTimes + "次");
        type.setText(sType);
        title.setText(sTitle);
        targetMoney.setText(String.valueOf(sTargetMoney));
        deadlineDate.setText(sDeadlineDate);
        savedMoney.setText(String.valueOf(sSavedMoney));
        state.setText(text);
        saved_money.setText(String.valueOf(sSavedMoney));
        if (sSavedMoney < sTargetMoney) {
            over_money.setText(String.valueOf(ChangeDouble.subDouble(sTargetMoney, sSavedMoney)));
        } else {
            over_money.setText("0");
        }
        progressBar.setProgress(Integer.parseInt(sSpeed));
    }

    @Override
    public void showPunchCardEventSuccessful() {

    }

    @Override
    public void showPunchCardEventUnSuccessful() {

    }

    @Override
    public void deletePunchCardEventSuccessful(String message) {
        SharedPreferences keep = null;
        keep = getSharedPreferences("DeleteOrUpdate", Context.MODE_PRIVATE);
        SharedPreferences.Editor user_editor = keep.edit();
        user_editor.putBoolean("start", true);
        user_editor.commit();
        finish();
        Toast.makeText(PunchCardActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void deletePunchCardEventUnSuccessful(String s) {
        Toast.makeText(PunchCardActivity.this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void PunchCardSuccessful(String s) {
        punchCard.setEnabled(false);
        punchCard.setClickable(false);
        SharedPreferences keep = null;
        if (keep == null) {
            keep = getSharedPreferences("DeleteOrUpdate", Context.MODE_PRIVATE);
        }
        SharedPreferences.Editor user_editor = keep.edit();
        user_editor.putBoolean("start", true);
        user_editor.commit();
        if (sTargetMoney <= newSavedMoney) {
            state.setText("已完成");
            over_money.setText("0");
            progressBar.setProgress(100);
            punchCard.setEnabled(false);
        } else {
            state.setText("已打卡");
            sSpeed = String.valueOf((int) (ChangeDouble.divDouble(newSavedMoney, sTargetMoney) * 100));
            progressBar.setProgress(Integer.parseInt(sSpeed));
            over_money.setText(String.valueOf(ChangeDouble.subDouble(sTargetMoney, newSavedMoney)));
        }
        punchCardAmount.setText(remainingTimes - 1 + "次");
        savedMoney.setText(String.valueOf(newSavedMoney));
        saved_money.setText(String.valueOf(newSavedMoney));
        Toast.makeText(PunchCardActivity.this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void PunchCardUnSuccessful(String s) {
        Toast.makeText(PunchCardActivity.this, s, Toast.LENGTH_SHORT).show();
    }

    private class Onclick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.delete:
                    deletePlan();
                    break;
                case R.id.punchCard:
                    punchCardPlan();
                    break;
            }
        }
    }

    private void punchCardPlan() {
        PunchCardDialog dialog = new PunchCardDialog(this);
        dialog.setEveryDaySave(everyDaySaved);
        dialog.setiOconfirmListener(new PunchCardDialog.IOconfirmListener() {
            @Override
            public void oncofirm(PunchCardDialog dialog, String remarkSavedMoney) {
                newSavedMoney = ChangeDouble.addDouble(sSavedMoney, Double.parseDouble(remarkSavedMoney));
                iPresenter.punchCard(sSavedMoney, Double.parseDouble(remarkSavedMoney), remainingTimes, Integer.parseInt(objectId));
                /*Calendar c = Calendar.getInstance();
                String year = String.valueOf(c.get(Calendar.YEAR));
                String month = String.valueOf(c.get(Calendar.MONTH) + 1);
                String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
                int week = c.get(Calendar.WEEK_OF_YEAR);

                if(Integer.parseInt(month) < 10){
                    month = "0" + month;
                }
                if(Integer.parseInt(day) < 10){
                    day = "0" + day;
                }
                String date = year + "-" + month + "-" + day+ "-" + week;
                SaveMoney saveMoney = new SaveMoney();
                saveMoney.setDate(date);
                saveMoney.setSavedMoney(newSavedMoney);
                saveMoney.setTargetMoney(sTargetMoney);
                saveMoney.setAmount(amount);
                saveMoney.update(objectId, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e == null){
                            SharedPreferences keep = null;
                            if (keep == null) {
                                keep = getSharedPreferences("DeleteOrUpdate", Context.MODE_PRIVATE);
                            }
                            SharedPreferences.Editor user_editor = keep.edit();
                            user_editor.putBoolean("start", true);
                            user_editor.commit();
                            if(sTargetMoney <= newSavedMoney){
                                state.setText("已完成");
                                over_money.setText("0");
                                progressBar.setProgress(100);
                                punchCard.setEnabled(false);
                            }else{
                                state.setText("已打卡");
                                sSpeed = String.valueOf((int)(ChangeDouble.divDouble(newSavedMoney,sTargetMoney) * 100));
                                progressBar.setProgress(Integer.parseInt(sSpeed));
                                over_money.setText(String.valueOf(ChangeDouble.subDouble(sTargetMoney,newSavedMoney)));
                            }
                            savedMoney.setText(String.valueOf(newSavedMoney));
                            saved_money.setText(String.valueOf(newSavedMoney));

                            Toast.makeText(PunchCardActivity.this,"打卡成功...",Toast.LENGTH_SHORT).show();

                        }else{
                            Log.i("punchCardError", "打卡失败:" + e.getMessage());
                            Toast.makeText(PunchCardActivity.this,"打卡失败:" + e.getMessage() ,Toast.LENGTH_SHORT).show();
                        }
                    }
                });*/
            }
        }).show();
    }

    private void deletePlan() {
        DeleteDialog dialog = new DeleteDialog(this);
        dialog.setsText("你确定要删除本次计划嘛？");
        dialog.setiOconfirmListener(new DeleteDialog.IOconfirmListener() {
            @Override
            public void oncofirm(DeleteDialog dialog) {

                iPresenter.deletePunchCardEvent(Integer.parseInt(objectId));

            }
        }).show();
    }

    private void SetListen() {
        Onclick onclick = new Onclick();
        delete.setOnClickListener(onclick);
        punchCard.setOnClickListener(onclick);

    }

    private void FindId() {
        punchCardAmount = findViewById(R.id.punchCardAmount);
        title = findViewById(R.id.title);
        type = findViewById(R.id.type);
        targetMoney = findViewById(R.id.targetMoney);
        deadlineDate = findViewById(R.id.deadlineDate);
        state = findViewById(R.id.state);
        saved_money = findViewById(R.id.saved_money);
        over_money = findViewById(R.id.over_money);
        imageView = findViewById(R.id.imageView);
        progressBar = findViewById(R.id.progressbar);
        delete = findViewById(R.id.delete);
        punchCard = findViewById(R.id.punchCard);
        savedMoney = findViewById(R.id.savedMoney);
    }
}