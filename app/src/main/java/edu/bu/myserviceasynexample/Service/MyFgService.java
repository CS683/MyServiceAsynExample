
package edu.bu.myserviceasynexample.Service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationCompat.Builder;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import androidx.core.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import edu.bu.myserviceasynexample.MainActivity;
import edu.bu.myserviceasynexample.R;


public class MyFgService extends Service {

    private static final int NOTIFICATION_SERVICE_ID = 101;
    private static final int CALL_PHONE_PERMREQ = 1;

    public class ACTION {
        public final static String ACTION1 = "edu.bu.myserviceasynexample.action.action1";
        public final static String MAIN_ACTION = "edu.bu.myserviceasynexample.action.main";

        public final static String PLAY_ACTION = "edu.bu.myserviceasynexample.action.play";
        public final static String ALARM_ACTION = "edu.bu.myserviceasynexample.action.alarm";

        public final static String CALL_ACTION = "edu.bu.myserviceasynexample.action.call";

        public final static String STARTFOREGROUND_ACTION = "edu.bu.myserviceasynexample..action.startforeground";
        public final static String STOPFOREGROUND_ACTION = "edu.bu.myserviceasynexample.stopforeground";
    }

    private static final String LOG_TAG = "ForegroundService";

    private NotificationManager notificationManager;

    public MyFgService() {
    }



    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {



        if (intent.getAction ().equals (ACTION.STARTFOREGROUND_ACTION)) {
            Log.i (LOG_TAG, "start service ");

            notificationManager = (NotificationManager)
                    this.getSystemService (Context.NOTIFICATION_SERVICE);

            Notification notification = buildNotification ();
      //      notificationManager.notify (NOTIFICATION_SERVICE_ID, notification);

            startForeground(NOTIFICATION_SERVICE_ID, notification);

        } else if (intent.getAction ().equals (ACTION.ACTION1)) {
            new Thread (new Runnable () {
                public void run() {
                    //mimic long operation
                    for (int i = 0; i < 100000000; i++) ;
                    Log.i (LOG_TAG, "action1 done");
                }
            }).start ();
            Log.i (LOG_TAG, "ACTION1 started");
        } else if (intent.getAction ().equals (ACTION.PLAY_ACTION)) {

            Log.i (LOG_TAG, "play action");
            Toast.makeText (this, "play ...", Toast.LENGTH_LONG).show ();
        } else if (intent.getAction ().equals (ACTION.ALARM_ACTION)) {
            // get the reference to the system alarm manager
            AlarmManager mAlarmManager = (AlarmManager)
                    getSystemService(Context.ALARM_SERVICE);

            //create a pendingIntent to specify the action.
            Intent serviceIntent = new Intent(this, MyFgService.class);
            serviceIntent.setAction(ACTION.PLAY_ACTION);
            PendingIntent pendingIntent = PendingIntent.getService(this, 0, serviceIntent, 0);
            mAlarmManager.cancel(pendingIntent);

           // mAlarmManager.set(AlarmManager.RTC_WAKEUP, 5000, pendingIntent);
            mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, 5000, 5000, pendingIntent);

        }
        return START_STICKY;
    }



    public Notification buildNotification(){
        Intent notificationIntent = new Intent(this, MainActivity.class);
        notificationIntent.setAction(ACTION.MAIN_ACTION);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);


        Intent playIntent = new Intent(this, MyFgService.class);
        playIntent.setAction(ACTION.PLAY_ACTION);
        PendingIntent pplayIntent = PendingIntent.getService(this, 0,
                playIntent, 0);

        Intent playlaterIntent = new Intent(this, MyFgService.class);
        playIntent.setAction(ACTION.ALARM_ACTION);
        PendingIntent pplaylaterIntent = PendingIntent.getService(this, 0,
                playIntent, 0);

        Notification notification;

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            notification = new NotificationCompat.Builder (this)
                    .setContentTitle ("Foreground Service Running")
                    .setContentText ("Which service would you like?")
                    .setSmallIcon (R.drawable.ic_fgservice)
                    .setContentIntent (pendingIntent)
                    .setOngoing (true)
                    .addAction (R.drawable.ic_play, "Play",
                            pplayIntent)
                    .addAction (R.drawable.ic_play, "Play later",
                        pplaylaterIntent).build ();
        }else {

            // The user-visible name of the channel.
            CharSequence name = "edu.bu";
            String channelId = "101";

            // The user-visible description of the channel.
            String description = "channel description";
            NotificationChannel channel = new NotificationChannel(channelId, name,
                    NotificationManager.IMPORTANCE_DEFAULT);

            // Configure the notification channel.
            channel.setDescription(description);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);
            channel.enableLights(true);
            channel.setShowBadge(true);

            notificationManager.createNotificationChannel(channel);

            NotificationCompat.Action playAction = new NotificationCompat.Action.Builder(R.drawable.ic_play,
                    "play", pplayIntent).build();
            NotificationCompat.Action playLaterAction = new NotificationCompat.Action.Builder(R.drawable.ic_play,
                    "play later", pplayIntent).build();



            notification =  new NotificationCompat.Builder(this,channelId)
                    .setSmallIcon(R.drawable.ic_fgservice)
                    .setContentTitle ("Example Foreground Service")
                    .setContentText ("Foreground Service")
                    .setContentIntent(pendingIntent)
                    .addAction (playAction)
                    .addAction (playLaterAction)
                    .build();
        }

        return notification;


    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(LOG_TAG, "In onDestroy");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // Used only in case of bound services.
        return null;
    }
}
