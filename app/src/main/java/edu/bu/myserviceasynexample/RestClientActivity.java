package edu.bu.myserviceasynexample;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ButtonBarLayout;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.bu.myserviceasynexample.RESTClient.MyHttpConnection;

public class RestClientActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_client);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button showUrlBtn = (Button)findViewById(R.id.showurlbtnid);

        showUrlBtn.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){

                EditText urlText = (EditText)findViewById(R.id.urlid);

                ConnectivityManager connMgr = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

                if (networkInfo != null && networkInfo.isConnected()) {
                    Toast.makeText(getApplicationContext(), "Website access begun ...",
                            Toast.LENGTH_LONG).show();

                    String urlString = urlText.getText().toString();

                    new RestClientTask().execute(urlString);
                } else {
                    Toast.makeText(getApplicationContext(), "No network connection available.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

// To create a task run in the background and show the result in the main UI thread
    private class RestClientTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            // Pre: urls[0] is the parameter of execute() (i.e., when invoked by an AsycTask object)
            // Post = Postconditions for MyHttpsConnection().accessURL(urls[0])
            try {
                return new MyHttpConnection().accessURL(urls[0]);
            } catch (Exception e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }
        @Override
        protected void onPostExecute(String aResult) {
            // Post: aResult is toasted
            EditText contentText = (EditText)findViewById(R.id.contentid);
            contentText.setText(aResult);
            Log.d("hii", aResult);

        }

    }

}

