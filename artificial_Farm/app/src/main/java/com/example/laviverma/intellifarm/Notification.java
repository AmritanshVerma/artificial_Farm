package com.example.laviverma.intellifarm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Notification {


    private String notification,timestamp;

    public Notification(String n,String t) {

        notification = n;
        timestamp = t;
    }

    public String getNotification() {
        return notification;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
