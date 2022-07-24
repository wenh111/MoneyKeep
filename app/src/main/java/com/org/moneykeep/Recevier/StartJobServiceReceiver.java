package com.org.moneykeep.Recevier;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.org.moneykeep.Until.JobSchedulerUntil;

public class StartJobServiceReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        JobSchedulerUntil.scheduleJob(context,1000);
    }
}
