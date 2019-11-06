package edu.bu.myserviceasynexample.Service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import edu.bu.myserviceasynexample.R;

public class MyBgServiceDT extends Service {
    private static final String TAG = "BgServiceDiffThread";

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

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        Log.d(TAG, "Service onStartCommand " + startId);
        final int currentId = startId;
        new Thread(new Runnable() {
            public void run() {
                new LongOperation().longWait(TAG,currentId);
                Log.d(TAG, "long operation finish");
            }
        }).start ();


        return Service.START_STICKY;
    }


    @Override
    public void onDestroy(){
        Log.d(TAG, "Service onDestroy");
    }
}
