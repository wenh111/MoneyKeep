package com.org.moneykeep.Recevier;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MessageRecevierHelp extends BroadcastReceiver {
    String TAG = "successfulTest";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "============> ACTION");
    }
}
