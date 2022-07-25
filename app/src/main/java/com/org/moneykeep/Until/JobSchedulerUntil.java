package com.org.moneykeep.Until;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;

import com.org.moneykeep.Recevier.JobSchedulerService;


public class JobSchedulerUntil {

    private static String TAG = "successfulTest";

    public static void scheduleJob(Context context, long time) {

        JobInfo.Builder builder = new JobInfo.Builder(100,
                new ComponentName(context, JobSchedulerService.class));

        builder.setMinimumLatency(time)//延迟time毫秒执行
                .setOverrideDeadline(time + 2000);// 最晚time毫秒后执行
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
        JobScheduler mJobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        mJobScheduler.schedule(builder.build());

    }
}
