package com.example.laviverma.intellifarm;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.cloudant.client.api.model.Response;

public class UpdateProfile extends AppCompatActivity implements View.OnClickListener{
    EditText name,email1,number1,address1;
    TextView save;
    ProgressDialog progress;
    ImageView back;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        back=(ImageView)findViewById(R.id.back);
       save=(TextView)findViewById(R.id.save);
        name=(EditText) findViewById(R.id.P_email);
        email1=(EditText) findViewById(R.id.P_name);
        address1=(EditText) findViewById(R.id.P_address);
        number1=(EditText) findViewById(R.id.P_number);
        save.setOnClickListener(this);
        back.setOnClickListener(this);
        progress = new ProgressDialog(this);
        progress.setTitle("Connecting");
        progress.setMessage("Updating your profile...");
        progress.setCancelable(false);
        SharedPreferences sharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE);
         id =sharedPreferences.getString("username_id","");
        populate();
    }
    class Helper extends AsyncTask<Void, String, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.show();
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progress.dismiss();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            CloudantClient client = ClientBuilder.account("3105b6ee-f089-459d-8ab7-d5df25b59662-bluemix")
                    .username("3105b6ee-f089-459d-8ab7-d5df25b59662-bluemix")
                    .password("3d900e6fa5ba28b50c592e64551deaecfb4d3fbf76ea7d245a419cb231ffa579")
                    .build();

            Database db = client.database("customer", false);
            Person p =  db.find(Person.class,id);

            p.setName(name.getText().toString());
            p.setEmail(email1.getText().toString());
            p.setPhone_number(number1.getText().toString());
            p.setAddress(address1.getText().toString());
            DbHelper dbHelper = new DbHelper(UpdateProfile.this);
            SQLiteDatabase myDB = dbHelper.getWritableDatabase();
        //    p.setId(id);
            com.cloudant.client.api.model.Response r = db.update(p);
            ContentValues cv = new ContentValues();
            cv.put(Contact.Entry.COLUMN_name,name.getText().toString()); //These Fields should be your String values of actual column names
            cv.put( Contact.Entry.COLUMN_address,address1.getText().toString());
            cv.put( Contact.Entry.COLUMN_email,email1.getText().toString());
            cv.put(  Contact.Entry.COLUMN_phone_number,number1.getText().toString());
            myDB.update(Contact.Entry.TABLE_NAME, cv, "_id="+Contact.Entry._ID, null);
        //    Response r =db.update(p);
          //  Toast.makeText(getApplicationContext(), "Profile Updated", Toast.LENGTH_SHORT).show();
            return null;

        }
    }
    public void populate(){

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
                Intent i1 = new Intent(UpdateProfile.this,Profile.class);
                UpdateProfile.this.startActivity(i1);
                break;
            case R.id.save:
              new Helper().execute();

                break;

        }
    }
}
