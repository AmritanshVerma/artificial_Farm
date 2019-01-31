package com.example.laviverma.intellifarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import me.tankery.lib.circularseekbar.CircularSeekBar;


public class Nocamera extends Fragment implements MyInterface {
    CircularSeekBar c1, c2, c4, c3;
    TextView t1, t2, t3, t4;

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
                String t = intent.getStringExtra("temp");
                String h = intent.getStringExtra("hum");
                String l = intent.getStringExtra("light");
                c1.setProgress(Float.parseFloat(h));
                t1.setText(h);
                c2.setProgress(Float.parseFloat(l) / 10);
                t2.setText(Float.toString(Float.parseFloat(l)));
                c4.setProgress(Float.parseFloat(t));
                t4.setText(t);
                Log.v("te",t);
                Log.v("te",h);
                Log.v("te",l);
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

    public Nocamera() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        c1 = (CircularSeekBar) getView().findViewById(R.id.seekbar1);
        c2 = (CircularSeekBar) getView().findViewById(R.id.seekbar2);
        c3 = (CircularSeekBar) getView().findViewById(R.id.seekbar3);
        c4 = (CircularSeekBar) getView().findViewById(R.id.seekbar4);
        t1 = (TextView) getView().findViewById(R.id.textView1);
        t2 = (TextView) getView().findViewById(R.id.textView2);
        t3 = (TextView) getView().findViewById(R.id.textView3);
        t4 = (TextView) getView().findViewById(R.id.textView4);
        c1.setProgress(25);
        c2.setProgress(50);
        c3.setProgress(75);
        c4.setProgress(100);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

   //         String s = getArguments().getString("hum");
     //   Log.v("hurray !!!!!!!!!",s);
            return inflater.inflate(R.layout.fragment_nocamera, container, false);
    }

    @Override
    public void myAction() {

    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
