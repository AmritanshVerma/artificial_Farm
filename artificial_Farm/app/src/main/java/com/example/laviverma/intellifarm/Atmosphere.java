package com.example.laviverma.intellifarm;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import org.json.JSONArray;
import org.json.JSONObject;

import me.tankery.lib.circularseekbar.CircularSeekBar;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Atmosphere.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Atmosphere#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Atmosphere extends Fragment  {
    String TAG = "IMqttToken";
    Context c;
    CircularSeekBar c1, c2, c4, c3;
    TextView t1, t2, t3, t4;
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
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v("Atmosphere","onDEstroy");
        disconn();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        c1 = (CircularSeekBar) getView().findViewById(R.id.seekbar1);
        c2 = (CircularSeekBar) getView().findViewById(R.id.seekbar2);
        c3 = (CircularSeekBar) getView().findViewById(R.id.seekbar3);
        c4 = (CircularSeekBar) getView().findViewById(R.id.seekbar4);
        t1 = (TextView) getView().findViewById(R.id.textView1);
        t2 = (TextView) getView().findViewById(R.id.textView2);
        t3 = (TextView) getView().findViewById(R.id.textView3);
        t4 = (TextView) getView().findViewById(R.id.textView4);


   //     try {
     //       connectDevice();
   //     } catch (MqttException e) {
   //         e.printStackTrace();
  //      }
    }

    final String subscriptionTopic = "iot-2/type/Raspberry/id/Farm_001/evt/user/fmt/json";
    TextView dataReceived;
    private MqttAndroidClient client;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Atmosphere() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Atmosphere.
     */
    // TODO: Rename and change types and number of parameters
    public static Atmosphere newInstance(String param1, String param2) {
        Atmosphere fragment = new Atmosphere();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("Atm","onCreate");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_atmosphere, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.v("Atm","onAttach");
        c = context;
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
        Log.v("Atm","onDetach");
        mListener = null;
    }
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
            return client.connect(options, c, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    setSub();
                    Toast.makeText(getActivity(), "connectd", Toast.LENGTH_SHORT).show();

                    client.setCallback(new MqttCallback() {
                        @Override
                        public void connectionLost(Throwable cause) {
                            Log.v("hey", "lost");
                        }

                        @Override
                        public void messageArrived(String topic, MqttMessage message) throws Exception {
                            Log.v("hey", "arrived");

                            String data = new String(message.getPayload());
                            JSONObject baseJsonResponse = new JSONObject(data);
                            JSONArray arr = baseJsonResponse.getJSONArray("Airdata");
                            String s = arr.getJSONObject(0).getString("Temp");
                            String s2 = baseJsonResponse.getJSONObject("Waterdata").getString("Temp");
                          //  Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                         //   Toast.makeText(getActivity(), s2, Toast.LENGTH_SHORT).show();
                            Log.v("temp", String.valueOf((int) Math.round(Double.parseDouble(s))));
                            String hum = arr.getJSONObject(0).getString("Humidity");
                            String light = arr.getJSONObject(0).getString("Light");
                            String temp = arr.getJSONObject(0).getString("Temp");
                            c1.setProgress(Float.parseFloat(hum));
                            t1.setText(hum);
                            c2.setProgress(Float.parseFloat(light) / 10);
                            t2.setText(Float.toString(Float.parseFloat(light)));
                            c4.setProgress(Float.parseFloat(temp));
                            t4.setText(temp);
                        }

                        @Override
                        public void deliveryComplete(IMqttDeliveryToken token) {

                        }
                    });
                    //   subscribeToTopic();
                    // pub();
                    // setSub();
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
