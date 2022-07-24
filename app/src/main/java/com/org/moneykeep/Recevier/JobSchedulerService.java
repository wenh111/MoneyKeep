package com.org.moneykeep.Recevier;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.org.moneykeep.Until.JobSchedulerUntil;


public class JobSchedulerService extends JobService {
    //private static final int MESSAGE_ID = 100;
    String TAG = "successfulTest";
    private MessageRecevierHelp messageRecevierHelp;

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.i(TAG, "onStartJob");

        /*messageRecevier = new MessageRecevier();
        IntentFilter filter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        filter.setPriority(2147483647);
        registerReceiver(messageRecevier,filter);
        Log.i(TAG, "registerReceiverSuccessful");*/
        messageRecevierHelp = new MessageRecevierHelp();
        IntentFilter filter = new IntentFilter();
        filter.addAction("ACTION");
        registerReceiver(messageRecevierHelp,filter);


        Intent intent = new Intent();
        intent.setAction("ACTION");
        sendBroadcast(intent);

        JobSchedulerUntil.scheduleJob(getApplicationContext(),1000 * 60 * 15 );

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.i(TAG, "onStopJob");

        unregisterReceiver(messageRecevierHelp);
        return true;
    }
}
