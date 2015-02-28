package cn.edu.ustc.appseed.clubseed.activity;
/*
* the first activity when ClubSeed starts,
* loading the global variables and initializing the application's configuration.
*/

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;

import java.util.LinkedList;

import cn.edu.ustc.appseed.clubseed.R;
import cn.edu.ustc.appseed.clubseed.jsondata.ListPhp;
import cn.edu.ustc.appseed.clubseed.jsondata.ListPhpDetail;
import cn.edu.ustc.appseed.clubseed.utils.AppUtils;

public class StartActivity extends Activity {


    private final int SPLASH_DISPLAY_LENGHT = 2000; //启动界面显示两秒

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        AppUtils.sAppContext = getApplicationContext();
        AppUtils.sSharedPreferences = PreferenceManager.getDefaultSharedPreferences(AppUtils.sAppContext);
        AppUtils.isReadingMode = AppUtils.sSharedPreferences.getBoolean(getString(R.string.pref_reading_mode), true);
        AppUtils.NullListPhp = setNullListPhp();
        String phoneInfo = "Product: " + android.os.Build.PRODUCT;
        phoneInfo += ", TAGS: " + android.os.Build.TAGS;
        phoneInfo += ", VERSION_CODES.BASE: " + android.os.Build.VERSION_CODES.BASE;
        phoneInfo += ", MODEL: " + android.os.Build.MODEL;
        phoneInfo += ", SDK: " + android.os.Build.VERSION.SDK;
        phoneInfo += ", VERSION.RELEASE: " + android.os.Build.VERSION.RELEASE;
        phoneInfo += ", DEVICE: " + android.os.Build.DEVICE;
        phoneInfo += ", DISPLAY: " + android.os.Build.DISPLAY;
        phoneInfo += ", BRAND: " + android.os.Build.BRAND;
        phoneInfo += ", BOARD: " + android.os.Build.BOARD;
        phoneInfo += ", FINGERPRINT: " + android.os.Build.FINGERPRINT;
        phoneInfo += ", ID: " + android.os.Build.ID;
        phoneInfo += ", MANUFACTURER: " + android.os.Build.MANUFACTURER;
        phoneInfo += ", USER: " + android.os.Build.USER;
        AppUtils.phoneInfo = phoneInfo;

        new Handler().postDelayed(new Runnable(){

            @Override
            public void run() {
                Intent mainIntent = new Intent(StartActivity.this,MainActivity.class);
                StartActivity.this.startActivity(mainIntent);
                StartActivity.this.finish();
            }

        }, SPLASH_DISPLAY_LENGHT);
    }

    private ListPhp setNullListPhp() {
        ListPhpDetail data = new ListPhpDetail();
        data.setClubid("0");
        data.setClubname("NULL");
        data.setID("0");
        data.setActivitytime("299311090000");
        data.setCreatetime("199311090000");
        data.setPlace("Nowhere");
        data.setRecommend("F");
        data.setTag("NULL");
        data.setTitle("NULL");
        data.setSummary("Ha");
        LinkedList<ListPhpDetail> linkedList = new LinkedList<>();
        linkedList.add(data);
        return new ListPhp(0, "", linkedList);
    }
}
