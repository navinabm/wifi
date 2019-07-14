package com.example.wifionof;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Switch aSwitch;
    WifiManager wifiManager;
    TextView textView;
    @SuppressLint("WifiManagerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        aSwitch=(Switch)findViewById(R.id.myswitch);
        textView  = (TextView)findViewById(R.id.textView);
        wifiManager = (WifiManager)getSystemService(WIFI_SERVICE);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked && !wifiManager.isWifiEnabled())
                {
                    //to switch on
                   wifiManager.setWifiEnabled(true);
                }
                else if (!isChecked && wifiManager.isWifiEnabled())
                {
                    //to switch off

                    wifiManager.setWifiEnabled(false);
                }

            }
        });

        MyBroadCastRecevier myBroadCastRecevier=new MyBroadCastRecevier();
        registerReceiver(myBroadCastRecevier , new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)); //registering broadcast receiver
    }

class MyBroadCastRecevier extends BroadcastReceiver
{

    @Override
    public void onReceive(Context context, Intent intent) {

        StringBuffer stringBuffer=new StringBuffer();
        List<ScanResult> list=wifiManager.getScanResults();
        for(ScanResult scanResult: list)
        {
            stringBuffer.append(scanResult);
        }
        textView.setText(stringBuffer);
    }
}

    }

