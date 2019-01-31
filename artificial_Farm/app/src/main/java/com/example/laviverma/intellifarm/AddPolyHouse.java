package com.example.laviverma.intellifarm;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

public class AddPolyHouse extends AppCompatActivity {
    String poly_farms;
    ImageView back;
    private void displayDatabaseInfo() {
        // Create and/or open a database to read from it
        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                Contact.Entry.COLUMN_poly_farms,
        };

        // Perform a query on the pets table
        Cursor cursor = db.query(
                Contact.Entry.TABLE_NAME,   // The table to query
                projection,            // The columns to return
                null,                  // The columns for the WHERE clause
                null,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);                   // The sort order


        try {
            int nameColumnIndex = cursor.getColumnIndex(Contact.Entry.COLUMN_poly_farms);
            while (cursor.moveToNext()) {
                 poly_farms = cursor.getString(nameColumnIndex);
            }
        } finally {
            cursor.close();
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_poly_house);
        final ArrayList<PolyHouses> ph = new ArrayList<PolyHouses>();
        displayDatabaseInfo();
        back=(ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(AddPolyHouse.this, Dashboard.class);
                AddPolyHouse.this.startActivity(i1);
            }
        });
        String[] arr = poly_farms.split(",");

        for ( String ss : arr) {
            ph.add(new PolyHouses(ss));

        }
        Adapter adapter = new Adapter(this, ph);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PolyHouses word = ph.get(i);


            }
        });
    }
}
