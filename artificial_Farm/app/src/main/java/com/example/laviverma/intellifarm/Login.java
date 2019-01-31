package com.example.laviverma.intellifarm;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.media.tv.TvContentRating;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.cloudant.client.api.model.IndexField;
import com.google.android.gms.plus.model.people.Person;
import com.ibm.mobilefirstplatform.clientsdk.android.core.api.BMSClient;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPPush;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPPushException;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPPushNotificationListener;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPPushResponseListener;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPSimplePushNotification;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Login extends AppCompatActivity implements View.OnClickListener {
    TextView SignUp;
    EditText email, password;
    Button bLogIn;
    ProgressDialog progress;
    private static final String TAG = Login.class.getSimpleName();
    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dbHelper = new DbHelper(this);
        bLogIn = (Button) findViewById(R.id.bLogIn);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.ePassword);
        SignUp = (TextView) findViewById(R.id.SignUp);
        bLogIn.setOnClickListener(this);
        SignUp.setOnClickListener(this);
        progress = new ProgressDialog(this);
        progress.setTitle("Connecting");
        progress.setMessage("Wait while connecting...");
        progress.setCancelable(false);
        SignUp.setText(Html.fromHtml(getString(R.string.multi_text)));


    }


    @Override
    public void onBackPressed() {
       // super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bLogIn:
                new Helper().execute();
                break;
            case R.id.SignUp:
                //       Intent intent = new Intent(CropDetails.this, PlantsRegisteration.class);
                //       CropDetails.this.startActivity(intent);
                Intent i1 = new Intent(Login.this, Testing.class);
                Login.this.startActivity(i1);
                // Toast.makeText(getApplicationContext(), "water", Toast.LENGTH_SHORT).show();
                break;

        }
    }

    class Helper extends AsyncTask<Void, String, Void> {
        private void insertPerson(String name, String phone_number, String application_key, String address, String email, String application_password, String poly_farms, String farm_types, String password2) {
            // Gets the database in write mode
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            // Create a ContentValues object where column names are the keys,
            // and Toto's pet attributes are the values.
            ContentValues values = new ContentValues();
            values.put(Contact.Entry.COLUMN_name, name);
            Log.v("name", name);
            Log.v("email", email);
            Log.v("phone", phone_number);
            Log.v("password", password2);
            Log.v("application_key", application_key);
            Log.v("application_password", application_password);
            Log.v("address", address);
            Log.v("farm_types", farm_types);
            Log.v("poly_farms", poly_farms);


            values.put(Contact.Entry.COLUMN_email, email);
            values.put(Contact.Entry.COLUMN_phone_number, phone_number);
            values.put(Contact.Entry.COLUMN_password, password2);
            values.put(Contact.Entry.COLUMN_application_key, application_key);
            values.put(Contact.Entry.COLUMN_application_password, application_password);
            values.put(Contact.Entry.COLUMN_address, address);
            values.put(Contact.Entry.COLUMN_farm_types, farm_types);
            values.put(Contact.Entry.COLUMN_poly_farms, poly_farms);
            // Insert a new row for Toto in the database, returning the ID of that new row.
            // The first argument for db.insert() is the pets table name.
            // The second argument provides the name of a column in which the framework
            // can insert NULL in the event that the ContentValues is empty (if
            // this is set to "null", then the framework will not insert a row when
            // there are no values).
            // The third argument is the ContentValues object containing the info for Toto.
            long newRowId = db.insert(Contact.Entry.TABLE_NAME, null, values);
        }
        List<com.example.laviverma.intellifarm.Person> persons;
        com.example.laviverma.intellifarm.Person person;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            CloudantClient client = ClientBuilder.account("3105b6ee-f089-459d-8ab7-d5df25b59662-bluemix")
                    .username("3105b6ee-f089-459d-8ab7-d5df25b59662-bluemix")
                    .password("3d900e6fa5ba28b50c592e64551deaecfb4d3fbf76ea7d245a419cb231ffa579")
                    .build();

            Database db = client.database("customer", false);
            db.createIndex("email", "email", "json",
                    new IndexField[]{new IndexField("email", IndexField.SortOrder.asc)});
            String email_enter = email.getText().toString().trim();
            persons = db.findByIndex("{\"email\" : \"" + email_enter + "\"}", com.example.laviverma.intellifarm.Person.class);
            if (persons.size() == 0) {
                publishProgress("Sorry wrong credentials ! check your email  again");
            } else {
                for (com.example.laviverma.intellifarm.Person person2 : persons) {
                    String pass = password.getText().toString().trim();
                    Log.v("address", person2.getAddress());
                    if (pass.equals(person2.getPassword())) {
                        SharedPreferences sharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("username_id",person2.getId());
                        Log.v("id",person2.getId());
                        editor.commit();
                        insertPerson(person2.getName(),
                                person2.getPhone_number(), person2.getApplication_key(), person2.getAddress(), person2.getEmail(),
                                person2.getApplication_password(), person2.getPolyfarms(),
                                person2.getFarmtypes(), person2.getPassword());
                        publishProgress("correct");
                    } else {
                        publishProgress("Sorry wrong credentials ! check your password  again");
                    }

                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progress.dismiss();
        }

        @Override
        protected void onProgressUpdate(String... values) {
            if (values[0] != "correct") {
                AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                builder.setMessage(values[0])
                        .setNegativeButton("Retry", null)
                        .create()
                        .show();
            } else {

                Intent i1 = new Intent(Login.this, Dashboard.class);
                Login.this.startActivity(i1);
            }

        }
    }
}