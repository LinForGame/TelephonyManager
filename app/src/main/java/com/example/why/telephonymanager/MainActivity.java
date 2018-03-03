package com.example.why.telephonymanager;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    ListView showView;
    String[] statusNames;
    ArrayList<String> statusValues = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        statusNames = getResources().getStringArray(R.array.statusNames);
        String[] simState = getResources().getStringArray(R.array.simState);
        String[] phoneType = getResources().getStringArray(R.array.phoneType);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        statusValues.add(telephonyManager.getDeviceId());
        statusValues.add(telephonyManager.getDeviceSoftwareVersion()!=null?telephonyManager.getDeviceSoftwareVersion():"未知");
        statusValues.add(telephonyManager.getNetworkOperator());
        statusValues.add(telephonyManager.getNetworkOperatorName());
        statusValues.add(phoneType[telephonyManager.getPhoneType()]);
        statusValues.add(telephonyManager.getCellLocation()!=null?telephonyManager.getCellLocation().toString():"未知位置");
        statusValues.add(telephonyManager.getSimCountryIso());
        statusValues.add(telephonyManager.getSimSerialNumber());
        statusValues.add(simState[telephonyManager.getSimState()]);

        showView = findViewById(R.id.show);
        ArrayList<Map<String,String>> status = new ArrayList<>();
        for(int i=0; i< statusValues.size();i++){
            HashMap<String,String> map = new HashMap<>();
            map.put("name",statusNames[i]);
            map.put("value",statusValues.get(i));
            status.add(map);

        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(this,
                status,
                R.layout.line,
                new String[]{"name","value"},
                new int[]{R.id.name,R.id.value});
        showView.setAdapter(simpleAdapter);
    }
}
