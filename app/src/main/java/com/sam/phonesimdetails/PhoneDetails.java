package com.sam.phonesimdetails;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PhoneDetails extends AppCompatActivity {

    private TextView tv;
    private Button next;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phonedetails);

        tv = (TextView) findViewById(R.id.phone);
        next = (Button) findViewById(R.id.next);

        getPhoneDetails();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PhoneDetails.this, SimDetails.class));
            }
        });
    }

    public void getPhoneDetails(){
//        DisplayMetrics dm = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(dm);
//        double x = Math.pow(mWidthPixels / dm.xdpi, 2);
//        double y = Math.pow(mHeightPixels / dm.ydpi, 2);
//        screenInches = Math.sqrt(x + y);
//        rounded = df2.format(screenInches);
//        densityDpi = (int) (dm.density * 160f);




        String phone_manufacturer = Build.MANUFACTURER;
        String phone_brand = Build.BRAND;
        String phone_model = Build.MODEL;
        String phone_board = Build.BOARD;
        String phone_hardware = Build.HARDWARE;
        String phone_serial_no = Build.SERIAL;
        //String phone_android_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        String phone_bootloader = Build.BOOTLOADER;
        String phone_user = Build.USER;
        String phone_host = Build.HOST;
        String phone_os_version = Build.VERSION.RELEASE;
        String phone_api_level = Build.VERSION.SDK_INT + "";
        String phone_build_id = Build.ID;
        String phone_build_time = Build.TIME + "";
        String phone_fingerprint = Build.FINGERPRINT;

        String fstr = "Phone Manufacturer: " + phone_manufacturer + "\n" +
                      "Phone Brand: " + phone_brand + "\n" +
                      "Phone Model: " + phone_model + "\n" +
                      "Phone Board: " + phone_board + "\n" +
                      "Phone Hardware: " + phone_hardware + "\n" +
                      "Phone Serial No.: " + phone_serial_no + "\n" +
                      "Phone Bootloader: " + phone_bootloader + "\n" +
                      "Phone User: " + phone_user + "\n" +
                      "Phone Host: " + phone_host + "\n" +
                      "Phone OS Version: " + phone_os_version + "\n" +
                      "Phone API Level: " + phone_api_level + "\n" +
                      "Phone Build ID: " + phone_build_id + "\n" +
                      "Phone Build Time:" + phone_build_time + "\n" +
                      "Phone Fingerprint: "+ phone_fingerprint + "\n";

        tv.setText(fstr);
    }
}
