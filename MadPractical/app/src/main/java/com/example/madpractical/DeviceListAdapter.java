package com.example.madpractical;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class DeviceListAdapter extends ArrayAdapter<BluetoothDevice> {

    private LayoutInflater mLayoutInflater;
    private int mViewResourceId;

    public DeviceListAdapter(Context context, int tvResourceId, ArrayList<BluetoothDevice> devices) {
        super(context, tvResourceId, devices);
        mLayoutInflater = LayoutInflater.from(context);
        mViewResourceId = tvResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mLayoutInflater.inflate(mViewResourceId, parent, false);

        BluetoothDevice device = getItem(position);
        TextView deviceName = convertView.findViewById(R.id.tvDeviceName);
        TextView deviceAdress = convertView.findViewById(R.id.tvDeviceAddress);
        deviceName.setText(device.getName());
        deviceAdress.setText(device.getAddress());

        return convertView;
    }

}
