package edu.bu.myserviceasynexample.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import edu.bu.myserviceasynexample.R;
import edu.bu.myserviceasynexample.services.MyBgServiceDT;
import edu.bu.myserviceasynexample.services.MyBgServiceST;
import edu.bu.myserviceasynexample.services.MyFgService;
import edu.bu.myserviceasynexample.services.MyJobIntentService;

public class MainActivity extends AppCompatActivity {

    private final static int CALL_PHONE_PERMREQ = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);
        Toolbar toolbar = findViewById (R.id.toolbar);
        setSupportActionBar (toolbar);

        FloatingActionButton fab = findViewById (R.id.fab);
        fab.hide ();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater ().inflate (R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId ();
        TextView myTextView = findViewById (R.id.textview1id);

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.bgserv:
                myTextView.setText ("start background service");
                startService (new Intent (this, MyBgServiceST.class));
                break;
            case R.id.bgservDfT:
                myTextView.setText ("start background service in different service");
                startService (new Intent (this, MyBgServiceDT.class));
                break;
            case R.id.bgintentserv:
                myTextView.setText ("start background intent service");
                //startService (new Intent (this, MyBgIntentService.class));
                // use job intent service instead
                 Intent mIntent = new Intent(this, MyJobIntentService.class);
                 MyJobIntentService.enqueueWork(this, mIntent);
                break;
            case R.id.boundserv:
                startActivity (new Intent (this, BoundServiceActivity.class));
                break;
            case R.id.fgserv:
                startService (new Intent (MyFgService.ACTION.STARTFOREGROUND_ACTION, null, this, MyFgService.class));
                break;
            case R.id.asyntaskconn:
                startActivity (new Intent (this, RestClientActivity.class));
                break;
            case R.id.jobscheduler:
                startActivity (new Intent (this, JobSchedulerActivity.class));
                break;

            case R.id.call:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                        ContextCompat.checkSelfPermission (this,
                                Manifest.permission.CALL_PHONE)
                                != PackageManager.PERMISSION_GRANTED) {

                    // No explanation needed; request the permission
                    ActivityCompat.requestPermissions (this,
                            new String[]{Manifest.permission.CALL_PHONE},
                            CALL_PHONE_PERMREQ);

                } else {
                    Intent callIntent = new Intent (Intent.ACTION_CALL, Uri.parse ("tel:" + "7777"));
                    startActivity (callIntent);
                }
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected (item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case CALL_PHONE_PERMREQ: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent callIntent = new Intent (Intent.ACTION_CALL, Uri.parse ("tel:" + "7777"));
                     startActivity (callIntent);
                        return;

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText (this, "need call phone permission", Toast.LENGTH_LONG).show ();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

}