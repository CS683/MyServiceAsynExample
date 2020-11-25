package edu.bu.myserviceasynexample.services;

import android.app.Service;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import androidx.annotation.RequiresApi;
import android.util.Log;

import java.util.Date;
import java.util.Locale;

public class MyLocalBoundService extends Service {

    private static final String TAG = "LocalBoundService";

    private final IBinder myBinder = new MyLocalBinder();

    public MyLocalBoundService() {
    }

    @Override
    public void onCreate(){
        Log.d(TAG, "Service onCreate");
    }


    /**
     *
     * Create a IBinder as the communication channel
     * between the client and this service
     */
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG,"service onBind");
        return myBinder;
    }

    @Override
    public boolean onUnbind(Intent intent){
        Log.d(TAG,"service onUnBind");
        return true;
    }

    @Override
    public void onDestroy(){
        Log.d(TAG, "Service onDestroy");
    }

    // simply returns the service itself
    // as this is a local service
    public class MyLocalBinder extends Binder  {
        public MyLocalBoundService getService() {
            return MyLocalBoundService.this;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public String getCurrentTime() {
        SimpleDateFormat dateFormat =
                new SimpleDateFormat("HH:mm:ss MM/dd/yyyy", Locale.US);
        return (dateFormat.format(new Date()));
    }
}
