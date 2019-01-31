package com.example.laviverma.intellifarm;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Profile extends AppCompatActivity implements View.OnClickListener {
TextView name,email1,number1,address1;
ImageView edit,back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        edit =(ImageView)findViewById(R.id.edit);
        name=(TextView)findViewById(R.id.P_email);
        email1=(TextView)findViewById(R.id.P_name);
        address1=(TextView)findViewById(R.id.P_address);
        number1=(TextView)findViewById(R.id.P_number);
        back =(ImageView)findViewById(R.id.back);
        back.setOnClickListener(this);
        edit.setOnClickListener(this);
     displayDatabaseInfo();
    }
    private void displayDatabaseInfo() {
        // Create and/or open a database to read from it
        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                Contact.Entry._ID,
                Contact.Entry.COLUMN_name,
                Contact.Entry.COLUMN_address,
                Contact.Entry.COLUMN_email,
                Contact.Entry.COLUMN_phone_number,
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
            int nameColumnIndex = cursor.getColumnIndex(Contact.Entry.COLUMN_name);
            int emailColumnIndex = cursor.getColumnIndex(Contact.Entry.COLUMN_email);
            int numberColumnIndex = cursor.getColumnIndex(Contact.Entry.COLUMN_phone_number);
            int addressColumnIndex = cursor.getColumnIndex(Contact.Entry.COLUMN_address);
            while (cursor.moveToNext()) {
                String currentName = cursor.getString(nameColumnIndex);
                String email = cursor.getString(emailColumnIndex);
                String number = cursor.getString(numberColumnIndex);
                String address = cursor.getString(addressColumnIndex);
             name.setText(currentName);
                email1.setText(email);
                address1.setText(address);
                number1.setText(number);
            }
        } finally {
            cursor.close();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                Intent intent = new Intent(Profile.this, Dashboard.class);
                Profile.this.startActivity(intent);
                break;
            case R.id.edit:
                Intent intent2 = new Intent(Profile.this, UpdateProfile.class);
                Profile.this.startActivity(intent2);
                break;

        }
    }
}
