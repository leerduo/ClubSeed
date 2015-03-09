package cn.edu.ustc.appseed.clubseed.activity;

/*
* Show the detail content of the event which you select.
* Why to use a custom toolbar instead of the default toolbar in ActionBarActivity?
* Because the custom toolbar is very convenient to edit it and good to unify the GUI.
 */

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import cn.edu.ustc.appseed.clubseed.R;
import cn.edu.ustc.appseed.clubseed.data.Event;
import cn.edu.ustc.appseed.clubseed.data.ViewActivityPhp;
import cn.edu.ustc.appseed.clubseed.fragment.NoticeFragment;
import cn.edu.ustc.appseed.clubseed.fragment.StarFragment;
import cn.edu.ustc.appseed.clubseed.utils.AppUtils;

public class StarContentActivity extends ActionBarActivity {
    private Toolbar toolbar;
    private TextView mTextView;
    private ImageView mImageView;
    private TextView nullTextView;
    private Event mEvent;
    int ID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_content);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mTextView = (TextView) findViewById(R.id.textViewEventContent);
        mImageView = (ImageView) findViewById(R.id.imgContent);
        if (toolbar != null) {
            toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back));
            setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
        ID = getIntent().getIntExtra(StarFragment.EXTRA_ID, 0);
        mEvent = AppUtils.savedEvents.get(ID);
        setTitle(mEvent.getTitle());
        mTextView.setText(mEvent.getContent());
        mImageView.setImageBitmap(mEvent.getBitmap());
    }
}
