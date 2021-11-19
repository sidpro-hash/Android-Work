package com.example.madpractical;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.widget.TextView;
import android.widget.Toast;
/*
Broadcast in android is the system-wide events that can occur when the device starts,
when a message is received on the device or when incoming calls are received, or when a
device goes to airplane mode, etc. Broadcast Receivers are used to respond to these
system-wide events. Broadcast Receivers allow us to register for the system and application
events, and when that event happens, then the register receivers get notified.
There are mainly two types of Broadcast Receivers:
Static Broadcast Receivers: These types of Receivers are declared in the manifest file and works even if the app is closed.
Dynamic Broadcast Receivers: These types of receivers work only if the app is active or minimized.
*/
// if we register this BroadcastReceiver by putting it in manifest file then it's statically register.
// here we have done dynamically and statically also.
public class BroadcastReceiverExample extends BroadcastReceiver {

    TextView brod_text;

    BroadcastReceiverExample(){}

    BroadcastReceiverExample(TextView txt){
        brod_text = txt;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Toast.makeText(context, "Boot completed", Toast.LENGTH_SHORT).show();
        }
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            Toast.makeText(context, "Internet Connectivity changed", Toast.LENGTH_SHORT).show();
        }
        if(Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())){
            //Toast.makeText(context, "Battery Changed", Toast.LENGTH_SHORT).show();
            int percentage = intent.getIntExtra("level",0);
            if(percentage!=0){
                brod_text.setText("Battery Level: "+percentage+"%");
            }
        }
        if(Intent.ACTION_POWER_CONNECTED.equals(intent.getAction())){
            Toast.makeText(context, "Power Connected", Toast.LENGTH_SHORT).show();
        }
        if(Intent.ACTION_AIRPLANE_MODE_CHANGED.equals(intent.getAction())){
            Toast.makeText(context, "Airplane Mode Changed", Toast.LENGTH_SHORT).show();
        }
    }
}
