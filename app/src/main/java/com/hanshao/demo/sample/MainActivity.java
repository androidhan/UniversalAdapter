package com.hanshao.demo.sample;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mButtonOne;
    private Button mButtonTow;
    private Button mButtonThree;
    private Button mButtonFour;
    private Button mButtonFive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();


    }


    private void initView() {
        mButtonOne = (Button) findViewById(R.id.button_one);
        mButtonTow = (Button) findViewById(R.id.button_tow);
        mButtonThree = (Button)findViewById(R.id.button_three);
        mButtonFour = (Button) findViewById(R.id.button_four);
        mButtonFive = (Button) findViewById(R.id.button_five);
    }

    private void initListener() {

        mButtonOne.setOnClickListener(this);
        mButtonTow.setOnClickListener(this);
        mButtonThree.setOnClickListener(this);
        mButtonFour.setOnClickListener(this);
        mButtonFive.setOnClickListener(this);



    }

    private Handler mHandler = new Handler();

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.button_one){
            SingleItemActivity.startActivity(this);
        }else if(v.getId() == R.id.button_tow){
            SwitchItemActivity.startActivity(this);
        }else if(v.getId() == R.id.button_three){
            MultipleActivity.startActivity(this,0);
        }else if(v.getId() == R.id.button_four){
            MultipleActivity.startActivity(this,1);
        }else if(v.getId() == R.id.button_five){
            MutipleSwitchItemActivity.startActivity(this);
        }
    }
}
