package edu.bu.myserviceasynexample;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import edu.bu.myserviceasynexample.Service.MyLocalBoundService;
import edu.bu.myserviceasynexample.Service.MyRemoteBoundService;

public class BoundServiceActivity extends AppCompatActivity {

    private static final String TAG = "BoundServiceActivity";

    MyLocalBoundService myLocalBoundService;
    boolean isLocalBound = false;
    boolean isRemoteBound = false;
    Messenger myMessenger = null;

    ServiceConnection localConn, remoteConn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bound_service);
    }

    public void startLocalBoundService(View v) {

        //create a service connection to monitor the connection with the boundservice
        localConn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                MyLocalBoundService.MyLocalBinder binder =
                        (MyLocalBoundService.MyLocalBinder) service;
                myLocalBoundService = binder.getService();
                isLocalBound = true;
                Log.d(TAG, "local service is connected");
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                isLocalBound = false;
                Log.d(TAG, "local service is disconnected");
            }
        };

        Intent intent = new Intent(this, MyLocalBoundService.class);
        bindService(intent,localConn, Context.BIND_AUTO_CREATE);

    }

    public void startRemoteBoundService(View v) {
        remoteConn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder serviceBinder) {
                myMessenger = new Messenger(serviceBinder);
                isRemoteBound = true;
                Log.d(TAG, "remote service is connected");
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                isRemoteBound = false;
                Log.d(TAG, "remote service is disconnected");
            }
        };

        Intent intent = new Intent(this, MyRemoteBoundService.class);
        bindService(intent, remoteConn, Context.BIND_AUTO_CREATE);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void showTime(View v){
        TextView myTextView = (TextView) findViewById(R.id.textView);
        myTextView.setText(myLocalBoundService.getCurrentTime());
    }

    public void sendMsg(View v){
        if (!isRemoteBound) return;
        Message msg = Message.obtain();

        Bundle bundle = new Bundle();
        bundle.putString("MyString", "Message Received");
        msg.setData(bundle);

        try{
            myMessenger.send(msg);
        }catch (RemoteException e){
            e.printStackTrace();
        }
    }

    public void unBindRemoteService(View v){
        if (isRemoteBound)
            unbindService(remoteConn);
        isRemoteBound = false;
    }

    public void unBindLocalService(View v){
        if (isLocalBound)
            unbindService(localConn);
        isLocalBound = false;
    }
}
