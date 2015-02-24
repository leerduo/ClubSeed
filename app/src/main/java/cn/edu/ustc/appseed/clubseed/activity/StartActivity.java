package cn.edu.ustc.appseed.clubseed.activity;
/*
* the first activity when ClubSeed starts,
* loading the global variables and initializing the application's configuration.
*/

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import cn.edu.ustc.appseed.clubseed.R;
import cn.edu.ustc.appseed.clubseed.utils.AppUtils;

public class StartActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        AppUtils.sAppContext = getApplicationContext();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
