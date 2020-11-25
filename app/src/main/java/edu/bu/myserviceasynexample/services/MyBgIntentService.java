package edu.bu.myserviceasynexample.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

/**
 * IntentService is deprecated in API 30. Instead,
 * We will use JobIntentService
 */
public class MyBgIntentService extends IntentService {

    private static final String TAG = "BgIntentService";

    // create a handler for the main thread
    public Handler mHandler = new Handler(Looper.getMainLooper());

    public MyBgIntentService() {
        super("MyBgIntentService");
    }

    /**
     * This method is executed in a separate work thread
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "Intent Service onHandleIntent");

        postToastMsg("long operation started");
        new LongOperation().longWait(TAG, 0);

        // You cannot call Toast directly here.
        // It needs to be executed in UI thread
//        Toast.makeText(getApplicationContext(),
//                        "long operation finished", Toast.LENGTH_LONG).show();

        postToastMsg("long operation finished");
    }

    /**
     *
     * This method is executed in the main UI thread
     * It will call onHandleIntent() for each received intent
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "Service onStartCommand");

        Toast.makeText(getApplicationContext(),
                "onStartCommand() called", Toast.LENGTH_LONG).show();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "Service onCreate");
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Service onDestroy");
    }

    public void postToastMsg(final String msg) {
        mHandler.post(new Runnable() {
            public void run() {
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            }
        });
    }

}

