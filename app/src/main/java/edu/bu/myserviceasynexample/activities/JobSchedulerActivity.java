package edu.bu.myserviceasynexample.activities;

import android.annotation.TargetApi;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import edu.bu.myserviceasynexample.R;
import edu.bu.myserviceasynexample.services.MyJobService;

public class JobSchedulerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_scheduler);
    }

    @TargetApi(21)
    public void scheduleJob(View v){

        int jobId  = 0;
        JobInfo.Builder jobInfoBuilder =  new JobInfo.Builder(jobId,
                new ComponentName(this, MyJobService.class));
        jobInfoBuilder.setRequiresCharging(true);

        JobInfo myJobInfo = jobInfoBuilder.build();
        JobScheduler scheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        scheduler.schedule(myJobInfo);
    }



}
