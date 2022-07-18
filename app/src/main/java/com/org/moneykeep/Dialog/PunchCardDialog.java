package com.org.moneykeep.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.org.moneykeep.R;
import com.org.moneykeep.Until.InputFilterMinMax;

public class PunchCardDialog extends Dialog {
    public PunchCardDialog(@NonNull Context context) {
        super(context);
    }

    public PunchCardDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    private IOnCancelListener iOnCancelListener;
    private IOconfirmListener iOconfirmListener;


    public PunchCardDialog setiOnCancelListener(IOnCancelListener iOnCancelListener) {
        this.iOnCancelListener = iOnCancelListener;
        return this;
    }

    public PunchCardDialog setiOconfirmListener(IOconfirmListener iOconfirmListener) {
        this.iOconfirmListener = iOconfirmListener;
        return this;
    }

    public interface IOnCancelListener {
        void oncancel(PunchCardDialog dialog);
    }

    public interface IOconfirmListener {
        void oncofirm(PunchCardDialog dialog, String remark);
    }

    private Button but_cancel,but_summit;
    private EditText et;
    private int everyDaySave = 0;

    public int getEveryDaySave() {
        return everyDaySave;
    }

    public void setEveryDaySave(int everyDaySave) {
        this.everyDaySave = everyDaySave;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//设置Dialog背景透明
        setContentView(R.layout.layout_punch_card_dialog);


        //设置宽度
        WindowManager m = getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = getWindow().getAttributes();
        Point size = new Point();
        d.getSize(size);
        p.width = (int)(size.x * 0.9);//设置dialog的宽度为当前手机屏幕的宽度*0.8
        getWindow().setAttributes(p);

        but_summit = findViewById(R.id.but_summit);
        but_cancel = findViewById(R.id.but_cancel);
        et = findViewById(R.id.et);
        et.setFilters(new InputFilter[]{new InputFilterMinMax(0,1000000)});
        if(everyDaySave != 0){
            et.setText(String.valueOf(everyDaySave));
        }
        but_summit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String money = et.getText().toString();
                if(!money.equals("")){
                    if (iOconfirmListener != null) {
                        iOconfirmListener.oncofirm(PunchCardDialog.this,money);
                    }
                    dismiss();
                }else{
                    Toast.makeText(getContext(),"请输入金额...",Toast.LENGTH_SHORT).show();
                }

            }
        });

        but_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (iOnCancelListener != null) {
                    iOnCancelListener.oncancel(PunchCardDialog.this);
                }
                dismiss();
            }
        });
    }
}
