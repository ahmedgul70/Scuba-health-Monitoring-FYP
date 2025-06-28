//package com.example.arduino_bluetooth;
//
//import static android.content.ContentValues.TAG;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//
//import android.Manifest;
//import android.bluetooth.BluetoothAdapter;
//import android.bluetooth.BluetoothDevice;
//import android.bluetooth.BluetoothSocket;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.content.pm.PackageManager;
//import android.os.Bundle;
//import android.os.Handler;
//import android.util.Log;
//import android.view.TextureView;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.ListView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.util.ArrayList;
//import java.util.Set;
//import java.util.Timer;
//import java.util.TimerTask;
//import java.util.UUID;
//
//public class MainActivity extends AppCompatActivity {
//    private boolean CONNECTION_ENSTABLISHED = false;
//    private boolean CONTINUE_READ_WRITE = true;
//    private Button turnon , paired , scan;
//    private BluetoothAdapter adapter;
//    ListView lv;
//    private TextView temp;
//    private String temp_val ="";
//    ArrayList<String> listItems;
//    private Set<BluetoothDevice> pairedDevices;
//    private BluetoothSocket socket;
//    private InputStream is;
//    private OutputStream os;
//    private boolean DEVICES_IN_LIST = true;
//    private BluetoothDevice remoteDevice;
//    private static UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"); //SerialPortService ID // MY_UUID is the app's UUID string, also used by the client code.
//
//
////    private ArrayList <DeviceItem>deviceItemList;
//
//    ArrayAdapter<String> listAdapter;
////    private ArrayAdapter<DeviceItem> mAdapter;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        turnon= (Button) findViewById(R.id.But1);
//        paired= (Button) findViewById(R.id.But2);
//        scan= (Button) findViewById(R.id.But3);
//        temp = (TextView)  findViewById(R.id.p1);
//        lv = (ListView)findViewById(R.id.listView);
////        et=(EditText) findViewById(R.id.txtMessage);
//
//        MainActivity.this.runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                temp.setText(" 100 ");
//                //
//            }
//        });
//        listItems = new ArrayList<String>(); //shows messages in list view
//        listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
//        lv.setAdapter(listAdapter);
//
//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() //list onclick selection process
//        {
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
//            {
//                if(DEVICES_IN_LIST)
//                {
//                    String name = (String) parent.getItemAtPosition(position);
//                    selectBTdevice(name); //selected device will be set globally
//                    //Toast.makeText(getApplicationContext(), "Selected " + name, Toast.LENGTH_SHORT).show();
//                    //do not automatically call OpenBT(null) because makes troubles with server/client selection
//                }
//                else //message is selected
//                {
////                    String message = (String) parent.getItemAtPosition(position);
////                    et.setText(message);
//                }
//            }
//        });
//
//
//
//        adapter = BluetoothAdapter.getDefaultAdapter();
//        if (adapter == null) //If the adapter is null, then Bluetooth is not supported
//        {
//            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_SHORT).show();
//            finish();
//            return;
//        }
//
//        turnon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v){
////                openActivity();
//                if (!adapter.isEnabled())
//                {
//                    Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//                    startActivityForResult(turnOn, 1);
//                    Toast.makeText(getApplicationContext(), "Turned on",Toast.LENGTH_SHORT).show();
//                } else
//                {
//                    Toast.makeText(getApplicationContext(), "Already on", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//        });
//
//        scan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v){
//                temp.setText("70");
//                clientConnecter.run();
//            }
//
//        });
//
//
//    }
//
//    private Runnable clientConnecter = new Runnable()
//    {
//
//        @Override
//        public void run()
//        {
//            try
//            {
//                socket = remoteDevice.createRfcommSocketToServiceRecord(MY_UUID);
//                socket.connect();
//                CONNECTION_ENSTABLISHED = true; //protect from failing
//                Toast.makeText(MainActivity.this, "HC-05 is connected", Toast.LENGTH_SHORT).show();
//
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() { //Show message on UIThread
//                        listItems.clear(); //remove chat history
//                        listItems.add(0, String.format("  ready to communicate! Write something..."));
//                        listAdapter.notifyDataSetChanged();
//                    }});
//
//                InputStream tmpIn = null;
//
//                try {
//                    tmpIn = socket.getInputStream();
//                    Toast.makeText(MainActivity.this, "Reading success 1", Toast.LENGTH_SHORT).show();
//
//                } catch (IOException e) {
//                    Toast.makeText(MainActivity.this, "Reading Failed 1", Toast.LENGTH_SHORT).show();
//                }
//
//                is = tmpIn;
//
//
////                Timer t = new Timer();
////                //Set the schedule function and rate
////                t.scheduleAtFixedRate(new TimerTask() {
////                            public void run(){
//                int bufferSize = 1024;
//                int bytesRead = -1;
//                byte[] buffer = new byte[bufferSize];
//                while(CONTINUE_READ_WRITE) //Keep reading the messages while connection is open...
//                {
//                    try {
//                        bytesRead = is.read(buffer);
//                        Toast.makeText(MainActivity.this, "Reading success 2", Toast.LENGTH_SHORT).show();
//
//                    } catch (IOException e) {
//                        Toast.makeText(MainActivity.this, "Reading Failed 2", Toast.LENGTH_SHORT).show();
//                    }
//
//                    final StringBuilder sb = new StringBuilder();
//
//                    if (bytesRead != -1) {
//                        String result = "";
////                                        while ((bytesRead == bufferSize) && (buffer[bufferSize - 1] != 0)) {
//                        Toast.makeText(MainActivity.this, "Reading success 3", Toast.LENGTH_SHORT).show();
//                        result = result + new String(buffer, 0, bytesRead - 1);
//                        try {
//                            bytesRead = is.read(buffer);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                            Toast.makeText(MainActivity.this, "failed", Toast.LENGTH_SHORT).show();
//                        }
////                        }
//                        result = result + new String(buffer, 0, bytesRead - 1);
//                        sb.append(result);
//                        temp.setText(sb.toString());
//                        Toast.makeText(MainActivity.this, "Reading success 4", Toast.LENGTH_SHORT).show();
//
//                    }
//                    else{
//                        Toast.makeText(MainActivity.this, "No data Received", Toast.LENGTH_SHORT).show();
//                        temp.setText("0");
//                        break;
//                    }
//                }
////                    android.util.Log.e("TrackingFlow", "Read: " + sb.toString());
//
////                                    runOnUiThread(new Runnable() {
////                                        @Override
////                                        public void run() { //Show message on UIThread
//////                            Toast.makeText(MainActivity.this, sb.toString(), Toast.LENGTH_SHORT).show();
////                                            temp.setText(sb.toString());
////                                            listItems.add(0, String.format("< %s", sb.toString()));
////                                            listAdapter.notifyDataSetChanged();
////                                        }
////                                    });
//
////                            }
////                            },
//////Set how long before to start calling the TimerTask (in milliseconds)
////                        0,
//////Set the amount of time between each execution (in milliseconds)
////                        2000);
//            }
//            catch (IOException e)
//            {
//                Log.e(TAG, "Client not connected...");
//                Toast.makeText(MainActivity.this, "HC-05 not connected", Toast.LENGTH_SHORT).show();
//
//                e.printStackTrace();
//            }
//        }
//    };
//
//
//    public void selectBTdevice(String name) //for selecting device from list which is used in procedures
//    {
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
//        if (is != null) {
//            try {is.close();} catch (Exception e) {}
//            is = null;
//        }
//
//        if (os != null) {
//            try {os.close();} catch (Exception e) {}
//            os = null;
//        }
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
//            mHandler.removeCallbacksAndMessages(clientConnecter);
//            Log.i(TAG, "Threads ended...");
//        }catch (Exception e)
//        {
//            Log.e(TAG, "Attemp for closing threads was unsucessfull.");
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
//
//    public void openActivity(){
//        Intent intent = new Intent(this,MainActivity2.class);
//        startActivity(intent);
//    }
//}