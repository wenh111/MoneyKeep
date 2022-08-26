package com.org.moneykeep.Activity.ui.home;

import android.content.Context;
import android.util.Log;

import com.org.moneykeep.RecyclerViewAdapter.RecyclerViewList.DayPayOrIncomeList;
import com.org.moneykeep.RecyclerViewAdapter.RecyclerViewList.MonthPayOrIncomeList;
import com.org.moneykeep.Until.ChangeDouble;
import com.org.moneykeep.Until.ListGroupUntil;
import com.org.moneykeep.retrofitBean.PayEventBean;
import com.org.moneykeep.retrofitBean.PayEventListBean;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragmentPresenterImplements implements HomeFragmentInterface.IPresenter {
    private final HomeFragmentInterface.IView iView;
    private final HomeFragmentInterface.IModel iModel;
    private Context context;

    public HomeFragmentPresenterImplements(HomeFragmentInterface.IView iView, Context context) {
        this.iView = iView;
        iModel = new HomeFragmentModelImplements(this);
        this.context = context;
    }

    @Override
    public void deletePayEvent(int id) {
        iModel.deletePayEvent(id);
    }

    @Override
    public void getDayMessage(String account, String type, String date) {

        PayEventBean payEventBean = new PayEventBean();
        payEventBean.setAccount(account);
        payEventBean.setDate(date);

        if (!type.equals("全部类型")) {
            payEventBean.setCategory(type);
        } else {
            payEventBean.setCategory("");
        }

        iModel.SelectDayMessage(payEventBean);

    }

    @Override
    public void getMonthMessage(String user_account, String select_type, String select_month, String select_year) {
        PayEventBean payEventBean = new PayEventBean();
        payEventBean.setAccount(user_account);
        payEventBean.setMonth(select_month);
        payEventBean.setYear(select_year);
        if (!select_type.equals("全部类型")) {
            payEventBean.setCategory(select_type);
        } else {
            payEventBean.setCategory("");
        }
        iModel.SelectMonthMessage(payEventBean);
    }

    @Override
    public void getYearMessage(String user_account, String select_type, String select_year) {
        PayEventBean payEventBean = new PayEventBean();
        payEventBean.setAccount(user_account);
        payEventBean.setYear(select_year);
        if (!select_type.equals("全部类型")) {
            payEventBean.setCategory(select_type);
        } else {
            payEventBean.setCategory("");
        }
        iModel.SelectYearMessage(payEventBean);
    }

    @Override
    public void SelectDayMessageSuccessfulCallBack(PayEventListBean body) {
        if (body.getCount() == 0) {
            iView.getDayMessageSuccessful("查询成功但当前时间没数据...", null, 0.0, 0.0);
        } else {
            List<DayPayOrIncomeList> newDayPayOrIncomeDate = new ArrayList<>();
            double CountIncome = 0;
            double CountPay = 0;
            List<PayEventListBean.AllPayListDTO> payEventBeans = body.getAllPayList();
            for (PayEventListBean.AllPayListDTO date : payEventBeans) {
                int Id = date.getId();
                String[] getLocations = date.getLocation().split(" ");
                String getLocation = getLocations[getLocations.length - 1];
                double get_cost = date.getCost();
                String set_cost;

                if (get_cost > 0) {
                    CountIncome = ChangeDouble.addDouble(CountIncome, get_cost);
                    set_cost = "+" + get_cost;
                } else {
                    CountPay = ChangeDouble.addDouble(CountPay, get_cost);
                    set_cost = "" + get_cost;
                }

                String time = date.getTime();
                String[] s_time = time.split(":");

                String i_times = s_time[0] + s_time[1];

                DayPayOrIncomeList dayPayOrIncomeList = new DayPayOrIncomeList();
                dayPayOrIncomeList.setInt_time(Integer.valueOf(i_times));
                dayPayOrIncomeList.setCategory(date.getCategory());
                dayPayOrIncomeList.setPayTime(date.getTime());
                dayPayOrIncomeList.setLocation(getLocation);
                dayPayOrIncomeList.setCost(set_cost);
                dayPayOrIncomeList.setRemark(date.getRemark());
                dayPayOrIncomeList.setId(Id);
                newDayPayOrIncomeDate.add(dayPayOrIncomeList);
            }

            newDayPayOrIncomeDate.sort((dayPayOrIncomeList, t1) -> t1.getInt_time() - dayPayOrIncomeList.getInt_time());
            /*for (DayPayOrIncomeList i_time : newDayPayOrIncomeDate) {
                Log.i("allPay", "时间:" + i_time.getInt_time());
            }*/
            iView.getDayMessageSuccessful("查询成功...", newDayPayOrIncomeDate, CountIncome, CountPay);
        }


    }

    @Override
    public void SelectDayMessageUnSuccessfulCallBack(String message) {
        iView.getDayMessageUnSuccessful("查询失败:" + message);
    }

    @Override
    public void SelectMonthAndYearMessageSuccessfulCallBack(PayEventListBean body) {
        double CountIncome = 0;
        double CountPay = 0;
        if (body.getCount() == 0) {
            iView.getMonthAndYearMessageSuccessful("查询成功但当前时间没数据...", null, null, null, CountIncome, CountPay);
        } else {

            List<PayEventListBean.AllPayListDTO> allSelect = body.getAllPayList();
            Map<String, List<PayEventListBean.AllPayListDTO>> map = ListGroupUntil.groupRetrofitMonthList(allSelect);
            List<MonthPayOrIncomeList> newMonthPayOrIncomeDate = new ArrayList<>();
            for (Map.Entry<String, List<PayEventListBean.AllPayListDTO>> entry : map.entrySet()) {
                double thisAllPay = 0;
                double thisAllIncome = 0;
                String date = entry.getKey();

                MonthPayOrIncomeList payOrIncomeList = new MonthPayOrIncomeList();
                payOrIncomeList.setDate(date);

                String[] dates = date.split("-");
                String int_dates = "";

                for (String this_date : dates) {
                    if (Integer.parseInt(this_date) < 10) {
                        this_date = "0" + this_date;
                    }
                    int_dates = int_dates + this_date;
                }

                int int_date = Integer.parseInt(int_dates);
                payOrIncomeList.setInt_date(int_date);

                for (PayEventListBean.AllPayListDTO thisDay : entry.getValue()) {
                    double cost = thisDay.getCost();
                    if (cost >= 0) {
                        thisAllIncome = ChangeDouble.addDouble(thisAllIncome, cost);
                    } else if (cost < 0) {
                        thisAllPay = ChangeDouble.addDouble(thisAllPay, cost);
                    }
                }
                payOrIncomeList.setAllIncome(thisAllIncome);
                payOrIncomeList.setAllPay(-thisAllPay);
                newMonthPayOrIncomeDate.add(payOrIncomeList);
            }

            newMonthPayOrIncomeDate.sort(new Comparator<MonthPayOrIncomeList>() {
                @Override
                public int compare(MonthPayOrIncomeList monthPayOrIncomeList, MonthPayOrIncomeList t1) {
                    return t1.getInt_date() - monthPayOrIncomeList.getInt_date();
                }
            });
            for (MonthPayOrIncomeList i_date : newMonthPayOrIncomeDate) {
                Log.i("allPay", "日期:" + i_date.getInt_date());
            }
            for (PayEventListBean.AllPayListDTO allPay : allSelect) {
                double cost = allPay.getCost();
                if (cost > 0) {
                    CountIncome = ChangeDouble.addDouble(CountIncome, cost);
                } else if (cost < 0) {
                    CountPay = ChangeDouble.addDouble(CountPay, cost);
                }
            }

            iView.getMonthAndYearMessageSuccessful("展示成功...", newMonthPayOrIncomeDate, map, allSelect, CountIncome, CountPay);
        }
    }

    @Override
    public void SelectMonthAndYearMessageUnSuccessfulCallBack(String s) {
        iView.getMonthAndYearMessageUnSuccessful(s);
    }

    @Override
    public void SelectAMonthMessageSuccessfulCallBack(PayEventListBean body) {
        double CountIncome = 0;
        double CountPay = 0;
        if (body.getCount() == 0) {
            iView.getMonthAndYearMessageSuccessful("查询成功但当前时间没数据...", null, null, null, CountIncome, CountPay);
        } else {
            List<PayEventListBean.AllPayListDTO> allSelect = body.getAllPayList();
            //Map<String, List<PayEventListBean.AllPayListDTO>> map = ListGroupUntil.groupRetrofitMonthList(allSelect);

            //List<MonthPayOrIncomeList> newMonthPayOrIncomeDate = new ArrayList<>();

            /*for (Map.Entry<String, List<PayEventListBean.AllPayListDTO>> entry : map.entrySet()) {
                double thisAllPay = 0;
                double thisAllIncome = 0;
                String date = entry.getKey();

                MonthPayOrIncomeList payOrIncomeList = new MonthPayOrIncomeList();
                payOrIncomeList.setDate(date);

                String[] dates = date.split("-");
                String int_dates = "";

                for (String this_date : dates) {
                    if (Integer.parseInt(this_date) < 10) {
                        this_date = "0" + this_date;
                    }
                    int_dates = int_dates + this_date;
                }

                int int_date = Integer.parseInt(int_dates);
                payOrIncomeList.setInt_date(int_date);

                for (PayEventListBean.AllPayListDTO thisDay : entry.getValue()) {
                    double cost = thisDay.getCost();
                    if (cost >= 0) {
                        thisAllIncome = ChangeDouble.addDouble(thisAllIncome, cost);
                    } else if (cost < 0) {
                        thisAllPay = ChangeDouble.addDouble(thisAllPay, cost);
                    }
                }
                payOrIncomeList.setAllIncome(thisAllIncome);
                payOrIncomeList.setAllPay(-thisAllPay);
                newMonthPayOrIncomeDate.add(payOrIncomeList);
            }*/
            double thisAllPay = 0;
            double thisAllIncome = 0;
            String date = allSelect.get(0).getDate();

            MonthPayOrIncomeList payOrIncomeList = new MonthPayOrIncomeList();
            payOrIncomeList.setDate(date);

            int int_date = allSelect.get(0).getInt_date();
            payOrIncomeList.setInt_date(int_date);

            for (PayEventListBean.AllPayListDTO thisDay : allSelect) {
                double cost = thisDay.getCost();
                if (cost >= 0) {
                    thisAllIncome = ChangeDouble.addDouble(thisAllIncome, cost);
                } else if (cost < 0) {
                    thisAllPay = ChangeDouble.addDouble(thisAllPay, cost);
                }
            }
            payOrIncomeList.setAllIncome(thisAllIncome);
            payOrIncomeList.setAllPay(-thisAllPay);

            //newMonthPayOrIncomeDate.add(payOrIncomeList);
            body.setPayOrIncomeList(payOrIncomeList);
            for (PayEventListBean.AllPayListDTO allPay : allSelect) {
                double cost = allPay.getCost();
                if (cost > 0) {
                    CountIncome = ChangeDouble.addDouble(CountIncome, cost);
                } else if (cost < 0) {
                    CountPay = ChangeDouble.addDouble(CountPay, cost);
                }
            }


            iView.getAMonthMessageSuccessful("展示成功...", body, CountIncome, CountPay);
            //iView.getMonthAndYearMessageSuccessful("展示成功...", newMonthPayOrIncomeDate, map, allSelect, CountIncome, CountPay);
        }
    }


    @Override
    public void SelectAMonthMessageUnSuccessfulCallBack(String errorMessage) {
        iView.getMonthAndYearMessageUnSuccessful(errorMessage);
    }

    @Override
    public void getAMonthMessage(String user_account, String select_type, String select_month, String select_year, int since, int perPage) {
        PayEventBean payEventBean = new PayEventBean();
        payEventBean.setAccount(user_account);
        payEventBean.setMonth(select_month);
        payEventBean.setYear(select_year);
        if (!select_type.equals("全部类型")) {
            payEventBean.setCategory(select_type);
        } else {
            payEventBean.setCategory("");
        }
        iModel.SelectAMonthMessage(payEventBean, since, perPage);
    }

    @Override
    public void deletePayEventSuccessfulCallBack(String s) {
        iView.deletePayEventSuccessful(s);
    }

    @Override
    public void deletePayEventUnSuccessfulCallBack(String s) {
        iView.deletePayEventUnSuccessful(s);
    }
}
