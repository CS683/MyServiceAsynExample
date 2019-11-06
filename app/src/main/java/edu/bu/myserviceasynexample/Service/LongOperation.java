package edu.bu.myserviceasynexample.Service;

import android.util.Log;

/**
 * Created by danazh on 10/3/17.
 */

public class LongOperation {
    public void longWait(String tag, int id){
        Log.d(tag, "execute long operation");
        long endTime = System.currentTimeMillis() + 10*1000;
        while (System.currentTimeMillis() < endTime) {
                synchronized (this) {
                    try {
                        wait(endTime - System.currentTimeMillis());
                    }catch (Exception e) {
                    }
                }
        }
        Log.d(tag, id + "  Still running");
    }
}
