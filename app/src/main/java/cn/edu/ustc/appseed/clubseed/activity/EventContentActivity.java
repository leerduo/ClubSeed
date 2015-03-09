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
import cn.edu.ustc.appseed.clubseed.fragment.NoticeFragment;
import cn.edu.ustc.appseed.clubseed.data.Event;
import cn.edu.ustc.appseed.clubseed.data.ViewActivityPhp;
import cn.edu.ustc.appseed.clubseed.utils.AppUtils;

public class EventContentActivity extends ActionBarActivity {
    private Toolbar toolbar;
    private TextView mTextView;
    private ImageView mImageView;
    private Event event = null;
    private boolean isSaved;
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
        String title = getIntent().getStringExtra(NoticeFragment.EXTRA_TITLE);
        ID = getIntent().getIntExtra(NoticeFragment.EXTRA_ACTIVITY_ID, 0);
        setTitle(title);

        if (ID == 0) {
            Toast.makeText(this, "未知的网络错误！", Toast.LENGTH_SHORT).show();
        } else if (AppUtils.savedEvents.containsKey(ID)) {
            isSaved = true;
        }
        new ContentAsyncTask().execute(ID);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event_content, menu);
        if (isSaved) {
            menu.getItem(1).setIcon(getResources().getDrawable(R.drawable.ic_star_black));
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_calendar) {
            if (event == null) return false;
            Intent intent = new Intent(Intent.ACTION_EDIT);
            intent.setType("vnd.android.cursor.item/event");
            intent.putExtra("title", event.getTitle());
            intent.putExtra("description", event.getSummary());
            intent.putExtra("beginTime", event.getTime());
            intent.putExtra("endTime", event.getTime() + "7200000");
            startActivity(Intent.createChooser(intent, "请选择一个日历应用"));
            return true;
        }

        if (id == R.id.action_star) {
            if (event == null) return false;
            if (isSaved) {
                new DelEventAsyncTask().execute(item);
            } else {
                new SaveEventAsyncTask().execute(item);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private class SaveEventAsyncTask extends AsyncTask<MenuItem, Void, MenuItem> {
        @Override
        protected MenuItem doInBackground(MenuItem... params) {
            try {
                AppUtils.savedEvents.put(ID, event);
                Bitmap bitmap = event.getBitmap();
                event.setBitmap(null);
                String jsonString = JSON.toJSONString(event);
                AppUtils.saveString(event.getID() + ".event", jsonString);
                if (bitmap != null){
                    event.setBitmap(bitmap);
                    AppUtils.saveBitmap(event.getID() + ".png", bitmap);}
                return params[0];
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(MenuItem item) {
            if (item != null) {
                item.setIcon(getResources().getDrawable(R.drawable.ic_star_black));
                isSaved = true;
                Toast.makeText(EventContentActivity.this, "已收藏", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(EventContentActivity.this, "收藏失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class DelEventAsyncTask extends AsyncTask<MenuItem, Void, MenuItem> {

        @Override
        protected MenuItem doInBackground(MenuItem... params) {
            try {
                AppUtils.deleteStar(event.getID() + ".event");
                AppUtils.deleteStar(event.getID() + ".png");
                return params[0];
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(MenuItem item) {
            if (item != null) {
                item.setIcon(getResources().getDrawable(R.drawable.ic_star_outline_black));
                isSaved = false;
                Toast.makeText(EventContentActivity.this, "已取消收藏", Toast.LENGTH_SHORT).show();
                AppUtils.savedEvents.remove(ID);
            } else {
                Toast.makeText(EventContentActivity.this, "取消收藏失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class ContentAsyncTask extends AsyncTask<Integer, Void, Void> {
        @Override
        protected Void doInBackground(Integer... params) {
            String url = "http://clubseed.sinaapp.com/api/viewactivity.php?format=json2&ID=" + params[0];
            ViewActivityPhp mViewActivityPhp = null;
            try {
                String jsonString = AppUtils.getJSONString(url);
                mViewActivityPhp =
                        JSON.parseObject(
                                jsonString,
                                ViewActivityPhp.class);
                event = mViewActivityPhp.getData();
            } catch (Exception e) {
                Log.d("HR", e.toString());
            }
            try {
                Bitmap bitmap = AppUtils.getBitmapFromURL(mViewActivityPhp.getData().getPhotoURL());
                event.setBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
                event.setBitmap(null);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            String text = event.getContent();
            Bitmap bitmap = event.getBitmap();
            Log.d("HR", "Content:" + event.getClubname());
            if (text == null) {
                Toast.makeText(EventContentActivity.this, "未知的网络错误", Toast.LENGTH_SHORT).show();
            } else
                mTextView.setText(text);
            if (bitmap == null) {
                ;
            } else
                mImageView.setImageBitmap(bitmap);
        }
    }
}
