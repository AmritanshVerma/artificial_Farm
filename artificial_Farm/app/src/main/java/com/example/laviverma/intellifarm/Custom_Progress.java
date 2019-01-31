package com.example.laviverma.intellifarm;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;

public class Custom_Progress extends AppCompatActivity {
    ProgressBar pb;
    Button b;
    int progress= 0;
    Handler h =new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom__progress);
        pb= (ProgressBar)findViewById(R.id.progress);
        process();
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
}
