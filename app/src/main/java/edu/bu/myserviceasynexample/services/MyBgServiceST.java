package edu.bu.myserviceasynexample.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;


public class MyBgServiceST extends Service {

    private static final String TAG = "BgServiceSameThread";

    //empty constructor
    public MyBgServiceST() {
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
        Log.d(TAG, "Service onStartCommand ");

        //This long operation will block the UI thread
        // and cause the app freeze.
        // It should be executed in a background thread.
        new LongOperation().longWait(TAG, startId);

        Toast.makeText(this, "service started", Toast.LENGTH_LONG).show();
        stopSelf();
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy(){
       Log.d(TAG, "Service onDestroy");
    }
}
