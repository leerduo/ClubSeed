package cn.edu.ustc.appseed.clubseed.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

import cn.edu.ustc.appseed.clubseed.R;
import cn.edu.ustc.appseed.clubseed.data.Event;
import cn.edu.ustc.appseed.clubseed.data.ListPhp;
import cn.edu.ustc.appseed.clubseed.utils.AppUtils;

/**
 * Created by Hengruo on 2015/3/6.
 */
public class StarListViewAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private LinkedList<Event> mEvents;
    public static final int red = 0xfffa3746;

    public StarListViewAdapter(Activity activity){
        this.activity = activity;
        mEvents = new LinkedList<>();
    }

    public StarListViewAdapter(Activity activity, HashMap<Integer, Event> events) {
        this.activity = activity;
        mEvents.addAll(events.values());
    }

    public void resetData(HashMap<Integer, Event> eventHashMap) {
        mEvents.clear();
        mEvents.addAll(eventHashMap.values());
    }

    public void remove(Event event){
        mEvents.remove(event);
    }

    @Override
    public int getCount() {
        return mEvents.size();
    }

    @Override
    public Object getItem(int position) {
        return mEvents.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_events, null);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日HH:mm");
        Date curDate = new Date(System.currentTimeMillis());
        String date = formatter.format(curDate);
        String eventdate = mEvents.get(position).getTime();

        TextView textViewClubName = (TextView) convertView.findViewById(R.id.textViewClubName);
        TextView textViewTitle = (TextView) convertView.findViewById(R.id.textViewTitle);
        TextView textViewPlace = (TextView) convertView.findViewById(R.id.textViewPlace);
        TextView textViewTime = (TextView) convertView.findViewById(R.id.textViewTime);
        TextView textViewSummary = (TextView) convertView.findViewById(R.id.textViewSummary);

        ImageView imageViewThumbnail = (ImageView) convertView.findViewById(R.id.imageViewThumbnail);

        textViewClubName.setText(mEvents.get(position).getClubname());
        textViewTitle.setText(mEvents.get(position).getTitle());
        textViewPlace.setText(mEvents.get(position).getPlace());
        if (eventdate.compareTo(date) > 0) {
            textViewTime.setText(eventdate);
        } else {
            String text = "<font color='#fa3746'>"+eventdate + " 【已过期】</font>";
            textViewTime.setText(Html.fromHtml(text));
        }
        textViewSummary.setText(mEvents.get(position).getSummary());

        TextDrawable drawable = TextDrawable.builder()
                .buildRound(mEvents.get(position).getClubname().substring(0, 1), red);
        imageViewThumbnail.setImageDrawable(drawable);

        return convertView;
    }
}
