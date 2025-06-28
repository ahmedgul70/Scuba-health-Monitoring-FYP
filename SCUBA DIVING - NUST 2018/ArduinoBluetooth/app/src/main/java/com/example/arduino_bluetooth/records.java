package com.example.arduino_bluetooth;

import static com.example.arduino_bluetooth.Mmr.i;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.Window;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.UUID;

public class records extends AppCompatActivity {
//    Thread thread;
//    byte buffer[];
//    private InputStream inputStream;
//    private final UUID PORT_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");//Serial Port Service ID
//    private BluetoothSocket socket;
//    private BluetoothDevice device;
    boolean stopThread;
    private TextView tt;
    int tim=5;
//    private final String DEVICE_ADDRESS="FC:A8:FF:00:35:11";
//    boolean deviceConnected=false;
//    Mmr nw = new Mmr();




    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_records);
        tt = (TextView) findViewById(R.id.t1);

//        Intent intent = getIntent();
//        String st = intent.getStringExtra("ky");
//        tt.setText(Mmr.str);

        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public void init() {
        TableLayout stk = (TableLayout) findViewById(R.id.table_main);

        TableRow tbrow0 = new TableRow(this);
        TextView tv0 = new TextView(this);
        tv0.setText(" S.No ");
        tv0.setTextColor(Color.WHITE);
        tbrow0.addView(tv0);

        TextView tv1 = new TextView(this);
        tv1.setText(" Time Elapsed ");
        tv1.setTextColor(Color.WHITE);
        tbrow0.addView(tv1);

        TextView tv2 = new TextView(this);
        tv2.setText(" Temperature ");
        tv2.setTextColor(Color.WHITE);
        tbrow0.addView(tv2);

        TextView tv3 = new TextView(this);
        tv3.setText(" Heart Beat ");
        tv3.setTextColor(Color.WHITE);
        tbrow0.addView(tv3);
//        stk.addView(tbrow0);

        TextView tv4 = new TextView(this);
        tv4.setText(" Oxygen Level ");
        tv4.setTextColor(Color.WHITE);
        tbrow0.addView(tv4);

        stk.addView(tbrow0);


        for (int n = 1; n<= Mmr.i; n++){
            TableRow tbrow = new TableRow(this);

            TextView t1v = new TextView(this);
            TextView t2v = new TextView(this);
            TextView t3v = new TextView(this);
            TextView t4v = new TextView(this);
            TextView t5v = new TextView(this);


            t1v.setText("" + n);
            t1v.setTextColor(Color.WHITE);
            t1v.setGravity(Gravity.CENTER);
            tbrow.addView(t1v);


            t2v.setText("" + tim);
            t2v.setTextColor(Color.WHITE);
            t2v.setGravity(Gravity.CENTER);
            tbrow.addView(t2v);

            t3v.setText("" + Mmr.str[n]);
            t3v.setTextColor(Color.WHITE);
            t3v.setGravity(Gravity.CENTER);
            tbrow.addView(t3v);

            t4v.setText(""+Mmr.str5[n]);
            t4v.setTextColor(Color.WHITE);
            t4v.setGravity(Gravity.CENTER);
            tbrow.addView(t4v);

            t5v.setText(""+Mmr.str6[n]);
            t5v.setTextColor(Color.WHITE);
            t5v.setGravity(Gravity.CENTER);
            tbrow.addView(t5v);

            stk.addView(tbrow);
            tim = tim +5;
        }
    }

//    public boolean BTinit()
//    {
//        boolean found=false;
//        BluetoothAdapter bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
//        if (bluetoothAdapter == null) {
//            Toast.makeText(getApplicationContext(),"Device doesnt Support Bluetooth",Toast.LENGTH_SHORT).show();
//        }
//        if(!bluetoothAdapter.isEnabled())
//        {
//            Intent enableAdapter = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//            startActivityForResult(enableAdapter, 0);
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        Set<BluetoothDevice> bondedDevices = bluetoothAdapter.getBondedDevices();
//        if(bondedDevices.isEmpty())
//        {
////            Toast.makeText(getApplicationContext(),"Please Pair the Device first",Toast.LENGTH_SHORT).show();
//        }
//        else
//        {
//            for (BluetoothDevice iterator : bondedDevices)
//            {
//                if(iterator.getAddress().equals(DEVICE_ADDRESS))
//                {
//                    device=iterator;
//                    found=true;
//                    break;
//                }
//            }
//        }
//        return found;
//    }
//
//    public boolean BTconnect()
//    {
//        boolean connected=true;
//
//        try {
//            socket = device.createRfcommSocketToServiceRecord(PORT_UUID);
//            socket.connect();
//        } catch (IOException e) {
//            e.printStackTrace();
//            connected=false;
//        }
//        if(connected)
//        {
//            try {
//                inputStream=socket.getInputStream();
//                Toast.makeText(records.this,"Reading Data",Toast.LENGTH_SHORT).show();
//            } catch (IOException e) {
//                e.printStackTrace();
//                Toast.makeText(records.this,"Reading Failed",Toast.LENGTH_SHORT).show();
//            }
//
//        }
//        return connected;
//    }

//    void beginListenForData()
//    {
//        TableRow tbrow = new TableRow(this);
//        TextView t1v = new TextView(this);
//        TextView t2v = new TextView(this);
//        TextView t3v = new TextView(this);
//        TextView t4v = new TextView(this);
//        TextView t5v = new TextView(this);
//        TableLayout stk = (TableLayout) findViewById(R.id.table_main);
//
//        final Handler handler = new Handler();
//        stopThread = false;
//        buffer = new byte[1024];
//
//        Thread thread  = new Thread(new Runnable()
//        {
//            int i =0 , times = 0;
//            public void run()
//            {
//                while(!Thread.currentThread().isInterrupted() && !stopThread)
//                {
//
//                    try
//                    {
//                        int byteCount = inputStream.available();
////                        int byteCount = inputStream.read(buffer);
//                        if(byteCount > 0)
//                        {
//                            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//                            while(reader.ready()){
//                                String line = reader.readLine();
////                                textview2.setText(line);
////                                temp.setText(line.substring(0,5) + " °C " );
////                                ox.setText(line.substring(6,8) + " % ");
////                                hb.setText(line.substring(9,11) + " Bpm ");
//
//                                tt.setText(line);
//
//
//
//
////                                t4v.setText(line.substring(9,11) + " Bpm ");
//                                t4v.setText("0");
//                                t4v.setTextColor(Color.WHITE);
//                                t4v.setGravity(Gravity.CENTER);
//                                tbrow.addView(t4v);
//
//
////                                t5v.setText(line.substring(6,8) + " % ");
//                                t5v.setText("0");
//                                t5v.setTextColor(Color.WHITE);
//                                t5v.setGravity(Gravity.CENTER);
//                                tbrow.addView(t5v);
//
//                                stk.addView(tbrow);
//                            }
//
//                        }
//                    }
//                    catch (IOException ex)
//                    {
//                        stopThread = true;
//                    }
//                    i++ ; times = times + 5;
//                }
//            }
//        });
//        thread.start();
//    }
}