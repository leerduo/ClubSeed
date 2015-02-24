package cn.edu.ustc.appseed.clubseed.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import cn.edu.ustc.appseed.clubseed.R;
import cn.edu.ustc.appseed.clubseed.jsondata.ViewActivityPhp;
import cn.edu.ustc.appseed.clubseed.utils.AppUtils;

public class EventContentFragment extends Fragment {
    TextView mTextView;
    int ID;
    String title;

    public EventContentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ID = getActivity().getIntent().getIntExtra(EventShowFragment.EXTRA_ACTIVITY_ID, 0);
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
                return mViewActivityPhp.getData().getContent();
            }

        }

        @Override
        protected void onPostExecute(String text) {
            mTextView.setText(text);
        }
    }
}
