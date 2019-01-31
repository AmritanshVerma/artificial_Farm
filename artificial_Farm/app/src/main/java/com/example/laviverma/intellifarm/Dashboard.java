package com.example.laviverma.intellifarm;

import android.*;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.DragAndDropPermissions;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.ibm.mobilefirstplatform.clientsdk.android.core.api.BMSClient;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPPush;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPPushException;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPPushNotificationListener;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPPushResponseListener;
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPSimplePushNotification;

import org.json.JSONException;
import org.json.JSONObject;

import pl.bclogic.pulsator4droid.library.PulsatorLayout;

public class Dashboard extends AppCompatActivity implements View.OnClickListener , NavigationView.OnNavigationItemSelectedListener {
Button details;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private static final String TAG = Dashboard.class.getSimpleName();
    private MFPPush push; // Push client
    private MFPPushNotificationListener notificationListener; // Notification listener to handle a push sent to the phone
    private ActionBarDrawerToggle actionBarDrawerToggle;
ProgressBar pb;
int progress;
int c = 0;
ProgressDialog progress2;
Handler h =new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v("DAshboard","onCreate");
        super.onCreate(savedInstanceState);
       progress2 = new ProgressDialog(this);
        setContentView(R.layout.activity_dashboard);
        PulsatorLayout pulsator = (PulsatorLayout) findViewById(R.id.pulsator);
        pulsator.start();
        details=(Button)findViewById(R.id.detail);
        details.setOnClickListener(this);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view2);
        navigationView.setNavigationItemSelectedListener(this);
        setUpToolBar();
        pb= (ProgressBar)findViewById(R.id.progress);
        process();
        // initialize core SDK with IBM Bluemix application Region, TODO: Update region if not using Bluemix US SOUTH
        BMSClient.getInstance().initialize(this, BMSClient.REGION_US_SOUTH);

        // Grabs push client sdk instance
        push = MFPPush.getInstance();
        push.initialize(this, "13427c42-b5b0-435f-91d4-b9e85d752181", "68af7cc6-6d36-4e6e-8488-f9c9e91654f3");

        notificationListener = new MFPPushNotificationListener() {
            @Override
            public void onReceive(final MFPSimplePushNotification message) {
                Log.i(TAG, "Received a Push Notification: " + message.toString());
                runOnUiThread(new Runnable() {
                    public void run() {
                        Log.v("getAlert",message.getAlert());
                    }
                });
            }
        };
    }


    public void registerDevice() {

        // Checks for null in case registration has failed previously
        if (push == null) {
            push = MFPPush.getInstance();
        }
        MFPPushResponseListener registrationResponselistener = new MFPPushResponseListener<String>() {
            @Override
            public void onSuccess(String response) {
                // Split response and convert to JSON object to display User ID confirmation from the backend
                String[] resp = response.split("Text: ");
                try {
                    JSONObject responseJSON = new JSONObject(resp[1]);
                    setStatus("Device Registered Successfully with USER ID " + responseJSON.getString("userId"), true);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.i(TAG, "Successfully registered for push notifications, " + response);
                // Start listening to notification listener now that registration has succeeded
                push.listen(notificationListener);
            }

            @Override
            public void onFailure(MFPPushException exception) {
                String errLog = "Error registering for push notifications: ";
                String errMessage = exception.getErrorMessage();
                int statusCode = exception.getStatusCode();

                // Set error log based on response code and error message
                if(statusCode == 401){
                    errLog += "Cannot authenticate successfully with Bluemix Push instance, ensure your CLIENT SECRET was set correctly.";
                } else if(statusCode == 404 && errMessage.contains("Push GCM Configuration")){
                    errLog += "Push GCM Configuration does not exist, ensure you have configured GCM Push credentials on your Bluemix Push dashboard correctly.";
                } else if(statusCode == 404 && errMessage.contains("PushApplication")){
                    errLog += "Cannot find Bluemix Push instance, ensure your APPLICATION ID was set correctly and your phone can successfully connect to the internet.";
                } else if(statusCode >= 500){
                    errLog += "Bluemix and/or your Push instance seem to be having problems, please try again later.";
                }

                setStatus(errLog, false);
                Log.e(TAG,errLog);
                // make push null since registration failed
                push = null;
            }
        };

        // Attempt to register device using response listener created above
        // Include unique sample user Id instead of Sample UserId in order to send targeted push notifications to specific users
        //TODO : Registartion with UserId
        push.registerDeviceWithUserId("Sample UserID",registrationResponselistener);

    }
    private void setStatus(final String messageText, boolean wasSuccessful){

        final String bottomStatus = wasSuccessful ? "You Are Connected" : "Something Went Wrong";

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (push != null) {
            push.hold();
        }
    }
    private void process(){
        pb.setProgress(0);
        pb.setMax(100);
        Drawable drawable = getResources().getDrawable(R.drawable.custom_progress);
        pb.setProgressDrawable(drawable);
        progress =0;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(progress<100){
                    progress++;
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    h.post(new Runnable() {
                        @Override
                        public void run() {
                            pb.setProgress(progress);
                        }
                    });
                }
            }
        }).start();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v("DAshboard","onDestroy");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (push != null) {
            push.listen(notificationListener);
        }
        Log.v("DAshboard","onREsume");
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.v("DAshboard","onRestart");
        if(c==1)
        progress2.dismiss();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v("DAshboard","onStop");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.detail:
                Intent i1 = new Intent(Dashboard.this,Details.class);
                Dashboard.this.startActivity(i1);
                break;


        }


    }

    private void setUpToolBar() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout2);
        toolbar = (Toolbar) findViewById(R.id.toolbar2);


        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        //    drawerLayout.addDrawerListener(actionBarDrawerToggle);

        //   getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu2);
        //    actionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Log.v("hjasjkd","navi");
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_change_password:
                Intent i2 = new Intent(Dashboard.this,ChangePassword.class);
                Dashboard.this.startActivity(i2);
                break;
            case R.id.nav_settings:
                     Intent i = new Intent(Dashboard.this,AddPolyHouse.class);
                   Dashboard.this.startActivity(i);
                break;
            case R.id.nav_log_out:
                   Intent out = new Intent(Dashboard.this,Login.class);
                 Dashboard.this.startActivity(out);
                //    finish();
                break;
            case R.id.nav_profile:
                Intent contactIntent = new Intent(Dashboard.this, Profile.class);
                try{
                Dashboard.this.startActivity(contactIntent);}
                catch (Exception e)
                {
                    Log.v("excep",e.toString());
                }

                break;
            case R.id.nav_notification:
                Intent notiIntent = new Intent(Dashboard.this, AddNotification.class);
                try{
                    Dashboard.this.startActivity(notiIntent);}
                catch (Exception e)
                {
                    Log.v("excep",e.toString());
                }

                break;
        }
        return false;
    }
}
