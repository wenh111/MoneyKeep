package com.org.moneykeep.Activity.ui.home;

import static android.content.Context.MODE_PRIVATE;

import static cn.bmob.v3.Bmob.getApplicationContext;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.org.moneykeep.Activity.AddPayEventView.AddPayEventActivity;
import com.org.moneykeep.Activity.DetailsView.DetailsActivity;
import com.org.moneykeep.Dialog.AllTypePickerDialog;
import com.org.moneykeep.Dialog.DeleteDialog;
import com.org.moneykeep.R;
import com.org.moneykeep.RecyclerViewAdapter.DayRecyclerViewAdapter;
import com.org.moneykeep.RecyclerViewAdapter.MonthRecyclerViewAdapter;
import com.org.moneykeep.RecyclerViewAdapter.RecyclerViewList.DayPayOrIncomeList;
import com.org.moneykeep.RecyclerViewAdapter.RecyclerViewList.MonthPayOrIncomeList;
import com.org.moneykeep.Until.ChangeDouble;
import com.org.moneykeep.databinding.FragmentHomeBinding;
import com.org.moneykeep.retrofitBean.PayEventListBean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;


public class HomeFragment extends Fragment implements HomeFragmentInterface.IView {

    //private Button but_select_type;
    private FragmentHomeBinding binding;
    private TextView  count_income, count_pay;
    //private EditText select_edit;
    private Button but_add;
    private RadioGroup radio_group;
    private RadioButton day, month, year;
    private RecyclerView recyclerView;
    private String user_account;
    private DayRecyclerViewAdapter dayRecyclerViewAdapter;
    private List<DayPayOrIncomeList> dayPayOrIncomeDate = new ArrayList<>();
    private MonthRecyclerViewAdapter monthRecyclerViewAdapter;
    private List<MonthPayOrIncomeList> monthPayOrIncomeDate = new ArrayList<>();
    private HomeFragmentInterface.IPresenter iPresenter;
    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        if(savedInstanceState == null){
            Calendar calendar = Calendar.getInstance();
            int now_year = calendar.get(Calendar.YEAR);
            int now_month = calendar.get(Calendar.MONTH) + 1;
            int now_day = calendar.get(Calendar.DAY_OF_MONTH);
            String now_date = now_year + "-" + now_month + "-" + now_day;

            homeViewModel.changDate(now_date);
            homeViewModel.changType("全部类型");
        }

        Log.i("lifecycle", "onCreateView()");
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences userdata = getActivity().getSharedPreferences("user", MODE_PRIVATE);

        user_account = userdata.getString("user_email", "");

        Findid();
        SetListen();

        iPresenter = new HomeFragmentPresenterImplements(this, getContext());

        binding.nowTime.setText(homeViewModel.getDate().getValue());
        binding.butSelectType.setText(homeViewModel.getType().getValue());


        binding.selectEdit.setInputType(InputType.TYPE_NULL);
        binding.selectEdit.setOnFocusChangeListener((view12, b) -> {
            if (b) {
                Calendar c = Calendar.getInstance();
                new DatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        String select_time = String.format("%s-%s-%s", i, i1 + 1, i2);
                        binding.selectEdit.setText(select_time);
                        binding.nowTime.setText(select_time);
                        homeViewModel.changDate(select_time);
                        if (day.isChecked()) {
                            getDayMessage();
                        } else if (month.isChecked()) {
                            getMonthMessage();
                        } else if (year.isChecked()) {
                            getYearMessage();
                        }
                        binding.selectEdit.clearFocus();
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        binding.selectEdit.setOnClickListener(view1 -> {
            Calendar c = Calendar.getInstance();
            new DatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    String select_time = String.format("%s-%s-%s", i, i1 + 1, i2);
                    binding.selectEdit.setText(select_time);
                    binding.nowTime.setText(select_time);
                    homeViewModel.changDate(select_time);
                    if (day.isChecked()) {
                        getDayMessage();
                    } else if (month.isChecked()) {
                        getMonthMessage();
                    } else if (year.isChecked()) {
                        getYearMessage();
                    }
                }
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new DayRecyclerViewAdapter(getContext(),new ArrayList<>()));

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (recyclerView.getScrollState() != 0) {
                    //recycleView正在滑动
                    but_add.getBackground().setAlpha(100); //0~255 之间任意调整
                } else {

                    if(!recyclerView.canScrollVertically(1)){
                        Toast.makeText(getApplicationContext(),"到底了",Toast.LENGTH_SHORT).show();
                        Log.i("ScrollStateChanged", "------------------->" + "到底了");
                    }
                    but_add.getBackground().setAlpha(255);
                }
            }
        });

        if (day.isChecked()) {
            getDayMessage();
        } else if (month.isChecked()) {
            getMonthMessage();
        } else if (year.isChecked()) {
            getYearMessage();
        }

        Log.i("lifecycle", "onViewCreated()");
    }


    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.i("lifecycle", "onViewStateRestored()");
    }



    private boolean isdelete;

    @Override
    public void onStart() {
        super.onStart();
        Log.i("lifecycle", "onStart()");
        SharedPreferences getBoolean = null;
        if (getBoolean == null) {
            getBoolean = getActivity().getSharedPreferences("DeleteOrUpdate", MODE_PRIVATE);
        }
        isdelete = getBoolean.getBoolean("isdelete", false);
        if (isdelete == true) {
            if (day.isChecked()) {
                getDayMessage();
            } else if (month.isChecked()) {
                getMonthMessage();
            } else if (year.isChecked()) {
                getYearMessage();
            }
            SharedPreferences.Editor editor = getBoolean.edit();
            editor.putBoolean("isdelete", false);
            editor.commit();

        }

    }

    private String deleteCost;
    private int ItemPosition;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void getDayMessageSuccessful(String s, List<DayPayOrIncomeList> newDayPayOrIncomeDate, double countIncome, double countPay) {
        if (newDayPayOrIncomeDate != null) {
            dayRecyclerViewAdapter = new DayRecyclerViewAdapter(getContext(), dayPayOrIncomeDate);
            dayPayOrIncomeDate = newDayPayOrIncomeDate;
            dayRecyclerViewAdapter.setData(dayPayOrIncomeDate);
            dayRecyclerViewAdapter.notifyDataSetChanged();
            dayRecyclerViewAdapter.setSetOnRecyclerItemLongClickListener((thisAdapter, position, DayDate) -> {
                int Id = DayDate.get(position).getId();
                DeleteDialog dialog = new DeleteDialog(getContext());
                dialog.setiOconfirmListener(new DeleteDialog.IOconfirmListener() {
                    @Override
                    public void oncofirm(DeleteDialog dialog) {
                        deleteCost = dayPayOrIncomeDate.get(position).getCost();
                        ItemPosition = position;
                        iPresenter.deletePayEvent(Id);
                    }
                }).show();
            });
            dayRecyclerViewAdapter.setOnRecyclerItemClickListener(new DayRecyclerViewAdapter.OnRecyclerItemClickListener() {
                @Override
                public void OnRecyclerOnItemClickListener(int id) {
                    ComponentName componentName = new ComponentName(getActivity(), DetailsActivity.class);
                    Intent intent = new Intent();
                    intent.setComponent(componentName);
                    Bundle bundle = new Bundle();
                    bundle.putInt("Id", id);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });

            recyclerView.setAdapter(dayRecyclerViewAdapter);
            count_income.setText(String.valueOf(countIncome));

            if (countPay == 0) {
                count_pay.setText(String.valueOf(0.0));
            } else {
                count_pay.setText(String.valueOf(-countPay));
            }

        } else {
            Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void getDayMessageUnSuccessful(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void getMonthAndYearMessageSuccessful(String s, List<MonthPayOrIncomeList> newMonthPayOrIncomeDate, Map<String, List<PayEventListBean.AllPayListDTO>> map,
                                                 List<PayEventListBean.AllPayListDTO> allSelect, double countIncome, double countPay) {
        if (map != null) {
            monthRecyclerViewAdapter = new MonthRecyclerViewAdapter(getContext(), monthPayOrIncomeDate, map);
            monthPayOrIncomeDate = newMonthPayOrIncomeDate;
            monthRecyclerViewAdapter.setMonthData(monthPayOrIncomeDate);
            monthRecyclerViewAdapter.notifyDataSetChanged();
            monthRecyclerViewAdapter.setSetOnRecyclerItemCostChangeListener(new MonthRecyclerViewAdapter.SetOnRecyclerItemCostChangeListener() {
                @Override
                public void OnRecyclerItemCostChangeListener(double cost, int position) {
                    if (cost > 0) {
                        count_income.setText(String.valueOf(ChangeDouble.subDouble(Double.parseDouble(count_income.getText().toString()), cost)));
                    } else if (cost < 0) {
                        count_pay.setText(String.valueOf(ChangeDouble.addDouble(Double.parseDouble(count_pay.getText().toString()), cost)));
                    }

                }
            });

            recyclerView.setAdapter(monthRecyclerViewAdapter);

            count_income.setText(String.valueOf(countIncome));
            if (countPay == 0) {
                count_pay.setText(String.valueOf(0.0));
            } else {
                count_pay.setText(String.valueOf(-countPay));
            }

        } else {
            Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void getMonthAndYearMessageUnSuccessful(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getYearMessageSuccessful() {

    }

    @Override
    public void getYearMessageUnSuccessful() {

    }

    @Override
    public void deletePayEventSuccessful(String s) {
        double DouDataCost = Double.valueOf(deleteCost);
        if (DouDataCost > 0) {
            double getCost = Double.valueOf(count_income.getText().toString());
            count_income.setText(String.valueOf(ChangeDouble.subDouble(getCost, DouDataCost)));
        } else {
            double getCost = Double.valueOf(count_pay.getText().toString());
            count_pay.setText(String.valueOf(ChangeDouble.addDouble(getCost, DouDataCost)));
        }
        dayRecyclerViewAdapter.removeData(ItemPosition);
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void deletePayEventUnSuccessful(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
    }

    private class Onclick implements View.OnClickListener {
        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.but_add:
                    Intent intent = new Intent(getActivity(), AddPayEventActivity.class);
                    startActivity(intent);
                    break;
                case R.id.but_select_type:
                    AllTypePickerDialog allTypePickerDialog = new AllTypePickerDialog(getContext());
                    allTypePickerDialog.but_summit((dialog, firstValue, secondValue) -> {
                        String[] stringArray;
                        String type = null;
                        if (firstValue == 0) {
                            stringArray = getContext().getResources().getStringArray(R.array.pay);
                            type = stringArray[secondValue];
                            //but_select_type.setText();
                        } else if (firstValue == 1) {
                            stringArray = getContext().getResources().getStringArray(R.array.income);
                            type = stringArray[secondValue];
                        } else if (firstValue == 2) {
                            type = "全部类型";
                            //but_select_type.setText("全部类型");
                        } else if (firstValue == 3) {
                            stringArray = getContext().getResources().getStringArray(R.array.BankCard);
                            type = stringArray[secondValue];
                        }
                        binding.butSelectType.setText(type);
                        homeViewModel.changType(type);
                        if (day.isChecked()) {
                            getDayMessage();
                        } else if (month.isChecked()) {
                            getMonthMessage();
                        } else if (year.isChecked()) {
                            getYearMessage();
                        }
                    }).show();
                    break;
            }
        }
    }

    private void SetListen() {
        Onclick onclick = new Onclick();
        CheckOnclick checkOnclick = new CheckOnclick();
        binding.selectEdit.setOnClickListener(onclick);
        but_add.setOnClickListener(onclick);
        radio_group.setOnCheckedChangeListener(checkOnclick);
        binding.butSelectType.setOnClickListener(onclick);

    }

    private class CheckOnclick implements RadioGroup.OnCheckedChangeListener {

        @SuppressLint("NonConstantResourceId")
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int ButtonId) {
            switch (ButtonId) {
                case R.id.day:
                    getDayMessage();
                    break;
                case R.id.month:
                    getMonthMessage();
                    break;
                case R.id.year:
                    getYearMessage();
                    break;

            }
        }


    }

    private void getYearMessage() {
        String select_date = homeViewModel.getDate().getValue();
        String[] select_dates = select_date.split("-");
        String select_year = select_dates[0];

        String select_type = homeViewModel.getType().getValue();
        iPresenter.getYearMessage(user_account, select_type, select_year);
    }

    private void getDayMessage() {

        String select_type = homeViewModel.getType().getValue();
        String select_date = homeViewModel.getDate().getValue();
        iPresenter.getDayMessage(user_account, select_type, select_date);


    }

    private void getMonthMessage() {
        String select_date = homeViewModel.getDate().getValue();
        String[] select_dates = select_date.split("-");

        String select_month = select_dates[1];
        String select_year = select_dates[0];


        String select_type = homeViewModel.getType().getValue();

        iPresenter.getMonthMessage(user_account, select_type, select_month, select_year);

    }


    private void Findid() {
        //now_time = getView().findViewById(R.id.now_time);
        //select_edit = getView().findViewById(R.id.select_edit);
        but_add = getView().findViewById(R.id.but_add);
        radio_group = getView().findViewById(R.id.radio_group);
        day = getView().findViewById(R.id.day);
        month = getView().findViewById(R.id.month);
        year = getView().findViewById(R.id.year);
        recyclerView = getView().findViewById(R.id.recyclerView);
        //but_select_type = getView().findViewById(R.id.but_select_type);
        count_income = getView().findViewById(R.id.count_income);
        count_pay = getView().findViewById(R.id.count_pay);

    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("lifecycle", "onStop()");
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("lifecycle", "onDestroyView()");
        binding = null;
    }
}