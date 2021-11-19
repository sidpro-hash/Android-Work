package com.example.madpractical;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class Discover_Bluetooth extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    BluetoothAdapter bluetoothAdapter;
    public DeviceListAdapter deviceListAdapter;
    ListView listView;
    ArrayList<BluetoothDevice> deviceArrayList;

    /**
     * Broadcast Receiver for changes made to bluetooth states such as:
     * 1) Discoverability mode on/off or expire.
     */
    final BroadcastReceiver btReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(bluetoothAdapter.ACTION_STATE_CHANGED)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, bluetoothAdapter.ERROR);

                switch (state) {
                    case BluetoothAdapter.STATE_OFF:
                        Log.d(TAG, "btReceiver: STATE OFF");
                        break;
                    case BluetoothAdapter.STATE_TURNING_OFF:
                        Log.d(TAG, "btReceiver: STATE TURNING OFF");
                        break;
                    case BluetoothAdapter.STATE_ON:
                        Log.d(TAG, "btReceiver: STATE ON");
                        break;
                    case BluetoothAdapter.STATE_TURNING_ON:
                        Log.d(TAG, "btReceiver: STATE TURNING ON");
                        break;
                }
            }
            //if(BluetoothDevice.ACTION_FOUND.equals(action)){
            //BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            // add the name to the list
            //BTArrayAdapter.add(device.getName() + "\n" + device.getAddress());
            //mBTArrayAdapter.notifyDataSetChanged();
            //}
        }
    };

    /**
     * Broadcast Receiver for listing devices that are not yet paired
     * -Executed by btnDiscover() method.
     */
    final BroadcastReceiver enable_discovery_receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(bluetoothAdapter.ACTION_SCAN_MODE_CHANGED)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, bluetoothAdapter.ERROR);

                switch (state) {
                    case BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE:
                        Log.d(TAG, "enable_discovery_receiver: Discoverability Enabled");
                        break;
                    case BluetoothAdapter.SCAN_MODE_CONNECTABLE:
                        Log.d(TAG, "enable_discovery_receiver: Discoverability Enabled. Able to receive Connect");
                        break;
                    case BluetoothAdapter.SCAN_MODE_NONE:
                        Log.d(TAG, "enable_discovery_receiver: Discoverability Disabled. Not able to receive Connect");
                        break;
                    case BluetoothAdapter.STATE_CONNECTING:
                        Log.d(TAG, "enable_discovery_receiver: Connecting...");
                        break;
                    case BluetoothAdapter.STATE_CONNECTED:
                        Log.d(TAG, "enable_discovery_receiver: Connected.");
                        break;
                }
            }
            //if(BluetoothDevice.ACTION_FOUND.equals(action)){
            //BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            // add the name to the list
            //BTArrayAdapter.add(device.getName() + "\n" + device.getAddress());
            //mBTArrayAdapter.notifyDataSetChanged();
            //}
        }
    };

    /**
     * Broadcast Receiver for listing devices that are not yet paired
     *
     */
    private BroadcastReceiver mBroadcastReceiver3 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            Log.d(TAG, "onReceive: ACTION FOUND.");

            if (action.equals(BluetoothDevice.ACTION_FOUND)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                Log.d(TAG, deviceArrayList.size() + "");

                Log.d(TAG, "onReceive: " + device.getName() + ": " + device.getAddress());
                if (!deviceArrayList.contains(device)) deviceArrayList.add(device);
                Log.d(TAG, deviceArrayList.size() + "");
                deviceListAdapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy called");
        try {
            unregisterReceiver(btReceiver);
        }catch(Exception e){
            Log.d(TAG,e.getMessage());
        }
        try {
            unregisterReceiver(enable_discovery_receiver);
        }catch(Exception e){
            Log.d(TAG,e.getMessage());
        }
        try {
            unregisterReceiver(mBroadcastReceiver3);
        }catch(Exception e){
            Log.d(TAG,e.getMessage());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover_bluetooth);
        Button on_off = findViewById(R.id.on_off);
        Button enable_discover = findViewById(R.id.enable_discover);
        listView = findViewById(R.id.device_list);
        deviceArrayList = new ArrayList<>();
        deviceListAdapter = new DeviceListAdapter(Discover_Bluetooth.this, R.layout.device_adapter_list, deviceArrayList);
        listView.setAdapter(deviceListAdapter);
        enable_discover.setOnClickListener(v -> {
            btnEnableDisable_Discoverable(v);
        });
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        on_off.setOnClickListener(v -> {
            enableBluetooth();
        });
        Button discover = findViewById(R.id.discover_devices);
        discover.setOnClickListener(v -> btnDiscover(v));
    }

    // on/off
    public void enableBluetooth() {
        if (bluetoothAdapter == null) {
            Log.d("enableBlueTooth", "enabledisableBT: your device doesn't have BT");
        }
        if (!bluetoothAdapter.isEnabled()) {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(intent);

            IntentFilter BTintent = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
            registerReceiver(btReceiver, BTintent);
        }
        if (bluetoothAdapter.isEnabled()) {
            bluetoothAdapter.disable();

            IntentFilter BTintent = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
            registerReceiver(btReceiver, BTintent);
        }
    }

    // show device to other
    public void btnEnableDisable_Discoverable(View view) {
        Log.d(TAG, "btnEnableDisable_Discoverable: Making device discoverable for 300 seconds");

        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        startActivity(intent);

        IntentFilter intentFilter = new IntentFilter(bluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
        registerReceiver(enable_discovery_receiver, intentFilter);
    }

    // start scan
    public void btnDiscover(View view) {
        Log.d(TAG, "btnDiscover: Looking for unpaired devices.");

        if (bluetoothAdapter.isDiscovering()) {
            bluetoothAdapter.cancelDiscovery();
            Log.d(TAG, "btnDiscover: Canceling discovery.");

            //check BT permissions in manifest
            checkBTPermissions();

            bluetoothAdapter.startDiscovery();
            IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(mBroadcastReceiver3, discoverDevicesIntent);
        }
        if (!bluetoothAdapter.isDiscovering()) {

            //check BT permissions in manifest
            checkBTPermissions();

            bluetoothAdapter.startDiscovery();
            IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(mBroadcastReceiver3, discoverDevicesIntent);
        }
    }

    /**
     * This method is required for all devices running API23+
     * Android must programmatically check the permissions for bluetooth. Putting the proper permissions
     * in the manifest is not enough.
     * <p>
     * NOTE: This will only execute on versions > LOLLIPOP because it is not needed otherwise.
     */
    private void checkBTPermissions() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            int permissionCheck = this.checkSelfPermission("Manifest.permission.ACCESS_FINE_LOCATION");
            permissionCheck += this.checkSelfPermission("Manifest.permission.ACCESS_COARSE_LOCATION");
            if (permissionCheck != 0) {

                this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1001); //Any number
            }
        } else {
            Log.d(TAG, "checkBTPermissions: No need to check permissions. SDK version < LOLLIPOP.");
        }
    }

}