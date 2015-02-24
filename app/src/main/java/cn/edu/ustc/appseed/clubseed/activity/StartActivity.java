package cn.edu.ustc.appseed.clubseed.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.LinkedList;

import cn.edu.ustc.appseed.clubseed.R;
import cn.edu.ustc.appseed.clubseed.jsondata.ListPhp;
import cn.edu.ustc.appseed.clubseed.jsondata.ListPhpDetail;
import cn.edu.ustc.appseed.clubseed.utils.AppUtils;

public class StartActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        AppUtils.sAppContext = getApplicationContext();
        LinkedList<ListPhpDetail> data = new LinkedList<>();
        data.add(new ListPhpDetail("0","未知活动","0","未知","无","无","无","无","无","201503012000"));
        AppUtils.sListPhp = new ListPhp(0,"",data);
        Log.d("HR", data.get(0).getTitle());
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
        finish();
    }
}
