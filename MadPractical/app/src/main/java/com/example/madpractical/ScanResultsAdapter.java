package com.example.madpractical;

import java.util.List;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ScanResultsAdapter extends ArrayAdapter<ScanResult> {

    private final Context context;
    private final List<ScanResult> results;
    private int mViewResourceId;
    private LayoutInflater mLayoutInflater;
    WifiManager wifiManager;
    private final String[] signalStrength = {"Weak", "Fair", "Good", "Excellent"};

    public ScanResultsAdapter(Context context, int tvResourceId, List<ScanResult> results, WifiManager wifiManager) {
        super(context, tvResourceId, results);
        this.context = context;
        this.results = results;
        mViewResourceId = tvResourceId;
        mLayoutInflater = LayoutInflater.from(context);
        this.wifiManager = wifiManager;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mLayoutInflater.inflate(mViewResourceId, parent, false);
        if (results.size() > 0) {
            ScanResult result = getItem(position);
            // Get textview fields

            TextView txtSSID = convertView.findViewById(R.id.txtSSID);
            TextView txtBSSID = convertView.findViewById(R.id.txtBSSID);
            TextView txtCapabilities = convertView.findViewById(R.id.txtCapabilities);
            TextView txtFrecuency = convertView.findViewById(R.id.txtFrecuency);
            TextView txtLevel = convertView.findViewById(R.id.txtLevel);

            int level = wifiManager.calculateSignalLevel(result.level);
            // Set fields values
            txtSSID.setText(convertView.getContext().getString(R.string.ssid_msg, result.SSID));
            txtBSSID.setText(convertView.getContext().getString(R.string.bssid_msg, result.BSSID));
            txtCapabilities.setText(convertView.getContext().getString(R.string.capabilities_msg, result.capabilities));
            txtFrecuency.setText(convertView.getContext().getString(R.string.frecuency_msg, Integer.toString(result.frequency)));
            txtLevel.setText(convertView.getContext().getString(R.string.signal_level_msg, signalStrength[level - 1]));
        }
        return convertView;
    }


}
