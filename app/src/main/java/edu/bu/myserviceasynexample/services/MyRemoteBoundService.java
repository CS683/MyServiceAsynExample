package edu.bu.myserviceasynexample.services;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.widget.Toast;

/**
 * This is a remote bound service
 * This service runs in another process my_process
 * as specified in the manifest file
 */
public class MyRemoteBoundService extends Service {

    private static final String TAG = "RemoteBoundService";

    // Since this service will not be in the same process as the client
    // we need to use a messenger to create between different processes
    final Messenger myMessenger = new Messenger (new IncomingHandler());

    public MyRemoteBoundService() {
    }

    @Override
    public void onCreate(){
        Log.d(TAG, "Service onCreate");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG,"service onBind");
       return myMessenger.getBinder();
    }

    @Override
    public boolean onUnbind(Intent intent){
        Log.d(TAG,"service onUnBind");
        return false;
    }

    @Override
    public void onDestroy(){
        Log.d(TAG, "Service onDestroy");
    }

    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            Bundle data = msg.getData();
            String dataString = data.getString("MyString");
        }
    }


}
