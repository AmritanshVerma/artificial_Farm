package com.example.laviverma.intellifarm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Lavi Verma on 16-Jul-18.
 */

public class AddNotification extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_poly_house);
        final ArrayList<Notification> n = new ArrayList<Notification>();

        n.add(new Notification("red","21312231"));
       n.add(new Notification("mustard yellow","3123131332"));
        n.add(new Notification("dusty yellow","#@!#!@#@!132"));



        AdapterNotification adapter = new AdapterNotification(AddNotification.this, n);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               Notification n2 = n.get(i);


            }
        });
    }
}
