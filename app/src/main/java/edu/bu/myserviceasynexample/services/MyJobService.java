package edu.bu.myserviceasynexample.services;

import android.annotation.TargetApi;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MyJobService extends JobService {

    @Override
    public boolean onStartJob(JobParameters params) {
        Toast.makeText(this, "Job is scheduled", Toast.LENGTH_LONG).show();

        // do some work here

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {

        Log.d("JobService", "stop job");
        return false;
    }
}
