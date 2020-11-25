package edu.bu.myserviceasynexample.services;

import android.util.Log;

/**
 * This is a POJO class, simulating a long operation
 * that should be executed in the background thread
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
                        Log.d(tag, "error");
                    }
                }
        }
        Log.d(tag, id + "  Still running");
    }
}
