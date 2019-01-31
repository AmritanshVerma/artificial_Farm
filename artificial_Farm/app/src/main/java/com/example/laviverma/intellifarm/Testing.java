package com.example.laviverma.intellifarm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Testing extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);
        Button b1,b2,b3,b4,b5,b6;
        b1=(Button)findViewById(R.id.button1);
        b2=(Button)findViewById(R.id.button2);
        b3=(Button)findViewById(R.id.button3);
        b4=(Button)findViewById(R.id.button4);
        b5=(Button)findViewById(R.id.button5);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                //       Intent intent = new Intent(CropDetails.this, PlantsRegisteration.class);
                //       CropDetails.this.startActivity(intent);
                Intent i1 = new Intent(Testing.this, SignUp.class);
                Testing.this.startActivity(i1);
                // Toast.makeText(getApplicationContext(), "water", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button2:
                //       Intent intent = new Intent(CropDetails.this, PlantsRegisteration.class);
                //       CropDetails.this.startActivity(intent);
                Intent i2 = new Intent(Testing.this,BarCode.class);
                Testing.this.startActivity(i2);
                // Toast.makeText(getApplicationContext(), "water", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button3:
                //       Intent intent = new Intent(CropDetails.this, PlantsRegisteration.class);
                Intent i3 = new Intent(Testing.this,AddPolyHouse.class);
                Testing.this.startActivity(i3);
                // Toast.makeText(getApplicationContext(), "water", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button4:
                //       Intent intent = new Intent(CropDetails.this, PlantsRegisteration.class);
                Intent i4 = new Intent(Testing.this,Dashboard.class);
                Testing.this.startActivity(i4);
                // Toast.makeText(getApplicationContext(), "water", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}