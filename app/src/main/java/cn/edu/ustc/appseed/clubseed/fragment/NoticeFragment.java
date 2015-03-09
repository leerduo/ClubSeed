package cn.edu.ustc.appseed.clubseed.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
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

import cn.edu.ustc.appseed.clubseed.R;
import cn.edu.ustc.appseed.clubseed.activity.EventContentActivity;
import cn.edu.ustc.appseed.clubseed.adapter.EventListViewAdapter;
import cn.edu.ustc.appseed.clubseed.data.ListPhp;
import cn.edu.ustc.appseed.clubseed.data.GetPhp;
import cn.edu.ustc.appseed.clubseed.data.Event;
import cn.edu.ustc.appseed.clubseed.listener.EndlessScrollListener;
import cn.edu.ustc.appseed.clubseed.utils.AppUtils;

/**
 * Created by Hengruo on 2015/2/26.
 */
public class NoticeFragment extends Fragment {
    private BaseAdapter adapter;
    private ListView mListView;
    private SliderLayout mSliderLayout;
    private LinearLayout mLinearLayout;
    private LayoutInflater mLayoutInflater;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ListPhp mListPhp;
    private int listmode = 0; // 0: First load; 1: Refresh; 2: load more
    private int slidermode = 0;
    public static final String EXTRA_ACTIVITY_ID = "ID";
    public static final String EXTRA_TITLE = "TITLE";

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_notice, container, false);
        mLayoutInflater = LayoutInflater.from(getActivity());
        mLinearLayout = (LinearLayout) mLayoutInflater.inflate(R.layout.header_list_events, null);
        mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipeContainer);
        mListView = (ListView) v.findViewById(R.id.listViewEvents);
        mSliderLayout = (SliderLayout) mLinearLayout.findViewById(R.id.sliderLayoutEvents);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getActivity(), "刷新中...", Toast.LENGTH_SHORT).show();
                listmode = 1;
                slidermode = 1;
                new ListViewAsyncTask().execute(1);
                new SliderAsyncTask().execute();
                mSwipeRefreshLayout.setRefreshing(false);
            }

        });
        mListView.addHeaderView(mLinearLayout);
        new ListViewAsyncTask().execute(1);
        new SliderAsyncTask().execute();
        return v;
    }

    private void setEmptySliderLayout() {
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
    }

    private void setSliderLayout(GetPhp getPhp) {
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

    private void setListView(ListPhp listPhp) {
        adapter = new EventListViewAdapter(getActivity(), listPhp);
        //must keep order of "addHeader, setAdapter, setOnXxxListener"
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Event event = (Event) parent.getItemAtPosition(position);
                Intent i = new Intent(getActivity(), EventContentActivity.class);
                i.putExtra(EXTRA_ACTIVITY_ID, Integer.parseInt(event.getID()));
                i.putExtra(EXTRA_TITLE, event.getTitle());
                startActivity(i);
            }
        });
        mListView.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                listmode = 2;
                new ListViewAsyncTask().execute(page);
            }
        });
        mListView.setVisibility(View.VISIBLE);
        adapter.notifyDataSetChanged();
    }

    private void setEmptyListView() {
        adapter = new EventListViewAdapter(getActivity(), AppUtils.sNullListPhp);
        //must keep order of "addHeader, setAdapter, setOnXxxListener"
        mListView.setAdapter(adapter);
        mListView.setVisibility(View.INVISIBLE);
        adapter.notifyDataSetChanged();
    }

    private class ListViewAsyncTask extends AsyncTask<Integer, Void, ListPhp> {
        @Override
        protected ListPhp doInBackground(Integer... params) {
            String jsonString = null;
            String url = "http://clubseed.sinaapp.com/api/list.php?format=json2&perpage=10&page=" + params[0];
            try {
                jsonString = AppUtils.getJSONString(url);
                return (ListPhp) JSON.parseObject(jsonString, ListPhp.class);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ListPhp listPhp) {
            switch (listmode) {
                case 0:
                    if (listPhp == null) {
                        Toast.makeText(getActivity(), "无法连接网络", Toast.LENGTH_SHORT).show();
                        setEmptyListView();
                    } else {
                        listmode = 1;
                        mListPhp = listPhp;
                        setListView(listPhp);
                    }
                    break;
                case 1:
                    if (listPhp == null) {
                        Toast.makeText(getActivity(), "无法连接网络", Toast.LENGTH_SHORT).show();
                    } else {
                        mListPhp = listPhp;
                        setListView(listPhp);
                    }
                    break;
                case 2:
                    if (listPhp == null) {
                        Toast.makeText(getActivity(), "无法连接网络", Toast.LENGTH_SHORT).show();
                    } else {
                        mListPhp.appendData(listPhp.getData());
                        adapter.notifyDataSetChanged();
                    }
                    break;
            }
        }
    }

    private class SliderAsyncTask extends AsyncTask<Void, Void, GetPhp> {
        @Override
        protected GetPhp doInBackground(Void... params) {
            String jsonString = null;
            String url = "http://clubseed.sinaapp.com/api/get.php?format=json2";
            try {
                jsonString = AppUtils.getJSONString(url);
                return (GetPhp) JSON.parseObject(jsonString, GetPhp.class);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(GetPhp getPhp) {
            switch (slidermode) {
                case 0:
                    if (getPhp == null) {
                        Toast.makeText(getActivity(), "无法连接网络", Toast.LENGTH_SHORT).show();
                        setEmptySliderLayout();
                    } else {
                        slidermode = 1;
                        setSliderLayout(getPhp);
                    }
                    break;
                case 1:
                    if (getPhp == null) {
                        Toast.makeText(getActivity(), "无法连接网络", Toast.LENGTH_SHORT).show();
                    } else {
                        mSliderLayout.removeAllSliders();
                        setSliderLayout(getPhp);
                    }
                    break;
            }
        }
    }


}
