package com.sylacd.carmaster;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.sylacd.carmaster.service.bluetoothService;
import com.sylacd.carmaster.utils.ServiceUtils;


public class buttonControlActivity extends AppCompatActivity {

    private bluetoothService.ControlBinder controlBinder;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            controlBinder = (bluetoothService.ControlBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            controlBinder = null;
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_control);

        Intent bindIntent = new Intent(buttonControlActivity.this, bluetoothService.class);
        if (ServiceUtils.isServiceRunning(buttonControlActivity.this, "com.sylacd.carmaster.service.bluetoothService")){
            bindService(bindIntent, connection, BIND_WAIVE_PRIORITY);
        }
        else{
            Toast.makeText(getApplicationContext(), "No bluetooth!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }


        Button button_up = (Button) findViewById(R.id.button_up);
        Button button_down = (Button) findViewById(R.id.button_down);
        Button button_left = (Button) findViewById(R.id.button_left);
        Button button_right = (Button) findViewById(R.id.button_right);
        Button button_middle = (Button) findViewById(R.id.button_middle);


        button_up.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    controlBinder.goAhead();
                }
                else if (event.getAction() == MotionEvent.ACTION_UP){
                    controlBinder.park();
                }
                return false;
            }
        });

        button_down.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    controlBinder.getBack();
                }
                else if (event.getAction() == MotionEvent.ACTION_UP){
                    controlBinder.park();
                }
                return false;
            }
        });

        button_left.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    controlBinder.turnLeft();
                }
                else if (event.getAction() == MotionEvent.ACTION_UP){
                    controlBinder.park();
                }
                return false;
            }
        });

        button_right.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    controlBinder.turnRight();
                }
                else if (event.getAction() == MotionEvent.ACTION_UP){
                    controlBinder.park();
                }
                return false;
            }
        });

        button_middle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controlBinder.park();
            }
        });
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if (ServiceUtils.isServiceRunning(buttonControlActivity.this, "com.sylacd.carmaster.service.bluetoothService")){
            unbindService(connection);
        }
    }
}
