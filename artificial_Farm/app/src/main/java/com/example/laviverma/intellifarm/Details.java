package com.example.laviverma.intellifarm;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONArray;
import org.json.JSONObject;

public class Details extends AppCompatActivity implements  Fertigation.OnFragmentInteractionListener,Nocamera.OnFragmentInteractionListener,Camera.OnFragmentInteractionListener,Atmosphere.OnFragmentInteractionListener{
    String TAG = "IMqttToken";
  MqttAndroidClient client;
  ImageView back;
    final String subscriptionTopic = "iot-2/type/Raspberry/id/Farm_001/evt/user/fmt/json";



     public  void dealerAtmp(String t,String h,String l,String wt,String ph,String ec){
       Bundle bundle = new Bundle();
       bundle.putString("hum",h);
         bundle.putString("temp",t);
         bundle.putString("light","");
         bundle.putString("co2",l);
          Intent intent = new Intent("my_package.action.UI_UPDATE");
         intent.putExtra("hum",h);
        intent.putExtra("temp",t);
        intent.putExtra("light",l);
        intent.putExtra("Wtemp",wt);
         intent.putExtra("ph",ph);
         intent.putExtra("ec",ec);
         LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
  //       catch (Exception e)
//         {
//             Log.v("hey",e.toString());
 //        }

       //  fragment.specific_function_name();
      /*   FragmentManager fragmentManager = getFragmentManager();
         Nocamera fr = new Nocamera();
         FragmentManager fm = getFragmentManager();
         dataFromActivityToFragment = (DataFromActivityToFragment) fr;
         FragmentTransaction fragmentTransaction = fm.beginTransaction();
         fragmentTransaction.replace(R.id.activity_details, fr);
         fragmentTransaction.commit();
         Nocamera nocamera =(Nocamera)fragmentManager.findFragmentById(R.id.nocamera);
         Nocamera fragobj = new Nocamera();
         fragobj.setArguments(bundle);*/
     }
    public void unsub() {
        try {

            client.unsubscribe(subscriptionTopic, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Toast.makeText(Details.this, "unsubscribed", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {

                }
            });


        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
    public void setSub() {
        try {
            Toast.makeText(Details.this, "subscribed", Toast.LENGTH_SHORT).show();
            client.subscribe(subscriptionTopic, 2,Details.this, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.v("sub","success");
                    Toast.makeText(Details.this, "subscribed success", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.v("sub","fail");
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
    public void disconn() {
        try {
            client.disconnect(null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Toast.makeText(Details.this, "disconnected", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Toast.makeText(Details.this, "could not disconnected", Toast.LENGTH_SHORT);
                }
            });


        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
    public IMqttToken connectDevice() throws MqttException {
        Log.d(TAG, ".connectDevice() entered");
        String clientID = "a:6pphpr:myApp";
        final String connectionURI;
        connectionURI = "ssl://" + "6pphpr" + ".messaging.internetofthings.ibmcloud.com:8883";
        client = new MqttAndroidClient(Details.this, connectionURI, clientID);
        Log.v("conn", "before call back");
        String username = "a-6pphpr-wwiocrols6";
        char[] password = "TX)9JoE!l@TbACkLsU".toCharArray();
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(true);
        options.setUserName(username);
        options.setPassword(password);
        Log.d(TAG, "Connecting to server: " + connectionURI);
        try {
            return client.connect(options,Details.this, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {

                    Toast.makeText(Details.this, "connectd", Toast.LENGTH_SHORT).show();
                    client.setCallback(new MqttCallback() {
                        @Override
                        public void connectionLost(Throwable cause) {
                            Log.v("hey", "lost");
                        }

                        @Override
                        public void messageArrived(String topic, MqttMessage message) throws Exception {
                            Log.v("hey water", "arrived");

                            String data = new String(message.getPayload());
                            JSONObject baseJsonResponse = new JSONObject(data);
                            String s = baseJsonResponse.getJSONObject("Waterdata").getString("Temp");
                            String s2 = baseJsonResponse.getJSONObject("Waterdata").getString("pH");
                           String s3 = baseJsonResponse.getJSONObject("Waterdata").getString("EC");
                          //  Toast.makeText(Details.this, s, Toast.LENGTH_SHORT).show();
                      //      Toast.makeText(Details.this, s2, Toast.LENGTH_SHORT).show();
                      //      Toast.makeText(Details.this, s3, Toast.LENGTH_SHORT).show();
                            JSONArray arr = baseJsonResponse.getJSONArray("Airdata");
                            String t = arr.getJSONObject(0).getString("Temp");
                           // Log.v("temp", String.valueOf((int) Math.round(Double.parseDouble(s))));
                            String h = arr.getJSONObject(0).getString("Humidity");
                            String l = arr.getJSONObject(0).getString("Light");

                            dealerAtmp(t,h,l,s,s2,s3);
                        }
                        @Override
                        public void deliveryComplete(IMqttDeliveryToken token) {

                        }
                    });
                    setSub();
                }
                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.w("Mqtt", "Failed to connect to: " + connectionURI + exception.toString());
                }
            });
        } catch (MqttException e) {
            Log.e(TAG, "Exception caught while attempting to connect to server", e.getCause());
            throw e;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v("Details", "onDestroy");
        unsub();
        disconn();
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v("Details", "onREsume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.v("Details", "onRestart");
    }

    @Override
    protected void onStop() {
        super.onStop();


        Log.v("Details", "onStop");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        back =(ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(Details.this, Dashboard.class);
                Details.this.startActivity(i1);
            }
        });
        Log.v("Details", "onCreate");
        try {
            connectDevice();
        } catch (MqttException e) {
           e.printStackTrace();
        }
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("ATMOSPHERE"));
        tabLayout.addTab(tabLayout.newTab().setText("FERTIGATION"));
        tabLayout.addTab(tabLayout.newTab().setText("CAMERA"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        Log.v("SDAS", Integer.toString(tabLayout.getTabCount()));
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}

