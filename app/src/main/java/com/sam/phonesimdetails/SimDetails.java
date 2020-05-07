package com.sam.phonesimdetails;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.reflect.Method;
import java.util.List;

public class SimDetails extends AppCompatActivity {

    private final static int READ_PHONE_STATE_CODE = 345;
    private TextView tv;
    private Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simdetails);

        tv = (TextView) findViewById(R.id.sim);
        next = (Button) findViewById(R.id.next);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            System.out.println("No Phone Permission");
            ActivityCompat.requestPermissions(SimDetails.this, new String[]{Manifest.permission.READ_PHONE_STATE}, READ_PHONE_STATE_CODE);
        } else {
            getSIMDetails();
        }

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SimDetails.this, PhoneDetails.class));
            }
        });

    }

    public void getSIMDetails() {

        SubscriptionManager subscriptionManager = SubscriptionManager.from(getApplicationContext());
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        List<SubscriptionInfo> subsInfoList = subscriptionManager.getActiveSubscriptionInfoList();
        SubscriptionInfo si1 = subsInfoList.get(0);
        SubscriptionInfo si2 = subsInfoList.get(1);


        String fstr = "SIM1:- \n Name: " + si1.getDisplayName() + ", \n ICCID: " + si1.getIccId() + ", \n IMSI: " + getSimIMSI(0) + ", \n IMEI: " + getIMEI(0) +
                "\n \n SIM2:- \n Name: " + si2.getDisplayName() + ", \n ICCID: " + si2.getIccId() + ", \n IMSI: " + getSimIMSI(1) + ", \n IMEI: " + getIMEI(1);
        tv.setText(fstr);


    }

    @SuppressLint("HardwareIds")
    public String getIMEI(int slotIndex) {
        String imei = "UNKNOWN";
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                return imei;
            }
            imei = tm.getImei(slotIndex);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { // older OS  versions
            tm.getDeviceId(slotIndex);
        }
        return imei;
    }

    @SuppressLint("HardwareIds")
    public String getSimIMSI(int slotIndex) {
        String imsi = "UNKNOWN";
        TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        SubscriptionManager sm = (SubscriptionManager) getSystemService(TELEPHONY_SUBSCRIPTION_SERVICE);

        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                return imsi;
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                imsi = tm.createForSubscriptionId(sm.getActiveSubscriptionInfoForSimSlotIndex(slotIndex).getSubscriptionId()).getSubscriberId();
            }else{
                Method getSubId = TelephonyManager.class.getMethod("getSubscriberId", int.class);
                imsi = (String) getSubId.invoke(tm, sm.getActiveSubscriptionInfoForSimSlotIndex(slotIndex).getSubscriptionId());
            }
            return imsi;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imsi;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case READ_PHONE_STATE_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission granted!
                    getSIMDetails();

                } else {
                    // permission denied
                }
                return;
            }

        }
    }
}
