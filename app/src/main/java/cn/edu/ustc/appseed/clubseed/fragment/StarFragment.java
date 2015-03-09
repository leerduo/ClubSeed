package cn.edu.ustc.appseed.clubseed.fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;

import cn.edu.ustc.appseed.clubseed.R;
import cn.edu.ustc.appseed.clubseed.activity.EventContentActivity;
import cn.edu.ustc.appseed.clubseed.activity.StarContentActivity;
import cn.edu.ustc.appseed.clubseed.adapter.StarListViewAdapter;
import cn.edu.ustc.appseed.clubseed.data.Event;
import cn.edu.ustc.appseed.clubseed.utils.AppUtils;
import cn.edu.ustc.appseed.clubseed.utils.Debug;
import cn.edu.ustc.appseed.clubseed.widget.SwipeListView;

/**
 * A simple {@link Fragment} subclass.
 */
public class StarFragment extends Fragment implements SwipeListView.RemoveListener {
    private SwipeListView mListView;
    private TextView mTextView;
    StarListViewAdapter adapter;
    public static String EXTRA_ID = "ID";

    public StarFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(final Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_star, container, false);
        mTextView = (TextView) v.findViewById(R.id.listViewNoStar);
        mListView = (SwipeListView) v.findViewById(R.id.listViewStars);
        adapter = new StarListViewAdapter(getActivity());
        if (AppUtils.savedEvents.isEmpty()) {
            mTextView.setVisibility(View.VISIBLE);
            mListView.setVisibility(View.INVISIBLE);
        } else {
            mTextView.setVisibility(View.INVISIBLE);
            mListView.setVisibility(View.VISIBLE);
            adapter.resetData(AppUtils.savedEvents);
            mListView.setAdapter(adapter);
            mListView.setRemoveListener(this);
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Event event = (Event) parent.getItemAtPosition(position);
                    Intent i = new Intent(getActivity(), StarContentActivity.class);
                    i.putExtra(EXTRA_ID, Integer.parseInt(event.getID()));
                    startActivity(i);
                }
            });
        }
        return v;
    }

    @Override
    public void onHiddenChanged(boolean hidden){
        super.onHiddenChanged(hidden);
        if(AppUtils.savedEvents.isEmpty()){
            mTextView.setVisibility(View.VISIBLE);
            mListView.setVisibility(View.INVISIBLE);
        }else{
            mTextView.setVisibility(View.INVISIBLE);
            mListView.setVisibility(View.VISIBLE);
            Debug.isNull(adapter);
            adapter.resetData(AppUtils.savedEvents);
            mListView.setAdapter(adapter);
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Event event = (Event) parent.getItemAtPosition(position);
                    Intent i = new Intent(getActivity(), StarContentActivity.class);
                    i.putExtra(EXTRA_ID, Integer.parseInt(event.getID()));
                    startActivity(i);
                }
            });
        }
    }

    @Override
    public void removeItem(SwipeListView.RemoveDirection direction, int position) {
        Debug.showLog(String.valueOf(position));
        Event event = (Event)adapter.getItem(position);
        adapter.remove(event);
        mListView.setAdapter(adapter);
        AppUtils.savedEvents.remove(Integer.parseInt(event.getID()));
        new DelEventAsyncTask().execute(event);
        if(AppUtils.savedEvents.isEmpty()){
            mTextView.setVisibility(View.VISIBLE);
            mListView.setVisibility(View.INVISIBLE);
        }
    }

    private class DelEventAsyncTask extends AsyncTask<Event, Void, Void> {

        @Override
        protected Void doInBackground(Event... params) {
            Event event = params[0];
            try {
                AppUtils.deleteStar(event.getID() + ".event");
                AppUtils.deleteStar(event.getID() + ".png");
            } catch (Exception e) {
                e.printStackTrace();
            }return null;
        }
    }
}
