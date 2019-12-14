package com.sylacd.carmaster;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sylacd.carmaster.service.bluetoothService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        //Switch bPort = (Switch) findViewById(R.id.switch_port);
        Button button0 = (Button) findViewById(R.id.button0);
        Button button00 = (Button) findViewById(R.id.button00);
        Button button1 = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);
        Button button3 = (Button) findViewById(R.id.button3);
        Button button4 = (Button) findViewById(R.id.button4);

        button0.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent startIntent = new Intent(MainActivity.this, bluetoothService.class);
                startService(startIntent);
            }
        }
        );

        button00.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent stopIntent = new Intent(MainActivity.this, bluetoothService.class);
                stopService(stopIntent);
            }
        }
        );

        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, buttonControlActivity.class);
                startActivity(intent);
            }
        }
        );

        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){}
        }
        );

        button3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){}
        }
        );

        button4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){}
        }
        );
    }
}
