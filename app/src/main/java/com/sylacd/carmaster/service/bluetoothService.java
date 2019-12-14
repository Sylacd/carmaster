package com.sylacd.carmaster.service;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;


public class bluetoothService extends Service {
    BluetoothAdapter mbtAdapter = null;
    BluetoothDevice mbtDevice = null;
    BluetoothSocket mbtSocket = null;
    OutputStream outStream = null;

    @Override
    public void onCreate(){
        super.onCreate();
        mbtAdapter = BluetoothAdapter.getDefaultAdapter();
        if ( !mbtAdapter.isEnabled() ) {
            Log.d("tag", "service created");
            Handler handler=new Handler(Looper.getMainLooper());
            handler.post(new Runnable(){
                public void run(){
                    Toast.makeText(getApplicationContext(), "Please open bluetooth then restart program!", Toast.LENGTH_SHORT).show();
                }});
            return;
        }

        String HC_MAC = "98:D3:91:FD:93:B4";
        mbtDevice = mbtAdapter.getRemoteDevice(HC_MAC);


        try{
            UUID HC_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
            mbtSocket = mbtDevice.createRfcommSocketToServiceRecord(HC_UUID);
        } catch(Exception e){
            Log.d("log", "获取socket失败");
            return;
        }

        mbtAdapter.cancelDiscovery();

        try {
            mbtSocket.connect();
            Handler handler=new Handler(Looper.getMainLooper());
            handler.post(new Runnable(){
                public void run(){
                    Toast.makeText(getApplicationContext(), "Connect bluetooth success!", Toast.LENGTH_SHORT).show();
                }});
            Log.d("log", "连接成功");
        } catch (IOException e) {
            // Unable to connect; close the socket and get out
            Log.d("log", "连接失败");
            try {
                mbtSocket.close();
            } catch (IOException closeException) { }
            return;
        }
        try {
            outStream = mbtSocket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        try {
            mbtSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Handler handler=new Handler(Looper.getMainLooper());
        handler.post(new Runnable(){
            public void run(){
                Toast.makeText(getApplicationContext(), "Disconnect bluetooth success!", Toast.LENGTH_SHORT).show();
            }});
        outStream = null;
        mbtAdapter = null;
        mbtDevice = null;
        mbtSocket = null;
        Log.d("tag", "service stopped");
    }

    public class ControlBinder extends Binder{
        public void goAhead(){
            try {
                outStream.write("W".getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void getBack(){
            try {
                outStream.write("S".getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void turnLeft(){
            try {
                outStream.write("A".getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void turnRight(){
            try {
                outStream.write("D".getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void park(){
            try {
                outStream.write("P".getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private ControlBinder mBinder = new ControlBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
