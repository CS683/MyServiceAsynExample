package edu.bu.myserviceasynexample.RESTClient;

import android.net.http.HttpResponseCache;
import android.util.JsonReader;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import static android.content.ContentValues.TAG;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;



public class MyHttpConnection {

    public MyHttpConnection(){}

    public String accessURL(String aUrl) throws IOException {
        InputStream inStream = null;
        final int MAX_LEN = 1500;
        String returnString;
        // Create URL
        try {
            URL url = new URL(aUrl);
            // Create connection
            HttpURLConnection myConn =
                    (HttpURLConnection) url.openConnection();

            myConn.setReadTimeout(10000 /* milliseconds */);
            myConn.setConnectTimeout(15000 /* milliseconds */);

            myConn.setRequestMethod("GET");
            myConn.setDoInput(true);

            myConn.connect();

            if (myConn.getResponseCode() == 200) {
                // Success
                // Further processing here
               inStream = myConn.getInputStream();
                InputStreamReader reader =
                        new InputStreamReader(inStream, "UTF-8");
               char[] buffer = new char[MAX_LEN];
                reader.read(buffer);
                returnString = new String(buffer);

            }else{
                returnString = new String( "errorcode: " + myConn.getResponseCode() + " " +
                        myConn.getResponseMessage().toString());
            }
            myConn.disconnect();
            return returnString;
        } catch (IOException e) {
            return "error";
        }  finally {
            if (inStream != null) {
                inStream.close();
            }

        }
    }


//    public Greeting parseGreeting(InputStreamReader inReader) throws IOException {
//        Greeting greeting = new Greeting();
//        JsonReader jsonReader = new JsonReader(inReader);
//        jsonReader.beginObject();
//        while (jsonReader.hasNext()){
//            String key = jsonReader.nextName();
//            if (key.equals("id")) {
//                greeting.setId(Integer.parseInt(jsonReader.nextString()));
//            }else if (key.equals("content")) {
//                greeting.setGreetingname(jsonReader.nextString());
//            }
//        }
//        return greeting;
//    }


}
