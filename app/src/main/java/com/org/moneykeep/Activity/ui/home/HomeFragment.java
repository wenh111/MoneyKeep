package com.org.moneykeep.Activity.ui.home;

import static android.content.Context.MODE_PRIVATE;

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
import android.view.MotionEvent;
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

import com.daimajia.swipe.util.Attributes;
import com.org.moneykeep.Activity.AddPayEventView.AddPayEventActivity;
import com.org.moneykeep.Activity.DetailsView.DetailsActivity;
import com.org.moneykeep.Dialog.AllTypePickerDialog;
import com.org.moneykeep.Dialog.DeleteDialog;
import com.org.moneykeep.R;
import com.org.moneykeep.RecyclerViewAdapter.AMonthRecyclerViewAdapter;
import com.org.moneykeep.RecyclerViewAdapter.DayRecyclerViewAdapter;
import com.org.moneykeep.RecyclerViewAdapter.RecyclerViewList.DayPayOrIncomeList;
import com.org.moneykeep.Until.ChangeDouble;
import com.org.moneykeep.databinding.FragmentHomeBinding;
import com.org.moneykeep.retrofitBean.PayEventListBean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class HomeFragment extends Fragment implements HomeFragmentInterface.IView {

    //private Button but_select_type;
    private FragmentHomeBinding binding;
    private TextView count_income, count_pay;
    //private EditText select_edit;
    private Button but_add;
    private RadioGroup radio_group;
    private RadioButton day, month, year;
    private RecyclerView recyclerView;
    private String user_account;
    private DayRecyclerViewAdapter dayRecyclerViewAdapter;
    private List<DayPayOrIncomeList> dayPayOrIncomeDate = new ArrayList<>();
    //private MonthRecyclerViewAdapter monthRecyclerViewAdapter;
    //private List<MonthPayOrIncomeList> monthPayOrIncomeDate = new ArrayList<>();
    private HomeFragmentInterface.IPresenter iPresenter;
    private HomeViewModel homeViewModel;
    private AMonthRecyclerViewAdapter amonthRecyclerViewAdapter;
    public HomeFragmentInterface.LoadInterface loadInterface;
    private boolean needRefresh;
    SharedPreferences getBoolean;

    public void setLoadInterface(HomeFragmentInterface.LoadInterface loadInterface) {
        this.loadInterface = loadInterface;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        if (savedInstanceState == null) {
            Calendar calendar = Calendar.getInstance();
            int now_year = calendar.get(Calendar.YEAR);
            int now_month = calendar.get(Calendar.MONTH) + 1;
            int now_day = calendar.get(Calendar.DAY_OF_MONTH);
            String now_date = now_year + "-" + now_month + "-" + now_day;
            homeViewModel.dataChange(-1, -1);
            homeViewModel.dataLoadOver(false);
            homeViewModel.changDate(now_date);
            homeViewModel.changType("全部类型");
            getBoolean = getActivity().getSharedPreferences("DeleteOrUpdate", MODE_PRIVATE);
            isdelete = getBoolean.getBoolean("isdelete", false);
            if (isdelete) {
                SharedPreferences.Editor editor = getBoolean.edit();
                editor.putBoolean("isdelete", false);
                editor.apply();
            }
        }

        Log.i("lifecycle", "onCreateView()");
        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences userdata = requireActivity().getSharedPreferences("user", MODE_PRIVATE);
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
        DayRecyclerViewAdapter dayRecyclerViewAdapter = new DayRecyclerViewAdapter(getContext(), new ArrayList<>());
        dayRecyclerViewAdapter.setMode(Attributes.Mode.Single);
        recyclerView.setAdapter(dayRecyclerViewAdapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (recyclerView.getScrollState() != 0) {
                    //recycleView正在滑动
                    but_add.getBackground().setAlpha(100); //0~255 之间任意调整
                } else {
                    but_add.getBackground().setAlpha(255);
                }

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (!recyclerView.canScrollVertically(1)) {
                        int since = homeViewModel.getSince().getValue() == null ? -1 : homeViewModel.getSince().getValue();
                        int perPage = homeViewModel.getPerPage().getValue() == null ? -1 : homeViewModel.getPerPage().getValue();

                        if (loadInterface != null) {
                            Log.i("ScrollStateChanged", "------------------->" + "loadInterface != null");
                            loadInterface.OnLoadLister(since, perPage);
                        }

                        Log.i("ScrollStateChanged", "------------------->" + "到底了");
                    }
                }
            }
        });
        setLoadInterface(new HomeFragmentInterface.LoadInterface() {
            @Override
            public void OnLoadLister(Integer since, Integer perPage) {
                Log.i("ScrollStateChanged", "------------------->" + "OnLoadLister");
                if (Boolean.FALSE.equals(homeViewModel.getIsOver().getValue())) {
                    Log.i("ScrollStateChanged", "------------------->" + "homeViewModel.getIsOver().getValue()) == false");
                    if (month.isChecked()) {
                        getNextDayMessage(0);
                        //getMonthMessage();
                    } else if (year.isChecked()) {
                        getNextDayMessage(1);
                    }
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

    private void getNextDayMessage(int selectType) {
        needRefresh = false;
        String select_date = homeViewModel.getDate().getValue();
        String[] select_dates = select_date.split("-");

        String select_month = select_dates[1];
        String select_year = select_dates[0];

        if (selectType == 1) {
            select_month = "";
        }
        String select_type = homeViewModel.getType().getValue();
        int since = homeViewModel.getSince().getValue() == null ? -1 : homeViewModel.getSince().getValue();
        int perPage = homeViewModel.getPerPage().getValue() == null ? -1 : homeViewModel.getPerPage().getValue();

        //iPresenter.getMonthMessage(user_account, select_type, select_month, select_year);
        iPresenter.getAMonthOrYearMessage(user_account, select_type, select_month, select_year, since, perPage, selectType);
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
        getBoolean = getActivity().getSharedPreferences("DeleteOrUpdate", MODE_PRIVATE);
        isdelete = getBoolean.getBoolean("isdelete", false);
        if (isdelete) {
            if (day.isChecked()) {
                getDayMessage();
            } else if (month.isChecked()) {
                getMonthMessage();
            } else if (year.isChecked()) {
                getYearMessage();
            }
            SharedPreferences.Editor editor = getBoolean.edit();
            editor.putBoolean("isdelete", false);
            editor.apply();

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



    /*@Override
    public void getMonthAndYearMessageUnSuccessful(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
    }*/

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void getAMonthOrYearMessageSuccessful(String s, PayEventListBean body, double countIncome, double countPay, int selectType) {
        Log.i("getAMonthMessageSuccessful", "到达这个位置");
        if (body != null) {
            Log.i("getAMonthMessageSuccessful", "到(body != null)位置");
            if (needRefresh) {
                Log.i("getAMonthMessageSuccessful", "到(isFirst)位置");
                amonthRecyclerViewAdapter = new AMonthRecyclerViewAdapter(getContext(), new ArrayList<>());
                List<PayEventListBean> payEventListBeans = new ArrayList<>();
                payEventListBeans.add(body);
                amonthRecyclerViewAdapter.setMonthData(payEventListBeans);
                amonthRecyclerViewAdapter.notifyDataSetChanged();
                amonthRecyclerViewAdapter.setSetOnRecyclerItemCostChangeListener(new AMonthRecyclerViewAdapter.SetOnRecyclerItemCostChangeListener() {
                    @Override
                    public void OnRecyclerItemCostChangeListener(double cost, int position) {
                        if (cost > 0) {
                            count_income.setText(String.valueOf(ChangeDouble.subDouble(Double.parseDouble(count_income.getText().toString()), cost)));
                        } else if (cost < 0) {
                            count_pay.setText(String.valueOf(ChangeDouble.addDouble(Double.parseDouble(count_pay.getText().toString()), cost)));
                        }

                    }
                });
                recyclerView.setAdapter(amonthRecyclerViewAdapter);
                count_income.setText(String.valueOf(countIncome));
                if (countPay == 0) {
                    count_pay.setText(String.valueOf(0.0));
                } else {
                    count_pay.setText(String.valueOf(-countPay));
                }

            } else {
                Log.i("getAMonthMessageSuccessful", "到(else)位置");
                amonthRecyclerViewAdapter.addData(body);
                count_income.setText(String.valueOf(ChangeDouble.addDouble(Double.parseDouble(count_income.getText().toString()), body.getPayOrIncomeList().getAllIncome())));
                count_pay.setText(String.valueOf(ChangeDouble.addDouble(Double.parseDouble(count_pay.getText().toString()), body.getPayOrIncomeList().getAllPay())));
            }
            if (body.getPerPage() == 0) {
                homeViewModel.dataLoadOver(true);
                amonthRecyclerViewAdapter.setHasMore(false);
            } else {
                homeViewModel.dataLoadOver(false);
                amonthRecyclerViewAdapter.setHasMore(true);
            }
            homeViewModel.dataChange(body.getSince(), body.getPerPage());
            boolean state = isRecyclerScrollable(recyclerView);
            Log.i("linearManager", "state = " + state);
            if (!state) {
                /*if (loadInterface != null) {
                    int since = homeViewModel.getSince().getValue() == null ? -1 : homeViewModel.getSince().getValue();
                    int perPage = homeViewModel.getPerPage().getValue() == null ? -1 : homeViewModel.getPerPage().getValue();
                    loadInterface.OnLoadLister(body.getSince(), body.getPerPage());
                }*/
                getNextDayMessage(selectType);
            }
        } else {
            Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
        }
    }

    public boolean isRecyclerScrollable(RecyclerView recyclerView) {
        return recyclerView.computeHorizontalScrollRange() > recyclerView.getWidth() || recyclerView.computeVerticalScrollRange() > recyclerView.getHeight();
    }

    @Override
    public void getAMonthOrYearMessageUnSuccessful(String errorMessage) {
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void deletePayEventSuccessful(String s) {
        double DouDataCost = Double.parseDouble(deleteCost);
        if (DouDataCost > 0) {
            double getCost = Double.parseDouble(count_income.getText().toString());
            count_income.setText(String.valueOf(ChangeDouble.subDouble(getCost, DouDataCost)));
        } else {
            double getCost = Double.parseDouble(count_pay.getText().toString());
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
            amonthRecyclerViewAdapter = null;
            homeViewModel.dataLoadOver(false);
            homeViewModel.dataChange(-1, -1);
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
        needRefresh = true;
        String select_date = homeViewModel.getDate().getValue();
        String[] select_dates = select_date.split("-");
        String select_year = select_dates[0];

        String select_type = homeViewModel.getType().getValue();
        //iPresenter.getYearMessage(user_account, select_type, select_year);
        iPresenter.getAMonthOrYearMessage(user_account, select_type, "", select_year, -1, -1, 1);
    }

    private void getDayMessage() {

        String select_type = homeViewModel.getType().getValue();
        String select_date = homeViewModel.getDate().getValue();
        iPresenter.getDayMessage(user_account, select_type, select_date);


    }

    private void getMonthMessage() {
        needRefresh = true;
        String select_date = homeViewModel.getDate().getValue();
        String[] select_dates = select_date.split("-");

        String select_month = select_dates[1];
        String select_year = select_dates[0];

        String select_type = homeViewModel.getType().getValue();

       /* int since = homeViewModel.getSince().getValue() == null ? -1 : homeViewModel.getSince().getValue();
        int perPage = homeViewModel.getPerPage().getValue() == null ? -1 : homeViewModel.getPerPage().getValue();*/

        //iPresenter.getMonthMessage(user_account, select_type, select_month, select_year);
        iPresenter.getAMonthOrYearMessage(user_account, select_type, select_month, select_year, -1, -1, 0);
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