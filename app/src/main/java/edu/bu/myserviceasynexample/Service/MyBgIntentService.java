package edu.bu.myserviceasynexample.Service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import static android.content.ContentValues.TAG;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MyBgIntentService extends IntentService {

    private static final String TAG = "BgIntentService";

    public Handler mHandler = null;

    public MyBgIntentService() {
        super("MyBgIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "Intent Service onHandleIntent");

        // do you background job such as update database

        mHandler.post(new Runnable() {
            public void run() {
                Toast.makeText(getApplicationContext(),
                        "service started", Toast.LENGTH_LONG);
            }

        });
        new LongOperation().longWait(TAG, 0);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "Service onStartCommand");
        if (mHandler == null)
            mHandler = new Handler();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "Service onCreate");

        // mHandler = new Handler();
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Service onDestroy");
    }


}


/*

This questions is



 */