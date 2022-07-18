package com.org.moneykeep.Activity.ui.dashboard;


import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.org.moneykeep.AAChartCoreLib.AAChartCreator.AAChartModel;
import com.org.moneykeep.AAChartCoreLib.AAChartCreator.AAChartView;
import com.org.moneykeep.R;
import com.org.moneykeep.databinding.FragmentDashboardBinding;

import java.util.Calendar;


public class DashboardFragment extends Fragment implements PayEventStatisticsInterface.IView {

    private FragmentDashboardBinding binding;
    private AAChartView AAChartViewPay, AAChartViewIncome, AAChartViewColumnPay;
    private RadioGroup radio_group;
    private RadioButton day, month, year;
    private EditText select_edit;
    private TextView now_time;
    private String user_account;
    private PayEventStatisticsInterface.IPresenter iPresenter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDashboardBinding.inflate(inflater, container, false);

        SharedPreferences userdata = null;
        if (userdata == null) {
            userdata = getActivity().getSharedPreferences("user", MODE_PRIVATE);
        }
        user_account = userdata.getString("user_email", "");

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        FindId();
        SetListen();

        iPresenter = new PayEventStatisticsPresenterImplements(this);
        select_edit.setInputType(InputType.TYPE_NULL);

        Calendar calendar = Calendar.getInstance();
        int now_year = calendar.get(Calendar.YEAR);
        int now_month = calendar.get(Calendar.MONTH) + 1;
        int now_day = calendar.get(Calendar.DAY_OF_MONTH);
        String now_date = now_year + "-" + now_month + "-" + now_day;

        now_time.setText(now_date);

        if (day.isChecked()) {
            getDayStatistics();
        } else if (month.isChecked()) {
            //getDayStatistics();
            getMonthStatistics();
        } else if (year.isChecked()) {
            getYearStatistics();
        }

    }

    private void SetListen() {
        Onclick onclick = new Onclick();
        CheckOnclick checkOnclick = new CheckOnclick();
        select_edit.setOnClickListener(onclick);
        select_edit.setOnFocusChangeListener(new CheckOnFocusChange());
        radio_group.setOnCheckedChangeListener(checkOnclick);

    }

    @Override
    public void GetDayPayStatisticsSuccessful(String s, AAChartModel payChartModel, AAChartModel incomeChartModel) {
        AAChartViewPay.aa_drawChartWithChartModel(payChartModel);
        AAChartViewPay.aa_refreshChartWithChartModel(payChartModel);

        AAChartViewIncome.aa_drawChartWithChartModel(incomeChartModel);
        AAChartViewIncome.aa_refreshChartWithChartModel(incomeChartModel);
        if (s != null) {
            Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void GetDayPayStatisticsUnSuccessful(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void GetMonthPayColumnsSuccessful(String message, AAChartModel columnModelPay) {
        AAChartViewColumnPay.aa_drawChartWithChartModel(columnModelPay);
        AAChartViewColumnPay.refreshDrawableState();
        /*if (message != null ) {

            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }*/
    }



    @Override
    public void GetYearPayColumnsSuccessful(String message, AAChartModel columnModelPay) {
        AAChartViewColumnPay.aa_drawChartWithChartModel(columnModelPay);
        AAChartViewColumnPay.refreshDrawableState();
       /* if (message != null ) {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }*/
    }



    private class CheckOnFocusChange implements View.OnFocusChangeListener {
        @Override
        public void onFocusChange(View view, boolean b) {
            switch (view.getId()) {
                case R.id.select_edit:
                    if (b) {
                        SelectTime();
                    }
                    break;
            }
        }
    }

    private class Onclick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.select_edit:
                    SelectTime();
                    break;
            }
        }
    }

    private class CheckOnclick implements RadioGroup.OnCheckedChangeListener {

        @SuppressLint("NonConstantResourceId")
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int ButtonId) {
            switch (ButtonId) {
                case R.id.day:
                    getDayStatistics();
                    break;
                case R.id.month:
                    getMonthStatistics();
                    break;
                case R.id.year:
                    getYearStatistics();
                    break;
            }
        }
    }

    private void SelectTime() {
        Calendar c = Calendar.getInstance();
        new DatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                String select_time = String.format("%s-%s-%s", i, i1 + 1, i2);
                select_edit.setText(select_time);
                now_time.setText(select_time);
                select_edit.clearFocus();
                if (day.isChecked()) {
                    getDayStatistics();
                } else if (month.isChecked()) {
                    getMonthStatistics();
                } else if (year.isChecked()) {
                    getYearStatistics();
                }
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void getYearStatistics() {
        String day_date = now_time.getText().toString();
        String[] date = day_date.split("-");
        String SelectYear = date[0];
        iPresenter.GetYearPayStatistics(user_account, SelectYear);

    }

    private void getMonthStatistics() {
        String day_date = now_time.getText().toString();
        String[] date = day_date.split("-");
        String SelectMonth = date[1];
        String SelectYear = date[0];
        iPresenter.GetMonthPayStatistics(user_account, SelectMonth, SelectYear);

    }

    private void getDayStatistics() {
        String day_date = now_time.getText().toString();

        iPresenter.GetDayPayStatistics(user_account, day_date);

    }


    private void FindId() {

        AAChartViewPay = getView().findViewById(R.id.AAChartViewPay);
        AAChartViewIncome = getView().findViewById(R.id.AAChartViewIncome);
        AAChartViewColumnPay = getView().findViewById(R.id.AAChartViewColumnPay);

        radio_group = getView().findViewById(R.id.radio_group);
        day = getView().findViewById(R.id.day);
        month = getView().findViewById(R.id.month);
        year = getView().findViewById(R.id.year);

        select_edit = getView().findViewById(R.id.select_edit);
        now_time = getView().findViewById(R.id.now_time);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}