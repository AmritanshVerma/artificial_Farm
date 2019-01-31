package com.example.laviverma.intellifarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import me.tankery.lib.circularseekbar.CircularSeekBar;

public class Camera extends Fragment implements MyInterface {
    TextView t1,t2,t3;
    ProgressBar p1,p2,p3;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentFilter filter = new IntentFilter(ACTION_INTENT);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(receiver, filter);
    }
    public static final String ACTION_INTENT = "my_package.action.UI_UPDATE";
    protected BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if(ACTION_INTENT.equals(intent.getAction())) {
                String t = intent.getStringExtra("Wtemp");
                String h = intent.getStringExtra("ph");
                String l = intent.getStringExtra("ec");
              //  c1.setProgress(Float.parseFloat(h));
         //       t1.setText(h);
        //        c2.setProgress(Float.parseFloat(l) / 10);
        //        t2.setText(Float.toString(Float.parseFloat(l)));
        //        c4.setProgress(Float.parseFloat(t));
        //        t4.setText(t);
                Log.v("teW",t);
                Log.v("teW",h);
                Log.v("teW",l);
               t1.setText(t);

                //    int i =(int)(Math.round(Double.parseDouble(s)*100/14));
            //    int  i=10;
                //   p2.setProgress(i);
                t2.setText(h);
                double d = Double.parseDouble(h);
                Log.v("dou",Double.toString(d));
                double d2 =d*100;
                Log.v("dou2",Double.toString(d2));
                double d3 =d2/14;
                Log.v("dou3",Double.toString(d3));
                int h2 = (int) ((Float.parseFloat(h)*100)/14.0);
                Log.v("float",Double.toString((Float.parseFloat(l)*100)/14.0));
                Log.v("int",Integer.toString(h2));
                p2.setProgress(h2);
                //     p3.setProgress((int)Math.round(Double.parseDouble(s))*10);
                t3.setText(l);
                //dateUIOnReceiverValue(value);
            }
        }
    };

    private void updateUIOnReceiverValue(String value) {
        // you probably want this:
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(receiver);
    }

    private OnFragmentInteractionListener mListener;

    public Camera() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        p1 = (ProgressBar) getView().findViewById(R.id.progress1);
        p2= (ProgressBar)getView().findViewById(R.id.progress2);
        p3= (ProgressBar)getView().findViewById(R.id.progress3);
        t1 =(TextView)getView().findViewById(R.id.water_level);
        t2 =(TextView)getView().findViewById(R.id.co2);
        t3 =(TextView)getView().findViewById(R.id.nutrient);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        //         String s = getArguments().getString("hum");
        //   Log.v("hurray !!!!!!!!!",s);
        return inflater.inflate(R.layout.fragment_camera, container, false);
    }

    @Override
    public void myAction() {

    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
/*
public class Camera extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    @Override
    public void onStart() {
        Log.v("Camera","onstart");
        super.onStart();
    }

    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v("Camera","onDEstroy");
    }
    private OnFragmentInteractionListener mListener;

    public Camera() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static Camera newInstance(String param1, String param2) {
        Camera fragment = new Camera();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("Camera","onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_camera, container, false);
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
        Log.v("Camera","onAttach");
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
        Log.v("Camera","onDEtach");
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
*/