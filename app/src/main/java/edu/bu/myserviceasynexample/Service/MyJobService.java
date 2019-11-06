package edu.bu.myserviceasynexample.Service;

import android.annotation.TargetApi;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by danazh on 4/29/18.
 */
@TargetApi(21)
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
