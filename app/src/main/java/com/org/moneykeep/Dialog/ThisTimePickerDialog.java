package com.org.moneykeep.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.org.moneykeep.R;

import java.util.Calendar;


public class ThisTimePickerDialog extends Dialog implements View.OnClickListener {
    public ThisTimePickerDialog(@NonNull Context context) {
        super(context);
    }

    public ThisTimePickerDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }


    private DatePicker datePicker;
    private TimePicker timePicker;
    private Button but_cancel, but_summit;
    private IOconfirmListener confirmListener;
    private IOnCancelListener cancelListener;
    private String data, time;


    public ThisTimePickerDialog but_cancel(IOnCancelListener listener) {
        this.cancelListener = listener;
        return this;
    }

    public ThisTimePickerDialog but_summit(IOconfirmListener listener) {
        this.confirmListener = listener;
        return this;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//设置Dialog背景透明
        setContentView(R.layout.layout_timepicker);
        but_cancel = findViewById(R.id.but_cancel);
        but_summit = findViewById(R.id.but_summit);
        datePicker = findViewById(R.id.datePicker);
        timePicker = findViewById(R.id.timePicker);
        but_cancel.setOnClickListener(this);
        but_summit.setOnClickListener(this);

        timePicker.setIs24HourView(true);
        datePicker.setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);
        timePicker.setDescendantFocusability(TimePicker.FOCUS_BLOCK_DESCENDANTS);

        Ocl ocl = new Ocl();
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int monthOfYear = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        datePicker.init(year, monthOfYear, dayOfMonth, ocl);


    }

    private class Ocl implements DatePicker.OnDateChangedListener {
        @Override
        public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
            data = String.format("%s-%s-%s ", i, i1 + 1, i2);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.but_cancel:
                if (cancelListener != null) {
                    cancelListener.oncancel(this);
                }
                dismiss();
                break;
            case R.id.but_summit:
                int month = datePicker.getMonth() + 1;
                data = datePicker.getYear() + "-" + month + "-" + datePicker.getDayOfMonth();
                int min = timePicker.getMinute();
                int hour = timePicker.getHour();
                String s_min;

                if (min < 10) {
                    s_min = "0" + min;
                } else {
                    s_min = String.valueOf(min);
                }

                time = hour + ":" + s_min;
                //String dataTime = data + "   " + time;//三个空格

                if (confirmListener != null) {
                    confirmListener.oncofirm(this, data, time);
                }
                dismiss();
                break;
        }
    }

    public interface IOnCancelListener {
        void oncancel(ThisTimePickerDialog dialog);

    }

    public interface IOconfirmListener {
        void oncofirm(ThisTimePickerDialog dialog, String data, String time);
    }
}
