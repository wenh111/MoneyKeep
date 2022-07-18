package com.org.moneykeep.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.org.moneykeep.R;

public class GetBankMessageDialog extends Dialog {
    public GetBankMessageDialog(@NonNull Context context) {
        super(context);
    }

    public GetBankMessageDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    private IOnCancelListener iOnCancelListener;
    private IOconfirmListener iOconfirmListener;


    public GetBankMessageDialog setiOnCancelListener(IOnCancelListener iOnCancelListener) {
        this.iOnCancelListener = iOnCancelListener;
        return this;
    }

    public GetBankMessageDialog setiOconfirmListener(IOconfirmListener iOconfirmListener) {
        this.iOconfirmListener = iOconfirmListener;
        return this;
    }

    public interface IOnCancelListener {
        void oncancel(GetBankMessageDialog dialog);
    }

    public interface IOconfirmListener {
        void oncofirm(GetBankMessageDialog dialog, String message);
    }

    private Button but_cancel,but_summit;
    private EditText et_message;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//设置Dialog背景透明
        setContentView(R.layout.layout_bank_message_dialog);


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
        et_message = findViewById(R.id.et_message);

        but_summit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = et_message.getText().toString();
                if (iOconfirmListener != null) {
                    iOconfirmListener.oncofirm(GetBankMessageDialog.this,message);
                }
                dismiss();
            }
        });

        but_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (iOnCancelListener != null) {
                    iOnCancelListener.oncancel(GetBankMessageDialog.this);
                }
                dismiss();
            }
        });
    }
}
