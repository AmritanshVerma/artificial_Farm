package com.example.laviverma.intellifarm;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
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

public class ChangePassword extends AppCompatActivity  {

    EditText c_pass,n_pass,r_pass;
    ProgressDialog progress;
    ImageView back;
    TextView save;
    boolean success =false;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        back=(ImageView)findViewById(R.id.back);
        save=(TextView)findViewById(R.id.save);
        c_pass=(EditText) findViewById(R.id.P_email);
        n_pass=(EditText) findViewById(R.id.P_address);
        r_pass=(EditText) findViewById(R.id.P_name);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((n_pass.getText().toString()).equals(r_pass.getText().toString())) {
                    new Helper2().execute();
              //      if(success==true) {
            //            Intent i2 = new Intent(ChangePassword.this, Dashboard.class);
           //             ChangePassword.this.startActivity(i2);
          //          }
                }
                else {
                    new android.app.AlertDialog.Builder(ChangePassword.this)
                            .setTitle("Password Change Failed")
                            .setMessage("new and retyped password does not match")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                }
                            })
                            .show();
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(ChangePassword.this, Dashboard.class);
                ChangePassword.this.startActivity(i1);
            }
        });
        progress = new ProgressDialog(this);
        progress.setTitle("Connecting");
        progress.setMessage("Changing your password...");
        progress.setCancelable(false);
        SharedPreferences sharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE);
        id =sharedPreferences.getString("username_id","");
    }
    class Helper2 extends AsyncTask<Void, String, Void> {
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
if(!((p.getPassword()).equals(c_pass.getText().toString())))
            {
                Log.v("hey","uncess");

            }
            else{
            p.setPassword(n_pass.getText().toString());
            DbHelper dbHelper = new DbHelper(ChangePassword.this);
            SQLiteDatabase myDB = dbHelper.getWritableDatabase();
            //    p.setId(id);
            com.cloudant.client.api.model.Response r = db.update(p);
            ContentValues cv = new ContentValues();
            cv.put(Contact.Entry.COLUMN_password,n_pass.getText().toString()); //These Fields should be your String values of actual column names

            myDB.update(Contact.Entry.TABLE_NAME, cv, "_id="+Contact.Entry._ID, null);
   success=true;}
            //    Response r =db.update(p);
            //  Toast.makeText(getApplicationContext(), "Profile Updated", Toast.LENGTH_SHORT).show();
            return null;

        }
    }




}
