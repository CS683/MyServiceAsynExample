package edu.bu.myserviceasynexample.activities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ButtonBarLayout;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import edu.bu.myserviceasynexample.R;
import edu.bu.myserviceasynexample.RESTClient.MyHttpConnection;

public class RestClientActivity extends AppCompatActivity {
    private EditText contentText;
    private final Handler mainHandler = new Handler(Looper.getMainLooper());



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_client);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

         contentText= findViewById(R.id.contentid);;

        Button showUrlBtn = findViewById(R.id.showurlbtnid);

        showUrlBtn.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){

                EditText urlText = findViewById(R.id.urlid);

                ConnectivityManager connMgr = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

                if (networkInfo != null && networkInfo.isConnected()) {
                    Toast.makeText(getApplicationContext(), "Website access begun ...",
                            Toast.LENGTH_LONG).show();

                    String urlString = urlText.getText().toString();

                    // create an Asynctask object and call its execute() method
                    // pass the url into the execute() method
                    new RestClientTask().execute(urlString);

                    //If not using Asynctask, we can use TaskUseExecutor defined below
                    // new TaskUseExecutor().executeTask(urlString);


                } else {
                    Toast.makeText(getApplicationContext(), "No network connection available.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

// To create a task run in the background and show the result in the main UI thread
    private class RestClientTask extends AsyncTask<String, Void, String> {
        // this method is executed in the background thread
        @Override
        protected String doInBackground(String... urls) {
            // urls[0] is the parameter of execute() when invoked by an AsynTask object
            try {
                // this returns a string that will be passed to onPostExecute()
                return new MyHttpConnection().accessURL(urls[0]);
            } catch (Exception e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }
        //this method is executed in the main thread
        @Override
        protected void onPostExecute(String aResult) {
            // Post: aResult is toasted
            EditText contentText = findViewById(R.id.contentid);
            contentText.setText(aResult);
            Log.d("RestClientActivity", aResult);
        }
    }

/**
 * since the Asynctask is deprecated in API 30,
 * we can define our own kind of Asynctask using Executor and handler
 *
 */

    private class TaskUseExecutor {
        // if you only need one thread
        private final Executor executor1 = Executors.newSingleThreadExecutor(); // change according to your requirements


        public void executeTask(final String url) {
            executor1.execute(new Runnable() {
                @Override
                public void run() {

                    String aResult = "";

                    try {
                        aResult = new MyHttpConnection().accessURL(url);
                    } catch (Exception e) {
                        aResult = "Unable to retrieve web page. URL may be invalid.";
                    }

                    // we use handler to post the
                    final String page = aResult;
                    mainHandler.post(new Runnable() {
                        public void run() {
                            if (contentText != null)
                                contentText.setText(page);
                        }
                    });
                }
            });
        }
    }
}

