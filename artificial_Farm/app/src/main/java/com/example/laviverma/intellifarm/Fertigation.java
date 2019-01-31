package com.example.laviverma.intellifarm;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONObject;

import org.json.*;
/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Fertigation.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Fertigation#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fertigation extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
TextView t1,t2,t3;
ProgressBar p1,p2,p3;
Context c =null;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String TAG = "IMqttToken";
    final String subscriptionTopic = "iot-2/type/Raspberry/id/Farm_001/evt/user/fmt/json";
    TextView dataReceived;
    private MqttAndroidClient client;
    private OnFragmentInteractionListener mListener;

    public Fertigation() {
        // Required empty public constructor
    }
    public void disconn() {
        try {
            client.disconnect(null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Toast.makeText(getActivity(), "disconnected", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Toast.makeText(getActivity(), "could not disconnected", Toast.LENGTH_SHORT);
                }
            });


        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
    public IMqttToken connectDevice() throws MqttException {
        Log.d(TAG, ".connectDevice() entered");
        //  String clientID = "d:" + "75w46b" + ":" + "Mobile" + ":" + "Android";
        String clientID = "a:6pphpr:myApp";
        final String connectionURI;

        connectionURI = "ssl://" + "6pphpr" + ".messaging.internetofthings.ibmcloud.com:8883";


        client = new MqttAndroidClient(getActivity(), connectionURI, clientID);
        Log.v("conn", "before call back");


        String username = "a-6pphpr-wwiocrols6";
        char[] password = "TX)9JoE!l@TbACkLsU".toCharArray();

        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(true);
        options.setUserName(username);
        options.setPassword(password);


        Log.d(TAG, "Connecting to server: " + connectionURI);
        try {
            // connect
            return client.connect(options,c, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {

                    Toast.makeText(getActivity(), "connectd", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                            Toast.makeText(getActivity(), s2, Toast.LENGTH_SHORT).show();
                            Toast.makeText(getActivity(), s3, Toast.LENGTH_SHORT).show();

                          //  Log.v("temp",String.valueOf((int)Math.round(Double.parseDouble(s))));
                        //    p1.setProgress((int)Math.round(Double.parseDouble(s)));
                            t1.setText(s);
                        //    int i =(int)(Math.round(Double.parseDouble(s)*100/14));
                          int  i=10;
                         //   p2.setProgress(i);
                            t2.setText(s2);
                       //     p3.setProgress((int)Math.round(Double.parseDouble(s))*10);
                            t3.setText(s3);
                            //        JSONObject json = (JSONObject) JSONSerializer.toJSON(data);
                            //       view1.setValue(Integer.parseInteger(baseJsonResponse.getString("temp")));
                        }

                        @Override
                        public void deliveryComplete(IMqttDeliveryToken token) {

                        }
                    });
                    //   subscribeToTopic();
                    // pub();
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
    public void conn() throws MqttException {
      //  try {
         //   connectDevice();
     //   Log.v("fert","conn");
  //          Toast.makeText(getActivity(), "connected", Toast.LENGTH_SHORT);
            connectDevice();
         //  setSub();
 // new NewClass().execute("done");
    //    } catch (MqttException e) {
    //        e.printStackTrace();
    //    }
    }
  //  public class NewClass extends AsyncTask<String, String, String> {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disconn();
        Log.v("Fertigation","onDEstroy");
    }

    //     @Override
    //    protected String doInBackground(String... strings) {

              //  Log.v("fert","async do in");
              //  connectDevice();
      //          setSub();

    //        return null;
     //   }
  //  }
    public void setSub() {
       try {
            Toast.makeText(c, "subscribed", Toast.LENGTH_SHORT).show();
          client.subscribe(subscriptionTopic, 2, c, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.v("sub","success");
                    Toast.makeText(c, "subscribed success", Toast.LENGTH_SHORT).show();
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

    public void unsub(View view) {
        try {

            client.unsubscribe(subscriptionTopic, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Toast.makeText(getActivity(), "unsubscribed", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {

                }
            });


        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fertigation.
     */
    // TODO: Rename and change types and number of parameters
    public static Fertigation newInstance(String param1, String param2) {
        Fertigation fragment = new Fertigation();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("fertigation","onCreate");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.v("fertigation","onCreateView");
        return inflater.inflate(R.layout.fragment_fertigation, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.v("fertigation","onViewCreated");
        p1 = (ProgressBar) getView().findViewById(R.id.progress1);
        p2= (ProgressBar)getView().findViewById(R.id.progress2);
        p3= (ProgressBar)getView().findViewById(R.id.progress3);
        t1 =(TextView)getView().findViewById(R.id.water_level);
        t2 =(TextView)getView().findViewById(R.id.co2);
        t3 =(TextView)getView().findViewById(R.id.nutrient);
   //     try {
      //      conn();
    //    } catch (MqttException e) {
    //        e.printStackTrace();
     //   }
    }

    @Override
    public void onAttach(Context context) {
       // c= context;
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
