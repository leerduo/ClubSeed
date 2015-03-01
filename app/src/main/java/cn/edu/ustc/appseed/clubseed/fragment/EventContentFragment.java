package cn.edu.ustc.appseed.clubseed.fragment;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import java.net.URL;

import cn.edu.ustc.appseed.clubseed.R;
import cn.edu.ustc.appseed.clubseed.jsondata.ViewActivityPhp;
import cn.edu.ustc.appseed.clubseed.utils.AppUtils;

public class EventContentFragment extends Fragment {
    TextView mTextView;
    int ID;
    String title;
    Drawable mDrawable;

    public EventContentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ID = getActivity().getIntent().getIntExtra(NoticeFragment.EXTRA_ACTIVITY_ID, 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_event_content, container, false);
        mTextView = (TextView) v.findViewById(R.id.textViewEventContent);
        if (ID == 0) {
            Toast.makeText(getActivity(), "未知的网络错误！", Toast.LENGTH_SHORT).show();
        } else {
            new ContentAsyncTask().execute(ID);
        }
        return v;
    }

    private class ContentAsyncTask extends AsyncTask<Integer, Void, String> {
        @Override
        protected String doInBackground(Integer... params) {
            String url = "http://clubseed.sinaapp.com/api/viewactivity.php?format=json2&ID=" + params[0];
            StringBuilder html = new StringBuilder("<img src=\"");
            ViewActivityPhp mViewActivityPhp = null;
            String jsonString = AppUtils.getJSONString(url);
            if (jsonString == null) {
                Toast.makeText(getActivity(), "未知的网络错误", Toast.LENGTH_SHORT).show();
                return "";
            } else {
                mViewActivityPhp =
                        JSON.parseObject(
                                jsonString,
                                ViewActivityPhp.class);
                html.append(mViewActivityPhp.getData().getPhotoURL()).append("\"/></br>").append(mViewActivityPhp.getData().getContent());
                return html.toString();
            }

        }

        @Override
        protected void onPostExecute(String html) {
            Html.ImageGetter imageGetter = new Html.ImageGetter() {
                @Override
                public Drawable getDrawable(String source) {
                    new ImageGetterAsyncTask().execute(source);
                    if(mDrawable==null){
                        return getActivity().getResources().getDrawable(R.drawable.empty_slider);
                    }
                    return mDrawable;
                }
            };
            mTextView.setText(Html.fromHtml(html, imageGetter, null));
        }
    }

    private class ImageGetterAsyncTask extends AsyncTask<String, Void, Drawable>{

        @Override
        protected Drawable doInBackground(String... params) {
            Drawable drawable = null;
            URL Url;
            try {
                Url = new URL(params[0]);
                drawable = Drawable.createFromStream(Url.openStream(), "");
                return drawable;
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(Drawable result) {
                mDrawable = result;
        }
    }
}
