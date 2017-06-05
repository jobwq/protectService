package com.ctoyo.protect.service;

import android.annotation.SuppressLint;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.ctoyo.protect.utils.Utils;


@SuppressLint("NewApi")
public class JobScheduleService extends JobService {
    private int kJobId = 0;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("job", "jobService启动");
        scheduleJob(getJobInfo());
        return START_NOT_STICKY;
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.i("job", "onStartJob");
        boolean isLocalServiceWork = Utils.isServiceWork(this, "com.ctoyo.protect.service.Service1");
        boolean isRemoteServiceWork = Utils.isServiceWork(this, "com.ctoyo.protect.serviceService2");
        if(!isLocalServiceWork||
                !isRemoteServiceWork){
            this.startService(new Intent(this,Service1.class));
            this.startService(new Intent(this,Service2.class));
            Toast.makeText(this, "进程复活", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.i("job", "onStopJob");
        scheduleJob(getJobInfo());
        return true;
    }

    public void scheduleJob(JobInfo t) {
        Log.i("job ", "scheduleJob");
        JobScheduler tm =
                (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        tm.schedule(t);
    }

    public JobInfo getJobInfo(){
        JobInfo.Builder builder = new JobInfo.Builder(kJobId++, new ComponentName(this, JobScheduleService.class));
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
        builder.setPersisted(true);
        builder.setRequiresCharging(false);
        builder.setRequiresDeviceIdle(false);

        builder.setPeriodic(1000);
        return builder.build();
    }

}