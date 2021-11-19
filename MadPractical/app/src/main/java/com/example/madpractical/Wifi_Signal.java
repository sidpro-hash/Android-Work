package com.example.madpractical;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Wifi_Signal extends AppCompatActivity {

    private WifiManager wifiManager;
    private WifiReceiver wifiReceiver;
    private List<ScanResult> result = new ArrayList<>();
    ScanResultsAdapter scanResultsAdapter;
    ListView deviceList;
    StringBuilder sb = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_signal);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.CHANGE_WIFI_STATE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PackageManager.PERMISSION_GRANTED);

        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifiReceiver = new WifiReceiver();
        registerReceiver(wifiReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        Log.d("MAinActivity", "Starting scan");
        wifiManager.startScan();

        Button on_off = findViewById(R.id.on_off_wifi);
        on_off.setOnClickListener(v -> {
            if (!wifiManager.isWifiEnabled()) {
                wifiManager.setWifiEnabled(true);
                Log.d("Wifi state", "wifi enable");
            } else {
                wifiManager.setWifiEnabled(false);
                Log.d("Wifi state", "wifi disable");
            }
        });

        Button scan = findViewById(R.id.enable_discover_wifi);
        scan.setOnClickListener(v -> {
            wifiManager.setWifiEnabled(true);
            Boolean success = wifiManager.startScan();

            if (success) Log.d("startscan", "started");
            else Log.d("startscan", "not started");

            Log.d("Buttton", "Scanning...");
        });
    }

    private void setadapter() {
        deviceList = findViewById(R.id.device_list);
        scanResultsAdapter = new ScanResultsAdapter(Wifi_Signal.this, R.layout.network_list_row, result, wifiManager);
        deviceList.setAdapter(scanResultsAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            unregisterReceiver(wifiReceiver);
        }catch(Exception e){
            Log.d("wifiReceiver",e.getMessage());
        }
    }

    public class WifiReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            sb = new StringBuilder();
            result = wifiManager.getScanResults();
            Log.d("WifiReceiver", "Received Something");
            // prepare text for display and CSV table
            sb.append("Number of APs Detected: ");
            sb.append((Integer.valueOf(result.size())).toString());
            sb.append("\n\n");
            for (int i = 0; i < result.size(); i++) {
                // sb.append((Integer.valueOf(i + 1)).toString() + ".");
                // SSID
                sb.append("SSID:").append((result.get(i)).SSID);
                sb.append("\n");
                // BSSID
                sb.append("BSSID:").append((result.get(i)).BSSID);
                sb.append("\n");
                // capabilities
                sb.append("Capabilities:").append(
                        (result.get(0)).capabilities);
                sb.append("\n");
                // frequency
                sb.append("Frequency:").append((result.get(i)).frequency);
                sb.append("\n");

                // level
                sb.append("Level:").append((result.get(i)).level);
                sb.append("\n\n");
            }
            Log.d("WifiReceiver", sb.toString());
            Log.d("WifiReceiver", "setting adapter");
            setadapter();
            // notify that Wi-Fi scan has finished
        }
    }


}