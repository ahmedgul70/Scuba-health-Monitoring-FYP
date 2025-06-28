package com.example.arduino_bluetooth;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.UUID;


public class MainActivity extends AppCompatActivity {
        private Button turnon , paired , scan;
//    ListView lv;
    private TextView temp,textview1,textview2,ox,hb;
//    ArrayList<String> listItems;
//    ArrayAdapter<String> listAdapter;
//    private boolean DEVICES_IN_LIST = true;
    private BluetoothAdapter adapter;
    private Set<BluetoothDevice> pairedDevices;
    private BluetoothSocket socket;
    private InputStream inputStream;
//    Mmr nw = new Mmr();

//    private BluetoothDevice remoteDevice;
//    private static UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"); //SerialPortService ID // MY_UUID is the app's UUID string, also used by the client code.
//    private boolean CONNECTION_ENSTABLISHED = false;
//    private boolean CONTINUE_READ_WRITE = true;

    private final String DEVICE_ADDRESS="FC:A8:FF:00:35:11";
    private final UUID PORT_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");//Serial Port Service ID
    private BluetoothDevice device;
    boolean deviceConnected=false;
    Thread thread;
    byte buffer[];
//    int bufferPosition;
    boolean stopThread;
    private String str=" ";
//    private Button button5;
//    int a =1 , b=1 , v =1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        turnon = (Button) findViewById(R.id.But1);

        temp = (TextView) findViewById(R.id.p1);
        ox = (TextView) findViewById(R.id.p2);
        hb = (TextView) findViewById(R.id.p3);
        textview1 = (TextView) findViewById(R.id.title1);
        textview2 = (TextView) findViewById(R.id.title2);
//        button5 = (Button) findViewById(R.id.But2);

//        button5.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this , records.class);
//                startActivity(intent);
//            }
//        });

    }

    @SuppressLint("MissingPermission")
    public boolean BTinit()
    {
        boolean found=false;
        BluetoothAdapter bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            Toast.makeText(getApplicationContext(),"Device doesnt Support Bluetooth",Toast.LENGTH_SHORT).show();
        }
        if(!bluetoothAdapter.isEnabled())
        {
            Intent enableAdapter = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableAdapter, 0);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Set<BluetoothDevice> bondedDevices = bluetoothAdapter.getBondedDevices();
        if(bondedDevices.isEmpty())
        {
//            Toast.makeText(getApplicationContext(),"Please Pair the Device first",Toast.LENGTH_SHORT).show();
        }
        else
        {
            for (BluetoothDevice iterator : bondedDevices)
            {
                if(iterator.getAddress().equals(DEVICE_ADDRESS))
                {
                    device=iterator;
                    found=true;
                    break;
                }
            }
        }
        return found;
    }

    @SuppressLint("MissingPermission")
    public boolean BTconnect()
    {
        boolean connected=true;
        try {
            socket = device.createRfcommSocketToServiceRecord(PORT_UUID);
            socket.connect();
        } catch (IOException e) {
            e.printStackTrace();
            connected=false;
        }
        if(connected)
        {
            try {
                inputStream=socket.getInputStream();
//                Toast.makeText(MainActivity.this,"Reading Data",Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
//                Toast.makeText(MainActivity.this,"Reading Failed",Toast.LENGTH_SHORT).show();
            }

        }
        return connected;
    }

    void beginListenForData()
    {
        final Handler handler = new Handler();
        stopThread = false;
        buffer = new byte[1024];
        String ss1 = "?,";



        Thread thread  = new Thread(new Runnable()
        {

            public void run()
            {
                while(!Thread.currentThread().isInterrupted() && !stopThread)
                {

                    try
                    {
                        int byteCount = inputStream.available();
//                        int byteCount = inputStream.read(buffer);
                        if(byteCount > 0)
                        {
                            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                            while(reader.ready()){

                                String line = reader.readLine();

                                textview2.setText(line);

                                String s1 = line.substring(0,5).toString();
                                String s2 = line.substring(6,8).toString();
                                String s3 = line.substring(9,11).toString();

                                if(s2.compareTo("?,") == 0){
                                    s2 = "0";
                                }
                                if(s3.compareTo("? ") == 0){
                                    s3 = "0";
                                }

                                temp.setText(s1 + " °C" );

                                ox.setText(s2 + " % ");
                                hb.setText(s3 + " Bpm ");


//                                String str1 = line.substring(0,5).toString();
//                                String str2 = line.substring(6,8).toString();
//                                String str3 = line.substring(9,11).toString();

//                                Mmr.str[a] = s1;
//                                Mmr.str5[a] = s2;
//                                Mmr.str6[a] = s3;
//                                Mmr.i++;
//                                a++;

                            }

                        }
                    }
                    catch (IOException ex)
                    {
                        stopThread = true;
//                        Log.d(TAG, "Input stream was disconnected", ex);
//                        break;
//                        Toast.makeText(MainActivity.this,"Error",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        thread.start();
    }


    public void onClickStart(View view) throws InterruptedException {
        textview1.setText("\nConnecting to HC-05!\n");
            if (BTinit()) {
                if (BTconnect()) {
                    deviceConnected = true;
                    beginListenForData();
                    textview1.setText("\nConnection Opened!\n");
//                    textview1.setText(nw.getValue());

//                    str = " ";
                }
                else {
                }

            } else {
            }
    }

    public void onClickClear(View view) {
        textview2.setText(" ");
    }

    public void list(View view) {
    }

//    public void selectBTdevice(String name) //for selecting device from list which is used in procedures
//        {
//        if(pairedDevices.isEmpty()) {
//            list(null);
//            Toast.makeText(MainActivity.this,"Selecting was unsucessful, no devices in list." ,Toast.LENGTH_SHORT ).show();
//
//        }
//
//        for(BluetoothDevice bt : pairedDevices) //foreach
//        {
//            if(name.equals(bt.getName()))
//            {
//                remoteDevice = bt;
//                Toast.makeText(getApplicationContext(), "Selected " + remoteDevice.getName(), Toast.LENGTH_SHORT ).show();
//            }
//        }
//    }
//
//    public void visible(View v) //BT device discoverable
//    {
//        Intent getVisible = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
//        startActivityForResult(getVisible, 0);
//    }
//
//    public void closeBT(View v) //for closing opened communications, cleaning used resources
//    {
//        /*if(adapter == null)
//            return;*/
//
//        CONTINUE_READ_WRITE = false;
//        CONNECTION_ENSTABLISHED = false;
//
//        if (inputStream != null) {
//            try {inputStream.close();} catch (Exception e) {}
//            inputStream = null;
//        }
//
//
//
//        if (socket != null) {
//            try {socket.close();} catch (Exception e) {}
//            socket = null;
//        }
//
//        try {
//            Handler mHandler = new Handler();
////            mHandler.removeCallbacksAndMessages(writter);
////            mHandler.removeCallbacksAndMessages(serverListener);
//
//
//        }catch (Exception e)
//        {
//
//        }
//
//        Toast.makeText(getApplicationContext(), "Communication closed" ,Toast.LENGTH_SHORT).show();
//
//        list(null); //shows list for reselection
////        et.setText(getResources().getString(Integer.parseInt("jn")));
//    }
//
//
//    public void list(View v) //shows paired devices to UI
//    {
//        CONNECTION_ENSTABLISHED = false; //protect from failing
//        listItems.clear(); //remove chat history
//        listAdapter.notifyDataSetChanged();
//
//        pairedDevices = adapter.getBondedDevices(); //list of devices
//
//        for(BluetoothDevice bt : pairedDevices) //foreach
//        {
//            listItems.add(0, bt.getName());
//        }
//        listAdapter.notifyDataSetChanged(); //reload UI
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        closeBT(null);
//
//        // Don't forget to unregister the ACTION_FOUND receiver.
//
//    }

}