package cn.edu.ustc.appseed.clubseed.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import cn.edu.ustc.appseed.clubseed.R;
import cn.edu.ustc.appseed.clubseed.adapter.DrawerListViewAdapter;

public class MainActivity extends ActionBarActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private ListView drawerList;
    private DrawerListViewAdapter navigationDrawerAdapter;
    private String[] drawerListData = {"活动信息","我的收藏", "设置"};
    private Toolbar toolbar;
    private Fragment[] fragments = new Fragment[3];
    private int debug = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerList = (ListView) findViewById(R.id.drawerList);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationDrawerAdapter = new DrawerListViewAdapter(this, drawerListData);
        drawerList.setAdapter(navigationDrawerAdapter);
        drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectFragment(position);
            }
        });
        if (toolbar != null) {
            toolbar.setTitle("ClubSeed");
            setSupportActionBar(toolbar);
        }
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);

        FragmentManager fm = getFragmentManager();
        fragments[0] = fm.findFragmentById(R.id.notice_fragment);
        fragments[1] = fm.findFragmentById(R.id.star_fragment);
        fragments[2] = fm.findFragmentById(R.id.settings_fragment);
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.commit();
        for (int i = 1; i < fragments.length; i++) {
            transaction.hide(fragments[i]);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_search) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    private void showFragment(int fragmentIndex, boolean addToBackStack) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        for (int i = 0; i < fragments.length; i++) {
            if (i == fragmentIndex) {
                transaction.show(fragments[i]);
            } else {
                transaction.hide(fragments[i]);
            }
        }
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    private void selectFragment(int position) {
        // update the main content by replacing fragments
        drawerLayout.closeDrawer(drawerList);
        switch (position) {
            case 0:
                toolbar.setTitle("ClubSeed");
                break;
            case 1:
                toolbar.setTitle("我的收藏");
                break;
            case 2:
                toolbar.setTitle("设置");
                break;
        }
        showFragment(position, false);
    }
}
