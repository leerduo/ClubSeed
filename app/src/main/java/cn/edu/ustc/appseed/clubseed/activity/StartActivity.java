package cn.edu.ustc.appseed.clubseed.activity;
/*
* the first activity when ClubSeed starts,
* loading the global variables and initializing the application's configuration.
*/

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;

import com.alibaba.fastjson.JSON;

import java.io.File;
import java.io.FileFilter;
import java.util.HashMap;
import java.util.LinkedList;

import cn.edu.ustc.appseed.clubseed.R;
import cn.edu.ustc.appseed.clubseed.data.ListPhp;
import cn.edu.ustc.appseed.clubseed.data.Event;
import cn.edu.ustc.appseed.clubseed.data.ViewActivityPhp;
import cn.edu.ustc.appseed.clubseed.utils.AppUtils;
import cn.edu.ustc.appseed.clubseed.utils.Debug;

public class StartActivity extends Activity {


    private final int SPLASH_DISPLAY_LENGHT = 2000; //启动界面显示两秒

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        AppUtils.sAppContext = getApplicationContext();
        AppUtils.sSharedPreferences = PreferenceManager.getDefaultSharedPreferences(AppUtils.sAppContext);
        AppUtils.isReadingMode = AppUtils.sSharedPreferences.getBoolean(getString(R.string.pref_reading_mode), true);
        AppUtils.sNullListPhp = setNullListPhp();
        AppUtils.savedEvents = new HashMap<>();
        String phoneInfo = "Product: " + android.os.Build.PRODUCT;
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
        new Thread(new Runnable() {
            @Override
            public void run() {
                File[] files = AppUtils.sAppContext.getFilesDir().listFiles(new FileFilter() {
                    @Override
                    public boolean accept(File pathname) {
                        return pathname.getName().contains(".event");
                    }
                });
                for (File file : files) {
                    Log.d("HR", "Name:" + file.getName());
                    try {
                        String jsonString = AppUtils.loadString(file.getName());
                        Event event = (Event) JSON.parseObject(jsonString, Event.class);Debug.showLog(event.getSummary());
                        File bitmapFile = new File(AppUtils.sAppContext.getFilesDir()+"/"+event.getID() + ".png");
                        if (bitmapFile.exists()) {
                            Debug.showLog(bitmapFile.getName());
                            Bitmap bitmap = AppUtils.loadBitmap(bitmapFile.getName());
                            event.setBitmap(bitmap);
                        }
                        AppUtils.savedEvents.put(Integer.parseInt(event.getID()), event);
                    } catch (Exception e) {
                        e.printStackTrace();
                        continue;
                    }
                }
            }
        }).run();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent mainIntent = new Intent(StartActivity.this, MainActivity.class);
                StartActivity.this.startActivity(mainIntent);
                StartActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGHT);
    }

    private ListPhp setNullListPhp() {
        Event data = new Event();
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
        LinkedList<Event> linkedList = new LinkedList<>();
        linkedList.add(data);
        return new ListPhp(0, "", linkedList);
    }
}
