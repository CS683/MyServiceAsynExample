package edu.bu.myserviceasynexample.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyBgServiceDT extends Service {
    private static final String TAG = "BgServiceDiffThread";
    Thread thread;

    public MyBgServiceDT() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate(){
        Log.d(TAG, "Service onCreate");
    }

    /**
     * The main method needs to be override in the service.
     * It is called when the service receives the intent
     * from startService(intent)
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        Log.d(TAG, "Service onStartCommand " + startId);
        final int currentId = startId;

        // create a background thread to execute this long operation
        thread = new Thread(new Runnable() {
            public void run() {
                new LongOperation().longWait(TAG,currentId);
                Log.d(TAG, "long operation finish");
            }
        });
        thread.start();

        stopSelf();
        return Service.START_STICKY;
    }


    @Override
    public void onDestroy(){
        Log.d(TAG, "Service onDestroy");

    }
}
