package cn.edu.ustc.appseed.clubseed.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.squareup.okhttp.OkHttpClient;

import cn.edu.ustc.appseed.clubseed.R;
import cn.edu.ustc.appseed.clubseed.activity.EventContentActivity;
import cn.edu.ustc.appseed.clubseed.adapter.EventListViewAdapter;
import cn.edu.ustc.appseed.clubseed.jsondata.GetPhp;
import cn.edu.ustc.appseed.clubseed.jsondata.ListPhp;
import cn.edu.ustc.appseed.clubseed.jsondata.ListPhpDetail;
import cn.edu.ustc.appseed.clubseed.utils.AppUtils;
import cn.edu.ustc.appseed.clubseed.listener.EndlessScrollListener;

public class EventShowFragment extends Fragment {
    BaseAdapter adapter;
    ListView mListView;
    SliderLayout mSliderLayout;
    LinearLayout mLinearLayout;
    LayoutInflater mLayoutInflater;
    SwipeRefreshLayout mSwipeRefreshLayout;
    ListPhp mListPhp;
    boolean boolFirstLoad = true;
    public static final String EXTRA_ACTIVITY_ID = "ID";
    public static final String EXTRA_TITLE = "TITLE";

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_event_show, container, false);
        mLayoutInflater = LayoutInflater.from(getActivity());
        mLinearLayout = (LinearLayout) mLayoutInflater.inflate(R.layout.header_list_events, null);
        mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipeContainer);
        mListView = (ListView) v.findViewById(R.id.listViewEvents);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getActivity(), "刷新中...", Toast.LENGTH_SHORT).show();
                boolFirstLoad = true;
                new ListViewLoadAsyncTask().execute(1);
                new SliderAsyncTask().execute();
            }
        });
        new ListViewLoadAsyncTask().execute(1);
        new SliderAsyncTask().execute();
        return v;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void startListView(ListPhp listPhp) {
        adapter = new EventListViewAdapter(getActivity(), listPhp);
        //must keep order of "addHeader, setAdapter, setOnXxxListener"
        mListView.addHeaderView(mLinearLayout);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListPhpDetail listPhpDetail = (ListPhpDetail) parent.getItemAtPosition(position);
                Intent i = new Intent(getActivity(), EventContentActivity.class);
                i.putExtra(EXTRA_ACTIVITY_ID, Integer.parseInt(listPhpDetail.getID()));
                i.putExtra(EXTRA_TITLE, listPhpDetail.getTitle());
                startActivity(i);
            }
        });
        mListView.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                new ListViewLoadAsyncTask().execute(page);
            }
        });
        adapter.notifyDataSetChanged();
    }

    private void setSliderLayout(GetPhp getPhp) {
        mSliderLayout = (SliderLayout) mLinearLayout.findViewById(R.id.sliderLayoutEvents);
        if (getPhp == null) {
            Toast.makeText(getActivity(), "未知的网络错误", Toast.LENGTH_SHORT).show();
            for (int i = 0; i < 5; i++) {
                TextSliderView mTextSliderView = new TextSliderView(getActivity());
                mTextSliderView.description("").image(R.drawable.empty_slider);
                mTextSliderView.getBundle().putString(EXTRA_ACTIVITY_ID, "0");
                mTextSliderView.getBundle().putString(EXTRA_TITLE, "");
                mTextSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                    @Override
                    public void onSliderClick(BaseSliderView slider) {
                        int ID = Integer.parseInt(slider.getBundle().getString(EXTRA_ACTIVITY_ID));
                        String title = slider.getBundle().getString(EXTRA_TITLE);
                        Intent i = new Intent(getActivity(), EventContentActivity.class);
                        i.putExtra(EXTRA_ACTIVITY_ID, ID);
                        i.putExtra(EXTRA_TITLE, title);
                        startActivity(i);
                    }
                });
                mSliderLayout.addSlider(mTextSliderView);
            }
        } else {
            for (int i = 0; i < getPhp.getData().size(); i++) {
                TextSliderView mTextSliderView = new TextSliderView(getActivity());
                mTextSliderView.description(getPhp.getData().get(i).getTitle()).image(getPhp.getData().get(i).getPhotoURL());
                mTextSliderView.getBundle().putString(EXTRA_ACTIVITY_ID, getPhp.getData().get(i).getID());
                mTextSliderView.getBundle().putString(EXTRA_TITLE, getPhp.getData().get(i).getTitle());
                mTextSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                    @Override
                    public void onSliderClick(BaseSliderView slider) {
                        int ID = Integer.parseInt(slider.getBundle().getString(EXTRA_ACTIVITY_ID));
                        String title = slider.getBundle().getString(EXTRA_TITLE);
                        Intent i = new Intent(getActivity(), EventContentActivity.class);
                        i.putExtra(EXTRA_ACTIVITY_ID, ID);
                        i.putExtra(EXTRA_TITLE, title);
                        startActivity(i);
                    }
                });
                mSliderLayout.addSlider(mTextSliderView);
            }
        }
    }

    private class ListViewLoadAsyncTask extends AsyncTask<Integer, Void, ListPhp> {
        @Override
        protected ListPhp doInBackground(Integer... params) {
            String jsonString = null;
            String url = "http://clubseed.sinaapp.com/api/list.php?format=json2&perpage=10&page=" + params[0];
            jsonString = AppUtils.getJSONString(url);
            Log.d("HR",jsonString);
            if (jsonString == null) {
                return null;
            } else {
                return (ListPhp) JSON.parseObject(jsonString, ListPhp.class);
            }
        }

        @Override
        protected void onPostExecute(ListPhp listPhp) {
            if (listPhp == null) {
                Toast.makeText(getActivity(), "未知的网络错误", Toast.LENGTH_SHORT).show();
            } else if (boolFirstLoad) {
                boolFirstLoad = false;
                mListPhp = listPhp;
                startListView(mListPhp);
            } else if (listPhp.getData().isEmpty()) {
                Toast.makeText(getActivity(), "没有更多活动了", Toast.LENGTH_SHORT).show();
            } else {
                mListPhp.appendData(listPhp.getData());
                adapter.notifyDataSetChanged();
            }
        }
    }

    private class SliderAsyncTask extends AsyncTask<Void, Void, GetPhp> {
        private final OkHttpClient client = new OkHttpClient();

        @Override
        protected GetPhp doInBackground(Void... params) {
            String jsonString = null;
            String url = "http://clubseed.sinaapp.com/api/get.php?format=json2";
            jsonString = AppUtils.getJSONString(url);
            if (jsonString == null) {
                return null;
            } else {
                return (GetPhp) JSON.parseObject(jsonString, GetPhp.class);
            }
        }

        @Override
        protected void onPostExecute(GetPhp getPhp) {
            setSliderLayout(getPhp);
        }
    }
}