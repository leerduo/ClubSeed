package cn.edu.ustc.appseed.clubseed.activity;

/*
* Show the detail content of the event which you select.
* Why to use a custom toolbar instead of the default toolbar in ActionBarActivity?
* Because the custom toolbar is very convenient to edit it and good to unify the GUI.
 */

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import cn.edu.ustc.appseed.clubseed.R;
import cn.edu.ustc.appseed.clubseed.fragment.EventContentFragment;
import cn.edu.ustc.appseed.clubseed.fragment.NoticeFragment;

public class EventContentActivity extends ActionBarActivity {
    private Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_content);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        FragmentManager fm = getFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.eventFragmentContainer);

        if (fragment == null) {
            fragment = new EventContentFragment();
            fm.beginTransaction().add(R.id.eventFragmentContainer, fragment).commit();
        }
        String title = this.getIntent().getStringExtra(NoticeFragment.EXTRA_TITLE);
        setTitle(title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event_content, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
