package edu.bu.myserviceasynexample.Service;

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

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        Log.d(TAG, "Service onStartCommand ");

        //some background operation here
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
