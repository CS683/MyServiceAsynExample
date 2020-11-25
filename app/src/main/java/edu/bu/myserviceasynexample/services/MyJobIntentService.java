package edu.bu.myserviceasynexample.services;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;

public class MyJobIntentService extends JobIntentService {
    private static final String TAG="MyJobIntentService";
    private Handler mHandler = new Handler(Looper.getMainLooper());

    static final int JOB_ID = 1000;

    /**
     * Convenience method for enqueuing work in to this service.
     */
    public static void enqueueWork(Context context, Intent work) {
        enqueueWork(context, MyJobIntentService.class, JOB_ID, work);
    }

    /**
     * This method is called whenever an intent is enqueued
     * and is executed in a background worker thread.
     */
    @Override
    protected void onHandleWork(@NonNull Intent intent) {

        Log.d(TAG, "JobIntentService onHandleWork");

        // do you background job such as update database
        postToastMsg("long operation started");

        new LongOperation().longWait(TAG, 0);
        // You cannot display toast message directly here, since
        // this method is executed in the worker thread.
        // You can only display the toast message in the main/UI thread
//        Toast.makeText(getApplicationContext(),
//                        "long operation finished", Toast.LENGTH_LONG).show();

        postToastMsg("long operation finished");
    }
    public void postToastMsg(final String msg) {
        mHandler.post(new Runnable() {
            public void run() {
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            }
        });
    }
}
